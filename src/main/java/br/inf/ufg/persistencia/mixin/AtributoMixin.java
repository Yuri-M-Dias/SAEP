package br.inf.ufg.persistencia.mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AtributoMixin extends Atributo {

  @JsonCreator
  public AtributoMixin(@JsonProperty("nome") String nome,
                       @JsonProperty("descricao") String descricao,
                       @JsonProperty("tipo") int tipo) {
    super(nome, descricao, tipo);
  }

}
