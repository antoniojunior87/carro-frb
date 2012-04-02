package br.edu.frb.carro.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author joelamalio
 */
public class MessageUtil {

    private static final Locale locale;
    private static ResourceBundle bundle;

    static {
        locale = new Locale("pt", "BR");
        bundle = ResourceBundle.getBundle("messages", locale);
    }

    public static String getMessage(final String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException mre) {
            return key;
        }
    }

    public static String getMessage(String key, String... params) {
        try {
            key = getMessage(key);
            for (int i = 0; i < params.length; i++) {
                params[i] = (String) getMessage(params[i]);
            }
            return MessageFormat.format(key, params);
        } catch (MissingResourceException mre) {
            return key;
        }
    }
}
