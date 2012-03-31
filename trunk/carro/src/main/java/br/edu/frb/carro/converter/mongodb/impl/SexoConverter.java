package br.edu.frb.carro.converter.mongodb.impl;

import br.edu.frb.carro.converter.mongodb.Converter;
import br.edu.frb.carro.enums.Sexo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *
 * @author joelamalio
 */
public class SexoConverter implements Converter<Sexo> {

    public SexoConverter() {
        super();
    }

    @Override
    public DBObject toDbObject(final Sexo sexo) {
        DBObject dBObject = new BasicDBObject();
        if (sexo != null) {
            dBObject.put(Sexo.CAMPO_ID, sexo.getId());
            dBObject.put(Sexo.CAMPO_POR_EXTENSO, sexo.getPorExtenso());
        }
        return dBObject;
    }

    @Override
    public Sexo toObject(final DBObject dbObject) {
        Sexo sexo = null;
        if (dbObject != null) {
            sexo = Sexo.from((String) dbObject.get(Sexo.CAMPO_ID));
        }
        return sexo;
    }
}
