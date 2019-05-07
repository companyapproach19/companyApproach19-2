package equipo8;

import equipo8.model.SensorStatic;

public class TestSensorRegistro {

	//prueba cambio de intervalo
	private static void prueba1() {
		SensorStatic.iniciarTransporte(608, 154);
		try {
			Thread.sleep(100000);//100 segs
			SensorStatic.cambiarIntervalo(3000, 15000);
			Thread.sleep(100000);
			SensorStatic.terminar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//prueba fallo de conexion
	private static void prueba2() {
		SensorStatic.iniciarTransporte(608, 154);
		try {
			Thread.sleep(100000);//100 segs
			System.out.println("Cambio de Intervalo!");
			SensorStatic.cambiarIntervalo(3000, 15000);
			Thread.sleep(100000);
			SensorStatic.terminar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//prueba intervalo corto de tiempo
	private static void prueba3() {
		SensorStatic.iniciarTransporte(608, 154);
		SensorStatic.cambiarIntervalo(3000, 10000);
		try {
			Thread.sleep(100000);//100 segs
			SensorStatic.terminar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void prueba4() {
		SensorStatic.iniciarTransporte(608, 154);
		SensorStatic.cambiarIntervalo((Integer) null, 10000);
		try {
			Thread.sleep(100000);//100 segs
			SensorStatic.terminar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	//Ejemplo registro temperatura del Lote id 54 en dos trayectos:
	public static void main(String[] args) {

		prueba1();
		//prueba2();
		prueba3();
		//prueba4();

	}

}
