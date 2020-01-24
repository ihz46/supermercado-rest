package com.ipartek.formacion.supermercado.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.ipartek.formacion.supermercado.controller.pojo.ResponseMensaje;
import com.ipartek.formacion.supermercado.modelo.dao.ProductoDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Producto;
import com.ipartek.formacion.supermercado.utils.Utilidades;

/**
 * Servlet implementation class ProductoRestController
 */
@WebServlet({ "/producto/*" })
public class ProductoRestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getLogger(ProductoRestController.class);
	private ProductoDAO productoDao;
	

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		productoDao = ProductoDAO.getInstance();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		productoDao = null;
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Preparamos la respuestas, como son comunes a todos los métodos lo añadimos al
		// service, que llamará a cada uno de ellos.

		response.setContentType("application/json"); // por defecto -> text/html
		response.setCharacterEncoding("utf-8"); // codificacion utf8

		super.service(request, response); // llama a doGet, doPost, doPut, doDelete
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOG.trace("peticion GET");

		String pathInfo = request.getPathInfo();

		// Comprobamos el path
		LOG.debug("Path info" + pathInfo);

		int idProducto;
		
		try {
			idProducto = Utilidades.obtenerId(pathInfo);
			
			LOG.debug("Número devuelto = " + idProducto);

			if (idProducto != -1) {
				// Significa que es numérico

				// Instanciamos un nuevo producto

				Producto p = productoDao.getById(idProducto);
				if (comprobarProducto(p)==false) {
					//Significa que no se ha encontrado el producto (404)
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					
					PrintWriter out = response.getWriter();

					// Convertimos de java a json
					String jsonResponseBody = new Gson().toJson("El producto con el id " + idProducto + " no existe");

					out.flush();

					// Pintamos la respuesta
					out.print(jsonResponseBody.toString());
					
				}else {
					// Indicamos el código de respuestas 200
					response.setStatus(HttpServletResponse.SC_OK);

					PrintWriter out = response.getWriter();

					// Convertimos de java a json
					String jsonResponseBody = new Gson().toJson(p);

					out.flush();

					// Pintamos la respuesta
					out.print(jsonResponseBody.toString());
				}
				

			} else {
				// Prepara la response
				ArrayList<Producto> lista = (ArrayList<Producto>) productoDao.getAll();

				// Comprobamos que la lista tenga contenido, en caso contrario devolveremos el
				// código de respuesta 204

				if (comprobarLista(lista) == false) {
					// Devolvemos el código de respuesta 204

					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					PrintWriter out = response.getWriter();

					String jsonResponseBody = new Gson().toJson(new ResponseMensaje("No existen productos"));
					
					// Pintamos la respuesta
					out.print(jsonResponseBody.toString());

					// retornamos un array vacio dentro del body
					out.flush();

				} else {

					// Indicamos el código de respuestas 200
					response.setStatus(HttpServletResponse.SC_OK);

					PrintWriter out = response.getWriter();

					// Convertimos de java a json

					String jsonResponseBody = new Gson().toJson(lista);

					// Pintamos la respuesta
					out.print(jsonResponseBody.toString());

					// retornamos un array vacio dentro del body
					out.flush();

				}

			}

		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			PrintWriter out = response.getWriter();

			String jsonResponseBody = new Gson().toJson( new ResponseMensaje(e.getMessage()));	
			out.print(jsonResponseBody.toString());
			out.flush();
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOG.debug("Post, creamos un recurso");
		
		//Convertimos el json del request body a producto
		
		//Leemos el contenido del request body
		BufferedReader reader = request.getReader();
		
		//Instanciamos un objeto gson
		Gson gson = new Gson();
		
		try {
			Producto producto = gson.fromJson(reader, Producto.class);
			
			if (comprobarProducto(producto)) {
				
				//Llamamos al método de crear producto
				
				Producto productoNuevo = productoDao.create(producto);
				
				// Indicamos el código de respuestas 200
				response.setStatus(HttpServletResponse.SC_OK);
				
				String jsonResponseBody = new Gson().toJson(productoNuevo);	
				PrintWriter out = response.getWriter();		              
				  
				out.print(jsonResponseBody.toString()); 	
				out.flush();
				
			}else {
				//Indicamos el código de error
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				PrintWriter out = response.getWriter();
				String jsonResponseBody = new Gson().toJson( new ResponseMensaje("Se han enviado mal los parámetros"));	
				out.print(jsonResponseBody.toString());
				out.flush();
				   
			}
			
			
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
		}
		
		

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/***
	 * 
	 * @param lista de productos
	 * @return false si la lista es vacía y true si la lista tiene contenido
	 */
	private boolean comprobarLista(ArrayList<Producto> lista) {
		if (lista.size() == 0 || lista == null) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean comprobarProducto(Producto p) {
		if (p!=null) {
			return true;
		}else {
			return false;
		}
	}

		
	

}
