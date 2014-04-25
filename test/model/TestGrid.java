package model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class TestGrid {

	Grid grid;
	Player p1,p2;
	
	@Before
	public void init() {
		p1 = new Player("P1", Color.BLACK);
		p2 = new Player("P2", Color.WHITE);
		grid = new Grid(p1,p2);
	}
	
	@Test
	public void testGrid() {
		// see init
	}

	@Test
	public void testInit() {
		grid.init();
	}

	@Test
	public void testGetToken() {
		Token t = grid.getToken(0, 0);
		assertTrue(t == null);
	}

	@Test
	public void testIsEmpty() {
		assertTrue(grid.isEmpty(0, 0));		
		grid.setToken(0, 0);
		assertFalse(grid.isEmpty(0, 0));
	}

	@Test
	public void testSetToken() {
		grid.setCurrentPlayer(p1);
		grid.currentPlayer().setStatus(Player.STATUS_SET);
		assertTrue(grid.setToken(0, 0));
		grid.setCurrentPlayer(p1);
		grid.currentPlayer().setStatus(Player.STATUS_SET);
		assertTrue(grid.setToken(0, 1));
		grid.setCurrentPlayer(p1);
		grid.currentPlayer().setStatus(Player.STATUS_SET);
		assertTrue(grid.setToken(0, 2));
		grid.setCurrentPlayer(p1);
		grid.currentPlayer().setStatus(Player.STATUS_SET);
		assertFalse(grid.setToken(0, 0));
		
		grid.currentPlayer().setStatus(Player.STATUS_MOVE);
		assertFalse(grid.setToken(2, 2));
	}

	@Test
	public void testCountToken() {		
		assertTrue(grid.countToken(p1) == 0);
		grid.setToken(0, 0);
		assertTrue(grid.countToken(p1) == 1);
		grid.setToken(0, 1);
		assertTrue(grid.countToken(p1) == 1);
		grid.setToken(0, 2);
		assertTrue(grid.countToken(p1) == 2);
	}

	@Test
	public void testPickToken() {
		grid.setCurrentPlayer(p1);
		p1.setStatus(Player.STATUS_SET);
		grid.setToken(0, 0);
		grid.setCurrentPlayer(p1);
		p1.setStatus(Player.STATUS_SET);				
		grid.setToken(0, 1);
		grid.setCurrentPlayer(p1);
		p1.setStatus(Player.STATUS_SET);
		grid.setToken(0, 2);
		p1.setStatus(Player.STATUS_PICK);
		assertFalse(grid.pickToken(0, 0));
		assertFalse(grid.pickToken(2, 7));	
		
		grid.setCurrentPlayer(p2);
		p2.setStatus(Player.STATUS_SET);
		grid.setToken(2, 7);
		grid.setCurrentPlayer(p1);
		p1.setStatus(Player.STATUS_PICK);
		assertTrue(grid.pickToken(2, 7));	
	}
	
	@Test
	public void testMoveToken() {
		grid.setCurrentPlayer(p1);
		p1.setStatus(Player.STATUS_PICK);
		
		// you can't set if you are not in status 'move'
		assertFalse(grid.moveToken(0, 0, 0, 0));
		p1.setStatus(Player.STATUS_MOVE);
		assertFalse(grid.moveToken(0, 0, 0, 0));
		
		p1.setStatus(Player.STATUS_SET);
		grid.setToken(0, 0);
		grid.setCurrentPlayer(p1);
		p1.setStatus(Player.STATUS_MOVE);
		assertTrue(grid.moveToken(0, 0, 0, 1));
		
		
		
		
	}

	@Test
	public void testMill() {
		
	}

	@Test
	public void testCurrentPlayer() {
		grid.setCurrentPlayer(p1);
		assertTrue(grid.currentPlayer().equals(p1));		
	}

	@Test
	public void testOtherPlayer() {
		assertTrue(grid.otherPlayer().equals(p2));
		assertFalse(grid.otherPlayer().equals(p1));
	}

	@Test
	public void testGrids() {
		assertTrue(grid.grids() == 3);
	}

	@Test
	public void testIndex() {
		assertTrue(grid.index() == 8);
	}

	@Test
	public void testValid() {
		
	}

}
