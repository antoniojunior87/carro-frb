package br.edu.frb.carro.service;

import br.edu.frb.carro.service.mysql.CarroService;
import br.edu.frb.carro.service.mysql.impl.CarroServiceImpl;
import br.edu.frb.carro.entity.Carro;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author joelamalio
 */
public class CarroServiceTest {

    private CarroService carroService = new CarroServiceImpl();

    @Before
    public void setUp() {
        this.carroService.excluir(null);
        this.carroService.inserir(Carro.Builder.get().comChassi(1L).comModelo("Modelo do Carro 1").comAno(2000).criar());
        this.carroService.inserir(Carro.Builder.get().comChassi(2L).comModelo("Modelo do Carro 2").comAno(2005).criar());
        this.carroService.inserir(Carro.Builder.get().comChassi(3L).comModelo("Modelo do Carro 3").comAno(2010).criar());
        
    }

    @Test
    public void quandoInserirComSucessoDeveRetornarTrue() {
        Carro carro = Carro.Builder.get().comChassi(10L).comModelo("Modelo Novo").comAno(2012).criar();
        assertTrue(this.carroService.inserir(carro));
    }
    
    @Test
    public void quandoObterPorFiltroVazioDeveRetornar3Registros() {
        List<Carro> carros = this.carroService.obterPorFiltro(null);
        assertNotNull(carros);
        assertEquals(3, carros.size());
    }
    
    @Test
    public void quandoObterPorFiltroComAno2005DeveRetornar1Registro() {
        Carro carro = Carro.Builder.get().comAno(2005).criar();
        List<Carro> carros = this.carroService.obterPorFiltro(carro);
        assertNotNull(carros);
        assertEquals(1, carros.size());
    }
    
    @Test
    public void quandoObterPorFiltroComChassi2DeveRetornar1Registro() {
        Carro carro = Carro.Builder.get().comChassi(2L).criar();
        List<Carro> carros = this.carroService.obterPorFiltro(carro);
        assertNotNull(carros);
        assertEquals(1, carros.size());
    }
    
    @Test
    public void quandoObterPorFiltroComModelo1DeveRetornar1Registro() {
        Carro carro = Carro.Builder.get().comModelo("1").criar();
        List<Carro> carros = this.carroService.obterPorFiltro(carro);
        assertNotNull(carros);
        assertEquals(1, carros.size());
    }
    
    @Test
    public void quandoObterPorChassi1DeveRetornar1Registro() {
        Long chassi = 1L;
        Carro carro = this.carroService.obterPorChassi(chassi);
        assertNotNull(carro);
        assertEquals(carro.getChassi(), chassi);
    }
    
    @Test
    public void quandoAlterarDeveRetornarTrue() {
        Long chassi = 1L;
        String modelo = "Modelo de Alterado";
        Carro carro = this.carroService.obterPorChassi(chassi);
        carro.setModelo(modelo);
        this.carroService.alterar(carro);
        assertEquals(modelo.toUpperCase(), this.carroService.obterPorChassi(chassi).getModelo());
    }
    
    @Test
    public void quandoExcluirUmRegistroExistenteDeveRetornarTrue() {
        Long chassi = 1L;
        assertTrue(this.carroService.excluir(chassi));
        Carro carro = this.carroService.obterPorChassi(chassi);
        assertNull(carro);
    }
    
    @Test
    public void quandoExcluirUmRegistroInexistenteDeveRetornarFalse() {
        Long chassi = -1L;
        assertFalse(this.carroService.excluir(chassi));
        Carro carro = this.carroService.obterPorChassi(chassi);
        assertNull(carro);
    }
}
