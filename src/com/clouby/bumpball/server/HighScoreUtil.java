package com.clouby.bumpball.server;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;

public class HighScoreUtil {
	static final String ENTITY_NAME = "HighScoreRecord";
	private static final int MAX_RESULTS = 10;
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

	public static List<HighScoreContainer> getCurrentHighScores() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(ENTITY_NAME);
		query.addSort("score", SortDirection.DESCENDING);
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(MAX_RESULTS);
		PreparedQuery pq = datastore.prepare(query);

		List<HighScoreContainer> containers = new ArrayList<HighScoreContainer>();
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		int i = 0;
		for (Entity result :results) {
			HighScoreContainer container = new HighScoreContainer();
			int score = ((Long) result.getProperty("score")).intValue();
			String name  = (String) result.getProperty("name");
			long time  = (long) result.getProperty("time");
			
			container.score = score;
			container.time = time;
			container.name = name;
			containers.add(container);
			i++;
		}
		while(i < MAX_RESULTS){
			//Defaults
			HighScoreContainer container = new HighScoreContainer();
			container.score = 0;
			container.time = System.currentTimeMillis();
			container.name = "Clouby";
			containers.add(container);
			i++;
		}
		return containers;
	}
	
	public static void deleteOnlineHighScores(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(ENTITY_NAME);
		PreparedQuery pq = datastore.prepare(query);
		
		for (Entity result : pq.asIterable()) {
			datastore.delete(result.getKey());
		}

	}
	
	public static int madeOnlineHighScore(int score){
		List<HighScoreContainer> containers =  getCurrentHighScores();

		int i = 1;
		for (HighScoreContainer container :containers) {
			int compScore = container.getScore();
			if(score > compScore)
				return i;
			i++;
		}
		return -1;
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
