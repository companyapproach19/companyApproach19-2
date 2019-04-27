package equipo8;

import java.io.IOException;
import java.sql.SQLException;
import equipo8.model.Sensor;

public class TestSensorRegistro {

	
	//Ejemplo registro temperatura Arduino numero de serie 354325
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		Sensor reg= new Sensor(2);
		reg.crearRegistroCadaXMinutos(999, 999, "datosSensorMiercoles.txt", 1);
		reg.pararCreacionRegistros("datosSensorMiercoles.txt");
	}

}
