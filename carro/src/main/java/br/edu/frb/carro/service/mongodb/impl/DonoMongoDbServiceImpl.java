package br.edu.frb.carro.service.mongodb.impl;

import br.edu.frb.carro.converter.mongodb.Converter;
import br.edu.frb.carro.converter.mongodb.impl.DonoConverter;
import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.service.DonoService;
import br.edu.frb.carro.service.mongodb.ab.ServiceAb;
import com.mongodb.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joelamalio
 */
public class DonoMongoDbServiceImpl extends ServiceAb implements DonoService {

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
    public boolean inserir(Dono dono) {
        DBObject dBObject = this.converter.toDbObject(dono);
        WriteResult writeResult = this.donoDbCollection.insert(dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao inserir Item no MongoDB");
            return false;
        }
        return true;
    }

    @Override
    public boolean alterar(Dono dono) {
        Dono donoBanco = this.obterPorCpf(dono.getCpf());
        DBObject dBObjectBanco = this.converter.toDbObject(donoBanco);
        DBObject dBObject = this.converter.toDbObject(dono);
        WriteResult writeResult = this.donoDbCollection.update(dBObjectBanco, dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao alterar Item no MongoDB");
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
    
}
