package br.inf.ufg.persistencia.mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongojack.Id;
import org.mongojack.ObjectId;

import java.util.Set;

public class TipoMixin extends Tipo {

  @JsonCreator
  public TipoMixin(@Id @ObjectId String id,
                   @JsonProperty("nome") String nome,
                   @JsonProperty("descricao") String descricao,
                   @JsonProperty("atributos") Set<Atributo> atributos) {
    super(id, nome, descricao, atributos);
  }

}
