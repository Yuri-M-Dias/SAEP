package br.inf.ufg.persistencia.database;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class MongoConnection {

  private static MongoClient mongo = null;
  private static final String DB_NAME = "saep";

  public static DB getDBConnection() {
    try {
      // Rodando no Docker
      //mongo = new MongoClient("172.17.0.1", 27017);
      // Rodando no travis/local
      mongo = new MongoClient("localhost", 27017);
    } catch (UnknownHostException e) {
      e.printStackTrace();
      throw new SecurityException("Não consegui abrir o banco de dados.", e);
    }
    if (mongo == null) {
      throw new SecurityException("Não consegui abrir o banco de dados.");
    }
    // if br.inf.ufg.persistencia.database doesn't exists, MongoDB will create it for you
    DB db = mongo.getDB(DB_NAME);
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
