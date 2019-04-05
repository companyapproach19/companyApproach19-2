package com.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import equipo7.model.ListaPedidos;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.OrdenTrazabilidad.EstadoOrden;

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
	//Devuelve y recibe un json
	public String creaOrden(
			@RequestParam(name="json", required=true) String json) {
		
		Main_pedidos pedido = new Main_pedidos(json);
        BlockchainServices bloque = new BlockchainServices();
        
		//TODO: verificar pedido
		//if(pedido.verificar_pedido()) {
			int id = 0;
			boolean yaExiste = true;
			//TODO: sobra la condicion de id==0 no??
			while (id == 0 && yaExiste) {
				// Obtiene un numero aleatorio entre 1 y 999999,
				// que sera el ID del pedido a la hora de crearse
				id = ThreadLocalRandom.current().nextInt(1, 1000000);
				yaExiste = equipo5.dao.metodosCompany.existeIdOrdenTrazabilidad(id);
			}
			pedido.OrdenTrazabilidad.setId(id);
			
			//Rellenar listas de pedidos padre y pedidos hijo
			ListaPedidos pendientes = this.pedidosPendientes(pedido.OrdenTrazabilidad.getActorOrigen().getId());
			
			if(pendientes!=null && pendientes.getListaIDs().length==1){
				//Si el origen de este pedido tiene algun pedido pendiente,
				//entendemos que usara este pedido para corresponder al anterior
				ArrayList<Integer> padre = new ArrayList<Integer>();
				padre.add(pendientes.getListaIDs()[0]);
				pedido.OrdenTrazabilidad.setPadres(padre);
				//Ahora hay que insertar en la lista de hijos del padre a esta orden
				ArrayList<Integer> hijo = new ArrayList<Integer>();
				hijo.add(id);
				//Obtenemos el objeto del padre
				bloque.getTraspaso(pendientes.getListaIDs()[0]).setHijos(hijo);
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
	@RequestMapping("/obtenerPedido")
	@ResponseBody
	// Recibe el ID de un pedido y devuelve su JSON asociado
	public String obtenerOrden(
			@RequestParam(name="id", required=true) int id) {

		// Obtenemos el pedido de trazabilidad
		BlockchainServices bloque = new BlockchainServices();
		OrdenTrazabilidad pedido = bloque.getTraspaso(id);
		if (pedido != null)
			return CodificadorJSON.crearJSON(pedido);
		else
			return "ERROR: No se pudo obtener el pedido";
	}
	
	//PARA EQUIPO 2: VISTAS
		@Scope("request")
		@RequestMapping("/pedidosNoAceptados")
		@ResponseBody
		// Recibe el ID de un actor y devuelve un JSON con los pedidos no aceptados de ese actor
		public String pedidosNoAceptados(
				@RequestParam(name="idActor", required=true) int idActor) {
			
			//Obtenemos los pedidos de trazabilidad
			BlockchainServices bloque = new BlockchainServices();
			ArrayList<OrdenTrazabilidad> pedidos = bloque.getLista(idActor);
			
			if(pedidos!=null && pedidos.size()>0) {
				ListaPedidos pedidosNoAceptados = new ListaPedidos();
				//Se necesitan aquellos pedidos pendientes por aceptar una persona
				Iterator<OrdenTrazabilidad> it = pedidos.iterator();
				while(it.hasNext()) {
					//Hay que mirar que el actor sea destino
					OrdenTrazabilidad actual = it.next();
					if(actual.getActorDestino().getId()==idActor) {
						//El estado del pedido cuando no ha sido aceptado
						if(actual.getEstado()==null) {
							pedidosNoAceptados.anyadePedido(actual.getId());
						}
					}
				}
				
				//Devolver lista de identificadores
				return CodificadorJSON.crearJSONlista(pedidosNoAceptados);
				
			}
			else return "ERROR: No tiene pedidos pendientes por aceptar";
			
		}
	
		private ListaPedidos pedidosPendientes(int idActor){
			//Obtenemos los pedidos de trazabilidad
			BlockchainServices bloque = new BlockchainServices();
			ArrayList<OrdenTrazabilidad> pedidos = bloque.getLista(idActor);
			
			if(pedidos!=null && pedidos.size()>0) {
				ListaPedidos pedidosEnProceso = new ListaPedidos();
				//Se necesitan aquellos pedidos pendientes por aceptar una persona
				Iterator<OrdenTrazabilidad> it = pedidos.iterator();
				while(it.hasNext()) {
					//Hay que mirar que el actor sea destino
					OrdenTrazabilidad actual = it.next();
					if(actual.getActorDestino().getId()==idActor) {
						//El estado del pedido debe ser en proceso
						if(actual.getEstado().compareTo(EstadoOrden.EN_PROCESO)==0) {
							pedidosEnProceso.anyadePedido(actual.getId());
						}
					}
				}
				return pedidosEnProceso;
			}
			return null;
		}
	
	//PARA EQUIPO 2: VISTAS
		@Scope("request")
		@RequestMapping("/pedidosEnProceso")
		@ResponseBody
		// Recibe el ID de un actor y devuelve un JSON con los pedidos en proceso de ese actor
		public String pedidosEnProceso(
				@RequestParam(name="idActor", required=true) int idActor) {
			
			ListaPedidos pedidosEnProceso=this.pedidosPendientes(idActor);
			if(pedidosEnProceso!=null){
			
				//Devolver lista de identificadores
				return CodificadorJSON.crearJSONlista(pedidosEnProceso);
				
			}
			else return "ERROR: No tiene pedidos en proceso";
		
		}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/aceptarPedido")
	@ResponseBody
	public String aceptarPedido(
			@RequestParam(name="id", required=true) int id) {
		
		Main_pedidos pedido = new Main_pedidos(json);
		Orden origen = pedido.crear_pedido();
		//Para cambiar el estado del pedido
		origen.aceptarPedido();
		//this.peticiones.add(origen);
		pedido.OrdenTrazabilidad.setOrigenOrdenes(origen);
		
		//NECESARIO PARA TRAZABILIDAD:
        BlockchainServices bloque = new BlockchainServices();
        bloque.guardarOrden(pedido.OrdenTrazabilidad);
        
        return CodificadorJSON.crearJSON(pedido.OrdenTrazabilidad);
	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/listoPedido")
	@ResponseBody
	public String listoPedido(
			@RequestParam(name="id", required=true) int id) {
		
		Main_pedidos pedido = new Main_pedidos(json);
		//Hay que comparar los identificadores de los ordentrazabilidad
		//Dichos ordenTrazabilidad son: el del json y el de los arrays
		Orden origen = pedido.OrdenTrazabilidad.getOrigenOrdenes();
		//Orden origen = this.peticiones.get(pedido.OrdenTrazabilidad.getId());
		
		//Para cambiar el estado del pedido
		origen.listoParaEntregar();
		
		
		//NECESARIO PARA TRAZABILIDAD:
        BlockchainServices bloque = new BlockchainServices();
        bloque.guardarOrden(pedido.OrdenTrazabilidad);
        
        return CodificadorJSON.crearJSON(pedido.OrdenTrazabilidad);
	}
	
	//PARA EQUIPO 3: TRANSPORTISTAS
	@Scope("request")
	@RequestMapping("/recogidoPedido")
	@ResponseBody
	public String recogidoPedido(
			@RequestParam(name="json", required=true) String json) {
		
		Main_pedidos pedido = new Main_pedidos(json);
		//Hay que compara los identificadores de los ordentrazabilidad
		//Dichos ordenTrazabilidad son: el del json y el de los arrays

		Orden origen = pedido.OrdenTrazabilidad.getOrigenOrdenes();
		//Para cambiar el estado del pedido
		origen.firmadoRecogida();
		
		
		//NECESARIO PARA TRAZABILIDAD:
        BlockchainServices bloque = new BlockchainServices();
        bloque.guardarOrden(pedido.OrdenTrazabilidad);
        
        return CodificadorJSON.crearJSON(pedido.OrdenTrazabilidad);
	}
	
	//PARA EQUIPO 3: TRANSPORTISTAS
	@Scope("request")
	@RequestMapping("/entregadoPedido")
	@ResponseBody
	public String entregadoPedido(
			@RequestParam(name="json", required=true) String json) {
		
		Main_pedidos pedido = new Main_pedidos(json);
		//Hay que compara los identificadores de los ordentrazabilidad
		//Dichos ordenTrazabilidad son: el del json y el de los arrays
		
		Orden origen = pedido.OrdenTrazabilidad.getOrigenOrdenes();
		//Orden origen = this.peticiones.get(pedido.OrdenTrazabilidad.getId());
		//Para cambiar el estado del pedido
		origen.firmadoEntrega();
		
		
		//NECESARIO PARA TRAZABILIDAD:
        BlockchainServices bloque = new BlockchainServices();
        bloque.guardarOrden(pedido.OrdenTrazabilidad);
        
        return CodificadorJSON.crearJSON(pedido.OrdenTrazabilidad);
	} 
		
}
