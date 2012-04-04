package br.edu.frb.carro.web;

import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.exception.ListaException;
import br.edu.frb.carro.repository.mysql.MySqlRepository;
import br.edu.frb.carro.service.CarroService;
import br.edu.frb.carro.service.DonoService;
import br.edu.frb.carro.service.mongodb.impl.CarroMongoDbServiceImpl;
import br.edu.frb.carro.service.mongodb.impl.DonoMongoDbServiceImpl;
import br.edu.frb.carro.service.mysql.impl.CarroMySqlServiceImpl;
import br.edu.frb.carro.service.mysql.impl.DonoMySqlServiceImpl;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * @author antonio.junior
 */
@ViewScoped
@ManagedBean(name = "carroFaces")
public class CarroFaces implements Serializable {

    private CarroService carroServiceMySql = new CarroMySqlServiceImpl();
    private DonoService donoServiceMySql = new DonoMySqlServiceImpl();
    private CarroService carroServiceMongo = new CarroMongoDbServiceImpl();
    private DonoService donoServiceMongo = new DonoMongoDbServiceImpl();
    private Carro filtro;
    private Carro carro;
    private List<Carro> listaCarro = Collections.EMPTY_LIST;
    private List<Dono> listaDono = Collections.EMPTY_LIST;
    private String query;

    @PostConstruct
    public void init() {
        filtro = new Carro();
        carro = new Carro();
        updateQuery();
        listaCarro = getCarroService().obterPorFiltro(null);
    }

    public void limpar() {
        filtro = new Carro();
    }

    public void pesquisar() {
        listaCarro = getCarroService().obterPorFiltro(filtro);
        updateQuery();
    }

    public String salvar() {
        String retorno = null;
        try {
            getCarroService().salvar(carro);
            updateQuery();
            carro = new Carro();
            retorno = "listaCarro.xhtml";
        } catch (ListaException le) {
            for (String exception : le.getExceptions()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(exception));
            }
        }
        return retorno;
    }

    public void excluir(Carro carroSelecionado) {
        getCarroService().excluir(carroSelecionado.getChassi());
        updateQuery();
        listaCarro = getCarroService().obterPorFiltro(filtro);
    }

    public List<Carro> getListaCarro() {
        return listaCarro;
    }

    public void setListaCarro(List<Carro> listaCarro) {
        this.listaCarro = listaCarro;
    }

    public Carro getFiltro() {
        return filtro;
    }

    public void setFiltro(Carro filtro) {
        this.filtro = filtro;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public List<Dono> getListaDono() {
        if (listaDono == null) {
            listaDono = getDonoService().obterPorFiltro(null);
        }
        return listaDono;
    }

    public void setListaDono(List<Dono> listaDono) {
        this.listaDono = listaDono;
    }

    public String getQuery() {
        return query;
    }

    public void updateQuery() {
        if (IndexFaces.obterBancoSelecionado() == 1) {
            query = MySqlRepository.obterUltimaConsulta();
        } else {
            query = CarroMongoDbServiceImpl.obterUltimaConsulta();
        }
    }

    private CarroService getCarroService() {
        return IndexFaces.obterBancoSelecionado() == 1 ? carroServiceMySql : carroServiceMongo;
    }

    private DonoService getDonoService() {
        return IndexFaces.obterBancoSelecionado() == 1 ? donoServiceMySql : donoServiceMongo;
    }
}