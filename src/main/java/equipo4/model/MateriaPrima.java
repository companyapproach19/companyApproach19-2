package equipo4.model;

public class MateriaPrima {
	private static String nombre;
	private static int id ;
	private static double capacidad;

	public MateriaPrima(String nombre, int id) {
		this.nombre=nombre;
		this.id=id;
		this.capacidad=0;
	}
	public MateriaPrima(String nombre) {
		this.nombre=nombre;
		//id de alberto
		this.id=metodosCompany.idMateriaPrima();
		this.capacidad=0;
	}

	public static String getNombre() {
		return nombre;
	}

	public static void setNombre(String nombre) {
		MateriaPrima.nombre = nombre;
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		MateriaPrima.id = id;
	}
}
