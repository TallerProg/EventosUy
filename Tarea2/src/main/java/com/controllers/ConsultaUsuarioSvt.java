package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.Asistente;
import servidorcentral.logica.Organizador;
import servidorcentral.logica.ControllerUsuario.DTSesionUsuario;
import servidorcentral.logica.DTRegistro;
import servidorcentral.logica.DTUsuarioListaConsulta;

import servidorcentral.logica.Edicion;
import servidorcentral.logica.Registro;

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
		nick = nick.trim(); // Evita espacios que rompan la busqueda
		
		IControllerUsuario icu = Factory.getInstance().getIControllerUsuario();

		// Primero detecto si existe y que rol tiene
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
			// No existe ni como Asistente ni como Organizador 404
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

			// Convertir a lista de DTRegistro
			List<DTRegistro> dtRegis = regis.stream()
			    .map(Registro::getDTRegistro)
			    .collect(Collectors.toList());

			// Guardar en el request
			req.setAttribute("Registros", dtRegis);


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