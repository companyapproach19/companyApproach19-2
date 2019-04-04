package equipo6.otros;

import java.util.List;

import equipo4.model.Lote;
import equipo6.model.Bloque;
import equipo5.model.Cadena;
import equipo6.model.DatosContainer;
import equipo7.model.OrdenTrazabilidad;
import equipo8.model.Registro;
import equipo8.model.Sensor;


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
    public void guardarOrden(DatosContainer datos_container){
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
    
    private int get_id_datos(DatosContainer datos_container) 
    {
    	if(datos_container instanceof OrdenTrazabilidad) return ((OrdenTrazabilidad)datos_container).getId();
    	if(datos_container instanceof Registro) return ((Registro)datos_container).getIdLote();
    	if(datos_container instanceof Lote) return ((Lote)datos_container).getCode();
    	return -1;
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
//    	Cadena cadena = BBDD.getCadena(codLote);
//    	List<Bloque> bloques = cadena.getCadena();
//    	int i =0;
//    	while(i<bloques.size()){
//    		if(bloques.get(i).getTipoBloque() == 0){
//    			return (Traspaso) bloques.get(i).getDatos();
//    		}
//    		i++;
//    	}
//    	return null;
    	
    	
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
