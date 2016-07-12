package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.RepositoryTestSuite;
import br.inf.ufg.persistencia.json.SAEPJacksonModule;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public class ParecerTest {

  private static ParecerRepository parecerRepository;

  @BeforeClass
  public static void initDatabaseConnection() {
    parecerRepository = new ParecerRepositoryMongoImpl(RepositoryTestSuite.getMongoDatabase());
  }

  @Test
  public void CRDParecer() {
    String identificador = UUID.randomUUID().toString();
    Parecer parecer = criaParecer(identificador);
    parecerRepository.persisteParecer(parecer);
    Parecer parecerSalvo = parecerRepository.byId(identificador);
    Assert.assertNotNull("Parecer não está sendo salvo.", parecerSalvo);
    //Assert.assertEquals("O parecer não está sendo salvo corretamente.", parecer, parecerSalvo);
    parecerRepository.removeParecer(identificador);
    Parecer parecerRemovido = parecerRepository.byId(identificador);
    Assert.assertNull("Parecer não foi removido com sucesso.", parecerRemovido);
  }

  @Test
  public void atualizaFundamentacao() {
    String identificador = UUID.randomUUID().toString();
    Parecer parecer = criaParecer(identificador);
    parecerRepository.persisteParecer(parecer);
    Parecer parecerSalvo = parecerRepository.byId(identificador);
    Assert.assertNotNull("Parecer não está sendo salvo.", parecerSalvo);
    //Assert.assertEquals("O parecer não está sendo salvo corretamente.", parecer, parecerSalvo);
    String novaFundamentacao = UUID.randomUUID().toString();
    parecerRepository.atualizaFundamentacao(identificador, novaFundamentacao);
    Parecer parecerAtualizado = parecerRepository.byId(identificador);
    Assert.assertEquals("Fundamentação não foi atualizada.", novaFundamentacao, parecerAtualizado.getFundamentacao());
    parecerRepository.removeParecer(identificador);
    Parecer parecerRemovido = parecerRepository.byId(identificador);
    Assert.assertNull("Parecer não foi removido com sucesso.", parecerRemovido);
  }

  @Test
  public void adicionaERemoveNota() throws Exception {
    String identificador = UUID.randomUUID().toString();
    Parecer parecer = criaParecer(identificador);
    parecerRepository.persisteParecer(parecer);
    Parecer parecerSalvo = parecerRepository.byId(identificador);
    Assert.assertNotNull("Parecer não está sendo salvo.", parecerSalvo);
    ObjectMapper mapper = SAEPJacksonModule.createSAEPObjectMapper();
    String resultOriginal = mapper.writeValueAsString(parecer);
    String resultSalvo = mapper.writeValueAsString(parecerSalvo);
    Assert.assertEquals("O parecer não está sendo salvo corretamente.", resultOriginal, resultSalvo);
    //Testes de igualdade comentados devido a falta de uma implementação de .equals()
    //Assert.assertEquals("O parecer não está sendo salvo corretamente.", parecer, parecerSalvo);
    String atributo = UUID.randomUUID().toString();
    Pontuacao pontuacao = geraPontuacao(atributo);
    Nota notaNova = geraNota(pontuacao);
    parecerRepository.adicionaNota(identificador, notaNova);
    Parecer parecerAtualizado = parecerRepository.byId(identificador);
    Assert.assertThat("Nota não sendo adicionada ao parecer.",
      parecerAtualizado.getNotas(), IsCollectionWithSize.hasSize(6));
    parecerRepository.removeNota(identificador, pontuacao);
    parecerAtualizado = parecerRepository.byId(identificador);
    Assert.assertThat("Nota não está sendo removida.", parecerAtualizado.getNotas(), IsCollectionWithSize.hasSize(5));
    parecerRepository.removeParecer(identificador);
    Parecer parecerRemovido = parecerRepository.byId(identificador);
    Assert.assertNull("Parecer não foi removido com sucesso.", parecerRemovido);
  }

  private Parecer criaParecer(String identificador) {
    List<String> radocsIDs = new ArrayList<>();
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

  private Nota geraNota(Pontuacao pontuacao) {
    Avaliavel novo = pontuacao;
    Valor valorNum = new Valor(true);
    Map<String, Valor> map = new HashMap<>();
    map.put("a key", valorNum);
    String atributo = UUID.randomUUID().toString();
    Avaliavel antigo = new Relato(atributo, map);
    return new Nota(novo, antigo, "uma descricao");
  }

}
