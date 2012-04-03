package br.edu.frb.carro.repository.mysql;

import java.io.Serializable;
import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joelamalio
 */
public class MySqlRepository implements Serializable {

    private final Logger LOG = LoggerFactory.getLogger(MySqlRepository.class);
    private final String url = "jdbc:mysql://localhost:3306/carro";
    private final String username = "root";
    private final String password = "";
    private final String driverName = "com.mysql.jdbc.Driver";
    private Connection connection;
    private Statement statement;
    private static String sql;

    public ResultSet executeQuery(final String query) {
        sql = query;
        ResultSet resultSet = null;
        this.carregarDriver();
        try {
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
            this.statement = this.connection.createStatement();
            resultSet = this.statement.executeQuery(query);
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
        }
        return resultSet;
    }

    public boolean executeUpdate(final String query) {
        sql = query;
        int retorno = 0;
        this.carregarDriver();
        try {
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
            this.statement = this.connection.createStatement();
            retorno = this.statement.executeUpdate(query);
            this.connection.commit();
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
            try {
                this.connection.rollback();
            } catch (SQLException ex1) {
                LOG.error("SQLException - rollback connection", ex);
            }
        }
        return retorno > 0;
    }



    public void close() {
        try {
            this.statement.close();
        } catch (SQLException ex) {
            LOG.error("SQLException - close statement", ex);
        } finally {
            try {
                this.connection.close();
            } catch (SQLException ex) {
                LOG.error("SQLException - close connection", ex);
            }

        }
    }

    private void carregarDriver() {
        try {
            try {
                Class.forName(this.driverName).newInstance();
            } catch (InstantiationException ex) {
                LOG.error("InstantiationException", ex);
            } catch (IllegalAccessException ex) {
                LOG.error("IllegalAccessException", ex);
            }
        } catch (ClassNotFoundException ex) {
            LOG.error("ClassNotFoundException", ex);
        }
    }

    public static String obterUltimaConsulta() {
        return sql;
    }
}
