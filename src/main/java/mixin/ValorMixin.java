package mixin;

import br.ufg.inf.es.saep.sandbox.dominio.Valor;

public abstract class ValorMixin extends Valor {

  public ValorMixin(String valor) {
    super(valor);
  }

  public ValorMixin(float valor) {
    super(valor);
  }

  public ValorMixin(boolean valor) {
    super(valor);
  }

}
