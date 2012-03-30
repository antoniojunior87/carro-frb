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
public abstract class ServiceAb implements Serializable {

    protected static final String _ID = "_id";
    private final Logger LOG = LoggerFactory.getLogger(ServiceAb.class);
    private MongoRepository mongoRepository;

    public ServiceAb() {
        super();
        try {
            this.mongoRepository = new MongoRepository();
        } catch (UnknownHostException ex) {
            LOG.error("UnknownHostException", ex);
        }
    }

    protected MongoRepository getMongoRepository() {
        return mongoRepository;
    }
}
