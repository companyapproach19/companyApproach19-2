package equipo7.otros;


import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import equipo7.model.OrdenTrazabilidad;
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
    public static String crearJSONlista(ArrayList<Integer> ordenes) {
    	GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        return gson.toJson(ordenes);
    }

}