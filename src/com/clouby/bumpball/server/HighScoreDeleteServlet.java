package com.clouby.bumpball.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HighScoreDeleteServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8691494727648115949L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HighScoreUtil.deleteCurrentHighscore();
	}
}
