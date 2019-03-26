package equipo7.otros;
import equipo7.model.OrdenTrazabilidad;
public class Main_pedidos {
	   public static OrdenTrazabilidad OrdenTrazabilidad; 
	   public static int codorigen;//
	   public static int  coddestino;//  	
	   
	   public Main_pedidos(String pedido) {
		   DescodificadorJson este=new DescodificadorJson();
		   OrdenTrazabilidad=este.DescodificadorJson(pedido);
	   }
	  
	   public Orden crear_pedido () { 
		  sacarCodigoOrigenDestino();
		  Orden devolver = new Orden(OrdenTrazabilidad);
			switch(codorigen) {
			case 2:
				devolver = new CooperativaOrdenes(OrdenTrazabilidad);
				break;
			case 3:
				devolver = new FabricaOrdenes(OrdenTrazabilidad);
				break;
			case 4:
				devolver = new RetailerOrdenes(OrdenTrazabilidad);
				break;
			case 5:
				devolver = new TiendaOrdenes(OrdenTrazabilidad);
				break;
			}
			devolver.crearPedido();
			return devolver;
	}	    
	
	public static boolean verificar_pedido() {
		//System.out.println("---->El sistema verifica que el pedido sea correcto" );
		boolean valido=false; 
		sacarCodigoOrigenDestino();
		if((codorigen-coddestino)==1) {valido=true;} 
		return valido;
	}
	
	public static void sacarCodigoOrigenDestino() {
		
		codorigen = OrdenTrazabilidad.getActorOrigen().getTipoActor();
		coddestino = OrdenTrazabilidad.getDestinatario().getTipoActor();
	} 
}
