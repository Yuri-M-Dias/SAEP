package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.RepositoryTestSuite;
import br.inf.ufg.persistencia.json.SAEPJacksonModule;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static br.inf.ufg.persistencia.repository.UtilsGenerator.criaParecer;
import static br.inf.ufg.persistencia.repository.UtilsGenerator.criaRadoc;

public class RadocTest {

  private static ParecerRepository parecerRepository;

  @BeforeClass
  public static void initDatabaseConnection(){
    parecerRepository = new ParecerRepositoryMongoImpl(RepositoryTestSuite.getMongoDatabase());
  }

  @Test
  public void semRelacionamentosCreateReadDeleteRadoc() throws Exception {
    String identificador = UUID.randomUUID().toString();
    Radoc radoc = criaRadoc(identificador);
    parecerRepository.persisteRadoc(radoc);
    Radoc radocSalvo = parecerRepository.radocById(identificador);
    Assert.assertNotNull("Radoc não foi encontrado com sucesso pelo identificador.", radocSalvo);
    ObjectMapper mapper = SAEPJacksonModule.createSAEPObjectMapper();
    String resultOriginal = mapper.writeValueAsString(radoc);
    String resultSalvo = mapper.writeValueAsString(radoc);
    Assert.assertEquals("O radoc não está sendo salvo.", resultOriginal, resultSalvo);
    parecerRepository.removeRadoc(identificador);
    Radoc radocRemovido = parecerRepository.radocById(identificador);
    Assert.assertNull("Radoc não foi removido com sucesso.", radocRemovido);
  }

  @Test(expected = ExisteParecerReferenciandoRadoc.class)
  public void falhaRemoverRadocReferenciadoPorParecer() throws Exception {
    String identificador = UUID.randomUUID().toString();
    String radocId = UUID.randomUUID().toString();
    Parecer parecer = criaParecer(identificador, radocId);
    parecerRepository.persisteParecer(parecer);
    Parecer parecerSalvo = parecerRepository.byId(identificador);
    Assert.assertNotNull("Parecer não está sendo salvo.", parecerSalvo);
    Radoc radoc = criaRadoc(radocId);
    parecerRepository.persisteRadoc(radoc);
    Radoc radocSalvo = parecerRepository.radocById(radocId);
    Assert.assertNotNull("Radoc não foi encontrado com sucesso pelo identificador.", radocSalvo);
    parecerRepository.removeRadoc(radocId);
    parecerRepository.removeParecer(identificador);
    Parecer parecerRemovido = parecerRepository.byId(identificador);
    Assert.assertNull("Parecer não foi removido com sucesso.", parecerRemovido);
  }

}
