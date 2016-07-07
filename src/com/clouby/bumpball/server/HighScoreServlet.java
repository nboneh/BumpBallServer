package com.clouby.bumpball.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.clouby.bumpball.server.HighScoreUtil.HighScoreContainer;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.JSONException;



public class HighScoreServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3651704760517377444L;
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
			e.printStackTrace();
		}
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		List<HighScoreUtil.HighScoreContainer> containers = HighScoreUtil.getCurrentHighScores();
		JSONArray entities = new JSONArray();

		for (HighScoreContainer container :containers) {
			try {
				JSONObject jsonEntity = new JSONObject(); 
				jsonEntity.put("score", container.getScore());
				jsonEntity.put("name", container.getName());
				entities.put(jsonEntity);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		
		try {
			entities.write(resp.getWriter());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		if(req.getParameter("pass").equals(PASSWORD) ){
			HighScoreUtil.insertNewHighScore(Integer.parseInt(req.getParameter("score")),req.getParameter("name"));
		}
	}

}