package br.edu.frb.carro.service.mysql.impl;

import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.enums.Sexo;
import br.edu.frb.carro.exception.ListaException;
import br.edu.frb.carro.service.DonoService;
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
public class DonoMySqlServiceImpl extends MySqlServiceAb implements DonoService {

    private final Logger LOG = LoggerFactory.getLogger(DonoMySqlServiceImpl.class);

    @Override
    public List<Dono> obterPorFiltro(Dono dono) {
        List<Dono> donos = new ArrayList<Dono>();
        Dono donoTemp;
        ResultSet resultSet;
        StringBuilder query = new StringBuilder();
        query.append("  SELECT ");
        query.append("      dono_cpf, ");
        query.append("      dono_nome, ");
        query.append("      dono_sexo ");
        query.append("  FROM ");
        query.append("      carro.dono ");
        query.append("  WHERE 1 = 1 ");
        if (dono != null) {
            if (dono.getCpf() != null) {
                query.append("  AND dono_cpf = ");
                query.append(dono.getCpf());
            }
            if (!Util.isNullOrEmpty(dono.getNomeFormatado())) {
                query.append("  AND UPPER(dono_nome) LIKE ");
                query.append("'%");
                query.append(dono.getNomeFormatado());
                query.append("%'");
            }
            if (dono.getSexo() != null) {
                query.append("  AND dono_sexo = ");
                query.append("'");
                query.append(dono.getSexo());
                query.append("'");
            }
        }
        query.append("  ORDER BY ");
        query.append("      dono_nome ASC ");

        resultSet = super.getMySqlRepository().executeQuery(query.toString());

        try {
            while (resultSet.next()) {
                donoTemp = Dono.Builder.get().comCpf(resultSet.getLong("dono_cpf")).comNome(resultSet.getString("dono_nome")).com(Sexo.from(resultSet.getString("dono_sexo"))).criar();
                donos.add(donoTemp);
            }
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
        } finally {
            super.getMySqlRepository().closeStatement();
        }

        return donos;
    }

    @Override
    public Dono obterPorCpf(Long cpf) {
        Dono donoTemp = null;
        ResultSet resultSet;
        StringBuilder query = new StringBuilder();
        query.append("  SELECT ");
        query.append("      dono_cpf, ");
        query.append("      dono_nome, ");
        query.append("      dono_sexo ");
        query.append("  FROM ");
        query.append("      carro.dono ");
        query.append("  WHERE 1 = 1 ");
        query.append("  AND dono_cpf = ");
        query.append(cpf);
        resultSet = super.getMySqlRepository().executeQuery(query.toString());

        try {
            while (resultSet.next()) {
                donoTemp = Dono.Builder.get().comCpf(resultSet.getLong("dono_cpf")).comNome(resultSet.getString("dono_nome")).com(Sexo.from(resultSet.getString("dono_sexo"))).criar();
            }
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
        } finally {
            super.getMySqlRepository().closeStatement();
        }
        return donoTemp;
    }

    @Override
    public boolean salvar(final Dono dono) throws ListaException {
        boolean retorno;
        this.validar(dono);
        try {
            retorno = this.inserir(dono);
        } catch (Exception ex) {
            retorno = this.alterar(dono);
        }
        return retorno;
    }

    private boolean inserir(final Dono dono) throws Exception {
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO carro.dono ( ");
        query.append("      dono_cpf, ");
        query.append("      dono_nome, ");
        query.append("      dono_sexo ");
        query.append("  ) VALUES (");
        query.append(dono.getCpf());
        query.append(", ");
        query.append(this.getCampo(dono.getNomeFormatado()));
        query.append(", ");
        query.append(this.getCampo(dono.getSexo()));
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

    private boolean alterar(final Dono dono) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE carro.dono ");
        query.append("  SET ");
        query.append("      dono_nome = ");
        query.append(this.getCampo(dono.getNomeFormatado()));
        query.append(", ");
        query.append("      dono_sexo = ");
        query.append(this.getCampo(dono.getSexo()));
        query.append("  WHERE 1 = 1 ");
        query.append("  AND dono_cpf = ");
        query.append(dono.getCpf());
        try {
            return super.getMySqlRepository().executeUpdate(query.toString());
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
            return false;
        }
    }

    @Override
    public boolean excluir(Long cpf) {
        StringBuilder query = new StringBuilder();
        query.append("  DELETE FROM ");
        query.append("      carro.dono ");
        query.append("  WHERE 1 = 1 ");
        if (cpf != null) {
            query.append("  AND dono_cpf = ");
            query.append(cpf);
        }
        try {
            return super.getMySqlRepository().executeUpdate(query.toString());
        } catch (SQLException ex) {
            LOG.error("SQLException", ex);
            return false;
        }
    }

    private void validar(final Dono dono) throws ListaException {
        ListaException listaException = new ListaException();
        if (dono == null) {
            listaException.inserirException("exception_dono_null");
        } else {
            if (Util.isNullOrEmpty(dono.getCpf())) {
                listaException.inserirException("warn_campo_obrigatorio", "label_dono_cpf");
            }
        }
        if (!Util.isNullOrEmpty(listaException.getExceptions())) {
            throw listaException;
        }
    }
}
