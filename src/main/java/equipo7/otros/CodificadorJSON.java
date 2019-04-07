package equipo7.otros;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import equipo7.model.ListaPedidos;
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
    
    //Pasar una lista de pedidos a json
    public static String crearJSONlista(ListaPedidos pedidos) {
    	GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        return gson.toJson(pedidos);
    }
   

}