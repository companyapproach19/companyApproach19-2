package com.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import equipo7.model.ListaPedidos;
import equipo7.model.OrdenTrazabilidad;

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
/*
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/creaPedido")
	@ResponseBody
	//Devuelve y recibe un json
	public String creaOrden(
			@RequestParam(name="json", required=true) String json) {
		
		Main_pedidos pedido = new Main_pedidos(json);
        BlockchainServices bloque = new BlockchainServices();
        Orden origen = pedido.crear_pedido();
        pedido.OrdenTrazabilidad.setOrigenOrdenes(origen);
        
		//TODO: verificar pedido
		//if(pedido.verificar_pedido()) {
			int id = 0;
			boolean yaExiste = true;
			try {
        id = equipo5.dao.metodosCompany.idOrdenTrazabilidad();
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      */
			/*//TODO: sobra la condicion de id==0 no??
			while (id == 0 && yaExiste) {
				// Obtiene un numero aleatorio entre 1 y 999999,
				// que sera el ID del pedido a la hora de crearse
				id = ThreadLocalRandom.current().nextInt(1, 1000000);
				yaExiste = equipo5.dao.metodosCompany.existeIdOrdenTrazabilidad(id);
			}*/
  /*
			pedido.OrdenTrazabilidad.setId(id);
			
			//Rellenar listas de pedidos padre y pedidos hijo
			ListaPedidos pendientes = this.pedidosPendientes(pedido.OrdenTrazabilidad.getActorOrigen().getId());
			
			if(pendientes!=null && pendientes.getListaIDs().length==1){
				//Si el origen de este pedido tiene algun pedido pendiente,
				//entendemos que usara este pedido para corresponder al anterior
				
				pedido.OrdenTrazabilidad.setPadres(pendientes.getListaIDs()[0]);
				//Ahora hay que insertar en la lista de hijos del padre a esta orden
				//Obtenemos el objeto del padre
				bloque.getTraspaso(pendientes.getListaIDs()[0]).setHijos(id);
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
				@RequestParam(name="idActor", required=true) String idActor) {
			
			//Obtenemos los pedidos de trazabilidad
			BlockchainServices bloque = new BlockchainServices();
			ArrayList<OrdenTrazabilidad> pedidos = bloque.extraerPedido(idActor);
			
			if(pedidos!=null && pedidos.size()>0) {
				ListaPedidos pedidosNoAceptados = new ListaPedidos();
				//Se necesitan aquellos pedidos pendientes por aceptar una persona
				Iterator<OrdenTrazabilidad> it = pedidos.iterator();
				while(it.hasNext()) {
					//Hay que mirar que el actor sea destino
					OrdenTrazabilidad actual = it.next();
					if(actual.getActorDestino().getId().compareTo(idActor)==0) {
						//El estado del pedido cuando no ha sido aceptado
						if(actual.getEstado()==0) {
							pedidosNoAceptados.anyadePedido(actual.getId());
						}
					}
				}
				
				//Devolver lista de identificadores
				return CodificadorJSON.crearJSONlista(pedidosNoAceptados);
				
			}
			else return "ERROR: No tiene pedidos pendientes por aceptar";
			
		}
	
	private ListaPedidos pedidosPendientes(String idActor){
			//Obtenemos los pedidos de trazabilidad
			BlockchainServices bloque = new BlockchainServices();
			ArrayList<OrdenTrazabilidad> pedidos = bloque.extraerPedido(idActor);
			
			if(pedidos!=null && pedidos.size()>0) {
				ListaPedidos pedidosEnProceso = new ListaPedidos();
				//Se necesitan aquellos pedidos pendientes por aceptar una persona
				Iterator<OrdenTrazabilidad> it = pedidos.iterator();
				while(it.hasNext()) {
					//Hay que mirar que el actor sea destino
					OrdenTrazabilidad actual = it.next();
					if(actual.getActorDestino().getId().compareTo(idActor)==0) {
						//El estado del pedido debe ser en proceso
						if(actual.getEstado()==1) {
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
				@RequestParam(name="idActor", required=true) String idActor) {
			
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
		
		//NECESARIO PARA TRAZABILIDAD:
        BlockchainServices bloque = new BlockchainServices();
        OrdenTrazabilidad pedido = bloque.getTraspaso(id);
		
		//Main_pedidos pedido = new Main_pedidos(json);
        int estado;
		//Para cambiar el estado del pedido
        if(pedido.getOrigenOrdenes()!=null) {
        	estado = pedido.getOrigenOrdenes().aceptarPedido(pedido.getEstado());
        }
        else {
        	Orden origenOrden = new Orden();
        	pedido.setOrigenOrdenes(origenOrden);
        	estado = origenOrden.aceptarPedido(pedido.getEstado());
        }
        pedido.setEstado(estado);
        bloque.guardarOrden(pedido);
        
        return CodificadorJSON.crearJSON(pedido);
	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/listoPedido")
	@ResponseBody
	public String listoPedido(
			@RequestParam(name="id", required=true) int id) {
		
		//NECESARIO PARA TRAZABILIDAD:
        BlockchainServices bloque = new BlockchainServices();
        OrdenTrazabilidad pedido = bloque.getTraspaso(id);
		//Main_pedidos pedido = new Main_pedidos(json);
		//Hay que comparar los identificadores de los ordentrazabilidad
		//Dichos ordenTrazabilidad son: el del json y el de los arrays
		//Orden origen = pedido.OrdenTrazabilidad.getOrigenOrdenes();
		//Orden origen = this.peticiones.get(pedido.OrdenTrazabilidad.getId());
		
        
		//Para cambiar el estado del pedido
        if(pedido.getOrigenOrdenes()!=null) {
        	pedido.getOrigenOrdenes().listoParaEntregar(pedido.getEstado(),pedido.getActorOrigen(),pedido.getActorDestino());
        }
        else {
        	Orden origenOrden = new Orden();
        	pedido.setOrigenOrdenes(origenOrden);
        	origenOrden.listoParaEntregar(pedido.getEstado(),pedido.getActorOrigen(),pedido.getActorDestino());
        }
		pedido.setEstado(2);
        bloque.guardarOrden(pedido);
        
        return CodificadorJSON.crearJSON(pedido);
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
		int estado;
		//Orden origen = pedido.OrdenTrazabilidad.getOrigenOrdenes();
		//Para cambiar el estado del pedido
		if(pedido.OrdenTrazabilidad.getOrigenOrdenes()!=null) {
			estado = pedido.OrdenTrazabilidad.getOrigenOrdenes().firmadoRecogida(pedido.OrdenTrazabilidad.getEstado());
		}
		else {
			Orden origenOrden = new Orden();
			pedido.OrdenTrazabilidad.setOrigenOrdenes(origenOrden);
			estado = origenOrden.firmadoRecogida(pedido.OrdenTrazabilidad.getEstado());
		}
		
		pedido.OrdenTrazabilidad.setEstado(estado);
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
		int estado;
		//Orden origen = pedido.OrdenTrazabilidad.getOrigenOrdenes();
		//Orden origen = this.peticiones.get(pedido.OrdenTrazabilidad.getId());
		//Para cambiar el estado del pedido
		if(pedido.OrdenTrazabilidad.getOrigenOrdenes()!=null) {
			estado = pedido.OrdenTrazabilidad.getOrigenOrdenes().firmadoEntrega(pedido.OrdenTrazabilidad.getEstado());
		}
		else {
			Orden origenOrden = new Orden();
			pedido.OrdenTrazabilidad.setOrigenOrdenes(origenOrden);
			estado = origenOrden.firmadoEntrega(pedido.OrdenTrazabilidad.getEstado());
		}
		pedido.OrdenTrazabilidad.setEstado(estado);
		//NECESARIO PARA TRAZABILIDAD:
        BlockchainServices bloque = new BlockchainServices();
        bloque.guardarOrden(pedido.OrdenTrazabilidad);
        
        return CodificadorJSON.crearJSON(pedido.OrdenTrazabilidad);
	} 
		*/
}
