package br.edu.frb.carro.service;

import br.edu.frb.carro.entity.Item;
import br.edu.frb.carro.service.mysql.impl.ItemMySqlServiceImpl;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author joelamalio
 */
public class ItemMySqlServiceTest {

    private ItemService itemService = new ItemMySqlServiceImpl();

    @Before
    public void setUp() {
        this.itemService.excluir(null);
        this.itemService.salvar(Item.Builder.get().comId(1L).comNome("Nome do Item 1").criar());
        this.itemService.salvar(Item.Builder.get().comId(2L).comNome("Nome do Item 2").criar());
        this.itemService.salvar(Item.Builder.get().comId(3L).comNome("Nome do Item 3").criar());
        this.itemService.salvar(Item.Builder.get().comId(4L).comNome("Nome do Item 4").criar());
        this.itemService.salvar(Item.Builder.get().comId(5L).comNome("Nome do Item 5").criar());
    }

    @Test
    public void quandoInserirComSucessoDeveRetornarTrue() {
        Item item = Item.Builder.get().comId(10L).comNome("Nome Novo").criar();
        assertTrue(this.itemService.salvar(item));
    }

    @Test
    public void quandoObterPorFiltroVazioDeveRetornar5Registros() {
        List<Item> itens = this.itemService.obterPorFiltro(null);
        assertNotNull(itens);
        assertEquals(5, itens.size());
    }

    @Test
    public void quandoObterPorFiltroComNome5DeveRetornar1Registro() {
        Item item = Item.Builder.get().comNome("5").criar();
        List<Item> itens = this.itemService.obterPorFiltro(item);
        assertNotNull(itens);
        assertEquals(1, itens.size());
    }

    @Test
    public void quandoObterPorFiltroComId2DeveRetornar1Registro() {
        Item item = Item.Builder.get().comId(2L).criar();
        List<Item> itens = this.itemService.obterPorFiltro(item);
        assertNotNull(itens);
        assertEquals(1, itens.size());
    }

    @Test
    public void quandoObterPorId1DeveRetornar1Registro() {
        Long id = 1L;
        Item item = this.itemService.obterPorId(id);
        assertNotNull(item);
        assertEquals(item.getId(), id);
    }

    @Test
    public void quandoAlterarDeveRetornarTrue() {
        Long id = 1L;
        String nome = "Nome Alterado";
        Item item = this.itemService.obterPorId(id);
        item.setNome(nome);
        this.itemService.salvar(item);
        assertEquals(nome.toUpperCase(), this.itemService.obterPorId(id).getNomeFormatado());
    }

    @Test
    public void quandoExcluirUmRegistroExistenteDeveRetornarTrue() {
        Long id = 1L;
        assertTrue(this.itemService.excluir(id));
        Item item = this.itemService.obterPorId(id);
        assertNull(item);
    }

    @Test
    public void quandoExcluirUmRegistroInexistenteDeveRetornarFalse() {
        Long id = -1L;
        assertFalse(this.itemService.excluir(id));
        Item item = this.itemService.obterPorId(id);
        assertNull(item);
    }
}
