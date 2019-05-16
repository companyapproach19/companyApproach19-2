package equipo7.otros;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import equipo7.model.OrdenTrazabilidad;
import equipo7.otros.ListaIDs;
import equipo7.otros.IDsOrdenes;


public class DescodificadorJson {
	
	public OrdenTrazabilidad DescodificadorJson(String pedido) {
		 Gson gson=new Gson();
		 Type tipoObjeto = new TypeToken<OrdenTrazabilidad>(){}.getType();
		 return gson.fromJson(pedido, tipoObjeto);  
	}

	public ListaIDs DescodificadorJSONlista(String lista) {
		Gson gson=new Gson();
		Type tipoObjeto = new TypeToken<ListaIDs>(){}.getType();
		return gson.fromJson(lista, tipoObjeto);
	}
	
	public OrdenInicial DescodificadorJSONinicial(String jsonInicial) {
		Gson gson=new Gson();
		Type tipoObjeto = new TypeToken<OrdenInicial>(){}.getType();
		return gson.fromJson(jsonInicial, tipoObjeto);	
	}

	public IDsOrdenes DescodificadorJSONrespuestas(String ordenes) {
		Gson gson=new Gson();
		Type tipoObjeto = new TypeToken<IDsOrdenes>(){}.getType();
		return gson.fromJson(ordenes, tipoObjeto);
	}
	
}