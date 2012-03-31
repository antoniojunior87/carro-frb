package br.edu.frb.carro.converter.mongodb.impl;

import br.edu.frb.carro.converter.mongodb.Converter;
import br.edu.frb.carro.entity.Item;
import br.edu.frb.carro.util.Util;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *
 * @author joelamalio
 */
public class ItemConverter implements Converter<Item> {

    public ItemConverter() {
        super();
    }

    @Override
    public DBObject toDbObject(final Item item) {
        DBObject dBObject = new BasicDBObject();
        if (item != null) {
            if (item.getId() != null) {
                dBObject.put(Item.CAMPO_ID, item.getId());
            }
            if (!Util.isNullOrEmpty(item.getNome())) {
                dBObject.put(Item.CAMPO_NOME, item.getNome());
            }
        }
        return dBObject;
    }

    @Override
    public Item toObject(final DBObject dbObject) {
        Item item = null;
        if (dbObject != null) {
            item = Item.Builder.get().comId((Long) dbObject.get(Item.CAMPO_ID)).comNome((String) dbObject.get(Item.CAMPO_NOME)).criar();
        }
        return item;
    }
}
