package br.ufg.inf.es.saep.persistencia.mongo.dao;

import br.ufg.inf.es.saep.persistencia.mongo.json.SAEPJacksonModule;
import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import org.mongojack.DBQuery;
import org.mongojack.DBUpdate;

import java.util.List;
import java.util.Optional;

/**
 * Encapsula operações em comum sobre a coleção "Parecer".
 */
public class ParecerDAO extends BaseMongoDAO<Parecer> {

  public ParecerDAO(DB mongoDatabase) {
    super("parecer", mongoDatabase, Parecer.class);
  }

  /**
   * Atualiza uma fundamentação dentro de um parecer.
   *
   * @param parecerId
   *         identificador único do parecer que será atualizado
   * @param fundamentacao
   *         novo valor da fundamentação.
   */
  public void atualizaFundamentacao(String parecerId, String fundamentacao) {
    jacksonCollection.findAndModify(DBQuery.is("id", parecerId), DBUpdate.set
      ("fundamentacao", fundamentacao));
  }

  /**
   * Remove uma nota de um parecer, buscando pelo seu avaliável.
   *
   * @param parecerId
   *         identificador do parecer a ser alterado.
   * @param avaliavel
   *         avaliável original a procurar dentro das notas do parecer.
   */
  public void removeNota(String parecerId, Avaliavel avaliavel) {
    Parecer parecer = jacksonCollection.findOne(DBQuery.is("id", parecerId));
    List<Nota> notas = parecer.getNotas();
    ObjectMapper mapper = SAEPJacksonModule.createSAEPObjectMapper();
    Optional<Nota> notaEncontrada = notas.stream()
      .filter(nota -> {
        boolean result = false;
        try {
          //Necessário fazer deste jeito devido a falta de um .equals...
          String itemOriginal =
            mapper.writeValueAsString(nota.getItemOriginal());
          String itemAvaliavel = mapper.writeValueAsString(avaliavel);
          result = itemOriginal.equals(itemAvaliavel);
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
        return result;
      }).findFirst();
    // Se não existir, não faz nada.
    if (!notaEncontrada.isPresent()) {
      return;
    }
    Nota nota = notaEncontrada.get();
    jacksonCollection.findAndModify(DBQuery.is("id", parecerId),
      DBUpdate.pull("notas", nota));
  }

  /**
   * Adiciona nota a coleção "notas" do parecer referenciado.
   *
   * @param parecerId
   *         id do parecer a adicionar a nota.
   * @param nota
   *         nota a ser adiciona.
   */
  public void adicionaNota(String parecerId, Nota nota) {
    jacksonCollection.findAndModify(DBQuery.is("id", parecerId),
      DBUpdate.push("notas", nota));
  }

  /**
   * Verifica se um parecer possui um radoc com o identificador fornecido.
   *
   * @param identificador
   *         identificador do radoc que será verificado.
   * @return true se estivier um parecer que referencia esse radoc, falso caso
   * o contrário.
   */
  public boolean verificaRadocReferenciado(String identificador) {
    boolean result = jacksonCollection
      .findOne(DBQuery.all("radocs", identificador)) != null;
    return result;
  }

}
