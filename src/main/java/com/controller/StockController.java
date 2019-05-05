package com.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import com.google.gson.JsonParser;

import equipo4.model.Lote;
import equipo4.model.MateriaPrima;
import equipo5.dao.metodosCompany;
import equipo5.model.StockLote;
import equipo5.model.StockMP;
import equipo5.model.NotInDatabaseException;
import equipo5.dao.NullException;
import equipo6.model.Actor;
import equipo6.model.Bloque;
import equipo6.model.CadenaActores;
import equipo6.model.geolocalizacion;
import equipo6.otros.BlockchainServices;
import equipo7.model.OrdenTrazabilidad;
import equipo8.model.Registro;
@Controller
@SpringBootApplication
public class StockController {

	String idActor;


	/*
	 * dado un actor y una materia prima devuelve la cantidad de esa materia prima
	 */
	public static double getCantidadStock(Actor actor, int idOrden, MateriaPrima mp)
			throws ClassNotFoundException, SQLException, equipo5.model.NotInDatabaseException, NotInDatabaseException {
		double devolvemos = 0;
		LinkedList<StockMP> lista = new LinkedList<StockMP>();
		lista = metodosCompany.extraerStockMP(actor, idOrden);
		for (StockMP encontrar : lista) {
			if (encontrar.getMp().getTipo().equals(mp.getTipo())) {
				encontrar.getMp().getCantidad();
			}
		}
		return devolvemos;
	}


	/*
	 * devuelve una lista de stocklotes dado un actor
	 */

	public static LinkedList<StockLote> getListaLotes(Actor actor, int idOrden)
			throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
		return metodosCompany.extraerStockLote(actor, idOrden);
	}


	/*
	 * dado un actor y un lote devuelve el stock de un tipo de lote
	 */
	public static int getStockLote(Actor actor, int idOrden, Lote lote) throws ClassNotFoundException, SQLException, equipo5.model.NotInDatabaseException, NotInDatabaseException {

		// nos devuleve una lista con todos los lotes de un actor, luego tenemos que
		// buscar
		// los lotes del tipo lote que nos pasan
		int resultado = 0;
		LinkedList<StockLote> lista = new LinkedList<StockLote>();
		lista = metodosCompany.extraerStockLote(actor, idOrden);
		for (StockLote l : lista) {
			if (l.getLote().getTipo().equals(lote.getTipo())) {
				resultado++;
			}
		}
		return resultado;
	}

	public static HashMap<String, Double> getStockPedidoFabrica(int idOrden) throws ClassNotFoundException, SQLException, equipo5.model.NotInDatabaseException, NotInDatabaseException {
		HashMap<String, Double> hm = new HashMap<String, Double>();
		LinkedList<StockMP> lista = new LinkedList<StockMP>();
		Actor actor=new Actor(null,null,3);
		lista = metodosCompany.extraerStockMP(actor, idOrden);
		for (StockMP encontrar : lista) {
			hm.put(encontrar.getMp().getTipo(), encontrar.getMp().getCantidad());
		}
		return hm;
	}

	/*
	 * metodo para cambiar la cantidad de stock de materia prima
	 */	
	public static void setCantidadMateriaPrima(Actor actor, MateriaPrima mp, double cantidad,int idOrden)
			throws ClassNotFoundException, SQLException, NullException {
		int cantidad_entera;
		cantidad_entera = (int) cantidad;
		BlockchainServices bcs;
		bcs = new BlockchainServices();
		OrdenTrazabilidad orden = bcs.getOrden(idOrden);		
		StockMP smp= new StockMP(mp,null,null,idOrden,orden.getIdPedido(),actor.getId());
		metodosCompany.insertarStockMP(smp);
	}

	/*
	 * metodo para cambiar la cantidad de stock de un lote, se insertar de uno en uno.
	 */	
	public static void setCantidadLote(Actor actor, Lote lote,int idOrden) throws Throwable, ClassNotFoundException, SQLException {
		BlockchainServices bcs;
		bcs = new BlockchainServices();
		OrdenTrazabilidad orden = bcs.getOrden(idOrden);
		StockLote sLote=new StockLote(lote,null,null,idOrden,orden.getIdPedido(),actor.getId());
		metodosCompany.insertarStockLote(sLote);
	}

	public static String buscarTipoCerveza(int idOrden) throws ClassNotFoundException, SQLException {
		BlockchainServices bcs;

		bcs = new BlockchainServices();
		OrdenTrazabilidad orden=bcs.getOrden(idOrden);
		if(orden.getProductosPedidos().getCant_lotes_bisner()!=0) {
			return "pilsner";
		}
		if (orden.getProductosPedidos().getCant_lotes_stout()!=0) {
			return "stout";
		}
		return null;
	}

	public static int buscarCantidadCerveza(int idOrden) throws ClassNotFoundException, SQLException {
		BlockchainServices bcs;

		bcs = new BlockchainServices();
		OrdenTrazabilidad orden=bcs.getOrden(idOrden);
		if(orden.getProductosPedidos().getCant_lotes_bisner()!=0) {
			return orden.getProductosPedidos().getCant_lotes_bisner();
		}
		if (orden.getProductosPedidos().getCant_lotes_stout()!=0) {
			return orden.getProductosPedidos().getCant_lotes_stout();
		}
		return 0;
	}



	public String get_id_actor_cookie(Cookie[] lista_cookies) 
	{
		String idActor;

		idActor = null;
		if(lista_cookies != null) {

			for(Cookie c : lista_cookies) {
				System.out.println(c.getName()+"    "+c.getValue());
				if(c.getName().equals("id")) 
				{
					idActor = c.getValue();
					break;
				}
			}
		}

		return idActor;
	}


	public List<Actor> actoresDeTipo(Integer tipo) throws SQLException
	{
		List<Actor> cadena_total;
		List<Actor> cadena_filtrada;

		cadena_total = metodosCompany.extraerCadenaActores().getlista_actores();
		cadena_filtrada = new ArrayList<Actor>();

		for(Actor actor : cadena_total) 
		{
			if(actor.getTipoActor() == tipo) 
			{
				cadena_filtrada.add(actor);
			}
		}

		return cadena_filtrada;
	}

	@Scope("request")
	@RequestMapping("/dameStockActores")
	@ResponseBody
	public String getStockActores(
			HttpServletRequest request,
			@RequestParam(name="id") String id,
			Model model) throws Exception, equipo5.model.NotInDatabaseException {
		int a = Integer.parseInt(id);
		String tipo = "";
		switch(a){
		case 0:
			tipo="Agricultor";
			break;
		case 1:
			tipo="Cooperativa";
			break;
		case 2:
			tipo="Transportista";
			break;
		case 3:
			tipo="Fabrica";
			break;
		case 4:
			tipo="Reatiler";
			break;
		default:
			tipo = "Unknown";
			break;
		}
		JsonObject todos = new JsonObject();
		List<Actor> actores = actoresDeTipo(a);
		for(int i=0;i<actores.size();i++){ 
			JsonObject stock = get_stock_actor(actores.get(i).getId());
			todos.add(tipo+Integer.toString(i),stock);
		}
		return todos.toString();
	}

	private void init_map_nombres_bbdd_vistas(Map<String,String> mapeo_nombres) 
	{

		mapeo_nombres.put("maltaBasePalida","malta_palida");
		mapeo_nombres.put("maltaTostada","malta_tostada");
		mapeo_nombres.put("maltaNegra","malta_negra");
		mapeo_nombres.put("maltacrystal","malta_crystal");
		mapeo_nombres.put("maltaChocolate","malta_chocolate");
		mapeo_nombres.put("maltaCaramelo","malta_caramelo");
		mapeo_nombres.put("maltaPilsner","malta_pilsner");
		mapeo_nombres.put("maltaMunich","malta_munich");
		mapeo_nombres.put("lupuloPerle","lupulo_perle");
		mapeo_nombres.put("lupuloTettnanger","lupulo_tettnanger");
		mapeo_nombres.put("lupuloCentennial","lupulo_centennial");
		mapeo_nombres.put("levaduraAle","levadura_ale");
		mapeo_nombres.put("levaduraLagger","levadura_lagger");

	}


	@Scope("request")
	@RequestMapping("/dameStockActor")
	@ResponseBody
	public String getStockActor(HttpServletRequest request, @RequestParam(name = "id") String id,Model model) throws Exception, equipo5.model.NotInDatabaseException {


		idActor = get_id_actor_cookie(request.getCookies());
		idActor = (idActor == null) ? (id) : (idActor);
		return get_stock_actor(idActor).toString();
	}

	private JsonObject get_stock_actor(String id) throws equipo5.model.NotInDatabaseException, NotInDatabaseException 
	{
		MateriaPrima mp;
		JsonObject json_resp;
		Actor actor;
		JsonObject stock;
		ArrayList<OrdenTrazabilidad> lista_ordenes;
		Map<String,String> lista_nombre_mp;

		lista_nombre_mp = new HashMap<String, String>();
		stock = new JsonObject();
		json_resp = new JsonObject();
		try {
			lista_ordenes = metodosCompany.extraerOrdenesActorOrigen(idActor);
		}catch (Exception e) {
			e.printStackTrace();
			lista_ordenes = new ArrayList<OrdenTrazabilidad>();
		}
		init_map_nombres_bbdd_vistas(lista_nombre_mp);



		try {
			actor = metodosCompany.extraerCadenaActores().get_actor_by_id(idActor);
		} catch (Exception e) {
			e.printStackTrace();
			actor = new Actor(idActor, "Agricultor", "asdasd", "rmj@g.cm", 0, "41.5N 2.0W", "Agricultor A",
					"c/mevoyamorir", "1234567C");
		}

		json_resp.addProperty("nomUsuario", actor.getNombreUsuario());
		json_resp.addProperty("email", actor.getEmail());
		json_resp.addProperty("nomUsuario", actor.getNombreUsuario());
		json_resp.addProperty("email", actor.getEmail());
		json_resp.addProperty("tipoActor", actor.getTipoActor());

		LinkedList<StockMP> lista = new LinkedList<StockMP>();
		try {
			for (OrdenTrazabilidad orden : lista_ordenes) {
				lista = metodosCompany.extraerStockMpPorPedido(actor, orden);
				for (StockMP var : lista) {
					stock.addProperty(
							lista_nombre_mp.get(var.getMp().getTipo()), 
							var.getMp().getCantidad()
							);
				}
			}

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}




		int tipoActor = actor.getTipoActor();
		int lotesP=0;
		int lotesS=0;
		String numLotesP = "0";
		String numLotesS = "0";
		String numLotes = "0";
		try {
			for (OrdenTrazabilidad orden : lista_ordenes) {
				//crear un lote, con el un stocklote  y llamar a getStockLote			
				Lote loteP= new Lote(0,null,null,null,false,false,false,false,false, "pilsner",null,null,null,null,null);
				Lote loteS= new Lote(0,null,null,null,false,false,false,false,false, "stout",null,null,null,null,null);
				lotesP=getStockLote(actor,orden.getId(),loteP);
				lotesS=getStockLote(actor,orden.getId(),loteS);
				numLotesP = String.valueOf(lotesP);
				numLotesS = String.valueOf(lotesS);
				numLotes = String.valueOf(lotesS+lotesP);
			}

		} catch (Exception e) {
			numLotesP = "0";
			numLotesS = "0"; 
			e.printStackTrace();
		}

		stock.addProperty("lotes_pilsner", numLotesP); 
		stock.addProperty("lotes_stout", numLotesS); 
		switch (tipoActor) {
		case 0:
			json_resp.add("stock", stock);
			break;
		case 1:
			json_resp.add("stock", stock);
			break;
		case 3:
			json_resp.add("stock", stock);
			json_resp.addProperty("Numero de lotes", numLotes);

			break;
		case 4:
			json_resp.addProperty("Numero de lotes", numLotes);

			break;
		default:
			break;
		}
		return json_resp;
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
			JsonParser parse;


			index = 0;
			json_resp = new JsonObject();
			cadena = metodosCompany.extraerCadenaActores();
			gson = new Gson();
			parse = new JsonParser();

			for(Actor actor : cadena.getlista_actores()) 
			{
				for(OrdenTrazabilidad or : metodosCompany.extraerOrdenesActorDestino(actor.getId()))
				{
					if(or.isNecesitaTransportista()) 
					{
						json_resp.add(or.getId()+"", parse.parse(gson.toJson(or)).getAsJsonObject());
						index++;
					}
				}
			}

			return json_resp.toString();


		} catch (Exception e) {
			e.printStackTrace();
			return "{\"respuesta\":\"Error\"}";
		}


	}

	@Scope("request")
	@RequestMapping("/actualizar_geolocalizacion")
	@ResponseBody
	public void actualizar_geolocalizacion(
			@RequestParam(name="id_pedido", required=true) int id_pedido,
			@RequestParam(name="id_orden", required=true) int id_orden,
			@RequestParam(name="datos_geolocalizacion", required=true) String datos_geolocalizacion,
			Model model) throws Throwable 
	{
		BlockchainServices bcs;
		geolocalizacion geo;

		int idGeoloca = metodosCompany.idGeolocalizacion();
		geo = new geolocalizacion(idGeoloca, id_pedido, id_orden, datos_geolocalizacion);
		bcs = new BlockchainServices();		
		bcs.guardarOrden(geo);

	}

	@Scope("request")
	@RequestMapping("/get_trabilidad_vista")
	@ResponseBody
	public String get_trabilidad_vista(@RequestParam(name="id_pedido", required=true) int id_pedido,
			Model model) throws SQLException 
	{
		try {
			JsonObject json_respuesta;	
			BlockchainServices bcs;
			Registro ultimo_registro;
			Lote ultimo_lote;
			List <Bloque> lista_bloques_lotes;
			List <Bloque> lista_bloques_registro;
			List <Bloque> lista_bloques_ordenes;


			bcs = new BlockchainServices();
			lista_bloques_ordenes = bcs.get_cadena(id_pedido).getBloque(0);
			lista_bloques_registro = bcs.get_cadena(id_pedido).getBloque(1);
			lista_bloques_lotes = bcs.get_cadena(id_pedido).getBloque(2);
			json_respuesta = new JsonObject();

			if(lista_bloques_ordenes == null || lista_bloques_ordenes.size() == 0) throw new Exception();
			if(lista_bloques_registro == null || lista_bloques_registro.size() == 0) throw new Exception();
			//esto es un hardcodeo asta que el grupo de lote meta el id de pedido dentro de lote
			if(lista_bloques_lotes == null || lista_bloques_lotes.size() == 0) 
			{
				json_respuesta.addProperty("Tipo", "Lager");
			}
			else 
			{
				ultimo_lote = ((Lote)(lista_bloques_lotes.get(lista_bloques_lotes.size()-1).getDatos()));
				json_respuesta.addProperty("Tipo", ultimo_lote.getTipo());
			}

			insertar_actores(lista_bloques_ordenes,json_respuesta);
			ultimo_registro = ((Registro)(lista_bloques_registro.get(lista_bloques_registro.size()-1).getDatos()));
			json_respuesta.addProperty("Temperatura maxima", ultimo_registro.getTempMax());
			json_respuesta.addProperty("Temperatura minima", ultimo_registro.getTempMax());

			return json_respuesta.toString();

		}
		catch (Exception e) {
			return "{\"Error\":\"cadena con encotrada\"}";
		}
	}
	
	private void insertar_actores(List<Bloque> list_blo, JsonObject json) 
	{
		Actor actor;
		for (Bloque bloque : list_blo) {
			actor = ((OrdenTrazabilidad)bloque.getDatos()).getActorDestino();
			switch(actor.getTipoActor()) 
			{
			case 0:
				json.addProperty("Agricultor", actor.getNombre());
				break;
			case 3:
				json.addProperty("Fabrica", actor.getNombre());
				break;
			}
		}
	}
	
	
	/*
	 {
		"Tipo":"Lager",
		"Agricultor":"Malta Madrid",
		"Fábrica": "Kolks",
		"Temperatura máxima":"6ºC",
		"Temperatura mínima":"4ºC"
		
	 */



}