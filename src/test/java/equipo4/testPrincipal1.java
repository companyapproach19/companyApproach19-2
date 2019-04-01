package equipo4;


import static org.junit.Assert.*;
import java.util.Date;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import equipo4.model.Lote;
import equipo4.model.Pilsner;
import equipo4.model.Principal;
import equipo4.model.Stout;

public class testPrincipal1 {
	
	final String fecha = "1/4/2019"; //modificar antes de ejecutar
	Date date = new Date();
	Lote lote1= new Lote(0, new Pilsner(), date);
	Lote lote2 = new Lote(0, new Stout(), date);
	
	@Test
	public void testFecha() throws InterruptedException {
		String fechaActual = date.getDate() + "/" + date.getMonth() + "/" + date.getYear();
		assertEquals(fecha,fechaActual);
	}
	@Test
	public void testMolienda() throws InterruptedException {
		int result = Principal.moler(lote1);
		assertEquals(result, 0); //correcto
	}
	@Test
	public void testCocinado() throws InterruptedException {
		int result = Principal.cocinar(lote1);
		int result1 = Principal.cocinar(lote2);
		assertEquals(result, 0); //correcto
		assertEquals(result1, 0); //incorrecto
	}
	@Test
	public void testFermentado() throws InterruptedException {
		int result = Principal.fermentar(lote1);
		int result1 = Principal.fermentar(lote2);
		assertEquals(result, 0); //correcto
		assertEquals(result1, 0); //incorrecto
	}
	
	public void testEmbotellado() throws InterruptedException {
		int result = Principal.embotellar(lote1);
		int result1 = Principal.embotellar(lote2);
		assertEquals(result, 0); //correcto
		assertEquals(result1, 0); //incorrecto
	}
	
	
	
}
