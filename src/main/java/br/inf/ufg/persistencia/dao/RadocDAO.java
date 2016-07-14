package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import com.mongodb.DB;

/**
 * Encapsula operações em comum sobre a coleção de Radocs.
 */
public class RadocDAO extends BaseMongoDAO<Radoc> {

  public RadocDAO(DB mongoDatabase) {
    super("radoc", mongoDatabase, Radoc.class);
  }

}
