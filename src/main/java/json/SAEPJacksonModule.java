package json;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import mixin.*;
import org.mongojack.internal.MongoJackModule;

public class SAEPJacksonModule extends SimpleModule {

  public SAEPJacksonModule() {
    super("SAEP-Module", new Version(0,0,1,null));
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

  public static ObjectMapper createSAEPObjectMapper(){
    SAEPJacksonModule saepJacksonModule = new SAEPJacksonModule();
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(saepJacksonModule);
    MongoJackModule.configure(mapper);
    return mapper;
  }

}
