package equipo4.model;
import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import equipo5.model.NotInDatabaseException;
public class Test4 {


		@Test
		public void densidad() {
			double d=Principal.calcularDensidad();
			assertTrue(d>=1.1 && d<=1.4);			
		}

		@Test
		public void faseCocinado() throws InterruptedException, ClassNotFoundException, SQLException, NotInDatabaseException {
			Lote l = new Lote();
			Principal.actualizarLista();
			String fase=Principal.comprobarFase(l.getIdBd());
			assertTrue(l.isMolido());
			assertTrue(fase.equals("Fase cocinado."));
		}
		
		@Test
		public void faseMolienda() throws InterruptedException, ClassNotFoundException, SQLException, NotInDatabaseException {
			Lote l = new Lote();
			Principal.actualizarLista();
			String fase=Principal.comprobarFase(l.getIdBd());
			assertTrue(fase.equals("Fase molienda."));
		}
		
		@Test
		public void faseFermentacion() throws InterruptedException, ClassNotFoundException, SQLException, NotInDatabaseException {
			Lote l = new Lote();
			Principal.actualizarLista();
			String fase=Principal.comprobarFase(l.getIdBd());
			assertTrue(l.isMolido());
			assertTrue(l.isCocido());
			assertTrue(fase.equals("Fase fermentacion."));
		}
		
		@Test
		public void faseEmbotellado() throws InterruptedException, ClassNotFoundException, SQLException, NotInDatabaseException {
			Lote l = new Lote();
			Principal.actualizarLista();
			String fase=Principal.comprobarFase(l.getIdBd());
			assertTrue(l.isMolido());
			assertTrue(l.isCocido());
			assertTrue(l.isFermentado());
			assertTrue(fase.equals("Fase embotellado."));
		}
		
	

	}

