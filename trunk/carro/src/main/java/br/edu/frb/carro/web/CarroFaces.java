package br.edu.frb.carro.web;

import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.service.CarroService;
import br.edu.frb.carro.service.DonoService;
import br.edu.frb.carro.service.mysql.impl.CarroMySqlServiceImpl;
import br.edu.frb.carro.service.mysql.impl.DonoMySqlServiceImpl;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @author antonio.junior
 */
@ViewScoped
@ManagedBean(name = "carroFaces")
public class CarroFaces {

    private CarroService carroService = new CarroMySqlServiceImpl();
    private DonoService donoService = new DonoMySqlServiceImpl();
    private Carro filtro;
    private Carro carro;
    private List<Carro> listaCarro = Collections.EMPTY_LIST;
    private List<Dono> listaDono = Collections.EMPTY_LIST;

    @PostConstruct
    public void init() {
        filtro = new Carro();
        carro = new Carro();
        listaCarro = getCarroService().obterPorFiltro(null);
        listaDono = getDonoService().obterPorFiltro(null);
    }

    public void limpar() {
        filtro = new Carro();
    }

    public void pesquisar() {
        listaCarro = getCarroService().obterPorFiltro(filtro);
    }

    public String salvar() {
        getCarroService().salvar(carro);
        carro = new Carro();
        return "listaCarro.xhtml";
    }

    public void excluir(Carro carroSelecionado) {
        getCarroService().excluir(carroSelecionado.getChassi());
        pesquisar();
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
        return listaDono;
    }

    public void setListaDono(List<Dono> listaDono) {
        this.listaDono = listaDono;
    }

    public CarroService getCarroService() {
        return carroService;
    }

    public DonoService getDonoService() {
        return donoService;
    }
}