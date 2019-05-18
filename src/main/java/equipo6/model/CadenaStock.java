package equipo6.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import equipo4.model.MateriaPrima;
import equipo5.dao.NullException;
import equipo5.dao.metodosCompany;
import equipo5.model.NotInDatabaseException;
import equipo5.model.StockLote;
import equipo5.model.StockMP;
import equipo7.model.OrdenTrazabilidad;

public class CadenaStock {
	

	public static List<StockMP> filtrar_stock_tipo_materia(Actor actor,OrdenTrazabilidad orden,String tipo) throws ClassNotFoundException, SQLException, NotInDatabaseException 
	{
		List<StockMP> lista_filtrada;
		List<StockMP> lista_total;
		
		lista_total = metodosCompany.extraerStockMpPorPedido(actor, orden);
		lista_filtrada = new ArrayList<StockMP>();
		
		for(StockMP stock_mp : lista_total) 
		{
			if(stock_mp.getMp().getTipo().equals(tipo)) 
			{
				lista_filtrada.add(stock_mp);
			}
		}
		
		return lista_filtrada;
	}
	
	public static int get_cantidad_stock(List<StockMP> lista_stock) throws ClassNotFoundException, SQLException, NotInDatabaseException 
	{
		int cantidad;
		
		cantidad = 0;
		
		if(lista_stock == null) return cantidad;
		
		for(StockMP stock_mp : lista_stock) 
		{
			cantidad += stock_mp.getMp().getCantidad();
		}
		
		return cantidad;
	}
	
	public static boolean actualizar_stock(OrdenTrazabilidad or_origen) throws ClassNotFoundException, SQLException, NotInDatabaseException, NullException 
	{
		Actor origen;
		List<StockMP> lista_stock_mp;
		
		origen = or_origen.getActorOrigen();
		lista_stock_mp = metodosCompany.extraerStockMpPorPedido(origen, or_origen);
		if(lista_stock_mp != null)
		{
			for(StockMP stock_mp : lista_stock_mp) {
				metodosCompany.insertarStockMP(stock_mp);
			}
			return true;
		}
		return false;
	}
}
