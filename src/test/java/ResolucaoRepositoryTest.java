import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import org.junit.*;
import repository.ResolucaoRepositoryMongoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResolucaoRepositoryTest {

  private static ResolucaoRepository resolucaoRepository;
  private static DB mongoDatabase;
  private String resolucaoId;

  /**
   * Abre a conexão com o banco e instancia o repository.
   */
  @BeforeClass
  public static void initDatabaseConnection(){
    //TODO: mock database
    Fongo fongo = new Fongo("mongo test server");
    mongoDatabase = fongo.getDB("test");
    resolucaoRepository = new ResolucaoRepositoryMongoImpl(mongoDatabase);
  }

  /**
   * Fecha a conexão com o Mongo.
   */
  @AfterClass
  public static void deleteConnection(){
    mongoDatabase.getMongo().dropDatabase("test");
    mongoDatabase.getMongo().close();
    //MongoConnection.deleteDB();
    //MongoConnection.closeDBConnection();
  }

  @Before
  public void before(){
    Resolucao resolucao = criaResolucao("1");
    String identificador = resolucaoRepository.persiste(resolucao);
    resolucaoId = identificador;
  }

  @After
  public void after(){
    resolucaoRepository.remove(resolucaoId);
  }

  @Test
  public void persisteResolucao(){
    Resolucao resolucao = criaResolucao("2");
    String identificador = resolucaoRepository.persiste(resolucao);
    Assert.assertNotNull("Resolução não foi salva com sucesso.", identificador);
    Resolucao resolucaoSalva = resolucaoRepository.byId(identificador);
    Assert.assertNotNull("Resolução não foi encontrada com sucesso.", resolucaoSalva);
    Assert.assertEquals("As resolução não está sendo salva.", resolucao, resolucaoSalva);
  }

  @Test
  public void findResolucaoById(){
    Resolucao resolucaoSalva = resolucaoRepository.byId(resolucaoId);
    Assert.assertNotNull("Resolução não foi encontrada com sucesso.", resolucaoSalva);
  }

  @Test
  public void removeResolucao(){
    boolean removido = resolucaoRepository.remove(resolucaoId);
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
    resolucoes.forEach(id -> Assert.assertNotNull("Resolução com ID nulo!", id));
  }

  @Test
  public void persisteTipo(){
    //resolucaoRepository.persisteTipo(null);
  }

  private Resolucao criaResolucao(String id){
    Resolucao resolucao = new Resolucao(id, "123", "Uma resolução.", new Date(), criaRegras());
    return resolucao;
  }

  private List<Regra> criaRegras(){
    List<Regra> regras = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String placeholder = "regra-" + i;
      List<String> dependeDe = new ArrayList<>();
      dependeDe.add("nada");
      Regra regra = new Regra(1, placeholder, 20, 5, placeholder, placeholder, placeholder, placeholder,placeholder,
        5, dependeDe);
      regras.add(regra);
    }
    return regras;
  }

}
