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
    if(byId(parecer) == null){
      throw new IdentificadorDesconhecido("Identificador " + parecer + " para parecer inexistente.");
    }
    removeNota(parecer, nota.getItemOriginal());
    parecerDAO.adicionaNota(parecer, nota);
  }

  @Override
  public void removeNota(String parecer, Avaliavel avaliavel) {
    if(byId(parecer) == null){
      throw new IdentificadorDesconhecido("Identificador " + parecer + " para parecer inexistente.");
    }
    parecerDAO.removeNota(parecer, avaliavel);
  }

  @Override
  public void persisteParecer(Parecer parecer) {
    if(byId(parecer.getId()) != null){
      throw new IdentificadorExistente("Parecer com identificador "+ parecer.getId() + " já existe.");
    }
    parecerDAO.create(parecer);
  }

  @Override
  public void atualizaFundamentacao(String parecer, String fundamentacao) {
    if(byId(parecer) == null){
      throw new IdentificadorDesconhecido("Parecer não encontrado: " + parecer);
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
    if(radoc.getId() != null && byId(radoc.getId()) != null){
      throw new IdentificadorExistente("Radoc com identificador "+ radoc.getId() + " já existe.");
    }
    Radoc radocSalvo = radocDAO.create(radoc);
    return radocSalvo.getId();
  }

  @Override
  public void removeRadoc(String identificador) {
    if(parecerDAO.verificaRadocReferenciado(identificador)){
      throw new ExisteParecerReferenciandoRadoc("Radoc " + identificador + " é referenciado por um parecer!");
    }
    radocDAO.delete(identificador);
  }

}
