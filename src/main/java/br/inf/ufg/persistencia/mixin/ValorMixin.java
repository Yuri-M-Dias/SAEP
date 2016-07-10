package br.inf.ufg.persistencia.mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ValorMixin extends Valor {

  @JsonCreator
  public ValorMixin(@JsonProperty("valor") String valor) {
    super(valor);
  }

  @JsonCreator
  public ValorMixin(@JsonProperty("valor") float valor) {
    super(valor);
  }

  @JsonCreator
  public ValorMixin(@JsonProperty("valor") boolean valor) {
    super(valor);
  }

}
