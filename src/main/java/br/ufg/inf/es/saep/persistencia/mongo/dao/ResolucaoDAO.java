package br.ufg.inf.es.saep.persistencia.mongo.dao;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import com.mongodb.DB;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;

import java.util.List;

/**
 * Encapsula operações em comum com a coleção de resoluções.
 */
public class ResolucaoDAO extends BaseMongoDAO<Resolucao> {

  public ResolucaoDAO(DB mongoDatabase) {
    super("resolucao", mongoDatabase, Resolucao.class);
  }

  /**
   * Itera sobre a coleção inteira de resoluções, pegando pelo id único como
   * discriminador, e retornando uma lista com todos os identificadores.
   *
   * @return uma Lista de strings com os ids de todas as resoluções salvas
   * atualmente.
   */
  public List<String> procuraTodosPeloId() {
    List<String> ids = jacksonCollection.distinct("id");
    return ids;
  }

  /**
   * Verifica se existe pelo menos alguma resolução que usa esse tipo de
   * Relato nas suas regras.
   *
   * @param tipoRelato
   *         tipo de relato como definido em
   *         {@link br.ufg.inf.es.saep.sandbox.dominio.Regra}
   * @return true se alguma resolução utiliza esse tipo, false caso contrário
   */
  public boolean verificaSeAlgumaResolucaoUsaRelato(String tipoRelato) {
    DBCursor<Resolucao> cursorResolucoes = jacksonCollection
      .find(DBQuery.elemMatch("regras", DBQuery.in("tipoRelato", tipoRelato)));
    return cursorResolucoes.count() > 0;
  }

}
