package equipo5.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import equipo5.dao.NullException;
import equipo5.model.StockLote;
import equipo5.model.StockMP;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

import equipo4.model.Lote;
import equipo4.model.MateriaPrima;
import equipo5.model.Cadena;
import equipo5.model.NotInDatabaseException;
import equipo6.model.Bloque;
import equipo6.model.CadenaActores;
import equipo6.model.DatosContainer;
import equipo6.model.geolocalizacion;
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
			if(conn == null || conn.isClosed()) {
			 conn= DriverManager.getConnection(JDBC_DATABASE_URL);
			System.out.println("Conectado");
			}
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
	                    "tipo VARCHAR(45) NOT NULL ," +
	            		"cantidad FLOAT NOT NULL ," +
	                    "PRIMARY KEY (idMateriaPrima)"+
						");"

	    );
	    pst16.executeUpdate();
		
	    PreparedStatement pst3 = conn.prepareStatement(
				"CREATE TABLE company.lote (" +
						"idLote INT NOT NULL ," +
						"fecha_inicio TIMESTAMP NOT NULL ," +
						"fecha_final TIMESTAMP, " +
						"molido BOOLEAN NOT NULL, " +
						"cocido BOOLEAN NOT NULL, " +
						"fermentado BOOLEAN NOT NULL, " +
						"fermentado2 BOOLEAN NOT NULL, " +
						"embotellado BOOLEAN NOT NULL, " +
						"qr BYTEA, " +
						"fecha_molido TIMESTAMP, " +
						"fecha_cocido TIMESTAMP, " +
						"fecha_fermentado TIMESTAMP, " +
						"fecha_fermentado2 TIMESTAMP, " +
						"fecha_embotellado TIMESTAMP, " +
						"tipo VARCHAR(45) NOT NULL ," +
						"PRIMARY KEY (idLote)"+
						");"

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
						"PRIMARY KEY (cif)"+
						");"

				);
		pst10.executeUpdate();
		
		PreparedStatement pst11 = conn.prepareStatement(
				"CREATE TABLE company.cadena ( " +
						"idCadena INT NOT NULL, " +
						"hashInicio VARCHAR(45) NULL, " +
						"numBloques INT NOT NULL, " +
						"PRIMARY KEY (idCadena)"+
						");"

				);
		pst11.executeUpdate();
		
		PreparedStatement pst14 = conn.prepareStatement(
				" CREATE TABLE company.registro (" +
						"id INT NOT NULL, " +
						"idOrden INT NOT NULL, " +
						"idCadena INT NOT NULL, " +
						"fechaInicio VARCHAR(45), " +
						"fechaFin VARCHAR(45), " +
						"tempMax VARCHAR(45), " +
						"tempMin VARCHAR(45), " +
						"PRIMARY KEY (id)" +
//						"FOREIGN KEY (idOrden) "+
//						"REFERENCES ordenTrazabilidad (id),"+
//						"FOREIGN KEY (idCadena) "+
//						"REFERENCES company.cadena (idCadena)"+
						");"
				);
		pst14.executeUpdate();
		
		PreparedStatement pst12 = conn.prepareStatement(
				"CREATE TABLE company.productos ( " +
						"id INT NOT NULL, " +
						"malta_palida INT NOT NULL, " +
						"malta_munich INT NOT NULL, " +
						"malta_negra INT NOT NULL, " +
						"malta_crystal INT NOT NULL, " +
						"malta_chocolate INT NOT NULL, " +
						"malta_caramelo INT NOT NULL, " +
						"cebada INT NOT NULL, " +
						"cebada_tostada INT NOT NULL, " +
						"lupulo_centenial INT NOT NULL, " +
						"cajas_stout INT NOT NULL, " +
						"cajas_bisner INT NOT NULL, " +
						"PRIMARY KEY (id)"+
						");"

				);
		pst12.executeUpdate();

		
		PreparedStatement pst13 = conn.prepareStatement(
				"CREATE TABLE company.ordenTrazabilidad ( " +
						"id INT NOT NULL, " +
						"idActorOrigen VARCHAR(45) NOT NULL, " +
						"idActorDestino VARCHAR(45) NOT NULL, " +
						"necesitaTransportista BOOLEAN NOT NULL, " +
						"idProductos INT NOT NULL, " +
						"estado INT NOT NULL, " +
						"firmaRecogida BYTEA, " +
						"firmaEntrega BYTEA, " +
						"idTransportista VARCHAR(45), " +
						"idRegistro INT, " +
						"idCadena INT NOT NULL, " +
						"fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
						"PRIMARY KEY (id, fecha)" +
//						"FOREIGN KEY (idActorOrigen) "+
//						"REFERENCES company.actor (cif),"+
//						"FOREIGN KEY (idActorDestino) "+
//						"REFERENCES company.actor (cif),"+
//						"FOREIGN KEY (idTransportista) "+
//						"REFERENCES company.actor (cif),"+
//						"FOREIGN KEY (idProductos) "+
//						"REFERENCES company.productos (id),"+
//						"FOREIGN KEY (idRegistro) "+
//						"REFERENCES company.registro (id),"+
//						"FOREIGN KEY (idCadena) "+
//						"REFERENCES company.cadena (idCadena)"+
						");"
						
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
                        "estadoOrden INT NOT NULL, "+
						"PRIMARY KEY (hashBloque)" +
//						"FOREIGN KEY (idCadena) "+
//						"REFERENCES company.cadena (idCadena)"+
						");"

				);
		pst15.executeUpdate();
		

		PreparedStatement pst4 = conn.prepareStatement(
				"CREATE TABLE company.stockFabricaLotes (" +
						"idStockFabricaLotes INT GENERATED BY DEFAULT AS IDENTITY," +
						"idLote INT NOT NULL," +
						"fecha_entrada TIMESTAMP, " +
						"fecha_salida TIMESTAMP, " +
	                    "idOrden INT NOT NULL ," +
	                    "idCadena INT NOT NULL ," +
						"PRIMARY KEY (idStockFabricaLotes) "+
//						"FOREIGN KEY (idLote) "+
//						"REFERENCES company.lote (idLote),"+
//						"FOREIGN KEY (idOrden) "+
//						"REFERENCES company.ordentrazabilidad (id),"+
//						"FOREIGN KEY (idCadena) "+
//						"REFERENCES company.cadena (idCadena)"+
						");"
				);
		pst4.executeUpdate();
	
		
		PreparedStatement pst17 = conn.prepareStatement(
	            "CREATE TABLE company.stockCooperativa (" +
						"idStockCooperativa INT GENERATED BY DEFAULT AS IDENTITY," +
						"idMateriaPrima INT NOT NULL," +
						"fecha_entrada TIMESTAMP, " +
						"fecha_salida TIMESTAMP, " +
	                    "idOrden INT NOT NULL ," +
	                    "idCadena INT NOT NULL ," +
	            		"idActor VARCHAR(45) NOT NULL," +
	                    "PRIMARY KEY (idStockCooperativa)" +
//	                    "FOREIGN KEY (idMateriaPrima) "+
//						"REFERENCES company.materiaprima (idMateriaPrima),"+
//						"FOREIGN KEY (idOrden) "+
//						"REFERENCES company.ordentrazabilidad (id),"+
//						"FOREIGN KEY (idCadena) "+
//						"REFERENCES company.cadena (idCadena),"+
//						"FOREIGN KEY (idActor) "+
//						"REFERENCES company.actor (cif)"+
	                    ");"  
	    );
	    pst17.executeUpdate();
	    
	    PreparedStatement pst18 = conn.prepareStatement(
	            "CREATE TABLE company.stockRetailer (" +
						"idStockRetailer INT GENERATED BY DEFAULT AS IDENTITY," +
						"idLote INT NOT NULL," +
						"fecha_entrada TIMESTAMP, " +
						"fecha_salida TIMESTAMP, " +
	                    "idOrden INT NOT NULL ," +
	                    "idCadena INT NOT NULL ," +
	                    "idActor VARCHAR(45) NOT NULL ," +
	                    "PRIMARY KEY (idStockRetailer) " +
//	                    "FOREIGN KEY (idLote) "+
//						"REFERENCES company.lote (idLote),"+
//						"FOREIGN KEY (idOrden) "+
//						"REFERENCES company.ordentrazabilidad (id),"+
//						"FOREIGN KEY (idCadena) "+
//						"REFERENCES company.cadena (idCadena),"+
//						"FOREIGN KEY (idActor) "+
//						"REFERENCES company.actor (cif)"+
	                    ");"
	    );
	    pst18.executeUpdate();
	    PreparedStatement pst19 = conn.prepareStatement(
	            "CREATE TABLE company.stockAgricultor (" +
						"idStockAgricultor INT GENERATED BY DEFAULT AS IDENTITY," +
						"idMateriaPrima INT NOT NULL," +
						"fecha_entrada TIMESTAMP, " +
						"fecha_salida TIMESTAMP, " +
	                    "idOrden INT NOT NULL ," +
	                    "idCadena INT NOT NULL ," +
	            		"idActor VARCHAR(45) NOT NULL," +
	                    "PRIMARY KEY (idStockAgricultor) " + 
//	                    "FOREIGN KEY (idMateriaPrima) "+
//						"REFERENCES company.materiaprima (idMateriaPrima),"+
//						"FOREIGN KEY (idOrden) "+
//						"REFERENCES company.ordentrazabilidad (id),"+
//						"FOREIGN KEY (idCadena) "+
//						"REFERENCES company.cadena (idCadena),"+
//						"FOREIGN KEY (idActor) "+
//						"REFERENCES company.actor (cif)"+
	                    ");"
	    );
	    pst19.executeUpdate();
	    
	    PreparedStatement pst20 = conn.prepareStatement(
	            "CREATE TABLE company.stockFabricaMMPP (" +
	            		"idStockMMPP INT GENERATED BY DEFAULT AS IDENTITY," +
	            		"idMateriaPrima INT NOT NULL," +
						"fecha_entrada TIMESTAMP, " +
						"fecha_salida TIMESTAMP, " +
	                    "idOrden INT NOT NULL ," +
	                    "idCadena INT NOT NULL ," +
	                    "PRIMARY KEY (idStockMMPP) " + 
//	                    "FOREIGN KEY (idMateriaPrima) "+
//						"REFERENCES company.materiaprima (idMateriaPrima),"+
//						"FOREIGN KEY (idOrden) "+
//						"REFERENCES company.ordentrazabilidad (id),"+
//						"FOREIGN KEY (idCadena) "+
//						"REFERENCES company.cadena (idCadena)"+
	                    ");"
	    );
	    pst20.executeUpdate();
	    
	    PreparedStatement pst21 = conn.prepareStatement(
	            "CREATE TABLE company.productosOrden (" +
	            		"idOrden INT NOT NULL," +
	                    "idMPoLote INT NOT NULL ," +
	                    "PRIMARY KEY (idOrden, idMPoLote)" + 
//	                    "FOREIGN KEY (idMateriaPrima) "+
//						"REFERENCES company.materiaprima (idMateriaPrima) OR lote (idLote),"+
//						"FOREIGN KEY (idOrden) "+
//						"REFERENCES company.ordentrazabilidad (id)"+
	                    ");"
	    );
	    pst21.executeUpdate();
	    
	    PreparedStatement pst22 = conn.prepareStatement(
	            "CREATE TABLE company.geolocalizacion (" +
	            		"id INT NOT NULL," +
	            		"idOrden INT NOT NULL," +
	            		"idCadena INT NOT NULL," +
	            		"coordenadas VARCHAR(45)," +
	                    "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP ," +
	                    "PRIMARY KEY (id) " + 
//	                    "FOREIGN KEY (idOrden) "+
//						"REFERENCES company.ordentrazabilidad (id),"+
//	                    "FOREIGN KEY (idCadena) "+
//						"REFERENCES company.cadena (idCadena)"+
	                    ");"
	    );
	    pst22.executeUpdate();

		System.out.println("�Base de datos Creada!");

	}
	
	public static Registro extraerUltimoRegistro(int idOrden) throws SQLException {
		conectar();
		String query = "SELECT * FROM company.registro WHERE registro.idOrden = " +  idOrden + " AND registro.fechaFin = (SELECT MAX (registro.fechaFin) FROM company.registro WHERE registro.idOrden = "+idOrden+");";
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()){
			Registro buscado = new Registro(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
			pst.close();
			rs.close();
			//conn.close();
			return buscado;
		}
		//conn.close();
		return null;
	}
	//Ver como se saca geolocalizacion
	public static geolocalizacion extraerGeolocalizacion (int id) throws SQLException{
		conectar();
		String query = "SELECT * FROM company.geolocalizacion WHERE id = " + id;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			geolocalizacion buscado = new geolocalizacion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5));
			pst.close();
			rs.close();
			//conn.close();
			return buscado;
		}
		return null;
	}
	public static ArrayList<geolocalizacion> extraerGeolocalizaciones (int idOrden) throws SQLException{
		conectar();
		String query = "SELECT * FROM company.geolocalizacion WHERE idOrden = " + idOrden;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		ArrayList<geolocalizacion> buscado = new ArrayList<geolocalizacion>();
		while(rs.next()) {
			buscado.add(new geolocalizacion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5)));
		}
		pst.close();
		rs.close();
		//conn.close();
		return buscado;
	}
	
	public static void insertarGeolocalizacion(geolocalizacion geo) throws SQLException, NullException, ClassNotFoundException {
		if(geo == null){
	             throw new NullException("La geolocalización introducida no es válida.");
		}
		conectar();
		String query = "INSERT INTO company.geolocalizacion (id, idOrden, idCadena, coordenadas) VALUES (?, ?, ?, ?);";
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		pst.setInt(1, geo.getId());
		pst.setInt(2, geo.getIdOrden());
		if(extraerCadena(geo.getIdPedido())==null) insertarCadena(new Cadena(geo.getIdPedido()));
		pst.setInt(3,geo.getIdPedido());
		pst.setString(4,geo.getCoordenadas());
		pst.executeUpdate();
		pst.close();
	}
	
	public static ArrayList<Integer> extraerProductosOrden(int idOrden) throws SQLException{
		conectar();
		ArrayList<Integer> buscado = new ArrayList<Integer>();
		String query = "SELECT * FROM company.productosOrden WHERE idOrden = " + idOrden;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			buscado.add(rs.getInt(2));
		}
		return buscado;
	}
	
	public static void insertarProductosOrden(ArrayList<Integer> pedidos, int idOrden) throws SQLException, equipo5.dao.NullException {
		if(pedidos == null){
	             throw new NullException("La lista de pedidos introducida no es válida.");
		}
		conectar();
		for(int i =0; i<pedidos.size(); i++) {
			String query = "INSERT INTO company.productosOrden (idOrden, idMPoLote) VALUES (?, ?);";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			pst.setInt(2, pedidos.get(i));
			pst.setInt(1,idOrden);
			pst.executeUpdate();
			pst.close();
		}
	}
	


	public static OrdenTrazabilidad extraerOrdenTrazabilidad(int id) throws SQLException, ClassNotFoundException {
		conectar();
		String query = "SELECT * FROM company.ordenTrazabilidad WHERE id = "+id+" AND fecha = (SELECT MAX(fecha) FROM company.ordenTrazabilidad WHERE id = " + id + " )";
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		OrdenTrazabilidad buscado = null;
		while(rs.next()) {
			Actor actorOrigen = extraerActor(rs.getString(2));
			Actor actorDestino = extraerActor(rs.getString(3));
			Productos productos = extraerProductos(rs.getInt(5));
			Actor actorTransportista= null;
			if ( extraerActor(rs.getString(9)) != null &&  extraerActor(rs.getString(9)).getId() != "-1") {
				actorTransportista = extraerActor(rs.getString(9));
			}
			ArrayList<Integer> productosOrden = new ArrayList<Integer>();
			if(extraerProductosOrden(rs.getInt(1))!=null) {
				productosOrden = extraerProductosOrden(rs.getInt(1));
			}
			buscado = new OrdenTrazabilidad(rs.getInt(1), actorOrigen, actorDestino, rs.getBoolean(4), productos,
					productosOrden, rs.getInt(6), null, null, actorTransportista, rs.getInt(10), rs.getInt(11), rs.getDate(12));
			buscado.setFirmaEntregaBBDD(rs.getBytes(8));
			buscado.setFirmaRecogidaBBDD(rs.getBytes(7));
					}
		pst.close();
		rs.close();
		return buscado;	
	}

	public static void insertarOrdenTrazabilidad(OrdenTrazabilidad orden) throws SQLException, ClassNotFoundException, NullException {
		if(orden == null){
	             throw new NullException("La orden introducida no es válida.");
		}
		if(orden.getActorOrigen() == null || orden.getActorDestino() == null){
	             throw new NullException("Los actores de la orden no pueden ser null.");
		}
		conectar();
		if(orden.getTransportista() == null || orden.getTransportista().getCifcooperativa()=="-1") {
			//comprobar que los productos no pueden ser null
			String query = "INSERT INTO company.ordenTrazabilidad (id, idActorOrigen, idActorDestino, necesitaTransportista, idProductos, estado, firmaRecogida, firmaEntrega, idRegistro, idCadena)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			pst.setInt(1, orden.getId());
			pst.setString(2, orden.getActorOrigen().getId());
			pst.setString(3, orden.getActorDestino().getId());
			pst.setBoolean(4, orden.isNecesitaTransportista());
			if(extraerProductos(orden.getId())==null) insertarProductos(orden.getProductosPedidos(), orden.getId());
			pst.setInt(5, orden.getId());
			pst.setInt(6, orden.getEstado());
			pst.setBytes(7,orden.getFirmaRecogidaBBDD());
			pst.setBytes(8,orden.getFirmaEntregaBBDD());
			pst.setInt(9, orden.getIdRegistro());
			pst.setInt(10, orden.getIdPedido());
			pst.executeUpdate();
			pst.close();
			if(extraerProductosOrden(orden.getId())==null && orden.getProductosAEntregar().size()>0) insertarProductosOrden(orden.getProductosAEntregar(), orden.getId());
			
		}
		else {
		//comprobar que los productos no pueden ser null
		String query = "INSERT INTO company.ordenTrazabilidad (id, idActorOrigen, idActorDestino, necesitaTransportista, idProductos, estado, firmaRecogida, firmaEntrega, idTransportista, idRegistro, idCadena)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		pst.setInt(1, orden.getId());
		pst.setString(2, orden.getActorOrigen().getId());
		pst.setString(3, orden.getActorDestino().getId());
		pst.setBoolean(4, orden.isNecesitaTransportista());
		if(extraerProductos(orden.getId())==null) insertarProductos(orden.getProductosPedidos(), orden.getId());
		pst.setInt(5, orden.getId());
		pst.setInt(6, orden.getEstado());
		pst.setBytes(7,orden.getFirmaRecogidaBBDD());
		pst.setBytes(8,orden.getFirmaEntregaBBDD());
		pst.setString(9, orden.getTransportista().getId());
		pst.setInt(10, orden.getIdRegistro());
		pst.setInt(11, orden.getIdPedido());
		pst.executeUpdate();
		pst.close();
		if(extraerProductosOrden(orden.getId())==null && orden.getProductosAEntregar().size()>0) insertarProductosOrden(orden.getProductosAEntregar(), orden.getId());
		}
	}

	public static Productos extraerProductos(int id) throws SQLException {
		conectar();
		String query = "SELECT * FROM company.productos WHERE id =" + id;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			Productos buscado = new Productos(rs.getInt(2),  rs.getInt(3), rs.getInt(4),rs.getInt(5),
					rs.getInt(6),rs.getInt(7),rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11),rs.getInt(12));
			pst.close();
			rs.close();
			//conn.close();
			return buscado;
		}
		return null;	
	}

	public static void insertarProductos(Productos producto, int idOrden) throws SQLException, ClassNotFoundException, NullException {
		if(producto == null){
	             throw new NullException("El producto introducido no es válido.");
		}
		conectar();
		String query = "INSERT INTO company.productos (id, malta_palida, malta_munich, malta_negra, malta_crystal, "
				+ "malta_chocolate , malta_caramelo, cebada, cebada_tostada, lupulo_centenial, cajas_stout ,cajas_bisner)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		pst.setInt(1, idOrden);
		pst.setInt(2, producto.getCant_malta_palida());
		pst.setInt(3, producto.getCant_malta_munich());
		pst.setInt(4, producto.getCant_malta_negra());
		pst.setInt(5, producto.getCant_malta_crystal());
		pst.setInt(6, producto.getCant_malta_chocolate());
		pst.setInt(7, producto.getCant_malta_caramelo());
		pst.setInt(8, producto.getCant_cebada());
		pst.setInt(9, producto.getCant_cebada_tostada());
		pst.setInt(10, producto.getCant_lupulo_centenial());
		pst.setInt(11, producto.getCant_lotes_stout());
		pst.setInt(12, producto.getCant_lotes_bisner());
		pst.executeUpdate();
		pst.close();
		//conn.close();
	}

	public static Cadena extraerCadena(int idCadena) throws SQLException {
		conectar();
		String query = "SELECT * FROM company.cadena WHERE cadena.idCadena = "+idCadena;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		Cadena buscado = null;
		while(rs.next()) {
			buscado = new Cadena(idCadena, rs.getString(2), rs.getInt(3));
		}
		pst.close();
		rs.close();
		//conn.close();
		return buscado;
	}

	public static void insertarCadena(Cadena cadena) throws SQLException, NullException {
		if(cadena == null){
	             throw new NullException("La cadena introducida no es válida.");
		}
		int idCadena = cadena.getCodLote();
		if(extraerCadena(idCadena)!= null) {
			conectar();
			String query = "UPDATE company.cadena SET hashInicio = ? , numBloques = ?  WHERE idCadena= ?";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			pst.setString(1, cadena.getHashUltimoBloque());
			pst.setInt(2, cadena.getNumBloques());
			pst.setInt(3, idCadena);
			pst.executeUpdate();
			pst.close();
			//conn.close();
		}else {
			conectar();
			String query = "INSERT INTO company.cadena (idCadena, hashInicio, numBloques) VALUES (?, ?, ?);";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			pst.setInt(1, idCadena);
			pst.setString(2, cadena.getHashUltimoBloque());
			pst.setInt(3, cadena.getNumBloques());
			pst.executeUpdate();
			pst.close();
			//conn.close();
		}
	}

	public static void insertarLote(Lote lote) throws Throwable {
		if(lote == null){
	             throw new NullException("El lote introducido no es válido.");
		}
		conectar();
			String query = "INSERT INTO company.lote (idLote, fecha_inicio, fecha_final, molido, cocido, fermentado, fermentado2, embotellado, qr, fecha_molido, fecha_cocido, fecha_fermentado, fecha_fermentado2, fecha_embotellado, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			pst.setInt(1, lote.getIdBd());
			pst.setDate(2, new Date(lote.getFecha_inicio().getTime()));
			pst.setDate(3, new Date(lote.getFecha_final().getTime()));
			pst.setBoolean(4, lote.isMolido());
			pst.setBoolean(5, lote.isCocido());
			pst.setBoolean(6, lote.isFermentado());
			pst.setBoolean(7, lote.isFermentado2());
			pst.setBoolean(8, lote.isEmbotellado());
			pst.setBytes(9, lote.getQr());
			pst.setDate(10, new Date(lote.getFecha_molido().getTime()));
			pst.setDate(11, new Date(lote.getFecha_cocido().getTime()));
			pst.setDate(12, new Date(lote.getFecha_fermentado().getTime()));
			pst.setDate(13, new Date(lote.getFecha_fermentado2().getTime()));
			pst.setDate(14, new Date(lote.getFecha_embotellado().getTime()));
			pst.setString(15, lote.getTipo());
			pst.executeUpdate();
			pst.close();
		//conn.close();
	}

	public static Lote extraerLote(int idLote) throws SQLException {
		conectar();
		String query = "SELECT * FROM company.lote WHERE idLote = " + idLote;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			Lote buscado = new Lote(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getBytes(9), rs.getBoolean(4), rs.getBoolean(5), rs.getBoolean(6), rs.getBoolean(7), rs.getBoolean(8), rs.getString(15),  rs.getDate(10), rs.getDate(11),  rs.getDate(12), rs.getDate(13), rs.getDate(14));
			pst.close();
			rs.close();
			//conn.close();
			return buscado;
		}
		return null;
	}

	public static void insertarRegistro (Registro registro) throws SQLException, NullException, ClassNotFoundException{
		if(registro == null){
	             throw new NullException("El registro introducido no es válido.");
		}
		conectar();
		String query = "INSERT INTO company.registro (id, idOrden, idCadena, fechaInicio, fechaFin, tempMax, tempMin) VALUES (?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		pst.setInt(1, registro.getId());
		pst.setInt(2, registro.getIdOrdenTrazabilidad());
		if(extraerCadena(registro.getIdPedido())==null) insertarCadena(new Cadena(registro.getIdPedido()));
		pst.setInt(3, registro.getIdPedido());
		pst.setString(4, registro.getFechaInicio());
		pst.setString(5, registro.getFechaFin());
		pst.setInt(6, registro.getTempMax());
		pst.setInt(7, registro.getTempMin());
		pst.executeUpdate();
		pst.close();
		//conn.close();
	}

	public static Registro extraerRegistro (int id) throws SQLException, ClassNotFoundException {
		conectar();
		String query = "SELECT * FROM company.registro WHERE registro.id = " +  id;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()){
			Registro buscado = new Registro(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
			pst.close();
			rs.close();
			//conn.close();
			return buscado;
		}
		//conn.close();
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
		//conn.close();
		return cadena;

	}

	public static Actor extraerActor(String cif) throws SQLException, ClassNotFoundException {
		if(cif!= null) {
			conectar();
			String query = "SELECT * FROM company.actor WHERE actor.cif = '" + cif + "';";
			Statement pst = conn.createStatement();
			ResultSet rs = pst.executeQuery(query);
			while(rs.next()) {
				Actor buscado = new Actor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
				pst.close();
				rs.close();
				return buscado; 
			}
			//conn.close();
			return null;
		}
		else return null;
	}		 

	public static void insertarActor(Actor actor) throws SQLException, ClassNotFoundException, RuntimeException, NullException{
		if(actor == null){
	             throw new NullException("El actor introducido no es válido.");
		}
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
		//conn.close();
	}

	public static Bloque extraerBloque(String hashBloquePedido) throws SQLException, ClassNotFoundException, RuntimeException {
		conectar();
		Bloque devolver =null;
		String query = "SELECT * FROM company.bloque WHERE bloque.hashBloque = '" + hashBloquePedido+"';" ;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			int tipoBloque = rs.getInt(3);
			String hashPrevio = rs.getString(2);
			int numBloque = rs.getInt(4);
			int codLote = rs.getInt(5);
			int idCadena = rs.getInt(8);
			int estadoOrden = rs.getInt(9);
			switch (tipoBloque) {
			case 0:
				Bloque buscado = new Bloque(hashPrevio, tipoBloque, numBloque, codLote, extraerOrdenTrazabilidad(rs.getInt(6)), idCadena, estadoOrden);
				devolver = buscado;
				break;
			case 1:
				Bloque buscado1 = new Bloque(hashPrevio, tipoBloque, numBloque, codLote, extraerRegistro(rs.getInt(6)), idCadena, estadoOrden);
				devolver = buscado1;
				break;
			case 2:
				Bloque buscado2 = new Bloque(hashPrevio, tipoBloque, numBloque, codLote, extraerLote(rs.getInt(6)), idCadena, estadoOrden);
				devolver = buscado2;
				break;
			case 3:
				Bloque buscado4 = new Bloque(hashPrevio, tipoBloque, numBloque, codLote, extraerGeolocalizacion(rs.getInt(6)), idCadena, estadoOrden);
				devolver = buscado4;
				break;
			default:
				Bloque buscado3 = new Bloque(hashPrevio, tipoBloque, numBloque, codLote, new DatosContainer(), idCadena, estadoOrden);
				devolver = buscado3;
				break;
			}
			pst.close();
			rs.close();
			//conn.close();
			return devolver;
		}
		//conn.close();
		return null;
	}

	public static void insertarBloque(Bloque bloqAinsertar) throws Throwable {
		if(bloqAinsertar == null){
	             throw new NullException("El bloque introducido no es válido.");
		}
		int data=0;
		switch (bloqAinsertar.getTipoBloque()) {
		case 0:
			OrdenTrazabilidad aInsertar = (OrdenTrazabilidad) bloqAinsertar.getDatos();
			data = aInsertar.getId();
			//if(extraerOrdenTrazabilidad(data)==null) {
			insertarOrdenTrazabilidad(aInsertar);
			//}
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
		case 3: //Geolocalizacion
			geolocalizacion aInsertar4 = (geolocalizacion) bloqAinsertar.getDatos();
			data = aInsertar4.getIdOrden();
			if(extraerGeolocalizacion(data)==null) {
				insertarGeolocalizacion(aInsertar4);
			}			
			break;
		}
		conectar();
		String query = "INSERT INTO company.bloque (hashBloque, hashPrevio, tipoBloque, numBloque, codLote, datosContainer, timeStamp, idCadena, estadoOrden) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		pst.setString(1, bloqAinsertar.getHashCode());
		pst.setString(2, bloqAinsertar.getHashPrevio());
		pst.setInt(3,bloqAinsertar.getTipoBloque());
		pst.setInt(4,bloqAinsertar.getNumBloque());
		pst.setInt(5,bloqAinsertar.getCodLote());
		pst.setInt(6, data);
		pst.setFloat(7, bloqAinsertar.getTimeStamp());
		pst.setInt(8, bloqAinsertar.getIdCadena());
		pst.setInt(9, bloqAinsertar.getEstadoOrden());
		pst.executeUpdate();
		pst.close();
		//conn.close();
	}
	
	public static boolean estaOrden(int id, ArrayList<OrdenTrazabilidad> lista) {
		for (int i =0; i< lista.size(); i++) {
			if (lista.get(i).getId()==id) return true;
		}
		return false;
		
	}
	
	public static ArrayList<OrdenTrazabilidad> extraerOrdenesActorOrigen(String idActor) throws SQLException, ClassNotFoundException{
		conectar();
		ArrayList<OrdenTrazabilidad> lista = new ArrayList<OrdenTrazabilidad>();
		String query = "SELECT id FROM company.ordenTrazabilidad WHERE idActorOrigen = '"+ idActor +"';";
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			if(!estaOrden(rs.getInt(1), lista)) {
				lista.add(extraerOrdenTrazabilidad(rs.getInt(1)));
			}
		}		
		pst.close();
		rs.close();
		return lista;
	}
	public static ArrayList<OrdenTrazabilidad> extraerOrdenesActorDestino(String idActor) throws SQLException, ClassNotFoundException{
		conectar();
		ArrayList<OrdenTrazabilidad> lista = new ArrayList<OrdenTrazabilidad>();
		String query = "SELECT id FROM company.ordenTrazabilidad WHERE idActorDestino = '"+ idActor +"';";
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			if(!estaOrden(rs.getInt(1), lista)) {
				lista.add(extraerOrdenTrazabilidad(rs.getInt(1)));
			}
		}		
		pst.close();
		rs.close();
		return lista;	
	}
	
	  public static LinkedList<Registro> registrosConOrden(int idOrden) throws SQLException, ClassNotFoundException{
	        conectar();
	        String query = "SELECT * FROM company.registro WHERE registro.idOrden = " + idOrden;
	        Statement pst = conn.createStatement();
	        ResultSet rs = pst.executeQuery(query);
	        LinkedList<Registro> lista = new LinkedList<Registro>();
	        while(rs.next()){
				Registro buscado = new Registro(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
	            lista.add(buscado);
	        }
	        pst.close();
	        rs.close();
	        conn.close();
	        return lista;
	    }
	
	public static LinkedList<StockLote> extraerStockLote(Actor actor, int idOrden) throws SQLException, ClassNotFoundException, NotInDatabaseException {
		conectar();
		LinkedList<StockLote> buscado = new LinkedList<StockLote>();
		switch(actor.getTipoActor()) {
		case 4:
			String query = "SELECT * FROM company.stockRetailer WHERE idActor = '" + actor.getId()+"' AND idOrden = "+idOrden+";";
			Statement pst = conn.createStatement();
			ResultSet rs = pst.executeQuery(query);
			while(rs.next()){
				Lote loteBD = extraerLote(rs.getInt(2));
				StockLote nuevo = new StockLote(loteBD, rs.getDate(3), rs.getDate(4), rs.getInt(5), rs.getInt(6), rs.getString(7));
				buscado=estaLote(buscado, nuevo);
			}
			pst.close();
			rs.close();
			break;
		case 3:
			String query2 = "SELECT * FROM company.stockFabricaLotes WHERE idOrden = "+idOrden+";";
			Statement pst2 = conn.createStatement();
			ResultSet rs2 = pst2.executeQuery(query2);
			while(rs2.next()){
				Lote loteBD = extraerLote(rs2.getInt(2));
				StockLote nuevo = new StockLote(loteBD, rs2.getDate(3), rs2.getDate(4), rs2.getInt(5), rs2.getInt(6), null);
				buscado=estaLote(buscado, nuevo);
			}
			pst2.close();
			rs2.close();
			break;
		default:  
			System.out.println("el actor suministrado no almacena lotes");
		}
		return buscado;
	}
	
	public static LinkedList<StockMP> estaMP(LinkedList<StockMP> lista, StockMP nuevo) {
		for(int i =0; i<lista.size(); i++) {
			if(lista.get(i).getMp().getId()==nuevo.getMp().getId()) {
				if(lista.get(i).getFecha_salida()==null && nuevo.getFecha_salida()!=null) {
					lista.remove(i);
					lista.add(nuevo);
					return lista;
				}
				else return lista;
			}			
		}
		lista.add(nuevo);
		return lista;
	}
	
	public static LinkedList<StockLote> estaLote(LinkedList<StockLote> lista, StockLote nuevo) {
		for(int i =0; i<lista.size(); i++) {
			if(lista.get(i).getLote().getIdBd()==nuevo.getLote().getIdBd()) {
				if(lista.get(i).getFecha_salida()==null && nuevo.getFecha_salida()!=null) {
					lista.remove(i);
					lista.add(nuevo);
					return lista;
				}
				else return lista;
			}			
		}
		lista.add(nuevo);
		return lista;
	}
    
	public static LinkedList<StockMP> extraerStockMP(Actor actor, int idOrden) throws SQLException, ClassNotFoundException, NotInDatabaseException {
		LinkedList<StockMP> aDevolver = new LinkedList<StockMP>();
		switch(actor.getTipoActor()){
		case 0:
			conectar();
			String query = "SELECT * FROM company.stockAgricultor WHERE idActor = '"+actor.getId()+"' AND idOrden = "+idOrden;
			Statement pst = conn.createStatement();
			ResultSet rs = pst.executeQuery(query);
			while(rs.next()) {
					MateriaPrima mp = extraerMateriaPrima(rs.getInt(2));
					StockMP nuevo = new StockMP(mp, rs.getDate(3), rs.getDate(4), rs.getInt(5), rs.getInt(6), rs.getString(7));
					aDevolver=estaMP(aDevolver, nuevo);
			}
			pst.close();
			rs.close();
			break;
		case 1:
			conectar();
			String query2 = "SELECT * FROM company.stockCooperativa WHERE idActor = '"+actor.getId()+"' AND idOrden = "+idOrden;
			Statement pst2 = conn.createStatement();
			ResultSet rs2 = pst2.executeQuery(query2);
			while(rs2.next()) {
				MateriaPrima mp = extraerMateriaPrima(rs2.getInt(2));
				StockMP nuevo = new StockMP(mp, rs2.getDate(3), rs2.getDate(4), rs2.getInt(5), rs2.getInt(6), rs2.getString(7));
				aDevolver=estaMP(aDevolver, nuevo);
			}
			pst2.close();
			rs2.close();
			break;
		case 3:
			conectar();
			String query3 = "SELECT * FROM company.stockFabricaMMPP WHERE idOrden = "+idOrden;
			Statement pst3 = conn.createStatement();
			ResultSet rs3 = pst3.executeQuery(query3);
			while(rs3.next()) {
				MateriaPrima mp = extraerMateriaPrima(rs3.getInt(2));
				StockMP nuevo = new StockMP(mp, rs3.getDate(3), rs3.getDate(4), rs3.getInt(5), rs3.getInt(6), null);
				aDevolver=estaMP(aDevolver, nuevo);
			}
			pst3.close();
			rs3.close();
			break;
		default: 
			System.out.println("el actor suministrado no almacena materias primas.");
		}
		return aDevolver;
	}
    
	public static void insertarStockLote(StockLote stockLote) throws Throwable{
	   	if(stockLote == null){
	             throw new NullException("El stock de lote introducido no es válido.");
		}
		if(stockLote.getFecha_salida()==null) {
	   		Actor actor = extraerActor((""+stockLote.getIdActor()));
	   		switch(actor.getTipoActor()){
	   		case 4:
	   			conectar();
			    String query = "INSERT INTO company.stockRetailer (idLote, fecha_entrada, idOrden, idCadena, idActor) VALUES ( ?, ?, ?, ?, ?);"; 
			    PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			    Date date = new Date(System.currentTimeMillis());
		        pst.setInt(1, stockLote.getLote().getIdBd());
			    pst.setDate(2, date);
			    pst.setInt(3, stockLote.getIdOrden());
			    pst.setInt(4, stockLote.getIdPedido());
			    pst.setString(5, stockLote.getIdActor());
			    pst.executeUpdate();
			    pst.close();
			    break;
	   		case 3:
	   			conectar();
			    String query2 = "INSERT INTO company.stockFabricaLotes (idLote, fecha_entrada, idOrden, idCadena) VALUES (?, ?, ?, ?);"; 
			    PreparedStatement pst2 = (PreparedStatement) conn.prepareStatement(query2);
			    Date date2 = new Date(System.currentTimeMillis());
		        pst2.setInt(1, stockLote.getLote().getIdBd());
			    pst2.setDate(2, date2);
			    pst2.setInt(3, stockLote.getIdOrden());
			    pst2.setInt(4, stockLote.getIdPedido());
			    pst2.executeUpdate();
			    pst2.close();
			    break;
	   		} 
	   	}
	   	else if(stockLote.getFecha_salida()!=null) {
	   		Actor actor = extraerActor((""+stockLote.getIdActor()));
	   		switch(actor.getTipoActor()){
	   		case 4:
	   			conectar();
			    String query = "INSERT INTO company.stockRetailer (idLote, fecha_entrada, fecha_salida, idOrden, idCadena, idActor) VALUES ( ?, ?, ?, ?, ?, ?);"; 
			    PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			    Date date = new Date(System.currentTimeMillis());
		        pst.setInt(1, stockLote.getLote().getIdBd());
			    pst.setDate(2, stockLote.getFecha_entrada());
			    pst.setDate(3, date);
			    pst.setInt(4, stockLote.getIdOrden());
			    pst.setInt(5, stockLote.getIdPedido());
			    pst.setString(6, stockLote.getIdActor());
			    pst.executeUpdate();
			    pst.close();
			    break;
	   		case 3:
	   			conectar();
			    String query2 = "INSERT INTO company.stockFabricaLotes (idLote, fecha_entrada, fecha_salida, idOrden, idCadena) VALUES (?, ?, ?, ?, ?);"; 
			    PreparedStatement pst2 = (PreparedStatement) conn.prepareStatement(query2);
			    Date date2 = new Date(System.currentTimeMillis());
		        pst2.setInt(1, stockLote.getLote().getIdBd());
			    pst2.setDate(2, stockLote.getFecha_entrada());
			    pst2.setDate(3, date2);
			    pst2.setInt(4, stockLote.getIdOrden());
			    pst2.setInt(5, stockLote.getIdPedido());
			    pst2.executeUpdate();
			    pst2.close();
			    break;
	   		} 
	    } 	
    }
    public static void insertarStockMP(StockMP stockMateria) throws SQLException, ClassNotFoundException, NullException{
    	    if(stockMateria == null){
	             throw new NullException("El stock de materia prima introducido no es válido.");
		}
	    if(stockMateria.getFecha_salida()==null) {
	   		Actor actor = extraerActor((""+stockMateria.getIdActor()));
	   		switch(actor.getTipoActor()){
	   		case 0:
	   			conectar();
			    String query = "INSERT INTO company.stockAgricultor (idMateriaPrima, fecha_entrada, idOrden, idCadena, idActor) VALUES ( ?, ?, ?, ?, ?);"; 
			    PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			    Date date = new Date(System.currentTimeMillis());
		        pst.setInt(1, stockMateria.getMp().getId());
			    pst.setDate(2, date);
			    pst.setInt(3, stockMateria.getIdOrden());
			    pst.setInt(4, stockMateria.getIdPedido());
			    pst.setString(5, stockMateria.getIdActor());
			    pst.executeUpdate();
			    pst.close();
			    break;
	   		case 1:
	   			conectar();
			    String query3 = "INSERT INTO company.stockCooperativa (idMateriaPrima, fecha_entrada, idOrden, idCadena, idActor) VALUES ( ?, ?, ?, ?, ?);"; 
			    PreparedStatement pst3 = (PreparedStatement) conn.prepareStatement(query3);
			    Date date3 = new Date(System.currentTimeMillis());
		        pst3.setInt(1, stockMateria.getMp().getId());
			    pst3.setDate(2, date3);
			    pst3.setInt(3, stockMateria.getIdOrden());
			    pst3.setInt(4, stockMateria.getIdPedido());
			    pst3.setString(5, stockMateria.getIdActor());
			    pst3.executeUpdate();
			    pst3.close();
			    break;
	   		case 3:
	   			conectar();
			    String query2 = "INSERT INTO company.stockFabricaMMPP (idMateriaPrima, fecha_entrada, idOrden, idCadena) VALUES (?, ?, ?, ?);"; 
			    PreparedStatement pst2 = (PreparedStatement) conn.prepareStatement(query2);
			    Date date2 = new Date(System.currentTimeMillis());
		        pst2.setInt(1, stockMateria.getMp().getId());
			    pst2.setDate(2, date2);
			    pst2.setInt(3, stockMateria.getIdOrden());
			    pst2.setInt(4, stockMateria.getIdPedido());
			    pst2.executeUpdate();
			    pst2.close();
			    break;
	   		} 
	   	}
	   	else if(stockMateria.getFecha_salida()!=null) {
	   		Actor actor = extraerActor((""+stockMateria.getIdActor()));
	   		switch(actor.getTipoActor()){
	   		case 0:
	   			conectar();
			    String query = "INSERT INTO company.stockAgricultor (idMateriaPrima, fecha_entrada, fecha_salida, idOrden, idCadena, idActor) VALUES (?, ?, ?, ?, ?, ?);"; 
			    PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
			    Date date = new Date(System.currentTimeMillis());
		        pst.setInt(1, stockMateria.getMp().getId());
			    pst.setDate(2, stockMateria.getFecha_entrada());
			    pst.setDate(3, date);
			    pst.setInt(4, stockMateria.getIdOrden());
			    pst.setInt(5, stockMateria.getIdPedido());
			    pst.setString(6, stockMateria.getIdActor());
			    pst.executeUpdate();
			    pst.close();
			    break;
	   		case 1:
	   			conectar();
			    String query3 = "INSERT INTO company.stockCooperativa (idMateriaPrima, fecha_entrada, fecha_salida, idOrden, idCadena, idActor) VALUES (?, ?, ?, ?, ?, ?);"; 
			    PreparedStatement pst3 = (PreparedStatement) conn.prepareStatement(query3);
			    Date date3 = new Date(System.currentTimeMillis());
		        pst3.setInt(1, stockMateria.getMp().getId());
			    pst3.setDate(2, stockMateria.getFecha_entrada());
			    pst3.setDate(3, date3);
			    pst3.setInt(4, stockMateria.getIdOrden());
			    pst3.setInt(5, stockMateria.getIdPedido());
			    pst3.setString(6, stockMateria.getIdActor());
			    pst3.executeUpdate();
			    pst3.close();
			    break;
	   		case 3:
	   			conectar();
			    String query2 = "INSERT INTO company.stockFabricaMMPP (idMateriaPrima, fecha_entrada, fecha_salida, idOrden, idCadena) VALUES (?, ?, ?, ?, ?);"; 
			    PreparedStatement pst2 = (PreparedStatement) conn.prepareStatement(query2);
			    Date date2 = new Date(System.currentTimeMillis());
		        pst2.setInt(1, stockMateria.getMp().getId());
			    pst2.setDate(2, stockMateria.getFecha_entrada());
			    pst2.setDate(3, date2);
			    pst2.setInt(4, stockMateria.getIdOrden());
			    pst2.setInt(5, stockMateria.getIdPedido());
			    pst2.executeUpdate();
			    pst2.close();
			    break;
	   		} 	
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
        return siguienteId;
    }
    public static int idPedido() throws SQLException, ClassNotFoundException{
        conectar();
        String query = "SELECT MAX (idCadena) FROM company.cadena";
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        int siguienteId = 1;
        if(rs.next()){
            siguienteId = rs.getInt(1) + 1;
        }
        pst.close();
        rs.close();
        //conn.close();
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
       //conn.close();
        return siguienteId;
    }
    
    public static int idGeolocalizacion() throws SQLException, ClassNotFoundException{
        conectar();
        String query = "SELECT MAX (id) FROM company.geolocalizacion";
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        int siguienteId = 1;
        if(rs.next()){
            siguienteId = rs.getInt(1) + 1;
        }
        pst.close();
       rs.close();
       //conn.close();
        return siguienteId;
    }
    
    public static int idRegistro() throws SQLException, ClassNotFoundException{
        conectar();
        String query = "SELECT MAX (id) FROM company.registro";
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        int siguienteId = 1;
        if(rs.next()){
            siguienteId = rs.getInt(1) + 1;
        }
        pst.close();
       rs.close();
       //conn.close();
        return siguienteId;
    }
    
    public static int idMateriaPrima() throws SQLException, ClassNotFoundException{
        conectar();
        String query = "SELECT MAX (idMateriaPrima) FROM company.materiaPrima";
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        int siguienteId = 1;
        if(rs.next()){
            siguienteId = rs.getInt(1) + 1;
        }
        pst.close();
       rs.close();
       //conn.close();
        return siguienteId;
    }
    public static MateriaPrima extraerMateriaPrima(int id) throws SQLException {
    	conectar();
		String query = "SELECT * FROM company.materiaPrima WHERE idMateriaPrima = " + id;
		Statement pst = conn.createStatement();
		ResultSet rs = pst.executeQuery(query);
		while(rs.next()) {
			MateriaPrima buscado = new MateriaPrima(rs.getString(2), id, rs.getFloat(3));
			pst.close();
			rs.close();
			return buscado;
		}
		return null;
    }
    public static void insertarMateriaPrima(MateriaPrima mp) throws SQLException, ClassNotFoundException, RuntimeException, NullException{
		if(mp == null){
	             throw new NullException("La materia prima introducida no es válida.");
		}
                conectar();
		String query = "INSERT INTO company.materiaPrima (idMateriaPrima, tipo, cantidad) VALUES (?, ?, ?);";
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(query);
		pst.setInt(1, mp.getId());
		pst.setString(2, mp.getTipo());
		pst.setFloat(3, (float) mp.getCantidad());
		pst.executeUpdate();
		pst.close();
	}
    public static LinkedList<StockMP> extraerStockMpPorPedido(Actor actor,OrdenTrazabilidad orden) throws SQLException, ClassNotFoundException, NotInDatabaseException {
        LinkedList<StockMP> aDevolver = new LinkedList<StockMP>();
        switch(actor.getTipoActor()){
        case 0:
            conectar();
            String query = "SELECT * FROM company.stockAgricultor WHERE idActor = '"+actor.getId()+"' AND idCadena = "+orden.getIdPedido()+" AND idOrden NOT IN (SELECT idOrden FROM company.stockAgricultor WHERE fecha_salida <> NULL)";
            Statement pst = conn.createStatement();
            ResultSet rs = pst.executeQuery(query);
            while(rs.next()) {
                    MateriaPrima mp = extraerMateriaPrima(rs.getInt(2));
                    StockMP nuevo = new StockMP(mp, rs.getDate(3), rs.getDate(4), rs.getInt(5), rs.getInt(6), rs.getString(7));
                    aDevolver=estaMP(aDevolver, nuevo);
            }
            pst.close();
            rs.close();
            break;
        case 1:
            conectar();
            String query2 = "SELECT * FROM company.stockCooperativa WHERE idActor = '"+actor.getId()+"' AND idCadena = "+orden.getIdPedido()+" AND idOrden NOT IN (SELECT idOrden FROM company.stockCooperativa WHERE fecha_salida <> NULL)";
            Statement pst2 = conn.createStatement();
            ResultSet rs2 = pst2.executeQuery(query2);
            while(rs2.next()) {
                MateriaPrima mp = extraerMateriaPrima(rs2.getInt(2));
                StockMP nuevo = new StockMP(mp, rs2.getDate(3), rs2.getDate(4), rs2.getInt(5), rs2.getInt(6), rs2.getString(7));
                aDevolver=estaMP(aDevolver, nuevo);
            }
            pst2.close();
            rs2.close();
            break;
        case 3:
            conectar();
            String query3 = "SELECT * FROM company.stockfabricalotes WHERE idCadena = "+orden.getIdPedido()+" AND idOrden NOT IN (SELECT idOrden FROM company.stockfabricalotes WHERE fecha_salida <> NULL)";
            Statement pst3 = conn.createStatement();
            ResultSet rs3 = pst3.executeQuery(query3);
            while(rs3.next()) {
                MateriaPrima mp = extraerMateriaPrima(rs3.getInt(2));
                StockMP nuevo = new StockMP(mp, rs3.getDate(3), rs3.getDate(4), rs3.getInt(5), rs3.getInt(6), null);
                aDevolver=estaMP(aDevolver, nuevo);
            }
            pst3.close();
            rs3.close();
            break;
        default:
            System.out.println("el actor suministrado no almacena materias primas.");
        }
        return aDevolver;
    }
    public static OrdenTrazabilidad extraerOrdenTrazabilidadEstado(int id,int estado) throws SQLException, ClassNotFoundException {
        conectar();
        String query = "SELECT * FROM company.ordenTrazabilidad WHERE id = " + id+" AND estado = "+estado;
        Statement pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(query);
        while(rs.next()) {
            Actor actorOrigen = extraerActor(rs.getString(2));
            Actor actorDestino = extraerActor(rs.getString(3));
            Productos productos = extraerProductos(rs.getInt(5));
            Actor actorTransportista = extraerActor(rs.getString(9));
            ArrayList<Integer> productosOrden = new ArrayList<Integer>();
            if(extraerProductosOrden(rs.getInt(1))!=null) {
                productosOrden = extraerProductosOrden(rs.getInt(1));
            }
            OrdenTrazabilidad buscado = new OrdenTrazabilidad(rs.getInt(1), actorOrigen, actorDestino, rs.getBoolean(4), productos,
                    productosOrden, rs.getInt(6), null, null, actorTransportista, rs.getInt(10), rs.getInt(11), rs.getDate(12));
			buscado.setFirmaEntregaBBDD(rs.getBytes(8));
			buscado.setFirmaRecogidaBBDD(rs.getBytes(7));
            pst.close();
            rs.close();
            conn.close();
            return buscado;
        }
        //conn.close();
        return null;    
    }
    	public static ArrayList<OrdenTrazabilidad> extraerTodasLasOrdenes() throws SQLException, ClassNotFoundException{
    		conectar();
    		ArrayList<OrdenTrazabilidad> lista = new ArrayList<OrdenTrazabilidad>();
    		String query = "SELECT * FROM company.ordenTrazabilidad;";
    		Statement pst = conn.createStatement();
    		ResultSet rs = pst.executeQuery(query);
    		while(rs.next()) {
    			Actor actorOrigen = extraerActor(rs.getString(2));
    			Actor actorDestino = extraerActor(rs.getString(3));
    			Productos productos = extraerProductos(rs.getInt(5));
    			Actor actorTransportista= null;
    			if ( extraerActor(rs.getString(9)) != null &&  extraerActor(rs.getString(9)).getId() != "-1") {
    				actorTransportista = extraerActor(rs.getString(9));
    			}
    			ArrayList<Integer> productosOrden = new ArrayList<Integer>();
    			if(extraerProductosOrden(rs.getInt(1))!=null) {
    				productosOrden = extraerProductosOrden(rs.getInt(1));
    			}
    			OrdenTrazabilidad buscado = new OrdenTrazabilidad(rs.getInt(1), actorOrigen, actorDestino, rs.getBoolean(4), productos,
    					productosOrden, rs.getInt(6), null, null, actorTransportista, rs.getInt(10), rs.getInt(11), rs.getDate(12));
    			buscado.setFirmaEntregaBBDD(rs.getBytes(8));
    			buscado.setFirmaRecogidaBBDD(rs.getBytes(7));
    			lista.add(buscado);
    		}		
    		pst.close();
    		rs.close();
    		return lista;	
    	}
   
}
