package com.ipartek.formacion.supermercado.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.supermercado.modelo.ConnectionManager;
import com.ipartek.formacion.supermercado.modelo.dao.CategoriaDAO;
import com.ipartek.formacion.supermercado.modelo.dao.ProductoDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Categoria;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;

/**
 * Servlet implementation class InicioController
 */
@WebServlet("/inicio")
public class InicioController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static ProductoDAO daoProducto;
	private static CategoriaDAO daoCategoria;
	
	private final static Logger LOG = Logger.getLogger(InicioController.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		daoProducto = ProductoDAO.getInstance();
		daoCategoria = CategoriaDAO.getInstance();

	}

	@Override
	public void destroy() {
		super.destroy();
		daoProducto = null;
		daoCategoria = null;
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (null == ConnectionManager.getConnection()) {
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
		} else {

			// llama a GET o POST
			super.service(req, resp);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		visualizarProductos(request, response);
		
		ArrayList<Categoria> categorias = (ArrayList<Categoria>) daoCategoria.getAll();

		request.setAttribute("categorias", categorias);

		request.getRequestDispatcher("/index.jsp").forward(request, response);
		LOG.debug("No falla");
	}

	private void visualizarProductos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Producto> productos = null;
		productos = (ArrayList<Producto>) daoProducto.getAll();
		String idCategoria = request.getParameter("categoria");
		String nombreProducto = request.getParameter("textoBuscado");
		int idParseado = 0;
		Categoria c = new Categoria();
			//busqueda por categoria.
		if ( nombreProducto == null || "".equalsIgnoreCase(nombreProducto)) {
			idCategoria = ("".equalsIgnoreCase(idCategoria) || idCategoria == null )?"0":idCategoria;
			nombreProducto = "";
			idParseado = Integer.parseInt(idCategoria);
			
			productos = (ArrayList<Producto>) daoProducto.busquedaPersonalizada(idParseado, nombreProducto);	
			
			
		}else {//busqueda por nombre
			idParseado = (idCategoria==null || idCategoria.isEmpty())? 0:Integer.parseInt(idCategoria) ;
			productos = (ArrayList<Producto>) daoProducto.busquedaPersonalizada(idParseado, nombreProducto);
			request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Estos son los productos cuyo nombre contiene <b> '' " + nombreProducto + " ''.</b>"));
		}
		
		if (idParseado!=0) { //si hay categoria, la devuelve
			c = daoCategoria.getById(idParseado);
			request.setAttribute("categoria",c);
			request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Estos son los productos de la categoría <b> " + c.getNombre() + ".</b>"));
		}if (idParseado !=0 && nombreProducto != "") { //si introduce categoria y nombre
			c = daoCategoria.getById(idParseado);
			request.setAttribute("categoria",c);
			request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Estos son los productos de la categoría  <b> " + c.getNombre() + " y su nombre contiene '' " + nombreProducto + " ''.</b>"));
		}
				
		if (productos.size()==0) {
			request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "No hay productos disponibles "));
		}
		
		request.setAttribute("productos", productos);
		
		//Enviamos de nuevo al formulario el parámetro de búsqueda y la categoria.
		request.setAttribute("parametroBusqueda", nombreProducto);

	}

	

	
}
