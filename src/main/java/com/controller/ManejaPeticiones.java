package com.controller;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletResponse;

import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo7.otros.DescodificadorJson;
import equipo7.otros.ListaIDs;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import equipo4.model.MateriaPrima;
import equipo6.model.Actor;
//NECESARIOS PARA TRAZABILIDAD:
import equipo6.otros.BlockchainServices;
import equipo7.otros.CodificadorJSON;
import equipo7.otros.OrdenInicial;

@Controller
@SpringBootApplication
public class ManejaPeticiones {

	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/crearOrden")
	@ResponseBody
	public String crearOrden(HttpServletResponse response,
			@RequestParam(name="json", required=true) String json) throws Throwable {
		
		DescodificadorJson decodificador = new DescodificadorJson();
		OrdenInicial inicial = decodificador.DescodificadorJSONinicial(json);
		
		//Pedimos id de la orden
		int idOrden = equipo5.dao.metodosCompany.idOrdenTrazabilidad();
		int idPedido = inicial.getIdPedido();
		if(inicial.getActorOrigen().getTipoActor()==4) {
			//Si se trata de Retailer, hay que pedir el idPedido a BBDD
			idPedido = equipo5.dao.metodosCompany.idPedido();
		}
		
		if(inicial.getActorOrigen()!=null && inicial.getActorDestino()!=null && inicial.getProductosPedidos()!=null) {
			//Creamos el objeto orden
			OrdenTrazabilidad orden = new OrdenTrazabilidad(idOrden, inicial.getActorOrigen(),
					inicial.getActorDestino(), inicial.getProductosPedidos());
			orden.setIdPedido(idPedido);
			
			//Guardamos en la cadena la orden
			BlockchainServices bloque = new BlockchainServices();
		    bloque.guardarOrden(orden);
		        
			return CodificadorJSON.crearJSON(orden);
		}
		else {
			return "ERROR: no se pudo crear la orden";
		}
		
	}

	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/obtenerOrden")
	@ResponseBody
	public String obtenerOrden(HttpServletResponse response,
			@RequestParam(name="id", required=true) String id) throws ClassNotFoundException, SQLException{

		int idInt = Integer.parseInt(id);

		//Obtenemos el pedido de trazabilidad
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad orden = bloque.getOrden(idInt);
		
		if (orden != null)
			return CodificadorJSON.crearJSON(orden);
		else
			return "ERROR: No se pudo obtener la orden";
	}
	
	
	private String ordenesPendientes(String idActor, int estado) throws ClassNotFoundException, SQLException{
		//Obtenemos los pedidos de trazabilidad
		BlockchainServices bloque = new BlockchainServices();
			
		ArrayList<OrdenTrazabilidad> ordenes = bloque.extraerOrdenesDestino(idActor);
		ArrayList<Integer> ordenesPendientes = new ArrayList<Integer>();
		ListaIDs listaIDs = new ListaIDs();
				
		if(ordenes!=null && ordenes.size()>0) {
					
			Iterator<OrdenTrazabilidad> it = ordenes.iterator();
			while(it.hasNext()) {
				//Hay que asegurarse que el actor sea destino
				OrdenTrazabilidad actual = it.next();
				if(actual.getActorDestino().getId().compareTo(idActor)==0) {
					//El estado del pedido cuando no ha sido aceptado es 0
					if(actual.getEstado()==estado) {
						ordenesPendientes.add(actual.getId());
					}
				}
			}
						
			//Devolver lista de identificadores
			listaIDs.setListaIDs(ordenesPendientes);
			return CodificadorJSON.crearJSONlista(listaIDs);
		} else {
			listaIDs.setListaIDs(null);
			return CodificadorJSON.crearJSONlista(listaIDs);
		}
	}
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesPendientesPorAceptar")
	@ResponseBody
	public String ordenesPendientesPorAceptar(HttpServletResponse response,
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
		
		return this.ordenesPendientes(idActor, 0);
			
	}
	
	//PARA EQUIPO2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesQueHeEnviado")
	@ResponseBody
	public String ordenesQueHeEnviado(HttpServletResponse response,
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {

		BlockchainServices bloque = new BlockchainServices();
		//Obtenemos las ordenes
		ArrayList<OrdenTrazabilidad> ordenes = bloque.extraerOrdenesOrigen(idActor);
		ArrayList<Integer> ordenesIds = new ArrayList<Integer>();
		ListaIDs listaIDs = new ListaIDs();
		
		if(ordenes!=null && ordenes.size()>0) {
						
			Iterator<OrdenTrazabilidad> it = ordenes.iterator();
			while(it.hasNext()) {
				//Hay que asegurarse que el actor sea origen y no sean ordenes rechazadas
				OrdenTrazabilidad actual = it.next();
				if(actual.getActorOrigen().getId().compareTo(idActor)==0 && actual.getEstado()!=-1) {
						ordenesIds.add(actual.getId());
				}
			}
			listaIDs.setListaIDs(ordenesIds);
			return CodificadorJSON.crearJSONlista(listaIDs);
		} else {
			listaIDs.setListaIDs(null);
			return CodificadorJSON.crearJSONlista(listaIDs);
		}
	}
	
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesEnProceso")
	@ResponseBody
	public String ordenesEnProceso(HttpServletResponse response,
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
		
		return this.ordenesPendientes(idActor, 1);					
	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesListasParaEntregar")
	@ResponseBody
	public String ordenesListasParaEntregar(HttpServletResponse response,
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
			
		return this.ordenesPendientes(idActor, 2);
						
	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesRechazadas")
	@ResponseBody
	public String ordenesRechazadas(HttpServletResponse response,
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
				
		BlockchainServices bloque = new BlockchainServices();
		//Obtenemos las ordenes
		ArrayList<OrdenTrazabilidad> ordenes = bloque.extraerOrdenesOrigen(idActor);
		ArrayList<Integer> ordenesIds = new ArrayList<Integer>();
		ListaIDs listaIDs = new ListaIDs();
		
		if(ordenes!=null && ordenes.size()>0) {
						
			Iterator<OrdenTrazabilidad> it = ordenes.iterator();
			while(it.hasNext()) {
				//Hay que asegurarse que el actor sea origen
				OrdenTrazabilidad actual = it.next();
				if(actual.getActorOrigen().getId().compareTo(idActor)==0) {
					if(actual.getEstado()==-1) {
						ordenesIds.add(actual.getId());			
					}
				}
			}

			listaIDs.setListaIDs(ordenesIds);
			return CodificadorJSON.crearJSONlista(listaIDs);
		} else {
			listaIDs.setListaIDs(null);
			return CodificadorJSON.crearJSONlista(listaIDs);
		}
	}
	
	//PARA EQUIPO 2: VISTAS
		@Scope("request")
		@RequestMapping("/ordenesCompletadas")
		@ResponseBody
		public String ordenesCompletadas(HttpServletResponse response,
				@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
					
			BlockchainServices bloque = new BlockchainServices();
			//Obtenemos las ordenes
			ArrayList<OrdenTrazabilidad> ordenes = bloque.extraerOrdenesOrigen(idActor);
			ArrayList<Integer> ordenesIds = new ArrayList<Integer>();
			ListaIDs listaIDs = new ListaIDs();
			
			if(ordenes!=null && ordenes.size()>0) {
							
				Iterator<OrdenTrazabilidad> it = ordenes.iterator();
				while(it.hasNext()) {
					//Hay que asegurarse que el actor sea origen
					OrdenTrazabilidad actual = it.next();
					if(actual.getActorOrigen().getId().compareTo(idActor)==0) {
						if(actual.getEstado()==4) {
							ordenesIds.add(actual.getId());			
						}
					}
				}

				listaIDs.setListaIDs(ordenesIds);
				return CodificadorJSON.crearJSONlista(listaIDs);
			} else {
				listaIDs.setListaIDs(null);
				return CodificadorJSON.crearJSONlista(listaIDs);
			}
		}
		
		//PARA EQUIPO 2: VISTAS
				@Scope("request")
				@RequestMapping("/ordenesQueMeHanAceptado")
				@ResponseBody
				public String ordenesAceptadas(HttpServletResponse response,
						@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
							
					BlockchainServices bloque = new BlockchainServices();
					//Obtenemos las ordenes
					ArrayList<OrdenTrazabilidad> ordenes = bloque.extraerOrdenesOrigen(idActor);
					ArrayList<Integer> ordenesIds = new ArrayList<Integer>();
					ListaIDs listaIDs = new ListaIDs();
					
					if(ordenes!=null && ordenes.size()>0) {
									
						Iterator<OrdenTrazabilidad> it = ordenes.iterator();
						while(it.hasNext()) {
							//Hay que asegurarse que el actor sea origen
							OrdenTrazabilidad actual = it.next();
							if(actual.getActorOrigen().getId().compareTo(idActor)==0) {
								if(actual.getEstado()==1 && actual.getEstado()<4) {
									ordenesIds.add(actual.getId());			
								}
							}
						}

						listaIDs.setListaIDs(ordenesIds);
						return CodificadorJSON.crearJSONlista(listaIDs);
					} else {
						listaIDs.setListaIDs(null);
						return CodificadorJSON.crearJSONlista(listaIDs);
					}
				}	
		
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesEnProcesoDeEntrega")
	@ResponseBody
	public String ordenesEnProcesoDeEntrega(HttpServletResponse response,
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
						
		return this.ordenesPendientes(idActor, 3);
									
	}
	


	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/aceptarOrden")
	@ResponseBody
	public String aceptarOrden(HttpServletResponse response,
			@RequestParam(name="id", required=true) String id) throws Throwable{
		
		int idInt = Integer.parseInt(id);

		//Recuperamos la orden para cambiar el estado
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad orden = bloque.getOrden(idInt);
		int idProd;
		if(orden!=null) {
			if(orden.getActorOrigen().getTipoActor()==0){
				//AGRICULTOR:
				//Hay que recorrer los productos pedidos y por cada producto se genera un objeto
				Productos productos = orden.getProductosPedidos();
				if(productos.getCant_malta_base_palida()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima maltaPalida = new MateriaPrima("MaltaBasePalida",idProd,productos.getCant_malta_base_palida());
				}
				else if(productos.getCant_malta_munich()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima maltaMunich = new MateriaPrima("MaltaMunich",idProd,productos.getCant_malta_munich());
				}
				else if(productos.getCant_malta_negra()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima maltaNegra = new MateriaPrima("MaltaNegra",idProd,productos.getCant_malta_negra());
				}
				else if(productos.getCant_malta_crystal()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima maltaCrystal = new MateriaPrima("MaltaCrystal",idProd,productos.getCant_malta_crystal());
				}
				else if(productos.getCant_malta_chocolate()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima maltaChocolate = new MateriaPrima("MaltaChocolate",idProd,productos.getCant_malta_chocolate());
				}
				else if(productos.getCant_malta_caramelo()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima maltaCaramelo = new MateriaPrima("MaltaCaramelo",idProd,productos.getCant_malta_caramelo());
				}
				else if(productos.getCant_malta_pilsner()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima maltaPilsner = new MateriaPrima("MaltaPilsner",idProd,productos.getCant_malta_pilsner());
				}
				else if(productos.getCant_cebada_tostada()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima cebadaTostada = new MateriaPrima("CebadaTostada",idProd,productos.getCant_cebada_tostada());
				}
				else if(productos.getCant_lupulo_centennial()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima lupuloCentennial = new MateriaPrima("LupuloCentennial",idProd,productos.getCant_lupulo_centennial());
				}
				else if(productos.getCant_lupulo_perle()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima lupuloPerle = new MateriaPrima("LupuloPerle",idProd,productos.getCant_lupulo_perle());
				}
				else if(productos.getCant_lupulo_tettnanger()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima lupuloTettnanger = new MateriaPrima("LupuloTettnanger",idProd,productos.getCant_lupulo_tettnanger());
				}
				else if(productos.getCant_levadura_lager()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima levaduraLager = new MateriaPrima("LevaduraLager",idProd,productos.getCant_levadura_lager());
				}
				else if(productos.getCant_levadura_ale()>0){
					idProd = equipo5.dao.metodosCompany.idMateriaPrima();
					MateriaPrima levaduraAle = new MateriaPrima("LevaduraAle",idProd,productos.getCant_levadura_ale());
				}
				orden.setEstado(4);
				bloque.guardarOrden(orden);
				return CodificadorJSON.crearJSON(orden);
			}
			else{
				orden.setEstado(1);
				bloque.guardarOrden(orden);
				return CodificadorJSON.crearJSON(orden);
			}
		}
		else {
			return "ERROR: no existe la orden asociada a este ID";
		}

	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/rechazarOrden")
	@ResponseBody
	public String rechazarOrden(HttpServletResponse response,
			@RequestParam(name="id", required=true) String id) throws Throwable{
		
		int idInt = Integer.parseInt(id);
			//Recuperamos la orden para cambiar el estado
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad orden = bloque.getOrden(idInt);
		
		if(orden!=null) {
			orden.setEstado(-1);
			bloque.guardarOrden(orden);
			return CodificadorJSON.crearJSON(orden);
		}
		else {
			return "ERROR: no existe la orden asociada a este ID";
		}

	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/listaOrden")
	@ResponseBody
	public String listaOrden(HttpServletResponse response,
			@RequestParam(name="id", required=true) String id) throws Throwable{

		DescodificadorJson decoder = new DescodificadorJson();
		OrdenTrazabilidad miniOrden = decoder.DescodificadorJson(id);
		//miniOrden contiene el id de la orden y los porductosAEntregar
		int idOrden = miniOrden.getId();
		
		//Con el idOrden sacamos la orden de BBDD
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad orden = bloque.getOrden(idOrden);
		
		
		if(orden!=null && miniOrden.getProductosAEntregar()!=null) {
			//En orden hay que rellenar el campo de los productosAEntregar y cambiar el estado
			orden.setProductosAEntregar(miniOrden.getProductosAEntregar());
			//Hay que activar necesitaTransportista
			if(orden.getActorOrigen().getTipoActor()!=1) {
				orden.setEstado(2);
				orden.setNecesitaTransportista(true);
			} else {
				orden.setEstado(4);
			}
		
			//Guardamos la orden actualizada en BBDD
			bloque.guardarOrden(orden);
			return CodificadorJSON.crearJSON(orden);
		}
		else {
			return "ERROR: no existe la orden asociada a este ID";
		}
		
	}
	
	
	//PARA EQUIPO 3: TRANSPORTISTAS
	@Scope("request")
	@RequestMapping("/recogidaOrden")
	@ResponseBody
	public String recogidaOrden(HttpServletResponse response,
			@RequestParam(name="json", required=true) String json) throws Throwable{

		DescodificadorJson decoder = new DescodificadorJson();
		OrdenTrazabilidad miniOrden = decoder.DescodificadorJson(json);
		//Esta miniOrden tiene el id, firmaRecogida, transportista relleno
		int idOrden = miniOrden.getId();
		
		//Obtenemos la orden de BBDD
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad orden = bloque.getOrden(idOrden);
		
		if(orden!=null) {
			//Avisar al equipo8 (equipo del sensor) que empieza un transporte
			//equipo8.model.SensorStatic.iniciarTransporte(idOrden, orden.getIdPedido());
			
			//Hay que rellenar los campos que tiene miniOrden en orden y cambiar el estado
			orden.setFirmaRecogida(miniOrden.getFirmaRecogida());
			orden.setTransportista(miniOrden.getTransportista());
			//Cambiamos el estado a en proceso de entrega(3) e indicamos que ya no necesita transportista
			orden.setEstado(3);
			orden.setNecesitaTransportista(false);
			
			//Ahora hay que guardar todos los cambios en BBDD
			bloque.guardarOrden(orden);
			
			return CodificadorJSON.crearJSON(orden);
		}
		else {
			return "ERROR: no existe la orden asociada a este ID";
		}
		
	}
	
	//PARA EQUIPO 3: TRANSPORTISTAS
	@Scope("request")
	@RequestMapping("/entregadaOrden")
	@ResponseBody
	public String entregadaOrden(HttpServletResponse response,
			@RequestParam(name="json", required=true) String json) throws Throwable{
		
		DescodificadorJson decoder = new DescodificadorJson();
		OrdenTrazabilidad miniOrden = decoder.DescodificadorJson(json);
		//miniOrden tiene los campos id, firmaEntrega y idRegistro
		int idOrden = miniOrden.getId();
		
		//Obtenemos la orden de BBDD
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad orden = bloque.getOrden(idOrden);
	
		
		if(orden!=null) {
			//Avisamos al equipo8(equipo del sensor) de que acaba el transporte
			int idRegistro = equipo8.model.SensorStatic.idUltimoRegistro();
			
			//Hay que rellenar orden con los campos de miniOrden y el idRegistro
			orden.setFirmaEntrega(miniOrden.getFirmaEntrega());
			orden.setIdRegistro(idRegistro);
			
			//Hay que cambiar el estado
			orden.setEstado(4);
			
			//Ahora hay que guardar todos los cambios en BBDD
			bloque.guardarOrden(orden);
			
			return CodificadorJSON.crearJSON(orden);
		}
		else {
			return "ERROR: no existe la orden asociada a este ID";
		}
	} 
		
}