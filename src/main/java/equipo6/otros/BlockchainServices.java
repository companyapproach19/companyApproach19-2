package equipo6.otros;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import equipo4.model.Lote;
import equipo4.model.MateriaPrima;
import equipo6.model.Bloque;
import equipo6.model.CadenaStock;
import equipo5.dao.NullException;
import equipo5.dao.metodosCompany;
import equipo5.model.Cadena;
import equipo5.model.NotInDatabaseException;
import equipo5.model.StockLote;
import equipo5.model.StockMP;
import equipo6.model.DatosContainer;
import equipo6.model.geolocalizacion;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo8.model.Registro;


//Esta es la clase a la que van a llamar el resto de grupos para hacer sus
//gestiones con respecto a añadir cosas al blockchain
public class BlockchainServices{
	public BlockchainServices() {}
	public boolean checkConsistencia(int codLote) {
		try {
			return equipo5.dao.metodosCompany.extraerCadena(codLote).checkConsistencia();
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	//Aun tienen que definir los otros grupos cual va a ser la clase
	//que nos van a pasar de con la informacion del traspaso. No se si sera
	//este nombre u otro, pero en cualquier caso podemos tratarlo como si fuera lo mismo.
	//Recibe un objeto contenedor con la informacion del traspaso, y tenemos que encapsularlo
	//en DatosContainer, y guardarlo en la cadena con los metodos de la clase Cadena correspondientes
	//TODO alejandro
	public boolean guardarOrden(DatosContainer datos_container) throws Throwable{
		//encapsularlo, sin tener los datos de la clase Traspaso no podemos encapsularlo
		try {
			int tipoBlque;
			int codLote = get_id_datos(datos_container);
			Cadena cadena = equipo5.dao.metodosCompany.extraerCadena(codLote);
			tipoBlque = get_tipo_bloque(datos_container);
			if(cadena == null) {
				cadena = new Cadena(codLote);
			}
			else if(tipoBlque == 0)
			{

				if(((OrdenTrazabilidad)datos_container).getIdPedido() == -1) 
				{
					((OrdenTrazabilidad)datos_container).setIdPedido(metodosCompany.idPedido());
				}
				if(!operaciones_stock((OrdenTrazabilidad)datos_container))return false; 
			}
			cadena.incorporarBloque(datos_container, tipoBlque); //Cambiar cuando asignemos cada entero a cada tipo de bloque
		}catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;


	}


	private boolean operaciones_stock(OrdenTrazabilidad orden) throws Throwable 
	{
		List <MateriaPrima> list_ele;
		Cadena cadenaDestino;
		OrdenTrazabilidad orden_origen;
		Bloque super_bloque;
		boolean resp;
		List<StockLote> list_stock_lotes;
		StockLote new_lote;

		list_ele = get_materia_prima(orden.getProductosPedidos());
		resp = true;

		try {
			switch(orden.getEstado()) 
			{
			case 2:

				if(orden.getActorDestino().getTipoActor() == 0){
					break;
				}

				cadenaDestino = metodosCompany.extraerCadena(orden.getId());
				super_bloque = cadenaDestino.getBloque(-1).get(0);
				orden_origen = metodosCompany.extraerOrdenTrazabilidad(super_bloque.getIdCadena());
				resp = CadenaStock.actualizar_stock(orden_origen);
				
				break;

			case 4:
				if(orden.getActorOrigen().getTipoActor() != 4)
				{
					for(MateriaPrima materia_prima : list_ele)
						metodosCompany.insertarStockMP(new StockMP(materia_prima, null, null, orden.getId(), orden.getIdPedido(), orden.getActorOrigen().getId()));
				}
				else 
				{
					cadenaDestino = metodosCompany.extraerCadena(orden.getId());
					super_bloque = cadenaDestino.getBloque(-1).get(0);
					orden_origen = metodosCompany.extraerOrdenTrazabilidad(super_bloque.getIdCadena());
					list_stock_lotes = metodosCompany.extraerStockLote(orden_origen.getActorOrigen(), orden_origen.getId());
					for(StockLote stock_lote : list_stock_lotes) {
						new_lote = new StockLote(stock_lote.getLote(), null, null, orden.getId(), orden.getId(), orden.getActorOrigen().getId());
						metodosCompany.insertarStockLote(stock_lote);
						metodosCompany.insertarStockLote(new_lote);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return resp;
	}

	public void guardarRespuestaPedido(int id_pedido_destino, int id_stock_origen) throws Throwable 
	{

		Cadena origen;
		Cadena destino;
		OrdenTrazabilidad ordenOrigen;
		Bloque new_super_block;
		String new_utimo_hash;

		destino = metodosCompany.extraerCadena(id_pedido_destino);
		origen = null;
		ordenOrigen = metodosCompany.extraerOrdenTrazabilidad(id_stock_origen);
	
		origen = metodosCompany.extraerCadena(id_stock_origen);
		new_super_block = new Bloque(destino.getHashUltimoBloque(), -1, destino.getNumBloques(), destino.getCodLote(), new DatosContainer(), origen.getCodLote(),-1);
		new_utimo_hash = new_super_block.getHashCode();
		try {
			metodosCompany.insertarBloque(new_super_block);
			destino.incrementarNumBloques();
			destino.setHashUltimoBloque(new_utimo_hash);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if(destino != null)
			metodosCompany.insertarCadena(destino);
		
		ordenOrigen.setEstado(5);
		metodosCompany.insertarOrdenTrazabilidad(ordenOrigen);

	}

	public String get_trazabilidad(int id_pedido) throws SQLException 
	{

		List<Cadena> pila_cadenas;
		List<Bloque> lista_bloques;
		Cadena cadena_actual;
		JsonObject respuesta;
		Cadena cadena_super_bloque;
		String nombre_clase;
		JsonObject new_datos_bloque;
		int indice;


		pila_cadenas = new LinkedList<Cadena>();
		cadena_actual = metodosCompany.extraerCadena(id_pedido);
		pila_cadenas.add(cadena_actual);
		respuesta = new JsonObject();
		indice = 0;


		while(pila_cadenas.size() > 0) 
		{
			lista_bloques = (LinkedList<Bloque>) pila_cadenas.remove(0).getCadena();
			for(Bloque bloque : lista_bloques) 
			{
				if(bloque.is_super_blocke()) 
				{
					cadena_super_bloque = metodosCompany.extraerCadena(bloque.getIdCadena());
					pila_cadenas.add(cadena_super_bloque);
				}
				else 
				{
					new_datos_bloque = convert_to_json(bloque.getDatos());
					nombre_clase = get_name_class(bloque.getDatos());
					new_datos_bloque.addProperty("Tipo_Datos_Container", nombre_clase);
					respuesta.add(indice+"", new_datos_bloque);
					indice++;
				}
			}
		}

		return respuesta.toString();
	}
	
	
	public List<Bloque> aplana_cadena(int id_pedido) throws SQLException 
	{

		List<Cadena> pila_cadenas;
		List<Bloque> lista_bloques;
		List<Bloque> lista_bloques_resp;
		Cadena cadena_actual;
		Cadena cadena_super_bloque;


		pila_cadenas = new LinkedList<Cadena>();
		cadena_actual = metodosCompany.extraerCadena(id_pedido);
		pila_cadenas.add(cadena_actual);
		lista_bloques_resp = new ArrayList<Bloque>();
		
		while(pila_cadenas.size() > 0) 
		{
			lista_bloques = (LinkedList<Bloque>) pila_cadenas.remove(0).getCadena();
			for(Bloque bloque : lista_bloques) 
			{
				if(bloque.is_super_blocke()) 
				{
					cadena_super_bloque = metodosCompany.extraerCadena(bloque.getIdCadena());
					pila_cadenas.add(cadena_super_bloque);
				}
				else 
				{
					lista_bloques_resp.add(bloque);
				}
			}
		}

		return lista_bloques_resp;
	}
	
	public  List<Bloque> filtra_cadena_bloques(List<Bloque> cadena_bloques, int tipo) 
	{
		List<Bloque> lista_flitrada;
		
		lista_flitrada = new ArrayList<Bloque>();
		
		for(Bloque bloque : cadena_bloques) 
		{
			if(bloque.getTipoBloque() == tipo) 
			{
				lista_flitrada.add(bloque);
			}
		}
		
		return lista_flitrada;
	}


	public ArrayList<OrdenTrazabilidad> extraerOrdenesDestino(String idActor) throws ClassNotFoundException, SQLException
	{
		return metodosCompany.extraerOrdenesActorDestino(idActor);
	}

	public ArrayList<OrdenTrazabilidad> extraerOrdenesOrigen(String idActor) throws ClassNotFoundException, SQLException
	{
		return metodosCompany.extraerOrdenesActorOrigen(idActor);
	}


	private JsonObject convert_to_json(DatosContainer datos) 
	{
		JsonParser parse;
		Gson convert_to_json;

		convert_to_json = new Gson();
		parse = new JsonParser();
		if(datos instanceof OrdenTrazabilidad) return parse.parse(convert_to_json.toJson((OrdenTrazabilidad)datos)).getAsJsonObject();
		if(datos instanceof Registro) return parse.parse(convert_to_json.toJson((Registro)datos)).getAsJsonObject();
		if(datos instanceof Lote) return parse.parse(convert_to_json.toJson((Lote)datos)).getAsJsonObject();
		if(datos instanceof geolocalizacion) return parse.parse(convert_to_json.toJson((geolocalizacion)datos)).getAsJsonObject();
		return null;
	}

	private int get_id_datos(DatosContainer datos_container) throws ClassNotFoundException, SQLException 
	{

		if(datos_container instanceof OrdenTrazabilidad) return ((OrdenTrazabilidad)datos_container).getId();
		if(datos_container instanceof Registro) return ((Registro)datos_container).getIdOrdenTrazabilidad();
		if(datos_container instanceof Lote) return ((Lote)datos_container).getIdBd();
		if(datos_container instanceof geolocalizacion) return ((geolocalizacion)datos_container).getIdOrden();

		return -1;
	}

	private String get_name_class(DatosContainer datos_container) 
	{
		if(datos_container instanceof OrdenTrazabilidad) return "Orden_Trazabilidad";
		if(datos_container instanceof Registro) return "Registro";
		if(datos_container instanceof Lote) return "Lote";
		if(datos_container instanceof geolocalizacion) return "geolocalizacion";

		return "Error";
	}


	private int get_tipo_bloque(DatosContainer datos_container) 
	{
		if(datos_container instanceof OrdenTrazabilidad) return 0;
		if(datos_container instanceof Registro) return 1;
		if(datos_container instanceof Lote) return 2;
		if(datos_container instanceof geolocalizacion) return 3;

		return -1;
	}





	public Cadena get_cadena(int id_pedido) 
	{
		Cadena cadena;
		try {

			cadena = metodosCompany.extraerCadena(id_pedido);

			return cadena;

		}catch (Exception ex) {

			ex.printStackTrace();

			return null;
		}
	}



	public OrdenTrazabilidad getOrden(int idOrden) throws ClassNotFoundException, SQLException{
		return	equipo5.dao.metodosCompany.extraerOrdenTrazabilidad(idOrden);
	}


	public static String extraer_nombres_materias_primas(Productos productos) throws ClassNotFoundException, SQLException, RuntimeException, NullException 
	{
		String list_ele;



		list_ele = "";

		if(productos.getCant_cebada_tostada() != 0) 
		{
			list_ele += " cebadaTostada";
		}
		if(productos.getCant_lupulo_centennial() != 0) 
		{
			list_ele += " lupuloCentennial";
		}
		if(productos.getCant_malta_caramelo() != 0) 
		{
			list_ele += " maltaCaramelo";
		}
		if(productos.getCant_malta_chocolate() != 0) 
		{
			list_ele += " maltaChocolate";
		}
		if(productos.getCant_malta_crystal() != 0) 
		{
			list_ele += " maltaCrystal";
		}
		if(productos.getCant_malta_munich() != 0) 
		{
			list_ele += " maltaMunich";
		}
		if(productos.getCant_malta_negra() != 0) 
		{
			list_ele += " maltaNegra";
		}
		if(productos.getCant_malta_base_palida() != 0)
		{
			list_ele += " maltaBasePalida";
		}
		if(productos.getCant_malta_pilsner()>0){
			list_ele += " MaltaPilsner";
		}
		if(productos.getCant_lupulo_perle()>0){
			list_ele += " LupuloPerle";
		}
		if(productos.getCant_lupulo_tettnanger()>0){
			list_ele += " LupuloTettnanger";
		}
		if(productos.getCant_levadura_lager()>0){
			list_ele += " LevaduraLager";
		}
		if(productos.getCant_levadura_ale()>0){
			list_ele += " LevaduraAle";
		}
		

		return list_ele;
	}


	private List <MateriaPrima> get_materia_prima(Productos productos) throws ClassNotFoundException, SQLException, RuntimeException, NullException 
	{
		List <MateriaPrima> list_ele;
		int id;



		list_ele = new ArrayList<MateriaPrima>();

		if(productos.getCant_malta_base_palida()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima maltaPalida = new MateriaPrima("MaltaBasePalida",id,productos.getCant_malta_base_palida());
			metodosCompany.insertarMateriaPrima(maltaPalida);
			list_ele.add(maltaPalida);
		}
		if(productos.getCant_malta_munich()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima maltaMunich = new MateriaPrima("MaltaMunich",id,productos.getCant_malta_munich());
			metodosCompany.insertarMateriaPrima(maltaMunich);
			list_ele.add(maltaMunich);
		}
		if(productos.getCant_malta_negra()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima maltaNegra = new MateriaPrima("MaltaNegra",id,productos.getCant_malta_negra());
			metodosCompany.insertarMateriaPrima(maltaNegra);
			list_ele.add(maltaNegra);
		}
		if(productos.getCant_malta_crystal()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima maltaCrystal = new MateriaPrima("MaltaCrystal",id,productos.getCant_malta_crystal());
			metodosCompany.insertarMateriaPrima(maltaCrystal);
			list_ele.add(maltaCrystal);
		}
		if(productos.getCant_malta_chocolate()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima maltaChocolate = new MateriaPrima("MaltaChocolate",id,productos.getCant_malta_chocolate());
			metodosCompany.insertarMateriaPrima(maltaChocolate);
			list_ele.add(maltaChocolate);
		}
		if(productos.getCant_malta_caramelo()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima maltaCaramelo = new MateriaPrima("MaltaCaramelo",id,productos.getCant_malta_caramelo());
			metodosCompany.insertarMateriaPrima(maltaCaramelo);
			list_ele.add(maltaCaramelo);
		}
		if(productos.getCant_malta_pilsner()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima maltaPilsner = new MateriaPrima("MaltaPilsner",id,productos.getCant_malta_pilsner());
			metodosCompany.insertarMateriaPrima(maltaPilsner);
			list_ele.add(maltaPilsner);
		}
		if(productos.getCant_cebada_tostada()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima cebadaTostada = new MateriaPrima("CebadaTostada",id,productos.getCant_cebada_tostada());
			metodosCompany.insertarMateriaPrima(cebadaTostada);
			list_ele.add(cebadaTostada);
		}
		if(productos.getCant_lupulo_centennial()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima lupuloCentennial = new MateriaPrima("LupuloCentennial",id,productos.getCant_lupulo_centennial());
			metodosCompany.insertarMateriaPrima(lupuloCentennial);
			list_ele.add(lupuloCentennial);
		}
		if(productos.getCant_lupulo_perle()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima lupuloPerle = new MateriaPrima("LupuloPerle",id,productos.getCant_lupulo_perle());
			metodosCompany.insertarMateriaPrima(lupuloPerle);
			list_ele.add(lupuloPerle);
		}
		if(productos.getCant_lupulo_tettnanger()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima lupuloTettnanger = new MateriaPrima("LupuloTettnanger",id,productos.getCant_lupulo_tettnanger());
			metodosCompany.insertarMateriaPrima(lupuloTettnanger);
			list_ele.add(lupuloTettnanger);
		}
		if(productos.getCant_levadura_lager()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima levaduraLager = new MateriaPrima("LevaduraLager",id,productos.getCant_levadura_lager());
			metodosCompany.insertarMateriaPrima(levaduraLager);
			list_ele.add(levaduraLager);
		}
		if(productos.getCant_levadura_ale()>0){
			id = metodosCompany.idMateriaPrima();
			MateriaPrima levaduraAle = new MateriaPrima("LevaduraAle",id,productos.getCant_levadura_ale());
			metodosCompany.insertarMateriaPrima(levaduraAle);
			list_ele.add(levaduraAle);
		}
		
		

		return list_ele;
	}



}
