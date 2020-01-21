package com.ipartek.formacion.supermercado.controller.seguridad;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductosAltaController
 */
@WebServlet("/seguridad/visualizarAlta")
public class ProductosAltaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String accion = request.getParameter("accion");
		
		switch(accion) {
		
		case "alta":
			
			request.getRequestDispatcher("productosAlta/index.jsp").forward(request, response);
			break;
			
		case "baja":
			
			request.getRequestDispatcher("productosAlta/index.jsp").forward(request, response);			
			break;
		
		default: 
			
			request.getRequestDispatcher("productosAlta/index.jsp").forward(request, response);
			break;
		}
		
		
	}

}
