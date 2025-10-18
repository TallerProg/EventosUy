package com.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LogoutSvt", urlPatterns = {"/logout"})
public class LogoutSvt extends HttpServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    resp.setHeader("Pragma", "no-cache");
    resp.setDateHeader("Expires", 0);

    HttpSession ses = req.getSession(false);
    if (ses != null) ses.invalidate();

    resp.sendRedirect(req.getContextPath() + "/home");
  }
}

