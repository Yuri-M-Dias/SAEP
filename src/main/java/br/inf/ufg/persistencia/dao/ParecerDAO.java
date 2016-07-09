package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import com.mongodb.DB;

public class ParecerDAO extends BaseMongoDAO<Parecer> {

  public ParecerDAO(DB mongoDatabase) {
    super("parecer", mongoDatabase, Parecer.class);
  }

}
