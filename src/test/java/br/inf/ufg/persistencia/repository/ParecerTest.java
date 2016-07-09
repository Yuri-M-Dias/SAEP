package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.RepositoryTestSuite;
import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParecerTest {

  private static ParecerRepository parecerRepository;

  @BeforeClass
  public static void initDatabaseConnection(){
    parecerRepository = new ParecerRepositoryMongoImpl(RepositoryTestSuite.getMongoDatabase());
  }

  @Test
  public void CRDParecer(){
    parecerRepository.persisteParecer(null);
  }

  @Test
  public void removeParecer(){
    parecerRepository.removeParecer(null);
  }

}
