package equipo7.otros;

<<<<<<< HEAD

import java.lang.reflect.Type;
import java.util.ArrayList;

=======
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import equipo7.model.OrdenTrazabilidad;
import equipo7.otros.ListaIDs;
<<<<<<< HEAD
=======
import equipo7.otros.IDsOrdenes;
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e

public class CodificadorJSON {

    /* Esta clase se encarga de pasar de objetos en Java
     * a un archivo JSON, para la interoperabilidad entre
     * el backend y la interfaz (en Javascript) */

    public static String crearJSON(OrdenTrazabilidad ordenTrazabilidad) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        return gson.toJson(ordenTrazabilidad);
    }
    
    //Pasar una lista de ids de ordenes a json
    public static String crearJSONlista(ListaIDs ordenes) {
    	GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        return gson.toJson(ordenes);
<<<<<<< HEAD
=======
    }

    public static String crearJSONrespuestas(IDsOrdenes ordenes) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        return gson.toJson(ordenes);
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e
    }

}