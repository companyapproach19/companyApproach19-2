package equipo4;
import java.util.*;
public class Lote {
	public  int idBd;
	public  int code;
	public  String tipo;
	public  LinkedList<String> pedidos;
	private  Date fecha_inicio;
	private  Date fecha_final;
	private byte[] qr;
	
	private boolean molido;
	private boolean cocido;
	private boolean fermentado;
	private boolean fermentado2;
	private boolean embotellado;

	public Lote(int idBd, Pilsner p, Date ini) {
		this.idBd=idBd;
		this.code=p.getId();
		this.tipo="Pilsner";
		this.pedidos=new LinkedList<String>();
		this.fecha_inicio=ini;
		Date aux = (Date) fecha_inicio.clone();
		aux.setDate(fecha_inicio.getDate()+12);
		this.fecha_final = aux;	
		molido=cocido=fermentado=fermentado2=embotellado=false;
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
		molido=cocido=fermentado=fermentado2=embotellado=false;
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

	public void generarQR() {
		if (embotellado)
			qr = main.java.equipo8.model.GeneradorQR2.generadorQR(code);
	}
	
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

	public boolean isMolido() {
		return molido;
	}

	public void setMolido(boolean molido) {
		this.molido = molido;
	}

	public boolean isCocido() {
		return cocido;
	}

	public boolean setCocido(boolean cocido) {
		this.cocido = cocido;
		return cocido;
	}

	public boolean isFermentado() {
		return fermentado;
	}

	public boolean setFermentado(boolean fermentado) {
		this.fermentado = fermentado;
		return fermentado;
	}

	public boolean isEmbotellado() {
		return embotellado;
	}

	public boolean setEmbotellado(boolean embotellado) {
		this.embotellado = embotellado;
		return embotellado;
	}

	public boolean isFermentado2() {
		return fermentado2;
	}

	public boolean setFermentado2(boolean fermentado2) {
		this.fermentado2 = fermentado2;
		return fermentado2;
	}
}


