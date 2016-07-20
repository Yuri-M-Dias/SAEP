package br.ufg.inf.es.saep.persistencia.mongo.mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongojack.Id;
import org.mongojack.ObjectId;

import java.util.Date;
import java.util.List;

public abstract class ResolucaoMixin extends Resolucao {

  public ResolucaoMixin(@Id @ObjectId String id,
                        @JsonProperty("nome") String nome,
                        @JsonProperty("descricao") String descricao,
                        @JsonProperty("dataAprovacao") Date dataAprovacao,
                        @JsonProperty("regras") List<Regra> regras) {
    super(id, nome, descricao, dataAprovacao, regras);
  }

}
