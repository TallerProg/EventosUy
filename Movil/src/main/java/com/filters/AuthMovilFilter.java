package com.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class AuthMovilFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req  = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    HttpSession ses = req.getSession(false);
    boolean logged = (ses != null) && (ses.getAttribute("MOVIL_SESION") != null);

    if (!logged) {
      String target = req.getRequestURI();
      String qs = req.getQueryString();
      if (qs != null && !qs.isBlank()) target += "?" + qs;
      req.getSession(true).setAttribute("MOVIL_NEXT", target);

      resp.sendRedirect(req.getContextPath() + "/login");
      return;
    }

    chain.doFilter(request, response);
  }
}
