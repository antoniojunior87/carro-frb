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

    private static final String DB_NAME = "carroDb";
    private static final String DB_TABLE_ITEM = "itemTable";
    private static final String DB_TABLE_DONO = "donoTable";
    private static final String DB_TABLE_CARRO = "carroTable";
    private DB db;
    private DBCollection itemDbCollection;
    private DBCollection donoDbCollection;
    private DBCollection carroDbCollection;

    public MongoRepository() throws UnknownHostException {
        initDB(DB_NAME, false);
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
        this.carroDbCollection = this.db.getCollection(DB_TABLE_CARRO);
        if (this.carroDbCollection == null) {
            this.carroDbCollection = this.db.createCollection(DB_TABLE_CARRO, null);
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

    public DBCollection getCarroDbCollection() {
        return carroDbCollection;
    }
}
