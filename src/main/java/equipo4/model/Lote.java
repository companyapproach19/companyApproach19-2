package equipo4.model;

import java.util.*;

import equipo6.model.DatosContainer;
import equipo7.model.OrdenTrazabilidad;
public class Lote extends DatosContainer{
	public  int idBd;
	public int code;
	private  Date fecha_inicio;
	private  Date fecha_final;
	
	private boolean molido;
	private boolean cocido;
	private boolean fermentado;
	private boolean fermentado2;
	private boolean embotellado;

	public Lote(int idBd, Pilsner p, Date ini) {
		this.idBd=idBd;
		this.code=p.getId();
		this.fecha_inicio=ini;
		Date aux = (Date) fecha_inicio.clone();
		aux.setDate(fecha_inicio.getDate()+12);
		this.fecha_final = aux;	
		molido=cocido=fermentado=fermentado2=embotellado=false;
	}
	
	public Lote(int idBd, Stout s, Date ini) {
		this.idBd=idBd;
		this.code=s.getId();
		this.fecha_inicio=ini;
		Date aux = (Date) fecha_inicio.clone();
		aux.setDate(fecha_inicio.getDate()+12);
		this.fecha_final = aux;	
		molido=cocido=fermentado=fermentado2=embotellado=false;
	}

	public  int getCode() {
		return code;
	}

	public  void setCode(int code) {
		this.code = code;
	}

	public  Date getFecha_inicio() {
		return fecha_inicio;
	}

	public  void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	
	public int getIdBd() {
		return OrdenTrazabilidad.getId();
	}

	public void setIdBd(int idBd) {
		this.idBd = idBd;
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

	BlockchainServices bloque = new BlockchainServices();
	       bloque.guardarOrden(Lote);
}
