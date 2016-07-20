package br.ufg.inf.es.saep.persistencia.mongo.repository;

import br.ufg.inf.es.saep.persistencia.mongo.dao.ResolucaoDAO;
import br.ufg.inf.es.saep.persistencia.mongo.dao.TipoDAO;
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
    return resolucaoDAO.findById(id);
  }

  @Override
  public String persiste(Resolucao resolucao) {
    if (resolucao.getId() == null) {
      throw new CampoExigidoNaoFornecido("identificador");
    }
    if (byId(resolucao.getId()) != null) {
      throw new IdentificadorExistente("Identificador já existe para uma " +
        "Resolução.");
    }
    Resolucao resolucaoSalva = null;
    try {
      resolucaoSalva = resolucaoDAO.create(resolucao);
    } catch (Exception e) {//Requerimento da documentação do repository...
      e.printStackTrace();
      return null;
    }
    return resolucaoSalva.getId();
  }

  @Override
  public boolean remove(String identificador) {
    resolucaoDAO.delete(identificador);
    return byId(identificador) == null;
  }

  @Override
  public List<String> resolucoes() {
    List<String> lista = resolucaoDAO.procuraTodosPeloId();
    try {
      lista = resolucaoDAO.procuraTodosPeloId();
    } catch (Exception e) {
      //Não faz nada.
    }
    return lista;
  }

  @Override
  public void persisteTipo(Tipo tipo) {
    if (tipo.getId() != null && tipoPeloCodigo(tipo.getId()) != null) {
      throw new IdentificadorExistente("Identificador para tipo já existe!");
    }
    try {
      tipoDAO.create(tipo);
    } catch (Exception e) {
      //Não faz nada.
    }
  }

  @Override
  public void removeTipo(String codigo) {
    if (resolucaoDAO.verificaSeAlgumaResolucaoUsaRelato(codigo)) {
      throw new ResolucaoUsaTipoException("O tipo é utilizado por pelo menos " +
        "uma resolução!");
    }
    tipoDAO.delete(codigo);
  }

  @Override
  public Tipo tipoPeloCodigo(String codigo) {
    Tipo tipo = null;
    try {
      tipo = tipoDAO.findById(codigo);
    } catch (Exception e) {
      //Não faz nada.
    }
    return tipo;
  }

  @Override
  public List<Tipo> tiposPeloNome(String nome) {
    List<Tipo> tipos = null;
    try {
      tipos = tipoDAO.procuraPeloNome(nome);
    } catch (Exception e) {
      //Não faz nada.
    }
    return tipos;
  }

}
