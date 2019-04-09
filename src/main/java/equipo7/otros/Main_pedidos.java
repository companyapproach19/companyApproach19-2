package equipo7.otros;
import equipo7.model.OrdenTrazabilidad;
public class Main_pedidos {
	   public OrdenTrazabilidad OrdenTrazabilidad;
	   public int codorigen; //Código del tipo de Actor que crea la petición
	   public int  coddestino; //Código del tipo de Actor que recibe la petición
	   
	   //Crea un objeto OrdenTrazabilidad con los datos del json
	   public Main_pedidos(String pedido) {
		   DescodificadorJson este=new DescodificadorJson();
		   OrdenTrazabilidad=este.DescodificadorJson(pedido);
	   }
	   //Se crearan los objetos de las ordenes de los actores de origen y destino del pedido
	   //Crea el objeto del actor de origen y este creara el de su destinatario
	   //Devuelve el objeto de la orden del actor de origen
	   public Orden crear_pedido () { 
		  sacarCodigoOrigenDestino();
		  Orden devolver = new Orden();
			switch(codorigen) {
			case 1:
				devolver = new CooperativaOrdenes();
				break;
			case 2:
				devolver = new FabricaOrdenes();
				break;
			case 3:
				devolver = new RetailerOrdenes();
				break;
			case 4:
				devolver = new TiendaOrdenes();
				break;
			default:
				System.out.println("Error en el codigo de notificacion");
				break;
			}
			devolver.crearPedido();
			return devolver;

	}	    
	
	//El sistema verifica que el pedido sea valido
	//De tal manera que un pedido de Fábrica a Agricultor no se puede dar
	public boolean verificar_pedido() {
		boolean valido=false; 
		sacarCodigoOrigenDestino();
		if((codorigen-coddestino)==1) {valido=true;} 
		return valido;
	}
	
	public void sacarCodigoOrigenDestino() {
		codorigen = OrdenTrazabilidad.getActorOrigen().getTipoActor();
		coddestino = OrdenTrazabilidad.getActorDestino().getTipoActor();
	} 
}