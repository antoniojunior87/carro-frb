package br.edu.frb.carro.exception;

import br.edu.frb.carro.util.MessageUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author joelamalio
 */
public class ListaException extends RuntimeException {

    private List<String> exceptions;

    public ListaException() {
        super();
        this.exceptions = new ArrayList<String>();
    }

    public List<String> getExceptions() {
        return Collections.unmodifiableList(this.exceptions);
    }

    public void setExceptions(List<String> exceptions) {
        this.exceptions = exceptions;
    }

    public void inserirException(String exception, String... params) {
        this.exceptions.add(MessageUtil.getMessage(exception, params));
    }

    public void excluirException(String exception) {
        this.exceptions.remove(MessageUtil.getMessage(exception));
    }

    public static class Builder {

        private ListaException listaException;

        private Builder() {
            this.listaException = new ListaException();
        }

        public static ListaException.Builder get() {
            return new ListaException.Builder();
        }

        public ListaException criar() {
            return this.listaException;
        }

        public ListaException.Builder com(final String exception, final String... params) {
            this.listaException.inserirException(exception, params);
            return this;
        }
        
        public ListaException.Builder com(final String exception) {
            return com(exception);
        }
    }
}
