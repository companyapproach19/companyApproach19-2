package equipo7.model;

import java.io.Serializable;


public class Productos implements Serializable {
	
    private int cant_malta_palida;
    private int cant_malta_munich;
    private int cant_malta_negra;
    private int cant_malta_crystal;
    private int cant_malta_chocolate;
    private int cant_malta_caramelo;
    private int cant_cebada;
    private int cant_cebada_tostada;
    private int cant_lupulo_centenial;
    private int cant_lotes_stout;
    private int cant_lotes_bisner;
    

    public Productos(int cant_malta_palida, int cant_malta_munich,
                     int cant_malta_negra, int cant_malta_crystal,
                     int cant_malta_chocolate, int cant_malta_caramelo,
                     int cant_cebada, int cant_cebada_tostada, int cant_lupulo_centenial,
                     int cant_lotes_stout, int cant_lotes_bisner) {
    	
        this.cant_malta_palida = cant_malta_palida;
        this.cant_malta_munich = cant_malta_munich;
        this.cant_malta_negra = cant_malta_negra;
        this.cant_malta_crystal = cant_malta_crystal;
        this.cant_malta_chocolate = cant_malta_chocolate;
        this.cant_malta_caramelo = cant_malta_caramelo;
        this.cant_cebada = cant_cebada;
        this.cant_cebada_tostada = cant_cebada_tostada;
        this.cant_lupulo_centenial = cant_lupulo_centenial;
        this.cant_lotes_stout = cant_lotes_stout;
        this.cant_lotes_bisner = cant_lotes_bisner;
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

    public int getCant_lotes_stout() {
        return cant_lotes_stout;
    }

    public int getCant_lotes_bisner() {
        return cant_lotes_bisner;
    }
<<<<<<< HEAD
}

=======
}
>>>>>>> c2c9f65c2cd22dee03b8db227de0266dbb4214cb
