package mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class NotaMixin extends Nota {

  @JsonCreator
  public NotaMixin(@JsonProperty("origem") Avaliavel origem,
                   @JsonProperty("destino") Avaliavel destino,
                   @JsonProperty("justificativa") String justificativa) {
    super(origem, destino, justificativa);
  }

}
