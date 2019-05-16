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

public class Prediccion {
	static ArrayList<OrdenTrazabilidad> agricultor = new ArrayList<OrdenTrazabilidad> (); 
	static ArrayList<OrdenTrazabilidad> cooperativa = new ArrayList<OrdenTrazabilidad> ();
	static ArrayList<OrdenTrazabilidad> fabrica = new ArrayList<OrdenTrazabilidad> ();




	public static void split () throws SQLException, ClassNotFoundException { //metodo que separa en actores
		ArrayList<OrdenTrazabilidad> list=metodosCompany.extraerTodasLasOrdenes(); //lista que nos pasan con todas las ordenes
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getActorOrigen().getTipoActor() == 1  ) { //materias p
				agricultor.add(list.get(i));
			}
			if(list.get(i).getActorOrigen().getTipoActor() == 3  ) { //mp
				cooperativa.add(list.get(i));
			}
			if(list.get(i).getActorOrigen().getTipoActor() == 4  ) { //fabrica y retailer; lotes
				fabrica.add(list.get(i));
			}
		}
	}
	//añadir ademas if(list.get(i).getActorOrigen().getTipoActor() == 4 && menosDeUnAno(list.get(i)) )
	/*public static Boolean menosDeUnAno(OrdenTrazabilidad ord) {
        Calendar hoy = Calendar.getInstance();
        Date fecha2 =  new Date();
        fecha2= ord.getFecha();
        long tiempo = (hoy.getTimeInMillis() - fecha2.getTime() );
        int tiempoPar = (int) tiempo;
        if(tiempoPar > 31536000) {
            return false;
        }else {
            return true;
        }
        }
*/

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
		return matriz;

		// al final nos queda una matriz de 12 filas mes x 13 columnas tipo materia con la cantidad total de ese tipo/mes en cada posicion
	}
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
        return matriz;
	}
	public static int[] algoritmoPrediccion( int[][] matriz) {
		int[] totalAnual= new int[13];
		int[] prediccion= new int[13];
		Date today = new Date(); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int month = cal.get(Calendar.MONTH)+1;
		for (int i=0; i<matriz[0].length; i++) {
			for(int j=0; j<12; j++) {
				totalAnual[i]= totalAnual[i] + matriz[j][i];
			}
			totalAnual[i]=totalAnual[i]/12;
			int pred = (matriz[month][i] + totalAnual[i])/2;
			prediccion[i] = pred;
		}
		return prediccion;
	}
	public static int[][] reordenacion(int[][] matriz) {
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
}