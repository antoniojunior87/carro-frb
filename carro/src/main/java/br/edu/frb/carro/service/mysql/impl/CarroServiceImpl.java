package br.edu.frb.carro.service.mysql.impl;

import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.service.mysql.CarroService;
import br.edu.frb.carro.repository.mysql.GenericRepository;
import br.edu.frb.carro.util.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joelamalio
 */
public class CarroServiceImpl extends GenericRepository implements CarroService {

    private final Logger LOG = LoggerFactory.getLogger(CarroServiceImpl.class);

    @Override
    public List<Carro> obterPorFiltro(Carro carro) {
        List<Carro> carros = new ArrayList<Carro>();
        Carro carroTemp;
        ResultSet resultSet;
        StringBuilder query = new StringBuilder();
        query.append("  SELECT ");
        query.append("      carr_chassi, ");
        query.append("      carr_modelo, ");
        query.append("      carr_ano ");
        query.append("  FROM ");
        query.append("      carro ");
        query.append("  WHERE 1 = 1 ");
        if (carro != null) {
            if (carro.getChassi() != null) {
                query.append("  AND carr_chassi = ");
                query.append(carro.getChassi());
            }
            if (!Util.isNullOrEmpty(carro.getModeloFormatado())) {
                query.append("  AND UPPER(carr_modelo) LIKE ");
                query.append("'%");
                query.append(carro.getModeloFormatado());
                query.append("%'");
            }
            if (carro.getAno() != null) {
                query.append("  AND carr_ano = ");
                query.append(carro.getAno());
            }
        }
        query.append("  ORDER BY ");
        query.append("      carr_ano ASC, ");
        query.append("      carr_modelo ASC ");

        resultSet = super.executeQuery(query.toString());

        try {
            while (resultSet.next()) {
                carroTemp = Carro.Builder.get().comChassi(resultSet.getLong("carr_chassi")).comModelo(resultSet.getString("carr_modelo")).comAno(resultSet.getInt("carr_ano")).criar();
                carros.add(carroTemp);
            }
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
        } finally {
            super.close();
        }

        return carros;
    }

    @Override
    public Carro obterPorChassi(Long chassi) {
        Carro carroTemp = null;
        ResultSet resultSet;
        StringBuilder query = new StringBuilder();
        query.append("  SELECT ");
        query.append("      carr_chassi, ");
        query.append("      carr_modelo, ");
        query.append("      carr_ano ");
        query.append("  FROM ");
        query.append("      carro ");
        query.append("  WHERE 1 = 1 ");
        if (chassi != null) {
            query.append("  AND carr_chassi = ");
            query.append(chassi);
        }
        resultSet = super.executeQuery(query.toString());

        try {
            while (resultSet.next()) {
                carroTemp = Carro.Builder.get().comChassi(resultSet.getLong("carr_chassi")).comModelo(resultSet.getString("carr_modelo")).comAno(resultSet.getInt("carr_ano")).criar();
            }
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
        } finally {
            super.close();
        }
        return carroTemp;
    }

    @Override
    public boolean inserir(Carro carro) {
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO carro.carro ( ");
        query.append("      carr_chassi, ");
        query.append("      carr_modelo, ");
        query.append("      carr_ano ");
        query.append("  ) VALUES (");
        query.append(carro.getChassi());
        query.append(", ");
        query.append("'");
        query.append(carro.getModeloFormatado());
        query.append("'");
        query.append(", ");
        query.append(carro.getAno());
        query.append(") ");

        return super.executeUpdate(query.toString());
    }

    @Override
    public boolean alterar(Carro carro) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE carro.carro ");
        query.append("  SET ");
        query.append("      carr_modelo = ");
        query.append("'");
        query.append(carro.getModeloFormatado());
        query.append("', ");
        query.append("      carr_ano = ");
        query.append(carro.getAno());
        query.append("  WHERE 1 = 1 ");
        query.append("  AND carr_chassi = ");
        query.append(carro.getChassi());

        return super.executeUpdate(query.toString());
    }

    @Override
    public boolean excluir(Long chassi) {
        StringBuilder query = new StringBuilder();
        query.append("  DELETE FROM ");
        query.append("      carro.carro ");
        query.append("  WHERE 1 = 1 ");
        if (chassi != null) {
            query.append("  AND carr_chassi = ");
            query.append(chassi);
        }

        return super.executeUpdate(query.toString());
    }
}
