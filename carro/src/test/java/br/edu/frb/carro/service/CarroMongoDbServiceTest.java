package br.edu.frb.carro.service;

import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.enums.Sexo;
import br.edu.frb.carro.service.mongodb.impl.CarroMongoDbServiceImpl;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author joelamalio
 */
public class CarroMongoDbServiceTest {
    
    private CarroService carroMongoDbService = new CarroMongoDbServiceImpl();

    @Before
    public void setUp() {
        this.carroMongoDbService.excluir(null);
        Dono dono1 = Dono.Builder.get().comCpf(1L).comNome("Nome Dono 1").com(Sexo.FEMININO).criar();
        Dono dono2 = Dono.Builder.get().comCpf(2L).comNome("Nome Dono 2").com(Sexo.MASCULINO).criar();
        this.carroMongoDbService.salvar(Carro.Builder.get().comChassi(1L).comModelo("Modelo Carro 1").comAno(2000).criar());
        this.carroMongoDbService.salvar(Carro.Builder.get().comChassi(2L).comModelo("Modelo Carro 2").comAno(2001).com(dono2).criar());
        this.carroMongoDbService.salvar(Carro.Builder.get().comChassi(3L).comModelo("Modelo Carro 3").comAno(2002).com(dono1).criar());
        this.carroMongoDbService.salvar(Carro.Builder.get().comChassi(4L).comModelo("Modelo Carro 4").comAno(2003).com(dono2).criar());
    }

    @Test
    public void quandoInserirComSucessoDeveRetornarTrue() {
        Carro carro = Carro.Builder.get().comChassi(10L).comModelo("Modelo Carro 10").comAno(1980).criar();
        assertTrue(this.carroMongoDbService.salvar(carro));
    }

    @Test
    public void quandoObterPorFiltroVazioDeveRetornar4Registros() {
        List<Carro> carros = this.carroMongoDbService.obterPorFiltro(null);
        assertNotNull(carros);
        assertEquals(4, carros.size());
    }

    @Test
    public void quandoObterPorFiltroComChassi1DeveRetornar1Registro() {
        Carro carro = Carro.Builder.get().comChassi(1L).criar();
        List<Carro> carros = this.carroMongoDbService.obterPorFiltro(carro);
        assertNotNull(carros);
        assertEquals(1, carros.size());
    }

    @Test
    public void quandoObterPorFiltroComModelo_Carro_1DeveRetornar1Registro() {
        Carro carro = Carro.Builder.get().comModelo("Modelo Carro 1").criar();
        List<Carro> carros = this.carroMongoDbService.obterPorFiltro(carro);
        assertNotNull(carros);
        assertEquals(1, carros.size());
    }

    @Test
    public void quandoAlterarDeveRetornarTrue() {
        Long chassi = 1L;
        String modelo = "Modelo Alterado";
        Carro carro = this.carroMongoDbService.obterPorChassi(chassi);
        carro.setModelo(modelo);
        this.carroMongoDbService.salvar(carro);
        assertEquals(modelo, this.carroMongoDbService.obterPorChassi(chassi).getModelo());
    }

    @Test
    public void quandoExcluirUmRegistroExistenteDeveRetornarTrue() {
        Long chassi = 3L;
        assertTrue(this.carroMongoDbService.excluir(chassi));
        Carro carro = this.carroMongoDbService.obterPorChassi(chassi);
        assertNull(carro);
    }

    @Test
    public void quandoExcluirUmRegistroInexistenteDeveRetornarFalse() {
        Long chassi = -1L;
        assertTrue(this.carroMongoDbService.excluir(chassi));
        Carro carro = this.carroMongoDbService.obterPorChassi(chassi);
        assertNull(carro);
    }
}
