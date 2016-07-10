package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import com.mongodb.DB;
import org.mongojack.DBQuery;
import org.mongojack.DBUpdate;

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

}
