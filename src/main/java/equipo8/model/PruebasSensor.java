package equipo8.model;

import java.io.IOException;

public class PruebasSensor {

	public static void main(String[] args) {
		Sensor sensor = new Sensor();
		Registro reg;
		try {
			reg = sensor.registro();
			System.out.print(reg.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
