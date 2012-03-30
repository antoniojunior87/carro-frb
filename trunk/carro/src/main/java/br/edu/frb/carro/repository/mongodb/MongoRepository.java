package br.edu.frb.carro.repository.mongodb;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import java.net.UnknownHostException;
import javax.annotation.PostConstruct;

/**
 *
 * @author joelamalio
 */
public class MongoRepository {
    
    private static final String DB_NAME = "carroMongoDB";
    private static final String DB_TABLE_ITEM = "itemTable";

    private DB db;
    private DBCollection itemDBCollection;

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
        this.itemDBCollection = this.db.getCollection(DB_TABLE_ITEM);
        if (this.itemDBCollection == null) {
            this.itemDBCollection = this.db.createCollection(DB_TABLE_ITEM, null);
        }
        
    }
    
    public DB getDataBase() {
        return this.db;
    }
    
    public DBCollection getItemDBCollection() {
        return this.itemDBCollection;
    }
}
