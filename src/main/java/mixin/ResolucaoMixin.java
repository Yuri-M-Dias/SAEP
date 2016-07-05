package mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongojack.Id;
import org.mongojack.ObjectId;

import java.util.Date;
import java.util.List;

public abstract class ResolucaoMixin extends Resolucao {

  @JsonCreator
  public ResolucaoMixin(@Id @ObjectId @JsonProperty("nome") String id,
                        @JsonProperty("descricao") String descricao,
                        @JsonProperty("dataAprovacao") Date dataAprovacao,
                        @JsonProperty("regras") List<Regra> regras) {
    super(id, descricao, dataAprovacao, regras);
  }

}
