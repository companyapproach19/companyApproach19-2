package equipo5.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import equipo5.model.NotInDatabaseException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

import equipo4.model.Lote;
import equipo4.model.MateriaPrima;
import equipo5.model.Cadena;
import equipo6.model.Bloque;
import equipo6.model.CadenaActores;
import equipo6.model.DatosContainer;
import equipo6.model.Actor;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo8.model.Registro;

public class metodosCompany {

	private static Connection conn;

	private static String JDBC_DATABASE_URL="jdbc:postgresql://ec2-54-197-232-203.compute-1.amazonaws.com:5432/da8thb0c81jj6n?user=voamftsogizhrl&password=b92c40a06c23bf20ef80f4270ebf62bd464e9432d65e38458e047b7597bd5446&sslmode=require";

	static boolean primerusuario= true;


	public static void conectar(){
		try {
			 conn= DriverManager.getConnection(JDBC_DATABASE_URL);
			//System.out.println("Conectado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void crearBD()   throws Exception {
		PreparedStatement pst = conn.prepareStatement("DROP SCHEMA company CASCADE;");
		pst.executeUpdate();
		PreparedStatement pst100 = conn.prepareStatement("CREATE SCHEMA company;");
		pst100.executeUpdate();

		//creacion de las tablas
		
		PreparedStatement pst16 = conn.prepareStatement(
	            "CREATE TABLE company.materiaPrima (" + 			//tabla materia prima
	            		"idMateriaPrima INT NOT NULL ," +
	                    "nombre VARCHAR(45) UNIQUE NOT NULL ," +
	                    "PRIMARY KEY (idMateriaPrima));"
	    );
	    pst16.executeUpdate();
		
	    PreparedStatement pst3 = conn.prepareStatement(
				"CREATE TABLE company.lote (" +
						"idLote INT NOT NULL ," +
						"fecha_inicio TIMESTAMP NOT NULL ," +
						"fecha_final TIMESTAMP NOT NULL, " +
						"molido BOOLEAN NOT NULL, " +
						"cocido BOOLEAN NOT NULL, " +
						"fermentado BOOLEAN NOT NULL, " +
						"fermentado2 BOOLEAN NOT NULL, " +
						"embotellado BOOLEAN NOT NULL, " +
						"qr BYTEA, " +
						"cantidad INT NOT NULL ," +
						"tipo VARCHAR(45) NOT NULL ," +
						"PRIMARY KEY (idLote));"
				);
		pst3.executeUpdate();
		
		PreparedStatement pst10 = conn.prepareStatement(
				"CREATE TABLE company.actor (" +
						"cif VARCHAR(45) NOT NULL," +
						"nombreUsuario VARCHAR(45) NOT NULL ," +
						"passwdPlana VARCHAR(45)  NOT NULL, " +
						"email VARCHAR(250)  NOT NULL, " +
						"tipoActor INT  NOT NULL, " +
						"localizacion VARCHAR(45), " +
						"nombre VARCHAR(45) NOT NULL, " +
						"direccion VARCHAR(250) NOT NULL ," +
						"cifCooperativa VARCHAR(45), " +
						"PRIMARY KEY (cif))"
				);
		pst10.executeUpdate();
		
		PreparedStatement pst11 = conn.prepareStatement(
				"CREATE TABLE company.cadena ( " +
						"codLote INT NOT NULL, " +
						"hashInicio VARCHAR(45) NULL, " +
						"numBloques INT NOT NULL, " +
						"PRIMARY KEY (codLote))"
				);
		pst11.executeUpdate();
		
		PreparedStatement pst14 = conn.prepareStatement(
				" CREATE TABLE company.registro (" +
						"id INT NOT NULL, " +
						"idLote INT NOT NULL, " +
						"idActor VARCHAR(45) NOT NULL, " +
						"fechaInicio VARCHAR(45), " +
						"fechaFin VARCHAR(45), " +
						"tempMax VARCHAR(45), " +
						"tempMin VARCHAR(45), " +
						"PRIMARY KEY (id)," +
						"CONSTRAINT fk_registro_1 " +
						"  FOREIGN KEY (idLote) " +
						"  REFERENCES company.lote(idLote) " +
						"  ON DELETE NO ACTION " +
						"  ON UPDATE NO ACTION, " +
						"CONSTRAINT fk_registro_2 " +
						"  FOREIGN KEY (idActor) " +
						"  REFERENCES company.actor(cif) " +
						"  ON DELETE NO ACTION " +
						"  ON UPDATE NO ACTION " +
						");"
				);
		pst14.executeUpdate();
		
		PreparedStatement pst12 = conn.prepareStatement(
				"CREATE TABLE company.productos ( " +
						"id INT NOT NULL, " +
						"malta_palida INT NOT NULL, " +
						"matla_munich INT NOT NULL, " +
						"malta_negra INT NOT NULL, " +
						"malta_crystal INT NOT NULL, " +
						"malta_chocolate INT NOT NULL, " +
						"malta_caramelo INT NOT NULL, " +
						"cebada INT NOT NULL, " +
						"cebada_tostada INT NOT NULL, " +
						"lupulo_centenial INT NOT NULL, " +
						"cajas_stout INT NOT NULL, " +
						"cajas_bisner INT NOT NULL, " +
						"PRIMARY KEY (id))"
				);
		pst12.executeUpdate();

		
		PreparedStatement pst13 = conn.prepareStatement(
				"CREATE TABLE company.ordenTrazabilidad ( " +
						"id INT NOT NULL, " +
						"idActorOrigen VARCHAR(45) NOT NULL, " +
						"idActorDestino VARCHAR(45) NOT NULL, " +
						"necesitaTransportista BOOLEAN NOT NULL, " +
						"idProductos INT NOT NULL, " +
						"mensaje VARCHAR(45), " +
						"estado INT NOT NULL, " +
						"firmaRecogida BYTEA, " +
						"firmaEntrega BYTEA, " +
						"idPadre INT NOT NULL, " +
						"idHijo INT NOT NULL, " +
						"idTransportista VARCHAR(45), " +
						"idRegistro INT, " +
						"PRIMARY KEY (id)," +
						"CONSTRAINT fk_orden_1 " +
						"  FOREIGN KEY (idActorOrigen) " +
						"  REFERENCES company.actor(cif) " +
						"  ON DELETE NO ACTION " +
						"  ON UPDATE NO ACTION, " +
						"CONSTRAINT fk_orden_2 " +
						"  FOREIGN KEY (idActorDestino) " +
						"  REFERENCES company.actor(cif) " +
						"  ON DELETE NO ACTION " +
						"  ON UPDATE NO ACTION, " +
						"CONSTRAINT fk_orden_3 " +
						"  FOREIGN KEY (idProductos) " +
						"  REFERENCES company.productos(id) " +
						"  ON DELETE NO ACTION " +
						"  ON UPDATE NO ACTION, " +
						"CONSTRAINT fk_orden_4 " +
						"  FOREIGN KEY (idTransportista) " +
						"  REFERENCES company.actor(cif) " +
						"  ON DELETE NO ACTION " +
						"  ON UPDATE NO ACTION, " +
						"CONSTRAINT fk_orden_5 " +
						"  FOREIGN KEY (idRegistro) " +
						"  REFERENCES company.registro(id) " +
						"  ON DELETE NO ACTION " +
						"  ON UPDATE NO ACTION); " 
						
				);
		pst13.executeUpdate();


		PreparedStatement pst15 = conn.prepareStatement(
				" CREATE TABLE company.bloque (" +
						"hashBloque VARCHAR(45) NOT NULL, " +
						"hashPrevio VARCHAR(45), " +
						"tipoBloque INT, " +
						"numBloque INT, " +
						"codLote INT, " +
						"datosContainer INT, " +
						"timeStamp FLOAT, " +
						"idCadena INT NOT NULL, " +
						"PRIMARY KEY (hashBloque));"
				);
		pst15.executeUpdate();
		

		PreparedStatement pst4 = conn.prepareStatement(
				"CREATE TABLE company.stockFabricaLotes (" +
						"idStockFabricaLotes INT GENERATED BY DEFAULT AS IDENTITY," +
						"fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
						"idLote INT NOT NULL," +
	                    "cantidad INT NOT NULL ," +
						"PRIMARY KEY (idStockFabricaLotes)," +
						"CONSTRAINT fk_stock_1 " +
						"  FOREIGN KEY (idLote) " +
						"  REFERENCES company.lote(idLote) " +
						"  ON DELETE NO ACTION " +
						"  ON UPDATE NO ACTION); "
				);
		pst4.executeUpdate();
	
		
		PreparedStatement pst17 = conn.prepareStatement(
	            "CREATE TABLE company.stockCooperativa (" +
						"idStockCooperativa INT GENERATED BY DEFAULT AS IDENTITY," +
	            		"idCooperativa VARCHAR(45) NOT NULL," +
	                    "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP ," +
	                    "idMateriaPrima INT NOT NULL ," +
	                    "cantidad INT NOT NULL ," +
	                    "PRIMARY KEY (idStockCooperativa)," +
	                    "CONSTRAINT fk_stockCooperativa_1 " +
	                    "  FOREIGN KEY (idCooperativa) " +
	                    "  REFERENCES company.actor(cif) " +
	                    "  ON DELETE NO ACTION " +
	                    "  ON UPDATE NO ACTION, " +
	                    "CONSTRAINT fk_stockCooperativa_2 " +
	                    "  FOREIGN KEY (idMateriaPrima) " +
	                    "  REFERENCES company.materiaPrima(idMateriaPrima) " +	//con que tabla se relaciona? también ponerlo en StockAgricultor
	                    "  ON DELETE NO ACTION " +
	                    "  ON UPDATE NO ACTION " +
	                    ");"
	                    
	    );
	    pst17.executeUpdate();
	    
	    PreparedStatement pst18 = conn.prepareStatement(
	            "CREATE TABLE company.stockRetailer (" +
						"idStockRetailer INT GENERATED BY DEFAULT AS IDENTITY," +
	    				"idRetailer VARCHAR(45) NOT NULL," +
	                    "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP ," +
	                    "idLote INT NOT NULL ," +
	                    "cantidad INT NOT NULL ," +
	                    "PRIMARY KEY (idStockRetailer)," +
	                    "CONSTRAINT fk_stockRetailer_1" +
	                    "  FOREIGN KEY (idRetailer) " +
	                    "  REFERENCES company.actor(cif) " +
	                    "  ON DELETE NO ACTION " +
	                    "  ON UPDATE NO ACTION, " +
	                    "CONSTRAINT fk_stockRetailer_2" +
	                    "  FOREIGN KEY (idLote) " +
	                    "  REFERENCES company.lote(idLote) " +
	                    "  ON DELETE NO ACTION " +
	                    "  ON UPDATE NO ACTION " +
	                    ");"
	    );
	    pst18.executeUpdate();
	    
	    PreparedStatement pst19 = conn.prepareStatement(
	            "CREATE TABLE company.stockAgricultor (" +
						"idStockAgricultor INT GENERATED BY DEFAULT AS IDENTITY," +
	            		"idAgricultor VARCHAR(45) NOT NULL," +
	                    "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP ," +
	                    "idMateriaPrima INT NOT NULL ," + 
	                    "cantidad INT NOT NULL ," +
	                    "PRIMARY KEY (idStockAgricultor)," + 
	                    "CONSTRAINT fk_stockAgricultor_1 " +
	                    "  FOREIGN KEY (idAgricultor) " +
	                    "  REFERENCES company.actor(cif) " +
	                    "  ON DELETE NO ACTION " +
	                    "  ON UPDATE NO ACTION, " +
	                    "CONSTRAINT fk_stockAgricultor_2 " +
	                    "  FOREIGN KEY (idMateriaPrima) " +
	                    "  REFERENCES company.materiaPrima(idMateriaPrima) " +//tabla??
	                    "  ON DELETE NO ACTION " +
	                    "  ON UPDATE NO ACTION " +
	                    ");"
	    );
	    pst19.executeUpdate();
	    
	    PreparedStatement pst20 = conn.prepareStatement(
	            "CREATE TABLE company.stockFabricaMMPP (" +
	            		"idStockMMPP INT GENERATED BY DEFAULT AS IDENTITY," +
	                    "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP ," +
	                    "idMateriaPrima INT NOT NULL ," + 
	                    "cantidad INT NOT NULL ," +
	                    "PRIMARY KEY (idStockMMPP)," + 
	                    "CONSTRAINT fk_stockMMPP_2 " +
	                    "  FOREIGN KEY (idMateriaPrima) " +
	                    "  REFERENCES company.materiaPrima(idMateriaPrima) " +
	                    "  ON DELETE NO ACTION " +
	                    "  ON UPDATE NO ACTION " +
	                    ");"
	    );
	    pst20.executeUpdate();

		System.out.println("�Base de datos Creada!");

	}

	public static OrdenTrazabilidad extraerOrdenTrazabilidad(int id) throws SQLException, ClassNotFoundException {
		conectar();
		String query = "SELECT * FROM company.ordenTrazabilidad WHERE id = " + id;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			Actor actor = extraerActor(rs.getString(2));
			Actor actor1 = extraerActor(rs.getString(3));
			Productos productos = extraerProductos(rs.getInt(5));
			Actor actor2 = extraerActor(rs.getString(12));
			Registro registro = extraerRegistro(rs.getInt(13));
			OrdenTrazabilidad buscado = new OrdenTrazabilidad(rs.getInt(1), actor, actor1, rs.getBoolean(4), productos,
					rs.getString(6), rs.getInt(7), rs.getBytes(8), rs.getBytes(9), rs.getInt(10), rs.getInt(11),actor2, registro);
			pst.close();
			rs.close();
			conn.close();
			return buscado;
		}
		conn.close();
		return null;	
	}

	public static void insertarOrdenTrazabilidad(OrdenTrazabilidad orden) throws SQLException, ClassNotFoundException {
		conectar();
		String query = "INSERT INTO company.ordenTrazabilidad (id, idActorOrigen, idActorDestino, necesitaTransportista, idProductos, mensaje, estado, firmaRecogida, firmaEntrega, idPadre, idHijo, idTransportista, idRegistro)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		pst.setInt(1, orden.getId());
		pst.setString(2, orden.getActorOrigen().getId());
		pst.setString(3, orden.getActorDestino().getId());
		pst.setBoolean(4, orden.getNecesitaTransportista());
		if(extraerProductos(orden.getProductos().getId())==null) insertarProductos(orden.getProductos());
		pst.setInt(5, orden.getProductos().getId());
		pst.setString(6, orden.getMensaje());
		pst.setInt(7, orden.getEstado());
		pst.setBytes(8,orden.getFirmaRecogida());
		pst.setBytes(9,orden.getFirmaEntrega());
		pst.setInt(10, orden.getIdPadre());
		pst.setInt(11, orden.getIdHijo());
		pst.setString(12, orden.getTransportista().getId());
		if(extraerRegistro(orden.getRegistro().getId())==null) insertarRegistro(orden.getRegistro());
		pst.setInt(13, orden.getRegistro().getId());
		pst.executeUpdate();
		pst.close();
		conn.close();
	}

	public static Productos extraerProductos(int id) throws SQLException {
		conectar();
		String query = "SELECT * FROM company.productos WHERE id =" + id;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			Productos buscado = new Productos(id, rs.getInt(2),  rs.getInt(3), rs.getInt(4),rs.getInt(5),
					rs.getInt(6),rs.getInt(7),rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11),rs.getInt(12));
			pst.close();
			rs.close();
			conn.close();
			return buscado;
		}
		return null;	
	}

	public static void insertarProductos(Productos producto) throws SQLException, ClassNotFoundException {
		conectar();
		String query = "INSERT INTO company.productos (id, malta_palida, matla_munich, malta_negra, malta_crystal, "
				+ "malta_chocolate , malta_caramelo, cebada, cebada_tostada, lupulo_centenial, cajas_stout ,cajas_bisner)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		pst.setInt(1, producto.getId());
		pst.setInt(2, producto.getCant_malta_palida());
		pst.setInt(3, producto.getCant_malta_munich());
		pst.setInt(4, producto.getCant_malta_negra());
		pst.setInt(5, producto.getCant_malta_crystal());
		pst.setInt(6, producto.getCant_malta_chocolate());
		pst.setInt(7, producto.getCant_malta_caramelo());
		pst.setInt(8, producto.getCant_cebada());
		pst.setInt(9, producto.getCant_cebada_tostada());
		pst.setInt(10, producto.getCant_lupulo_centenial());
		pst.setInt(11, producto.getCant_cajas_stout());
		pst.setInt(12, producto.getCant_cajas_bisner());
		pst.executeUpdate();
		pst.close();
		conn.close();
	}

	public static Cadena extraerCadena(int codLote) throws SQLException {
		conectar();
		String query = "SELECT * FROM company.cadena WHERE cadena.codLote = "+codLote;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		Cadena buscado = null;
		while(rs.next()) {
			buscado = new Cadena(codLote, rs.getString(2), rs.getInt(3));
		}
		pst.close();
		rs.close();
		conn.close();
		return buscado;
	}

	public static void insertarCadena(Cadena cadena) throws SQLException {
		int codLote = cadena.getCodLote();
		if(extraerCadena(codLote)!= null) {
			conectar();
			String query = "UPDATE company.cadena SET hashInicio = ? , numBloques = ?  WHERE codLote= ?";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			pst.setString(1, cadena.getHashUltimoBloque());
			pst.setInt(2, cadena.getNumBloques());
			pst.setInt(3, codLote);
			pst.executeUpdate();
			pst.close();
			conn.close();
		}else {
			conectar();
			String query = "INSERT INTO company.cadena (codLote, hashInicio, numBloques) VALUES (?, ?, ?);";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			pst.setInt(1, codLote);
			pst.setString(2, cadena.getHashUltimoBloque());
			pst.setInt(3, cadena.getNumBloques());
			pst.executeUpdate();
			pst.close();
			conn.close();
		}
	}

	public static void insertarLote(Lote lote) throws Throwable {
		conectar();
			String query = "INSERT INTO company.lote (idLote, fecha_inicio, fecha_final, molido, cocido, fermentado, fermentado2, embotellado, qr, cantidad, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?);";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			pst.setInt(1, lote.getIdBd());
			pst.setDate(2, (Date) lote.getFecha_inicio());
			pst.setDate(3, (Date)lote.getFecha_final());
			pst.setBoolean(4, lote.isMolido());
			pst.setBoolean(5, lote.isCocido());
			pst.setBoolean(6, lote.isFermentado());
			pst.setBoolean(7, lote.isFermentado2());
			pst.setBoolean(8, lote.isEmbotellado());
			pst.setBytes(9, lote.getQr());
			pst.setInt(10, lote.getCantidad());
			pst.setString(11, lote.getTipo());
			pst.executeUpdate();
			pst.close();
		conn.close();
	}

	public static byte [] getQR(int idLote) throws SQLException {
		conectar();
		String query = "SELECT * FROM company.lote WHERE idLote =" + idLote;
		Statement pst2 = conn.createStatement();
		ResultSet rs2 = pst2.executeQuery(query);
		while(rs2.next()) {
			byte [] binario = rs2.getBytes(9);
			pst2.close();
			rs2.close();
			conn.close();
			return binario;

		}
		return null;
	}

	public static Lote extraerLote(int idLote) throws SQLException {
		conectar();
		String query = "SELECT * FROM company.lote WHERE idLote = " + idLote;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			Lote buscado = new Lote(rs.getInt(1),  rs.getDate(2), rs.getString(11), rs.getDate(3), rs.getBoolean(4), rs.getBoolean(5), rs.getBoolean(6), rs.getBoolean(7), rs.getBoolean(8), rs.getBytes(9), rs.getInt(10));
			pst.close();
			rs.close();
			conn.close();
			return buscado;
		}
		conn.close();
		return null;
	}

	public static void insertarRegistro (Registro registro) throws SQLException{
		conectar();
		String query = "INSERT INTO company.registro (id, idLote, idActor, fechaInicio, fechaFin, tempMax, tempMin) VALUES (?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		pst.setInt(1, registro.getId());
		pst.setInt(2, registro.getLote().getIdBd());
		pst.setString(3, registro.getActor().getId());
		pst.setString(4, registro.getFechaInicio());
		pst.setString(5, registro.getFechaFin());
		pst.setInt(6, registro.getTempMax());
		pst.setInt(7, registro.getTempMin());
		pst.executeUpdate();
		pst.close();
		conn.close();
	}

	public static Registro extraerRegistro (int id) throws SQLException, ClassNotFoundException {
		conectar();
		String query = "SELECT * FROM company.registro WHERE registro.id = " +  id;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()){
			Lote lote = extraerLote(rs.getInt(2));
			Actor actor = extraerActor(rs.getString(3));
			Registro buscado = new Registro(rs.getInt(1), lote , actor, rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
			pst.close();
			rs.close();
			conn.close();
			return buscado;
		}
		conn.close();
		return null;
	}

	public static CadenaActores extraerCadenaActores() throws SQLException {
		conectar();
		CadenaActores cadena = new CadenaActores();
		String query = "SELECT * FROM company.actor";
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			Actor buscado = new Actor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
			cadena.addActor(buscado);
		}
		conn.close();
		return cadena;

	}

	public static Actor extraerActor(String nombreUsuario) throws SQLException, ClassNotFoundException {
		if(nombreUsuario!= null) {
			conectar();
			String query = "SELECT * FROM company.actor WHERE actor.nombreUsuario = '" + nombreUsuario + "'";
			Statement pst = conn.createStatement();
			ResultSet rs = pst.executeQuery(query);
			while(rs.next()) {
				Actor buscado = new Actor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
				pst.close();
				rs.close();
				return buscado; 
			}
			conn.close();
			return null;
		}
		else return null;
	}		 

	public static void insertarActor(Actor actor) throws SQLException, ClassNotFoundException, RuntimeException{
		conectar();
		String query = "INSERT INTO company.actor (cif, nombreUsuario, passwdPlana, email, tipoActor, localizacion, nombre, direccion, cifCooperativa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		pst.setString(1, actor.getId());
		pst.setString(2, actor.getNombreUsuario());
		pst.setString(3, actor.getpasswordPlana());
		pst.setString(4, actor.getEmail());
		pst.setInt(5, actor.getTipoActor());
		pst.setString(6, actor.getLocalizacion());
		pst.setString(7, actor.getNombre());
		pst.setString(8, actor.getDireccion());
		pst.setString(9, actor.getCifcooperativa());
		pst.executeUpdate();
		pst.close();
		conn.close();
	}

	public static Bloque extraerBloque(String hashBloquePedido) throws SQLException, ClassNotFoundException, RuntimeException {
		hashBloquePedido="Hash";
		conectar();
		Bloque devolver =null;
		String query = "SELECT * FROM company.bloque WHERE bloque.hashBloque = " + hashBloquePedido +";" ;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			int tipoBloque = rs.getInt(3);
			String hashPrevio = rs.getString(2);
			int numBloque = rs.getInt(4);
			int codLote = rs.getInt(5);
			int idCadena = rs.getInt(8);
			switch (tipoBloque) {
			case 0:
				DatosContainer datos = new DatosContainer (extraerOrdenTrazabilidad(rs.getInt(6)));
				Bloque buscado = new Bloque(hashPrevio, tipoBloque, numBloque, codLote, datos, idCadena);
				devolver = buscado;
				break;
			case 1:
				DatosContainer datos2 = new DatosContainer (extraerRegistro(rs.getInt(6)));
				Bloque buscado1 = new Bloque(hashPrevio, tipoBloque, numBloque, codLote, datos2, idCadena);
				devolver = buscado1;
				break;
			case 2:
				DatosContainer datos3 = new DatosContainer (extraerLote(rs.getInt(6)));
				Bloque buscado2 = new Bloque(hashPrevio, tipoBloque, numBloque, codLote, datos3, idCadena);
				devolver = buscado2;
				break;
			}

			pst.close();
			rs.close();
			conn.close();
			return devolver;
		}
		conn.close();
		return null;
	}

	public static void insertarBloque(Bloque bloqAinsertar) throws Throwable {
		int data=0;
		switch (bloqAinsertar.getTipoBloque()) {
		case 0:
			OrdenTrazabilidad aInsertar = (OrdenTrazabilidad) bloqAinsertar.getDatos();
			data = aInsertar.getId();
			if(extraerOrdenTrazabilidad(data)==null) {
				insertarOrdenTrazabilidad(aInsertar);
			}
			insertarOrdenTrazabilidad(aInsertar);
			break;
		case 1: //Registro
			Registro aInsertar2 = (Registro) bloqAinsertar.getDatos();
			data = aInsertar2.getId();
			if(extraerRegistro(data)==null) {
				insertarRegistro(aInsertar2);
			}
			break;
		case 2: //Lote
			Lote aInsertar3 = (Lote) bloqAinsertar.getDatos();
			data = aInsertar3.getIdBd();
			if(extraerLote(data)==null) {
				insertarLote(aInsertar3);
			}			
			break;
		}
		conectar();
		String query = "INSERT INTO company.bloque (hashBloque, hashPrevio, tipoBloque, numBloque, codLote, datosContainer, timeStamp, idCadena) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		pst.setString(1, bloqAinsertar.getHashCode());
		pst.setString(2, bloqAinsertar.getHashPrevio());
		pst.setInt(3,bloqAinsertar.getTipoBloque());
		pst.setInt(4,bloqAinsertar.getNumBloque());
		pst.setInt(5,bloqAinsertar.getCodLote());
		pst.setInt(6, data);
		pst.setFloat(7, bloqAinsertar.getTimeStamp());
		pst.setInt(8, bloqAinsertar.getIdCadena());
		pst.executeUpdate();
		pst.close();
		conn.close();
	}
	
	public static ArrayList<OrdenTrazabilidad> extraerPedidosActorDestino(String idActor) throws SQLException, ClassNotFoundException{
		conectar();
		ArrayList<OrdenTrazabilidad> lista = new ArrayList<OrdenTrazabilidad>();
//		String query = "SELECT * FROM company.ordenTrazabilidad WHERE ordenTrazabilidad.idActorDestino = " + idActor;
//		Statement pst = conn.createStatement();
//		ResultSet rs = pst.executeQuery(query);
//		while(rs.next()) {
//			Actor actor = extraerActor(rs.getString(2));
//			Actor actor1 = extraerActor(rs.getString(3));
//			Productos productos = extraerProductos(rs.getInt(5));
//			Actor actor2 = extraerActor(rs.getString(12));
//			Registro registro = extraerRegistro(rs.getInt(13));
//			OrdenTrazabilidad buscado = new OrdenTrazabilidad(rs.getInt(1), actor, actor1, rs.getBoolean(4), productos,
//					rs.getString(6), rs.getInt(7), rs.getBytes(8), rs.getBytes(9), rs.getInt(10), rs.getInt(11), actor2, registro);
//			lista.add(buscado);
//		}		
//		pst.close();
//		rs.close();
//		conn.close();
		return lista;	
	}
	public static LinkedList<Lote> extraerStockLote(Actor actor) throws SQLException, ClassNotFoundException, NotInDatabaseException {
		conectar();
		LinkedList<Lote> buscado = new LinkedList<Lote>();
		switch(actor.getTipoActor()) {
		case 4:
			String query = "SELECT * FROM company.stockRetailer WHERE idRetailer = " + actor.getId();
			Statement pst = conn.createStatement();
			ResultSet rs = pst.executeQuery(query);
			while(rs.next()){
				buscado.add(extraerLote(rs.getInt(4)));
			}
			pst.close();
			rs.close();
			conn.close();
			break;
		case 3:
			String query2 = "SELECT * FROM company.stockFabricaLotes"; 
			Statement pst2 = conn.createStatement();
			ResultSet rs2 = pst2.executeQuery(query2);
			while(rs2.next()){
				buscado.add(extraerLote(rs2.getInt(4)));
			}
			pst2.close();
			rs2.close();
			conn.close();
			break;
		default:  
			System.out.println("el actor suministrado no almacena lotes");
		}
		return buscado;
	}
	
    
	public static int extraerStockMP(Actor actor, MateriaPrima mp) throws SQLException, ClassNotFoundException, NotInDatabaseException {
		int buscado;
		switch(actor.getTipoActor()){
		case 0:
			conectar();
			String query2 = "SELECT * FROM company.stockAgricultor WHERE idAgricultor = '"+actor.getId()+"' AND idMateriaPrima = '"+mp.getId()+"'";
			Statement pst2 = conn.createStatement();
			ResultSet rs2 = pst2.executeQuery(query2);
			if(!rs2.next()){
				throw new NotInDatabaseException("El stock del agricultor buscado no se encuentra en la base de datos.");
			}
			buscado = rs2.getInt(5);
			pst2.close();
			rs2.close();
			conn.close();
			break;
		case 1:
			conectar();

			String query = "SELECT * FROM company.stockCooperativa WHERE idCooperativa =  '"+actor.getId()+"' AND idMateriaPrima = '"+mp.getId()+"' ";
			Statement pst = conn.createStatement();
			ResultSet rs = pst.executeQuery(query);
			if(!rs.next()){
				throw new NotInDatabaseException("El stock de la cooperativa buscado no se encuentra en la base de datos.");
			}
			buscado = rs.getInt(5);
			pst.close();
			rs.close();
			conn.close();
			break;
		case 3:
			conectar();

			String query3 = "SELECT * FROM company.stockFabricaMMPP WHERE idMateriaPrima" + mp.getId();
			Statement pst3 = conn.createStatement();
			ResultSet rs3 = pst3.executeQuery(query3);
			if(!rs3.next()){
				throw new NotInDatabaseException("El stock de fabrica buscado no se encuentra en la base de datos.");
			}
			buscado = rs3.getInt(4);
			pst3.close();
			rs3.close();
			conn.close();
			break;		
		default: 
			System.out.println("el actor suministrado no almacena materias primas.");
			buscado=0;
		}
		return buscado;
	}
    
	public static void insertarStockLote(Actor actor, Lote lote) throws Throwable{
	   	if(extraerLote(lote.getIdBd())==null) insertarLote(lote);
	   	switch(actor.getTipoActor()){
	   		case 4:
	   			conectar();
			    String query = "INSERT INTO company.stockRetailer (idRetailer, idLote, cantidad) VALUES ( ?, ?, ?);"; 
			    PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			    pst.setString(1, actor.getId());
			    pst.setInt(2, lote.getIdBd());
			    pst.setInt(3, lote.getCantidad());
			    pst.executeUpdate();
			    pst.close();
			    conn.close();
			    break;
	   		case 3:
	   			conectar();
			    String query2 = "INSERT INTO company.stockFabricaLotes (idLote, cantidad) VALUES (?, ?);"; 
			    PreparedStatement pst2 = (PreparedStatement) conn.prepareStatement(query2);
			    pst2.setInt(1, lote.getIdBd());
			    pst2.setInt(2, lote.getCantidad());
			    pst2.executeUpdate();
			    pst2.close();
			    conn.close();
			    break;
	    } 	
    }
    public static void insertarStockMP(Actor actor, MateriaPrima mp, int cantidad) throws SQLException, ClassNotFoundException{
	   	switch(actor.getTipoActor()){
	   		case 0:
	   			conectar();
			    String query = "INSERT INTO company.stockAgricultor (idAgricultor, idMateriaPrima, cantidad) VALUES ( ?, ?, ?);"; 
			    PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			    pst.setString(1, actor.getId());
			    pst.setInt(2, mp.getId());
			    pst.setInt(3, cantidad);
			    pst.executeUpdate();
			    pst.close();
			    conn.close();
			    break;
	   		case 1:
	   			conectar();
			    String query2 = "INSERT INTO company.stockCooperativa (idCooperativa, idMateriaPrima, cantidad) VALUES (?, ?, ?);"; 
			    PreparedStatement pst2 = (PreparedStatement) conn.prepareStatement(query2);
			    pst2.setString(1, actor.getId());
			    pst2.setInt(2, mp.getId());
			    pst2.setInt(3, cantidad);
			    pst2.executeUpdate();
			    pst2.close();
			    conn.close();
			    break;
	   		case 3:
	   			conectar();
			    String query3 = "INSERT INTO company.stockFabricaMMPP (idMateriaPrima, cantidad) VALUES (?, ?);"; 
			    PreparedStatement pst3 = (PreparedStatement) conn.prepareStatement(query3);
			    pst3.setInt(1, mp.getId());
			    pst3.setInt(2, cantidad);
			    pst3.executeUpdate();
			    pst3.close();
			    conn.close();
			    break;
	    } 	
    }    

    
	public static int idOrdenTrazabilidad() throws SQLException, ClassNotFoundException{
        conectar();
        String query = "SELECT MAX (id) FROM company.ordenTrazabilidad";
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        int siguienteId = 1;
        if(rs.next()){
            siguienteId = rs.getInt(1) + 1;
        }
        pst.close();
       rs.close();
       conn.close();
        return siguienteId;
    }
    
    public static int idProductos() throws SQLException, ClassNotFoundException{
        conectar();
        String query = "SELECT MAX (id) FROM company.productos";
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        int siguienteId = 1;
        if(rs.next()){
            siguienteId = rs.getInt(1) + 1;
        }
        pst.close();
       rs.close();
       conn.close();
        return siguienteId;
    }
    
    public static int idLote() throws SQLException, ClassNotFoundException{
        conectar();
        String query = "SELECT MAX (idLote) FROM company.lote";
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        int siguienteId = 1;
        if(rs.next()){
            siguienteId = rs.getInt(1) + 1;
        }
        pst.close();
       rs.close();
       conn.close();
        return siguienteId;
    }
    
    public static int idRegistro() throws SQLException, ClassNotFoundException{
        conectar();
        String query = "SELECT MAX (idLote) FROM company.registro";
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        int siguienteId = 1;
        if(rs.next()){
            siguienteId = rs.getInt(1) + 1;
        }
        pst.close();
       rs.close();
       conn.close();
        return siguienteId;
    }
    
    public static int idMateriaPrima() throws SQLException, ClassNotFoundException{
        conectar();
        String query = "SELECT MAX (idstockFabricaMateriasPrimas) FROM company.stockFabricaMateriasPrimas";
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        int siguienteId = 1;
        if(rs.next()){
            siguienteId = rs.getInt(1) + 1;
        }
        pst.close();
       rs.close();
       conn.close();
        return siguienteId;
    }
    public static MateriaPrima extraerMateriaPrima(String nombre) throws SQLException {
    	conectar();
		String query = "SELECT * FROM company.materiaPrima WHERE nombre = " + nombre;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			MateriaPrima buscado = new MateriaPrima(nombre, rs.getInt(1));
			pst.close();
			rs.close();
			conn.close();
			return buscado;
		}
		return null;
    }
    public static void insertarMateriaPrima(MateriaPrima mp) throws SQLException, ClassNotFoundException, RuntimeException{
		conectar();
		String query = "INSERT INTO company.materiaPrima (idMateriaPrima, nombre) VALUES (?, ?);";
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		pst.setInt(1, mp.getId());
		pst.setString(2, mp.getNombre());
		pst.executeUpdate();
		pst.close();
	}
    

}
