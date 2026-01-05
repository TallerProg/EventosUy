package com.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebFilter(
  urlPatterns = { "/movil/*", "/m/*" },
  dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD }
)
public class AuthMovilFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req  = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    final String ctx  = req.getContextPath();
    final String path = req.getRequestURI().substring(ctx.length());

    if (path.equals("/login") || esEstatico(path)) {
      chain.doFilter(request, response);
      return;
    }

    HttpSession ses = req.getSession(false);
    Object sesionMovil = (ses != null) ? ses.getAttribute("MOVIL_SESION") : null;
    boolean logged = (sesionMovil != null);

    boolean esAsistente = (ses != null) && Boolean.TRUE.equals(ses.getAttribute("usuarioAsistente"));

    if (!esAsistente && sesionMovil instanceof cliente.ws.sc.DtSesionUsuario su) {
      cliente.ws.sc.RolUsuario rol = null;
      try { rol = su.getRol(); } catch (Throwable ignore) {}
      esAsistente = (rol == cliente.ws.sc.RolUsuario.ASISTENTE);
    }

    resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    resp.setHeader("Pragma", "no-cache");
    resp.setDateHeader("Expires", 0);

    if (!logged) {
      String target = req.getRequestURI();
      String qs = req.getQueryString();
      if (qs != null && !qs.isBlank()) target += "?" + qs;

      req.getSession(true).setAttribute("MOVIL_NEXT", target);

      String next = URLEncoder.encode(
          target.startsWith(ctx) ? target : (ctx + target),
          StandardCharsets.UTF_8.name()
      );
      resp.sendRedirect(resp.encodeRedirectURL(ctx + "/login?next=" + next));
      return;
    }

    if (!esAsistente) {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN);
      return;
    }

    chain.doFilter(request, response);
  }

  private boolean esEstatico(String path) {
    return path.startsWith("/vendor/") || path.startsWith("/assets/") || path.startsWith("/media/")
        || path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/img/");
  }
}
