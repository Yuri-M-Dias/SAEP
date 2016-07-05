package repository;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.mongodb.DB;
import framework.BaseMongoRepository;

public class ParecerRepositoryMongoImpl extends BaseMongoRepository<Parecer> implements ParecerRepository {

  public ParecerRepositoryMongoImpl(DB mongoDatabase) {
    super("parecer", mongoDatabase, Parecer.class);
  }

  @Override
  public void adicionaNota(String parecer, Nota nota) {

  }

  @Override
  public void removeNota(Avaliavel original) {

  }

  @Override
  public void persisteParecer(Parecer parecer) {

  }

  @Override
  public void atualizaFundamentacao(String parecer, String fundamentacao) {

  }

  @Override
  public Parecer byId(String id) {
    return null;
  }

  @Override
  public void removeParecer(String id) {

  }

  @Override
  public Radoc radocById(String identificador) {
    return null;
  }

  @Override
  public String persisteRadoc(Radoc radoc) {
    return null;
  }

  @Override
  public void removeRadoc(String identificador) {

  }

}
