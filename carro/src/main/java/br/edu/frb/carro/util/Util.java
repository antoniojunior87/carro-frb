package br.edu.frb.carro.util;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author joelamalio
 */
public class Util {

    public static boolean isNullOrEmpty(Object valor) {
        if (valor == null) {
            return true;
        } else if (valor instanceof String) {
            String string = (String) valor;
            return string.isEmpty();
        } else if (valor instanceof Collection) {
            Collection collection = (Collection) valor;
            return collection.isEmpty();
        } else if (valor instanceof Map) {
            Map map = (Map) valor;
            return map.isEmpty();
        } else if (valor instanceof Object[]) {
            Object[] array = (Object[]) valor;
            for (Object object : array) {
                if (!isNullOrEmpty(object)) {
                    return false;
                }
            }
        }
        return false;
    }
}
