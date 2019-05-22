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
import equipo6.otros.*;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;
import equipo8.model.GeneradorQR2;


	@Controller
	@SpringBootApplication
	public class FabricaController {
		
	
        
		@Scope("request")
        @RequestMapping("/comprobar")
        @ResponseBody
        public String comprobar(HttpServletResponse response,@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
                Model model) throws Throwable { 
			int existe=1;
			Actor actor = new Actor("3",null,3);
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
            
            HashMap<String, Double> lista = com.controller.StockController.getStockPedidoFabrica(idOrden);
            Actor actor = new Actor("3",null,3);
            String tipo;
            int kilosPedidos;
            JsonObject obj = new JsonObject();
            
            tipo = com.controller.StockController.buscarTipoCerveza(idOrden);
            kilosPedidos = com.controller.StockController.buscarCantidadCerveza(idOrden);
            
            String res="";
            String mensaje="";
            boolean bien=true;
            BlockchainServices bl= new BlockchainServices();
            
          /*  if(lista.get("maltaBasePalida")!=null && lista.get("maltaMunich")!= null && lista.get("cebadaTostada")!=null && lista.get("maltaNegra")!=null &&
            		lista.get("maltaCrystal")!=null && lista.get("maltaChocolate")!=null && lista.get("maltaCaramelo")!=null && lista.get("lupuloCentennial")!=null &&lista.get("levaduraAle")!=null
            		&& lista.get("maltaPilsner")!=null &&lista.get("lupuloPerle")!=null && lista.get("lupuloTettnanger")!=null &&lista.get("levaduraLager")!=null) {
            */
            
            //LAS CANTIDADES ESTAN EN GRAMOS
            if(tipo=="stout") {
                if(lista.get("maltaBasePalida") >= (261*kilosPedidos) && lista.get("maltaMunich") >= (61*kilosPedidos) && lista.get("cebadaTostada") >= 21*kilosPedidos &&
                        lista.get("maltaNegra") >= 10*kilosPedidos && lista.get("maltaCrystal") >=6*kilosPedidos && lista.get("maltaChocolate") >= 5*kilosPedidos &&
                        lista.get("maltaCaramelo") >= 4*kilosPedidos && lista.get("lupuloCentennial") >= 3*kilosPedidos && lista.get("levaduraAle") >= 1*kilosPedidos ) {
                
                   Lote lote=Principal.crearLote("stout");
           		   lote.setQr(GeneradorQR2.generadorQR(idOrden));
                   bl.guardarOrden(lote);
                    com.controller.StockController.setCantidadLote(actor,lote,idOrden);
                    
                }
                else {
                    bien=false;
                }
                
            }
            else if(tipo=="pilsner") {
                if(lista.get("maltaPilsner") >= 173*kilosPedidos && lista.get("maltaCaramelo") >= 21*kilosPedidos && lista.get("lupuloPerle") >= 1*kilosPedidos &&
                        lista.get("lupuloTettnanger") >= 2*kilosPedidos && lista.get("levaduraLager") >= 1*kilosPedidos) {
                    Lote lote=Principal.crearLote("pilsner");
            		lote.setQr(GeneradorQR2.generadorQR(idOrden));
                    bl.guardarOrden(lote);
                    com.controller.StockController.setCantidadLote(actor,lote,idOrden);
                }
                else {
                    bien=false;
                }
            }
            else {
            	bien=false;
            }
            //}
            if(bien) {
            	mensaje="mensajeOK";
            	res="Hay stock suficiente";
            }
            else {
            	mensaje="mensajeNOOK";
            	res="No hay stock suficiente";
            }
            obj.addProperty(mensaje, res.toString());
            return obj.toString();
        }
        
		
		@Scope("request")
		@RequestMapping("/llegadaALaFabrica")
		@ResponseBody
		public static String llegadaALaFabrica(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el n�mero de lote introducido en la vista
										Model model) 
						throws Exception, NotInDatabaseException {
            Actor actor = new Actor("3",null,3);
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
			if(introducido == 3000 ) {
				esta=true;
				fecha=" Fecha de llegada = 22/5/2019.";
			}
			if(!esta) fecha="El lote "+introducido+" no esta en el almacen";
			obj.addProperty("fechaInicio", fecha.toString());
			return obj.toString();
		}
		
		@Scope("request")
		@RequestMapping("/molienda")
		@ResponseBody
		public static String molienda(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el n�mero de lote introducido en la vista
										Model model) 
						throws Exception, NotInDatabaseException {
			Actor actor = new Actor("3",null,3);
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
			if(introducido == 3000 ) {
				esta=true;
				fechaInicio=" Fecha de inicio de fase molienda = 22/5/2019.";
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
										//comprueba el n�mero de lote introducido en la vista
										Model model) 
						throws Exception, NotInDatabaseException {
			Actor actor = new Actor("3",null,3);
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
			if(!esta) fechaFin="Este lote aun no se ha cocinado";
			obj.addProperty("fechaInicio", fechaFin.toString());
			return obj.toString();
		}
		
		@Scope("request")
		@RequestMapping("/fermentacion")
		@ResponseBody
		public static String fermentacion(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el n�mero de lote introducido en la vista
										Model model) 
						throws Exception, NotInDatabaseException {
			Actor actor = new Actor("3",null,3);
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
			if(!esta) fechaFin="Este lote aun no se ha fermentado";
			obj.addProperty("fechaInicio", fechaFin.toString());
			return obj.toString();
		}
		
		
		
		@Scope("request")
		@RequestMapping("/embotellado")
		@ResponseBody
		public static String embotellado(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el n�mero de lote introducido en la vista
										Model model) 
						throws Exception, NotInDatabaseException {
			Actor actor = new Actor("3",null,3);
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
			if(!esta) fechaFin="Este lote aun no se ha embotellado";

			obj.addProperty("fechaInicio", fechaFin.toString());
			return obj.toString();
		}
		
	/*	@Scope("request")
		@RequestMapping("/salidaDeLaFabrica")
		@ResponseBody
		public static String salidaDeLaFabrica(HttpServletResponse response,
										@RequestParam(name="numLoteIntroducido", required=true) String numLoteIntroducido, 
										//comprueba el n�mero de lote introducido en la vista
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
		public static String cocinandoCadena(HttpServletResponse response, Model model) 
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
		public static String fermentandoCadena(HttpServletResponse response, Model model) 
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
		public static String embotellandoCadena(HttpServletResponse response, Model model) 
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
		
		
		@Scope("request")
		@RequestMapping("/ponerTabla")
		@ResponseBody
		public static String ponerTabla(HttpServletResponse response, Model model) 
						throws Throwable {
			JsonObject obj = new JsonObject();
			String s = "";
			Actor actor = new Actor("3",null,3);
			LinkedList<StockLote> lista = com.controller.StockController.getListaLotes(actor);
			Lote l1 = Principal.crearLote("pilsner");
            BlockchainServices bl= new BlockchainServices();
            bl.guardarOrden(l1);
            com.controller.StockController.setCantidadLote(actor,l1,3001);
			s+="Lote: 3000 - Fase molienda. Llegó el día : 22/5/2019.";
			if(lista!=null) {
			for(int i = 0; i<lista.size(); i++) {
				s+="Lote: "+lista.get(i).getLote().getIdBd()+"  "+Principal.comprobarFase(lista.get(i).getLote().getIdBd());
			}}
			obj.addProperty("s", s.toString());
			return obj.toString();
			
		}
		
		     @Scope("request")
	       @RequestMapping("/fabricaComenzarProduccion")
	       @ResponseBody
	       public void fabricaComenzarProduccion(HttpServletResponse response,
	                                             @RequestParam(name="idOrden", required=true) String idOrden,
	                                             Model model) throws Throwable {
				Actor actor = new Actor("3",null,3);
	            int orden = Integer.parseInt(idOrden);
	            BlockchainServices bl= new BlockchainServices();
	            OrdenTrazabilidad ot = bl.getOrden(orden); //devuelve un objeto de tipo OrdenTrazabilidad
	            Productos prod = ot.getProductosPedidos(); //aqu� tienes las cantidades de materias primas
	            int cantLotes=0;
	            boolean pilsner=false;
	            if (prod.getCant_malta_base_palida()>0){
	                cantLotes = prod.getCant_malta_base_palida()/261;
	            } else if (prod.getCant_malta_pilsner()>0) {
	                cantLotes = prod.getCant_malta_pilsner()/173;
	                pilsner=true;
	            }

	            if (!pilsner &&
	                prod.getCant_malta_base_palida()>=(261*cantLotes) &&
	                prod.getCant_malta_munich()>=(61*cantLotes) &&
	                prod.getCant_cebada_tostada()>=(21*cantLotes) &&
	                prod.getCant_malta_negra()>=(10*cantLotes) &&
	                prod.getCant_malta_crystal()>=(6*cantLotes) &&
	                prod.getCant_malta_chocolate()>=(5*cantLotes) &&
	                prod.getCant_lupulo_centennial()>=(3*cantLotes) &&
	                prod.getCant_levadura_ale()>=(1*cantLotes)) {
	                    Lote lote=Principal.crearLote("stout");
	                    bl.guardarOrden(lote);
	                    com.controller.StockController.setCantidadLote(actor,lote,orden);

	            } else if (pilsner &&
	                prod.getCant_malta_pilsner()>=(173*cantLotes) &&
	                prod.getCant_malta_caramelo()>=(21*cantLotes) &&
	                prod.getCant_lupulo_perle()>=(1*cantLotes) &&
	                prod.getCant_lupulo_tettnanger()>=(2*cantLotes) &&
	                prod.getCant_levadura_lager()>=(1*cantLotes)) {
	                    Lote lote=Principal.crearLote("pilsner");
	                    bl.guardarOrden(lote);
	                    com.controller.StockController.setCantidadLote(actor,lote,orden);
	            }

	            
	        }
		
		
		
		
		
}
