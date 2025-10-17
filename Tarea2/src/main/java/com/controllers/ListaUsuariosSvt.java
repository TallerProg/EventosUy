package com.controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.DTUsuarioListaConsulta;

@WebServlet(name = "ListaUsuariosSvt", urlPatterns = {"/ListaUsuarios"})
@MultipartConfig(
    fileSizeThreshold = 1 * 1024 * 1024,
    maxFileSize = 10 * 1024 * 1024,
    maxRequestSize = 15 * 1024 * 1024
)
public class ListaUsuariosSvt extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	 
    try {
      Factory fabrica = Factory.getInstance();
      IControllerUsuario ctrl = fabrica.getIControllerUsuario();
      List<DTUsuarioListaConsulta> Asistentes = ctrl.getDTAsistentes(); 
      List<DTUsuarioListaConsulta> Organizadores = ctrl.getDTOrganizadores(); 

      req.setAttribute("LISTA_ASISTENTES", Asistentes);
      req.setAttribute("LISTA_ORGANIZADORES", Organizadores);
      
    } catch (Exception e) {
      req.setAttribute("msgError", "No se pudo cargar la lista de usuarios: " + e.getMessage());
      req.setAttribute("LISTA_USUARIOS", null);
    }

    req.getRequestDispatcher("/WEB-INF/views/ListaUsuarios.jsp").forward(req, resp);
  }


}


