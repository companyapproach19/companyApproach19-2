package equipo8;

import equipo8.model.SensorStatic;

public class TestSensorRegistro {


	//Ejemplo registro temperatura del Lote id 54 en dos trayectos:
	public static void main(String[] args) {
		
		SensorStatic.iniciarTransporte(608, 154);
		SensorStatic.selectPort(7);
		try {
			Thread.sleep(100000);//200 segs
			SensorStatic.terminar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
//		Sensor sensor = new Sensor();
//		sensor.setIdOrdentrazabilidad(143);
//		sensor.setIdPedido(703);
//		sensor.selectPort(7);
//		try {
//			Thread.sleep(100000);//200 segs
//			//Registro reg = sensor.crearRegistro(45, 98);
//			sensor.terminar();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

}
