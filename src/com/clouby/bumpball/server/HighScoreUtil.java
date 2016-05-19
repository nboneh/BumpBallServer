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
			int score = (int) result.getProperty("score");
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
		
		//datastore.delete(pq);

	}



}
