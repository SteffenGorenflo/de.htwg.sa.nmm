package controller;

import static org.junit.Assert.*;

import java.awt.Color;

import model.Grid;
import model.Player;
import model.Token;

import org.junit.Before;
import org.junit.Test;

public class TestNmmController {

	NmmController controller;
	
	@Before
	public void init() {
		Player playerOne = new Player("Player One", Color.BLACK);
		Player playerTwo = new Player("Player Two", Color.WHITE);
		Grid model = new Grid(playerOne, playerTwo);
		controller = new NmmController(model);
	}
	
	@Test
	public void testNmmController() {
		Player playerOne = new Player("Player One", Color.BLACK);
		Player playerTwo = new Player("Player Two", Color.WHITE);
		Grid model = new Grid(playerOne, playerTwo);
		controller = new NmmController(model);
	}

	@Test
	public void testExit() {
		
	}

	@Test
	public void testRestart() {
		controller.restart();		
	}

	@Test
	public void testPickToken() {
		assertFalse(controller.pickToken(0, 0));
	}

	@Test
	public void testSetToken() {
		controller.setToken(0, 0);
		assertFalse(controller.pickToken(0, 0));
	}

	@Test
	public void testMoveToken() {
		assertFalse(controller.moveToken(0, 0, 1, 1));
	}

	@Test
	public void testGetStatus() {
		assertTrue(controller.getStatus().equals("Nine Men's Morris"));
	}

	@Test
	public void testGetToken() {
		Token t = controller.getToken(0, 0);
		assertTrue(t == null);
	}

	@Test
	public void testValid() {
		assertFalse(controller.valid(20, 42));
		assertTrue(controller.valid(0, 0));
	}

}
