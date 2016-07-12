package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.RepositoryTestSuite;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static br.inf.ufg.persistencia.repository.UtilsGenerator.*;

public class TipoTest {

  private static ResolucaoRepository resolucaoRepository;

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  /**
   * Abre a conexão com o banco.
   */
  @BeforeClass
  public static void initDatabaseConnection(){
    resolucaoRepository = new ResolucaoRepositoryMongoImpl(RepositoryTestSuite.getMongoDatabase());
  }

  @Test
  public void createReadDeleteTipo() throws Exception {
    String identificadorTipo = UUID.randomUUID().toString();
    Tipo tipo = criaTipo(identificadorTipo, "tipo", "");
    resolucaoRepository.persisteTipo(tipo);
    Tipo tipoSalvo = resolucaoRepository.tipoPeloCodigo(identificadorTipo);
    Assert.assertNotNull("Tipo não foi encontrado com sucesso pelo identificador.", tipoSalvo);
    Assert.assertEquals("O tipo não está sendo salvo.", tipo, tipoSalvo);
    resolucaoRepository.removeTipo(identificadorTipo);
    Tipo tipoRemovido = resolucaoRepository.tipoPeloCodigo(identificadorTipo);
    Assert.assertNull("Tipo não foi removido com sucesso.", tipoRemovido);
  }

  @Test
  public void tipoIdentificadorRepetido() throws Exception {
    String identificadorTipo = UUID.randomUUID().toString();
    Tipo tipo = criaTipo(identificadorTipo, "tipo", "");
    resolucaoRepository.persisteTipo(tipo);
    Tipo tipoSalvo = resolucaoRepository.tipoPeloCodigo(identificadorTipo);
    Assert.assertNotNull("Tipo não foi encontrada com sucesso pelo identificador.", tipoSalvo);
    Assert.assertEquals("O tipo não está sendo salvo.", tipo, tipoSalvo);
    Tipo outroTipoIgual = criaTipo(identificadorTipo, "tipo", "");
    exception.expect(IdentificadorExistente.class);
    resolucaoRepository.persisteTipo(outroTipoIgual);
  }

  @Test
  public void buscaPorNomeTipos(){
    List<Tipo> tiposAProcurar = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String identificadorTipo = String.valueOf(i);
      Tipo tipo = criaTipo(identificadorTipo, "tipoNome", "");
      resolucaoRepository.persisteTipo(tipo);
      tiposAProcurar.add(tipo);
    }
    Tipo tipoEstranho1 = criaTipo(UUID.randomUUID().toString(), "strange", "");
    resolucaoRepository.persisteTipo(tipoEstranho1);
    Tipo tipoEstranho2 = criaTipo(UUID.randomUUID().toString(), "weird", "");
    resolucaoRepository.persisteTipo(tipoEstranho2);
    Tipo tipoValidoEstranho = criaTipo(UUID.randomUUID().toString(), "weird", "tipoNome");
    resolucaoRepository.persisteTipo(tipoValidoEstranho);
    tiposAProcurar.add(tipoValidoEstranho);
    List<Tipo> tipos = resolucaoRepository.tiposPeloNome("tipoNome");
    Assert.assertNotNull("Tipos não foram encontrados com sucesso.", tipos);
    Assert.assertTrue("Não foram encontrados tipos.", tipos.size() > 0);
    Assert.assertEquals("Coleção de tipos não é igual.", tiposAProcurar, tipos);
  }

  @Test
  public void removeTipoUtilizadoPorResolucao() throws Exception {
    String identificadorTipo = UUID.randomUUID().toString();
    Tipo tipo = criaTipo(identificadorTipo, "tipo", "");
    resolucaoRepository.persisteTipo(tipo);
    Tipo tipoSalvo = resolucaoRepository.tipoPeloCodigo(identificadorTipo);
    Assert.assertNotNull("Tipo não foi encontrada com sucesso pelo identificador.", tipoSalvo);
    Assert.assertEquals("O tipo não está sendo salvo.", tipo, tipoSalvo);
    String identificadorResolucao = UUID.randomUUID().toString();
    String idResolucaoSalvo = resolucaoRepository
      .persiste(criaResolucaoQueUsaTipo(identificadorResolucao, identificadorTipo));
    Resolucao resolucaoSalva = resolucaoRepository.byId(idResolucaoSalvo);
    Assert.assertNotNull("Resolução com tipo não foi persistida!", resolucaoSalva);
    exception.expect(ResolucaoUsaTipoException.class);
    resolucaoRepository.removeTipo(identificadorTipo);
  }

  @Test
  public void buscaTipoInexistente() throws Exception {
    String identificadorTipo = UUID.randomUUID().toString();
    Tipo tipoEncontrado = resolucaoRepository.tipoPeloCodigo(identificadorTipo);
    Assert.assertNull("Tipo inexistente está sendo encontrado!", tipoEncontrado);
  }

}
