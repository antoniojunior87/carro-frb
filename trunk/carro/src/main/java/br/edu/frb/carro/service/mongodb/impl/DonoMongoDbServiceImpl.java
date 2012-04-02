package br.edu.frb.carro.service.mongodb.impl;

import br.edu.frb.carro.converter.mongodb.Converter;
import br.edu.frb.carro.converter.mongodb.impl.DonoConverter;
import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.exception.ListaException;
import br.edu.frb.carro.service.DonoService;
import br.edu.frb.carro.service.mongodb.ab.MongoDbServiceAb;
import br.edu.frb.carro.util.Util;
import com.mongodb.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joelamalio
 */
public class DonoMongoDbServiceImpl extends MongoDbServiceAb implements DonoService {

    private DBCollection donoDbCollection;
    private Converter<Dono> converter;

    public DonoMongoDbServiceImpl() {
        super();
        this.converter = new DonoConverter();
        this.donoDbCollection = super.getMongoRepository().getDonoDbCollection();
    }

    @Override
    public List<Dono> obterPorFiltro(final Dono dono) {
        DBObject dBObject = this.converter.toDbObject(dono);
        DBCursor dBCursor = this.donoDbCollection.find(dBObject);
        Dono donoTemp;
        List<Dono> donos = new ArrayList<Dono>();

        while (dBCursor.hasNext()) {
            donoTemp = this.converter.toObject(dBCursor.next());
            donos.add(donoTemp);
        }
        return donos;
    }

    @Override
    public Dono obterPorCpf(Long cpf) {
        DBObject dBObject = new BasicDBObject();
        dBObject.put(Dono.CAMPO_CPF, cpf);
        dBObject = this.donoDbCollection.findOne(dBObject);
        return this.converter.toObject(dBObject);
    }

    @Override
    public boolean salvar(final Dono dono) {
        this.validar(dono);
        DBObject dBObject = this.converter.toDbObject(dono);
        WriteResult writeResult = this.donoDbCollection.save(dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao inserir Item no MongoDB");
            return false;
        }
        return true;
    }

    @Override
    public boolean excluir(Long cpf) {
        Dono dono = Dono.Builder.get().comCpf(cpf).criar();
        DBObject dBObject = this.converter.toDbObject(dono);
        WriteResult writeResult = this.donoDbCollection.remove(dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao excluir Item no MongoDB");
            return false;
        }
        return true;
    }
    
    private void validar(final Dono dono) throws ListaException {
        ListaException listaException = new ListaException();
        if (dono == null) {
            listaException.inserirException("exception_dono_null");
        } else {
            if (Util.isNullOrEmpty(dono.getCpf())) {
                listaException.inserirException("warn_campo_obrigatorio", "label_dono_cpf");
            }
        }
        if (!Util.isNullOrEmpty(listaException.getExceptions())) {
            throw listaException;
        }
    }
    
}
