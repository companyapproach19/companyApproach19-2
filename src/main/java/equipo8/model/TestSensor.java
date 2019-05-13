package equipo8.model;

public class TestSensor {

	public static void main(String[] args) {
		SensorStatic.iniciarTransporte(45, 33);
		SensorStatic.selectPort(7);
		try {
			Thread.sleep(60000);
			SensorStatic.terminar();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
