package br.edu.frb.carro.converter.mongodb.impl;

import br.edu.frb.carro.converter.mongodb.Converter;
import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.util.Util;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *
 * @author joelamalio
 */
public class CarroConverter implements Converter<Carro> {
    
    private Converter donoConverter;

    public CarroConverter() {
        super();
        this.donoConverter = new DonoConverter();
    }

    @Override
    public DBObject toDbObject(final Carro carro) {
        DBObject dBObject = new BasicDBObject();
        if (carro != null) {
            if (carro.getChassi() != null) {
                dBObject.put(Carro.CAMPO_CHASSI, carro.getChassi());
                dBObject.put(_ID, carro.getChassi().hashCode());
            }
            if (!Util.isNullOrEmpty(carro.getModelo())) {
                dBObject.put(Carro.CAMPO_MODELO, carro.getModelo());
            }
            if (carro.getAno() != null) {
                dBObject.put(Carro.CAMPO_ANO, carro.getAno());
            }
            if (carro.getDono() != null) {
                dBObject.put(Carro.CAMPO_DONO, this.donoConverter.toDbObject(carro.getDono()));
            }
            if (!Util.isNullOrEmpty(carro.getItens())) {
                dBObject.put(Carro.CAMPO_ITENS, carro.getItens());
            }
        }
        return dBObject;
    }

    @Override
    public Carro toObject(DBObject dbObject) {
        Carro carro = null;
        if (dbObject != null) {
            DBObject donoDbObject = (DBObject) dbObject.get(Carro.CAMPO_DONO);
            Dono dono = (donoDbObject != null) ? (Dono) this.donoConverter.toObject(donoDbObject) : null;
            carro = Carro.Builder.get().comChassi((Long) dbObject.get(Carro.CAMPO_CHASSI)).comModelo((String) dbObject.get(Carro.CAMPO_MODELO)).comAno((Integer) dbObject.get(Carro.CAMPO_ANO)).com(dono).criar();
        }
        return carro;
    }
}
