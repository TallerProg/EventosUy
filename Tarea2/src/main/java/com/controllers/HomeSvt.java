package com.controllers;

import java.io.IOException;
import java.util.List;

import com.model.CargarDatos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerEvento;
import servidorcentral.logica.IControllerUsuario;
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

        IControllerEvento ice = Factory.getInstance().getIControllerEvento();
        IControllerUsuario icu = Factory.getInstance().getIControllerUsuario();

        ensureSeedOnce(req.getServletContext(), icu, ice);
        
        cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
        cliente.ws.sc.WebServices port = service.getWebServicesPort();
        
        DTeventoArray eventosDTA = port.listarDTEventos();
        List<DTevento> eventos = eventosDTA.getItem();
        DtCategoriaArray categoriasDTA = port.listarDTCategorias();
        List<DtCategoria> categorias = categoriasDTA.getItem();
        req.setAttribute("LISTA_EVENTOS", eventos.toArray(DTevento[]::new));
        req.setAttribute("LISTA_CATEGORIAS", categorias.toArray(DtCategoria[]::new));


        req.getRequestDispatcher("/WEB-INF/views/home/home.jsp").forward(req, resp);
    }

    private void ensureSeedOnce(jakarta.servlet.ServletContext app,
                                IControllerUsuario icu,
                                IControllerEvento ice) {

        if (app.getAttribute(SEED_FLAG) != null) return;

        synchronized (app) {

            if (app.getAttribute(SEED_FLAG) != null) return;
            try {
                CargarDatos.inicializar(icu, ice);
            } catch (Exception e) {

                e.printStackTrace();
            } finally {

                app.setAttribute(SEED_FLAG, Boolean.TRUE);
            }
        }
    }
}
