package equipo9;

import java.util.ArrayList;
import java.time.Instant;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.time.*;
import equipo5.dao.metodosCompany;
import equipo5.model.Cooperativa;
import equipo5.model.Fabrica;
import equipo5.model.Agricultor;
import equipo7.model.Productos;
import equipo5.model.Retailer;
import equipo7.model.OrdenTrazabilidad;
import equipo6.model.Actor;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

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

public class Prediccion {
	static ArrayList<OrdenTrazabilidad> agricultor = new ArrayList<OrdenTrazabilidad> (); 
	static ArrayList<OrdenTrazabilidad> cooperativa = new ArrayList<OrdenTrazabilidad> ();
	static ArrayList<OrdenTrazabilidad> fabrica = new ArrayList<OrdenTrazabilidad> ();




	public static void split () throws SQLException, ClassNotFoundException { //metodo que separa en actores
		ArrayList<OrdenTrazabilidad> list=metodosCompany.extraerTodasLasOrdenes(); //lista que nos pasan con todas las ordenes
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getActorOrigen().getTipoActor() == 1  && menosDeUnAno(list.get(i)) ) { //materias p
				agricultor.add(list.get(i));
			}
			if(list.get(i).getActorOrigen().getTipoActor() == 3 && menosDeUnAno(list.get(i))  ) { //mp
				cooperativa.add(list.get(i));
			}
			if(list.get(i).getActorOrigen().getTipoActor() == 4 && menosDeUnAno(list.get(i)) ) { //fabrica y retailer; lotes
				fabrica.add(list.get(i));
			}
		}
	} 
	
	/*public static ArrayList<OrdenTrazabilidad> splitActor (Actor actor) throws NoExisteException, SQLException, ClassNotFoundException  { //metodo que separa en actores
		Prediccion.split();
		switch (actor.getTipoActor()) {
		case 1: return Prediccion.agricultor;
		case 3: return Prediccion.cooperativa;
		case 4: return Prediccion.fabrica;
		default: throw NoExisteException();
		}
		}
	*/
	
	public static boolean menosDeUnAno (OrdenTrazabilidad orden) {
	       java.util.Date fechaActual = new Date(119, 5, 17);
	       switch(fechaActual.getYear() - orden.getFecha().getYear() ) {
	       case 1:
	           if(fechaActual.getMonth() <= orden.getFecha().getMonth()) {
	               return true;
	           }
	           else {
	               return false;
	           }
	       case 0:
	           return true;
	       default:
	           return false;
	       }    
	   }
	   
	/*@Scope("request")
    @RequestMapping("localHost:5000/comprobar")
    @ResponseBody
    public static String metodon (Model model) throws SQLException, ClassNotFoundException{
		return Prediccion.metodo1(Prediccion.split()).toString();
	}
	*/
////cambiado para que devuelva una matriz en lugar de json
	public static int[][] metodo1 (ArrayList<OrdenTrazabilidad> list){            // METODO TOCHO
		//ArrayList<Productos> list2 = new ArrayList<Productos>();	
		int[] [] matriz = new int [12][13]; 
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 13; j++) {
				matriz[i][j] = 0;
			}
		}

		for(int i = 0; i < list.size(); i++) {
			switch(list.get(i).getFecha().getMonth()) {
			case 0: //enero
				for(int j = 0; j < 13; j++) {
					matriz[0][j] = matriz[0][j] + list.get(i).getProductosPedidos().getCantMateriasPrimas()[j];
				}		
				continue;
			case 1: //febrero
				for(int j = 0; j < 13; j++) {
					matriz[1][j] = matriz[1][j] + list.get(i).getProductosPedidos().getCantMateriasPrimas()[j];
				}
				continue;
			case 2: //marzo
				for(int j = 0; j < 13; j++) {
					matriz[2][j] = matriz[2][j] + list.get(i).getProductosPedidos().getCantMateriasPrimas()[j];
				}
				continue;
			case 3: //abril
				for(int j = 0; j < 13; j++) {
					matriz[3][j] = matriz[3][j] + list.get(i).getProductosPedidos().getCantMateriasPrimas()[j];
				}
				continue;
			case 4: //mayo
				for(int j = 0; j < 13; j++) {
					matriz[4][j] = matriz[4][j] + list.get(i).getProductosPedidos().getCantMateriasPrimas()[j];
				}
				continue;
			case 5: //junio
				for(int j = 0; j < 13; j++) {
					matriz[5][j] = matriz[5][j] + list.get(i).getProductosPedidos().getCantMateriasPrimas()[j];
				}
				continue;
			case 6: //julio
				for(int j = 0; j < 13; j++) {
					matriz[6][j] = matriz[6][j] + list.get(i).getProductosPedidos().getCantMateriasPrimas()[j];
				}
				continue;
			case 7: //agosto
				for(int j = 0; j < 13; j++) {
					matriz[7][j] = matriz[7][j] + list.get(i).getProductosPedidos().getCantMateriasPrimas()[j];
				}
				continue;
			case 8: //septiembre
				for(int j = 0; j < 13; j++) {
					matriz[8][j] = matriz[8][j] + list.get(i).getProductosPedidos().getCantMateriasPrimas()[j];
				}
				continue;
			case 9: //octubre
				for(int j = 0; j < 13; j++) {
					matriz[9][j] = matriz[9][j] + list.get(i).getProductosPedidos().getCantMateriasPrimas()[j];
				}
				continue;
			case 10: //noviembre
				for(int j = 0; j < 13; j++) {
					matriz[10][j] = matriz[10][j] + list.get(i).getProductosPedidos().getCantMateriasPrimas()[j];
				}
				continue;
			case 11: //diciembre
				for(int j = 0; j < 13; j++) {
					matriz[11][j] = matriz[11][j] + list.get(i).getProductosPedidos().getCantMateriasPrimas()[j];
				}
				continue;
			}

		}
		return (Prediccion.reordenacionM(matriz));

		// al final nos queda una matriz de 12 filas mes x 13 columnas tipo materia con la cantidad total de ese tipo/mes en cada posicion
	}
	//cambiado para que devuelva una matriz en lugar de json
	public static int[][] metodo2 (ArrayList<OrdenTrazabilidad> list){            // METODO TOCHO 2
        int[] [] matriz = new int [12][2];
        for(int i = 0; i < 12; i++) {
            for(int j = 0; j < 2; j++) {
                matriz[i][j] = 0;
            }
        }

        for(int i = 0; i < list.size(); i++) {
            switch(list.get(i).getFecha().getMonth()) {
            case 0: //enero
                for(int j = 0; j < 2; j++) {
                    matriz[0][j] = matriz[0][j] + list.get(i).getProductosPedidos().getCantLotes()[j];
                }        
                continue;
            case 1: //febrero
                for(int j = 0; j < 2; j++) {
                    matriz[1][j] = matriz[1][j] + list.get(i).getProductosPedidos().getCantLotes()[j];
                }
                continue;
            case 2: //marzo
                for(int j = 0; j < 2; j++) {
                    matriz[2][j] = matriz[2][j] + list.get(i).getProductosPedidos().getCantLotes()[j];
                }
                continue;
            case 3: //abril
                for(int j = 0; j < 2; j++) {
                    matriz[3][j] = matriz[3][j] + list.get(i).getProductosPedidos().getCantLotes()[j];
                }
                continue;
            case 4: //mayo
                for(int j = 0; j < 2; j++) {
                    matriz[4][j] = matriz[4][j] + list.get(i).getProductosPedidos().getCantLotes()[j];
                }
                continue;
            case 5: //junio
                for(int j = 0; j < 2; j++) {
                    matriz[5][j] = matriz[5][j] + list.get(i).getProductosPedidos().getCantLotes()[j];
                }
                continue;
            case 6: //julio
                for(int j = 0; j < 2; j++) {
                    matriz[6][j] = matriz[6][j] + list.get(i).getProductosPedidos().getCantLotes()[j];
                }
                continue;
            case 7: //agosto
                for(int j = 0; j < 2; j++) {
                    matriz[7][j] = matriz[7][j] + list.get(i).getProductosPedidos().getCantLotes()[j];
                }
                continue;
            case 8: //septiembre
                for(int j = 0; j < 2; j++) {
                    matriz[8][j] = matriz[8][j] + list.get(i).getProductosPedidos().getCantLotes()[j];
                }
                continue;
            case 9: //octubre
                for(int j = 0; j < 2; j++) {
                    matriz[9][j] = matriz[9][j] + list.get(i).getProductosPedidos().getCantLotes()[j];
                }
                continue;
            case 10: //noviembre
                for(int j = 0; j < 2; j++) {
                    matriz[10][j] = matriz[10][j] + list.get(i).getProductosPedidos().getCantLotes()[j];
                }
                continue;
            case 11: //diciembre
                for(int j = 0; j < 2; j++) {
                    matriz[11][j] = matriz[11][j] + list.get(i).getProductosPedidos().getCantLotes()[j];
                }
                continue;
            }
        }
        return (Prediccion.reordenacionL(matriz));
	}
	public static int[] algoritmoPrediccion( int[][] matriz) {
		int[] totalAnual= new int[13];
		int[] prediccion= new int[13];
		Date today = new Date(); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		for (int i=0; i<matriz[0].length; i++) {
			for(int j=0; j<12; j++) {
				totalAnual[i]= totalAnual[i] + matriz[j][i];
			}
			totalAnual[i]=totalAnual[i]/12;
			int pred = (matriz[0][i] + totalAnual[i])/2;
			prediccion[i] = pred;
		}
		return prediccion;
	}
	public static int[][] reordenacionM(int[][] matriz) {
		Date today = new Date(); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int month = cal.get(Calendar.MONTH);
		int cont1 = 11;
		int cont2= 0;
		int[][] reor = new int[12][13];
		for(int i=month; i>=0; i--) {
			for(int j=0; j<matriz[0].length; j++) {
				reor[cont1][j]=matriz[i][j];
			}
			cont1--;
		}
		for(int i=month+1; i<12 ;i++) {
			for(int j=0; j<matriz[0].length ;j++) {
				reor[cont2][j]=matriz[i][j];
			}
			cont2++;
		}
		return reor;
		}
	public static int[][] reordenacionL(int[][] matriz) {
		Date today = new Date(); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int month = cal.get(Calendar.MONTH);
		int cont1 = 11;
		int cont2= 0;
		int[][] reor = new int[12][2];
		for(int i=month; i>=0; i--) {
			for(int j=0; j<matriz[0].length; j++) {
				reor[cont1][j]=matriz[i][j];
			}
			cont1--;
		}
		for(int i=month+1; i<12 ;i++) {
			for(int j=0; j<matriz[0].length ;j++) {
				reor[cont2][j]=matriz[i][j];
			}
			cont2++;
		}
		return reor;
		}

    
    
    
	public static JsonObject matPrimas( int[][] matrix) {
	       String[] mes = {"junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre", "enero", "febrero", "marzo", "abril", "mayo"};
	       String[] materias = {"Malta base palida", "Malta munich", "Malta negra", "Malta crystal", "Malta chocolate", "Malta caramelo", "Malta pilsner", "Cebada tostada", "Lupulo centennial", "Lupulo perle", "Lupulo tettnanger", "Levadura lager", "Levadura ale"};
	       JsonObject res = new JsonObject();
	       for(int i=0; i<matrix[0].length; i++) {
	    	   JsonObject col = new JsonObject();
	    	   for(int j=0; j<matrix.length; j++) {
	    		   col.addProperty(mes[j] , matrix[i][j]);
		   }
	    	   res.add(materias[i], col);
	   }
	       return res;
	}
	   public static JsonObject lotes(int[][] matrix) {
	       JsonObject res = new JsonObject();
	       String[] mes = {"junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre", "enero", "febrero", "marzo", "abril", "mayo"};
	       String [] lotes = {"Lotes stout", "Lotes pilsner"};
	       for(int i = 0; i < matrix.length; i++) {
	           JsonObject col = new JsonObject();
	           for (int j = 0; j < matrix[0].length; j++) {
	               col.addProperty(lotes[j] , matrix[i][j]);
	           }
	           res.add(mes[i] , col);
	       }
	             
	            return res;
	   }

	   public static JsonObject pred (int[] pre) {
	       JsonObject res = new JsonObject();

	       if(pre.length==2) {
	           String [] aux = {"Lotes stout", "Lotes pilsner"};
	           for(int i = 0; i < pre.length; i++) {
	               res.addProperty(aux[i], pre[i]);
	           }
	           return res;
	       }
	       else {
	           String [] aux = {"Malta base palida", "Malta munich", "Malta negra", "Malta crystal", "Malta chocolate", "Malta caramelo", "Malta pilsner", "Cebada tostada", "Lupulo centennial", "Lupulo perle", "Lupulo tettnanger", "Levadura lager", "Levadura ale"};
	           for(int i = 0; i < pre.length; i++) {
	               res.addProperty(aux[i], pre[i]);
	           }
	           return res;
	       }
	   }
	
}