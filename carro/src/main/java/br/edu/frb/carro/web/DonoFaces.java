package br.edu.frb.carro.web;

import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.enums.Sexo;
import br.edu.frb.carro.exception.ListaException;
import br.edu.frb.carro.repository.mysql.MySqlRepository;
import br.edu.frb.carro.service.DonoService;
import br.edu.frb.carro.service.mongodb.impl.DonoMongoDbServiceImpl;
import br.edu.frb.carro.service.mysql.impl.DonoMySqlServiceImpl;
import br.edu.frb.carro.util.Util;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * @author joelamalio
 */
@ViewScoped
@ManagedBean(name = "donoFaces")
public class DonoFaces implements Serializable {

    private DonoService donoServiceMySql = new DonoMySqlServiceImpl();
    private DonoService donoServiceMongoDb = new DonoMongoDbServiceImpl();
    private Dono filtro;
    private Dono dono;
    private List<Dono> donos = Collections.EMPTY_LIST;
    private String query;

    @PostConstruct
    public void init() {
        this.filtro = new Dono();
        this.dono = new Dono();
        this.updateQuery();
        this.donos = getDonoService().obterPorFiltro(null);
    }

    public void limpar() {
        this.filtro = new Dono();
    }

    public void pesquisar() {
        this.donos = getDonoService().obterPorFiltro(this.filtro);
        this.updateQuery();
    }

    public String salvar() {
        String retorno = null;
        try {
            this.getDonoService().salvar(this.dono);
            this.updateQuery();
            this.dono = new Dono();
            retorno = "listaDono.xhtml";
        } catch (ListaException le) {
            for (String exception : le.getExceptions()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(exception));
            }
        }
        return retorno;
    }

    public void excluir(Dono donoSelecionado) {
        this.getDonoService().excluir(donoSelecionado.getCpf());
        this.updateQuery();
        this.donos = getDonoService().obterPorFiltro(this.filtro);
    }

    public Dono getDono() {
        return dono;
    }

    public void setDono(Dono dono) {
        this.dono = dono;
    }

    public Dono getFiltro() {
        return filtro;
    }

    public void setFiltro(Dono filtro) {
        this.filtro = filtro;
    }

    public List<Dono> getDonos() {
        if (Util.isNullOrEmpty(this.donos)) {
            this.donos = getDonoService().obterPorFiltro(null);
        }
        return this.donos;
    }

    public void setDonos(List<Dono> donos) {
        this.donos = donos;
    }

    public String getQuery() {
        return query;
    }

    public void updateQuery() {
        if (IndexFaces.obterBancoSelecionado() == 1) {
            query = MySqlRepository.obterUltimaConsulta();
        } else {
            query = DonoMongoDbServiceImpl.obterUltimaConsulta();
        }
    }

    private DonoService getDonoService() {
        return IndexFaces.obterBancoSelecionado() == 1 ? donoServiceMySql : donoServiceMongoDb;
    }
    
    public List<Sexo> getSexos() {
        return Arrays.asList(Sexo.values());
    }
}