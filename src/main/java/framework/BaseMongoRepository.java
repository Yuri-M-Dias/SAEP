package framework;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

/**
 * Encapsula operações em comum entre os repositórios.
 */
public abstract class BaseMongoRepository<T> {

  protected String collectionName;
  protected DB mongoDatabase;
  protected DBCollection mongoCollection;
  // Assumindo o Id como String
  protected JacksonDBCollection<T, String> jacksonCollection;

  public BaseMongoRepository(String collectionName, DB mongoDatabase, Class clazz){
    this.collectionName = collectionName;
    this.mongoDatabase = mongoDatabase;
    // Cria a coleção sozinho se ela não existir
    this.mongoCollection = mongoDatabase.getCollection(collectionName);
    this.jacksonCollection = JacksonDBCollection.wrap(this.mongoCollection, clazz, String
      .class);
  }

  public T create(T elemento) {
    WriteResult<T, String> result = jacksonCollection.insert(elemento);
    T objetoSalvo = result.getSavedObject();
    return objetoSalvo;
  }

  public T findById(String id){
    T element = jacksonCollection.findOneById(id);
    return element;
  }

  public T update(String idAntigo, T novoElemento){
    WriteResult<T, String> result  = jacksonCollection.updateById(idAntigo, novoElemento);
    T objetoAtualizado = result.getSavedObject();
    return objetoAtualizado;
  }

  public T delete(String id) {
    WriteResult<T, String> result = jacksonCollection.removeById(id);
    T objetoDeletado = result.getSavedObject();
    return objetoDeletado;
  }

}
