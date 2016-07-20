package br.ufg.inf.es.saep.persistencia.mongo.json;

import br.ufg.inf.es.saep.persistencia.mongo.mixin.*;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.mongojack.internal.MongoJackModule;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

/**
 * Módulo customizado do Jackson para registrar configurações específicas.
 */
public class SAEPJacksonModule extends SimpleModule {

  public SAEPJacksonModule() {
    super();
  }

  /**
   * Referência estática que cria um novo ObjectMapper do Jackson com as
   * configurações necessárias.
   *
   * @return um ObjectMapper pronto para serializar as classes do modelo
   * atual.
   */
  public static ObjectMapper createSAEPObjectMapper() {
    SAEPJacksonModule saepJacksonModule = new SAEPJacksonModule();
    saepJacksonModule.addSerializer(Valor.class, new ValorSerializer());
    saepJacksonModule.addDeserializer(Valor.class, new ValorDeserialzer());
    saepJacksonModule.addDeserializer(Avaliavel.class, new
      AvaliavelDeserializer());
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(saepJacksonModule);
    mapper.setVisibility(FIELD, ANY);
    MongoJackModule.configure(mapper);
    return mapper;
  }

  @Override
  public void setupModule(SetupContext context) {
    super.setupModule(context);
    context.setMixInAnnotations(Resolucao.class, ResolucaoMixin.class);
    context.setMixInAnnotations(Regra.class, RegraMixin.class);
    context.setMixInAnnotations(Pontuacao.class, PontuacaoMixin.class);
    context.setMixInAnnotations(Atributo.class, AtributoMixin.class);
    context.setMixInAnnotations(Tipo.class, TipoMixin.class);

    context.setMixInAnnotations(Parecer.class, ParecerMixin.class);
    context.setMixInAnnotations(Radoc.class, RadocMixin.class);
    context.setMixInAnnotations(Relato.class, RelatoMixin.class);
    context.setMixInAnnotations(Nota.class, NotaMixin.class);

  }

}
