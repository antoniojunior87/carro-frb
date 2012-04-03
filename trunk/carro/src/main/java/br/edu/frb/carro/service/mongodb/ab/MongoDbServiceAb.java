package br.edu.frb.carro.service.mongodb.ab;

import br.edu.frb.carro.repository.mongodb.MongoRepository;
import java.io.Serializable;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joelamalio
 */
public abstract class MongoDbServiceAb implements Serializable {

    private final Logger LOG = LoggerFactory.getLogger(MongoDbServiceAb.class);
    private MongoRepository mongoRepository;
    private static String query;

    public MongoDbServiceAb() {
        super();
        try {
            this.mongoRepository = new MongoRepository();
        } catch (UnknownHostException ex) {
            LOG.error("UnknownHostException", ex);
        }
    }

    public MongoRepository getMongoRepository() {
        return mongoRepository;
    }

    protected void setQuery(String pQuery) {
        query = pQuery;
    }

    public static String obterUltimaConsulta() {
        String temp = query;
        query = null;
        return temp;
    }
}
