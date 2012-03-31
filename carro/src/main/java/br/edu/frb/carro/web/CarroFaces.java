package br.edu.frb.carro.web;

import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.service.CarroService;
import br.edu.frb.carro.service.mysql.impl.CarroMySqlServiceImpl;
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

    private List<Carro> listaCarro = Collections.EMPTY_LIST;

    private CarroService carroService = new CarroMySqlServiceImpl();

    @PostConstruct
    public void init() {
        listaCarro = carroService.obterPorFiltro(null);
    }

    public List<Carro> getListaCarro() {
        return listaCarro;
    }

    public void setListaCarro(List<Carro> listaCarro) {
        this.listaCarro = listaCarro;
    }
}