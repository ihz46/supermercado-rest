package com.ipartek.formacion.supermercado.controller.seguridad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.ipartek.formacion.supermercado.controller.Alerta;
import com.ipartek.formacion.supermercado.modelo.dao.CategoriaDAO;
import com.ipartek.formacion.supermercado.modelo.pojo.Categoria;

@WebServlet("/seguridad/categorias")
public class CategoriasController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VIEW_TABLA = "categorias/index.jsp";
	private static final String VIEW_FORM = "categorias/formulario.jsp";
	private static String vistaSeleccionda = VIEW_TABLA;
	private static CategoriaDAO daoCategoria;

	// acciones
	public static final String ACCION_LISTAR = "listar";
	public static final String ACCION_IR_FORMULARIO = "formulario";
	public static final String ACCION_GUARDAR = "guardar"; // crear y modificar
	public static final String ACCION_ELIMINAR = "eliminar";

	// Crear Factoria y Validador
	ValidatorFactory factory;
	Validator validator;

	// parametros
	String cAccion;
	String cId;
	String cNombre;

	@Override
	public void init(ServletConfig config) throws ServletException {		
		super.init(config);
		daoCategoria = CategoriaDAO.getInstance();
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
      
	@Override
	public void destroy() {	
		super.destroy();
		daoCategoria = null;
		factory = null;
		validator = null;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recogemos parámetros
		
		cAccion = request.getParameter("accion");
		cId = request.getParameter("id");
		cNombre = request.getParameter("nombre");
		
		try {
			
			switch (cAccion) {
			case ACCION_LISTAR:
				listar(request, response);
				break;
			case ACCION_ELIMINAR:	
				eliminar(request, response);
				break;
			case ACCION_GUARDAR:	
				guardar(request, response);
				break;
			case ACCION_IR_FORMULARIO:	
				irFormulario(request, response);
				break;
			default:
				listar(request, response);
				break;
			}
			
			
			
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
		}finally {
			
			request.getRequestDispatcher(vistaSeleccionda).forward(request, response);
		}
	}

	private void irFormulario(HttpServletRequest request, HttpServletResponse response) {
		Categoria categoria = new Categoria();
		if ( cId != null ) {
			
			int id = Integer.parseInt(cId);
			categoria = daoCategoria.getById(id);
						
		}
		
		request.setAttribute("categoria", categoria);
		vistaSeleccionda = VIEW_FORM;
		
	}
	
	private void mensajeValidacion(HttpServletRequest request, Set<ConstraintViolation<Categoria>> validaciones ) {

		StringBuilder mensaje = new StringBuilder();
		for (ConstraintViolation<Categoria> cv : validaciones) {
			
			mensaje.append("<p>");
			mensaje.append(cv.getPropertyPath()).append(": ");
			mensaje.append(cv.getMessage());
			mensaje.append("</p>");
			
		}
		
		request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, mensaje.toString() ));
		
	}

	private void guardar(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(cId);
		
		Categoria categoria = new Categoria();
		categoria.setId(id);
		categoria.setNombre(cNombre);
			
		Set<ConstraintViolation<Categoria>> validaciones = validator.validate(categoria);
		if( validaciones.size() > 0 ) {			
			mensajeValidacion(request, validaciones);
		}else {	
		
				try {
				
					if ( id > 0 ) {  // modificar
						try {
							categoria = daoCategoria.update(id, categoria);
							request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Se ha creado la categoría " + categoria.getNombre() ));
						} catch (Exception e) {
							request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "No se puede crear la categoria"));
							
						}
											
						listar(request, response);
						
					}else {            // crear
						
						try {
							categoria = daoCategoria.create(categoria);
							request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Se ha creado la categoría " + categoria.getNombre() ));
						} catch (Exception e) {
							request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "No se puede crear la categoria"));
							
						}
						
						listar(request, response);
					}
					
				}catch (Exception e) {  // validacion a nivel de base datos
					
					request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "El nombre ya existe, selecciona otro"));
				}					
			
		}	
		
		request.setAttribute("categorias", daoCategoria.getAll());
		request.setAttribute("categoria", categoria);
		vistaSeleccionda = VIEW_FORM;
	
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(cId);
		try {
			Categoria categoria = daoCategoria.delete(id);
			request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_PRIMARY, "Se ha eliminado la categoría " + categoria.getNombre() ));
		} catch (Exception e) {
			request.setAttribute("mensajeAlerta", new Alerta(Alerta.TIPO_DANGER, "No se puede Eliminar la categoria"));
			
		}
		
		listar(request, response);
		
	}

	private void listar(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<Categoria> categorias = (ArrayList<Categoria>) daoCategoria.getAll(); 
		request.setAttribute("categorias", categorias );
		vistaSeleccionda = VIEW_TABLA;
		
	}
	
	

}
