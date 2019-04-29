package com.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import equipo5.dao.metodosCompany;
import equipo6.model.Actor;
import equipo6.model.CadenaActores;
import equipo6.otros.UsuariosService;


@Controller
@SpringBootApplication
public class MapController {
	
	@Scope("request")
	@RequestMapping("/cargarMapaActores")
	@ResponseBody
	public ResponseEntity cargarMapaActores(Model model) {
		String JSON;
		
		
		JSON=getMapData();
		
		return ResponseEntity.status(HttpStatus.OK).body(JSON);
	}
	
	private String getMapData() {
		try {
			CadenaActores cadena = metodosCompany.extraerCadenaActores();
			String salida="[\n";
			List<Actor> lista = cadena.getlista_actores();
			
			for(int i=0;i<lista.size()-1;i++) {
				salida+=getActorData(lista.get(i));
				salida+=",";
			}
			
			salida+=getActorData(lista.get(lista.size()-1));
			
			salida+="]\n";
			return salida;
		} catch (SQLException e) {return "";}		
	}
	
	//NO SE USA. LO DEJO COMO REFERENCIA
	/*private String placeholderMap() {
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
	}*/
	
	private String getActorData(Actor actor) {
		String s="";
		s+="{ \n";
		s+="\"tipo\":\""+intToStringTipoActor(actor.getTipoActor())+"\", \n";
		s+="\"nombre\":\""+actor.getNombre()+"\", \n";
		s+="\"email\":\""+actor.getEmail()+"\", \n";
		s+="\"coord\":\""+actor.getLocalizacion()+"\", \n";
		s+="\"lat\":\""+getLat(actor.getLocalizacion())+"\", \n";
		s+="\"lon\":\""+getLon(actor.getLocalizacion())+"\" \n";
		s+="} \n";
		return s;
	}
	
	private String intToStringTipoActor(int tipoActor) {
		switch(tipoActor) {
		case 0:
			return "Agricultor";
		case 1:
			return "Cooperativa";
		case 2:
			return "Fabrica";
		case 3:
			return "Transportista";
		case 4:
			return "Frabrica";
		case 5:
			return "Retailer";
		default:
			return "";
		}
	}
	
	private String getLat(String localizacion) {
		String[] p = localizacion.split(";");
		return p[0];
	}
	
	private String getLon(String localizacion) {
		String[] p = localizacion.split(";");
		return p[1];
	}

}
