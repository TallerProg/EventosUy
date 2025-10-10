package com.controllers;

import java.io.IOException;
import java.util.List;

import com.model.EstadoSesion;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.Categoria;
import ServidorCentral.logica.Evento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import com.model.CargarDatos;
import ServidorCentral.logica.ManejadorEvento;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/home")
public class HomeSvt extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static void initSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("estado_sesion") == null) {
			session.setAttribute("estado_sesion", EstadoSesion.NO_LOGIN);
		}
	}

	public static EstadoSesion getEstado(HttpServletRequest request) {
		return (EstadoSesion) request.getSession().getAttribute("estado_sesion");
	}	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		initSession(req);
		IControllerEvento ice = Factory.getInstance().getIControllerEvento();
		IControllerUsuario icu = Factory.getInstance().getIControllerUsuario();

		try {
			CargarDatos.inicializar(icu, ice);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		
		List<Evento> eventos = ice.listarEventos();
		req.setAttribute("LISTA_EVENTOS", eventos.toArray(Evento[]::new));



		req.getRequestDispatcher("/WEB-INF/views/home/home.jsp").forward(req, resp);
	}
}
