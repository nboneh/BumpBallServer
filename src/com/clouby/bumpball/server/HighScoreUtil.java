package com.clouby.bumpball.server;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class HighScoreUtil {
	static final String ENTITY_NAME = "HighScoreRecord";
	public static class HighScoreContainer{
		private String name;
		private long time;
		private int score;
		
		public HighScoreContainer(){
			//Default values in case is not stored
			name = "Clouby";
			time = System.currentTimeMillis();
			score = 0;
		}


		public String getName() {
			return name;
		}
		public long getTime() {
			return time;
		}
		public int getScore() {
			return score;
		}
	}

	public static HighScoreContainer getCurrentHighScore() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(ENTITY_NAME);
		PreparedQuery pq = datastore.prepare(query);

		HighScoreContainer container = new HighScoreContainer();


		for (Entity result : pq.asIterable()) {
			int score = ((Long) result.getProperty("score")).intValue();
			String name  = (String) result.getProperty("name");
			long time  = (long) result.getProperty("time");
			
			container.score = score;
			container.time = time;
			container.name = name;
		}
		return container;
	}
	
	public static void deleteCurrentHighscore(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(ENTITY_NAME);
		PreparedQuery pq = datastore.prepare(query);
		
		for (Entity result : pq.asIterable()) {
			datastore.delete(result.getKey());
		}

	}
	
	public static void insertNewHighScore(int score, String name){
		// Places the location parameters in the same entity group as the Location record
		Entity highscoreInst = new Entity(ENTITY_NAME);


		highscoreInst.setProperty("time", System.currentTimeMillis());
		highscoreInst.setProperty("name", name);
		highscoreInst.setProperty("score", score);

		// Now put the entry to Google data store
		DatastoreService datastore =
				DatastoreServiceFactory.getDatastoreService();
		datastore.put(highscoreInst);
	}



}
