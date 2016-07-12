package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.RepositoryTestSuite;
import br.inf.ufg.persistencia.json.SAEPJacksonModule;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static br.inf.ufg.persistencia.repository.UtilsGenerator.*;

public class ParecerTest {

  private static ParecerRepository parecerRepository;

  @BeforeClass
  public static void initDatabaseConnection() {
    parecerRepository = new ParecerRepositoryMongoImpl(RepositoryTestSuite.getMongoDatabase());
  }

  @Test
  public void createReadDeleteParece() {
    String identificador = UUID.randomUUID().toString();
    Parecer parecer = criaParecer(identificador);
    parecerRepository.persisteParecer(parecer);
    Parecer parecerSalvo = parecerRepository.byId(identificador);
    Assert.assertNotNull("Parecer não está sendo salvo.", parecerSalvo);
    Assert.assertEquals("Parecer salvo com id diferente, erro ocorreu.", parecer.getId(), parecerSalvo.getId());
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
    Assert.assertEquals("Parecer salvo com id diferente, erro ocorreu.", parecer.getId(), parecerSalvo.getId());
    String novaFundamentacao = UUID.randomUUID().toString();
    parecerRepository.atualizaFundamentacao(identificador, novaFundamentacao);
    Parecer parecerAtualizado = parecerRepository.byId(identificador);
    Assert.assertEquals("Fundamentação não foi atualizada.", novaFundamentacao, parecerAtualizado.getFundamentacao());
    parecerRepository.removeParecer(identificador);
    Parecer parecerRemovido = parecerRepository.byId(identificador);
    Assert.assertNull("Parecer não foi removido com sucesso.", parecerRemovido);
  }

  @Test
  public void adicionaRemoveNota() throws Exception {
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

  @Test
  public void adicionaNotaComSubstituicaoDeNotaAntiga() throws Exception {
    String identificador = UUID.randomUUID().toString();
    Parecer parecer = criaParecer(identificador);
    parecerRepository.persisteParecer(parecer);
    Parecer parecerSalvo = parecerRepository.byId(identificador);
    Assert.assertNotNull("Parecer válido não está sendo salvo.", parecerSalvo);
    Nota notaAAlterar = parecerSalvo.getNotas().get(1);
    Avaliavel original = notaAAlterar.getItemOriginal();
    String atributo = UUID.randomUUID().toString();
    Pontuacao pontuacaoNova = geraPontuacao(atributo);
    Nota notaNova = geraNota(pontuacaoNova, original);
    parecerRepository.adicionaNota(identificador, notaNova);
    Parecer parecerAtualizado = parecerRepository.byId(identificador);
    Assert.assertThat("Nota não foi adicionada corretamente, ou antiga não foi modificada.",
      parecerAtualizado.getNotas(), IsCollectionWithSize.hasSize(5));
    parecerRepository.removeParecer(identificador);
    Parecer parecerRemovido = parecerRepository.byId(identificador);
    Assert.assertNull("Parecer não foi removido com sucesso.", parecerRemovido);
  }

}
