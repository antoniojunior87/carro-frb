package br.edu.frb.carro.service.mysql.impl;

import br.edu.frb.carro.entity.Item;
import br.edu.frb.carro.service.ItemService;
import br.edu.frb.carro.service.mysql.ab.MySqlServiceAb;
import br.edu.frb.carro.util.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joelamalio
 */
public class ItemMySqlServiceImpl extends MySqlServiceAb implements ItemService {

    private final Logger LOG = LoggerFactory.getLogger(ItemMySqlServiceImpl.class);

    @Override
    public List<Item> obterPorFiltro(Item item) {
        List<Item> itens = new ArrayList<Item>();
        Item itemTemp;
        ResultSet resultSet;
        StringBuilder query = new StringBuilder();
        query.append("  SELECT ");
        query.append("      item_id, ");
        query.append("      item_nome ");
        query.append("  FROM ");
        query.append("      carro.item ");
        query.append("  WHERE 1 = 1 ");
        if (item != null) {
            if (item.getId() != null) {
                query.append("  AND item_id = ");
                query.append(item.getId());
            }
            if (!Util.isNullOrEmpty(item.getNomeFormatado())) {
                query.append("  AND UPPER(item_nome) LIKE ");
                query.append("'%");
                query.append(item.getNomeFormatado());
                query.append("%'");
            }
        }
        query.append("  ORDER BY ");
        query.append("      item_nome ASC ");

        resultSet = super.getMySqlRepository().executeQuery(query.toString());

        try {
            while (resultSet.next()) {
                itemTemp = Item.Builder.get().comId(resultSet.getLong("item_id")).comNome(resultSet.getString("item_nome")).criar();
                itens.add(itemTemp);
            }
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
        } finally {
            super.getMySqlRepository().close();
        }

        return itens;
    }

    @Override
    public Item obterPorId(Long id) {
        Item itemTemp = null;
        ResultSet resultSet;
        StringBuilder query = new StringBuilder();
        query.append("  SELECT ");
        query.append("      item_id, ");
        query.append("      item_nome ");
        query.append("  FROM ");
        query.append("      carro.item ");
        query.append("  WHERE 1 = 1 ");
        query.append("  AND item_id = ");
        query.append(id);
        resultSet = super.getMySqlRepository().executeQuery(query.toString());

        try {
            while (resultSet.next()) {
                itemTemp = Item.Builder.get().comId(resultSet.getLong("item_id")).comNome(resultSet.getString("item_nome")).criar();
            }
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
        } finally {
            super.getMySqlRepository().close();
        }
        return itemTemp;
    }

    @Override
    public boolean salvar(Item item) {
        String query;
        if (this.obterPorId(item.getId()) != null) {
            query = this.alterar(item);
        } else {
            query = this.inserir(item);
        }
        return super.getMySqlRepository().executeUpdate(query);
    }

    private String inserir(final Item item) {
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO carro.item ( ");
        query.append("      item_id, ");
        query.append("      item_nome ");
        query.append("  ) VALUES (");
        query.append(item.getId());
        query.append(", ");
        query.append("'");
        query.append(item.getNomeFormatado());
        query.append("'");
        query.append(") ");
        return query.toString();
    }

    private String alterar(final Item item) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE carro.item ");
        query.append("  SET ");
        query.append("      item_nome = ");
        query.append("'");
        query.append(item.getNomeFormatado());
        query.append("'");
        query.append("  WHERE 1 = 1 ");
        query.append("  AND item_id = ");
        query.append(item.getId());
        return query.toString();
    }

    @Override
    public boolean excluir(Long id) {
        StringBuilder query = new StringBuilder();
        query.append("  DELETE FROM ");
        query.append("      carro.item ");
        query.append("  WHERE 1 = 1 ");
        if (id != null) {
            query.append("  AND item_id = ");
            query.append(id);
        }

        return super.getMySqlRepository().executeUpdate(query.toString());
    }
}
