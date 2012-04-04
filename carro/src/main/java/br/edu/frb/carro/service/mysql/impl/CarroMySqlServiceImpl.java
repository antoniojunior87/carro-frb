package br.edu.frb.carro.service.mysql.impl;

import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.enums.Sexo;
import br.edu.frb.carro.exception.ListaException;
import br.edu.frb.carro.service.CarroService;
import br.edu.frb.carro.service.mysql.ab.MySqlServiceAb;
import br.edu.frb.carro.util.Util;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
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
public class CarroMySqlServiceImpl extends MySqlServiceAb implements CarroService {

    private final Logger LOG = LoggerFactory.getLogger(CarroMySqlServiceImpl.class);

    @Override
    public List<Carro> obterPorFiltro(Carro carro) {
        List<Carro> carros = new ArrayList<Carro>();
        Carro carroTemp;
        Dono donoTemp;
        ResultSet resultSet;
        StringBuilder query = new StringBuilder();
        query.append("  SELECT ");
        query.append("      carr.carr_chassi, ");
        query.append("      carr.carr_modelo, ");
        query.append("      carr.carr_ano, ");
        query.append("      dono.dono_cpf, ");
        query.append("      dono.dono_nome, ");
        query.append("      dono.dono_sexo ");
        query.append("  FROM ");
        query.append("      carro.carro carr ");
        query.append("  LEFT JOIN ");
        query.append("      carro.dono dono ");
        query.append("  ON  ");
        query.append("      dono.dono_cpf = carr.dono_cpf ");
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

        resultSet = super.getMySqlRepository().executeQuery(query.toString());

        try {
            while (resultSet.next()) {
                donoTemp = Dono.Builder.get().comCpf(resultSet.getLong("dono_cpf")).comNome(resultSet.getString("dono_nome")).com(Sexo.from("dono_sexo")).criar();
                carroTemp = Carro.Builder.get().comChassi(resultSet.getLong("carr_chassi")).comModelo(resultSet.getString("carr_modelo")).comAno(resultSet.getInt("carr_ano")).com(donoTemp).criar();
                carros.add(carroTemp);
            }
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
        } finally {
            super.getMySqlRepository().closeStatement();
        }

        return carros;
    }

    @Override
    public Carro obterPorChassi(Long chassi) {
        Carro carroTemp = null;
        Dono donoTemp;
        ResultSet resultSet;
        StringBuilder query = new StringBuilder();
        query.append("  SELECT ");
        query.append("      carr.carr_chassi, ");
        query.append("      carr.carr_modelo, ");
        query.append("      carr.carr_ano, ");
        query.append("      dono.dono_cpf, ");
        query.append("      dono.dono_nome, ");
        query.append("      dono.dono_sexo ");
        query.append("  FROM ");
        query.append("      carro.carro carr ");
        query.append("  LEFT JOIN ");
        query.append("      carro.dono dono ");
        query.append("  ON  ");
        query.append("      dono.dono_cpf = carr.dono_cpf ");
        query.append("  WHERE 1 = 1 ");
        query.append("  AND carr_chassi = ");
        query.append(chassi);
        resultSet = super.getMySqlRepository().executeQuery(query.toString());

        try {
            while (resultSet.next()) {
                donoTemp = Dono.Builder.get().comCpf((Long) resultSet.getObject("dono_cpf")).comNome(resultSet.getString("dono_nome")).com(Sexo.from("dono_sexo")).criar();
                carroTemp = Carro.Builder.get().comChassi(resultSet.getLong("carr_chassi")).comModelo(resultSet.getString("carr_modelo")).comAno(resultSet.getInt("carr_ano")).com(donoTemp).criar();
            }
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
        } finally {
            super.getMySqlRepository().closeStatement();
        }
        return carroTemp;
    }

    @Override
    public boolean salvar(final Carro carro) throws ListaException {
        boolean retorno;
        this.validar(carro);
        try {
            retorno = this.inserir(carro);
        } catch (Exception ex) {
            retorno = this.alterar(carro);
        }
        return retorno;
    }

    private boolean inserir(final Carro carro) throws Exception {
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO carro.carro ( ");
        query.append("      carr_chassi, ");
        query.append("      carr_modelo, ");
        query.append("      carr_ano, ");
        query.append("      dono_cpf ");
        query.append("  ) VALUES (");
        query.append(carro.getChassi());
        query.append(", ");
        query.append(this.getCampo(carro.getModeloFormatado()));
        query.append(", ");
        query.append(this.getCampo(carro.getAno()));
        query.append(", ");
        query.append(this.getCampo(carro.getDono() != null && carro.getDono().getCpf()!= null ? carro.getDono().getCpf() : null));
        query.append(") ");
        try {
            return super.getMySqlRepository().executeUpdate(query.toString());
        } catch (SQLException ex) {
            if (ex instanceof MySQLIntegrityConstraintViolationException) {
                LOG.error("MySQLIntegrityConstraintViolationException", ex);
                throw ex;
            } else {
                LOG.error("SQLException", ex);
            }
            return false;
        }
    }

    private boolean alterar(final Carro carro) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE carro.carro ");
        query.append("  SET ");
        query.append("      carr_modelo = ");
        query.append(this.getCampo(carro.getModeloFormatado()));
        query.append(", ");
        query.append("      carr_ano = ");
        query.append(this.getCampo(carro.getAno()));
        query.append(", ");
        query.append("      dono_cpf = ");
        query.append(this.getCampo(carro.getDono() != null ? carro.getDono().getCpf() : null));
        query.append("  WHERE 1 = 1 ");
        query.append("  AND carr_chassi = ");
        query.append(carro.getChassi());
        try {
            return super.getMySqlRepository().executeUpdate(query.toString());
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
            return false;
        }
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
        try {
            return super.getMySqlRepository().executeUpdate(query.toString());
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
            return false;
        }
    }

    private void validar(final Carro carro) throws ListaException {
        ListaException listaException = new ListaException();
        if (carro == null) {
            listaException.inserirException("exception_carro_null");
        } else {
            if (Util.isNullOrEmpty(carro.getChassi())) {
                listaException.inserirException("warn_campo_obrigatorio", "label_carro_chassi");
            }
        }
        if (!Util.isNullOrEmpty(listaException.getExceptions())) {
            throw listaException;
        }
    }
}
