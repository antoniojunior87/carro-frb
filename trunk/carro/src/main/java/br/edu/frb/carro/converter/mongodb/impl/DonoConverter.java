package br.edu.frb.carro.converter.mongodb.impl;

import br.edu.frb.carro.converter.mongodb.Converter;
import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.entity.Item;
import br.edu.frb.carro.enums.Sexo;
import br.edu.frb.carro.util.Util;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *
 * @author joelamalio
 */
public class DonoConverter implements Converter<Dono> {
    
    private Converter sexoConverter;

    public DonoConverter() {
        super();
        this.sexoConverter = new SexoConverter();
    }

    @Override
    public DBObject toDbObject(final Dono dono) {
        DBObject dBObject = new BasicDBObject();
        if (dono != null) {
            if (dono.getCpf() != null) {
                dBObject.put(Dono.CAMPO_CPF, dono.getCpf());
            }
            if (!Util.isNullOrEmpty(dono.getNome())) {
                dBObject.put(Dono.CAMPO_NOME, dono.getNome());
            }
            if (dono.getSexo() != null) {
                dBObject.put(Dono.CAMPO_SEXO, this.sexoConverter.toDbObject(dono.getSexo()));
            }
        }
        return dBObject;
    }

    @Override
    public Dono toObject(final DBObject dbObject) {
        Dono dono = null;
        if (dbObject != null) {
            DBObject sexoDbObject = (DBObject) dbObject.get(Dono.CAMPO_SEXO);
            Sexo sexo = (sexoDbObject != null) ? (Sexo) this.sexoConverter.toObject(sexoDbObject) : null;
            dono = Dono.Builder.get().comCpf((Long) dbObject.get(Dono.CAMPO_CPF)).comNome((String) dbObject.get(Item.CAMPO_NOME)).com(sexo).criar();
        }
        return dono;
    }
}
