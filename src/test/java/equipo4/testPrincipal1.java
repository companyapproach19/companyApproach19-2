package equipo4;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.LinkedList;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import iSoftware.AlmacenLotes;
import iSoftware.AlmacenMMPP;
import iSoftware.Lote;
import iSoftware.Pilsner;
import iSoftware.Principal;
import iSoftware.Stout;

public class testPrincipal1 {

	@Test
	public void testAlmacenLotes() throws InterruptedException {
		int idBd = -1;
		Date fechaActual = new Date();
		LinkedList<Lote> lista = new LinkedList<Lote>();
		AlmacenLotes almacen = new AlmacenLotes(idBd, lista);
		assertNotNull(almacen);
		Stout c11 = new Stout();
		Stout c12 = new Stout();
		Stout c13 = new Stout();
		Stout c14 = new Stout();
		Stout c15 = new Stout();
		Stout c16 = new Stout();
		Stout c17 = new Stout();
		Stout c18 = new Stout();
		assertNotNull(c11);
		assertNotNull(c12);
		assertNotNull(c13);
		assertNotNull(c14);
		assertNotNull(c15);
		assertNotNull(c16);
		assertNotNull(c17);
		assertNotNull(c18);

		Pilsner c21 = new Pilsner();
		Pilsner c22 = new Pilsner();
		assertNotNull(c21);
		assertNotNull(c22);

		Lote l1 = new Lote(idBd, c11, fechaActual);
		Lote l2 = new Lote(idBd, c12, fechaActual);
		Lote l3 = new Lote(idBd, c13, fechaActual);
		Lote l4 = new Lote(idBd, c14, fechaActual);
		Lote l5 = new Lote(idBd, c15, fechaActual);
		Lote l6 = new Lote(idBd, c16, fechaActual);
		Lote l7 = new Lote(idBd, c17, fechaActual);
		Lote l8 = new Lote(idBd, c18, fechaActual);
		Lote l21 = new Lote(idBd, c21, fechaActual);
		Lote l22 = new Lote(idBd, c22, fechaActual);

		assertNotNull(l1);
		assertNotNull(l2);
		assertNotNull(l3);
		assertNotNull(l4);
		assertNotNull(l5);
		assertNotNull(l6);
		assertNotNull(l7);
		assertNotNull(l8);
		assertNotNull(l21);
		assertNotNull(l22);

		almacen.almacenarLote(l1);
		almacen.almacenarLote(l2);
		almacen.almacenarLote(l3);
		almacen.almacenarLote(l4);
		almacen.almacenarLote(l5);
		almacen.almacenarLote(l6);
		almacen.almacenarLote(l7);
		almacen.sacarLoteId(4);
		almacen.sacarLoteId(7);
		almacen.almacenarLote(l21);
		almacen.almacenarLote(l22);
		
		assertTrue(almacen.getLista().size() <= almacen.getMaxCapacidad());
		assertTrue(l1.getIdBd() > 0);

		assertTrue(almacen.existeLoteId(3));
		almacen.sacarLoteId(3);
		assertFalse(almacen.existeLoteId(78));
	}

	@Test
	public void testCantidadesStout() throws InterruptedException {
		Stout cerveza = new Stout();

		assertNotNull(cerveza);
		assertTrue(cerveza.getCebadaTostada() - 214285714.286 == 0);
		assertTrue(cerveza.getMaltaBasePalida() - 2619047619.05 == 0);
		assertTrue(cerveza.getAgua() - 11428571.4286 == 0);
		assertTrue(cerveza.getMaltaMunich() - 619047619.048 == 0);
		assertTrue(cerveza.getMaltaNegra() - 107142857.143 == 0);
		assertTrue(cerveza.getMaltaCrystal() - 66666666.6667 == 0);
		assertTrue(cerveza.getMaltaChocolate() - 57142857.1429 == 0);
		assertTrue(cerveza.getMaltaCaramelo() - 42857142.8571 == 0);
		assertTrue(cerveza.getLupuloCentennial() - 33333333.3333 == 0);
		assertTrue(cerveza.getLevaduraAle() - 287.5 == 0);

		cerveza.setAgua(0);
		cerveza.setCebadaTostada(0);
		cerveza.setLevaduraAle(0);
		cerveza.setLupuloCentennial(0);
		cerveza.setMaltaBasePalida(0);
		cerveza.setMaltaCaramelo(0);
		cerveza.setMaltaChocolate(0);
		cerveza.setMaltaCrystal(0);
		cerveza.setMaltaMunich(0);
		cerveza.setMaltaNegra(0);

		assertTrue(cerveza.getCebadaTostada() - 0 == 0);
		assertTrue(cerveza.getMaltaBasePalida() - 0 == 0);
		assertTrue(cerveza.getAgua() - 0 == 0);
		assertTrue(cerveza.getMaltaMunich() - 0 == 0);
		assertTrue(cerveza.getMaltaNegra() - 0 == 0);
		assertTrue(cerveza.getMaltaCrystal() - 0 == 0);
		assertTrue(cerveza.getMaltaChocolate() - 0 == 0);
		assertTrue(cerveza.getMaltaCaramelo() - 0 == 0);
		assertTrue(cerveza.getLupuloCentennial() - 0 == 0);
		assertTrue(cerveza.getLevaduraAle() - 0 == 0);
	}

	@Test
	public void testCantidadesPilsner() throws InterruptedException {
		Pilsner cerveza2 = new Pilsner();
		assertNotNull(cerveza2);
		assertTrue(cerveza2.getLupuloTettnanger() - 17391304.3478 == 0);
		assertTrue(cerveza2.getAgua() - 11956521.7391 == 0);
		assertTrue(cerveza2.getMaltaPilsner() - 1739130434.78 == 0);
		assertTrue(cerveza2.getMaltaCaramelo() - 217391304.348 == 0);
		assertTrue(cerveza2.getLupuloPerle() - 8695652.17391 == 0);
		assertTrue(cerveza2.getLevaduraLager() - 287.5 == 0);

		cerveza2.setAgua(0);
		cerveza2.setLevaduraLager(0);
		cerveza2.setLupuloPerle(0);
		cerveza2.setLupuloTettnanger(0);
		cerveza2.setMaltaCaramelo(0);
		cerveza2.setMaltaPilsner(0);
		cerveza2.setLevaduraLager(0);

		assertTrue(cerveza2.getLupuloTettnanger() - 0 == 0);
		assertTrue(cerveza2.getAgua() - 0 == 0);
		assertTrue(cerveza2.getMaltaPilsner() - 0 == 0);
		assertTrue(cerveza2.getMaltaCaramelo() - 0 == 0);
		assertTrue(cerveza2.getLupuloPerle() - 0 == 0);
		assertTrue(cerveza2.getLevaduraLager() - 0 == 0);

	}
	
	@Test
	public void testFabricacion() throws InterruptedException {
		Date date = new Date();
		Lote lote1 = new Lote(0, new Pilsner(), date);
		Principal.moler(lote1);
		Principal.cocinar(lote1);
		Principal.fermentar(lote1);
		Principal.fermentar2(lote1);
		assertTrue(lote1.isFermentado2());
		Principal.embotellar(lote1);
		assertTrue(lote1.isEmbotellado());
		Date dateFinal=lote1.getFecha_final();
		assertTrue(lote1.getFecha_final().compareTo(lote1.getFecha_inicio())>0);
	}
	
	@Test
	public void testMain() throws InterruptedException {
		Principal.main(null);
	}
	


}