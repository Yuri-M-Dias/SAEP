package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.dao.ResolucaoDAO;
import br.inf.ufg.persistencia.dao.TipoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.mongodb.DB;

import java.util.List;

public class ResolucaoRepositoryMongoImpl implements ResolucaoRepository {

  private ResolucaoDAO resolucaoDAO;
  private TipoDAO tipoDAO;

  public ResolucaoRepositoryMongoImpl(DB mongoDatabase) {
    resolucaoDAO = new ResolucaoDAO(mongoDatabase);
    tipoDAO = new TipoDAO(mongoDatabase);
  }

  @Override
  public Resolucao byId(String id) {
    //TODO: validation
    Resolucao resolucaoEncontrada = resolucaoDAO.findById(id);
    return resolucaoEncontrada;
  }

  @Override
  public String persiste(Resolucao resolucao) {
    //TODO: validation
    Resolucao resolucaoSalva = resolucaoDAO.create(resolucao);
    return resolucao.getId();
  }

  @Override
  public boolean remove(String identificador) {
    //TODO: validation
    resolucaoDAO.findByIdAndRemove(identificador);
    if (byId(identificador) != null) {
      return false;
    }
    return true;
  }

  @Override
  public List<String> resolucoes() {
    List<String> resolucoesIds = resolucaoDAO.findAllIds();
    return resolucoesIds;
  }

  @Override
  public void persisteTipo(Tipo tipo) {
    //TODO: validation
    tipoDAO.create(tipo);
  }

  @Override
  public void removeTipo(String codigo) {
    //TODO: validation
    tipoDAO.delete(codigo);
  }

  @Override
  public Tipo tipoPeloCodigo(String codigo) {
    //TODO: validation
    Tipo tipo = tipoDAO.findById(codigo);
    return tipo;
  }

  @Override
  public List<Tipo> tiposPeloNome(String nome) {
    //TODO:
    return null;
  }

}
