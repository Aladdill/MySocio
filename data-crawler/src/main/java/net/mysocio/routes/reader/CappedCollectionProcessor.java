package net.mysocio.routes.reader;

import net.mysocio.data.management.DataManagerFactory;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

public abstract class CappedCollectionProcessor {
	abstract public long process(Object object) throws Exception;
	public<T> T getObject(Class<T> T, Object object){
		ObjectId objectId = ((BasicDBObject)object).getObjectId("_id");
		return DataManagerFactory.getDataManager().getObject(T, objectId.toString());
	}
}
