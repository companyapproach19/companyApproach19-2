package equipo8.model;

public class PruebasSensorYRegistro {

	public static void main(String[] args) {
		Sensor sensor = new Sensor(54);
		Registro reg1,reg2,reg3;
		try {
			reg1 = sensor.crearRegistro(609,990,"/home/marisol/Desktop/Pruebas/2019-02-03|11:03:23.txt");
			System.out.println(reg1.toString());
			reg2 = sensor.crearRegistro(8869,75,"/home/marisol/Desktop/Pruebas/2019-02-05|11:03:23.txt");
			System.out.println(reg2.toString());
			reg3 = sensor.crearRegistro(1008,7565,"/home/marisol/Desktop/Pruebas/2019-02-04|11:03:23.txt");
			System.out.println(reg3.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
