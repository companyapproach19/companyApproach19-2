package com.controller;

import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

import equipo4.model.Lote;
import equipo4.model.MateriaPrima;
import equipo5.dao.metodosCompany;
import equipo6.model.Actor;
import equipo6.otros.BlockchainServices;
@Controller
@SpringBootApplication
public class StockController {
	
	String idActor;

	
	/*
	 * dado un actor y una materia prima devuelve la cantidad de esa materia prima
	 */
	public double getCantidadStock (Actor actor, MateriaPrima mp){
		return metodosCompany.extraerStockMateriaPrima(actor,mp);		
	}
	
	/*
	 * dado un actor y un lote devuelve el stock de un tipo de lote
	 */
	public int getStockLote(Actor actor, Lote lote) {
		
		//nos devuleve una lista con todos los lotes de un actor, luego tenemos que buscar
		//los lotes del tipo lote que nos pasan
		LinkedList<Lote> lotes= metodosCompany.extraerStockLote(actor);
		Iterator<Lote> iter = lotes.iterator();
		int resultado = 0;
		while(iter.hasNext()){			
			if(lote.getTipo().equals(iter.next().getTipo())) {
				resultado++;
			}
		}
		return resultado;
	}
	
	
	/*
	 * metodo para cambiar la cantidad de stock de materia prima
	 */	
	public void setCantidadMateriaPrima(Actor actor, MateriaPrima mp, double cantidad) {
		metodosCompany.insertarStockMateriaPrima(actor, mp, cantidad);		
	}
	
	/*
	 * metodo para cambiar la cantidad de stock de un lote, se insertar de uno en uno.
	 */	
	public void setCantidadLote(Actor actor, Lote lote) {
		metodosCompany.insertarStockLote(actor, lote);
	}
	
	
	@Scope("request")
	@RequestMapping("/dameStockActor")
	@ResponseBody
	public String getStockActor(
			HttpServletRequest request,
			Model model) throws Exception {
		
		
		idActor = null;
		
		for(Cookie c : request.getCookies()) {
			System.out.println(c.getName()+"    "+c.getValue());
			if(c.getName().equals("id")) 
			{
				idActor = c.getValue();
				break;
			}
		}
		
		
		
		JsonObject json_resp;
		Actor actor;
		
		json_resp = new JsonObject();
		
		
		//actor = metodosCompany.getCadenaActores().get_actor_by_id(idActor);
		actor=new Actor(idActor, "Agricultor", "asdasd", "rmj@g.cm", 0, "41.5N 2.0W","Agricultor A", "c/mevoyamorir", "1234567C");
		
		json_resp.addProperty("nomUsuario", actor.getNombreUsuario());
		json_resp.addProperty("email", actor.getEmail());
		json_resp.addProperty("tipoActor", actor.getTipoActor());
		JsonObject stock = new JsonObject();
		stock.addProperty("malta_palida", metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("maltaBasePalida")));
		stock.addProperty("malta_tostada", metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("maltaTostada")));
		stock.addProperty("malta_negra", metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("maltaNegra")));
		stock.addProperty("malta_crystal", metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("maltaCrystal")));
		stock.addProperty("malta_chocolate", metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("maltaChocolate")));
		stock.addProperty("malta_caramelo",metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("maltaCaramelo")));
		stock.addProperty("malta_pilsner", metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("maltaPilsner")));
		stock.addProperty("malta_munich", metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("maltaMunich")));
		stock.addProperty("lupulo_perle", metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("lupuloPerle")));
		stock.addProperty("lupulo_tettnanger", metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("lupuloTettnanger")));
		stock.addProperty("lupulo_centennial", metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("lupuloCentennial")));
		stock.addProperty("levadura_ale", metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("levaduraAle")));
		stock.addProperty("levadura_lagger", metodosCompany.extraerStockMateriaPrima(
				actor, 
				metodosCompany.extraerMateriaPrima("levaduraAle")));
		stock.addProperty("lotes_pilsner", getStockLote(actor, "aaais"));  //TODO
		stock.addProperty("lotes_stout", getStockLote(actor, "aaaais"));   //TODO
		//insertarStockMateriaPrima(
		//insertarStockLote(Actor, Lote)
		
		
		
		json_resp.add("stock", stock);
		//obtener el stock asociado a un actor consultando a la base de datos
		//metodosCompani.dameStockActor(string idActor)
		
		return json_resp.toString();		
	}
	
	@Scope("request")
	@RequestMapping("/get_trazabilidad")
	@ResponseBody
	public String get_trazabilidad(HttpServletResponse response,
			@RequestParam(name="id_pedido", required=true) int id_pedido,
			Model model) throws SQLException 
	{
		BlockchainServices bcs;
		
		bcs = new BlockchainServices();
		
		System.out.println(id_pedido);
		
		return bcs.get_trazabilidad(id_pedido);
	}
	
	@Scope("request")
	@RequestMapping("/get_trazabilidad")
	@ResponseBody
	public String get_trazabilidad(HttpServletResponse response,
			@RequestParam(name="id_pedido", required=true) int id_pedido,
			Model model) throws SQLException 
	{
		BlockchainServices bcs;
		
		bcs = new BlockchainServices();
		
		System.out.println(id_pedido);
		
		return bcs.get_trazabilidad(id_pedido);
	}

}
