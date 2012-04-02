package br.edu.frb.carro.service;

import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.exception.ListaException;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author joelamalio
 */
public interface CarroService extends Serializable {

    public abstract List<Carro> obterPorFiltro(final Carro carro);

    public abstract Carro obterPorChassi(final Long chassi);

    public abstract boolean salvar(Carro carro) throws ListaException;

    public abstract boolean excluir(final Long chassi);
}
