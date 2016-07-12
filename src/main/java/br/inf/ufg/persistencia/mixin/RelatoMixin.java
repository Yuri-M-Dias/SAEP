package br.inf.ufg.persistencia.mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Set;

public abstract class RelatoMixin extends Relato {

  @JsonCreator
  public RelatoMixin(@JsonProperty("tipo") String tipo,
                     @JsonProperty("valores") Map<String, Valor> valores) {
    super(tipo, valores);
  }

  @JsonIgnore
  @Override
  public abstract Set<String> getVariaveis();

}
