package br.edu.frb.carro.enums;

import java.io.Serializable;

/**
 *
 * @author joelamalio
 */
public enum Sexo implements Serializable {

    MASCULINO("M", "Masculino"),
    FEMININO("F", "Feminino");
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_POR_EXTENSO = "porExtenso";
    private final String id;
    private final String porExtenso;

    private Sexo(final String id, final String porExtenso) {
        this.id = id;
        this.porExtenso = porExtenso;
    }

    public String getId() {
        return id;
    }

    public String getPorExtenso() {
        return porExtenso;
    }
    
    public static Sexo from(String id) {
        for (Sexo s : values()) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Sexo{" + "id=" + id + ", porExtenso=" + porExtenso + '}';
    }
}
