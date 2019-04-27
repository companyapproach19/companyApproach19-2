package equipo7.otros;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import equipo7.model.OrdenTrazabilidad;


public class DescodificadorJson {
	
	public OrdenTrazabilidad DescodificadorJson(String pedido) {
		 Gson gson=new Gson();
		 Type tipoObjeto = new TypeToken<OrdenTrazabilidad>(){}.getType();
		 return gson.fromJson(pedido, tipoObjeto);  
	}

	public ArrayList<Integer> DescodificadorJSONlista(String lista) {
		Gson gson=new Gson();
		Type tipoObjeto = new TypeToken<ArrayList<Integer>>(){}.getType();
		return gson.fromJson(lista, tipoObjeto);
	}

	
}
