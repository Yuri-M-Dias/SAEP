package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.RepositoryTestSuite;
import br.inf.ufg.persistencia.json.SAEPJacksonModule;
import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public class RadocTest {

  private static ParecerRepository parecerRepository;

  @BeforeClass
  public static void initDatabaseConnection(){
    parecerRepository = new ParecerRepositoryMongoImpl(RepositoryTestSuite.getMongoDatabase());
  }

  @Test
  public void NoRelationshipsCRDRadoc() throws Exception {
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

  @Test
  public void jacksonMapSerialization() throws Exception {
    ObjectMapper mapper = SAEPJacksonModule.createSAEPObjectMapper();
    String identificador = UUID.randomUUID().toString();
    Radoc radoc = criaRadoc(identificador);
    String resultJson = mapper.writeValueAsString(radoc.getRelatos());
    System.out.println(resultJson);
  }

  private Radoc criaRadoc(String identificador){
    List<Relato> relatos = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Map<String, Valor> valores = new HashMap<>();
      for (int j = 0; j < 5; j++) {
        String tipoValor = UUID.randomUUID().toString();
        //TODO: valores de string e boolean
        Valor valor = new Valor(j+1);
        valores.put(tipoValor, valor);
      }
      Relato relato = new Relato(UUID.randomUUID().toString(), valores);
      relatos.add(relato);
    }
    return new Radoc(identificador, 2015, relatos);
  }

}
