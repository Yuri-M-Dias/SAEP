package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import com.mongodb.DB;

import java.util.List;

public class ResolucaoDAO extends BaseMongoDAO<Resolucao> {

  public ResolucaoDAO(DB mongoDatabase) {
    super("resolucao", mongoDatabase, Resolucao.class);
  }

  public List<String> findAllIds() {
    List<String> ids = jacksonCollection.distinct("id");
    return ids;
  }

}
