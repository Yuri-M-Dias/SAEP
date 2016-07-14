package br.inf.ufg.persistencia.dao;

import br.inf.ufg.persistencia.framework.BaseMongoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.mongodb.DB;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Encapsula operações em comum com a coleção de tipos.
 */
public class TipoDAO extends BaseMongoDAO<Tipo> {

  public TipoDAO(DB mongoDatabase) {
    super("tipo", mongoDatabase, Tipo.class);
  }

  /**
   * Procura os tipos pelo nome fornecido.
   *
   * @param name
   *         nome a ser procurado, podendo ser caracteres que estarão
   *         presentes em qualquer parte do nome.
   * @return uma lista contendo os tipos que possuem nomes similares a esses.
   */
  public List<Tipo> procuraPeloNome(String name) {
    List<Tipo> tipos;
    DBCursor<Tipo> tiposQuery =
      jacksonCollection.find(DBQuery.regex("nome", Pattern.compile(name)));
    tipos = tiposQuery.toArray();
    return tipos;
  }

}
