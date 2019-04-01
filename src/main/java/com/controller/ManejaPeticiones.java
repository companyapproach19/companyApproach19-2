package com.controller;

import java.util.ArrayList;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
		//TODO: configurar identificador
		if(pedido.verificar_pedido()) {
			int id;
			while (id == 0) {

			}
			pedido.OrdenTrazabilidad.setId(id);
			
			//NECESARIO PARA TRAZABILIDAD:
			//La orden se guardara en la base de datos
	        BlockchainServices bloque = new BlockchainServices();
	        bloque.guardarOrden(pedido.OrdenTrazabilidad);
	        
			return CodificadorJSON.crearJSON(pedido.OrdenTrazabilidad);
		}
		else return "ERROR";
	}
	
	//PARA EQUIPO 2: VISTAS
	@Scope("request")
	@RequestMapping("/aceptarPedido")
	@ResponseBody
	public String aceptarPedido(
			@RequestParam(name="json", required=true) String json) {
		
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
			@RequestParam(name="json", required=true) String json) {
		
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
