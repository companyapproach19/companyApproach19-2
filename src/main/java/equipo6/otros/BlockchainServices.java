package equipo6.otros;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import equipo4.model.Lote;
import equipo6.model.Bloque;
import equipo5.dao.metodosCompany;
import equipo5.model.Cadena;
import equipo6.model.DatosContainer;
import equipo7.model.OrdenTrazabilidad;
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
        	cadena.incorporarBloque(datos_container, tipoBlque); //Cambiar cuando asignemos cada entero a cada tipo de bloque
        }catch (Exception ex) {
        	ex.printStackTrace();
        }

          
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
    

    public ArrayList<OrdenTrazabilidad> extraerPedido(String idActor) throws ClassNotFoundException, SQLException
    {
    	return metodosCompany.extraerPedidosActorDestino(idActor);
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
    	return null;
    }
    
    private int get_id_datos(DatosContainer datos_container) 
    {
    	if(datos_container instanceof OrdenTrazabilidad) return ((OrdenTrazabilidad)datos_container).getId();
    	if(datos_container instanceof Registro) return ((Registro)datos_container).getId();
    	if(datos_container instanceof Lote) return ((Lote)datos_container).getIdBd();
    	return -1;
    }
    
    private String get_name_class(DatosContainer datos_container) 
    {
    	if(datos_container instanceof OrdenTrazabilidad) return "Orden_Trazabilidad";
    	if(datos_container instanceof Registro) return "Registro";
    	if(datos_container instanceof Lote) return "Lote";
    	return "Error";
    }
    
    
    private int get_tipo_bloque(DatosContainer datos_container) 
    {
    	if(datos_container instanceof OrdenTrazabilidad) return 0;
    	if(datos_container instanceof Registro) return 1;
    	if(datos_container instanceof Lote) return 2;
    	return -1;
    }


    //Funcion que devuelve la informacion de un traspaso. 
    //Aun por concretar como se va a identificar al traspaso, si por un id_traspaso o como. 
    //en cualquier caso, nos pasan siempre el codLote, nosotros cogemos la cadena asociada
    //a ese lote y a partir de ahi vamos tirando. 

    //Obtiene bloque adecuado utilizando los metodos de clase Cadena, y una vez lo tiene 
    //extrae la informacion del traspaso y la devuelve.
    //TODO anton
    public OrdenTrazabilidad getTraspaso(int codLote){ 	
    	
    	try {
			Cadena cadena = equipo5.dao.metodosCompany.extraerCadena(codLote);
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
}
