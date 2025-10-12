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
		nick = nick.trim(); // FIX 1: evita espacios que rompan la búsqueda
		
		IControllerUsuario icu = Factory.getInstance().getIControllerUsuario();

		// FIX 2: primero detecto si existe y qué rol tiene
		Asistente asis = icu.getAsistente(nick);
		Organizador org = (asis == null) ? icu.getOrganizador(nick) : null;
		String rol = (asis != null) ? "A" : (org != null) ? "O" : "v";
		if(asis!=null) {
			String img=asis.getImg();
            req.setAttribute("IMAGEN", img);
		}else if(org!=null){
			String img=org.getImg();
            req.setAttribute("IMAGEN", img);
		}
		if ("v".equals(rol)) {
			// No existe ni como Asistente ni como Organizador → 404
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado.");
			return;
		}
		DTUsuarioListaConsulta u = icu.consultaDeUsuario(nick);
		if (u == null) {
			if (asis != null) {
				u = icu.consultaDeUsuario(asis.getNickname());
			} else if (org != null) {
				u = icu.consultaDeUsuario(org.getNickname());
			}

			if (u == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado.");
				return;
			}
		}

		HttpSession session = req.getSession(false);
		DTSesionUsuario sesUser = (session != null) ? (DTSesionUsuario) session.getAttribute("usuario_logueado") : null;
		boolean S = (sesUser != null) && sesUser.getNickname().equals(nick);

		// Datos extra para la vista, según rol
		if ("A".equals(rol) && S) {
			List<Registro> regis = asis.getRegistros();
			req.setAttribute("Registros", regis);

		}
		if ("O".equals(rol)) {
			List<Edicion> edis = org.getEdiciones();
			req.setAttribute("Ediciones", edis);
		}

		req.setAttribute("esSuPerfil", S);
		req.setAttribute("rol", rol);
		req.setAttribute("usuario", u);
		req.getRequestDispatcher("/WEB-INF/views/ConsultaUsuario.jsp").forward(req, resp);
	}

}