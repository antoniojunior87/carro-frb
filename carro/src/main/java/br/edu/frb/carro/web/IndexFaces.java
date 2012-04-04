package br.edu.frb.carro.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * @author antonio.junior
 */
@SessionScoped
@ManagedBean(name = "indexFaces")
public class IndexFaces implements Serializable {

    public static final String PARAMETRO_BANCO_SELECIONADO = "br.edu.frb.carro.web.BANCO_SELECIONADO";

    public List<SelectItem> getListaBancos() {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        lista.add(new SelectItem(1, "Mysql"));
        lista.add(new SelectItem(2, "MongoDB"));
        return lista;
    }

    public void setBancoSelecionado(Integer id) {
        System.getProperties().put(PARAMETRO_BANCO_SELECIONADO, id);
    }

    public Integer getBancoSelecionado() {
        return IndexFaces.obterBancoSelecionado();
    }

    public static Integer obterBancoSelecionado() {
        Integer banco = (Integer) System.getProperties().get(PARAMETRO_BANCO_SELECIONADO);
        if (banco == null) {
            banco = 1;
            System.getProperties().put(PARAMETRO_BANCO_SELECIONADO, banco);
        }
        return banco;
    }

    public String getBanco() {
        return (Integer) System.getProperties().get(PARAMETRO_BANCO_SELECIONADO) == 1 ? "Mysql" : "MongoDB";
    }
}