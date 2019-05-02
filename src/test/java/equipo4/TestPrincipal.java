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

	@Test
	public void testMetodo1() {
		JsonObject result = new JsonObject();
		result.addProperty("fechaInicio", fecha);
		try {
			a = FabricaController.llegadaALaFabrica(response, "1234", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(result.toString(), a);
	}
	
	@Test
	public void testMetodo2() {
		JsonObject res = new JsonObject();
		res.addProperty("fechaInicio", fecha);
		try {
			a = FabricaController.molienda(response, "1234", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(res.toString(), a);
	}
	
	@Test
	public void testMetodo3() {
		JsonObject res = new JsonObject();
		res.addProperty("fechaInicio", fecha);
		try {
			a = FabricaController.coccion(response, "1234", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(res.toString(), a);
	}
	
	@Test
	public void testMetodo4() {
		JsonObject res = new JsonObject();
		res.addProperty("fechaInicio", fecha);
		try {
			a = FabricaController.fermentacion(response, "1234", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(res.toString(), a);
	}
	
	@Test
	public void testMetodo5() {
		JsonObject res = new JsonObject();
		res.addProperty("fechaInicio", fecha);
		try {
			a = FabricaController.embotellado(response, "1234", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(res.toString(), a);
	}
	
	@Test
	public void testMetodo6() {
		JsonObject res = new JsonObject();
		res.addProperty("fechaInicio", fecha);
		try {
			a = FabricaController.salidaDeLaFabrica(response, "1234", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(res.toString(), a);
	}
}
