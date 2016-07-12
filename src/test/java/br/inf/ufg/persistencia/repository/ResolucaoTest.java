package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.RepositoryTestSuite;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static br.inf.ufg.persistencia.repository.UtilsGenerator.criaResolucao;

public class ResolucaoTest {

  private static ResolucaoRepository resolucaoRepository;

  /**
   * Abre a conexão com o banco.
   */
  @BeforeClass
  public static void initDatabaseConnection(){
    resolucaoRepository = new ResolucaoRepositoryMongoImpl(RepositoryTestSuite.getMongoDatabase());
  }

  @Test
  public void createReadDeleteResolucao(){
    Resolucao resolucao = criaResolucao(UUID.randomUUID().toString());
    String identificador = resolucaoRepository.persiste(resolucao);
    Assert.assertNotNull("Resolução não foi salva com sucesso.", identificador);
    Resolucao resolucaoSalva = resolucaoRepository.byId(identificador);
    Assert.assertNotNull("Resolução não foi encontrada com sucesso.", resolucaoSalva);
    Assert.assertEquals("As resolução não está sendo salva.", resolucao, resolucaoSalva);
    boolean removido = resolucaoRepository.remove(identificador);
    Assert.assertTrue("Não consegui remover a resolução", removido);
  }

  @Test(expected = IdentificadorExistente.class)
  public void resolucaoIdentificadorRepetido(){
    String identificador = UUID.randomUUID().toString();
    Resolucao resolucao = criaResolucao(identificador);
    identificador = resolucaoRepository.persiste(resolucao);
    Resolucao outraResolucao = criaResolucao(identificador);
    try {
      resolucaoRepository.persiste(resolucao);
    } finally {
      resolucaoRepository.remove(identificador);
    }
  }

  @Test
  public void recuperaListaResolucoesDisponiveis(){
    List<String> idsSalvos = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Resolucao resolucao = criaResolucao(String.valueOf(i));
      String identificador = resolucaoRepository.persiste(resolucao);
      idsSalvos.add(identificador);
    }
    List<String> resolucoes = resolucaoRepository.resolucoes();
    Assert.assertTrue("IDs recuperados são diferentes.", resolucoes.containsAll(idsSalvos));
  }

}
