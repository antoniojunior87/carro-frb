package br.edu.frb.carro.service;

import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.enums.Sexo;
import br.edu.frb.carro.service.mysql.impl.DonoMySqlServiceImpl;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author joelamalio
 */
public class DonoMySqlServiceTest {
    
    private DonoService donoService = new DonoMySqlServiceImpl();

    @Before
    public void setUp() {
        this.donoService.excluir(null);
        this.donoService.salvar(Dono.Builder.get().comCpf(1L).comNome("Nome do Dono 1").com(Sexo.FEMININO).criar());
        this.donoService.salvar(Dono.Builder.get().comCpf(2L).comNome("Nome do Dono 2").com(Sexo.FEMININO).criar());
        this.donoService.salvar(Dono.Builder.get().comCpf(3L).comNome("Nome do Dono 3").com(Sexo.MASCULINO).criar());
        this.donoService.salvar(Dono.Builder.get().comCpf(4L).comNome("Nome do Dono 4").com(Sexo.MASCULINO).criar());
        this.donoService.salvar(Dono.Builder.get().comCpf(5L).comNome("Nome do Dono 5").com(Sexo.FEMININO).criar());
    }

    @Test
    public void quandoInserirComSucessoDeveRetornarTrue() {
        Dono dono = Dono.Builder.get().comCpf(10L).comNome("Nome Novo").com(Sexo.MASCULINO).criar();
        assertTrue(this.donoService.salvar(dono));
    }

    @Test
    public void quandoObterPorFiltroVazioDeveRetornar5Registros() {
        List<Dono> donos = this.donoService.obterPorFiltro(null);
        assertNotNull(donos);
        assertEquals(5, donos.size());
    }
    
    @Test
    public void quandoObterPorFiltroComNome5DeveRetornar1Registro() {
        Dono dono = Dono.Builder.get().comNome("5").criar();
        List<Dono> donos = this.donoService.obterPorFiltro(dono);
        assertNotNull(donos);
        assertEquals(1, donos.size());
    }
    
    @Test
    public void quandoObterPorFiltroComSexoMasculinoDeveRetornar2Registros() {
        Dono dono = Dono.Builder.get().com(Sexo.MASCULINO).criar();
        List<Dono> donos = this.donoService.obterPorFiltro(dono);
        assertNotNull(donos);
        assertEquals(2, donos.size());
    }

    @Test
    public void quandoObterPorFiltroComSexoFeminino5DeveRetornar3Registros() {
        Dono dono = Dono.Builder.get().com(Sexo.FEMININO).criar();
        List<Dono> donos = this.donoService.obterPorFiltro(dono);
        assertNotNull(donos);
        assertEquals(3, donos.size());
    }

    @Test
    public void quandoObterPorFiltroComCpf2DeveRetornar1Registro() {
        Dono dono = Dono.Builder.get().comCpf(2L).criar();
        List<Dono> donos = this.donoService.obterPorFiltro(dono);
        assertNotNull(donos);
        assertEquals(1, donos.size());
    }

    @Test
    public void quandoObterPorCpf1DeveRetornar1Registro() {
        Long cpf = 1L;
        Dono dono = this.donoService.obterPorCpf(cpf);
        assertNotNull(dono);
        assertEquals(dono.getCpf(), cpf);
    }

    @Test
    public void quandoAlterarDeveRetornarTrue() {
        Long cpf = 1L;
        String nome = "Nome Alterado";
        Dono dono = this.donoService.obterPorCpf(cpf);
        dono.setNome(nome);
        this.donoService.salvar(dono);
        assertEquals(nome.toUpperCase(), this.donoService.obterPorCpf(cpf).getNomeFormatado());
    }

    @Test
    public void quandoExcluirUmRegistroExistenteDeveRetornarTrue() {
        Long cpf = 1L;
        assertTrue(this.donoService.excluir(cpf));
        Dono dono = this.donoService.obterPorCpf(cpf);
        assertNull(dono);
    }

    @Test
    public void quandoExcluirUmRegistroInexistenteDeveRetornarFalse() {
        Long cpf = -1L;
        assertFalse(this.donoService.excluir(cpf));
        Dono dono = this.donoService.obterPorCpf(cpf);
        assertNull(dono);
    }
    
}
