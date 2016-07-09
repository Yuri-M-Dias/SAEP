package br.inf.ufg.persistencia;

import br.inf.ufg.persistencia.repository.ParecerTest;
import br.inf.ufg.persistencia.repository.ResolucaoTest;
import br.inf.ufg.persistencia.repository.TipoTest;
import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe para agregar as outras classes de testes, para que todas rodem junto de uma forma faciltiada.
 * Também serve para diminuir boilerplate e acoplamento com a conexão do DB.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  ParecerTest.class,
  ResolucaoTest.class,
  TipoTest.class
})
public class RepositoryTestSuite {

  private static DB mongoDatabase;

  /**
   * Abre a conexão com o banco e instancia o br.inf.ufg.persistencia.test.repository.
   */
  @BeforeClass
  public static void initDatabaseConnection(){

    //Database mock
    Fongo fongo = new Fongo("mongo test server");
    mongoDatabase = fongo.getDB("test");

    /**
    // Actual Database
    mongoDatabase = MongoConnection.getDBConnection();
    MongoConnection.deleteDB();
    mongoDatabase = MongoConnection.getDBConnection();
     */

  }

  /**
   * Fecha a conexão com o Mongo e dá drop no DB.
   */
  @AfterClass
  public static void deleteConnection(){
    //mongoDatabase.getMongo().dropDatabase(DB_NAME);
    mongoDatabase.getMongo().close();
    //MongoConnection.deleteDB();
    //MongoConnection.closeDBConnection();
  }

  public static DB getMongoDatabase() {
    return mongoDatabase;
  }

}
