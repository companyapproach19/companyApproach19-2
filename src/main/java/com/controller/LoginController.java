package com.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import equipo6.model.Actor;
import equipo6.otros.UsuariosService;


@Controller
@SpringBootApplication
public class LoginController {
	

	@Scope("request")
	@RequestMapping("/loginUser")
	@ResponseBody
	public String loginUser(HttpServletResponse response,
			@RequestParam(name="usuario", required=true) String usuario,
			@RequestParam(name="pwd", required=true) String pwd,
			Model model) throws Exception {
		
		
		//Obtiene los datos del usuario que se quiere logear
		equipo6.model.Actor usuarioLogin = new Actor(usuario,pwd);
		
		//Mandamos a nuestras clases que haga la logica de negocio
		UsuariosService usrv = new UsuariosService(); //Descomentar cuando enlacemos con BBDD
		Actor actorRespuesta = usrv.logUsuario(usuarioLogin);
		
		
		response.addCookie(new Cookie("id", actorRespuesta.getId()));
		
		
		//Devuelve el actor logeado como JSON
		return getJSONFromActor(actorRespuesta);
	}
	
	
	private final String getJSONFromActor(Actor actor) {
		String salida="";
		//si el actor no esta en la cadena nos llega un actor con todos sus campos a null
		String tipo = "";
		System.out.println(actor.getTipoActor());
		switch (actor.getTipoActor()) {
		case 0:
			tipo = "agricultoresInicio";
			break;
		case 1:
			tipo = "cooperativaInicio";
			break;
		case 2:
			tipo = "Transportista";
			break;
		case 3:
			tipo = "Fabrica";
			break;
		case 4:
			tipo = "Retailer";
			break;
		case 5:
			tipo = "tiendaInicio";
			break;
		default:
			tipo="LoginError";
			break;
		}
		salida += "{\n \"nomUsuario\":\"" + actor.getNombreUsuario();
		salida += "\" , \n \"email\":\"" + actor.getEmail();
		salida += "\" , \n \"tipoActor\":\"" + tipo;
		salida += "\" , \n \"redirect\":\""+tipo+".html\"\n}";

		//si no esta el actor en la cadena devolvemos ""
		return salida;
	}
}
