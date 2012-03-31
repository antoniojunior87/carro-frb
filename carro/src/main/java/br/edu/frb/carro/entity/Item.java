package br.edu.frb.carro.entity;

import br.edu.frb.carro.util.Util;
import java.io.Serializable;

/**
 *
 * @author joelamalio
 */
public class Item implements Serializable {

    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOME = "nome";
    private Long id;
    private String nome;

    public Item() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", nome=" + nome + '}';
    }
    
    public String getNomeFormatado() {
        return !Util.isNullOrEmpty(this.nome) ? this.nome.trim().toUpperCase() : null;
    }

    public static class Builder {

        private Item item;

        private Builder() {
            this.item = new Item();
        }

        public static Item.Builder get() {
            return new Item.Builder();
        }

        public Item criar() {
            return this.item;
        }

        public Item.Builder comId(final Long id) {
            this.item.setId(id);
            return this;
        }

        public Item.Builder comNome(final String nome) {
            this.item.setNome(nome);
            return this;
        }
    }
}
