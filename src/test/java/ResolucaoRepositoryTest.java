import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import com.mongodb.DB;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.MongoConnection;
import repository.ResolucaoRepositoryMongoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResolucaoRepositoryTest {

  private static ResolucaoRepository resolucaoRepository;
  private static DB mongoDatabase;

  @BeforeClass
  public static void initDatabaseConnection(){
    //TODO: mock database
    mongoDatabase = MongoConnection.getDBConnection();
    resolucaoRepository = new ResolucaoRepositoryMongoImpl(mongoDatabase);
  }

  @Test
  public void persisteTipo(){
    resolucaoRepository.persisteTipo(null);
  }

  @Test
  public void persisteResolucao(){
    Resolucao resolucao = criaResolucao();
    String identificador = resolucaoRepository.persiste(resolucao);
    Assert.assertNotNull("Resolução não foi salva com sucesso.", identificador);
    Resolucao resolucaoSalva = resolucaoRepository.byId("123");
    Assert.assertNotNull("Resolução não foi encontrada com sucesso.", resolucaoSalva);
    Assert.assertEquals("As resolução não está sendo salva.", resolucao, resolucaoSalva);
  }

  @Test
  public void findResolucaoById(){
    Resolucao resolucaoSalva = resolucaoRepository.byId("123");
    Assert.assertNotNull("Resolução não foi encontrada com sucesso.", resolucaoSalva);
  }

  @Test
  public void removeResolucao(){
    boolean removido = resolucaoRepository.remove("123");
    Assert.assertTrue("Não consegui remover a resolução", removido);
  }

  private Resolucao criaResolucao(){
    Resolucao resolucao = new Resolucao("123", "Uma resolução.", new Date(), criaRegras());
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
