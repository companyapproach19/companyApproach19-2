package com.controller;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;


import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo7.otros.DescodificadorJson;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	//Recibe el json inicial 
	public String crearOrden(
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
		        
			return "OK";
		}
		else {
			return "ERROR: no se pudo crear la orden";
		}
		
	}

	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/obtenerOrden")
	@ResponseBody
	// Recibe el ID de una orden y devuelve su JSON asociado
	public String obtenerOrden(@RequestParam(name="id", required=true) String id) throws ClassNotFoundException, SQLException{

		int idInt = Integer.parseInt(id);

		//Obtenemos el pedido de trazabilidad
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad pedido = bloque.getOrden(idInt);
		
		if (pedido != null)
			return CodificadorJSON.crearJSON(pedido);
		else
			return "ERROR: No se pudo obtener la orden";
	}
	
	
	private String ordenesPendientes(String idActor, int estado) throws ClassNotFoundException, SQLException{
		//Obtenemos los pedidos de trazabilidad
		BlockchainServices bloque = new BlockchainServices();
			
		ArrayList<OrdenTrazabilidad> ordenes = bloque.extraerOrdenesDestino(idActor);
		ArrayList<Integer> ordenesPendientes = new ArrayList<Integer>();
				
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
			return CodificadorJSON.crearJSONlista(ordenesPendientes);		
		}
		else return "null";
	}
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesPendientesPorAceptar")
	@ResponseBody
	// Recibe el ID de un actor y devuelve un JSON con las ordenes pendientes por aceptar de ese actor
	public String ordenesPendientesPorAceptar(
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
		
		return this.ordenesPendientes(idActor, 0);
			
	}
	
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesEnProceso")
	@ResponseBody
	// Recibe el ID de un actor y devuelve un JSON con las ordenes en proceso de ese actor
	public String ordenesEnProceso(
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
		
		return this.ordenesPendientes(idActor, 1);					
	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesListasParaEntregar")
	@ResponseBody
	// Recibe el ID de un actor y devuelve un JSON con las ordenes en proceso de ese actor
	public String ordenesListasParaEntregar(
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
			
		return this.ordenesPendientes(idActor, 2);
						
	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesRechazadas")
	@ResponseBody
	// Recibe el ID de un actor y devuelve un JSON con las ordenes en proceso de ese actor
	public String ordenesRechazadas(
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
				
		return this.ordenesPendientes(idActor, -1);
							
	}
		
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesEnProcesoDeEntrega")
	@ResponseBody
	// Recibe el ID de un actor y devuelve un JSON con las ordenes en proceso de ese actor
	public String ordenesEnProcesoDeEntrega(
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
						
		return this.ordenesPendientes(idActor, 3);
									
	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/aceptarOrden")
	@ResponseBody
	//Recibe una lista de ids de las ordenes que va a aceptar
	public String aceptarOrden(@RequestParam(name="id", required=true) String id) throws ClassNotFoundException, SQLException{
		
		int idInt = Integer.parseInt(id);

		//Recuperamos la orden para cambiar el estado
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad orden = bloque.getOrden(idInt);
		
		if(orden!=null) {
			orden.setEstado(1);
			bloque.guardarOrden(orden);
			return "OK";
		}
		else {
			return "ERROR: no existe la orden asociada a este ID";
		}

	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/rechazarOrden")
	@ResponseBody
	//Recibe una lista de ids de las ordenes que va a aceptar
	public String rechazarOrden(@RequestParam(name="id", required=true) String id) throws ClassNotFoundException, SQLException{
		
		int idInt = Integer.parseInt(id);
			//Recuperamos la orden para cambiar el estado
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad orden = bloque.getOrden(idInt);
		
		if(orden!=null) {
			orden.setEstado(-1);
			bloque.guardarOrden(orden);
			return "OK";
		}
		else {
			return "ERROR: no existe la orden asociada a este ID";
		}

	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/listaOrden")
	@ResponseBody
	//Recibe el ID de una orden y una lista con los IDs de los productos (lotes o materias primas)
	public String listaOrden(@RequestParam(name="id", required=true) String id) throws ClassNotFoundException, SQLException{

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
			orden.setEstado(2);
		
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
	//Recibe un JSON con el ID de una orden, la firma de recogida y los datos del transportista
	public String recogidaOrden(@RequestParam(name="json", required=true) String json) throws ClassNotFoundException, SQLException{

		DescodificadorJson decoder = new DescodificadorJson();
		OrdenTrazabilidad miniOrden = decoder.DescodificadorJson(json);
		//Esta miniOrden tiene el id, firmaRecogida, transportista relleno
		int idOrden = miniOrden.getId();
		
		//Obtenemos la orden de BBDD
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad orden = bloque.getOrden(idOrden);
		
		if(orden!=null) {
			//Avisar al equipo8 (equipo del sensor) que empieza un transporte
			equipo8.model.SensorStatic.iniciarTransporte(idOrden, orden.getIdPedido());
			
			//Hay que rellenar los campos que tiene miniOrden en orden y cambiar el estado
			orden.setFirmaRecogida(miniOrden.getFirmaRecogida());
			orden.setTransportista(miniOrden.getTransportista());
			//Ya no se necesita otro transportista
			orden.setNecesitaTransportista(false);
			//Cambiamos el estado a en proceso de entrega(3)
			orden.setEstado(3);
			
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
	//Recibe un json con la firma de entrega y los datos del registro
	public String entregadaOrden(@RequestParam(name="json", required=true) String json) throws ClassNotFoundException, SQLException{
		
		DescodificadorJson decoder = new DescodificadorJson();
		OrdenTrazabilidad miniOrden = decoder.DescodificadorJson(json);
		//miniOrden tiene los campos id, firmaEntrega y idRegistro
		int idOrden = miniOrden.getId();
		
		//Obtenemos la orden de BBDD
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad orden = bloque.getOrden(idOrden);
	
		
		if(orden!=null) {
			//Avisamos al equipo8(equipo del sensor) de que acaba el transporte
			int idRegistro = equipo8.model.SensorStatic.terminar();
			
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