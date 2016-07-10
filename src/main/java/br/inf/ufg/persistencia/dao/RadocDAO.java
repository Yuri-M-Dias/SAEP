package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import org.mongojack.DBQuery;

import java.util.Objects;

public class RadocDAO extends BaseMongoDAO<Radoc> {

  public RadocDAO(DB mongoDatabase) {
    super("radoc", mongoDatabase, Radoc.class);
    BasicDBObject idIndex = new BasicDBObject("id", 1);
    BasicDBObject idIndexOptions = new BasicDBObject("unique", "true");
    jacksonCollection.createIndex(idIndex, idIndexOptions);
  }

  @Override
  public Radoc findById(String id) {
    Radoc radoc = jacksonCollection.findOne(DBQuery.is("id", id));
    return radoc;
  }

  @Override
  public void delete(String id) {
    Radoc radoc = jacksonCollection.findAndRemove(DBQuery.is("id", id));
    if(!Objects.equals(radoc.getId(), id)){
      throw new SecurityException("Error while removing from the database!");
    }
  }

}
