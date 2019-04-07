package equipo7;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import equipo6.model.Actor;
import equipo7.model.OrdenTrazabilidad;
import equipo7.model.Productos;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class productos {
	private int id;
    private int cant_malta_palida;
    private int cant_malta_munich;
    private int cant_malta_negra;
    private int cant_malta_crystal;
    private int cant_malta_chocolate;
    private int cant_malta_caramelo;
    private int cant_cebada;
    private int cant_cebada_tostada;
    private int cant_lupulo_centenial;
    private int cant_cajas_stout;
    private int cant_cajas_bisner;
   private Productos m; 
   @Before
   public void initialize() {//creo el objeto
	      m=new Productos( id,  cant_malta_palida,  cant_malta_munich,
                  cant_malta_negra,  cant_malta_crystal,
                  cant_malta_chocolate,  cant_malta_caramelo,
                  cant_cebada,  cant_cebada_tostada,  cant_lupulo_centenial,
                  cant_cajas_stout,  cant_cajas_bisner);  
   }
   // Each parameter should be placed as an argument here
   // Every time runner triggers, it will pass the arguments
   // from parameters we defined in primeNumbers() method
	
   public productos(int id, int cant_malta_palida, int cant_malta_munich,
           int cant_malta_negra, int cant_malta_crystal,
           int cant_malta_chocolate, int cant_malta_caramelo,
           int cant_cebada, int cant_cebada_tostada, int cant_lupulo_centenial,
           int cant_cajas_stout, int cant_cajas_bisner) {
	   this.cant_malta_palida = cant_malta_palida;
       this.cant_malta_munich = cant_malta_munich;
       this.cant_malta_negra = cant_malta_negra;
       this.cant_malta_crystal = cant_malta_crystal;
       this.cant_malta_chocolate = cant_malta_chocolate;
       this.cant_malta_caramelo = cant_malta_caramelo;
       this.cant_cebada = cant_cebada;
       this.cant_cebada_tostada = cant_cebada_tostada;
       this.cant_lupulo_centenial = cant_lupulo_centenial;
       this.cant_cajas_stout = cant_cajas_stout;
       this.cant_cajas_bisner = cant_cajas_bisner;
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
    	  crearaleatorio(),//
    	  crearaleatorio(), crearaleatorio(), crearaleatorio(), crearaleatorio(), crearaleatorio(), crearaleatorio(), crearaleatorio(),});
   }
     public static Object[] crearaleatorio(){
    	 Object[] sol={al(), al(), al(), al(), al(), al(), al(), al(), al(), al(), al(), al()}; 
		return  sol;
   }
     public static int al() {
    	 return (int)(Math.random()*(100-1+1)+0);
     }
     

   // This test will run 4 times since we have 5 parameters defined
   @Test
   public void testget() {//mensajes en caso de error  
      assertEquals(cant_malta_palida, m.getCant_malta_palida()); 
      assertEquals(cant_malta_munich,m.getCant_malta_munich()); 
      assertEquals(cant_malta_negra,m.getCant_malta_negra()); 
      assertEquals(cant_malta_crystal,m.getCant_malta_crystal()); 
      assertEquals(cant_malta_chocolate,m.getCant_malta_chocolate()); 
      assertEquals(cant_malta_caramelo, m.getCant_malta_caramelo()); 
      assertEquals(cant_cebada,m.getCant_cebada()); 
      assertEquals(cant_cebada_tostada,m.getCant_cebada_tostada()); 
      assertEquals(cant_lupulo_centenial,m.getCant_lupulo_centenial()); 
      assertEquals(cant_cajas_stout,m.getCant_cajas_stout()); 
      assertEquals( cant_cajas_bisner,m.getCant_cajas_bisner());
      System.out.println("Probados : ");
   }
   @Test
   public void testset() {//mensajes en caso de error 
	   int id_cambio=0;
	   m.setId(id_cambio);
	   assertEquals(id_cambio,m.getId()); 
   } 
}