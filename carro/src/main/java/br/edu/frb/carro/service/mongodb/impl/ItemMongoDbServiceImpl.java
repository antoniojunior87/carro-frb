package br.edu.frb.carro.service.mongodb.impl;

import br.edu.frb.carro.converter.mongodb.Converter;
import br.edu.frb.carro.converter.mongodb.impl.ItemConverter;
import br.edu.frb.carro.entity.Item;
import br.edu.frb.carro.exception.ListaException;
import br.edu.frb.carro.service.ItemService;
import br.edu.frb.carro.service.mongodb.ab.MongoDbServiceAb;
import br.edu.frb.carro.util.Util;
import com.mongodb.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joelamalio
 */
public class ItemMongoDbServiceImpl extends MongoDbServiceAb implements ItemService {

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
    public boolean salvar(final Item item) throws ListaException {
        this.validar(item);
        DBObject dBObject = this.converter.toDbObject(item);
        WriteResult writeResult = this.itemDbCollection.save(dBObject);
        if (writeResult.getError() != null) {
            // throw new RuntimeException("Erro ao inserir Item no MongoDB");
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
    
    private void validar(final Item item) throws ListaException {
        ListaException listaException = new ListaException();
        if (item == null) {
            listaException.inserirException("exception_item_null");
        } else {
            if (Util.isNullOrEmpty(item.getId())) {
                listaException.inserirException("warn_campo_obrigatorio", "label_item_id");
            }
            if (Util.isNullOrEmpty(item.getNome())) {
                listaException.inserirException("warn_campo_obrigatorio", "label_item_nome");
            }
        }
        if (!Util.isNullOrEmpty(listaException.getExceptions())) {
            throw listaException;
        }
    }
}
