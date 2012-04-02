package br.edu.frb.carro.service;

import br.edu.frb.carro.entity.Item;
import br.edu.frb.carro.exception.ListaException;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author joelamalio
 */
public interface ItemService extends Serializable {

    public abstract List<Item> obterPorFiltro(final Item item);

    public abstract Item obterPorId(final Long id);

    public abstract boolean salvar(Item item) throws ListaException;

    public abstract boolean excluir(final Long id);
}
