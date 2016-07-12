package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.RepositoryTestSuite;
import br.inf.ufg.persistencia.json.SAEPJacksonModule;
import br.ufg.inf.es.saep.sandbox.dominio.*;
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
  public void semRelacionamentosCRDRadoc() throws Exception {
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

  private Parecer criaParecer(String identificador, String idRadoc) {
    List<String> radocsIDs = new ArrayList<>();
    radocsIDs.add(idRadoc);
    for (int i = 0; i < 5; i++) {
      String radocId = UUID.randomUUID().toString();
      radocsIDs.add(radocId);
    }
    List<Pontuacao> pontuacoes = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String atributo = UUID.randomUUID().toString();
      pontuacoes.add(geraPontuacao(atributo));
    }
    List<Nota> notas = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String atributo = UUID.randomUUID().toString();
      notas.add(geraNota(atributo));
    }
    return new Parecer(identificador, "resolucao", radocsIDs, pontuacoes, "fundamentacao", notas);
  }

  private Pontuacao geraPontuacao(String atributo) {
    Valor valor = new Valor(atributo);
    return new Pontuacao(atributo, valor);
  }

  private Nota geraNota(String atributo) {
    Valor valor = new Valor(atributo);
    Avaliavel novo = new Pontuacao(atributo, valor);
    Valor valorNum = new Valor(true);
    Map<String, Valor> map = new HashMap<>();
    map.put("a key", valorNum);
    Avaliavel antigo = new Relato(atributo, map);
    return new Nota(antigo, novo, "uma descricao");
  }

}
