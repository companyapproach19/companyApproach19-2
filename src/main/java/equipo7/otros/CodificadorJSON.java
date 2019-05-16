package equipo7.otros;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import equipo7.model.OrdenTrazabilidad;
import equipo7.otros.ListaIDs;
import equipo7.otros.IDsOrdenes;

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
    }

    public static String crearJSONrespuestas(IDsOrdenes ordenes) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        return gson.toJson(ordenes);
    }

}