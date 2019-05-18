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


	private boolean operaciones_stock(OrdenTrazabilidad orden) throws ClassNotFoundException, SQLException, NotInDatabaseException, RuntimeException, NullException 
	{
		List <MateriaPrima> list_materia_prima;
		Cadena cadenaDestino;
		OrdenTrazabilidad orden_origen;
		Bloque super_bloque;

		list_materia_prima = get_materia_prima(orden.getProductosPedidos());


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
				CadenaStock.actualizar_stock(orden_origen);
				
				break;

			case 4:

				for(MateriaPrima materia_prima : list_materia_prima)
					metodosCompany.insertarStockMP(new StockMP(materia_prima, null, null, orden.getId(), orden.getIdPedido(), orden.getActorOrigen().getId()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
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
		String list_materia_prima;



		list_materia_prima = "";

		if(productos.getCant_cebada_tostada() != 0) 
		{
			list_materia_prima += " cebadaTostada";
		}
		if(productos.getCant_lupulo_centennial() != 0) 
		{
			list_materia_prima += " lupuloCentennial";
		}
		if(productos.getCant_malta_caramelo() != 0) 
		{
			list_materia_prima += " maltaCaramelo";
		}
		if(productos.getCant_malta_chocolate() != 0) 
		{
			list_materia_prima += " maltaChocolate";
		}
		if(productos.getCant_malta_crystal() != 0) 
		{
			list_materia_prima += " maltaCrystal";
		}
		if(productos.getCant_malta_munich() != 0) 
		{
			list_materia_prima += " maltaMunich";
		}
		if(productos.getCant_malta_negra() != 0) 
		{
			list_materia_prima += " maltaNegra";
		}
		if(productos.getCant_malta_base_palida() != 0)
		{
			list_materia_prima += " maltaBasePalida";
		}

		return list_materia_prima;
	}


	private List <MateriaPrima> get_materia_prima(Productos productos) throws ClassNotFoundException, SQLException, RuntimeException, NullException 
	{
		List <MateriaPrima> list_materia_prima;
		MateriaPrima materiaPrima;
		int id;
		int cantidad;



		list_materia_prima = new ArrayList<MateriaPrima>();

		if(productos.getCant_cebada_tostada() != 0) 
		{
			cantidad = productos.getCant_cebada_tostada();
			id = metodosCompany.idMateriaPrima();
			materiaPrima = new MateriaPrima("cebadaTostada", id, cantidad);
			metodosCompany.insertarMateriaPrima(materiaPrima);
			list_materia_prima.add(materiaPrima);
		}
		if(productos.getCant_lupulo_centennial() != 0) 
		{
			cantidad = productos.getCant_lupulo_centennial();
			id = metodosCompany.idMateriaPrima();
			materiaPrima = new MateriaPrima("lupuloCentennial", id, cantidad);
			metodosCompany.insertarMateriaPrima(materiaPrima);
			list_materia_prima.add(materiaPrima);
		}
		if(productos.getCant_malta_caramelo() != 0) 
		{
			cantidad = productos.getCant_malta_caramelo();
			id = metodosCompany.idMateriaPrima();
			materiaPrima = new MateriaPrima("maltaCaramelo", id, cantidad);
			metodosCompany.insertarMateriaPrima(materiaPrima);
			list_materia_prima.add(materiaPrima);
		}
		if(productos.getCant_malta_chocolate() != 0) 
		{
			cantidad = productos.getCant_malta_chocolate();
			id = metodosCompany.idMateriaPrima();
			materiaPrima = new MateriaPrima("maltaChocolate", id, cantidad);
			metodosCompany.insertarMateriaPrima(materiaPrima);
			list_materia_prima.add(materiaPrima);
		}
		if(productos.getCant_malta_crystal() != 0) 
		{
			cantidad = productos.getCant_malta_crystal();
			id = metodosCompany.idMateriaPrima();
			materiaPrima = new MateriaPrima("maltaCrystal", id, cantidad);
			metodosCompany.insertarMateriaPrima(materiaPrima);
			list_materia_prima.add(materiaPrima);
		}
		if(productos.getCant_malta_munich() != 0) 
		{
			cantidad = productos.getCant_malta_munich();
			id = metodosCompany.idMateriaPrima();
			materiaPrima = new MateriaPrima("maltaMunich", id, cantidad);
			metodosCompany.insertarMateriaPrima(materiaPrima);
			list_materia_prima.add(materiaPrima);
		}
		if(productos.getCant_malta_negra() != 0) 
		{
			cantidad = productos.getCant_malta_negra();
			id = metodosCompany.idMateriaPrima();
			materiaPrima = new MateriaPrima("maltaNegra", id, cantidad);
			metodosCompany.insertarMateriaPrima(materiaPrima);
			list_materia_prima.add(materiaPrima);
		}
		if(productos.getCant_malta_base_palida() != 0)
		{
			cantidad = productos.getCant_malta_base_palida();
			id = metodosCompany.idMateriaPrima();
			materiaPrima = new MateriaPrima("maltaBasePalida", id, cantidad);
			metodosCompany.insertarMateriaPrima(materiaPrima);
			list_materia_prima.add(materiaPrima);
		}

		return list_materia_prima;
	}



}