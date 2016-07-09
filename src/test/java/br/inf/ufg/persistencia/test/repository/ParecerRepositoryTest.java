package br.inf.ufg.persistencia.test.repository;

import br.inf.ufg.persistencia.repository.ParecerRepositoryMongoImpl;
import br.inf.ufg.persistencia.test.RepositoryTestSuite;
import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParecerRepositoryTest {

  private static ParecerRepository parecerRepository;

  @BeforeClass
  public static void initDatabaseConnection(){
    parecerRepository = new ParecerRepositoryMongoImpl(RepositoryTestSuite.getMongoDatabase());
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
