package br.edu.frb.carro.repository.mongodb;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import java.net.UnknownHostException;

/**
 *
 * @author joelamalio
 */
public class MongoRepository {

    private static final String DB_NAME = "carroDB";
    private static final String DB_TABLE_ITEM = "itemTable";
    private static final String DB_TABLE_DONO = "donoTable";
    private DB db;
    private DBCollection itemDbCollection;
    private DBCollection donoDbCollection;

    public MongoRepository() throws UnknownHostException {
        initDB(DB_NAME, true);
    }

    protected final void initDB(String dbName, boolean drop) throws UnknownHostException {
        Mongo mongo = new Mongo();

        if (drop) {
            mongo.dropDatabase(dbName);
        }

        this.db = mongo.getDB(dbName);

        // tenta obter a coleção - se não conseguir cria 
        this.itemDbCollection = this.db.getCollection(DB_TABLE_ITEM);
        if (this.itemDbCollection == null) {
            this.itemDbCollection = this.db.createCollection(DB_TABLE_ITEM, null);
        }
        this.donoDbCollection = this.db.getCollection(DB_TABLE_DONO);
        if (this.donoDbCollection == null) {
            this.donoDbCollection = this.db.createCollection(DB_TABLE_DONO, null);
        }

    }

    public DB getDataBase() {
        return this.db;
    }

    public DBCollection getItemDbCollection() {
        return this.itemDbCollection;
    }

    public DBCollection getDonoDbCollection() {
        return donoDbCollection;
    }
}
