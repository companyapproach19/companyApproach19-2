package com.controller;

	import java.util.Date;
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
	//import equipo5.model.StockLote;


	@Controller
	@SpringBootApplication
	public class FabricaController {
		//va a dar error hasta que equipo5 suba el código de este sprint a máster
//		LinkedList<StockLote> lista = StockController.getStockListas();
		
		/*
		 * Comprueba si en la lista se encuentra el lote con el número de lote introducido
		 * para devolver información acerca de él
		 */
		/*@Scope("request")
		@RequestMapping("/numLote")
		@ResponseBody
		public JsonObject obtenerNumLote(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) int numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			
			obj.addProperty("existe", false);
			for (int i=0; i<lista.size(); i++) {
				StockLote lote = lista.get(i);
				if (lote.getIdPedido()==numLoteIntroducido) {
					i=lista.size();
					obj.addProperty("existe",true);
					obj.addProperty("num",1);
//					obj.addProperty("nombreLote", lote.getLote());
				}
			}
			return obj;
		}*/
		
		@Scope("request")
		@RequestMapping("/llegadaALaFabrica")
		@ResponseBody
		public static String llegadaALaFabrica(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			int introducido = Integer.parseInt(numLoteIntroducido);
			//obj.addProperty("fechaInicio", "");
			//ya hemos comprobado que la lista contiene al lote deseado
			/*for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==introducido) {
					Lote lote2 = lote1.getLote();
					Date fechaLlegada = lote2.getFecha_inicio();
					String fecha = fechaLlegada.getDate() + "/" + fechaLlegada.getMonth() + "/" + fechaLlegada.getYear();
					obj.addProperty("fechaInicio", fecha);
				}
			}*/
			obj.addProperty("fechaInicio", "fechaqueyoheescrito1");
			return obj.toString();
		}
		
		@Scope("request")
		@RequestMapping("/molienda")
		@ResponseBody
		public static String molienda(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			int introducido = Integer.parseInt(numLoteIntroducido);
			//ya hemos comprobado que la lista contiene al lote deseado
			/*for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==numLoteIntroducido) {
					Lote lote2 = lote1.getLote();
					obj.addProperty("molido", lote2.isMolido());
					Date fecha = lote2.getFecha_molido();
					String fechaFin = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getYear();
					obj.addProperty("fechaInicio", fechaFin);
				}
			}*/
			obj.addProperty("fechaInicio", "fechaqueyoheescrito2");
			return obj.toString();
		}
		
		@Scope("request")
		@RequestMapping("/numLote")
		@ResponseBody
		public static String coccion(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			int introducido = Integer.parseInt(numLoteIntroducido);
			//ya hemos comprobado que la lista contiene al lote deseado
			/*for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==numLoteIntroducido) {
					Lote lote2 = lote1.getLote();
					obj.addProperty("cocido", lote2.isCocido());
					Date fecha = lote2.getFecha_cocido();
					String fechaInicio = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getYear();
					obj.addProperty("fechaInicio", fechaInicio);
				}
			}*/
			obj.addProperty("fechaInicio", "fechaqueyoheescrito3");
			return obj.toString();
		}
		
		@Scope("request")
		@RequestMapping("/fermentacion")
		@ResponseBody
		public static String fermentacion(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			int introducido = Integer.parseInt(numLoteIntroducido);

			//ya hemos comprobado que la lista contiene al lote deseado
			/*for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==numLoteIntroducido) {
					Lote lote2 = lote1.getLote();
					obj.addProperty("fermentado", lote2.isFermentado());
					Date fecha = lote2.getFecha_fermentado2();
					String fechaFin = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getYear();
					obj.addProperty("fechaInicio", fechaFin);
				}
			}*/
			obj.addProperty("fechaInicio", "fechaqueyoheescrito4");
			return obj.toString();
		}
		
		@Scope("request")
		@RequestMapping("/embotellado")
		@ResponseBody
		public static String embotellado(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			int introducido = Integer.parseInt(numLoteIntroducido);

			//ya hemos comprobado que la lista contiene al lote deseado
		/*	for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==numLoteIntroducido) {
					Lote lote2 = lote1.getLote();
					obj.addProperty("embotellado", lote2.isEmbotellado());
					Date fecha = lote2.getFecha_embotellado();
					String fechaFin = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getYear();
					obj.addProperty("fechaInicio", fechaFin);
				}
			}*/
			obj.addProperty("fechaInicio", "fechaqueyoheescrito5");

			return obj.toString();
		}
		
		@Scope("request")
		@RequestMapping("/salidaDeLaFabrica")
		@ResponseBody
		public static String salidaDeLaFabrica(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			obj.addProperty("fechaFin", "");
			//ya hemos comprobado que la lista contiene al lote deseado
		/*	for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==numLoteIntroducido) {
					Lote lote2 = lote1.getLote();
					Date fechaSalida = lote2.getFecha_final();
					String fecha = fechaSalida.getDate() + "/" + fechaSalida.getMonth() + "/" + fechaSalida.getYear();
					obj.addProperty("fechaInicio", fecha);
				}
			}*/
			obj.addProperty("fechaInicio", "fechaqueyoheescrito6");

			return obj.toString();
		}
	
		
		/*@Scope("request")
		@RequestMapping("/salidaDeLaFabrica")
		@ResponseBody
		public static String moliendaCadena(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			obj.addProperty("fechaFin", "");
			//ya hemos comprobado que la lista contiene al lote deseado
			for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
					Lote lote2 = lote1.getLote();
					Date fechaInicial = lote2.getFecha_inicio();
					Date fechaActual = new Date();
					int tiempo= fechaInicial.getMinutes()-fechaActual.getMinutes();
					switch
					
					
					
					String fecha = fechaSalida.getDate() + "/" + fechaSalida.getMonth() + "/" + fechaSalida.getYear();
					obj.addProperty("fechaInicio", fecha);
				}
			
			obj.addProperty("fechaInicio", "fechaqueyoheescrito");

			return obj.toString();
		}*/
		
		
		
		
		
		
}