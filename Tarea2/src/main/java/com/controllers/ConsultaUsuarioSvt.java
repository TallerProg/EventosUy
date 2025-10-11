package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.Asistente;
import ServidorCentral.logica.Organizador;
import ServidorCentral.logica.ControllerUsuario.DTSesionUsuario;
import ServidorCentral.logica.DTUsuarioListaConsulta;
// Clases dominio que ya tenés:
import ServidorCentral.logica.Edicion;
import ServidorCentral.logica.Registro;

@WebServlet("/ConsultaUsuario")
public class ConsultaUsuarioSvt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String nick = req.getParameter("nick");
		if (nick == null || nick.isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro 'nick'.");
			return;
		}

		IControllerUsuario icu = Factory.getInstance().getIControllerUsuario();
		DTUsuarioListaConsulta u = icu.ConsultaDeUsuario(nick);
		if (u == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado.");
			return;
		}
		String rol = (icu.getAsistente(nick) != null) ? "A" : (icu.getOrganizador(nick) != null) ? "O" : "v";
		// Es su propio perfil?
		HttpSession session = req.getSession(false);
		boolean S = false;
		if (session != null) {
			DTSesionUsuario sesUser = (DTSesionUsuario) session.getAttribute("usuario_logueado");
			if (sesUser.getNickname().equals(nick)) {
				S = true;
				if (rol.equals("A")) {
					Asistente a = icu.getAsistente(nick);
					List<Registro> regis = a.getRegistros();
					session.setAttribute("Registros", regis);
				}
			}
			if (rol.equals("O")) {
				Organizador o = icu.getOrganizador(nick);
				List<Edicion> edis = o.getEdiciones();
				req.setAttribute("Ediciones", edis);
			}

			req.setAttribute("esSuPerfil", S);
			req.setAttribute("rol", rol);
			req.setAttribute("usuario", u);
			req.getRequestDispatcher("/WEB-INF/views/ConsultaUsuario.jsp").forward(req, resp);

		}
	}
}