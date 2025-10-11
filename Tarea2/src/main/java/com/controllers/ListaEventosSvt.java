package com.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ServidorCentral.logica.Evento;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.Factory; 

@WebServlet("/Eventos")
public class ListaEventosSvt extends HttpServlet {

    private static final long serialVersionUID = 1L;

    
    private static final String JSP_LISTA_EVENTOS = "/WEB-INF/views/ListarEventos.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            IControllerEvento icEvento = getController();

            List<Evento> eventos = icEvento.listarEventos();

            req.setAttribute("LISTA_EVENTOS", eventos);

            resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
            resp.setHeader("Pragma", "no-cache");

            req.getRequestDispatcher(JSP_LISTA_EVENTOS).forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", (e.getMessage() != null) ? e.getMessage() : "Error al listar eventos.");
            req.getRequestDispatcher(JSP_LISTA_EVENTOS).forward(req, resp);
        }
    }

    private IControllerEvento getController() {
    	Factory fabrica = Factory.getInstance();
        return fabrica.getIControllerEvento();
    }
}
