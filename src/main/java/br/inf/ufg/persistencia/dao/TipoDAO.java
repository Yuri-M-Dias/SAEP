package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;

import java.util.List;
import java.util.regex.Pattern;

public class TipoDAO extends BaseMongoDAO<Tipo> {

  public TipoDAO(DB mongoDatabase) {
    super("tipo", mongoDatabase, Tipo.class);
    BasicDBObject idIndex = new BasicDBObject("id", 1);
    BasicDBObject idIndexOptions = new BasicDBObject("unique", "true");
    jacksonCollection.createIndex(idIndex, idIndexOptions);
  }

  @Override
  public Tipo findById(String id) {
    Tipo tipo = jacksonCollection.findOne(DBQuery.is("id", id));
    return tipo;
  }

  public List<Tipo> findByName(String name) {
    List<Tipo> tipos;
    DBCursor<Tipo> tiposQuery = jacksonCollection.find(DBQuery.regex("nome", Pattern.compile(name)));
    tipos = tiposQuery.toArray();
    return tipos;
  }

  @Override
  public void delete(String id) {
    Tipo tipo = jacksonCollection.findAndRemove(DBQuery.is("id", id));
    //Check?
  }

}
