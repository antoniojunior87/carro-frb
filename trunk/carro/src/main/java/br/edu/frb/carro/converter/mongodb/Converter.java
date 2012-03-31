package br.edu.frb.carro.converter.mongodb;

import br.edu.frb.carro.enums.Sexo;
import com.mongodb.DBObject;

/**
 *
 * @author joelamalio
 */
public interface Converter<T> {

    /**
     * A partir de um objeto da aplicacao, cria documento BSON correspondente.
     */
    public DBObject toDbObject(final T t);

    /**
     * A partir de um documento BSON, cria objeto da aplicacao correspondente.
     */
    public T toObject(final DBObject dbObject);
}
