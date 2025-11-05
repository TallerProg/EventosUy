package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cliente.ws.sc.DTevento;
import cliente.ws.sc.DTeventoArray;
import cliente.ws.sc.DTeventoOedicion;
import cliente.ws.sc.DTeventoOedicionArray;
import cliente.ws.sc.DtEdicion;
import cliente.ws.sc.DtEdicionArray;
import cliente.ws.sc.LocalDate;





@WebServlet("/Search")
public class SearchSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final String JSP_LISTA_EVENTOS = "/WEB-INF/views/Search.jsp";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  String busqueda = req.getParameter("q");
	  cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
      cliente.ws.sc.WebServices port = service.getWebServicesPort();
      DTeventoOedicionArray evenoediDTA= port.listarEventosYEdicionesBusqueda(busqueda);
      List<DTeventoOedicion> evenOedi=evenoediDTA.getItem();
 
      req.setAttribute("LISTA_EVENTOS_EDICIONES", evenOedi);
      req.getRequestDispatcher(JSP_LISTA_EVENTOS).forward(req, resp);

    
  }
}

