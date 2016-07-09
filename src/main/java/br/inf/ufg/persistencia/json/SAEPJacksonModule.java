package br.inf.ufg.persistencia.json;

import br.inf.ufg.persistencia.mixin.*;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.mongojack.internal.MongoJackModule;

/**
 * Módulo customizado do Jackson para registrar configurações específicas.
 */
public class SAEPJacksonModule extends SimpleModule {

  public SAEPJacksonModule() {
    super();
  }

  @Override
  public void setupModule(SetupContext context) {
    super.setupModule(context);
    context.setMixInAnnotations(Resolucao.class, ResolucaoMixin.class);
    context.setMixInAnnotations(Regra.class, RegraMixin.class);
    context.setMixInAnnotations(Parecer.class, ParecerMixin.class);
    context.setMixInAnnotations(Pontuacao.class, PontuacaoMixin.class);
    context.setMixInAnnotations(Valor.class, ValorMixin.class);
  }

  /**
   * Referência estática que cria um novo ObjectMapper do Jackson com as configurações necessárias.
   * @return um ObjectMapper pronto para serializar as classes do modelo atual.
   */
  public static ObjectMapper createSAEPObjectMapper(){
    SAEPJacksonModule saepJacksonModule = new SAEPJacksonModule();
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(saepJacksonModule);
    MongoJackModule.configure(mapper);
    return mapper;
  }

}
