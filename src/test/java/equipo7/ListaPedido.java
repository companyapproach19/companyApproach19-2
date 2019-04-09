package equipo7;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import equipo6.model.Actor;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.*;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ListaPedido {//objeto 
  
	private ListaPedidos lista ;
	Integer meter; 
	private ArrayList<Integer> l;
   @Before
   public void initialize() {//creo el objeto
	   lista =new ListaPedidos();  
	   l = new  ArrayList<Integer>();
   }  

   @Parameterized.Parameters
   public static Collection primeNumbers() {
	   //codigos
	   //0->4 para Productor, Cooperativa, Transportista, Fabrica y Retailer
	   //Fabrica:
	   //Actor("PepitoF","MARIPOSA","pepito@gmail.com",2)
	   //Tienda:
	   // Actor("JuanT","PUERTA","juan@gmail.com",2)
	   //Actor("AnaT","Gracias","ana@gmail.com",2)
	   //Retailer:
	   //Actor("RebecaR","Atención","rebe@gmail.com",2)
	   //Cooperativa:
	   //Actor("MariaC","Cuidado con la temperatura","maria@gmail.com",2)
	   //Agricultores::
	   //Actor("Ricardo"," ","ricardo@gmail.com",2)
	   //Transportista
	   //Actor("Correos","",correaos@gmail.com)
      return Arrays.asList(new Object[][] {
    	  /*
    	   * 
    	   * 
    	   * Aqui bien
    	   */
         {} ,{},{},{},{},{} ,{},{},{},{}
         /*
          * 
          * aqui mal
          */ });
   }
   
   // This test will run 4 times since we have 5 parameters defined
   @Test
   public void testget() {//mejorar salida  
	   boolean pasado=false ;
	   int cont=0;
	   String correcto ="";
	   while (cont<12){//numero de elementos a meter
		   if(checkList()) {pasado=true;} //assertEquals(lista.GetArrayList(),l); 
		   assertTrue(pasado); 
	   meter=(int)(Math.random()*(75-25+1)+25);//metemos elemento entre 75 y 25

	   if(checkLast()) {pasado=true;}// assertEquals("Hubo un error",checkLast(),false);
	   
	   assertTrue(pasado);
	   cont++;}
	   System.out.println(l.toString());
   } 
/*
 * 
 * Funciones que comprueban
 */
   public boolean checkLast() {//compueba get tal correcta
	   lista.anyadePedido(meter); 
	   l.add(meter);
	   int[] a =lista.getListaIDs();
	   return (a[a.length-1]==meter);
   }
   
   public boolean checkList() { //comprueba que la funcion anyadirsea correca
	   return (false/*CORREGIDlista.GetArrayList().equals(l)*/);
   }
 
}