package repository;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.mongodb.DB;
import framework.BaseMongoRepository;
import org.mongojack.DBCursor;
import org.mongojack.DBQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuri on 28/06/16.
 */
public class ResolucaoRepositoryMongoImpl extends BaseMongoRepository<Resolucao> implements ResolucaoRepository {

  public ResolucaoRepositoryMongoImpl(DB mongoDatabase) {
    super("resolucao", mongoDatabase, Resolucao.class);
  }

  @Override
  public Resolucao byId(String id) {
    //TODO: validation
    Resolucao resolucaoEncontrada = jacksonCollection.findOne(DBQuery.is("id", id));
    return resolucaoEncontrada;
  }

  @Override
  public String persiste(Resolucao resolucao) {
    //TODO: validation
    Resolucao resolucaoSalva = create(resolucao);
    return resolucao.getId();
  }

  @Override
  public boolean remove(String identificador) {
    //TODO: validation
    jacksonCollection.findAndRemove(DBQuery.is("id", identificador));
    if (byId(identificador) != null) {
      return false;
    }
    return true;
  }

  @Override
  public List<String> resolucoes() {
    List<String> resolucoesIds = new ArrayList<>();
    DBCursor<Resolucao> resolucaoDBCursor = jacksonCollection.find();
    resolucaoDBCursor.forEach(resolucao -> resolucoesIds.add(resolucao.getId()));
    return resolucoesIds;
  }

  @Override
  public void persisteTipo(Tipo tipo) {

  }

  @Override
  public void removeTipo(String codigo) {

  }

  @Override
  public Tipo tipoPeloCodigo(String codigo) {
    return null;
  }

  @Override
  public List<Tipo> tiposPeloNome(String nome) {
    return null;
  }

}
