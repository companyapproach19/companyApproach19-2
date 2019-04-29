package com.controller;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import equipo7.model.ListaOrdenes;
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
import equipo7.otros.Main_pedidos;
import equipo7.otros.Orden;
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
		int id = equipo5.dao.metodosCompany.idOrdenTrazabilidad();
		//Creamos el objeto orden
		OrdenTrazabilidad orden = new OrdenTrazabilidad(id, inicial.getActorOrigen(),
				inicial.getActorDestino(), inicial.getProductosPedidos());
		orden.setIdPedido(inicial.getIdPedido());
		
		//Guardamos en la cadena la orden
		BlockchainServices bloque = new BlockchainServices();
	    bloque.guardarOrden(orden);
	        
		return CodificadorJSON.crearJSON(orden);
		
	}

	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/obtenerOrden")
	@ResponseBody
	// Recibe el ID de una orden y devuelve su JSON asociado
	public String obtenerOrden(@RequestParam(name="id", required=true) String id) {

		int idInt = Integer.parseInt(id);

		//Obtenemos el pedido de trazabilidad
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad pedido = bloque.getTraspaso(idInt);
		
		if (pedido != null)
			return CodificadorJSON.crearJSON(pedido);
		else
			return "ERROR: No se pudo obtener la orden";
	}
	
	
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesPendientesPorAceptar")
	@ResponseBody
	// Recibe el ID de un actor y devuelve un JSON con las ordenes pendientes por aceptar de ese actor
	public String ordenesPendientesPorAceptar(
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
		
		//Obtenemos los pedidos de trazabilidad
		BlockchainServices bloque = new BlockchainServices();
		
		ArrayList<OrdenTrazabilidad> ordenes = bloque.extraerPedido(idActor);
		ArrayList<Integer> ordenesPendientes = new ArrayList<Integer>();
		
		if(ordenes!=null && ordenes.size()>0) {
			
			Iterator<OrdenTrazabilidad> it = ordenes.iterator();
			while(it.hasNext()) {
				//Hay que asegurarse que el actor sea destino
				OrdenTrazabilidad actual = it.next();
				if(actual.getActorDestino().getId().compareTo(idActor)==0) {
					//El estado del pedido cuando no ha sido aceptado es 0
					if(actual.getEstado()==0) {
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
	@RequestMapping("/ordenesEnProceso")
	@ResponseBody
	// Recibe el ID de un actor y devuelve un JSON con las ordenes en proceso de ese actor
	public String ordenesEnProceso(
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
		
		//Obtenemos los pedidos de trazabilidad
				BlockchainServices bloque = new BlockchainServices();
				
				ArrayList<OrdenTrazabilidad> ordenes = bloque.extraerPedido(idActor);
				ArrayList<Integer> ordenesPendientes = new ArrayList<Integer>();
				
				if(ordenes!=null && ordenes.size()>0) {
					
					Iterator<OrdenTrazabilidad> it = ordenes.iterator();
					while(it.hasNext()) {
						//Hay que asegurarse que el actor sea destino
						OrdenTrazabilidad actual = it.next();
						if(actual.getActorDestino().getId().compareTo(idActor)==0) {
							//El estado del pedido cuando no ha sido aceptado es 0
							if(actual.getEstado()==1) {
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
	@RequestMapping("/aceptarOrden")
	@ResponseBody
	//Recibe una lista de ids de las ordenes que va a aceptar
	public String aceptarOrden(@RequestParam(name="id", required=true) String id) {

		//NECESARIO PARA TRAZABILIDAD:
		BlockchainServices bloque = new BlockchainServices();
		
		DescodificadorJson decoder = new DescodificadorJson();
		ListaOrdenes ids = decoder.DescodificadorJSONlista(id);
		if(ids!=null ) {
			ArrayList<Integer> lista = ids.getListaIDs();
			
			//Hay que cambiar el estado de todos los pedidos que existan
			for (int i = 0; i < lista.size(); i++) {
				int idInt = lista.get(i);
				if(idInt!=-1) {
					OrdenTrazabilidad pedido = bloque.getTraspaso(idInt);

					int estado;
					//Para cambiar el estado del pedido
					if (pedido.getOrigenOrdenes() != null) {
						estado = pedido.getOrigenOrdenes().aceptarPedido(pedido.getEstado());
					} 
					else {
						Orden origenOrden = new Orden();
						pedido.setOrigenOrdenes(origenOrden);
						estado = origenOrden.aceptarPedido(pedido.getEstado());
					}
					pedido.setEstado(estado);
					try {
						bloque.guardarOrden(pedido);
					} 
					catch (Throwable e) { e.printStackTrace(); }
				}
			}
		}
        
        return "Success";
	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/listaOrden")
	@ResponseBody
	//Recibe una lista de ids de las ordenes que va a poner como listas
	public String listaOrden(@RequestParam(name="id", required=true) String id) {

		//NECESARIO PARA TRAZABILIDAD:
		BlockchainServices bloque = new BlockchainServices();
		
		DescodificadorJson decoder = new DescodificadorJson();
		ListaOrdenes ids = decoder.DescodificadorJSONlista(id);
		if(ids!=null) {
			ArrayList<Integer> lista = ids.getListaIDs();

			for (int i = 0; i < lista.size(); i++) {

				int idInt = lista.get(i);

				OrdenTrazabilidad pedido = bloque.getTraspaso(idInt);
				boolean necesitaTransporte;
				//Para cambiar el estado del pedido
				if (pedido.getOrigenOrdenes() != null) {
					necesitaTransporte = pedido.getOrigenOrdenes().listoParaEntregar(pedido.getEstado(), pedido.getActorOrigen(), pedido.getActorDestino());
				} 
				else {
					Orden origenOrden = new Orden();
					pedido.setOrigenOrdenes(origenOrden);
					necesitaTransporte = origenOrden.listoParaEntregar(pedido.getEstado(), pedido.getActorOrigen(), pedido.getActorDestino());
				}
				//Cuando el pedido cambia de en proceso a listo para entregar, 
				//hay que activar necesitaTransportista en caso de ser necesario el transportista
				pedido.setEstado(2);
				pedido.setNecesitaTransportista(necesitaTransporte);
				try {
					bloque.guardarOrden(pedido);
				} catch (Throwable e) { e.printStackTrace(); }
			}
		}
        
        return "Success";
	}
	
	
	//PARA EQUIPO 3: TRANSPORTISTAS
	@Scope("request")
	@RequestMapping("/recogidaOrden")
	@ResponseBody
	//Recibe un json con la firma de recogida y el actor que va a transportar la orden
	public String recogidaOrden(@RequestParam(name="json", required=true) String json) throws Throwable {
		
		DescodificadorJson decoder = new DescodificadorJson();
		OrdenTrazabilidad pedido = decoder.DescodificadorJson(json);
		
		if(pedido!=null) {
			int estado;
			//Para cambiar el estado del pedido
			if(pedido.getOrigenOrdenes()!=null) {
				estado = pedido.getOrigenOrdenes().firmadoRecogida(pedido.getEstado());
			}
			else {
				Orden origenOrden = new Orden();
				pedido.setOrigenOrdenes(origenOrden);
				estado = origenOrden.firmadoRecogida(pedido.getEstado());
			}
			
			pedido.setEstado(estado);
			//NECESARIO PARA TRAZABILIDAD:
	        BlockchainServices bloque = new BlockchainServices();
	        bloque.guardarOrden(pedido);
	        
	        return CodificadorJSON.crearJSON(pedido);
		}
		else return "ERROR: json no valido";
	}
	
	//PARA EQUIPO 3: TRANSPORTISTAS
	@Scope("request")
	@RequestMapping("/entregadaOrden")
	@ResponseBody
	//Recibe un json con la firma de entrega y los datos del registro
	public String entregadaOrden(@RequestParam(name="json", required=true) String json) throws Throwable {
		
		DescodificadorJson decoder = new DescodificadorJson();
		OrdenTrazabilidad pedido = decoder.DescodificadorJson(json);
		if(pedido!=null) {
			int estado;
			
			//Para cambiar el estado del pedido
			if(pedido.getOrigenOrdenes()!=null) {
				estado = pedido.getOrigenOrdenes().firmadoEntrega(pedido.getEstado());
			}
			else {
				Orden origenOrden = new Orden();
				pedido.setOrigenOrdenes(origenOrden);
				estado = origenOrden.firmadoEntrega(pedido.getEstado());
			}
			
			pedido.setEstado(estado);
			//NECESARIO PARA TRAZABILIDAD:
	        BlockchainServices bloque = new BlockchainServices();
	        bloque.guardarOrden(pedido);
	        
	        return CodificadorJSON.crearJSON(pedido);
		}
		
		else return "ERROR: json no valido";
	} 
		
}