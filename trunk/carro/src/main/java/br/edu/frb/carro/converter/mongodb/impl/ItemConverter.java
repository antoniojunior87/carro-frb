package br.edu.frb.carro.converter.mongodb.impl;

import br.edu.frb.carro.entity.Item;
import br.edu.frb.carro.converter.mongodb.Converter;
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
    public DBObject toDBObject(final Item item) {
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
    public Item toObject(final DBObject dBObject) {
        Item item = null;
        if (dBObject != null) {
            item = Item.Builder.get().comId((Long) dBObject.get(Item.CAMPO_ID)).comNome((String) dBObject.get(Item.CAMPO_NOME)).criar();
        }
        return item;
    }
}
