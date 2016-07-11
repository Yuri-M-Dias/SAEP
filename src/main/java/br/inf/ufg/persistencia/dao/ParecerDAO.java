package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
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
    Parecer result = findById(parecerId);
    String fundamentacaoSalva = result.getFundamentacao();
    if (!fundamentacao.equals(fundamentacaoSalva)) {
      throw new SecurityException("Falha ao atualizar o parecer.");
    }
  }

  public void removeNota(String parecerId, Avaliavel avaliavel) {
    Parecer parecer = jacksonCollection.findOne(DBQuery.is("id", parecerId));
    List<Nota> notas = parecer.getNotas();
    Optional<Nota> notaEncontrada = notas.stream()
      .filter(nota -> nota.getItemNovo().equals(avaliavel))
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

}
