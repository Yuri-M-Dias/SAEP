package br.inf.ufg.persistencia.json;

import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ValorDeserialzer extends JsonDeserializer<Valor> {

  @Override
  public Valor deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    Valor valor = null;
    JsonNode node = p.getCodec().readTree(p);
    Float floatValue = node.get("real").floatValue();
    Boolean aBoolean = node.get("logico").asBoolean();
    String aString = node.get("string").asText();

    valor = new Valor(floatValue);
    if(aBoolean){
      valor = new Valor(aBoolean);
      return valor;
    } else if(!"".equals(aString) && !"null".equals(aString)){
      valor = new Valor(aString);
      return valor;
    }
    return valor;
  }

}
