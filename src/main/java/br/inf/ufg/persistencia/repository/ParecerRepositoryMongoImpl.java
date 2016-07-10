package br.inf.ufg.persistencia.repository;

import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;

import br.inf.ufg.persistencia.dao.ParecerDAO;
import br.inf.ufg.persistencia.dao.RadocDAO;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.mongodb.DB;

public class ParecerRepositoryMongoImpl implements ParecerRepository {

  private ParecerDAO parecerDAO;
  private RadocDAO radocDAO;

  public ParecerRepositoryMongoImpl(DB mongoDatabase) {
    parecerDAO = new ParecerDAO(mongoDatabase);
    radocDAO = new RadocDAO(mongoDatabase);
  }

  @Override
  public void adicionaNota(String parecer, Nota nota) {

  }

  @Override
  public void removeNota(String s, Avaliavel avaliavel) {

  }

  @Override
  public void persisteParecer(Parecer parecer) {
    parecerDAO.create(parecer);
  }

  @Override
  public void atualizaFundamentacao(String parecer, String fundamentacao) {
    if(byId(parecer) == null){
      throw new IdentificadorDesconhecido("parecer");
    }
    parecerDAO.atualizaFundamentacao(parecer, fundamentacao);
  }

  @Override
  public Parecer byId(String id) {
    return parecerDAO.findById(id);
  }

  @Override
  public void removeParecer(String id) {
    parecerDAO.delete(id);
  }

  @Override
  public Radoc radocById(String identificador) {
    return radocDAO.findById(identificador);
  }

  @Override
  public String persisteRadoc(Radoc radoc) {
    Radoc radocSalvo = radocDAO.create(radoc);
    return radocSalvo.getId();
  }

  @Override
  public void removeRadoc(String identificador) {
    radocDAO.delete(identificador);
    if(radocById(identificador) != null){
      throw new SecurityException("Falha ao remover radoc.");
    }
  }

}
