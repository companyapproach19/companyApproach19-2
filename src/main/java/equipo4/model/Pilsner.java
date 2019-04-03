	package equipo4.model;
	public class Pilsner {
		
		public static double maltaPilsner;
		public static double maltaCaramelo;
		public static double lupuloPerle;
		public static double lupuloTettnanger;
		public static double levaduraLager;
		public static double agua;
		public static int id;
		public static boolean molido;
		public static boolean cocinado;
		public static boolean fermentacion1;
		public static boolean fermentacion2;
		public static boolean embotellado;
		
		public Pilsner() {
			agua=11956521.7391;
			maltaPilsner = 1739130434.78;
			AlmacenMMPP.setMaltaPilsner(AlmacenMMPP.getMaltaPilsner()-1739130434.78);
			maltaCaramelo = 217391304.348;
			AlmacenMMPP.setMaltaCaramelo(AlmacenMMPP.getMaltaCaramelo()-217391304.348);
			lupuloPerle = 8695652.17391;
			AlmacenMMPP.setLupuloPerle(AlmacenMMPP.getLupuloPerle()-8695652.17391);
			lupuloTettnanger = 17391304.3478;
			AlmacenMMPP.setLupuloTettnanger(AlmacenMMPP.getLupuloTettnanger()-17391304.3478);
			levaduraLager=287.5;
			AlmacenMMPP.setLevaduraLager(AlmacenMMPP.getLevaduraLager()-287.5);
		}

		public static double getMaltaPilsner() {
			return maltaPilsner;
		}

		public static void setMaltaPilsner(double maltaPilsner) {
			Pilsner.maltaPilsner = maltaPilsner;
		}

		public static double getMaltaCaramelo() {
			return maltaCaramelo;
		}

		public static void setMaltaCaramelo(double maltaCaramelo) {
			Pilsner.maltaCaramelo = maltaCaramelo;
		}

		public static double getLupuloPerle() {
			return lupuloPerle;
		}

		public static void setLupuloPerle(double lupuloPerle) {
			Pilsner.lupuloPerle = lupuloPerle;
		}

		public static double getLupuloTettnanger() {
			return lupuloTettnanger;
		}

		public static void setLupuloTettnanger(double lupuloTettnanger) {
			Pilsner.lupuloTettnanger = lupuloTettnanger;
		}

		public static double getLevaduraLager() {
			return levaduraLager;
		}

		public static void setLevaduraLager(double levaduraLager) {
			Pilsner.levaduraLager = levaduraLager;
		}

		public static double getAgua() {
			return agua;
		}

		public static void setAgua(double agua) {
			Pilsner.agua = agua;
		}

		public static int getId() {
			return id;
		}
			
	}