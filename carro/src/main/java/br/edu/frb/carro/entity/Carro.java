package br.edu.frb.carro.entity;

import br.edu.frb.carro.util.Util;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author joelamalio
 */
public class Carro implements Serializable {
    
    
    public static final String CAMPO_CHASSI = "chassi";
    public static final String CAMPO_MODELO = "modelo";
    public static final String CAMPO_ANO = "ano";
    public static final String CAMPO_DONO = "dono";
    public static final String CAMPO_ITENS = "itens";
    private Long chassi;
    private String modelo;
    private Integer ano;
    private Dono dono;
    private Set<Item> itens;

    public Carro() {
        super();
        this.itens = new HashSet<Item>();
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Long getChassi() {
        return chassi;
    }

    public void setChassi(Long chassi) {
        this.chassi = chassi;
    }

    public Dono getDono() {
        return dono;
    }

    public void setDono(Dono dono) {
        this.dono = dono;
    }

    public Set<Item> getItens() {
        return Collections.unmodifiableSet(this.itens);
    }

    public void setItens(Set<Item> itens) {
        this.itens = itens;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public void adicionarItem(final Item item) {
        this.itens.add(item);
    }
    
    public void removerItem(final Item item) {
        this.itens.remove(item);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carro other = (Carro) obj;
        if (this.chassi != other.chassi && (this.chassi == null || !this.chassi.equals(other.chassi))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.chassi != null ? this.chassi.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Carro{" + "chassi=" + chassi + ", modelo=" + modelo + ", ano=" + ano + '}';
    }
    
    public String getModeloFormatado() {
        return !Util.isNullOrEmpty(this.modelo) ? this.modelo.trim().toUpperCase() : null;
    }

    public static class Builder {

        private Carro carro;

        private Builder() {
            this.carro = new Carro();
        }

        public static Builder get() {
            return new Builder();
        }

        public Carro criar() {
            return this.carro;
        }

        public Builder comChassi(final Long chassi) {
            this.carro.setChassi(chassi);
            return this;
        }

        public Builder comModelo(final String modelo) {
            this.carro.setModelo(modelo);
            return this;
        }

        public Builder comAno(final Integer ano) {
            this.carro.setAno(ano);
            return this;
        }
        
        public Builder com(final Dono dono) {
            this.carro.setDono(dono);
            return this;
        }
        
        public Builder comItens(final Set<Item> itens) {
            this.carro.setItens(itens);
            return this;
        }
        
        public Builder com(final Item item) {
            this.carro.adicionarItem(item);
            return this;
        }
    }
}
