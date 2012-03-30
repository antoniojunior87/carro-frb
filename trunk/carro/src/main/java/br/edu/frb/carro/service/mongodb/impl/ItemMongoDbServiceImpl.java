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
public class ItemMongoDbServiceImpl extends ServiceAb implements ItemService {

    private DBCollection itemDbCollection;
    private Converter<Item> converter;

    public ItemMongoDbServiceImpl() {
        super();
        this.converter = new ItemConverter();
        this.itemDbCollection = super.getMongoRepository().getItemDbCollection();
    }

    @Override
    public List<Item> obterPorFiltro(Item item) {
        DBObject dBObject = this.converter.toDbObject(item);
        DBCursor dBCursor = this.itemDbCollection.find(dBObject);
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
        dBObject = this.itemDbCollection.findOne(dBObject);
        return this.converter.toObject(dBObject);
    }

    @Override
    public boolean inserir(Item item) {
        DBObject dBObject = this.converter.toDbObject(item);
        WriteResult writeResult = this.itemDbCollection.insert(dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao inserir Item no MongoDB");
            return false;
        }
        return true;
    }

    @Override
    public boolean alterar(Item item) {
        Item itemBanco = this.obterPorId(item.getId());
        DBObject dBObjectBanco = this.converter.toDbObject(itemBanco);
        DBObject dBObject = this.converter.toDbObject(item);
        WriteResult writeResult = this.itemDbCollection.update(dBObjectBanco, dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao alterar Item no MongoDB");
            return false;
        }
        return true;
    }

    @Override
    public boolean excluir(Long id) {
        Item item = Item.Builder.get().comId(id).criar();
        DBObject dBObject = this.converter.toDbObject(item);
        WriteResult writeResult = this.itemDbCollection.remove(dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao excluir Item no MongoDB");
            return false;
        }
        return true;
    }
}
