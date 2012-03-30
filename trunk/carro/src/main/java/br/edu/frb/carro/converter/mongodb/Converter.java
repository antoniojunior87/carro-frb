package br.edu.frb.carro.converter.mongodb;

import com.mongodb.DBObject;

/**
 *
 * @author joelamalio
 */
public interface Converter<T> {

    /**
     * A partir de um objeto da aplicação, cria documento BSON correspondente.
     */
    public DBObject toDBObject(final T t);

    /**
     * A partir de um documento BSON, cria objeto da aplicação correspondente.
     */
    public T toObject(final DBObject dBObject);
}
