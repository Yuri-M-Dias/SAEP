package framework;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import json.SAEPJacksonModule;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

/**
 * Encapsula operações em comum entre os repositórios.
 */
public abstract class BaseMongoRepository<T> {

  protected String collectionName;
  protected DB mongoDatabase;
  protected DBCollection mongoCollection;
  // Assumindo o _Id como String
  protected JacksonDBCollection<T, String> jacksonCollection;
  protected Class currentGenericClass;

  protected BaseMongoRepository(String collectionName, DB mongoDatabase, Class clazz){
    this.currentGenericClass = clazz;
    this.collectionName = collectionName;
    this.mongoDatabase = mongoDatabase;
    // Cria a coleção sozinho se ela não existir
    this.mongoCollection = mongoDatabase.getCollection(collectionName);
    this.jacksonCollection = JacksonDBCollection.wrap(this.mongoCollection, this.currentGenericClass, String
      .class, SAEPJacksonModule.createSAEPObjectMapper());
  }

  protected T create(T elemento) {
    WriteResult<T, String> result = jacksonCollection.insert(elemento);
    T objetoSalvo = result.getSavedObject();
    return objetoSalvo;
  }

  protected T findById(String id){
    T element = jacksonCollection.findOneById(id);
    return element;
  }

  protected T update(String idAntigo, T novoElemento){
    WriteResult<T, String> result  = jacksonCollection.updateById(idAntigo, novoElemento);
    T objetoAtualizado = result.getSavedObject();
    return objetoAtualizado;
  }

  protected void delete(String id) {
    WriteResult<T, String> result = jacksonCollection.removeById(id);
    //return objetoDeletado;
  }

  /**
   * Pega a classe genérica instanciada atualmente.
   *
   * @return a classe T anotada para essa classe base
   */
  protected Class getCurrentGenericClass(){
    return currentGenericClass;
  }

}
