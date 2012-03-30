package br.edu.frb.carro.service.mongodb.impl;

import br.edu.frb.carro.entity.Item;
import br.edu.frb.carro.converter.mongodb.Converter;
import br.edu.frb.carro.converter.mongodb.impl.ItemConverter;
import br.edu.frb.carro.service.ItemService;
import br.edu.frb.carro.service.mongodb.ab.ServiceAb;
import com.mongodb.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joelamalio
 */
public class ItemMongoDBServiceImpl extends ServiceAb implements ItemService {

    private DBCollection itemDBCollection;
    private Converter<Item> converter;

    public ItemMongoDBServiceImpl() {
        super();
        this.converter = new ItemConverter();
        this.itemDBCollection = super.getMongoRepository().getItemDBCollection();
    }

    @Override
    public List<Item> obterPorFiltro(Item item) {
        DBObject dBObject = this.converter.toDBObject(item);
        DBCursor dBCursor = this.itemDBCollection.find(dBObject);
        Item itemTemp;
        List<Item> itens = new ArrayList<Item>();

        while (dBCursor.hasNext()) {
            itemTemp = this.converter.toObject(dBCursor.next());
            itens.add(itemTemp);
        }
        return itens;
    }

    @Override
    public Item obterPorId(Long id) {
        DBObject dBObject = new BasicDBObject();
        dBObject.put(Item.CAMPO_ID, id);
        dBObject = this.itemDBCollection.findOne(dBObject);
        return this.converter.toObject(dBObject);
    }

    @Override
    public boolean inserir(Item item) {
        DBObject dBObject = this.converter.toDBObject(item);
        WriteResult writeResult = this.itemDBCollection.insert(dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao inserir Item no MongoDB");
            return false;
        }
        return true;
    }

    @Override
    public boolean alterar(Item item) {
        Item itemBanco = this.obterPorId(item.getId());
        DBObject dBObjectBanco = this.converter.toDBObject(itemBanco);
        DBObject dBObject = this.converter.toDBObject(item);
        WriteResult writeResult = this.itemDBCollection.update(dBObjectBanco, dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao alterar Item no MongoDB");
            return false;
        }
        return true;
    }

    @Override
    public boolean excluir(Long id) {
        Item item = Item.Builder.get().comId(id).criar();
        DBObject dBObject = this.converter.toDBObject(item);
        WriteResult writeResult = this.itemDBCollection.remove(dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao excluir Item no MongoDB");
            return false;
        }
        return true;
    }
}
