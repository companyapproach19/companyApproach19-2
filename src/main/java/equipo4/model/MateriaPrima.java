package equipo4.model;

public class MateriaPrima {
	private static double capacidad;
	private static double maxCapacidad;

	public MateriaPrima(double capacidad, double maxCapacidad) {
		this.capacidad=capacidad;
		this.maxCapacidad=maxCapacidad;
	}

	public static double getCapacidad() {
		return capacidad;
	}

	public static void setCapacidad(double capacidad) {
		MateriaPrima.capacidad = capacidad;
	}

	public static double getMaxCapacidad() {
		return maxCapacidad;
	}

	public static void setMaxCapacidad(double maxCapacidad) {
		MateriaPrima.maxCapacidad = maxCapacidad;
	}
}
