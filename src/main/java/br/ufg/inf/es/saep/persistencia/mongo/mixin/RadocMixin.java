package br.ufg.inf.es.saep.persistencia.mongo.mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongojack.Id;
import org.mongojack.ObjectId;

import java.util.List;

public abstract class RadocMixin extends Radoc {

  @JsonCreator
  public RadocMixin(@Id @ObjectId String id,
                    @JsonProperty("anoBase") int anoBase,
                    @JsonProperty("relatos") List<Relato> relatos) {
    super(id, anoBase, relatos);
  }

}
