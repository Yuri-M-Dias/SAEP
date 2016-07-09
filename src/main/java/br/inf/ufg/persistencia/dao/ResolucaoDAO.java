package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import com.mongodb.DB;
import org.mongojack.DBQuery;

import java.util.List;
import java.util.Objects;

public class ResolucaoDAO extends BaseMongoDAO<Resolucao> {

  public ResolucaoDAO(DB mongoDatabase) {
    super("resolucao", mongoDatabase, Resolucao.class);
  }

  @Override
  public Resolucao findById(String id) {
    Resolucao resolucao = jacksonCollection.findOne(DBQuery.is("id", id));
    return resolucao;
  }

  public void findByIdAndRemove(String id) {
    Resolucao resolucao = jacksonCollection.findAndRemove(DBQuery.is("id", id));
    if(!Objects.equals(resolucao.getId(), id)){
      throw new SecurityException("Error while removing from the br.inf.ufg.persistencia.test.database!");
    }
  }

  public List<String> findAllIds() {
    List<String> ids = jacksonCollection.distinct("id");
    return ids;
  }


}