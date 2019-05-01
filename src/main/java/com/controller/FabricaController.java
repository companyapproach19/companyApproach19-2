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
		public JsonObject llegadaALaFabrica(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) int numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			//obj.addProperty("fechaInicio", "");
			//ya hemos comprobado que la lista contiene al lote deseado
			/*for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==numLoteIntroducido) {
					Lote lote2 = lote1.getLote();
					Date fechaLlegada = lote2.getFecha_inicio();
					String fecha = fechaLlegada.getDate() + "/" + fechaLlegada.getMonth() + "/" + fechaLlegada.getYear();
					obj.addProperty("fechaInicio", fecha);
				}
			}*/
			obj.addProperty("fechaInicio", "fechaqueyoheescrito");
			return obj;
		}
		
		/*@Scope("request")
		@RequestMapping("/molienda")
		@ResponseBody
		public JsonObject molienda(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) int numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			//ya hemos comprobado que la lista contiene al lote deseado
			for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==numLoteIntroducido) {
					Lote lote2 = lote1.getLote();
					obj.addProperty("molido", lote2.isMolido());
					Date fecha = lote2.getFecha_molido();
					String fechaFin = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getYear();
					obj.addProperty("fechaFin", fechaFin);
				}
			}
			return obj;
		}
		
		@Scope("request")
		@RequestMapping("/numLote")
		@ResponseBody
		public JsonObject coccion(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) int numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			//ya hemos comprobado que la lista contiene al lote deseado
			for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==numLoteIntroducido) {
					Lote lote2 = lote1.getLote();
					obj.addProperty("cocido", lote2.isCocido());
					Date fecha = lote2.getFecha_cocido();
					String fechaFin = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getYear();
					obj.addProperty("fechaFin", fechaFin);
				}
			}
			return obj;
		}
		
		@Scope("request")
		@RequestMapping("/fermentacion")
		@ResponseBody
		public JsonObject fermentacion(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) int numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			//ya hemos comprobado que la lista contiene al lote deseado
			for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==numLoteIntroducido) {
					Lote lote2 = lote1.getLote();
					obj.addProperty("fermentado", lote2.isFermentado());
					Date fecha = lote2.getFecha_fermentado2();
					String fechaFin = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getYear();
					obj.addProperty("fechaFin", fechaFin);
				}
			}
			return obj;
		}
		
		@Scope("request")
		@RequestMapping("/embotellado")
		@ResponseBody
		public JsonObject embotellado(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) int numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			//ya hemos comprobado que la lista contiene al lote deseado
			for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==numLoteIntroducido) {
					Lote lote2 = lote1.getLote();
					obj.addProperty("embotellado", lote2.isEmbotellado());
					Date fecha = lote2.getFecha_embotellado();
					String fechaFin = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getYear();
					obj.addProperty("fechaFin", fechaFin);
				}
			}
			return obj;
		}
		
		@Scope("request")
		@RequestMapping("/salidaDeLaFabrica")
		@ResponseBody
		public JsonObject salidaDeLaFabrica(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) int numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			obj.addProperty("fechaFin", "");
			//ya hemos comprobado que la lista contiene al lote deseado
			for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==numLoteIntroducido) {
					Lote lote2 = lote1.getLote();
					Date fechaSalida = lote2.getFecha_final();
					String fecha = fechaSalida.getDate() + "/" + fechaSalida.getMonth() + "/" + fechaSalida.getYear();
					obj.addProperty("fechaFin", fecha);
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
		//comienzaProcesoFabricacion
			
			@Scope("request")
			@RequestMapping("/comienzaProcesoFabricacion")
			@ResponseBody
			public JsonObject comienzaProcesoFabricacion(HttpServletResponse response,
					@RequestParam(name="pedido", required=true) String pedido,  
					@RequestParam(name="orden", required=true) String orden,
					Model model) throws Exception {
				
				int idPedido = Integer.parseInt(pedido);
				int idOrden = Integer.parseInt(orden);
				
				HashMap<String, Integer> lista = new HashMap<>();
				Actor actor = new Actor(null,null,null,3);
				String tipo;
				int kilosPedidos;
				JsonObject objOK = new JsonObject();
				JsonObject objNOOK = new JsonObject();
				
				lista = getStockPedidoFabrica(idPedido);
				tipo = buscarTipoCerveza(idPedido);
				kilosPedidos = buscarCantidadCerveza(idPedido);
				
				//LAS CANTIDADES ESTAN EN GRAMOS
				if(tipo=="stout") {
					if(lista.get(maltaBasePalida) >= 261*kilosPedidos && lista.get(maltaMunich) >= 61*kilosPedidos && lista.get(cebadaTostada) >= 21*kilosPedidos && 
							lista.get(maltaNegra) >= 10*kilosPedidos && lista.get(maltaCrystal) >=6*kilosPedidos && lista.get(maltaChocolate) >= 5*kilosPedidos &&
							lista.get(maltaCaramelo) >= 4*kilosPedidos && lista.get(lupuloCentennial) >= 3*kilosPedidos && lista.get(levaduraAle) >= 1*kilosPedidos ) {
						objOK.addProperty("mensajeOK", "Hay stock suficiente");
						Lote lote = new Lote();
						Date fechaInicio = new Date(System.currentTimeMillis());
						lote.setTipo(tipo);
						lote.setFecha_inicio(fechaInicio);
						setCantidadLote(3,lote);
						
						return objOK;
					}
					else {
						objNOOK.addProperty("mensajeNOOK", "No hay stock suficiente");
						return objNOOK;
					}
					
				}
				else if(tipo=="pilsner") {
					if(lista.get(maltaPilsner) >= 173*kilosPedidos && lista.get(maltaCaramelo) >= 21*kilosPedidos && lista.get(lupuloPerle) >= 1*kilosPedidos && 
							lista.get(lupuloTettnanger) >= 2*kilosPedidos && lista.get(levaduraLager) >= 1*kilosPedidos) {
						objOK.addProperty("mensajeOK", "Hay stock suficiente");
						Lote lote = new Lote();
						Date fechaInicio = new Date(System.currentTimeMillis());
						lote.setTipo(tipo);
						lote.setFecha_inicio(fechaInicio);
						setCantidadLote(3,lote);
						return objOK;
					}
					else {
						objNOOK.addProperty("mensajeNOOK", "No hay stock suficiente");
						return objNOOK;
					}
					
				}
				else {
					objNOOK.addProperty("mensajeNOOK", "Ese tipo no es válido");
					return objNOOK;
				}
				
				
			}
		*/
}
