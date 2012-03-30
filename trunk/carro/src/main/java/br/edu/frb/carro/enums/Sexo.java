package br.edu.frb.carro.enums;

import java.io.Serializable;

/**
 *
 * @author joelamalio
 */
public enum Sexo implements Serializable {
    
    MASCULINO("M", "Masculino"),
    FEMININO("F", "Feminino");
    
    private final String id;
    private final String porExtenso;
    
    private Sexo(final String id, final String porExtenso) {
        this.id = id;
        this.porExtenso = porExtenso;
    }
    
}
