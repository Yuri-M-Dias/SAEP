package br.ufg.inf.es.saep.persistencia.mongo.json;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class AvaliavelDeserializer extends JsonDeserializer<Avaliavel> {

  @Override
  public Avaliavel deserialize(JsonParser p, DeserializationContext ctxt) throws
    IOException, JsonProcessingException {
    Avaliavel result = null;
    ObjectMapper mapper = SAEPJacksonModule.createSAEPObjectMapper();
    JsonNode node = p.getCodec().readTree(p);
    JsonNode atributo = node.get("atributo");
    if (atributo != null) {
      // É uma pontuacao
      Pontuacao pontuacao = mapper.readValue(node.toString(), Pontuacao.class);
      result = pontuacao;
    } else {
      JsonNode tipo = node.get("tipo");
      if (tipo != null) {
        // É um relato
        Relato relato = mapper.readValue(node.toString(), Relato.class);
        result = relato;
      }
    }
    return result;
  }

}
