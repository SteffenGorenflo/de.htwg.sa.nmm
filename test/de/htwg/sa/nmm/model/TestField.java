package de.htwg.sa.nmm.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.htwg.sa.nmm.model.impl.Field;
import de.htwg.sa.nmm.model.impl.Token;

public class TestField {

	Field field;
	
	@Before
	public void initTest() {
		field = new Field(0, 0);		
	}
	
	@Test
	public void testField() {
		assertTrue(field.grid() == 0);
		assertTrue(field.index() == 0);
		assertFalse(field.hasToken());
	}

	@Test
	public void testGrid() {
		assertTrue(field.grid() == 0);
		field = new Field(0,1);
		assertTrue(field.grid() == 0);
		field = new Field(1,0);
		assertTrue(field.grid() == 1);
	}

	@Test
	public void testIndex() {
		assertTrue(field.index() == 0);
		field = new Field(1,0);
		assertTrue(field.index() == 0);
		field = new Field(0,1);
		assertTrue(field.index() == 1);
	}

	@Test
	public void testGetToken() {
		assertTrue(field.getToken() == null);
		Token t = new Token(IToken.Color.BLACK);
		field.setToken(t);
		assertTrue(field.getToken() == t);
	}

	@Test
	public void testHasToken() {
		assertFalse(field.hasToken());
		Token t = new Token(IToken.Color.BLACK);
		field.setToken(t);
		assertTrue(field.hasToken());
		field.setToken(null);
		assertFalse(field.hasToken());
	}

	@Test
	public void testSetToken() {
		Token t = new Token(IToken.Color.BLACK);
		field.setToken(t);
		assertTrue(field.getToken() == t);
	}

	@Test
	public void testNeighbour() {
		Field f;
						
		f = new Field(0, 1);
		assertTrue(field.neighbour(f));
		
		f = new Field(0, 2);
		assertFalse(field.neighbour(f));
		
		f = new Field(0, 7);
		assertTrue(field.neighbour(f));
		
		f = new Field(1, 0);
		assertFalse(field.neighbour(f));
		
		f = new Field(0, 1);
		Field other = new Field(1, 1);
		assertTrue(f.neighbour(other));
	}

	@Test
	public void testSameIndex() {
		Field f;
		
		// same grid, same index
		f = new Field(0,0);
		assertTrue(field.sameIndex(f));
		
		// other grid, same index
		f = new Field(1,0);
		assertTrue(field.sameIndex(f));
		
		// same grid, other index
		f = new Field(0,1);
		assertFalse(field.sameIndex(f));
		
		// other grid, other index
		f = new Field(1,1);
		assertFalse(field.sameIndex(f));
	}

	@Test
	public void testIndexNeighbour() {
		Field f;
		
		// same index ==> no neighbour!
		f = new Field(0, 0);
		assertFalse(field.indexNeighbour(f));
		
		// next index
		f = new Field(0, 1);
		assertTrue(field.indexNeighbour(f));
		
		// next index
		f = new Field(0, 7);
		assertTrue(field.indexNeighbour(f));
		
		// no neighbour
		f = new Field(0, 4);
		assertFalse(field.indexNeighbour(f));
	}

	@Test
	public void testSameGrid() {
		Field f;
		
		// same grid, same index
		f = new Field(0,0);
		assertTrue(field.sameGrid(f));
		
		// other grid, same index
		f = new Field(1,0);
		assertFalse(field.sameGrid(f));
		
		// same grid, other index
		f = new Field(0,1);
		assertTrue(field.sameGrid(f));
		
		// other grid, other index
		f = new Field(1,1);
		assertFalse(field.sameGrid(f));
	}

	@Test
	public void testGridNeighour() {
		Field f;
		
		// same grid ==> no neighbour!
		f = new Field(0, 0);
		assertFalse(field.gridNeighour(f));
		
		// next grid
		f = new Field(1, 0);
		assertTrue(field.gridNeighour(f));		 
		
		// no neighbour
		f = new Field(2, 0);
		assertFalse(field.gridNeighour(f));
	}

	@Test
	public void testCorner() {
		assertTrue(field.corner());

		// middle field ==> no corner
		field = new Field(0,1);
		assertFalse(field.corner());
	}

	@Test
	public void testEqualsObject() {
		Field other;
		
		// same grid, same index
		other = new Field(0, 0);
		assertTrue(field.equals(other));
		
		// same grid, other index
		other = new Field(0, 1);
		assertFalse(field.equals(other));
		
		// other grid, same index
		other = new Field(1, 0);
		assertFalse(field.equals(other));
		
		// other grid, other index
		other = new Field(1, 1);
		assertFalse(field.equals(other));
		
		// test a non-field-object
		assertFalse(field.equals(new Object()));
	}

	@Test
	public void testToString() {
		// no assert needed, we don't care
		// how the string looks like!
		field.toString();		
	}
	
	@Test
	public void testHasCode() {
		Field f1 = new Field(0, 0);
		Field f2 = new Field(0, 0);
		assertTrue(f1.hashCode() == f2.hashCode());
	}

}
