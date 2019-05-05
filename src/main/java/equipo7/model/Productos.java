package equipo7.model;

import java.io.Serializable;
import java.sql.SQLException;

import equipo5.dao.metodosCompany;

public class Productos implements Serializable {
	private int id;
    private int cant_malta_palida;
    private int cant_malta_munich;
    private int cant_malta_negra;
    private int cant_malta_crystal;
    private int cant_malta_chocolate;
    private int cant_malta_caramelo;
    private int cant_cebada;
    private int cant_cebada_tostada;
    private int cant_lupulo_centenial;
    private int cant_cajas_stout;
    private int cant_cajas_bisner;
    private static final long serialVersionUID = 1L;

    public Productos(int id, int cant_malta_palida, int cant_malta_munich,
                     int cant_malta_negra, int cant_malta_crystal,
                     int cant_malta_chocolate, int cant_malta_caramelo,
                     int cant_cebada, int cant_cebada_tostada, int cant_lupulo_centenial,
                     int cant_cajas_stout, int cant_cajas_bisner) {
    	this.id=id;
        this.cant_malta_palida = cant_malta_palida;
        this.cant_malta_munich = cant_malta_munich;
        this.cant_malta_negra = cant_malta_negra;
        this.cant_malta_crystal = cant_malta_crystal;
        this.cant_malta_chocolate = cant_malta_chocolate;
        this.cant_malta_caramelo = cant_malta_caramelo;
        this.cant_cebada = cant_cebada;
        this.cant_cebada_tostada = cant_cebada_tostada;
        this.cant_lupulo_centenial = cant_lupulo_centenial;
        this.cant_cajas_stout = cant_cajas_stout;
        this.cant_cajas_bisner = cant_cajas_bisner;
    }
    
	    public Productos(int cant_malta_palida, int cant_malta_munich,
	            int cant_malta_negra, int cant_malta_crystal,
	            int cant_malta_chocolate, int cant_malta_caramelo,
	            int cant_cebada, int cant_cebada_tostada, int cant_lupulo_centenial,
<<<<<<< HEAD
	            int cant_cajas_stout, int cant_cajas_bisner) throws ClassNotFoundException, SQLException {
	this.id=metodosCompany.idProductos();
=======
	            int cant_cajas_stout, int cant_cajas_bisner) {
	try {
    this.id=metodosCompany.idProductos();
  } catch (ClassNotFoundException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  } catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
>>>>>>> 2617ac256a11f7c9c41d26e9adc5aa6d86211dac
	this.cant_malta_palida = cant_malta_palida;
	this.cant_malta_munich = cant_malta_munich;
	this.cant_malta_negra = cant_malta_negra;
	this.cant_malta_crystal = cant_malta_crystal;
	this.cant_malta_chocolate = cant_malta_chocolate;
	this.cant_malta_caramelo = cant_malta_caramelo;
	this.cant_cebada = cant_cebada;
	this.cant_cebada_tostada = cant_cebada_tostada;
	this.cant_lupulo_centenial = cant_lupulo_centenial;
	this.cant_cajas_stout = cant_cajas_stout;
	this.cant_cajas_bisner = cant_cajas_bisner;
	}

    public int getCant_malta_palida() {
        return cant_malta_palida;
    }

    public int getCant_malta_munich() {
        return cant_malta_munich;
    }

    public int getCant_malta_negra() {
        return cant_malta_negra;
    }

    public int getCant_malta_crystal() {
        return cant_malta_crystal;
    }

    public int getCant_malta_chocolate() {
        return cant_malta_chocolate;
    }

    public int getCant_malta_caramelo() {
        return cant_malta_caramelo;
    }

    public int getCant_cebada() {
        return cant_cebada;
    }

    public int getCant_cebada_tostada() {
        return cant_cebada_tostada;
    }

    public int getCant_lupulo_centenial() {
        return cant_lupulo_centenial;
    }

    public int getCant_cajas_stout() {
        return cant_cajas_stout;
    }

    public int getCant_cajas_bisner() {
        return cant_cajas_bisner;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

