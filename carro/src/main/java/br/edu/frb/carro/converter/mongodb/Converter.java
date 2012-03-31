package br.edu.frb.carro.converter.mongodb;

import com.mongodb.DBObject;

/**
 *
 * @author joelamalio
 */
public interface Converter<T> {
    
    public static final String _ID = "_id";

    /**
     * A partir de um objeto da aplicacao, cria documento BSON correspondente.
     */
    public DBObject toDbObject(final T t);

    /**
     * A partir de um documento BSON, cria objeto da aplicacao correspondente.
     */
    public T toObject(final DBObject dbObject);
}
