package br.edu.frb.carro.service;

import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.exception.ListaException;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author joelamalio
 */
public interface DonoService extends Serializable {

    public abstract List<Dono> obterPorFiltro(final Dono dono);

    public abstract Dono obterPorCpf(final Long cpf);

    public abstract boolean salvar(Dono dono) throws ListaException;

    public abstract boolean excluir(final Long cpf);
}
