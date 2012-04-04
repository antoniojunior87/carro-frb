package br.edu.frb.carro.web;

import br.edu.frb.carro.entity.Item;
import br.edu.frb.carro.exception.ListaException;
import br.edu.frb.carro.repository.mysql.MySqlRepository;
import br.edu.frb.carro.service.ItemService;
import br.edu.frb.carro.service.mongodb.impl.ItemMongoDbServiceImpl;
import br.edu.frb.carro.service.mysql.impl.ItemMySqlServiceImpl;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author joelamalio
 */
@ViewScoped
@ManagedBean(name = "itemFaces")
public class ItemFaces {

    private ItemService itemServiceMySql = new ItemMySqlServiceImpl();
    private ItemService itemServiceMongoDb = new ItemMongoDbServiceImpl();
    private Item filtro;
    private Item item;
    private List<Item> itens = Collections.EMPTY_LIST;
    private String query;

    @PostConstruct
    public void init() {
        this.filtro = new Item();
        this.item = new Item();
        this.updateQuery();
        this.itens = this.getItemService().obterPorFiltro(null);
    }

    public void limpar() {
        this.filtro = new Item();
    }

    public void pesquisar() {
        this.itens = getItemService().obterPorFiltro(filtro);
        this.updateQuery();
    }

    public String salvar() {
        String retorno = null;
        try {
            this.getItemService().salvar(this.item);
            this.updateQuery();
            this.item = new Item();
            retorno = "listaItem.xhtml";
        } catch (ListaException le) {
            for (String exception : le.getExceptions()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(exception));
            }
        }
        return retorno;
    }

    public void excluir(Item itemSelecionado) {
        this.getItemService().excluir(itemSelecionado.getId());
        this.updateQuery();
        this.itens = this.getItemService().obterPorFiltro(filtro);
    }

    public Item getFiltro() {
        return filtro;
    }

    public void setFiltro(Item filtro) {
        this.filtro = filtro;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Item> getItens() {
        if (this.itens == null) {
            this.itens = this.getItemService().obterPorFiltro(null);
        }
        return this.itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public String getQuery() {
        return query;
    }

    public void updateQuery() {
        if (IndexFaces.obterBancoSelecionado() == 1) {
            query = MySqlRepository.obterUltimaConsulta();
        } else {
            query = ItemMongoDbServiceImpl.obterUltimaConsulta();
        }
    }

    private ItemService getItemService() {
        return IndexFaces.obterBancoSelecionado() == 1 ? itemServiceMySql : itemServiceMongoDb;
    }
}
