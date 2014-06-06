package de.htwg.se.nmm.controller.impl;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import de.htwg.se.nmm.controller.impl.NmmController;
import de.htwg.se.nmm.model.impl.Gamefield;
import de.htwg.se.nmm.model.impl.Player;
import de.htwg.se.nmm.model.IPlayer.Status;

public class TestNmmController {

	NmmController controller;
	Gamefield gamefield;
	Player playerOne, playerTwo;
	
	@Before
	public void initTest() {
		gamefield = new Gamefield();
		playerOne = new Player("player1", Color.BLACK);
		playerTwo = new Player("player2", Color.WHITE);
		controller = new NmmController(gamefield, playerOne, playerTwo);
	}
	
	@Test
	public void testNmmController() {
		// see initTest()
	}


	@Test
	public void testRestart() {
		
		controller.setToken(0, 0);
		controller.setToken(0, 1);
		
		while (playerOne.hasToken()) {
			playerOne.takeToken();
		}
		while (playerTwo.hasToken()) {
			playerTwo.takeToken();
		}
			
		controller.restart();
		assertTrue(playerOne.hasToken());
		assertTrue(playerTwo.hasToken());
		assertTrue(controller.getToken(0, 0) == null);
		assertTrue(controller.getToken(0, 1) == null);
		
		// set all tokens we have
		for (int i = 0; i < controller.grids(); i++) {
			for (int k = 1; k < controller.index(); k++) {
				if (controller.currentPlayer().hasToken()) {
					controller.setToken(i, k);
				}
			}
		}
		
		assertFalse(playerOne.hasToken());
		assertFalse(playerTwo.hasToken());
		
		controller.restart();
		
		assertTrue(playerOne.hasToken());
		assertTrue(playerTwo.hasToken());
	}

	@Test
	public void testPickToken() {
		
		// player1 set token
		controller.setToken(0, 0);
		
		// player2 ist now allowed to pick
		controller.pickToken(0, 0);
		
		controller.setToken(1, 0);
		controller.setToken(0, 1);
		controller.setToken(1, 1);
		controller.setToken(2, 0);
		controller.setToken(2, 1);
				
		while (playerTwo.hasToken()) {
			playerTwo.takeToken();
		}	
		
		playerOne.setStatus(Status.PickToken);
		controller.pickToken(1, 0);		
	}

	@Test
	public void testSetToken() {
		assertTrue(controller.currentPlayer() == playerOne);
		controller.setToken(0, 0);		
		assertTrue(controller.currentPlayer() == playerTwo);
		controller.setToken(1, 0);
		controller.setToken(0, 1);
		controller.setToken(1, 1);
		controller.setToken(0, 2);
		controller.setToken(2, 7);
		/* we may not set a token, we have to pick first */
		assertTrue(controller.getToken(2, 7) == null);
	}

	@Test
	public void testMoveToken() {
		// moving not okay
		controller.moveToken(0, 0, 0, 1);
		
		// P1: 0/0
		controller.setToken(0, 0);
		
		// P2: 1/0
		controller.setToken(1, 0);
		
		// P1: 0/1
		playerOne.setStatus(Status.MoveToken);
		controller.moveToken(0, 0, 0, 1);		
		
		// P2: 1/0 1/1
		controller.setToken(1, 1);
		
		// P1: 0/1 0/0
		playerOne.setStatus(Status.SetToken);
		controller.setToken(0, 0);
		
		// P2: 1/0 1/1 1/3
		controller.setToken(1, 3);
		
		// P1: 0/1 0/0 0/5
		controller.setToken(0, 5);
		
		// PlayerTwo move to get a mill
		playerTwo.setStatus(Status.MoveToken);
		controller.moveToken(1, 3, 1, 2);		
		assertTrue(controller.currentPlayer() == playerTwo);
	}
	
	@Test
	public void testGetCurrentPlayer() {
		assertTrue(controller.currentPlayer() == playerOne);
		assertTrue(controller.otherPlayer() == playerTwo);
		controller.setToken(0, 0);
		assertTrue(controller.currentPlayer() == playerTwo);
		assertTrue(controller.otherPlayer() == playerOne);
	}

	@Test
	public void testGetStatus() {
		// no assert needed, we don't care
		// how the string looks like
		controller.getStatus();
		//assertTrue(controller.getStatus().equals("Nine Men's Morris"));
		//controller.setToken(0, 0);			
		//assertTrue(controller.getStatus().equals("set token to (0, 0)"));
	}

	@Test
	public void testGetToken() {		
		assertTrue(controller.getToken(0, 0) == null);
		controller.setToken(0, 0);
		assertTrue(controller.getToken(0, 0) != null);
	}

	@Test
	public void testValid() {
		int grid = 0;
		int index = 0;
		assertTrue(controller.valid(grid, index));
		grid = 2;
		index = 8;
		assertFalse(controller.valid(grid, index));
		grid = 3;
		index = 0;
		assertFalse(controller.valid(grid, index));
		grid = 3;
		index = 8;
		assertFalse(controller.valid(grid, index));
	}
	
	@Test
	public void testGrids() {
		assertTrue(controller.grids() == 3);
	}
	
	@Test
	public void testIndex() {
		assertTrue(controller.index() == 8);
	}
	
	@Test
	public void testUndo() {
		assert(controller.undo() == false);
		controller.setToken(0, 0);
		assert(controller.undo() == true);
	}
}
