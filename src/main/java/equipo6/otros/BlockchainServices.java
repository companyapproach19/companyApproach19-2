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
    
    
    private boolean operaciones_stock(OrdenTrazabilidad orden) throws ClassNotFoundException, SQLException, NotInDatabaseException 
    {
    	List <StockMP> stock_mp;
    	MateriaPrima materia_prima;
    	
    	switch(orden.getEstado()) 
    	{
    	case 1:
    		if(orden.getActorDestino().getTipoActor() == 0)return true;
    		stock_mp = metodosCompany.extraerStockMP(orden.getActorDestino(), getTraspaso(orden.getIdPedido()).getId());
    		if(stock_mp.size() != 1)return false;
    		metodosCompany.insertarStockMP(stock_mp.get(0));
    		break;
    	case 4:
    		materia_prima = get_materia_prima(orden.getProductosPedidos());
    		metodosCompany.insertarStockMP(new StockMP(materia_prima, null, null, orden.getId(), orden.getIdPedido(), orden.getActorOrigen().getId()));
    		break;
    	}
    	
    	return true;
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
    		new_super_block = new Bloque(destino.getHashUltimoBloque(), -1, destino.getNumBloques(), destino.getCodLote(), new DatosContainer(), origen.getCodLote());
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
    
    private int get_id_datos(DatosContainer datos_container) 
    {
    	if(datos_container instanceof OrdenTrazabilidad) return ((OrdenTrazabilidad)datos_container).getIdPedido();
    	if(datos_container instanceof Registro) return ((Registro)datos_container).getId();
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


    //Funcion que devuelve la informacion de un traspaso. 
    //Aun por concretar como se va a identificar al traspaso, si por un id_traspaso o como. 
    //en cualquier caso, nos pasan siempre el codLote, nosotros cogemos la cadena asociada
    //a ese lote y a partir de ahi vamos tirando. 

    //Obtiene bloque adecuado utilizando los metodos de clase Cadena, y una vez lo tiene 
    //extrae la informacion del traspaso y la devuelve.
    //TODO anton
    private OrdenTrazabilidad getTraspaso(int idPedido){ 	
    	
    	try {
			Cadena cadena = equipo5.dao.metodosCompany.extraerCadena(idPedido);
			List<Bloque> bloques = cadena.getBloque(0);
			if (!bloques.isEmpty()) {
				return (OrdenTrazabilidad) bloques.get(bloques.size() - 1).getDatos();
			}
    	}catch(Exception ex) {
    		ex.printStackTrace();
    		return null;
    	}
      return null;    	
    } 
	
	
	   
    public OrdenTrazabilidad getOrden(int idOrden) throws ClassNotFoundException, SQLException{
    	return	equipo5.dao.metodosCompany.extraerOrdenTrazabilidad(idOrden);
    }
    
    
    private MateriaPrima get_materia_prima(Productos productos) throws ClassNotFoundException, SQLException 
    {
    	MateriaPrima materia_prima;
    	int id;
    	int cantidad;
    	
    	if(productos.getCant_cebada() != 0) 
    	{
    		cantidad = productos.getCant_cebada();
    		id = metodosCompany.idMateriaPrima();
    		materia_prima = new MateriaPrima("Cebada", id, cantidad);
    	}
    	else if(productos.getCant_cebada_tostada() != 0) 
    	{
    		cantidad = productos.getCant_cebada_tostada();
    		id = metodosCompany.idMateriaPrima();
    		materia_prima = new MateriaPrima("cebadaTostada", id, cantidad);
    	}
    	else if(productos.getCant_lupulo_centenial() != 0) 
    	{
    		cantidad = productos.getCant_lupulo_centenial();
    		id = metodosCompany.idMateriaPrima();
    		materia_prima = new MateriaPrima("lupuloCentennial", id, cantidad);
    	}
    	else if(productos.getCant_malta_caramelo() != 0) 
    	{
    		cantidad = productos.getCant_malta_caramelo();
    		id = metodosCompany.idMateriaPrima();
    		materia_prima = new MateriaPrima("maltaCaramelo", id, cantidad);
    	}
    	else if(productos.getCant_malta_chocolate() != 0) 
    	{
    		cantidad = productos.getCant_malta_chocolate();
    		id = metodosCompany.idMateriaPrima();
    		materia_prima = new MateriaPrima("maltaChocolate", id, cantidad);
    	}
    	else if(productos.getCant_malta_crystal() != 0) 
    	{
    		cantidad = productos.getCant_malta_crystal();
    		id = metodosCompany.idMateriaPrima();
    		materia_prima = new MateriaPrima("maltaCrystal", id, cantidad);
    	}
    	else if(productos.getCant_malta_munich() != 0) 
    	{
    		cantidad = productos.getCant_malta_munich();
    		id = metodosCompany.idMateriaPrima();
    		materia_prima = new MateriaPrima("maltaMunich", id, cantidad);
    	}
    	else if(productos.getCant_malta_negra() != 0) 
    	{
    		cantidad = productos.getCant_malta_negra();
    		id = metodosCompany.idMateriaPrima();
    		materia_prima = new MateriaPrima("maltaNegra", id, cantidad);
    	}
    	else 
    	{
    		cantidad = productos.getCant_malta_palida();
    		id = metodosCompany.idMateriaPrima();
    		materia_prima = new MateriaPrima("maltaBasePalida", id, cantidad);
    	}
    	
    	return materia_prima;
    }
	
	
	
}
