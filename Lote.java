package equipo4.model;

import java.util.*;

import equipo5.dao.metodosCompany;
import equipo6.model.DatosContainer;
public class Lote extends DatosContainer{
	public  int idBd;
	public  Date fecha_inicio;
	public  Date fecha_final;
	private byte[] qr;
	public boolean molido;
	public boolean cocido;
	public boolean fermentado;
	public boolean fermentado2;
	public boolean embotellado;
	public String tipo;

	public Lote(int idBd, Date fecha_inicio, String tipo, Date fecha_final,boolean molido, boolean cocido, boolean fermentado, boolean fermentado2, boolean embotellado, byte[] qr) {
		this.idBd= idBd;
		this.fecha_inicio=fecha_inicio;
		this.fecha_final=fecha_final;
		this.molido=molido;
		this.cocido=cocido;
		this.fermentado=fermentado;
		this.fermentado2 = fermentado2;
		this.embotellado=embotellado;
		this.tipo=tipo;
		
	}
	
	public Lote(Pilsner p, Date ini) {
		this.idBd=metodosCompany.idLote();
		this.fecha_inicio=ini;
		Date aux = (Date) fecha_inicio.clone();
		aux.setDate(fecha_inicio.getDate()+12);
		this.fecha_final = aux;	
		molido=cocido=fermentado=fermentado2=embotellado=false;
		this.tipo="pilsner";
	}
	
	public Lote(Stout s, Date ini) {
		this.idBd=metodosCompany.idLote();
		this.fecha_inicio=ini;
		Date aux = (Date) fecha_inicio.clone();
		aux.setDate(fecha_inicio.getDate()+12);
		this.fecha_final = aux;	
		molido=cocido=fermentado=fermentado2=embotellado=false;
		this.tipo="stout";
	}

	public byte[] getQr() {
		return qr;
	}

	public void setQr(byte[] qr) {
		this.qr = qr;
	}


	public  Date getFecha_inicio() {
		return fecha_inicio;
	}

	public  void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	
	public int getIdBd() {
		return idBd;
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
}