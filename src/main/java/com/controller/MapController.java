package com.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import equipo6.model.Actor;
import equipo6.otros.UsuariosService;


@Controller
@SpringBootApplication
public class MapController {
	
	@Scope("request")
	@RequestMapping("/cargarMapaActores")
	@ResponseBody
	public ResponseEntity cargarMapaActores(Model model) {
		String JSON;
		
		
		JSON=placeholderMap();
		
		return ResponseEntity.status(HttpStatus.OK).body(JSON);
	}
	
	private String placeholderMap() {
		String salidaTemporal;
		
		salidaTemporal ="[\n";
		salidaTemporal+="{\n";
		salidaTemporal+="\"nombre\":\"Fabrica A\", \n";
		salidaTemporal+="\"coordLat\":40.4025076 , \n";
		salidaTemporal+="\"coordLon\":-3.8363319 , \n";
		salidaTemporal+="\"stock\": 200 \n";
		salidaTemporal+="},{ \n";
		salidaTemporal+="\"nombre\":\"Fabrica B\", \n";
		salidaTemporal+="\"coordLat\":40.4035076 , \n";
		salidaTemporal+="\"coordLon\":-3.8353319 , \n";
		salidaTemporal+="\"stock\": 100 \n";
		salidaTemporal+="},{ \n";
		salidaTemporal+="\"nombre\":\"Fabrica C\", \n";
		salidaTemporal+="\"coordLat\":40.4030076 , \n";
		salidaTemporal+="\"coordLon\":-3.8343319 , \n";
		salidaTemporal+="\"stock\": 150 \n";
		salidaTemporal+="},{ \n";
		salidaTemporal+="\"nombre\":\"Fabrica D\", \n";
		salidaTemporal+="\"coordLat\":40.4130076 , \n";
		salidaTemporal+="\"coordLon\":-3.8243319 , \n";
		salidaTemporal+="\"stock\": 550 \n";
		salidaTemporal+="}\n";
		salidaTemporal+="]\n";
		
		return salidaTemporal;
	}

}
