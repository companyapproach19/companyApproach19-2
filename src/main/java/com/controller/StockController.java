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

import equipo5.dao.metodosCompany;
import equipo6.model.Actor;
import equipo6.otros.BlockchainServices;
@Controller
@SpringBootApplication
public class StockController {
	
	String idActor;

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
		//stock.addProperty("malta_palida", metodosCompany.extraerStockMateriaPrima(actor, MateriaPrima materiaPrima));
		stock.addProperty("malta_palida", 10);
		stock.addProperty("malta_tostada", 11);
		stock.addProperty("malta_negra", 12);
		stock.addProperty("malta_crystal", 13);
		stock.addProperty("malta_chocolate", 14);
		stock.addProperty("malta_caramelo",15);
		stock.addProperty("malta_pilsner", 16);
		stock.addProperty("malta_munich", 17);
		stock.addProperty("lupulo_perle", 18);
		stock.addProperty("lupulo_tettnager", 19);
		stock.addProperty("lupulo_centennial", 20);
		stock.addProperty("levadura_ale", 21);
		stock.addProperty("levadura_lagger", 22);
		stock.addProperty("lotes_pilsner", 0);
		stock.addProperty("lotes_stout", 0);
		
		
		
		
		json_resp.add("stock", stock);
		//obtener el stock asociado a un actor consultando a la base de datos
		//metodosCompani.dameStockActor(string idActor)
		
		return json_resp.toString();
		
		
		/*
		 {

  "nomUsuario":"nombre del actor" ,

  "email":"email asociado" ,

  "tipoActor":"tipo asociado" ,

  "stock" {

   "malta_palida": "la cantidad que tenga",

   "malta_tostada": "la cantidad que tenga",

   "malta_negra": "la cantidad que tenga",

   "malta_crystal": "la cantidad que tenga",

   "malta_chocolate": "la cantidad que tenga",

   "malta_caramelo": "la cantidad que tenga",

   "malta_pilsner": "la cantidad que tenga",

   "malta_munich": "la cantidad que tenga",

   "lupulo_perle": "la cantidad que tenga",

   "lupulo_tettnager": "la cantidad que tenga",

   "lupulo_centennial": "la cantidad que tenga",

   "levadura_ale": "la cantidad que tenga",

   "levadura_lagger": "la cantidad que tenga",    
   
   "lotes_pilsner":"la cantidad de lotes que tenga",

   "lotes_stout":"la cantidad de lotes que tenga",   }

}
		 */
		
		
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
