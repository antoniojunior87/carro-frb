package br.edu.frb.carro.service;

import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.entity.Dono;
import br.edu.frb.carro.enums.Sexo;
import br.edu.frb.carro.exception.ListaException;
import br.edu.frb.carro.service.mysql.impl.CarroMySqlServiceImpl;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author joelamalio
 */
public class CarroMySqlServiceTest {

    private CarroService carroService = new CarroMySqlServiceImpl();

    @Before
    public void setUp() {
        this.carroService.excluir(null);
        this.carroService.salvar(Carro.Builder.get().comChassi(1L).comModelo("Modelo do Carro 1").comAno(2000).criar());
        this.carroService.salvar(Carro.Builder.get().comChassi(2L).comModelo("Modelo do Carro 2").comAno(2005).criar());
        this.carroService.salvar(Carro.Builder.get().comChassi(3L).comModelo("Modelo do Carro 3").comAno(2010).criar());
    }

    @Test
    public void quandoSalvarUmRegistroNovoCompletamentePreenchidoDeveRetornarTrue() {
        Carro carro = Carro.Builder.get().comChassi(10L).comModelo("Modelo Novo").comAno(2012).criar();
        assertTrue(this.carroService.salvar(carro));
    }
    
    @Test
    public void quandoSalvarUmRegistroNovoSemChassiPreenchidoDeveRetornarUmaListaException() {
        Carro carro = Carro.Builder.get().comModelo("Modelo Novo").comAno(2012).criar();
        try {
            this.carroService.salvar(carro);
            fail();
        } catch(ListaException le) {
            ListaException exceptions = ListaException.Builder.get().com("warn_campo_obrigatorio", "label_carro_chassi").criar();
            assertEquals(1, le.getExceptions().size());
            assertEquals(le.getExceptions().get(0), exceptions.getExceptions().get(0));
        }
    }
    
    @Test
    public void quandoSalvarUmRegistroExistenteCompletamentePreenchidoDeveRetornarTrue() {
        Long chassi = 1L;
        String modelo = "Modelo Alterado";
        Carro carro = this.carroService.obterPorChassi(chassi);
        carro.setModelo(modelo);
        this.carroService.salvar(carro);
        assertEquals(modelo.toUpperCase(), this.carroService.obterPorChassi(chassi).getModeloFormatado());
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
