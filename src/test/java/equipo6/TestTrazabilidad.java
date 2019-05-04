package equipo6;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import equipo4.model.Lote;
import equipo5.dao.metodosCompany;
import equipo6.otros.BlockchainServices;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo8.model.Registro;
import equipo6.model.Actor;


//https://beer-company2019.herokuapp.com

/*
 
  
  
INSERT INTO company.actor(cif,nombreusuario,passwdplana,email,tipoactor,localizacion,nombre,direccion,cifcooperativa)
VALUES ('33','G6','password','G6@gmail.es','2','40.4026076;-3.8363219','Luis','CalleOlivares','fg7'),
('11','TranspG3_1','password','tranpG3_1@gmail.es','2','40.4022076;-3.8369319','Maria','CalleRenacimiento','fg4'),
('12','TranspG3_2','password','tranpG3_2@gmail.es','2','40.4029076;-3.8369319','Jose','CalleIlustración','fg5'),
('0','agricultorG2','password','agricultorG2@gmail.es','0','40.4025976;-3.8393319','Marta','CalleGirona','fg3'),
('1','cooperativaG2','password','cooperativaG2@gmail.es','1','40.4025079;-3.8369319','Maria','CalleTarragona','fg2'),
('2','fabricaG3','password','fabricaG3@gmail.es','3','40.4925076;-3.8363399','Luis','CalleLerida','fg1'),
('3','retailerG2','password','retailerG2@gmail.es','4','40.4825076;-3.8383319','Santiago','CalleBarcelona','fg3'),
('5','Productor','password','prod@gmail.es','0','40.4085076;-3.8363318','Marta','CalleValladolid','fg3'),
('6','Cooperativa','password','coop@gmail.es','1','40.4825076;-3.8863319','Maria','CalleMurcia','fg2'),
('7','Transportista','password','transp@gmail.es','2','40.4025776;-3.8367319','Luis','CalleCartagena','fg1'),
('8','Fabrica','password','fab@gmail.es','3','40.4085076;-3.8363919','Santiago','CalleAndalucía','fg3'),
('9','Retailer','password','ret@gmail.es','4','40.4085076;-3.8393319','Pilar','CalleMadrid','fg2'),
('10','Agricultor','password','agri@gmail.es','0','40.4725076;-3.8763319','juan','CalleGoicoechea','fg3'),
('15','alberto','password','alberto@gmail.es','1','40.4028076;-3.8368319','alberto','AvenidaJarales','fg3'),
('17','felipe','password','felipe@gmail.es','2','40.4027076;-3.8368319','felipe','AvenidaIlustración','fg1'),
('16','maria','password','maria@gmail.es','3','40.4025086;-3.8363379','maria','AvenidaEuropa','fg2'),
('18','rick','password','rick@gmail.es','4','40.4025876;-3.8363379','rick','AvenidaArgentina','fg0');


  salidaTemporal ="[\n";
		salidaTemporal+="{\n";
		salidaTemporal+="\"nombre\":\"Fabrica A\", \n";
		salidaTemporal+="\"coordLat\":40.4025076 , \n";
		salidaTemporal+="\"coordLon\":-3.8363319 , \n";
		salidaTemporal+="\"stock\": 200 \n";
		salidaTemporal+="},{ \n";
		salidaTemporal+="\"nombre\":\"Fabrica B\", \n";
		salidaTemporal+="\"coordLat\":40.4035076 , \n";
		salidaTemporal+="\"coordLon\":-3.8353319 , \n";
		salidaTemporal+="\"stock\": 100 \n";
		salidaTemporal+="},{ \n";
		salidaTemporal+="\"nombre\":\"Fabrica C\", \n";
		salidaTemporal+="\"coordLat\":40.4030076 , \n";
		salidaTemporal+="\"coordLon\":-3.8343319 , \n";
		salidaTemporal+="\"stock\": 150 \n";
		salidaTemporal+="},{ \n";
		salidaTemporal+="\"nombre\":\"Fabrica D\", \n";
		salidaTemporal+="\"coordLat\":40.4130076 , \n";
		salidaTemporal+="\"coordLon\":-3.8243319 , \n";
		salidaTemporal+="\"stock\": 550 \n";
		salidaTemporal+="}\n";
		salidaTemporal+="]\n";
  
  
  
 */




class TestTrazabilidad {


	void insertar_usuarios() throws ClassNotFoundException, SQLException, RuntimeException 
	{
		metodosCompany.insertarActor(new Actor("5","Productor","password","prod@gmail.es", 0,"Calle Ribadeo", "Marta","Calle Valladolid","fg4"));
		metodosCompany.insertarActor(new Actor("6","Cooperativa","password","coop@gmail.es", 1,"Calle Ribadeo", "Maria","Calle Valladolid","fg5"));
		metodosCompany.insertarActor(new Actor("7","Transportista","password","transp@gmail.es", 2,"Calle Ribadeo", "Luis","Calle Valladolid","fg3"));

	}

	void insertar_lote() throws Throwable 
	{
		Lote lote;

		lote = new Lote(1000, new java.util.Date(), "uno", new java.util.Date(), true, false, false, false, false, null, 0);
		metodosCompany.insertarLote(lote);
	}

	void init_data_base() throws Throwable 
	{
		metodosCompany.conectar();
		metodosCompany.crearBD();
		//insertar_usuarios();
		//insertar_lote();

	}
	
	@Test
	void testInit() throws Throwable 
	{
		init_data_base();
	}


	void test1() throws Throwable  {
		try {

			//init_data_base();
			BlockchainServices bcs = new BlockchainServices();
			Actor actor_o;
			Actor actor_d;
			Actor actor_t;
			Productos p;
			OrdenTrazabilidad orden;
			ArrayList<Integer> lista_ids_stock;
			Lote lote;

			actor_t = new Actor("7","Transportista","password","transp@gmail.es", 2,"Calle Ribadeo", "Luis","Calle Valladolid","fg3");
			lote = new Lote(0, new java.util.Date(), "uno", new java.util.Date(), true, false, false, false, false, null, 0);
			Registro[] reg = {
					new Registro(0,lote, actor_t, "", "", 10, 5),
					new Registro(1,lote, actor_t, "", "", 11, 6),
					new Registro(2,lote, actor_t, "", "", 12, 7),
					new Registro(3,lote, actor_t, "", "", 13, 8),
					new Registro(4,lote, actor_t, "", "", 14, 9),
					new Registro(5,lote, actor_t, "", "", 15, 10),
					new Registro(6,lote, actor_t, "", "", 16, 11),
					new Registro(8,lote, actor_t, "", "", 17, 12),
					new Registro(9,lote, actor_t, "", "", 18, 13),
					new Registro(10,lote, actor_t, "", "", 18, 13),
			};
			Productos[] prod = {
					new Productos(0, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(1, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(2, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(3, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(4, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(5, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(6, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(7, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(8, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(9, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
			};

			lista_ids_stock = new ArrayList<Integer>();
			actor_o = new Actor("5","Productor","password","prod@gmail.es", 0,"Calle Ribadeo", "Marta","Calle Valladolid","fg4");
			actor_d = new Actor("6","Cooperativa","password","coop@gmail.es", 1,"Calle Ribadeo", "Maria","Calle Valladolid","fg5");




			bcs.guardarOrden(new OrdenTrazabilidad(0, actor_o, actor_d, true, prod[0], "primero", 0, null, null, 0, 0, actor_d, reg[0]));
			bcs.guardarOrden(new OrdenTrazabilidad(1, actor_o, actor_d, false, prod[1], "segundo", 0, null, null, 0, 0, actor_d, reg[1]));
			bcs.guardarOrden(new OrdenTrazabilidad(2, actor_o, actor_d, true, prod[2], "tercero", 0, null, null, 0, 0, actor_d, reg[2]));
			bcs.guardarOrden(new OrdenTrazabilidad(3, actor_o, actor_d, true, prod[3], "tercero", 0, null, null, 0, 0, actor_d, reg[3]));	
			bcs.guardarOrden(new OrdenTrazabilidad(0, actor_o, actor_d, true, prod[4], "primero", 0, null, null, 0, 0, actor_d, reg[4]));
			bcs.guardarOrden(new OrdenTrazabilidad(1, actor_o, actor_d, false, prod[5], "segundo", 0, null, null, 0, 0, actor_d, reg[5]));
			bcs.guardarOrden(new OrdenTrazabilidad(2, actor_o, actor_d, true, prod[6], "tercero", 0, null, null, 0, 0, actor_d, reg[6]));
			bcs.guardarOrden(new OrdenTrazabilidad(3, actor_o, actor_d, true, prod[7], "tercero", 0, null, null, 0, 0, actor_d, reg[7]));	
			bcs.guardarOrden(new OrdenTrazabilidad(0, actor_o, actor_d, true, prod[8], "primero", 0, null, null, 0, 0, actor_d, reg[8]));
			bcs.guardarOrden(new OrdenTrazabilidad(1, actor_o, actor_d, false, prod[9], "segundo", 0, null, null, 0, 0, actor_d, reg[9]));



			lista_ids_stock.add(1);
			lista_ids_stock.add(3);

			bcs.guardarRespuestaPedido(0, lista_ids_stock);

			lista_ids_stock = new ArrayList<Integer>();

			lista_ids_stock.add(0);

			bcs.guardarRespuestaPedido(2, lista_ids_stock);



			System.out.println(bcs.get_trazabilidad(2));

		}
		catch (Exception e) {
			e.printStackTrace();
		}



	}


	void testGetTrazabilidad() throws Throwable  {
		try {
			try {
				insertar_lote();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			BlockchainServices bcs = new BlockchainServices();
			Actor actor_o;
			Actor actor_d;
			Actor actor_t;
			Productos p;
			OrdenTrazabilidad orden;
			ArrayList<Integer> lista_ids_stock;
			Lote lote;

			actor_t = new Actor("7","Transportista","password","transp@gmail.es", 2,"Calle Ribadeo", "Luis","Calle Valladolid","fg3");
			lote = new Lote(1000, new java.util.Date(), "uno", new java.util.Date(), true, false, false, false, false, null, 0);
			Registro[] reg = {
					new Registro(100,lote, actor_t, "", "", 10, 5),
					new Registro(101,lote, actor_t, "", "", 11, 6),
					new Registro(102,lote, actor_t, "", "", 12, 7),
					new Registro(103,lote, actor_t, "", "", 13, 8),
			};
			Productos[] prod = {
					new Productos(100, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(101, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(102, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(103, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
			};

			lista_ids_stock = new ArrayList<Integer>();
			actor_o = new Actor("5","Productor","password","prod@gmail.es", 0,"Calle Ribadeo", "Marta","Calle Valladolid","fg4");
			actor_d = new Actor("6","Cooperativa","password","coop@gmail.es", 1,"Calle Ribadeo", "Maria","Calle Valladolid","fg5");




			bcs.guardarOrden(new OrdenTrazabilidad(0, actor_o, actor_d, true, prod[0], "primero", 0, null, null, 0, 0, actor_d, reg[0]));
			bcs.guardarOrden(new OrdenTrazabilidad(1, actor_o, actor_d, false, prod[1], "segundo", 0, null, null, 0, 0, actor_d, reg[1]));
			bcs.guardarOrden(new OrdenTrazabilidad(2, actor_o, actor_d, true, prod[2], "tercero", 0, null, null, 0, 0, actor_d, reg[2]));
			bcs.guardarOrden(new OrdenTrazabilidad(3, actor_o, actor_d, true, prod[3], "cuarto", 0, null, null, 0, 0, actor_d, reg[3]));			


			lista_ids_stock.add(1);
			lista_ids_stock.add(3);

			bcs.guardarRespuestaPedido(0, lista_ids_stock);

			lista_ids_stock = new ArrayList<Integer>();

			lista_ids_stock.add(0);

			bcs.guardarRespuestaPedido(2, lista_ids_stock);



			System.out.println(bcs.get_trazabilidad(2));

		}
		catch (Exception e) {
			e.printStackTrace();
		}



	}

	void testInsertarOrdenTrazabilidad() throws Throwable  {
		try {
			try {
				insertar_lote();
			}
			catch (Exception e) {
				//e.printStackTrace();
			}
			BlockchainServices bcs = new BlockchainServices();
			Actor actor_o;
			Actor actor_d;
			Actor actor_t;
			Productos p;
			Lote lote;

			actor_t = new Actor("7","Transportista","password","transp@gmail.es", 2,"Calle Ribadeo", "Luis","Calle Valladolid","fg3");
			lote = new Lote(1000, new java.util.Date(), "uno", new java.util.Date(), true, false, false, false, false, null, 0);
			Registro[] reg = {
					new Registro(100,lote, actor_t, "", "", 10, 5),
					new Registro(101,lote, actor_t, "", "", 11, 6),
					new Registro(102,lote, actor_t, "", "", 12, 7),
					new Registro(103,lote, actor_t, "", "", 13, 8),
			};
			Productos[] prod = {
					new Productos(100, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(101, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(102, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
					new Productos(103, 10, 10, 10, 10, 10, 0, 0, 10, 10, 10, 10),
			};

			actor_o = new Actor("5","Productor","password","prod@gmail.es", 0,"Calle Ribadeo", "Marta","Calle Valladolid","fg4");
			actor_d = new Actor("6","Cooperativa","password","coop@gmail.es", 1,"Calle Ribadeo", "Maria","Calle Valladolid","fg5");




			bcs.guardarOrden(new OrdenTrazabilidad(0, actor_o, actor_d, true, prod[0], "primero", 0, null, null, 0, 0, actor_d, reg[0]));
			bcs.guardarOrden(new OrdenTrazabilidad(1, actor_o, actor_d, false, prod[1], "segundo", 0, null, null, 0, 0, actor_d, reg[1]));
			bcs.guardarOrden(new OrdenTrazabilidad(2, actor_o, actor_d, true, prod[2], "tercero", 0, null, null, 0, 0, actor_d, reg[2]));
			bcs.guardarOrden(new OrdenTrazabilidad(3, actor_o, actor_d, true, prod[3], "cuarto", 0, null, null, 0, 0, actor_d, reg[3]));	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	void testInsertarLote() throws Throwable  {
		try {
			
			BlockchainServices bcs = new BlockchainServices();
			Actor actor_o;
			Actor actor_d;
			Actor actor_t;
			Productos p;
			Lote lote;

			actor_t = new Actor("7","Transportista","password","transp@gmail.es", 2,"Calle Ribadeo", "Luis","Calle Valladolid","fg3");
			lote = new Lote(1001, new java.util.Date(), "uno", new java.util.Date(), true, false, false, false, false, null, 0);
			Registro[] reg = {
					new Registro(100,lote, actor_t, "", "", 10, 5),
					new Registro(101,lote, actor_t, "", "", 11, 6),
					new Registro(102,lote, actor_t, "", "", 12, 7),
					new Registro(103,lote, actor_t, "", "", 13, 8),
			};



			bcs.guardarOrden(lote);
			bcs.guardarOrden(reg[0]);
			bcs.guardarOrden(reg[1]);
			bcs.guardarOrden(reg[2]);
			bcs.guardarOrden(reg[3]);
				
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
