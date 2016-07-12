package br.inf.ufg.persistencia.database;

import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * Classe para encapsular uma conexão com o banco de dados, seja real ou falsificado.
 */
public class MongoConnection {

  private static MongoClient mongo = null;
  private static final String DB_NAME = "saep";

  /**
   * Pega uma conexão válida para uma instância do mongo real.
   *
   * @return uma conexão real com o Mongo.
   */
  public static DB getDBConnection() {
    DB db = null;
    //Conexões ao mongo propriamente dito.
    try {
      // Rodando no Docker
      //mongo = new MongoClient("172.17.0.1", 27017);
      // Rodando no travis/local
      mongo = new MongoClient("localhost", 27017);
      db = mongo.getDB(DB_NAME);
    } catch (UnknownHostException e) {
      e.printStackTrace();
      throw new SecurityException("Não consegui abrir o banco de dados.", e);
    }
    if (db == null) {
      throw new SecurityException("Não consegui abrir o banco de dados.");
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

}
