package br.inf.ufg.persistencia.framework;

import br.inf.ufg.persistencia.json.SAEPJacksonModule;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

/**
 * Encapsula operações em comum entre os diferentes DAOs, deixando uma
 * implementação padrão para as operações mais repetitivas.
 */
public abstract class BaseMongoDAO<T> {

  protected String collectionName;
  protected DB mongoDatabase;
  protected DBCollection mongoCollection;
  // Assumindo o _Id como String
  protected JacksonDBCollection<T, String> jacksonCollection;
  protected Class currentGenericClass;

  protected BaseMongoDAO(String collectionName, DB mongoDatabase, Class clazz) {
    this.currentGenericClass = clazz;
    this.collectionName = collectionName;
    this.mongoDatabase = mongoDatabase;
    // Cria a coleção sozinho se ela não existir
    this.mongoCollection = mongoDatabase.getCollection(collectionName);
    this.jacksonCollection =
      JacksonDBCollection.wrap(this.mongoCollection,
        this.currentGenericClass, String.class,
        SAEPJacksonModule.createSAEPObjectMapper());
    //Assume que toda coleção tem um campo "id" para ser unique.
    BasicDBObject idIndex = new BasicDBObject("id", 1);
    BasicDBObject idIndexOptions = new BasicDBObject("unique", "true");
    jacksonCollection.createIndex(idIndex, idIndexOptions);
  }

  public T create(T elemento) {
    WriteResult<T, String> result = jacksonCollection.insert(elemento);
    T objetoSalvo = result.getSavedObject();
    return objetoSalvo;
  }

  public T findById(String id) {
    T element = jacksonCollection.findOne(DBQuery.is("id", id));
    return element;
  }

  public void delete(String id) {
    T radoc = jacksonCollection.findAndRemove(DBQuery.is("id", id));
    //return objetoDeletado;
  }

  /**
   * Pega a classe genérica instanciada atualmente.
   *
   * @return a classe T anotada para essa classe base
   */
  public Class getCurrentGenericClass() {
    return currentGenericClass;
  }

}
