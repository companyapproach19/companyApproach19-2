package com.controller;

import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.validator.cfg.context.ReturnValueConstraintMappingContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import equipo4.model.Lote;
import equipo4.model.MateriaPrima;
import equipo5.dao.metodosCompany;
import equipo5.model.NotInDatabaseException;
import equipo6.model.Actor;
import equipo6.model.CadenaActores;
import equipo6.otros.BlockchainServices;
import equipo7.model.OrdenTrazabilidad;
@Controller
@SpringBootApplication
public class StockController {
	
	String idActor;

	
	/*
	 * dado un actor y una materia prima devuelve la cantidad de esa materia prima
	 */
	public static double getCantidadStock (Actor actor, MateriaPrima mp) throws ClassNotFoundException, SQLException, NotInDatabaseException{
		return metodosCompany.extraerStockMP(actor,mp);		
	}
	
	/*
	 * dado un actor y un lote devuelve el stock de un tipo de lote
	 */
	public static int getStockLote(Actor actor, Lote lote) {
		
		//nos devuleve una lista con todos los lotes de un actor, luego tenemos que buscar
		//los lotes del tipo lote que nos pasan
		
		try {
			return metodosCompany.extraerStockLote(actor);
		} catch (ClassNotFoundException | SQLException | NotInDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	
	/*
	 * metodo para cambiar la cantidad de stock de materia prima
	 */	
	public static void setCantidadMateriaPrima(Actor actor, MateriaPrima mp, double cantidad) throws ClassNotFoundException, SQLException {
		int cantidad_entera;
		
		cantidad_entera = (int)cantidad;
		metodosCompany.insertarStockMP(actor, mp, cantidad_entera);		
	}
	
	/*
	 * metodo para cambiar la cantidad de stock de un lote, se insertar de uno en uno.
	 */	
	public static void setCantidadLote(Actor actor, Lote lote) throws ClassNotFoundException, SQLException {
		metodosCompany.insertarStockLote(actor, lote);
	}
	
	public String get_id_actor_cookie(Cookie[] lista_cookies) 
	{
		String idActor;
		
		idActor = null;
		for(Cookie c : lista_cookies) {
			System.out.println(c.getName()+"    "+c.getValue());
			if(c.getName().equals("id")) 
			{
				idActor = c.getValue();
				break;
			}
		}
		
		return idActor;
	}
	
	
	@Scope("request")
	@RequestMapping("/dameStockActor")
	@ResponseBody
	public String getStockActor(
			HttpServletRequest request,
			@RequestParam(name="usuario", required=true) String id,
			Model model) throws Exception {
				
		
		
		
		JsonObject json_resp;
		Actor actor;
		JsonObject stock;
		String[][] lista_nombre_mp = {
				{"malta_palida","maltaBasePalida"},{"malta_tostada","maltaTostada"},
				{"malta_negra","maltaNegra"},{"malta_crystal","maltaCrystal"},
				{"malta_chocolate","maltaChocolate"},{"malta_caramelo","maltaCaramelo"},
				{"malta_pilsner","maltaPilsner"},{"malta_munich","maltaMunich"},
				{"lupulo_perle","lupuloPerle"},{"lupulo_tettnanger","lupuloTettnanger"},
				{"lupulo_centennial","lupuloCentennial"},{"levadura_ale","levaduraAle"},
				{"levadura_lagger","levaduraLagger"}
			};
		
		
		idActor = get_id_actor_cookie(request.getCookies());
		stock = new JsonObject();
		json_resp = new JsonObject();
		actor=new Actor(idActor, "Agricultor", "asdasd", "rmj@g.cm", 0, "41.5N 2.0W","Agricultor A", "c/mevoyamorir", "1234567C");
		
		idActor = (idActor == null)?(id):(idActor);	
		json_resp.addProperty("nomUsuario", actor.getNombreUsuario());
		json_resp.addProperty("email", actor.getEmail());
		json_resp.addProperty("tipoActor", actor.getTipoActor());	
		for(int i = 0; i < lista_nombre_mp.length; i++) 
		{
			try {
				stock.addProperty(lista_nombre_mp[i][0], metodosCompany.extraerStockMP(
						actor, 
						metodosCompany.extraerMateriaPrima(lista_nombre_mp[i][1])));
			} catch (NotInDatabaseException e) {
				stock.addProperty(lista_nombre_mp[i][0], 0);
				e.printStackTrace();
				
			}
		}
		
		stock.addProperty("lotes_pilsner", getStockLote(actor,null));  //TODO
		stock.addProperty("lotes_stout", getStockLote(actor,null));   //TODO		
		json_resp.add("stock", stock);

		
		return json_resp.toString();		
	}
	
	@Scope("request")
	@RequestMapping("/get_trazabilidad")
	@ResponseBody
	public String get_trazabilidad(@RequestParam(name="id_pedido", required=true) int id_pedido,
			Model model) throws SQLException 
	{
		BlockchainServices bcs;
		
		bcs = new BlockchainServices();
		
		System.out.println(id_pedido);
		
		return bcs.get_trazabilidad(id_pedido);
	}
	
	@Scope("request")
	@RequestMapping("/damePedidosTransportista")
	@ResponseBody
	public String get_pedidos_transportista(Model model) throws SQLException, ClassNotFoundException 
	{
		try {
			
			CadenaActores cadena;
			JsonObject json_resp;
			Gson gson;
			int index;
			
			index = 0;
			json_resp = new JsonObject();
			cadena = metodosCompany.extraerCadenaActores();
			gson = new Gson();
			
			for(Actor actor : cadena.getlista_actores()) 
			{
				for(OrdenTrazabilidad or : metodosCompany.extraerPedidosActorDestino(actor.getId()))
				{
						if(or.getNecesitaTransportista()) 
						{
							json_resp.addProperty(index+"", gson.toJson(or));
						}
				}
			}
			
			return json_resp.toString();

			
		} catch (Exception e) {
			return "{\"respuesta\":\"Error\"}";
		}
		
		
	}
	
	

}
