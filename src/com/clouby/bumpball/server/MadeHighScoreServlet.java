package com.clouby.bumpball.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class MadeHighScoreServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5942610502616434789L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		JSONObject jsonEntity = new JSONObject(); 
		try {
			jsonEntity.put("rank", 	HighScoreUtil.madeOnlineHighScore(Integer.parseInt(req.getParameter("score"))));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			jsonEntity.write(resp.getWriter());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}