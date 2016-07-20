package br.ufg.inf.es.saep.persistencia.mongo.database;

import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Classe para encapsular uma conexão com o banco de dados, seja real ou
 * falsificado.
 */
public class MongoConnection {

  private static String URL_BANCO;
  private static String DB_NAME;
  private static MongoClient mongo = null;
  private static String PORTA_BANCO;
  private static String FONGO;

  /**
   * Pega uma conexão válida para uma instância do mongo real.
   *
   * @return uma conexão real com o Mongo.
   */
  public static DB getDBConnection() {
    try {
      leArquivoConfiguracao();
    } catch (IOException e) {
      e.printStackTrace();
      throw new SecurityException("Não consegui abrir a conexão com o banco " +
        "de dados.");
    }
    DB db = null;
    try {
      mongo = new MongoClient(URL_BANCO, Integer.parseInt(PORTA_BANCO));
      db = mongo.getDB(DB_NAME);
    } catch (UnknownHostException e) {
      e.printStackTrace();
      throw new SecurityException("Não consegui abrir o banco de dados.", e);
    }
    if (db == null) {
      throw new SecurityException("Não consegui abrir a conexão com o banco " +
        "de dados.");
    }
    return db;
  }

  /**
   * Utilizado para testes in-memory, sem instância do mongo.
   *
   * @return uma conexão válida para o BD, porém falsificada.
   */
  public static DB getFakedDBConnection() {
    DB db = null;
    //Database mock
    mongo = new Fongo("mongo test server").getMongo();
    db = mongo.getDB(DB_NAME);
    return db;
  }

  public static void closeDBConnection() {
    mongo.close();
  }

  public static void createDB() {
    mongo.getDB(DB_NAME);
  }

  public static void deleteDB() {
    mongo.dropDatabase(DB_NAME);
  }

  private static void leArquivoConfiguracao() throws IOException {
    Properties prop = new Properties();
    String propFileName = "config.properties";
    InputStream inputStream = MongoConnection.class.getClassLoader()
      .getResourceAsStream(propFileName);
    if (inputStream != null) {
      prop.load(inputStream);
    } else {
      throw new FileNotFoundException("Arquivo de configuração não está " +
        "presente.");
    }
    DB_NAME = prop.getProperty("nomeBanco");
    URL_BANCO = prop.getProperty("urlBanco");
    PORTA_BANCO = prop.getProperty("portaBanco");
    FONGO = prop.getProperty("fongo");
  }

}
