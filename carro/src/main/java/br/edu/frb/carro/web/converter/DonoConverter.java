package br.edu.frb.carro.web.converter;

import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.util.Util;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author joelamalio
 */
public class DonoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (Util.isNullOrEmpty(value)) {
            return new Dono();
        }
        String[] str = value.split(";");
        return Dono.Builder.get().comCpf(Long.valueOf(str[0])).comNome(str[1]).criar();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (Util.isNullOrEmpty(value)) {
            return null;
        }
        if (value instanceof Dono) {
            Dono dono = (Dono) value;
            if (dono.getCpf() != null) {
                return dono.getCpf().toString() + ";" + dono.getNome();
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException(
                    "object: "
                    + value
                    + " of type: "
                    + value.getClass().getName()
                    + "; expected type: br.edu.frb.carro.web.converter"
                    + this.getClass().getName());
        }
    }
    
}
