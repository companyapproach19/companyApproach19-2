package equipo4;
import java.util.*;
public class Lote {
	public  int idBd;
	public  int code;
	public  String tipo;
	public  LinkedList<String> pedidos;
	private  Date fecha_inicio;
	private  Date fecha_final;

	public Lote(int idBd, Pilsner p, Date ini) {
		this.idBd=idBd;
		this.code=p.getId();
		this.tipo="Pilsner";
		this.pedidos=new LinkedList<String>();
		this.fecha_inicio=ini;
		Date aux = (Date) fecha_inicio.clone();
		aux.setDate(fecha_inicio.getDate()+12);
		this.fecha_final = aux;	
	}
	
	public Lote(int idBd, Stout s, Date ini) {
		this.idBd=idBd;
		this.code=s.getId();
		this.tipo="Stout";
		this.pedidos=new LinkedList<String>();
		this.fecha_inicio=ini;
		Date aux = (Date) fecha_inicio.clone();
		aux.setDate(fecha_inicio.getDate()+12);
		this.fecha_final = aux;	
	}
	
	
	/*@SuppressWarnings({ "deprecation", "static-access" })
	public Lote(Stout name, Date fecha_inicio) {
		Lote.code=name.getId();
		Lote.tipo="Stout";
		Lote.fecha_inicio = fecha_inicio;
		Date aux = (Date) fecha_inicio.clone();
		aux.setDate(fecha_inicio.getDate()+12);
		Lote.fecha_final = aux;
		pedidos = new LinkedList<String>();
	}
	
	@SuppressWarnings({ "deprecation", "static-access" })
	public Lote (Pilsner name, Date fecha_inicio) {
		Lote.code=name.getId();
		Lote.tipo="Pilsner";
		Lote.fecha_inicio = fecha_inicio;
		Date aux = (Date) fecha_inicio.clone();
		aux.setDate(fecha_inicio.getDate()+12);
		Lote.fecha_final = aux;
		pedidos = new LinkedList<String>();
	}*/

  public  LinkedList<String> getPedidos() {
		return pedidos;
	}

	public  void setPedidos(LinkedList<String> pedidos) {
		this.pedidos = pedidos;
	}

	public  int getCode() {
		return code;
	}

	public  void setCode(int code) {
		this.code = code;
	}

	public  String getTipo() {
		return tipo;
	}

	public  void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public  Date getFecha_inicio() {
		return fecha_inicio;
	}

	public  void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	
	public  void setFecha_final(Date fecha_final) {
		this.fecha_final = fecha_final;
	}
	
	public  Date getFecha_final() {
		return this.fecha_final;
	}
}


