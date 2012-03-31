package br.edu.frb.carro.service.mongodb.impl;

import br.edu.frb.carro.converter.mongodb.Converter;
import br.edu.frb.carro.converter.mongodb.impl.CarroConverter;
import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.service.CarroService;
import br.edu.frb.carro.service.mongodb.ab.MongoDbServiceAb;
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
        dBObject = this.carroDbCollection.findOne(dBObject);
        return this.converter.toObject(dBObject);
    }

    @Override
    public boolean salvar(Carro carro) {
        DBObject dBObject = this.converter.toDbObject(carro);
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
        WriteResult writeResult = this.carroDbCollection.remove(dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao excluir Item no MongoDB");
            return false;
        }
        return true;
    }
}
