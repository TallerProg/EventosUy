package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LogoutSvt", urlPatterns = {"/logout"})
public class LogoutSvt extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Evitar que el navegador “re-muestre” páginas cacheadas luego de logout
    resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    resp.setHeader("Pragma", "no-cache");
    resp.setDateHeader("Expires", 0);

    HttpSession ses = req.getSession(false);
    if (ses != null) ses.invalidate();

    resp.sendRedirect(req.getContextPath() + "/home");
  }
}

