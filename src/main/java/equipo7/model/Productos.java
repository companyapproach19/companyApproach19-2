package equipo7.model;

import java.io.Serializable;


public class Productos implements Serializable {
	
<<<<<<< HEAD
    private int cant_malta_palida;
=======
    private int cant_malta_base_palida;
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e
    private int cant_malta_munich;
    private int cant_malta_negra;
    private int cant_malta_crystal;
    private int cant_malta_chocolate;
    private int cant_malta_caramelo;
    private int cant_malta_pilsner;
    private int cant_cebada_tostada;
<<<<<<< HEAD
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
=======
    private int cant_lupulo_centennial;
    private int cant_lupulo_perle;
	private int cant_lupulo_tettnanger;
	private int cant_levadura_lager;
	private int cant_levadura_ale;
    private int cant_lotes_stout;
    private int cant_lotes_pilsner;
    
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e

    public Productos(int cant_malta_base_palida,int cant_malta_munich,int cant_malta_negra,int cant_malta_crystal,
    		int cant_malta_chocolate,int cant_malta_caramelo,int cant_malta_pilsner,int cant_cebada_tostada,
    		int cant_lupulo_centennial,int cant_lupulo_perle,int cant_lupulo_tettnanger,int cant_levadura_lager,
    		int cant_levadura_ale,int cant_lotes_stout,int cant_lotes_pilsner){
    	
    	this.cant_malta_base_palida=cant_malta_base_palida;
    	this.cant_malta_munich=cant_malta_munich;
    	this.cant_malta_negra=cant_malta_negra;
    	this.cant_malta_crystal=cant_malta_crystal;
    	this.cant_malta_chocolate=cant_malta_chocolate;
    	this.cant_malta_caramelo=cant_malta_caramelo;
    	this.cant_malta_pilsner=cant_malta_pilsner;
    	this.cant_cebada_tostada=cant_cebada_tostada;
    	this.cant_lupulo_centennial=cant_lupulo_centennial;
        this.cant_lupulo_perle=cant_lupulo_perle;
        this.cant_lupulo_tettnanger=cant_lupulo_tettnanger;
        this.cant_levadura_lager=cant_levadura_lager;
        this.cant_levadura_ale=cant_levadura_ale;
        this.cant_lotes_stout=cant_lotes_stout;
        this.cant_lotes_pilsner=cant_lotes_pilsner;
    }


	public int getCant_malta_base_palida() {
		return cant_malta_base_palida;
	}


	public int getCant_malta_munich() {
		return cant_malta_munich;
	}


	public int getCant_malta_negra() {
		return cant_malta_negra;
	}


<<<<<<< HEAD
    public int getCant_lotes_stout() {
        return cant_lotes_stout;
    }

    public int getCant_lotes_bisner() {
        return cant_lotes_bisner;
    }
}
=======
	public int getCant_malta_crystal() {
		return cant_malta_crystal;
	}


	public int getCant_malta_chocolate() {
		return cant_malta_chocolate;
	}


	public int getCant_malta_caramelo() {
		return cant_malta_caramelo;
	}


	public int getCant_malta_pilsner() {
		return cant_malta_pilsner;
	}


	public int getCant_cebada_tostada() {
		return cant_cebada_tostada;
	}


	public int getCant_lupulo_centennial() {
		return cant_lupulo_centennial;
	}


	public int getCant_lupulo_perle() {
		return cant_lupulo_perle;
	}


	public int getCant_lupulo_tettnanger() {
		return cant_lupulo_tettnanger;
	}


	public int getCant_levadura_lager() {
		return cant_levadura_lager;
	}


	public int getCant_levadura_ale() {
		return cant_levadura_ale;
	}


	public int getCant_lotes_stout() {
		return cant_lotes_stout;
	}


	public int getCant_lotes_pilsner() {
		return cant_lotes_pilsner;
	}
	
	public int[] getCantMateriasPrimas() {
		return new int[] {this.cant_malta_base_palida,this.cant_malta_munich,this.cant_malta_negra,
				this.cant_malta_crystal,this.cant_malta_chocolate,this.cant_malta_caramelo,
				this.cant_malta_pilsner,this.cant_cebada_tostada,this.cant_lupulo_centennial,
				this.cant_lupulo_perle,this.cant_lupulo_tettnanger,this.cant_levadura_lager,this.cant_levadura_ale};
	}
	
	public int[] getCantLotes() {
		return new int[] {this.cant_lotes_stout,this.cant_lotes_pilsner};
	}
>>>>>>> fb283f52dfb5959529ee1a682e0a78260bc81c4e

   
}