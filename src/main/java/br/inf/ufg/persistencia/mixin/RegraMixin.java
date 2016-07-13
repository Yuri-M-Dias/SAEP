package br.inf.ufg.persistencia.mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public abstract class RegraMixin extends Regra {

  @JsonCreator
  public RegraMixin(@JsonProperty("variavel") String variavel,
                    @JsonProperty("tipo") int tipo,
                    @JsonProperty("descricao") String descricao,
                    @JsonProperty("valorMaximo") float valorMaximo,
                    @JsonProperty("valorMinimo") float valorMinimo,
                    @JsonProperty("expressao") String expressao,
                    @JsonProperty("entao") String entao,
                    @JsonProperty("senao") String senao,
                    @JsonProperty("tipoRelato") String tipoRelato,
                    @JsonProperty("pontosPorItem") float pontosPorItem,
                    @JsonProperty("dependeDe") List<String> dependeDe) {
    super(variavel, tipo, descricao, valorMaximo, valorMinimo, expressao,
      entao, senao, tipoRelato, pontosPorItem, dependeDe);
  }

  @JsonProperty("tipoRelato")
  @Override
  public abstract String getTipoRelato();
}
