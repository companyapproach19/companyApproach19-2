package equipo6;

import static org.junit.jupiter.api.Assertions.*;

import java.net.HttpURLConnection;

import org.junit.jupiter.api.Test;

import equipo6.otros.UsuariosService;
import equipo6.model.Actor;
import equipo6.model.CadenaActores;
import equipo6.objetosTemporales.BBDDTemporal;

class TestLogin {
	@Test
	void test1() {
		Actor actor_login;
		UsuariosService us;
		Actor respuesta;
		
		us = new UsuariosService();
		actor_login = new Actor("Agricultor", "password");
		
		try {
			respuesta = us.logUsuario(actor_login);
			if(respuesta.getTipoActor() != -1) 
			{
				System.out.println("actor logeado");
			}
			else 
			{
				System.out.println("\nactor incorrecto");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	void test2() {
		Actor actor_login;
		UsuariosService us;
		Actor respuesta;
		
		us = new UsuariosService();
		actor_login = new Actor("Agricultor", "contraseña_no_valida");
		
		try {
			respuesta = us.logUsuario(actor_login);
			if(respuesta.getTipoActor() != -1) 
			{
				System.out.println("actor logeado");
			}
			else 
			{
				System.out.println("\nactor incorrecto");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	void test3() {
		Actor actor_login;
		UsuariosService us;
		Actor respuesta;
		
		us = new UsuariosService();
		actor_login = new Actor("usuario_no_valido", "password");
		
		try {
			respuesta = us.logUsuario(actor_login);
			if(respuesta.getTipoActor() != -1) 
			{
				System.out.println("actor logeado");
			}
			else 
			{
				System.out.println("\nactor incorrecto");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	void test4() {
		Actor actor_login;
		UsuariosService us;
		Actor respuesta;
		
		us = new UsuariosService();
		actor_login = new Actor("usuario_no_valido", "contraseña_no_valida");

		
		try {
			respuesta = us.logUsuario(actor_login);
			if(respuesta.getTipoActor() != -1) 
			{
				System.out.println("actor logeado");
			}
			else 
			{
				System.out.println("\nactor incorrecto");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
