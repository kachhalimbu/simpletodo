/**
 * 
 */
package org.zkoss.mongodb;

import com.mongodb.DB;
import com.mongodb.Mongo;

/**
 * @author Ashish
 *
 */
public class MongoDBManager {

	private static Mongo mongo = null;
	private MongoDBManager() {};
	
	public static synchronized DB getDB(String dbName) throws Exception {
		if(mongo == null) {
			mongo = new Mongo();
		} 
		return mongo.getDB(dbName);
	}
}
