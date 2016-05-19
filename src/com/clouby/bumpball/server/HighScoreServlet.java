package com.clouby.bumpball.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.JSONException;



public class HighScoreServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3651704760517377444L;
	static final String ENTITY_NAME = "HighScoreRecord";
	static  String PASSWORD = "";

	public void init() {
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader("password.txt"));
			String line = br.readLine();

			if (line != null) {
				PASSWORD = line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HighScoreUtil.HighScoreContainer container = HighScoreUtil.getCurrentHighScore();
		JSONObject jsonEntity = new JSONObject(); 

		try {
			jsonEntity.put("score", container.getScore());
			jsonEntity.put("name", container.getName());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			jsonEntity.write(resp.getWriter());
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		if(req.getParameter("pass").equals(PASSWORD)){
			// Places the location parameters in the same entity group as the Location record
			Entity highscoreInst = new Entity(ENTITY_NAME);


			highscoreInst.setProperty("date", new Date(Long.parseLong(req.getParameter("date"))));
			highscoreInst.setProperty("name", req.getParameter("name"));
			highscoreInst.setProperty("score", Long.parseLong(req.getParameter("score")));

			// Now put the entry to Google data store
			DatastoreService datastore =
					DatastoreServiceFactory.getDatastoreService();
			datastore.put(highscoreInst);
		}
	}

}