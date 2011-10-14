package org.zkoss.mongodb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.mongodb.MongoDBManager;
import org.zkoss.mongodb.model.Task;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public class TaskDAO {

	public List<Task> findAll(){
		List<Task> tasks = new ArrayList<Task>();
		try {
			DB db = MongoDBManager.getDB("simpletasks");
			DBCollection coll = db.getCollection("Tasks");
			DBCursor cursor = coll.find();
			
			while (cursor.hasNext()) {
				DBObject dbobj = cursor.next();
				Task task = new Task((String) dbobj.get("id"),
						(String) dbobj.get("name"),
						(Integer) dbobj.get("priority"),
						(Date) dbobj.get("date"));
				tasks.add(task);
			}
			
		} catch (Exception e) {}
		return tasks;
	}
	
	public void delete(Task task){
		try {
			BasicDBObject newdbobj = toBasicDBObject(task);
			DB db = MongoDBManager.getDB("simpletasks");
			DBCollection coll = db.getCollection("Tasks");
			coll.remove(newdbobj);
		} catch (Exception e) {}
	}

	private BasicDBObject toBasicDBObject(Task task) {
		BasicDBObject newdbobj = new BasicDBObject();
		newdbobj.put("id", task.getId());
		newdbobj.put("name", task.getName());
		newdbobj.put("priority", task.getPriority());
		newdbobj.put("date", task.getExecutionDate());
		return newdbobj;
	}
	
	public void insert(Task task){
		try {
			BasicDBObject newdbobj = toBasicDBObject(task);
			DB db = MongoDBManager.getDB("simpletasks");
			DBCollection coll = db.getCollection("Tasks");
			coll.save(newdbobj);
		} catch (Exception e) {}
	}
	
	public void update(Task task){
		try {
			BasicDBObject searchobj = new BasicDBObject();
			searchobj.put("id", task.getId());
			BasicDBObject newdbobj = toBasicDBObject(task);
			DB db = MongoDBManager.getDB("simpletasks");
			DBCollection coll = db.getCollection("Tasks");
			coll.update(searchobj, newdbobj);
		} catch (Exception e) {}
	}
}
