package com.ipartek.formacion.supermercado.utils;

import org.apache.log4j.Logger;

public class Utilidades {
	
	private final static Logger LOG = Logger.getLogger(Utilidades.class);
	
	/**
	 * Obtenemos el id de la URI si existe.
	 * @param pathInfo parte de la URI donde debemos buscar un número
	 * @return idNumero 
	 * @throws Excepción si el pathInfo está mal formado
	 * 
	 * Ejemplos:
	 * 	<ol>
		 	<li>/ url válida</li>
		 	<li>/2 url válida</li>
		 	<li>/2/ url válida</li>
		 	<li>/2/2 url INVALIDA</li>
		 	<li>/2/otracosa/34 url INVALIDA </li>
	 	</ol>
	 */
	public static int obtenerId(String path) throws Exception {
		int resul = -1;

		if (path != null && !path.isEmpty()) {
			/*
			 * Cogemos el path y hacemos un split para obtener los elementos que separa el símbolo ("/").
			 * El split retorna un array de strings
			 */
			String[] splitParts = path.split("/");
			
			//Comprobamos que el array no este vacío
			if (splitParts.length !=0) {
				/*
				 * Comprobamos si hay un valor numérico dentro del primer elemento del array de strings
				 * Además comprobamos que de haber número, no haya más de dos elementos ("/numero")
				 * 
				 */
				if (splitParts[1].matches("[0-9]*") && splitParts.length<=2) {
					//Parseamos el número
					try {
						int numeroDevuelto = Integer.parseInt(splitParts[1]);
						resul = numeroDevuelto;
					} catch (NumberFormatException e) {
						LOG.error("Error de parseo" + e.getMessage());
						throw new Exception("El id no es válido");

					} catch (Exception e) {
						LOG.error("Error " + e.getMessage());
					}
				}else {
					throw new Exception("La ruta indicada no existe ");
				}
			}

		}
		return resul;
		
	}
	
		
}
