import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;
import com.mongodb.DB;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.MongoConnection;
import repository.ParecerRepositoryMongoImpl;

/**
 * Created by yuri on 05/07/16.
 */
public class ParecerRepositoryTest {

  private static ParecerRepository parecerRepository;
  private static DB mongoDatabase;

  @BeforeClass
  public static void initDatabaseConnection(){
    //TODO: mock database
    mongoDatabase = MongoConnection.getDBConnection();
    parecerRepository = new ParecerRepositoryMongoImpl(mongoDatabase);
  }

  /**
   * Fecha a conexão com o Mongo.
   */
  @AfterClass
  public static void deleteConnection(){
    MongoConnection.closeDBConnection();
  }


  @Test
  public void persisteParecer(){
    parecerRepository.persisteParecer(null);
  }

  @Test
  public void persisteRadoc(){
    parecerRepository.persisteRadoc(null);
  }

  @Test
  public void removeParecer(){
    parecerRepository.removeParecer(null);
  }

  @Test
  public void removeRadoc(){
    parecerRepository.removeRadoc(null);
  }

}
