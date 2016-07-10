package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.RepositoryTestSuite;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParecerTest {

  private static ParecerRepository parecerRepository;

  @BeforeClass
  public static void initDatabaseConnection(){
    parecerRepository = new ParecerRepositoryMongoImpl(RepositoryTestSuite.getMongoDatabase());
  }

  @Test
  public void CRDParecer(){
    parecerRepository.byId(null);
    parecerRepository.removeParecer(null);
    parecerRepository.persisteParecer(null);
  }

  @Test
  public void removeParecer(){
    parecerRepository.removeParecer(null);
  }

  private Parecer criaParecer(){
    String identificador = UUID.randomUUID().toString();
    List<String> radocsIDs = new ArrayList<>();
    List<Pontuacao> pontuacoes = new ArrayList<>();
    List<Nota> notas = new ArrayList<>();
    return new Parecer(identificador, "resolucao", radocsIDs, pontuacoes, "fundamentacao", notas);
  }

}
