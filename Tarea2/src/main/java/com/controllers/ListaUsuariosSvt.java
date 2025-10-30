package com.controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import cliente.ws.sc.DtUsuarioListaConsulta;
import cliente.ws.sc.DtUsuarioListaConsultaArray;


@WebServlet(name = "ListaUsuariosSvt", urlPatterns = {"/ListaUsuarios"})
@MultipartConfig(
    fileSizeThreshold = 1 * 1024 * 1024,
    maxFileSize = 10 * 1024 * 1024,
    maxRequestSize = 15 * 1024 * 1024
)
public class ListaUsuariosSvt extends HttpServlet {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	 
    try {

      cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
      cliente.ws.sc.WebServices port = service.getWebServicesPort();
      DtUsuarioListaConsultaArray asisDTA= port.listarDTAsistentes();
      DtUsuarioListaConsultaArray orgDTA= port.listarDTOrganizadores();

      List<DtUsuarioListaConsulta> Asistentes=asisDTA.getItem();
      List<DtUsuarioListaConsulta> Organizadores=orgDTA.getItem();

      req.setAttribute("LISTA_ASISTENTES", Asistentes);
      req.setAttribute("LISTA_ORGANIZADORES", Organizadores);
      
    } catch (Exception e) {
      req.setAttribute("msgError", "No se pudo cargar la lista de usuarios: " + e.getMessage());
      req.setAttribute("LISTA_USUARIOS", null);
    }

    req.getRequestDispatcher("/WEB-INF/views/ListaUsuarios.jsp").forward(req, resp);
  }


}


