package repository;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.mongodb.DB;
import framework.BaseMongoRepository;

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
    Resolucao resolucao = findById(id);
    return resolucao;
  }

  @Override
  public String persiste(Resolucao resolucao) {
    Resolucao resolucaoSalva = create(resolucao);
    return resolucao.getId();
  }

  @Override
  public boolean remove(String identificador) {
    Resolucao resolucaoDeletada = delete(identificador);
    if (byId(resolucaoDeletada.getId()) != null) {
      return false;
    }
    return true;
  }

  @Override
  public List<String> resolucoes() {
    return null;
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
