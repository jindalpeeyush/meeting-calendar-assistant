package services.db;

import javax.inject.Provider;
import javax.inject.Singleton;
import javax.inject.Inject;

import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.DefaultCreator;

import vo.CalendarVo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;


@Singleton
public class Mongo {

	private MongoClient mongoClient;
	public final AdvancedDatastore datastore;

	private static final String MONGO_PATH = "mongodb://localhost:27017";
	private static final String MONGO_DATABASE = "peeyush";
 	
	@Inject
    public Mongo(Provider<play.Application> appProvider) {
        this.mongoClient = new MongoClient(new MongoClientURI(MONGO_PATH));
        final Morphia morphia = new Morphia();
        
		morphia.mapPackageFromClass(CalendarVo.class);
		morphia.getMapper().getOptions().setObjectFactory(new DefaultCreator() {
	    @Override
	    protected ClassLoader getClassLoaderForClass() {
	    	return appProvider.get().classloader();
	    }
		});
		
		morphia.getMapper().getOptions().setStoreEmpties(true);
		morphia.getMapper().getOptions().setStoreNulls(true);
		datastore = (AdvancedDatastore) morphia.createDatastore(this.mongoClient, MONGO_DATABASE);
		datastore.ensureIndexes();
    }
	
	public void connect() {
		  MongoIterable<String> strings=this.mongoClient.listDatabaseNames();
		  MongoCursor<String> iterator=strings.iterator();
		  while (iterator.hasNext()) {
		    System.out.println(iterator.tryNext());
		  }
	}
	
	public MongoClient getMongoClient() {
		return mongoClient;
	}
}
