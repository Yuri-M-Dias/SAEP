package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.mongodb.DB;

public class TipoDAO extends BaseMongoDAO<Tipo> {

  public TipoDAO(DB mongoDatabase) {
    super("tipo", mongoDatabase, Tipo.class);
  }

}