package com.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "LogoutSvt", urlPatterns = {"/logout"})
public class LogoutSvt extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp); 
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        HttpSession ses = req.getSession(false);
        if (ses != null) {
            ses.removeAttribute("usuario_logueado");
            ses.removeAttribute("IMAGEN_LOGUEADO");
            ses.removeAttribute("nickname");
            ses.invalidate();
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("JSESSIONID".equalsIgnoreCase(c.getName()) || "AUTH_NICK".equalsIgnoreCase(c.getName())) {
                    c.setValue("");
                    c.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
                    c.setMaxAge(0);
                    c.setHttpOnly(true);
                    c.setSecure(true);
                    resp.addCookie(c);
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/home");
    }
}

