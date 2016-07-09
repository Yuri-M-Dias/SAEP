package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import com.mongodb.DB;

public class RadocDAO extends BaseMongoDAO<Radoc> {

  public RadocDAO(DB mongoDatabase) {
    super("radoc", mongoDatabase, Radoc.class);
  }

}
