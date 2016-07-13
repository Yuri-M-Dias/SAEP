package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.mongodb.DB;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;

import java.util.List;
import java.util.regex.Pattern;

public class TipoDAO extends BaseMongoDAO<Tipo> {

  public TipoDAO(DB mongoDatabase) {
    super("tipo", mongoDatabase, Tipo.class);
  }

  public List<Tipo> findByName(String name) {
    List<Tipo> tipos;
    DBCursor<Tipo> tiposQuery =
      jacksonCollection.find(DBQuery.regex("nome", Pattern.compile(name)));
    tipos = tiposQuery.toArray();
    return tipos;
  }

}
