package de.htwg.sa.nmm.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.htwg.sa.nmm.model.impl.Field;
import de.htwg.sa.nmm.model.impl.Gamefield;
import de.htwg.sa.nmm.model.impl.Player;
import de.htwg.sa.nmm.model.impl.Token;

public class TestGamefield {

	Gamefield gamefield;
	
	@Before
	public void initTest() {
		gamefield = new Gamefield(new Player("P1", IToken.Color.WHITE),
				new Player("P2", IToken.Color.BLACK));
	}

	@Test
	public void testInit() {
		Player p = new Player("name", IToken.Color.BLACK);
		IToken t = p.takeToken();
		Field f = gamefield.field(0, 0);
		f.setToken(t);
		assertTrue(gamefield.countToken(p) == 1);
		gamefield.init();
		assertTrue(gamefield.countToken(p) == 0);
	}

	@Test
	public void testField() {
		Field f = gamefield.field(0, 0);
		assertTrue(f != null);
	}

	@Test
	public void testCountToken() {
		Player p1 = new Player("p1", IToken.Color.BLACK);
		Player p2 = new Player("p2", IToken.Color.WHITE);
		assertTrue(gamefield.countToken(p1) == 0);
		assertTrue(gamefield.countToken(p2) == 0);
		gamefield.field(0, 0).setToken(p1.takeToken());
		assertTrue(gamefield.countToken(p1) == 1);
		assertTrue(gamefield.countToken(p2) == 0);
		gamefield.field(1, 1).setToken(p2.takeToken());
		assertTrue(gamefield.countToken(p1) == 1);
		assertTrue(gamefield.countToken(p2) == 1);
		gamefield.init();
		assertTrue(gamefield.countToken(p1) == 0);
		assertTrue(gamefield.countToken(p2) == 0);
	}

	@Test
	public void testMill() {		
		Token tBlack = new Token(IToken.Color.BLACK);
		Token tWhite = new Token(IToken.Color.WHITE);
		 
		// create black mill
		gamefield.field(0, 0).setToken(tBlack);
		assertFalse(gamefield.mill(gamefield.field(0, 0)));
		gamefield.field(0, 1).setToken(tBlack);
		assertFalse(gamefield.mill(gamefield.field(0, 1)));
		gamefield.field(0, 2).setToken(tBlack);
		assertTrue(gamefield.mill(gamefield.field(0, 0)));
		assertTrue(gamefield.mill(gamefield.field(0, 1)));
		assertTrue(gamefield.mill(gamefield.field(0, 2)));
		
		// no mill if the three token don't have the same color
		gamefield.field(0,0).setToken(tWhite);
		assertFalse(gamefield.mill(gamefield.field(0, 0)));	
	}

	@Test
	public void testGrids() {
		assertTrue(gamefield.grids() == 3);
	}

	@Test
	public void testIndex() {
		assertTrue(gamefield.index() == 8);
	}

	@Test
	public void testValid() {
		assertTrue(gamefield.valid(0, 0));
		assertFalse(gamefield.valid(5, 0));
		assertFalse(gamefield.valid(0, 10));
		assertFalse(gamefield.valid(5, 10));
	}

}
