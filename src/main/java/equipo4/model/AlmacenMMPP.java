package equipo4.model;

public class AlmacenMMPP {
	public static int id;
	public static int capacidad;
	public static MateriaPrima maltaPilsner = new MateriaPrima(0,1739130434.7826 * 5);
	public static MateriaPrima maltaCaramelo = new MateriaPrima(0,1739130434.7826 * 5);
	public static MateriaPrima maltaBasePalida = new MateriaPrima(0,1739130434.7826 * 5);
	public static MateriaPrima maltaMunich = new MateriaPrima(0,1739130434.7826 * 5);
	public static MateriaPrima maltaNegra = new MateriaPrima(0,1739130434.7826 * 5);
	public static MateriaPrima maltaCrystal = new MateriaPrima(0,1739130434.7826 * 5);
	public static MateriaPrima maltaChocolate = new MateriaPrima(0,1739130434.7826 * 5);
	public static MateriaPrima cebadaTostada = new MateriaPrima(0,1739130434.7826 * 5);
	public static MateriaPrima lupuloPerle = new MateriaPrima(0,1739130434.7826 * 5);
	public static MateriaPrima lupuloTettnanger = new MateriaPrima(0,1739130434.7826 * 5);
	public static MateriaPrima lupuloCentennial = new MateriaPrima(0,1739130434.7826 * 5);
	public static MateriaPrima levaduraAle = new MateriaPrima(0,1739130434.7826 * 5);
	public static MateriaPrima levaduraLager = new MateriaPrima(0,1739130434.7826 * 5);

	
	public static  int maxcapacidad;
	public static  double maxmaltaPilsner;
	public static  double maxmaltaCaramelo;
	public static  double maxmaltaBasePalida;
	public static  double maxmaltaMunich;
	public static  double maxmaltaNegra;
	public static  double maxmaltaCrystal;
	public static  double maxmaltaChocolate;
	public static  double maxcebadaTostada;
	public static  double maxlupuloPerle;
	public static  double maxlupuloTettnanger;
	public static  double maxlupuloCentennial;
	public static  double maxlevaduraAle;
	public static  double maxlevaduraLager;



	public int getCapacidad() {
		return capacidad;
	}
	public static void setCapacidad (int capacidad) {
		AlmacenMMPP.capacidad = capacidad;
	}
	public static int getMaxcapacidad() {
		return maxcapacidad;
	}
	public static void setMaxcapacidad(int maxcapacidad) {
		AlmacenMMPP.maxcapacidad = maxcapacidad;
	}
	
	public static double getMaltaPilsner() {
		return maltaPilsner;
    }
	public static void setMaltaPilsner(double maltaPilsner) {
       if(maxmaltaPilsner>=maltaPilsner) AlmacenMMPP.maltaPilsner = maltaPilsner;
    }
    public static double getMaltaCaramelo() {
    		return maltaCaramelo;
    }
    public static void setMaltaCaramelo(double maltaCaramelo) {
    		if(maxmaltaCaramelo>=maltaCaramelo)AlmacenMMPP.maltaCaramelo = maltaCaramelo;
    }
    public static double getMaltaBasePalida() {
    		return maltaBasePalida;
    }
    public static void setMaltaBasePalida(double maltaBasePalida) {
    		if(maxmaltaBasePalida>=maltaBasePalida) AlmacenMMPP.maltaBasePalida = maltaBasePalida;
    }
    public static double getMaltaMunich() {
             return maltaMunich;
     }
     public static void setMaltaMunich(double maltaMunich) {
    	if(maxmaltaMunich>=maltaMunich) AlmacenMMPP.maltaMunich = maltaMunich;
     }
     public static double getMaltaNegra() {
             return maltaNegra;
     }
     public static void setMaltaNegra(double maltaNegra) {
    	if(maxmaltaNegra>=maltaNegra) AlmacenMMPP.maltaNegra = maltaNegra;
     }
     public static double getMaltaCrystal() {
             return maltaCrystal;
     }
     public static void setMaltaCrystal(double maltaCrystal) {
    	if(maxmaltaCrystal>=maltaCrystal) AlmacenMMPP.maltaCrystal = maltaCrystal;
     }
     public static double getMaltaChocolate() {
             return maltaChocolate;
     }
     public static void setMaltaChocolate(double maltaChocolate) {
    	if(maxmaltaChocolate>=maltaChocolate) AlmacenMMPP.maltaChocolate = maltaChocolate;
     }
     public static double getCebadaTostada() {
             return cebadaTostada;
     }
     public static void setCebadaTostada(double cebadaTostada) {
    	if(maxcebadaTostada>=cebadaTostada) AlmacenMMPP.cebadaTostada = cebadaTostada;
     }
     public static double getLupuloPerle() {
             return lupuloPerle;
     }
     public static void setLupuloPerle(double lupuloPerle) {
    	if(maxlupuloPerle>=lupuloPerle) AlmacenMMPP.lupuloPerle = lupuloPerle;
     }
     public static double getLupuloTettnanger() {
             return lupuloTettnanger;
     }
     public static void setLupuloTettnanger(double lupuloTettnanger) {
    	if(maxlupuloTettnanger>=lupuloTettnanger) AlmacenMMPP.lupuloTettnanger = lupuloTettnanger;
     }
     public static double getLupuloCentennial() {
             return lupuloCentennial;
     }
     public static void setLupuloCentennial(double lupuloCentennial) {
    	 if(maxlupuloCentennial>=lupuloCentennial) AlmacenMMPP.lupuloCentennial = lupuloCentennial;
     }
     public static double getLevaduraAle() {
             return levaduraAle;
     }
     public static void setLevaduraAle(double levaduraAle) {
    	AlmacenMMPP.levaduraAle = levaduraAle;
     }
     public static double getLevaduraLager() {
             return levaduraLager;
     }
     public static void setLevaduraLager(double levaduraLager) {
    	 if(maxlevaduraLager>=levaduraLager) AlmacenMMPP.levaduraLager = levaduraLager;
     }
		

	
	/*
	 * Constructor inicial que "llena" el almac�n de materias primas
	 * teniendo en cuente que tiene capacidad para 5 lotes de producci�n
	 * de 10.000 hl cada uno.
	 */
	public AlmacenMMPP() {
		maxcapacidad = 3714286 * 1000; //gr
		maxmaltaCaramelo = 217391304.3478 * 5;
		maxmaltaBasePalida = 2621426571.428571 * 5;
		maxmaltaCrystal = 6666666.6666667 * 5;
		maxmaltaMunich = 619047619.047619 * 5;
		maxcebadaTostada = 214285714.2857143 * 5;
		maxmaltaNegra = 107142857.1428571 * 5;
		maxmaltaChocolate = 57142857.14285714 * 5;
		maxlupuloPerle = 9523809.523809524 * 5;
		maxlupuloCentennial = 33333333.33333333 * 5;
	}

	public static int getId() {
		return id;
	}
	

}
