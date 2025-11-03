package com.movil.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LogoutMovilSvt", urlPatterns = {"/logout"})
public class LogoutMovilSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession ses = req.getSession(false);
    if (ses != null) ses.invalidate();
    resp.sendRedirect(req.getContextPath() + "/login");
  }
}
