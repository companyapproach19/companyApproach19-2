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

	import equipo5.model.StockLote;


	@Controller
	@SpringBootApplication
	public class FabricaController {
		//va a dar error hasta que equipo5 suba el código de este sprint a máster
		LinkedList<StockLote> lista = StockController.getListaLotes();
		
		/*
		 * Comprueba si en la lista se encuentra el lote con el número de lote introducido
		 * para devolver información acerca de él
		 */
		@Scope("request")
		@RequestMapping("/numLote")
		@ResponseBody
		public JsonObject obtenerNumLote(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) int numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			/*if(almacen.existeLoteId(numLoteIntroducido)) {
				 obj.addProperty("num",numLoteIntroducido);
				 obj.addProperty("existe",true);
			}
			else {
			 obj.addProperty("existe",false);
			}*/
			
			obj.addProperty("existe", false);
			for (int i=0; i<lista.size(); i++) {
				StockLote lote = lista.get(i);
				if (lote.getIdOrden()==numLoteIntroducido) {
					i=lista.size();
					obj.addProperty("existe",true);
					obj.addProperty("num",1);
//					obj.addProperty("nombreLote", lote.getLote());
				}
			}
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
