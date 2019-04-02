package com.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

import equipo5.dao.metodosCompany;
import equipo6.model.Actor;

public class StockController {

	@Scope("request")
	@RequestMapping("/dameStockActor")
	@ResponseBody
	public String getStockActor(
			@RequestParam(name="id", required=true) String idActor,
			Model model) throws Exception {
		
		JsonObject json_resp;
		Actor actor;
		
		json_resp = new JsonObject();
		
		
		actor = metodosCompany.getCadenaActores().get_actor_by_id(idActor);
		
		json_resp.addProperty("nomUsuario", actor.getNombreUsuario());
		json_resp.addProperty("email", actor.getEmail());
		json_resp.addProperty("tipoActor", actor.getTipoActor());
		json_resp.add("stock", new JsonObject());
		//obtener el stock asociado a un actor consultando a la base de datos
		//metodosCompani.dameStockActor(string idActor)
		
		return json_resp.toString();
		
		
	}

}
