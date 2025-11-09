package com.controllers;

import com.config.WSClientProvider;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import cliente.ws.sc.DTevento;
import cliente.ws.sc.DTeventoArray;
import cliente.ws.sc.DtCategoria;
import cliente.ws.sc.DtCategoriaArray;
import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;


@WebServlet(urlPatterns = "/home")
public class HomeSvt extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String SEED_FLAG = "app.seed.done";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        cliente.ws.sc.WebServicesService service = WSClientProvider.newService();
        cliente.ws.sc.WebServices port = service.getWebServicesPort();
        
        DTeventoArray eventosDTA = port.listarDTEventos();
        List<DTevento> eventos = eventosDTA.getItem();
        DtCategoriaArray categoriasDTA = port.listarDTCategorias();
        List<DtCategoria> categorias = categoriasDTA.getItem();
        req.setAttribute("LISTA_EVENTOS", eventos.toArray(DTevento[]::new));
        req.setAttribute("LISTA_CATEGORIAS", categorias.toArray(DtCategoria[]::new));


        req.getRequestDispatcher("/WEB-INF/views/home/home.jsp").forward(req, resp);
    }
}


    
