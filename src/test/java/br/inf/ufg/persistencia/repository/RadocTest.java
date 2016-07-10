package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.RepositoryTestSuite;
import br.ufg.inf.es.saep.sandbox.dominio.*;
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
  public void NoRelationshipsCRDRadoc(){
    String identificador = UUID.randomUUID().toString();
    Radoc radoc = criaRadoc(identificador);
    parecerRepository.persisteRadoc(radoc);
    Radoc radocSalvo = parecerRepository.radocById(identificador);
    Assert.assertNotNull("Radoc não foi encontrado com sucesso pelo identificador.", radocSalvo);
    Assert.assertEquals("O radoc não está sendo salvo.", radoc, radocSalvo);
    parecerRepository.removeRadoc(identificador);
    Radoc radocRemovido = parecerRepository.radocById(identificador);
    Assert.assertNull("Radoc não foi removido com sucesso.", radocRemovido);
  }

  private Radoc criaRadoc(String identificador){
    List<Relato> relatos = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Map<String, Valor> valores = new HashMap<>();
      for (int j = 0; j < 5; j++) {
        String tipoValor = UUID.randomUUID().toString();
        //TODO: valores de string e boolean
        Valor valor = new Valor(j);
        valores.put(tipoValor, valor);
      }
      Relato relato = new Relato(UUID.randomUUID().toString(), valores);
      relatos.add(relato);
    }
    return new Radoc(identificador, 2015, relatos);
  }

}
