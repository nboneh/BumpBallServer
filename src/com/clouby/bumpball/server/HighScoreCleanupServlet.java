package com.clouby.bumpball.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HighScoreCleanupServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3351836987315084630L;
	//Milliseconds in a week
	private static final long timeGap = 604800000L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		long time = HighScoreUtil.getCurrentHighScore().getTime();
		if((System.currentTimeMillis() - time) > timeGap)
			HighScoreUtil.deleteCurrentHighscore();
	}
}
