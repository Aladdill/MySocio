/**
 * 
 */
package net.mysocio.data.management;

import java.net.UnknownHostException;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;

/**
 * @author Aladdin
 *
 */
public class AbstractMongoInitializer {
	public static final String ROUTE_PACKAGES = "route_packages";
	public static final String TEMP_USER_PROCESSORS = "temp_user_processors";
	public static final String PROCESSORS_DB_SERVER_ADMIN_PASSWORD = "processors.db.server.admin.password";
	public static final String PROCESSORS_DB_SERVER_ADDRESS = "processors.db.server.address";
	public static final String PROCESSORS_DB_SERVER_PORT = "processors.db.server.port";
	public static final String PROCESSORS_DB_SERVER_DB_NAME = "processors.db.server.db.name";
	public static final String PROCESSORS_DB_SERVER_ADMIN_USERNAME = "processors.db.server.admin.username";
	public static final String DB_SERVER_ADMIN_PASSWORD = "db.server.admin.password";
	public static final String DB_SERVER_ADDRESS = "db.server.address";
	public static final String DB_SERVER_PORT = "db.server.port";
	public static final String DB_SERVER_DB_NAME = "db.server.db.name";
	public static final String DB_SERVER_ADMIN_USERNAME = "db.server.admin.username";
	
	public static DB getMongoDatabaseAndInitCappedCollections(String dbUser, String dbName,	String dbPort, String dbServer, String dbPass) throws UnknownHostException{
		DB db = getMongoDatabase(dbUser, dbName, dbPort, dbServer, dbPass);
		initCappedCollections(db);
		return db;
	}

	public static DB getMongoDatabase(String dbUser, String dbName, String dbPort,
			String dbServer, String dbPass) throws UnknownHostException {
		MongoURI uri = new MongoURI("mongodb://" + AuthenticationResourcesManager.getResource(dbServer) + 
				":" + AuthenticationResourcesManager.getResource(dbPort));
		Mongo connectionBean = new Mongo(uri);
		DB db = connectionBean.getDB(AuthenticationResourcesManager.getResource(dbName));
		db.authenticate(AuthenticationResourcesManager.getResource(dbUser), 
				AuthenticationResourcesManager.getResource(dbPass).toCharArray());
		return db;
	}

	public static void initCappedCollections(DB db) {
		if (!db.collectionExists(ROUTE_PACKAGES)){
			DBObject options = BasicDBObjectBuilder.start().add("capped", true).add("size", 10000000).get();
			db.createCollection(ROUTE_PACKAGES, options);
		}
		if (!db.collectionExists(TEMP_USER_PROCESSORS)){
			DBObject options = BasicDBObjectBuilder.start().add("capped", true).add("size", 10000000).get();
			db.createCollection(TEMP_USER_PROCESSORS, options);
		}
	}

	public static Datastore getMongoDatastore(String dbUser, String dbName,
			String dbPort, String dbServer, String dbPass)
			throws UnknownHostException {
		Datastore ds = new Morphia().createDatastore(new Mongo(
				AuthenticationResourcesManager.getResource(dbServer),
				Integer.parseInt(AuthenticationResourcesManager.getResource(dbPort))),
				AuthenticationResourcesManager.getResource(dbName),
				AuthenticationResourcesManager.getResource(dbUser),
				AuthenticationResourcesManager.getResource(dbPass).toCharArray());
		ds.ensureCaps();
		ds.ensureIndexes();
		return ds;
	}

}
