package br.edu.frb.carro.service;

import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.enums.Sexo;
import br.edu.frb.carro.service.mongodb.impl.DonoMongoDbServiceImpl;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author joelamalio
 */
public class DonoMongoDbServiceTest {
 
    private DonoService donoMongoDbService = new DonoMongoDbServiceImpl();

    @Before
    public void setUp() {
        //this.donoMongoDbService.excluir(null);
        this.donoMongoDbService.inserir(Dono.Builder.get().comCpf(1L).comNome("Nome Dono 1").com(Sexo.FEMININO).criar());
        this.donoMongoDbService.inserir(Dono.Builder.get().comCpf(2L).comNome("Nome Dono 2").com(Sexo.MASCULINO).criar());
    }

    @Test
    public void quandoInserirComSucessoDeveRetornarTrue() {
        Dono dono = Dono.Builder.get().comCpf(3L).comNome("Dono Novo").com(Sexo.MASCULINO).criar();
        assertTrue(this.donoMongoDbService.inserir(dono));
    }

    @Test
    public void quandoObterPorFiltroVazioDeveRetornar2Registros() {
        List<Dono> donos = this.donoMongoDbService.obterPorFiltro(null);
        assertNotNull(donos);
        assertEquals(2, donos.size());
    }

    @Test
    public void quandoObterPorFiltroComCpf1DeveRetornar1Registro() {
        Dono dono = Dono.Builder.get().comCpf(1L).criar();
        List<Dono> donos = this.donoMongoDbService.obterPorFiltro(dono);
        assertNotNull(donos);
        assertEquals(1, donos.size());
    }

    @Test
    public void quandoObterPorFiltroComNome_Dono_1DeveRetornar1Registro() {
        Dono dono = Dono.Builder.get().comNome("Nome Dono 1").criar();
        List<Dono> donos = this.donoMongoDbService.obterPorFiltro(dono);
        assertNotNull(donos);
        assertEquals(1, donos.size());
    }
    
    @Test
    public void quandoObterPorFiltroComSexoMasculinoDeveRetornar1Registro() {
        Dono dono = Dono.Builder.get().com(Sexo.MASCULINO).criar();
        List<Dono> donos = this.donoMongoDbService.obterPorFiltro(dono);
        assertNotNull(donos);
        assertEquals(1, donos.size());
    }
    
    @Test
    public void quandoObterPorFiltroComSexoFemininoDeveRetornar1Registro() {
        Dono dono = Dono.Builder.get().com(Sexo.FEMININO).criar();
        List<Dono> donos = this.donoMongoDbService.obterPorFiltro(dono);
        assertNotNull(donos);
        assertEquals(1, donos.size());
    }

    @Test
    public void quandoAlterarDeveRetornarTrue() {
        Long cpf = 1L;
        String nome = "Nome Alterado";
        Dono dono = this.donoMongoDbService.obterPorCpf(cpf);
        dono.setNome(nome);
        this.donoMongoDbService.alterar(dono);
        assertEquals(nome, this.donoMongoDbService.obterPorCpf(cpf).getNome());
    }

    @Test
    public void quandoExcluirUmRegistroExistenteDeveRetornarTrue() {
        Long cpf = 1L;
        assertTrue(this.donoMongoDbService.excluir(cpf));
        Dono dono = this.donoMongoDbService.obterPorCpf(cpf);
        assertNull(dono);
    }

    @Test
    public void quandoExcluirUmRegistroInexistenteDeveRetornarFalse() {
        Long cpf = -1L;
        assertTrue(this.donoMongoDbService.excluir(cpf));
        Dono dono = this.donoMongoDbService.obterPorCpf(cpf);
        assertNull(dono);
    }
}
