package br.edu.frb.carro.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author antonio.junior
 */
public class ConfigListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.getProperties().put("org.apache.el.parser.COERCE_TO_ZERO", "false");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
