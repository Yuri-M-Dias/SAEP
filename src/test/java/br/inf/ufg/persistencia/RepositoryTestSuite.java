package br.inf.ufg.persistencia;

import br.inf.ufg.persistencia.database.MongoConnection;
import br.inf.ufg.persistencia.repository.ParecerTest;
import br.inf.ufg.persistencia.repository.RadocTest;
import br.inf.ufg.persistencia.repository.ResolucaoTest;
import br.inf.ufg.persistencia.repository.TipoTest;
import com.mongodb.DB;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe para agregar as outras classes de testes, para que todas rodem junto
 * de uma forma faciltiada. Também serve para diminuir boilerplate e acoplamento
 * com a conexão do DB.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  ParecerTest.class,
  ResolucaoTest.class,
  TipoTest.class,
  RadocTest.class
})
public class RepositoryTestSuite {

  private static DB mongoDatabase;

  /**
   * Abre a conexão com o banco e instancia o br.inf.ufg.persistencia.test
   * .repository.
   */
  @BeforeClass
  public static void initDatabaseConnection() {
    // Enable MongoDB logging in general
    System.setProperty("DEBUG.MONGO", "true");
    // Enable DB operation tracing
    System.setProperty("DB.TRACE", "true");
    // Pega a conexão com o banco de dados.
    //mongoDatabase = MongoConnection.getFakedDBConnection(); //Utilizado
    // para testes locais.
    mongoDatabase = MongoConnection.getDBConnection();
    //Deleta o banco já existente, e cria o BD de novo.
    MongoConnection.deleteDB();
    mongoDatabase = MongoConnection.getDBConnection();
    System.out.println("Conexão com o mongo: " + mongoDatabase);
    Assert.assertNotNull("Falha na conexão com o mongo", mongoDatabase);
  }

  /**
   * Fecha a conexão com o Mongo;
   */
  @AfterClass
  public static void deleteConnection() {
    mongoDatabase.getMongo().close();
  }


  /**
   * Referência estática para uma conexão, para vias de testes.
   *
   * @return uma conexão com o MongoDB
   */
  public static DB getMongoDatabase() {
    return mongoDatabase;
  }

}
