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
	@Test
	public void test1() throws InterruptedException {
		Lote lote= new Lote(new Pilsner(), new Date());
		int result = Principal.moler(lote);
		assertEquals(result, 0);
	}
	@Test
	public void test2() throws InterruptedException {
		int result = Principal.cocinar(new Lote(new Pilsner(), new Date()));
		assertEquals(result, 0);
	}
	@Test
	public void test3() throws InterruptedException {
		Lote lote = new Lote(new Stout(), new Date());
		int result = Principal.cocinar(lote);
		assertEquals(result, 0);
	}
	public void test4() throws InterruptedException {
		Lote lote= new Lote(new Stout(), new Date());
		int result = Principal.moler(lote);
		assertEquals(result, 0);
	}
	
	
	
}
