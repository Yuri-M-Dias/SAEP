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