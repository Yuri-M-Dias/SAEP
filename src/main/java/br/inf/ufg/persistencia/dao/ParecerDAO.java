package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.inf.ufg.persistencia.json.SAEPJacksonModule;
import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import org.mongojack.DBQuery;
import org.mongojack.DBUpdate;

import java.util.List;
import java.util.Optional;

public class ParecerDAO extends BaseMongoDAO<Parecer> {

  public ParecerDAO(DB mongoDatabase) {
    super("parecer", mongoDatabase, Parecer.class);
  }

  public void atualizaFundamentacao(String parecerId, String fundamentacao) {
    jacksonCollection.findAndModify(DBQuery.is("id", parecerId), DBUpdate.set("fundamentacao", fundamentacao));
  }

  public void removeNota(String parecerId, Avaliavel avaliavel) {
    Parecer parecer = jacksonCollection.findOne(DBQuery.is("id", parecerId));
    List<Nota> notas = parecer.getNotas();
    ObjectMapper mapper = SAEPJacksonModule.createSAEPObjectMapper();
    Optional<Nota> notaEncontrada = notas.stream()
      .filter(nota -> {
        boolean result = false;
        try {
          //Necessário fazer deste jeito devido a falta de um .equals...
          String itemOriginal = mapper.writeValueAsString(nota.getItemOriginal());
          String itemAvaliavel = mapper.writeValueAsString(avaliavel);
          result = itemOriginal.equals(itemAvaliavel);
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
        return result;
      })
      .findFirst();
    Nota nota = notaEncontrada
      .orElseThrow(() -> new IdentificadorDesconhecido("Avaliável não encontrado para o parecer: " + parecerId));
    jacksonCollection.findAndModify(DBQuery.is("id", parecerId),
      DBUpdate.pull("notas", nota));
  }

  public void adicionaNota(String parecerId, Nota nota) {
    jacksonCollection.findAndModify(DBQuery.is("id", parecerId),
      DBUpdate.push("notas", nota));
  }

  public boolean verificaRadocReferenciado(String identificador) {
    boolean result = jacksonCollection
      .findOne(DBQuery.all("radocs", identificador)) != null;
    return result;
  }

}
