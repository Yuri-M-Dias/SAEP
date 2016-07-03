package repository;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class MongoConnection {

  public static DB getDBConnection(){
    // Rodando no Docker
    MongoClient mongo = null;
    try {
      mongo = new MongoClient("172.17.0.1", 27017);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    if(mongo == null){
      throw new SecurityException("Não consegui abrir o banco de dados.");
    }
    // if database doesn't exists, MongoDB will create it for you
    DB db = mongo.getDB("saep");
    return db;
  }

  public DB getDBConnection(String collectionName){
    return null;
  }

}
