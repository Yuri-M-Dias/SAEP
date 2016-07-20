package br.ufg.inf.es.saep.persistencia.mongo.repository;

import br.ufg.inf.es.saep.persistencia.mongo.dao.ParecerDAO;
import br.ufg.inf.es.saep.persistencia.mongo.dao.RadocDAO;
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
    if (byId(parecer) == null) {
      throw new IdentificadorDesconhecido("Identificador " + parecer + " para" +
        " parecer inexistente.");
    }
    removeNota(parecer, nota.getItemOriginal());
    parecerDAO.adicionaNota(parecer, nota);
  }

  @Override
  public void removeNota(String parecer, Avaliavel avaliavel) {
    if (byId(parecer) == null) {
      throw new IdentificadorDesconhecido("Identificador " + parecer + " para" +
        " parecer inexistente.");
    }
    parecerDAO.removeNota(parecer, avaliavel);
  }

  @Override
  public void persisteParecer(Parecer parecer) {
    //Catch em qualquer exceção lançada.
    try {
      parecerDAO.create(parecer);
    } catch (Exception e) {
      throw new IdentificadorExistente("Parecer com identificador " + parecer
        .getId() + " já existe.");
    }
  }

  @Override
  public void atualizaFundamentacao(String parecer, String fundamentacao) {
    if (byId(parecer) == null) {
      throw new IdentificadorDesconhecido("Parecer não encontrado: " + parecer);
    }
    parecerDAO.atualizaFundamentacao(parecer, fundamentacao);
  }

  @Override
  public Parecer byId(String id) {
    Parecer parecer = null;
    try {
      parecer = parecerDAO.findById(id);
    } catch (Exception e){
      //Não faz nada
    }
    return parecer;
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
    if (radoc.getId() != null && byId(radoc.getId()) != null) {
      throw new IdentificadorExistente("Radoc com identificador " + radoc
        .getId() + " já existe.");
    }
    Radoc radocSalvo = null;
    try{
        radocSalvo = radocDAO.create(radoc);
    } catch (Exception e){
      //Não faz nada.
    }
    if (radocSalvo == null || radocSalvo.getId() != null) {
      return null;
    }
    return radocSalvo.getId();
  }

  @Override
  public void removeRadoc(String identificador) {
    if (parecerDAO.verificaRadocReferenciado(identificador)) {
      throw new ExisteParecerReferenciandoRadoc("Radoc " + identificador + " " +
        "é referenciado por um parecer!");
    }
    radocDAO.delete(identificador);
  }

}
