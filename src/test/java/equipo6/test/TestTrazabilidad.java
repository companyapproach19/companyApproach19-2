package equipo6.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import equipo5.dao.metodosCompany;
import equipo6.otros.BlockchainServices;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo8.model.Sensor;
import equipo6.model.Actor;
import equipo6.objetosTemporales.BBDDTemporal;
import equipo6.objetosTemporales.Traspaso;

class TestTrazabilidad {
	
	
	void insertar_usuarios() throws ClassNotFoundException, SQLException, RuntimeException 
	{
		metodosCompany.insertarActor(new Actor("654521615F", "Agricultor", "root", "Agricultor@Agricultor.com", 0, "5465,5644", "Agricultor", "Agricultor casa", null));
		metodosCompany.insertarActor(new Actor("824534315P", "Fabrica", "root", "Fabrica@Fabrica.com", 2, "5465,5644", "Fabrica", "Fabrica casa", null));
		metodosCompany.insertarActor(new Actor("954521615K", "Cooperativa", "root", "Cooperativa@Cooperativa.com", 1, "5465,5644", "Cooperativa", "Cooperativa casa", null));
		metodosCompany.insertarActor(new Actor("686525635L", "Retailer", "root", "Retailer@Retailer.com", 3, "5465,5644", "Retailer", "Retailer casa", null));

	}
	
	void init_data_base() throws Exception 
	{
		metodosCompany.conectar();
		metodosCompany.crearBD();
		insertar_usuarios();
	}

	@Test
	void test() throws Exception  {
		try {
		init_data_base();
		BlockchainServices bcs = new BlockchainServices();
		Actor actor_o;
		Actor actor_d;
		Productos p;
		OrdenTrazabilidad orden;
		ArrayList<Integer> lista_ids_stock;
		Gson gson;
		
		gson = new Gson();
				
		lista_ids_stock = new ArrayList<Integer>();
		p = new Productos(0, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10);
		actor_o = new Actor("Agricultor", "Agricultor", "root", "pepe@pepe.com", 1, "5465 , 6454", "gonzalo", "una", "hadskj");
		actor_d = new Actor("Fabrica", "Fabrica", "root", "pepe@pepe.com", 1, "5465 , 6454", "pepe", "una", "hadskj");
		
		System.out.println((gson.toJson(new OrdenTrazabilidad(5, "soy pepe", actor_o, actor_d, p))));
		
		bcs.guardarOrden(new OrdenTrazabilidad(0, "que pasa", actor_o, actor_d, p));
		bcs.guardarOrden(new OrdenTrazabilidad(1, "hola que tal", actor_o, actor_d, p));
		bcs.guardarOrden(new OrdenTrazabilidad(2, "velocidad", actor_o, actor_d, p));
		bcs.guardarOrden(new OrdenTrazabilidad(3, "que bien", actor_o, actor_d, p));
		bcs.guardarOrden(new OrdenTrazabilidad(4, "estupendo", actor_o, actor_d, p));
		bcs.guardarOrden(new OrdenTrazabilidad(5, "soy pepe", actor_o, actor_d, p));

		
		
		lista_ids_stock.add(1);
		lista_ids_stock.add(2);
		lista_ids_stock.add(3);
		
		bcs.guardarRespuestaPedido(0, lista_ids_stock);
		
		lista_ids_stock = new ArrayList<Integer>();
		
		lista_ids_stock.add(0);
		lista_ids_stock.add(4);
		
		bcs.guardarRespuestaPedido(5, lista_ids_stock);
		
		
		
		System.out.println(bcs.get_trazabilidad(5));
		}
		catch (Exception e) {
				e.printStackTrace();
		}
		
		
		
	}

}
