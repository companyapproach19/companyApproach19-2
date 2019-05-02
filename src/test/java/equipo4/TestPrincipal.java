package equipo4;

import org.junit.Test;
import org.springframework.ui.Model;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletResponse;

import com.controller.FabricaController;
import com.google.gson.JsonObject;

public class TestPrincipal {
	
	/**
	 * Variables necesarias en todos los métodos que inicializaré cuando Alberto me aclare el qué
	 */
	HttpServletResponse response = null;
	Model model = null;
	String fecha = "fechaqueyoheescrito";
	String a = null;
	String idLote = "";

	@Test
	public void testFabricaController() {
		
		JsonObject res = new JsonObject();
		
		res.addProperty("fechaInicio", fecha);
		try {
			a = FabricaController.llegadaALaFabrica(response, idLote, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(res.toString(), a);
	
		res = new JsonObject();
		res.addProperty("fechaInicio", fecha);
		try {
			a = FabricaController.molienda(response, idLote, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(res.toString(), a);
	
		res = new JsonObject();
		res.addProperty("fechaInicio", fecha);
		try {
			a = FabricaController.coccion(response, idLote, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(res.toString(), a);
		
		res = new JsonObject();
		res.addProperty("fechaInicio", fecha);
		try {
			a = FabricaController.fermentacion(response, idLote, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(res.toString(), a);
	
		res = new JsonObject();
		res.addProperty("fechaInicio", fecha);
		try {
			a = FabricaController.embotellado(response, idLote, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(res.toString(), a);
	
		res = new JsonObject();
		res.addProperty("fechaInicio", fecha);
		try {
			a = FabricaController.salidaDeLaFabrica(response, idLote, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(res.toString(), a);
	}
}
