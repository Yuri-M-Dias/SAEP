package br.inf.ufg.persistencia.repository;

import br.ufg.inf.es.saep.sandbox.dominio.*;

import java.util.*;

/**
 * Used to aggregate various different generators used accross the tests.
 */
public class UtilsGenerator {

  public static Parecer criaParecer(String identificador) {
    List<String> radocsIDs = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String radocId = UUID.randomUUID().toString();
      radocsIDs.add(radocId);
    }
    List<Pontuacao> pontuacoes = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String atributo = UUID.randomUUID().toString();
      pontuacoes.add(geraPontuacao(atributo));
    }
    List<Nota> notas = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String atributo = UUID.randomUUID().toString();
      notas.add(geraNota(atributo));
    }
    return new Parecer(identificador, "resolucao", radocsIDs, pontuacoes, "fundamentacao", notas);
  }

  public static Pontuacao geraPontuacao(String atributo) {
    Valor valor = new Valor(atributo);
    return new Pontuacao(atributo, valor);
  }

  public static Nota geraNota(String atributo) {
    Valor valor = new Valor(atributo);
    Avaliavel novo = new Pontuacao(atributo, valor);
    Valor valorNum = new Valor(true);
    Map<String, Valor> map = new HashMap<>();
    map.put("a key", valorNum);
    Avaliavel antigo = new Relato(atributo, map);
    return new Nota(antigo, novo, "uma descricao");
  }

  public static Nota geraNota(Pontuacao pontuacao) {
    Avaliavel novo = pontuacao;
    Valor valorNum = new Valor(true);
    Map<String, Valor> map = new HashMap<>();
    map.put("a key", valorNum);
    String atributo = UUID.randomUUID().toString();
    Avaliavel antigo = new Relato(atributo, map);
    return new Nota(novo, antigo, "uma descricao");
  }

  public static Tipo criaTipo(String id, String namePrepend, String nameAppend) {
    Set<Atributo> atributos = new HashSet<>();
    for (int i = 0; i < 5; i++) {
      atributos.add(new Atributo(String.valueOf(i), "um atributo", 1));
    }
    return new Tipo(id, namePrepend + "-" + id + "-" + nameAppend, "É um tipo.", atributos);
  }

  public static Resolucao criaResolucaoQueUsaTipo(String id, String identificadorTipo) {
    return new Resolucao(id, "123", "Uma resolução.", new Date(), criaRegras(identificadorTipo));
  }

  public static List<Regra> criaRegras(String identifcadorTipo) {
    List<Regra> regras = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String placeholder = "regra-" + i;
      List<String> dependeDe = new ArrayList<>();
      dependeDe.add("alguma coisa");
      //Tipo 0 pra salvar o id
      Regra regra = new Regra(placeholder, 0, placeholder, 20, 5, placeholder, placeholder, placeholder,
        identifcadorTipo, 5, dependeDe);
      regras.add(regra);
    }
    return regras;
  }

  public static Resolucao criaResolucao(String id) {
    return new Resolucao(id, "123", "Uma resolução.", new Date(), criaRegras());
  }

  public static List<Regra> criaRegras() {
    List<Regra> regras = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String placeholder = "regra-" + i;
      List<String> dependeDe = new ArrayList<>();
      dependeDe.add("nada");
      Random rand = new Random();
      int tipoRegra = rand.nextInt(5);
      Regra regra = new Regra(placeholder, tipoRegra, placeholder, 20, 5,
        placeholder, placeholder, placeholder, placeholder, 5, dependeDe);
      regras.add(regra);
    }
    return regras;
  }

  public static Radoc criaRadoc(String identificador) {
    List<Relato> relatos = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Map<String, Valor> valores = new HashMap<>();
      for (int j = 0; j < 5; j++) {
        String tipoValor = UUID.randomUUID().toString();
        Valor valor = new Valor(j + 1);
        valores.put(tipoValor, valor);
      }
      Relato relato = new Relato(UUID.randomUUID().toString(), valores);
      relatos.add(relato);
    }
    return new Radoc(identificador, 2015, relatos);
  }

  public static Parecer criaParecer(String identificador, String idRadoc) {
    List<String> radocsIDs = new ArrayList<>();
    radocsIDs.add(idRadoc);
    for (int i = 0; i < 5; i++) {
      String radocId = UUID.randomUUID().toString();
      radocsIDs.add(radocId);
    }
    List<Pontuacao> pontuacoes = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String atributo = UUID.randomUUID().toString();
      pontuacoes.add(geraPontuacao(atributo));
    }
    List<Nota> notas = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String atributo = UUID.randomUUID().toString();
      notas.add(geraNota(atributo));
    }
    return new Parecer(identificador, "resolucao", radocsIDs, pontuacoes, "fundamentacao", notas);
  }

}

