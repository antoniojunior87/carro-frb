package br.edu.frb.carro.web;

import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.service.CarroService;
import br.edu.frb.carro.service.mongodb.impl.CarroMongoDbServiceImpl;
import br.edu.frb.carro.service.mysql.impl.CarroMySqlServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * @author antonio.junior
 */
@ViewScoped
@ManagedBean(name = "batchFaces")
public class BatchFaces {

    private static final int QTD_PADRAO = 2000;
    private Integer quantidade;
    private List<Carro> listaCarros = Collections.EMPTY_LIST;
    private int progresso;
    private CarroService carroServiceMySql = new CarroMySqlServiceImpl();
    private CarroService carroServiceMongo = new CarroMongoDbServiceImpl();

    public void confirmar() {
        for (progresso = 0; progresso < listaCarros.size(); progresso++) {
            getCarroService().salvar(listaCarros.get(progresso));
        }
    }

    public void gerarDados() {
        listaCarros = new ArrayList<Carro>();
        quantidade = quantidade != null ? quantidade : QTD_PADRAO;
        for (int i = 0; i < quantidade; i++) {
            Carro item = new Carro();
            item.setAno(1 + i);
            item.setChassi((long) i);
            item.setModelo("Modelo " + i);
            listaCarros.add(item);
        }
    }

    public Integer getProgresso() {
        Integer p = 0;
        if (quantidade != null & quantidade > 0 && progresso > 0) {
            p = (progresso * 100) / quantidade;
        }
        return p;
    }

    public void completou() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Progresso Completado", "Progresso Completado"));
    }

    private CarroService getCarroService() {
        return IndexFaces.obterBancoSelecionado() == 1 ? carroServiceMySql : carroServiceMongo;
    }

    //<editor-fold defaultstate="collapsed" desc="get and set">
    public List<Carro> getListaCarros() {
        return listaCarros;
    }

    public void setListaCarros(List<Carro> listaCarros) {
        this.listaCarros = listaCarros;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    //</editor-fold>
}
