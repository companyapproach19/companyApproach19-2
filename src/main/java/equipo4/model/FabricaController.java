package com.controller;


	import java.util.LinkedList;

	import javax.servlet.http.Cookie;
	import javax.servlet.http.HttpServletResponse;

	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import org.springframework.web.bind.annotation.ResponseBody;

	//import com.controller.Lote;
	import com.google.gson.JsonObject;
	import com.google.gson.JsonParser;

import equipo4.model.Lote;
import equipo4.model.Principal;


	@Controller
	@SpringBootApplication
	public class FabricaController extends Thread{
		//LinkedList<Lote> lista = new LinkedList<Lote>();
		//AlmacenLotes almacen = new AlmacenLotes(0, lista);
		
		@Scope("request")
		@RequestMapping("/numLote")
		@ResponseBody
		public JsonObject obtenerNumLote(HttpServletResponse response,
				@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido,
				Model model) throws Exception {
			JsonObject obj = new JsonObject();
			/*if(almacen.existeLoteId(numLoteIntroducido)) {
				 obj.addProperty("num",numLoteIntroducido);
				 obj.addProperty("existe",true);
			}
			else {
			 obj.addProperty("existe",false);
			}*/
			 obj.addProperty("existe",true);
			 obj.addProperty("num",1);

			return obj;
		}
		
		@Scope("request")
		@RequestMapping("/funWait")
		@ResponseBody
		public void funWait(HttpServletResponse response,
				/*@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido,*/
				Model model) throws Exception {
			Thread.sleep(1000);

			;
		}
		
		@Scope("request")
		@RequestMapping("/funWait")
		@ResponseBody
		public void crearLotePilsner(HttpServletResponse response,
				/*@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido,*/
				Model model) throws Exception {
			Lote l = Principal.crearLote("p");
			;
		}
		
		@Scope("request")
		@RequestMapping("/funWait")
		@ResponseBody
		public void crearLoteStout(HttpServletResponse response,
				/*@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido,*/
				Model model) throws Exception {
			Lote l = Principal.crearLote("s");
			;
		}
		
		
		
		
		
		
		
		
		
		
		

}
