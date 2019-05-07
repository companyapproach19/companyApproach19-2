package com.controller;

	import java.util.Date;
import java.util.HashMap;
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
import equipo4.model.Principal;
import equipo5.model.NotInDatabaseException;
import equipo5.model.StockLote;
import equipo6.model.Actor;


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
				StockLote lote = lista.get(i);en
				if (lote.getIdPedido()==numLoteIntroducido) {
					i=lista.size();
					obj.addProperty("existe",true);
					obj.addProperty("num",1);
//					obj.addProperty("nombreLote", lote.getLote());
				}
			}
			return obj;
		}*/
		
		
		
		
		//comienzaProcesoFabricacion
		
		
		
        
		@Scope("request")
        @RequestMapping("/comprobar")
        @ResponseBody
        public String comprobar(HttpServletResponse response,@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
                Model model) throws Throwable { 
			int existe=1;
			Actor actor = new Actor(null,null,null,3);
			JsonObject obj = new JsonObject();
			int introducido = Integer.parseInt(numLoteIntroducido);
			LinkedList<StockLote> lista = com.controller.StockController.getListaLotes(actor);
			for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==introducido) {
					existe=0;
					i=lista.size();
				}
			}
			obj.addProperty("existe", existe);
			return obj.toString();
		}
            
		
        @Scope("request")
        @RequestMapping("/comienzaProcesoFabricacion")
        @ResponseBody
        public String comienzaProcesoFabricacion(HttpServletResponse response,
                @RequestParam(name="pedido", required=true) String pedido,
                @RequestParam(name="orden", required=true) String orden,
                Model model) throws Throwable {
            
            int idPedido = Integer.parseInt(pedido);
            int idOrden = Integer.parseInt(orden);
            
            HashMap<String, Double> lista = com.controller.StockController.getStockPedidoFabrica(idPedido);
            Actor actor = new Actor(null,null,null,3);
            String tipo;
            int kilosPedidos;
            JsonObject objOK = new JsonObject();
            JsonObject objNOOK = new JsonObject();
            
            tipo = com.controller.StockController.buscarTipoCerveza(idPedido);
            kilosPedidos = com.controller.StockController.buscarCantidadCerveza(idPedido);
            
            //LAS CANTIDADES ESTAN EN GRAMOS
            if(tipo=="stout") {
                if(lista.get("maltaBasePalida") >= 261*kilosPedidos && lista.get("maltaMunich") >= 61*kilosPedidos && lista.get("cebadaTostada") >= 21*kilosPedidos &&
                        lista.get("maltaNegra") >= 10*kilosPedidos && lista.get("maltaCrystal") >=6*kilosPedidos && lista.get("maltaChocolate") >= 5*kilosPedidos &&
                        lista.get("maltaCaramelo") >= 4*kilosPedidos && lista.get("lupuloCentennial") >= 3*kilosPedidos && lista.get("levaduraAle") >= 1*kilosPedidos ) {
                    objOK.addProperty("mensajeOK", "Hay stock suficiente");
                   Lote lote=Principal.crearLote("stout");
                    com.controller.StockController.setCantidadLote(actor,lote,idOrden);
                    
                    return objOK.toString();
                }
                else {
                    objNOOK.addProperty("mensajeNOOK", "No hay stock suficiente");
                    return objNOOK.toString();
                }
                
            }
            else if(tipo=="pilsner") {
                if(lista.get("maltaPilsner") >= 173*kilosPedidos && lista.get("maltaCaramelo") >= 21*kilosPedidos && lista.get("lupuloPerle") >= 1*kilosPedidos &&
                        lista.get("lupuloTettnanger") >= 2*kilosPedidos && lista.get("levaduraLager") >= 1*kilosPedidos) {
                    objOK.addProperty("mensajeOK", "Hay stock suficiente");
                    Lote lote=Principal.crearLote("pilsner");
                    com.controller.StockController.setCantidadLote(actor,lote,idOrden);
                    return objOK.toString();
                }
                else {
                    objNOOK.addProperty("mensajeNOOK", "No hay stock suficiente");
                    return objNOOK.toString();
                }
                
            }
            else {
                objNOOK.addProperty("mensajeNOOK", "Ese tipo no es válido");
                return objNOOK.toString();
            }
            
            
        }
        
		
		@Scope("request")
		@RequestMapping("/llegadaALaFabrica")
		@ResponseBody
		public static String llegadaALaFabrica(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception, NotInDatabaseException {
            Actor actor = new Actor(null,null,null,3);
			JsonObject obj = new JsonObject();
			int introducido = Integer.parseInt(numLoteIntroducido);
			LinkedList<StockLote> lista = com.controller.StockController.getListaLotes(actor);
			String fecha="";
			boolean esta=false;
			for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				
				if (lote1.getIdPedido()==introducido) {
					esta=true;
					Lote lote2=lote1.getLote();
					Date fechaLlegada = lote2.getFecha_inicio();
					fecha = fechaLlegada.getDate() + "/" + fechaLlegada.getMonth() + "/" + fechaLlegada.getYear();
					i=lista.size();
				}
			}
			if(!esta) fecha="El lote "+introducido+" no está en el almacen";
			obj.addProperty("fechaInicio", fecha.toString());
			return obj.toString();
		}
		
		@Scope("request")
		@RequestMapping("/molienda")
		@ResponseBody
		public static String molienda(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception, NotInDatabaseException {
			Actor actor = new Actor(null,null,null,3);
			JsonObject obj = new JsonObject();
			boolean esta=false;
			int introducido = Integer.parseInt(numLoteIntroducido);
			LinkedList<StockLote> lista = com.controller.StockController.getListaLotes(actor);
			String fechaInicio="";
			for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==introducido) {
					esta=true;
					Lote lote2 = lote1.getLote();
					if(lote2.isMolido()){
						Date fecha = lote2.getFecha_molido();
						fechaInicio = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getYear();
					}
				}
			}
			if(!esta) fechaInicio="Este lote aun no se ha molido";
			obj.addProperty("fechaInicio", fechaInicio.toString());
			return obj.toString();
		}
		@Scope("request")
		@RequestMapping("/coccion")
		@ResponseBody
		public static String coccion(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception, NotInDatabaseException {
			Actor actor = new Actor(null,null,null,3);
			JsonObject obj = new JsonObject();
			boolean esta=false;
			int introducido = Integer.parseInt(numLoteIntroducido);
			LinkedList<StockLote> lista = com.controller.StockController.getListaLotes(actor);
			String fechaFin="";
			for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==introducido) {
					esta=true;
					Lote lote2 = lote1.getLote();
					if(lote2.isCocido()){
						Date fecha = lote2.getFecha_cocido();
						fechaFin = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getYear();
					}
				}
			}
			if(!esta) fechaFin="Este lote aun no se ha molido";
			obj.addProperty("fechaInicio", fechaFin.toString());
			return obj.toString();
		}
		
		@Scope("request")
		@RequestMapping("/fermentacion")
		@ResponseBody
		public static String fermentacion(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception, NotInDatabaseException {
			Actor actor = new Actor(null,null,null,3);
			JsonObject obj = new JsonObject();
			int introducido = Integer.parseInt(numLoteIntroducido);
			LinkedList<StockLote> lista = com.controller.StockController.getListaLotes(actor);
			String fechaFin="";
			boolean esta=false;
			for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==introducido) {
					esta=true;
					Lote lote2 = lote1.getLote();
					if(lote2.isFermentado()){
						Date fecha = lote2.getFecha_fermentado();
						fechaFin = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getYear() + "\n Densidad = "+Principal.calcularDensidad() + " , no hace falta segunda fermentacion";
						
					}
				}				
			}
			if(!esta) fechaFin="Este lote aun no se ha molido";
			obj.addProperty("fechaInicio", fechaFin.toString());
			return obj.toString();
		}
		
		
		
		@Scope("request")
		@RequestMapping("/embotellado")
		@ResponseBody
		public static String embotellado(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception, NotInDatabaseException {
			Actor actor = new Actor(null,null,null,3);
			JsonObject obj = new JsonObject();
			boolean esta=false;
			int introducido = Integer.parseInt(numLoteIntroducido);
			LinkedList<StockLote> lista = com.controller.StockController.getListaLotes(actor);
			String fechaFin="";
			for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==introducido) {
					esta=true;
					Lote lote2 = lote1.getLote();
					if(lote2.isEmbotellado()){
						Date fecha = lote2.getFecha_embotellado();
						fechaFin = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getYear();
					}
				}				
			}
			if(!esta) fechaFin="Este lote aun no se ha molido";

			obj.addProperty("fechaInicio", fechaFin.toString());
			return obj.toString();
		}
		
	/*	@Scope("request")
		@RequestMapping("/salidaDeLaFabrica")
		@ResponseBody
		public static String salidaDeLaFabrica(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el número de lote introducido en la vista
										Model model) 
						throws Exception {
			JsonObject obj = new JsonObject();
			obj.addProperty("fechaFin", "");
			int introducido = Integer.parseInt(numLoteIntroducido);

			//ya hemos comprobado que la lista contiene al lote deseado
			//HashMap<String,Double> lista = getStockPedidoFabrica(introducido);
		for (int i=0; i<lista.size(); i++) {
				StockLote lote1 = lista.get(i);
				if (lote1.getIdPedido()==numLoteIntroducido) {
					Lote lote2 = lote1.getLote();
					Date fechaSalida = lote2.getFecha_final();
					String fecha = fechaSalida.getDate() + "/" + fechaSalida.getMonth() + "/" + fechaSalida.getYear();
					obj.addProperty("fechaInicio", fecha);
				}
			}
			obj.addProperty("fechaInicio", "fechaqueyoheescrito6");

			return obj.toString();
		}*/
	
		
		@Scope("request")
		@RequestMapping("/moliendaCadena")
		@ResponseBody
		public static String moliendaCadena(HttpServletResponse response, Model model) 
						throws Exception, NotInDatabaseException {
			JsonObject obj = new JsonObject();
			String info="";

			Principal.actualizarLista();
			if(Principal.getMoliendo().isEmpty()) {
				info= "No hay lotes en la fase de molienda";
			}
			else {
				Lote l = Principal.getMoliendo().get(0);
				info= "Lote moliendo numero: "+l.getIdBd()+"\n Fecha de llegada a fabrica: "+l.getFecha_inicio();
			}
			obj.addProperty("info", info.toString());
			return obj.toString();
		}
		
		@Scope("request")
		@RequestMapping("/cocinandoCadena")
		@ResponseBody
		public static String cocinandoCadena(HttpServletResponse response,@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, Model model) 
						throws Exception, NotInDatabaseException {
			JsonObject obj = new JsonObject();
			String info="";

			Principal.actualizarLista();
			if(Principal.getCociendo().isEmpty()) {
				info= "No hay lotes en la fase de cocinado";
			}
			else {
				Lote l = Principal.getCociendo().get(0);
				info= "Lote cocinando numero: "+l.getIdBd()+"\n Fecha de llegada a fabrica: "+l.getFecha_inicio();
			}
			obj.addProperty("info", info.toString());
			return obj.toString();
		}
		
		@Scope("request")
		@RequestMapping("/fermentandoCadena")
		@ResponseBody
		public static String fermentandoCadena(HttpServletResponse response,@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, Model model) 
						throws Exception, NotInDatabaseException {
			JsonObject obj = new JsonObject();
			String info="";

			Principal.actualizarLista();
			if(Principal.getFermentando().isEmpty()) {
				info= "No hay lotes en la fase de fermentacion";
			}
			else {
				Lote l = Principal.getFermentando().get(0);
				info= "Lote fermentando numero: "+l.getIdBd()+"\n Fecha de llegada a fabrica: "+l.getFecha_inicio();
			}
			obj.addProperty("info", info.toString());

			return obj.toString();
		}
		
		
		/*@Scope("request")
		@RequestMapping("/segundaFermentandoCadena")
		@ResponseBody
		public static String segundaFermentandoCadena(HttpServletResponse response,@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, Model model) 
						throws Exception, NotInDatabaseException {
			JsonObject obj = new JsonObject();
			String info= "No hay lotes en la fase de segunda fermentacion";
				obj.addProperty("info", info.toString());
			return obj.toString();
		}*/
		
		
		@Scope("request")
		@RequestMapping("/embotellandoCadena")
		@ResponseBody
		public static String embotellandoCadena(HttpServletResponse response,@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, Model model) 
						throws Exception, NotInDatabaseException {
			JsonObject obj = new JsonObject();
			String info="";

			Principal.actualizarLista();
			if(Principal.getEmbotellando().isEmpty()) {
				info= "No hay lotes en la fase de embotellado";
			}
			else {
				Lote l = Principal.getEmbotellando().get(0);
				info= "Lote embotellando numero: "+l.getIdBd()+"\n Fecha de llegada a fabrica: "+l.getFecha_inicio();
			}
			obj.addProperty("info", info.toString());

			return obj.toString();
		}
		
	
		
		
		
		
		
}