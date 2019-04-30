package equipo4.model;


import java.sql.SQLException;
import java.util.Date;

import equipo5.dao.metodosCompany;
import equipo6.model.DatosContainer;
public class Lote extends DatosContainer{
	private  int idBd;
	private  Date fecha_inicio;
	private  Date fecha_final;
	private byte[] qr;
	private boolean molido;
	private boolean cocido;
	private boolean fermentado;
	private boolean fermentado2;
	private boolean embotellado;
	private String tipo;
	private Date fecha_molido;
	private Date fecha_cocido;
	private Date fecha_fermentado1;
	private Date fecha_fermentado2;
	private Date fecha_embotellado;
	
	
	public Lote() throws ClassNotFoundException, SQLException {
		this.idBd=metodosCompany.idLote();
		molido=cocido=fermentado=fermentado2=embotellado=false;
	}
	
	
	public Lote(int idBd, Date fecha_inicio, Date fecha_final, byte[] qr, boolean molido, boolean cocido,
			boolean fermentado, boolean fermentado2, boolean embotellado, String tipo, Date fecha_molido,
			Date fecha_cocido, Date fecha_fermentado1, Date fecha_fermentado2, Date fecha_embotellado) {
		super();
		this.idBd = idBd;
		this.fecha_inicio = fecha_inicio;
		this.fecha_final = fecha_final;
		this.qr = qr;
		this.molido = molido;
		this.cocido = cocido;
		this.fermentado = fermentado;
		this.fermentado2 = fermentado2;
		this.embotellado = embotellado;
		this.tipo = tipo;
		this.fecha_molido = fecha_molido;
		this.fecha_cocido = fecha_cocido;
		this.fecha_fermentado1 = fecha_fermentado1;
		this.fecha_fermentado2 = fecha_fermentado2;
		this.fecha_embotellado = fecha_embotellado;
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

	public  void setFecha_inicio(java.util.Date fechaActual) {
		this.fecha_inicio = (Date) fechaActual;
	}
	
	public int getIdBd() {
		return idBd;
	}

	public void setIdBd(int idBd) {
		this.idBd = idBd;
	}

	public  void setFecha_final(Date fechaActual) {
		this.fecha_final = (Date) fechaActual;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFecha_molido() {
		return fecha_molido;
	}

	public void setFecha_molido(java.util.Date fechaActual) {
		this.fecha_molido = fechaActual;
	}

	public Date getFecha_cocido() {
		return fecha_cocido;
	}

	public void setFecha_cocido(Date fecha_cocido) {
		this.fecha_cocido = fecha_cocido;
	}

	public Date getFecha_fermentado1() {
		return fecha_fermentado1;
	}

	public void setFecha_fermentado1(Date fecha_fermentado1) {
		this.fecha_fermentado1 = fecha_fermentado1;
	}

	public Date getFecha_fermentado2() {
		return fecha_fermentado2;
	}

	public void setFecha_fermentado2(Date fecha_fermentado2) {
		this.fecha_fermentado2 = fecha_fermentado2;
	}

	public Date getFecha_embotellado() {
		return fecha_embotellado;
	}

	public void setFecha_embotellado(Date fecha_embotellado) {
		this.fecha_embotellado = fecha_embotellado;
	}

}