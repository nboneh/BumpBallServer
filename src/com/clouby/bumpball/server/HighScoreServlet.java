package com.clouby.bumpball.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		int score = Integer.parseInt(req.getParameter("score"));
		int currentHighScore =  HighScoreUtil.getCurrentHighScore().getScore();
		if(req.getParameter("pass").equals(PASSWORD) && score > currentHighScore){
			HighScoreUtil.deleteCurrentHighscore();
			HighScoreUtil.insertNewHighScore(score,req.getParameter("name"));
		}
	}

}