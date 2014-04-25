package model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class TestField {

	Field f = new Field(0,0);
		
	@Test
	public void testField() {
			// not in here	
	}

	@Test
	public void testGrid() {
		assertTrue(f.grid() == 0);
	}

	@Test
	public void testIndex() {
		assertTrue(f.index() == 0);
	}

	@Test
	public void testGetToken() {
		assertTrue(f.getToken() == null);
		f.setToken(new Token(new Player("myPlayer", Color.GREEN)));
		assertTrue(f.getToken() != null);
	}

	@Test
	public void testHasToken() {
		assertFalse(f.hasToken());
		f.setToken(new Token(new Player("myPlayer", Color.GREEN)));
		assertTrue(f.hasToken());		
	}

	@Test
	public void testSetToken() {
		Token t = new Token(new Player("x", Color.RED));
		assertTrue(f.setToken(t));
		Token t2 = new Token(new Player("y", Color.GRAY));
		assertFalse(f.setToken(t2));		
	}

	@Test
	public void testNeighbour() {
		Field f07 = new Field(0,7);
		Field f17 = new Field(1,7);
		Field f27 = new Field(2,7);
		Field f00 = new Field(0,0);
		Field f01 = new Field(0,1);		
		Field f10 = new Field(1,0);
		
		assertTrue(f07.neighbour(f00));		
		assertFalse(f00.neighbour(f10));
		assertFalse(f07.neighbour(f01));
		assertFalse(f27.neighbour(f07));
		assertTrue(f17.neighbour(f07));
	}

	@Test
	public void testSameIndex() {
		Field other = new Field(1,0);
		assertTrue(f.sameIndex(other));
		other = new Field(1,1);
		assertFalse(f.sameIndex(other));
	}

	@Test
	public void testIndexNeighbour() {
		Field other = new Field(1,0);
		assertFalse(f.indexNeighbour(other));	
		other = new Field(0,7);
		assertTrue(f.indexNeighbour(other));		
	}

	@Test
	public void testSameGrid() {
		Field other = new Field(0,1);
		assertTrue(f.sameGrid(other));
		other = new Field(1,0);
		assertFalse(f.sameGrid(other));
	}

	@Test
	public void testGridNeighour() {
		Field other = new Field(1,0);
		assertTrue(f.gridNeighour(other));
		other = new Field(2,0);
		assertFalse(f.gridNeighour(other));
	}

	@Test
	public void testCorner() {
		assertTrue(f.corner());
		Field other = new Field(0,1);
		assertFalse(other.corner());
	}

	@Test
	public void testEqualsObject() {
		Field other = new Field(0,0);
		assertTrue(f.equals(other));
		other = new Field(2,2);
		assertFalse(f.equals(other));
		Object o = new Object();
		assertFalse(f.equals(o));
	}

}
