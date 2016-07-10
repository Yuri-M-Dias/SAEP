package br.inf.ufg.persistencia.mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class NotaMixin extends Nota {

  @JsonCreator
  public NotaMixin(@JsonProperty("original") Avaliavel origem,
                   @JsonProperty("novo") Avaliavel destino,
                   @JsonProperty("justificativa") String justificativa) {
    super(origem, destino, justificativa);
  }

  @JsonProperty("novo")
  public abstract Avaliavel getItemNovo();

  @JsonProperty("original")
  public abstract Avaliavel getItemOriginal();

}
