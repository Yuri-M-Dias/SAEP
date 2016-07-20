package br.ufg.inf.es.saep.persistencia.mongo.mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongojack.Id;
import org.mongojack.ObjectId;

import java.util.List;

public abstract class ParecerMixin extends Parecer {

  @JsonCreator
  public ParecerMixin(@Id @ObjectId String id,
                      @JsonProperty("resolucaoId") String resolucaoId,
                      @JsonProperty("radocsIds") List<String> radocsIds,
                      @JsonProperty("pontuacoes") List<Pontuacao> pontuacoes,
                      @JsonProperty("fundamentacao") String fundamentacao,
                      @JsonProperty("notas") List<Nota> notas) {
    super(id, resolucaoId, radocsIds, pontuacoes, fundamentacao, notas);
  }

}
