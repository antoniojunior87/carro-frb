package br.edu.frb.carro.entity;

import br.edu.frb.carro.enums.Sexo;
import java.io.Serializable;

/**
 *
 * @author joelamalio
 */
public class Dono implements Serializable {

    private Long cpf; 
    private String nome;
    private Sexo sexo;

    public Dono() {
        super();
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dono other = (Dono) obj;
        if (this.cpf != other.cpf && (this.cpf == null || !this.cpf.equals(other.cpf))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.cpf != null ? this.cpf.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Dono{" + "cpf=" + cpf + ", nome=" + nome + '}';
    }
    
}
