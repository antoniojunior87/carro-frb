package br.edu.frb.carro.service.mongodb.impl;

import br.edu.frb.carro.converter.mongodb.Converter;
import br.edu.frb.carro.converter.mongodb.impl.CarroConverter;
import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.exception.ListaException;
import br.edu.frb.carro.service.CarroService;
import br.edu.frb.carro.service.mongodb.ab.MongoDbServiceAb;
import br.edu.frb.carro.util.Util;
import com.mongodb.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joelamalio
 */
public class CarroMongoDbServiceImpl extends MongoDbServiceAb implements CarroService {

    private DBCollection carroDbCollection;
    private Converter<Carro> converter;

    public CarroMongoDbServiceImpl() {
        super();
        this.converter = new CarroConverter();
        this.carroDbCollection = super.getMongoRepository().getCarroDbCollection();
    }

    @Override
    public List<Carro> obterPorFiltro(Carro carro) {
        DBObject dBObject = this.converter.toDbObject(carro);
        DBCursor dBCursor = this.carroDbCollection.find(dBObject);

        setQuery("carroDbCollection.find(carro)");

        Carro carroTemp;
        List<Carro> carros = new ArrayList<Carro>();

        while (dBCursor.hasNext()) {
            carroTemp = this.converter.toObject(dBCursor.next());
            carros.add(carroTemp);
        }
        return carros;
    }

    @Override
    public Carro obterPorChassi(Long chassi) {
        DBObject dBObject = new BasicDBObject();
        dBObject.put(Carro.CAMPO_CHASSI, chassi);

        setQuery("carroDbCollection.findOne(chassi)");

        dBObject = this.carroDbCollection.findOne(dBObject);
        return this.converter.toObject(dBObject);
    }

    @Override
    public boolean salvar(final Carro carro) {
        this.validar(carro);
        DBObject dBObject = this.converter.toDbObject(carro);

        setQuery("carroDbCollection.save(carro)");

        WriteResult writeResult = this.carroDbCollection.save(dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao inserir Item no MongoDB");
            return false;
        }
        return true;
    }

    @Override
    public boolean excluir(Long chassi) {
        Carro carro = Carro.Builder.get().comChassi(chassi).criar();
        DBObject dBObject = this.converter.toDbObject(carro);

        setQuery("carroDbCollection.remove(chassi)");

        WriteResult writeResult = this.carroDbCollection.remove(dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao excluir Item no MongoDB");
            return false;
        }
        return true;
    }
    
    private void validar(final Carro carro) throws ListaException {
        ListaException listaException = new ListaException();
        if (carro == null) {
            listaException.inserirException("exception_carro_null");
        } else {
            if (Util.isNullOrEmpty(carro.getChassi())) {
                listaException.inserirException("warn_campo_obrigatorio", "label_carro_chassi");
            }
        }
        if (!Util.isNullOrEmpty(listaException.getExceptions())) {
            throw listaException;
        }
    }
}
