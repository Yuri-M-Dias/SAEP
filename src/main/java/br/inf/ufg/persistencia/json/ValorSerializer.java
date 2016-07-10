package br.inf.ufg.persistencia.json;

import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by yuri on 10/07/16.
 */
public class ValorSerializer extends JsonSerializer<Valor> {

  @Override
  public void serialize(Valor value, JsonGenerator gen, SerializerProvider serializers)
    throws IOException, JsonProcessingException {
    gen.writeStartObject();
    gen.writeNumberField("real", value.getFloat());
    gen.writeBooleanField("logico", value.getBoolean());
    gen.writeStringField("string", value.getString());
    gen.writeEndObject();
  }

}
