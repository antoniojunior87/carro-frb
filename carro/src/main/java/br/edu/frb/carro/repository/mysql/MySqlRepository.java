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
    private static Connection connection;
    private Statement statement;
    private static String sql;

    private Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(this.url, this.username, this.password);
            }
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
        }
        return connection;
    }

    public ResultSet executeQuery(final String query) {
        sql = query;
        ResultSet resultSet = null;
        this.carregarDriver();
        try {
            this.statement = this.getConnection().createStatement();
            resultSet = this.statement.executeQuery(query);
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
        }
        return resultSet;
    }

    public boolean executeUpdate(final String query) throws SQLException {
        sql = query;
        int retorno;
        this.carregarDriver();
        this.statement = this.getConnection().createStatement();
        retorno = this.statement.executeUpdate(query);
        this.statement.close();
        return retorno > 0;
    }

    public void closeConnection() throws SQLException {
        try {
            connection.close();
        } catch (SQLException ex) {
            LOG.error("SQLException - close connection", ex);
        }
    }

    public void closeStatement() {
        try {
            this.statement.close();
        } catch (SQLException ex) {
            LOG.error("SQLException - close statement", ex);
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
