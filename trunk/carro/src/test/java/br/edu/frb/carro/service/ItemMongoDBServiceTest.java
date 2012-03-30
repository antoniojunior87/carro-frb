package br.edu.frb.carro.service;

import br.edu.frb.carro.entity.Carro;
import br.edu.frb.carro.entity.Item;
import br.edu.frb.carro.service.mongodb.impl.ItemMongoDBServiceImpl;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joelamalio
 */
public class ItemMongoDBServiceTest {

    private ItemService itemMongoDBService = new ItemMongoDBServiceImpl();

    @Before
    public void setUp() {
        //this.itemMongoDBService.excluir(null);
        this.itemMongoDBService.inserir(Item.Builder.get().comId(1L).comNome("Nome Item 1").criar());
        this.itemMongoDBService.inserir(Item.Builder.get().comId(2L).comNome("Nome Item 2").criar());

    }

    @Test
    public void quandoInserirComSucessoDeveRetornarTrue() {
        Item item = Item.Builder.get().comId(3L).comNome("Item Novo").criar();
        assertTrue(this.itemMongoDBService.inserir(item));
    }

    @Test
    public void quandoObterPorFiltroVazioDeveRetornar2Registros() {
        List<Item> items = this.itemMongoDBService.obterPorFiltro(null);
        assertNotNull(items);
        assertEquals(2, items.size());
    }

    @Test
    public void quandoObterPorFiltroComId1DeveRetornar1Registro() {
        Item item = Item.Builder.get().comId(1L).criar();
        List<Item> items = this.itemMongoDBService.obterPorFiltro(item);
        assertNotNull(items);
        assertEquals(1, items.size());
    }

    @Test
    public void quandoObterPorFiltroComNome_Item_1DeveRetornar1Registro() {
        Item item = Item.Builder.get().comNome("Nome Item 1").criar();
        List<Item> items = this.itemMongoDBService.obterPorFiltro(item);
        assertNotNull(items);
        assertEquals(1, items.size());
    }

    @Test
    public void quandoAlterarDeveRetornarTrue() {
        Long id = 1L;
        String nome = "Nome Alterado";
        Item item = this.itemMongoDBService.obterPorId(id);
        item.setNome(nome);
        this.itemMongoDBService.alterar(item);
        assertEquals(nome, this.itemMongoDBService.obterPorId(id).getNome());
    }

    @Test
    public void quandoExcluirUmRegistroExistenteDeveRetornarTrue() {
        Long id = 1L;
        assertTrue(this.itemMongoDBService.excluir(id));
        Item item = this.itemMongoDBService.obterPorId(id);
        assertNull(item);
    }

    @Test
    public void quandoExcluirUmRegistroInexistenteDeveRetornarFalse() {
        Long id = -1L;
        assertTrue(this.itemMongoDBService.excluir(id));
        Item item = this.itemMongoDBService.obterPorId(id);
        assertNull(item);
    }
}
