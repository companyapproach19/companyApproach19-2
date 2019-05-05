package equipo6.otros;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import equipo4.model.Lote;
import equipo4.model.MateriaPrima;
import equipo6.model.Actor;
import equipo6.model.Bloque;
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
	public void guardarOrden(DatosContainer datos_container) throws Throwable{
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
				System.out.println(((OrdenTrazabilidad)datos_container).getActorOrigen().getId());
				System.out.println(((OrdenTrazabilidad)datos_container).getActorDestino().getId());
				if(!operaciones_stock((OrdenTrazabilidad)datos_container))return; 
			}
			cadena.incorporarBloque(datos_container, tipoBlque); //Cambiar cuando asignemos cada entero a cada tipo de bloque
		}catch (Exception ex) {
			ex.printStackTrace();
		}


	}

	private StockMP get_coincidencia(List <StockMP> stock_mp,String tipo) 
	{
		List <StockMP> stock_filtrado;

		stock_filtrado = new ArrayList<StockMP>();

		for(StockMP stock : stock_mp) 
		{
			if(stock.getMp()
					.getTipo()
					.equals(tipo)) 
			{
				return stock;
			}
		}

		return null;
	}

	private boolean operaciones_stock(OrdenTrazabilidad orden) throws ClassNotFoundException, SQLException, NotInDatabaseException, equipo5.dao.NotInDatabaseException, RuntimeException, NullException 
	{
		List <StockMP> stock_mp;
		List <MateriaPrima> list_materia_prima;
		StockMP coincidencia;
		boolean valor_retorno;

		valor_retorno = false;
		list_materia_prima = get_materia_prima(orden.getProductosPedidos());


		try {
			switch(orden.getEstado()) 
			{
			case 1:

				if(orden.getActorDestino().getTipoActor() == 0){
					valor_retorno = true;
					break;
				}

				stock_mp = metodosCompany.extraerStockMpPorPedido(orden.getActorDestino(),orden);

				if(stock_mp.size() == 0) {
					valor_retorno = false;
					break;
				}

				for(MateriaPrima materia_prima : list_materia_prima) {

					coincidencia = get_coincidencia(stock_mp, materia_prima.getTipo());

					if(coincidencia != null)
					{
						metodosCompany.insertarStockMP(coincidencia);
						valor_retorno = true;
					}

				}

				break;

			case 4:

				for(MateriaPrima materia_prima : list_materia_prima)
					metodosCompany.insertarStockMP(new StockMP(materia_prima, null, null, orden.getId(), orden.getIdPedido(), orden.getActorOrigen().getId()));

			default:
				valor_retorno = true;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return valor_retorno;
	}

	public void guardarRespuestaPedido(int id_pedido_destino, ArrayList<Integer> ids_stock_origen) throws Throwable 
	{

		Cadena origen;
		Cadena destino;
		Bloque new_super_block;
		String new_utimo_hash;

		destino = metodosCompany.extraerCadena(id_pedido_destino);
		origen = null;

		for(Integer id : ids_stock_origen) 
		{
			origen = metodosCompany.extraerCadena(id);
			new_super_block = new Bloque(destino.getHashUltimoBloque(), -1, destino.getNumBloques(), destino.getCodLote(), new DatosContainer(), origen.getCodLote(),-1);
			destino.incrementarNumBloques();
			new_utimo_hash = new_super_block.getHashCode();
			try {
				metodosCompany.insertarBloque(new_super_block);
				destino.setHashUltimoBloque(new_utimo_hash);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if(destino != null)
			metodosCompany.insertarCadena(destino);

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

		if(datos_container instanceof OrdenTrazabilidad) return ((OrdenTrazabilidad)datos_container).getIdPedido();
		if(datos_container instanceof Registro) return ((Registro)datos_container).getIdPedido();
		if(datos_container instanceof Lote) return ((Lote)datos_container).getIdBd();
		if(datos_container instanceof geolocalizacion) return metodosCompany.extraerOrdenTrazabilidad(((geolocalizacion)datos_container).getIdOrden()).getIdPedido();

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


	private List <MateriaPrima> get_materia_prima(Productos productos) throws ClassNotFoundException, SQLException, RuntimeException, NullException 
	{
		List <MateriaPrima> list_materia_prima;
		MateriaPrima materiaPrima;
		int id;
		int cantidad;
		


		list_materia_prima = new ArrayList<MateriaPrima>();

		if(productos.getCant_cebada() != 0) 
		{
			cantidad = productos.getCant_cebada();
			id = metodosCompany.idMateriaPrima();
			materiaPrima = new MateriaPrima("Cebada", id, cantidad);
			metodosCompany.insertarMateriaPrima(materiaPrima);
			list_materia_prima.add(materiaPrima);
		}
		if(productos.getCant_cebada_tostada() != 0) 
		{
			cantidad = productos.getCant_cebada_tostada();
			id = metodosCompany.idMateriaPrima();
			materiaPrima = new MateriaPrima("cebadaTostada", id, cantidad);
			metodosCompany.insertarMateriaPrima(materiaPrima);
			list_materia_prima.add(materiaPrima);
		}
		if(productos.getCant_lupulo_centenial() != 0) 
		{
			cantidad = productos.getCant_lupulo_centenial();
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
		if(productos.getCant_malta_palida() != 0)
		{
			cantidad = productos.getCant_malta_palida();
			id = metodosCompany.idMateriaPrima();
			materiaPrima = new MateriaPrima("maltaBasePalida", id, cantidad);
			metodosCompany.insertarMateriaPrima(materiaPrima);
			list_materia_prima.add(materiaPrima);
		}

		return list_materia_prima;
	}



}
