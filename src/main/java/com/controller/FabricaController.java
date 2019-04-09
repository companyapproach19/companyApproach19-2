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


	@Controller
	@SpringBootApplication
	public class FabricaController {
		//LinkedList<Lote> lista = new LinkedList<Lote>();
		//AlmacenLotes almacen = new AlmacenLotes(0, lista);
		
		@Scope("request")
		@RequestMapping("/numLote")
		@ResponseBody
		public JsonObject obtenerNumLote(HttpServletResponse response,
				@RequestParam(name="numLoteIntroducido", required=true) int numLoteIntroducido,
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
				@RequestParam(name="numLoteIntroducido", required=true) int numLoteIntroducido,
				Model model) throws Exception {
			Thread.sleep(1000);

			;
		}
		
		
		
		
		
		
		
		

}
