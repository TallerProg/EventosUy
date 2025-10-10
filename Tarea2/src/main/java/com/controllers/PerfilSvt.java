package com.controllers;	

import java.io.IOException;
import java.util.List;

import ServidorCentral.logica.Evento;
import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerInstitucion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import ServidorCentral.logica.Institucion;
// import ServidorCentral.test.CargarDatos;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = "/perfil")
public class PerfilSvt extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IControllerInstitucion ici = Factory.getInstance().getIControllerInstitucion();
		List<Institucion> instituciones = ici.getInstituciones();
		req.setAttribute("LISTA_INSTITUCION", instituciones.toArray(Institucion[]::new));
		req.getRequestDispatcher("/WEB-INF/views/ModificarUsuario.jsp").forward(req, resp);
	}
}
	