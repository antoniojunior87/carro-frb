package br.edu.frb.carro.util;

import br.edu.frb.carro.util.Util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author joelamalio
 */
public class UtilTest {

    @Test
    public void testarSeAStringValorEhNulaEORetornoEhTrue() {
        String valor = null;
        boolean retorno = Util.isNullOrEmpty(valor);
        Assert.assertTrue(retorno);
    }

    @Test
    public void testarSeAStringValorEhVaziaEORetornoEhTrue() {
        String valor = "";
        boolean retorno = Util.isNullOrEmpty(valor);
        Assert.assertTrue(retorno);
    }

    @Test
    public void testarSeAStringValorNaoEhVaziaEORetornoEhFalse() {
        String valor = "@joelamalio";
        boolean retorno = Util.isNullOrEmpty(valor);
        Assert.assertFalse(retorno);
    }

    @Test
    public void testarSeUmaCollectionEhNulaEORetornoEhTrue() {
        Collection collection = null;
        boolean retorno = Util.isNullOrEmpty(collection);
        Assert.assertTrue(retorno);
    }

    @Test
    public void testarSeUmMapEhNuloEORetornoEhTrue() {
        Map map = null;
        boolean retorno = Util.isNullOrEmpty(map);
        Assert.assertTrue(retorno);
    }

    @Test
    public void testarSeUmArrayDeObjectEhNuloEORetornoEhTrue() {
        Object[] array = null;
        boolean retorno = Util.isNullOrEmpty(array);
        Assert.assertTrue(retorno);
    }

    @Test
    public void testarSeUmaCollectionInstanciadaEstaVaziaEORetornoEhTrue() {
        Collection collection = new ArrayList();
        boolean retorno = Util.isNullOrEmpty(collection);
        Assert.assertTrue(retorno);
    }

    @Test
    public void testarSeUmaCollectionCom2ObjetosEstaVaziaEORetornoEhFalse() {
        Collection collection = new ArrayList();
        collection.add("Objeto 1");
        collection.add("Objeto 2");
        boolean retorno = Util.isNullOrEmpty(collection);
        Assert.assertFalse(retorno);
    }

    @Test
    public void testarSeUmMapInstanciadoEstaVazioEORetornoEhTrue() {
        Map map = new HashMap();
        boolean retorno = Util.isNullOrEmpty(map);
        Assert.assertTrue(retorno);
    }

    @Test
    public void testarSeUmMapCom2ObjetosEstaVazioEORetornoEhFalse() {
        Map map = new HashMap();
        String objeto1 = "Objeto 1";
        String objeto2 = "Objeto 2";
        map.put(objeto1.hashCode(), objeto1);
        map.put(objeto2.hashCode(), objeto2);
        boolean retorno = Util.isNullOrEmpty(map);
        Assert.assertFalse(retorno);
    }
}
