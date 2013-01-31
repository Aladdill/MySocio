package net.mysocio.routes.reader;

import net.mysocio.data.CappedCollectionTimeStamp;
import net.mysocio.data.management.DataManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoCappedCollectionReader implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(MongoCappedCollectionReader.class);
	private final DB db;
	private final String collection;
	private final CappedCollectionProcessor processor;
	public MongoCappedCollectionReader(final DB db, String collection, CappedCollectionProcessor processor) {
		this.db = db;
		this.collection = collection;
		this.processor = processor;
	}
	@Override
	public void run() {
		CappedCollectionTimeStamp timestamp = DataManagerFactory.getDataManager().getTimestamp(collection);
		if (timestamp == null){
			timestamp = new CappedCollectionTimeStamp();
			timestamp.setCollection(collection);
			timestamp.setTimestamp(0);
		} 

		while (true) {
			try {
				db.requestStart();
				final DBCursor cur = createCursor(timestamp.getTimestamp());
				try {
					while (cur.hasNext()) {
						DBObject object = cur.next();
						logger.debug("Got object from collection " + collection + object.toString());
						long lastTimestamp = processor.process(object);
						timestamp.setTimestamp(lastTimestamp);
						DataManagerFactory.getDataManager().saveObject(timestamp);
					}
				} finally {
					try {
						if (cur != null)
							cur.close();
					} catch (final Throwable t) { /* nada */
					}
					db.requestDone();
				}

				try {
					Thread.sleep(100);
				} catch (final InterruptedException ie) {
					break;
				}
			} catch (final Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private DBCursor createCursor(final long pLast) {
		final DBCollection routesCollection = db.getCollection(collection);

		if (pLast == 0) {
			return routesCollection.find()
					.sort(new BasicDBObject("$natural", 1))
					.addOption(Bytes.QUERYOPTION_TAILABLE)
					.addOption(Bytes.QUERYOPTION_AWAITDATA);
		}

		final BasicDBObject query = new BasicDBObject("creationDate",
				new BasicDBObject("$gt", pLast));
		return routesCollection.find(query)
				.sort(new BasicDBObject("$natural", 1))
				.addOption(Bytes.QUERYOPTION_TAILABLE)
				.addOption(Bytes.QUERYOPTION_AWAITDATA);
	}
}
