package br.edu.frb.carro.service;

import br.edu.frb.carro.entity.Item;
import br.edu.frb.carro.service.mongodb.impl.ItemMongoDbServiceImpl;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author joelamalio
 */
public class ItemMongoDbServiceTest {

    private ItemService itemMongoDbService = new ItemMongoDbServiceImpl();

    @Before
    public void setUp() {
        this.itemMongoDbService.excluir(null);
        this.itemMongoDbService.salvar(Item.Builder.get().comId(1L).comNome("Nome Item 1").criar());
        this.itemMongoDbService.salvar(Item.Builder.get().comId(2L).comNome("Nome Item 2").criar());
    }

    @Test
    public void quandoInserirComSucessoDeveRetornarTrue() {
        Item item = Item.Builder.get().comId(3L).comNome("Item Novo").criar();
        assertTrue(this.itemMongoDbService.salvar(item));
    }

    @Test
    public void quandoObterPorFiltroVazioDeveRetornar2Registros() {
        List<Item> items = this.itemMongoDbService.obterPorFiltro(null);
        assertNotNull(items);
        assertEquals(2, items.size());
    }

    @Test
    public void quandoObterPorFiltroComId1DeveRetornar1Registro() {
        Item item = Item.Builder.get().comId(1L).criar();
        List<Item> items = this.itemMongoDbService.obterPorFiltro(item);
        assertNotNull(items);
        assertEquals(1, items.size());
    }

    @Test
    public void quandoObterPorFiltroComNome_Item_1DeveRetornar1Registro() {
        Item item = Item.Builder.get().comNome("Nome Item 1").criar();
        List<Item> items = this.itemMongoDbService.obterPorFiltro(item);
        assertNotNull(items);
        assertEquals(1, items.size());
    }

    @Test
    public void quandoAlterarDeveRetornarTrue() {
        Long id = 1L;
        String nome = "Nome Alterado";
        Item item = this.itemMongoDbService.obterPorId(id);
        item.setNome(nome);
        this.itemMongoDbService.salvar(item);
        assertEquals(nome, this.itemMongoDbService.obterPorId(id).getNome());
    }

    @Test
    public void quandoExcluirUmRegistroExistenteDeveRetornarTrue() {
        Long id = 1L;
        assertTrue(this.itemMongoDbService.excluir(id));
        Item item = this.itemMongoDbService.obterPorId(id);
        assertNull(item);
    }

    @Test
    public void quandoExcluirUmRegistroInexistenteDeveRetornarFalse() {
        Long id = -1L;
        assertTrue(this.itemMongoDbService.excluir(id));
        Item item = this.itemMongoDbService.obterPorId(id);
        assertNull(item);
    }
}
