package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.RepositoryTestSuite;
import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

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
  public void CRDResolucaoNormal(){
    Resolucao resolucao = criaResolucao(UUID.randomUUID().toString());
    String identificador = resolucaoRepository.persiste(resolucao);
    Assert.assertNotNull("Resolução não foi salva com sucesso.", identificador);
    Resolucao resolucaoSalva = resolucaoRepository.byId(identificador);
    Assert.assertNotNull("Resolução não foi encontrada com sucesso.", resolucaoSalva);
    Assert.assertEquals("As resolução não está sendo salva.", resolucao, resolucaoSalva);
    boolean removido = resolucaoRepository.remove(identificador);
    Assert.assertTrue("Não consegui remover a resolução", removido);
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
    resolucoes.forEach(id -> Assert.assertNotNull("Resolução com ID nulo.", id));
    Assert.assertEquals("IDs recuperados são diferentes.", idsSalvos, resolucoes);
  }

  private Resolucao criaResolucao(String id){
    return new Resolucao(id, "123", "Uma resolução.", new Date(), criaRegras());
  }

  private List<Regra> criaRegras(){
    List<Regra> regras = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String placeholder = "regra-" + i;
      List<String> dependeDe = new ArrayList<>();
      dependeDe.add("nada");
      Random rand = new Random();
      int tipoRegra = rand.nextInt(5);
      Regra regra = new Regra(placeholder, tipoRegra, placeholder, 20, 5,
        placeholder, placeholder, placeholder, placeholder, 5, dependeDe);
      regras.add(regra);
    }
    return regras;
  }

}
