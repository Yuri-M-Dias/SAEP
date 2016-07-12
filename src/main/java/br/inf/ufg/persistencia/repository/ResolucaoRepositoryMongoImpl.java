package br.inf.ufg.persistencia.repository;

import br.inf.ufg.persistencia.dao.ResolucaoDAO;
import br.inf.ufg.persistencia.dao.TipoDAO;
import br.ufg.inf.es.saep.sandbox.dominio.*;
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
    Resolucao resolucaoEncontrada = resolucaoDAO.findById(id);
    return resolucaoEncontrada;
  }

  @Override
  public String persiste(Resolucao resolucao) {
    if (resolucao.getId() == null){
      throw new CampoExigidoNaoFornecido("identificador");
    }
    if (byId(resolucao.getId()) != null){
      throw new IdentificadorExistente("Identificador já existe para uma Resolução.");
    }
    Resolucao resolucaoSalva = null;
    try {
      resolucaoSalva = resolucaoDAO.create(resolucao);
    } catch (Exception e){
      e.printStackTrace();
      return null;
    }
    return resolucao.getId();
  }

  @Override
  public boolean remove(String identificador) {
    resolucaoDAO.delete(identificador);
    return byId(identificador) == null;
  }

  @Override
  public List<String> resolucoes() {
    List<String> resolucoesIds = resolucaoDAO.findAllIds();
    return resolucoesIds;
  }

  @Override
  public void persisteTipo(Tipo tipo) {
    if(tipo.getId() != null && tipoPeloCodigo(tipo.getId()) != null){
      throw new IdentificadorExistente("Identificador para tipo já existe!");
    }
    tipoDAO.create(tipo);
  }

  @Override
  public void removeTipo(String codigo) {
    if(resolucaoDAO.verificaSeAlgumaResolucaoUsaRelato(codigo)){
      throw new ResolucaoUsaTipoException("O tipo é utilizado por pelo menos uma resolução!");
    }
    tipoDAO.delete(codigo);
  }

  @Override
  public Tipo tipoPeloCodigo(String codigo) {
    Tipo tipo = tipoDAO.findById(codigo);
    return tipo;
  }

  @Override
  public List<Tipo> tiposPeloNome(String nome) {
    List<Tipo> tipos = tipoDAO.findByName(nome);
    return tipos;
  }

}
