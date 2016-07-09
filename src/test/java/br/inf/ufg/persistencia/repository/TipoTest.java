package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.RepositoryTestSuite;
import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.mongodb.DuplicateKeyException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public class TipoTest {

  private static ResolucaoRepository resolucaoRepository;

  /**
   * Abre a conexão com o banco.
   */
  @BeforeClass
  public static void initDatabaseConnection(){
    resolucaoRepository = new ResolucaoRepositoryMongoImpl(RepositoryTestSuite.getMongoDatabase());
  }

  @Test
  public void CRDTipoValido() throws Exception {
    String identificadorTipo = UUID.randomUUID().toString();
    Tipo tipo = criaTipo(identificadorTipo, "tipo", "");
    resolucaoRepository.persisteTipo(tipo);
    Tipo tipoSalvo = resolucaoRepository.tipoPeloCodigo(identificadorTipo);
    Assert.assertNotNull("Tipo não foi encontrada com sucesso pelo identificador.", tipoSalvo);
    Assert.assertEquals("O tipo não está sendo salva.", tipo, tipoSalvo);
    resolucaoRepository.removeTipo(identificadorTipo);
    Tipo tipoRemovido = resolucaoRepository.tipoPeloCodigo(identificadorTipo);
    Assert.assertNull("Tipo não foi removido com sucesso.", tipoRemovido);
  }

  //TODO: take mongo exception out
  @Test(expected = DuplicateKeyException.class)
  public void tipoIdentificadorRepetido() throws Exception {
    String identificadorTipo = UUID.randomUUID().toString();
    Tipo tipo = criaTipo(identificadorTipo, "tipo", "");
    resolucaoRepository.persisteTipo(tipo);
    Tipo tipoSalvo = resolucaoRepository.tipoPeloCodigo(identificadorTipo);
    Assert.assertNotNull("Tipo não foi encontrada com sucesso pelo identificador.", tipoSalvo);
    Assert.assertEquals("O tipo não está sendo salva.", tipo, tipoSalvo);
    Tipo outroTipo = criaTipo(identificadorTipo, "tipo", "");
    resolucaoRepository.persisteTipo(outroTipo);
  }

  @Test
  public void buscaPorNomeTipos(){
    List<Tipo> tiposAProcurar = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String identificadorTipo = String.valueOf(i);
      Tipo tipo = criaTipo(identificadorTipo, "tipo", "");
      resolucaoRepository.persisteTipo(tipo);
      tiposAProcurar.add(tipo);
    }
    Tipo tipoEstranho1 = criaTipo(UUID.randomUUID().toString(), "strange", "");
    resolucaoRepository.persisteTipo(tipoEstranho1);
    Tipo tipoEstranho2 = criaTipo(UUID.randomUUID().toString(), "weird", "");
    resolucaoRepository.persisteTipo(tipoEstranho2);
    Tipo tipoValidoEstranho = criaTipo(UUID.randomUUID().toString(), "weird", "tipo");
    resolucaoRepository.persisteTipo(tipoValidoEstranho);
    tiposAProcurar.add(tipoValidoEstranho);
    List<Tipo> tipos = resolucaoRepository.tiposPeloNome("tipo");
    Assert.assertNotNull("Tipos não foram encontrados com sucesso.", tipos);
    for (Tipo tipo: tipos) {
        Assert.assertNotNull("Tipo null.", tipo);
    }
    Assert.assertEquals("Coleção de tipos não é igual.", tiposAProcurar, tipos);
  }

  private Tipo criaTipo(String id, String namePrepend, String nameAppend){
    Set<Atributo> atributos = new HashSet<>();
    for (int i = 0; i < 5; i++) {
      atributos.add(new Atributo(String.valueOf(i), "um atributo", 1));
    }
    Tipo tipo = new Tipo(id, namePrepend + "-" + id + "-" + nameAppend, "É um tipo.", atributos);
    return tipo;
  }

}
