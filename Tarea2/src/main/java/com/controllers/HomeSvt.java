package com.controllers;

import java.io.IOException;
import java.util.List;

import com.model.CargarDatos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ServidorCentral.logica.Categoria;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.IControllerUsuario;

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

        List<Evento> eventos = ice.listarEventos();
        List<Categoria> categorias = ice.getCategorias();
        req.setAttribute("LISTA_EVENTOS", eventos.toArray(Evento[]::new));
        req.setAttribute("LISTA_CATEGORIAS", categorias.toArray(Categoria[]::new));

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
