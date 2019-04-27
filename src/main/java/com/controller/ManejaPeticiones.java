package com.controller;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import equipo7.model.ListaOrdenes;
import equipo7.model.OrdenTrazabilidad;

import equipo7.otros.DescodificadorJson;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//NECESARIOS PARA TRAZABILIDAD:
import equipo6.otros.BlockchainServices;
import equipo7.otros.CodificadorJSON;
import equipo7.otros.Main_pedidos;
import equipo7.otros.Orden;

@Controller
@SpringBootApplication
public class ManejaPeticiones {

	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/creaPedido")
	@ResponseBody
	
	//Recibe el json inicial con id -1 y devuelve un json
	public String creaOrden(
			@RequestParam(name="json", required=true) String json) throws Throwable {
		
		//Main_pedido basicamente descodifica el json
		Main_pedidos pedido = new Main_pedidos(json);
		BlockchainServices bloque = new BlockchainServices();
		
        Orden origen = pedido.crear_pedido();
        pedido.OrdenTrazabilidad.setOrigenOrdenes(origen);
        
		//TODO: verificar pedido
		//if(pedido.verificar_pedido()) {
		int id = 0;
		boolean yaExiste = true;
		id = equipo5.dao.metodosCompany.idOrdenTrazabilidad();
		/*TODO: sobra la condicion de id==0 no??
		while (id == 0 && yaExiste) {
			// Obtiene un numero aleatorio entre 1 y 999999,
			// que sera el ID del pedido a la hora de crearse
			id = ThreadLocalRandom.current().nextInt(1, 1000000);
			yaExiste = equipo5.dao.metodosCompany.existeIdOrdenTrazabilidad(id);
		}*/
		pedido.OrdenTrazabilidad.setId(id);
		
		//PEDIDOS PADRE E HIJO
		ListaOrdenes pendientes = this.ordenesPendientes(pedido.OrdenTrazabilidad.getActorOrigen().getId());
		
		if(pendientes!=null && pendientes.getListaIDs().size()>0){
			//Si el origen de este pedido tiene algun pedido pendiente,
			//entendemos que usara este pedido para corresponder al anterior
			int padre = pendientes.getListaIDs().get(0);
			pedido.OrdenTrazabilidad.setPadres(padre);
			
			//Ahora hay que insertar en la lista de hijos del padre a esta orden
			//Obtenemos el objeto del padre
			//TODO: se actualiza el padre?????
			OrdenTrazabilidad padreOrden = bloque.getTraspaso(padre); 
			if(padreOrden!=null) {
				padreOrden.setHijos(id);
				//ESTO LO GUARDARA
				bloque.guardarOrden(padreOrden);
			}
		}
		
		//NECESARIO PARA TRAZABILIDAD:
		//La orden se guardara en la base de datos
	    bloque.guardarOrden(pedido.OrdenTrazabilidad);
	        
		return CodificadorJSON.crearJSON(pedido.OrdenTrazabilidad);
		//}
		//else return "ERROR";
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
		//TODO: no debería throws...
		ArrayList<OrdenTrazabilidad> pedidos = bloque.extraerPedido(idActor);
		
		if(pedidos!=null && pedidos.size()>0) {
			
			ListaOrdenes pedidosNoAceptados = new ListaOrdenes();
			//Se necesitan aquellos pedidos pendientes por aceptar una persona
			Iterator<OrdenTrazabilidad> it = pedidos.iterator();
			while(it.hasNext()) {
				//Hay que asegurarse que el actor sea destino
				OrdenTrazabilidad actual = it.next();
				if(actual.getActorDestino().getId().compareTo(idActor)==0) {
					//El estado del pedido cuando no ha sido aceptado es 0
					if(actual.getEstado()==0) {
						pedidosNoAceptados.anyadeOrden(actual.getId());
					}
				}
			}
				
			//Devolver lista de identificadores
			return CodificadorJSON.crearJSONlista(pedidosNoAceptados);		
		}
		else return "ERROR: No tiene pedidos pendientes por aceptar";
			
	}
	
	//Devuelve lista de ordenes recibidos y estan en proceso
	private ListaOrdenes ordenesPendientes(String idActor) throws ClassNotFoundException, SQLException{
		//Obtenemos los pedidos de trazabilidad
		BlockchainServices bloque = new BlockchainServices();
		ArrayList<OrdenTrazabilidad> pedidos = bloque.extraerPedido(idActor);
		
		if(pedidos!=null && pedidos.size()>0) {
			ListaOrdenes pedidosEnProceso = new ListaOrdenes();
			
			//Se necesitan aquellos pedidos pendientes por aceptar una persona
			Iterator<OrdenTrazabilidad> it = pedidos.iterator();
			while(it.hasNext()) {
				//Hay que mirar que el actor sea destino
				OrdenTrazabilidad actual = it.next();
				if(actual.getActorDestino().getId().compareTo(idActor)==0) {
					//El estado del pedido debe ser en proceso
					if(actual.getEstado()==1) {
						pedidosEnProceso.anyadeOrden(actual.getId());
					}
				}
			}
			return pedidosEnProceso;
		}
		return null;
	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/ordenesEnProceso")
	@ResponseBody
	// Recibe el ID de un actor y devuelve un JSON con las ordenes en proceso de ese actor
	public String ordenesEnProceso(
			@RequestParam(name="idActor", required=true) String idActor) throws ClassNotFoundException, SQLException {
		
		ListaOrdenes pedidosEnProceso=this.ordenesPendientes(idActor);
		if(pedidosEnProceso!=null){
			//Devolver lista de identificadores
			return CodificadorJSON.crearJSONlista(pedidosEnProceso);
		}
		else return "ERROR: No tiene ordenes en proceso";
	
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