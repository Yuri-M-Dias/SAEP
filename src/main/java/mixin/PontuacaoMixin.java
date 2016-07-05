package mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class PontuacaoMixin extends Pontuacao {

  @JsonCreator
  public PontuacaoMixin(@JsonProperty("nome") String nome,
                        @JsonProperty("valor") Valor valor) {
    super(nome, valor);
  }

}
