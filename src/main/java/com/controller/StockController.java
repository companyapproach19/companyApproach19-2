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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import equipo4.model.Lote;
import equipo4.model.MateriaPrima;
import equipo5.dao.NullException;
import equipo5.dao.metodosCompany;
import equipo5.model.StockLote;
import equipo5.model.StockMP;
import equipo5.model.NotInDatabaseException;
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
			throws ClassNotFoundException, SQLException, equipo5.model.NotInDatabaseException {
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

	public static LinkedList<StockLote> getListaLotes(Actor actor)
            throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.model.NotInDatabaseException {
        LinkedList<StockLote> lista = new LinkedList<StockLote>();
        ArrayList<OrdenTrazabilidad> listaOrdenes = metodosCompany.extraerOrdenesActorDestino(actor.getId());
        System.out.println(" tamano "+listaOrdenes.size());
        for(OrdenTrazabilidad orden: listaOrdenes) {
            System.out.println("id de la orden "+orden.getId());
            LinkedList<StockLote> listaStockLote = metodosCompany.extraerStockLote(actor, orden.getId());
            System.out.println(" tamano2 "+listaStockLote.size());
            for(StockLote sLote: listaStockLote) {
                lista.add(sLote);
            }            
        }
        return lista;
    }


	/*
	 * dado un actor y un lote devuelve el stock de un tipo de lote
	 */
	public static int getStockLote(Actor actor, int idOrden, Lote lote) throws ClassNotFoundException, SQLException, equipo5.model.NotInDatabaseException {

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

	public static HashMap<String, Double> getStockPedidoFabrica(int idOrden) throws ClassNotFoundException, SQLException, equipo5.model.NotInDatabaseException {
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
		if(orden.getProductosPedidos().getCant_lotes_pilsner()!=0) {
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
		if(orden.getProductosPedidos().getCant_lotes_pilsner()!=0) {
			return orden.getProductosPedidos().getCant_lotes_pilsner();
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
			Model model) throws Exception {
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
				tipo="Retailer";
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

		mapeo_nombres.put("cebadaTostada","cebada_tostada");
		mapeo_nombres.put("maltaBasePalida","malta_palida");
		mapeo_nombres.put("maltaNegra","malta_negra");
		mapeo_nombres.put("maltaCrystal","malta_crystal");
		mapeo_nombres.put("maltaChocolate","malta_chocolate");
		mapeo_nombres.put("maltaCaramelo","malta_caramelo");
		mapeo_nombres.put("maltaPilsner","malta_pilsner");
		mapeo_nombres.put("maltaMunich","malta_munich");
		mapeo_nombres.put("lupuloPerle","lupulo_perle");
		mapeo_nombres.put("lupuloTettnanger","lupulo_tettnanger");
		mapeo_nombres.put("lupuloCentennial","lupulo_centennial");
		mapeo_nombres.put("levaduraAle","levadura_ale");
		mapeo_nombres.put("levaduraLager","levadura_lagger");

	}


	@Scope("request")
	@RequestMapping("/dameStockActor")
	@ResponseBody
	public String getStockActor(HttpServletRequest request, @RequestParam(name = "id") String id,Model model) throws Exception {

		if(id==null) {
			idActor = get_id_actor_cookie(request.getCookies());
		}
		else {
			idActor=id;
		}
		return get_stock_actor(idActor).toString();
	}
	
	
	@Scope("request")
	@RequestMapping("/stockSuficienteFabricarLote")
	@ResponseBody
	public boolean tieneStockSuficienteParaLote(String tipoCerveza) {
		//getStockActor()
		JsonObject obj = get_stock_actor("3");
		JsonObject stock = obj.get("stock").getAsJsonObject();
		if(tipoCerveza.equals("stout")) {
			
		}else if(tipoCerveza.equals("pilsner")) {
			
		}
		
		System.out.println();
		
		/*
		 * 1 LITRO DE STOUT (cantidades en gramos para que sean 'int'):
maltaBasePalida=261
maltaMunich=61
cebadaTostada=21
maltaNegra=10
maltaCrystal=6
maltaChocolate=5
maltaCaramelo=4
lupuloCentennial=3
levaduraAle=11

LITRO DE STOUT (cantidades en gramos para que sean 'int'):
maltaPilsner=173
maltaCaramelo=21
lupuloPerle=1
lupuloTettnanger=2
levaduraLager=1
		 */
		return true;
	}

	private JsonObject get_stock_actor(String id) 
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
		idActor=id;

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
		
		System.out.println(actor.getId());
		System.out.println(actor.getNombreUsuario());

		json_resp.addProperty("nomUsuario", actor.getNombreUsuario());
		json_resp.addProperty("id", actor.getId());
		json_resp.addProperty("email", actor.getEmail());
		json_resp.addProperty("nomUsuario", actor.getNombreUsuario());
		json_resp.addProperty("email", actor.getEmail());
		json_resp.addProperty("tipoActor", actor.getTipoActor());

		LinkedList<StockMP> lista = new LinkedList<StockMP>();
		try {
			for (OrdenTrazabilidad orden : lista_ordenes) {
				lista = metodosCompany.extraerStockMP(actor, orden.getId());
				for (StockMP var : lista) {
						stock.addProperty(
											var.getMp().getTipo(), 
											var.getMp().getCantidad()
											);
				}
			}

		} catch (equipo5.model.NotInDatabaseException | ClassNotFoundException | SQLException e1) {
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

		} catch (Exception | equipo5.model.NotInDatabaseException e) {
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
			json_resp.add("stock", stock );
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
	/*Descomponemos en varias URL para darle los pedidos a transportista ya filtrado.
	 * 
	 *damePedidosTransportistaListo
	 *damePedidosTransportistaRecogido
	 *damePedidosTransportistaEntregado 
	 */
	@ResponseBody
	public String get_pedidos_transportista(Model model) throws SQLException, ClassNotFoundException 
	{
		try {

			CadenaActores cadena;
			Gson gson;
			JsonParser parse;
			JsonArray lista;

			lista = new JsonArray();

			cadena = metodosCompany.extraerCadenaActores();
			gson = new Gson();
			parse = new JsonParser();

			for(Actor actor : cadena.getlista_actores()) 
			{
				for(OrdenTrazabilidad or : metodosCompany.extraerOrdenesActorDestino(actor.getId()))
				{
					if(or.isNecesitaTransportista()) 
					{
						lista.add(parse.parse(gson.toJson(or)).getAsJsonObject());
					}
				}
			}

			return lista.toString();


		} catch (Exception e) {
			e.printStackTrace();
			return "{\"respuesta\":\"Error\"}";
		}


	}
	
	@Scope("request")
	@RequestMapping("/damePedidosTransportistaListo")
	@ResponseBody
	public String getPedidosTransportistaListo(Model m) throws SQLException, ClassNotFoundException
	{
		try {
			CadenaActores cadena;
			Gson gson;
			JsonParser parse;
			JsonArray lista;

			lista = new JsonArray();

			cadena = metodosCompany.extraerCadenaActores();
			gson = new Gson();
			parse = new JsonParser();

			for(Actor actor : cadena.getlista_actores()) 
			{
				for(OrdenTrazabilidad or : metodosCompany.extraerOrdenesActorDestino(actor.getId()))
				{
					if(or.isNecesitaTransportista() && or.getEstado()==2) 
					{
						lista.add(parse.parse(gson.toJson(or)).getAsJsonObject());
					}
				}
			}

			return lista.toString();


		} catch (Exception e) {
			e.printStackTrace();
			return "{\"respuesta\":\"Error\"}";
		}
	}
	
	
	@Scope("request")
	@RequestMapping("/damePedidosTransportistaRecogido")
	@ResponseBody
	public String getPedidosTransportistaRecogido(Model m) throws SQLException, ClassNotFoundException
	{
		try {
			CadenaActores cadena;
			Gson gson;
			JsonParser parse;
			JsonArray lista;

			lista = new JsonArray();

			cadena = metodosCompany.extraerCadenaActores();
			gson = new Gson();
			parse = new JsonParser();

			for(Actor actor : cadena.getlista_actores()) 
			{
				for(OrdenTrazabilidad or : metodosCompany.extraerOrdenesActorDestino(actor.getId()))
				{
        if(or.getEstado()==3 && !(or.getActorOrigen().getId().equals("1") && or.getActorDestino().getId().equals("0")))
					{
						lista.add(parse.parse(gson.toJson(or)).getAsJsonObject());
					}
				}
			}

			return lista.toString();


		} catch (Exception e) {
			e.printStackTrace();
			return "{\"respuesta\":\"Error\"}";
		}
	}
	
	
	@Scope("request")
	@RequestMapping("/damePedidosTransportistaEntregado")
	@ResponseBody
	public String getPedidosTransportistaEntregado(Model m) throws SQLException, ClassNotFoundException
	{
		try {
			CadenaActores cadena;
			Gson gson;
			JsonParser parse;
			JsonArray lista;

			lista = new JsonArray();

			cadena = metodosCompany.extraerCadenaActores();
			gson = new Gson();
			parse = new JsonParser();

			for(Actor actor : cadena.getlista_actores()) 
			{
				for(OrdenTrazabilidad or : metodosCompany.extraerOrdenesActorDestino(actor.getId()))
				{
					if(or.getEstado()==4 && !(or.getActorOrigen().getId().equals("1") && or.getActorDestino().getId().equals("0"))) 
					{
						lista.add(parse.parse(gson.toJson(or)).getAsJsonObject());
					}
				}
			}

			return lista.toString();


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
	
	private List<Registro> dame_dos_ultimos_registros(List<Bloque> regitros)
	{
		Map<Integer, Boolean> map_reg;
		List<Registro> reg_ret;
		Registro reg;
		
		map_reg = new HashMap<Integer, Boolean>();
		reg_ret = new ArrayList<Registro>();
		
		for(Bloque bloq : regitros) 
		{
			reg = ((Registro)bloq.getDatos());
			if(map_reg.get(reg.getIdOrdenTrazabilidad()) == null) 
			{
				map_reg.put(reg.getIdOrdenTrazabilidad(), true);
				reg_ret.add(reg);
			}
		}
		
		return reg_ret;
	}
	
	@Scope("request")
	@RequestMapping("/get_trazabilidad_vista")
	@ResponseBody
	public String get_trabilidad_vista(@RequestParam(name="id_pedido", required=true) int id_pedido,
			Model model) throws SQLException 
	{
		try {
			JsonObject json_respuesta;	
			BlockchainServices bcs;
			List<Registro> ultimos_registros;
			Lote ultimo_lote;
			List <Bloque> lista_bloques_lotes;
			List <Bloque> lista_bloques_registro;
			List <Bloque> lista_bloques_ordenes;
			List <Bloque> cadena_aplanada;


			bcs = new BlockchainServices();
			cadena_aplanada = bcs.aplana_cadena(id_pedido);
			lista_bloques_ordenes = bcs.filtra_cadena_bloques(cadena_aplanada, 0);
			lista_bloques_registro = bcs.filtra_cadena_bloques(cadena_aplanada, 1);
			lista_bloques_lotes = bcs.filtra_cadena_bloques(cadena_aplanada, 2);
			json_respuesta = new JsonObject();
			ultimo_lote = null;
			
			

			if(lista_bloques_ordenes == null || lista_bloques_ordenes.size() == 0) throw new Exception();
			if(lista_bloques_registro == null || lista_bloques_registro.size() <= 1) throw new Exception();
			//esto es un hardcodeo asta que el grupo de lote meta el id de pedido dentro de lote
			if(lista_bloques_lotes == null || lista_bloques_lotes.size() == 0) 
			{
				json_respuesta.addProperty("Tipo", "Lager");
			}
			else 
			{
				ultimo_lote = ((Lote)(lista_bloques_lotes.get(0).getDatos()));
				json_respuesta.addProperty("Tipo", ultimo_lote.getTipo());
			}
			
			json_respuesta.addProperty("Agricultor", "Sin resultados");
			json_respuesta.addProperty("Cooperativa", "Sin resultados");
			json_respuesta.addProperty("Fabrica", "Sin resultados");
			
			if(ultimo_lote == null) 
			{
				json_respuesta.addProperty("fecha_embotellado", "Sin resultados");
			}
			else 
			{
				json_respuesta.addProperty("fecha_embotellado", ultimo_lote.getFecha_embotellado().toString());
			}

			insertar_actores(lista_bloques_ordenes,json_respuesta);
			ultimos_registros = dame_dos_ultimos_registros(lista_bloques_registro);
			json_respuesta.addProperty("TemperaturaMax1", ultimos_registros.get(0).getTempMax());
			json_respuesta.addProperty("TemperaturaMin1", ultimos_registros.get(0).getTempMin());
			json_respuesta.addProperty("TemperaturaMax2", ultimos_registros.get(1).getTempMax());
			json_respuesta.addProperty("TemperaturaMin2", ultimos_registros.get(1).getTempMin());
			return json_respuesta.toString();

		}
		catch (Exception e) {
			e.printStackTrace();
			return "{\"Error\":\"cadena no encotrada\"}";
		}
	}
	
	private void insertar_actores(List<Bloque> list_blo, JsonObject json) throws ClassNotFoundException, SQLException, RuntimeException, NullException 
	{
		OrdenTrazabilidad orden;
		for (Bloque bloque : list_blo) {
			orden = ((OrdenTrazabilidad)bloque.getDatos());
			switch(orden.getActorDestino().getTipoActor()) 
			{
			case 0:
				json.addProperty("Agricultor", orden.getActorDestino().getDireccion()+" "+BlockchainServices.extraer_nombres_materias_primas(orden.getProductosPedidos()));
				break;
			case 1:
				json.addProperty("Cooperativa", orden.getActorDestino().getNombre());
				break;
			case 3:
				json.addProperty("Fabrica", orden.getActorDestino().getNombre());
				break;
			}
		}
	}


}
