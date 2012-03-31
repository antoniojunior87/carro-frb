package br.edu.frb.carro.service.mysql.ab;

import br.edu.frb.carro.repository.mysql.MySqlRepository;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joelamalio
 */
public abstract class MySqlServiceAb implements Serializable {

    private final Logger LOG = LoggerFactory.getLogger(MySqlServiceAb.class);
    private MySqlRepository mySqlRepository;

    public MySqlServiceAb() {
        super();
        this.mySqlRepository = new MySqlRepository();
    }

    protected MySqlRepository getMySqlRepository() {
        return mySqlRepository;
    }
}
