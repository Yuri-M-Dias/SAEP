package br.ufg.inf.es.saep.persistencia.mongo.mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ValorMixin extends Valor {

  @JsonCreator
  public ValorMixin(@JsonProperty("string") String valor) {
    super(valor);
  }

  public ValorMixin(@JsonProperty("real") float valor) {
    super(valor);
  }

  public ValorMixin(@JsonProperty("logico") boolean valor) {
    super(valor);
  }

  @JsonProperty("real")
  @Override
  public float getFloat() {
    return super.getFloat();
  }

  @JsonProperty("logico")
  @Override
  public boolean getBoolean() {
    return super.getBoolean();
  }

  @JsonProperty("string")
  @Override
  public String getString() {
    return super.getString();
  }

}
