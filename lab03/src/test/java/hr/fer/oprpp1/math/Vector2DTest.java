package hr.fer.oprpp1.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Razred koji testira metode i konstruktore razreda Vector2D.
 * 
 * @author Ana Bagić
 *
 */
public class Vector2DTest {
	
	Vector2D vector;
	Vector2D vector2;
	
    @Test
    public void testVectorConstructor() {
        vector = new Vector2D(2.2, 4.4);

        assertEquals(2.2, vector.getX());
        assertEquals(4.4, vector.getY());
    }

    @Test
    public void testVectorAdd() {
        vector = new Vector2D(2, 4);
        vector.add(new Vector2D(10.12, 55));

        assertTrue(12.12 - Math.abs(vector.getX()) < 1E-8);
        assertTrue(59 - Math.abs(vector.getY()) < 1E-8);
    }

    @Test
    public void testVectorAdded() {
        vector = new Vector2D(2, 4);
        vector2 = vector.added(new Vector2D(10, 20));

        assertTrue(2 - Math.abs(vector.getX()) < 1E-8);
        assertTrue(4 - Math.abs(vector.getY()) < 1E-8);

        assertTrue(12 - Math.abs(vector2.getX()) < 1E-8);
        assertTrue(24 - Math.abs(vector2.getY()) < 1E-8);
    }

    @Test
    public void testVectorRotate() {
        vector = new Vector2D(2, 0);
        vector.rotate(Math.PI / 2);

        assertTrue(Math.abs(vector.getX()) < 1E-8);
        assertTrue(2 - Math.abs(vector.getY()) < 1E-8);
    }

    @Test
    public void testVectorRotate2() {
        vector = new Vector2D(2, 2);
        vector.rotate(Math.PI);

        assertTrue(2 - Math.abs(vector.getX()) < 1E-8);
        assertTrue(2 - Math.abs(vector.getY()) < 1E-8);
    }

    @Test
    public void testVectorRotated() {
        vector = new Vector2D(2, 2);
        vector2 = vector.rotated(Math.PI);

        assertNotSame(vector2, vector);
        assertTrue(2 - Math.abs(vector2.getX()) < 1E-8);
        assertTrue(2 - Math.abs(vector2.getY()) < 1E-8);
    }

    @Test
    public void testVectorScale() {
        vector = new Vector2D(2, 4);
        vector.scale(2);

        assertTrue(4 - Math.abs(vector.getX()) < 1E-8);
        assertTrue(8 - Math.abs(vector.getY()) < 1E-8);
    }

    @Test
    public void testVectorScaled() {
        vector = new Vector2D(2, 4);
        vector2 = vector.scaled(3);

        assertNotSame(vector2, vector);
        assertTrue(6 - Math.abs(vector2.getX()) < 1E-8);
        assertTrue(12 - Math.abs(vector2.getY()) < 1E-8);
    }
    
    @Test
    public void testVectorCopy() {
        vector = new Vector2D(2, 4);
        vector2 = vector.copy();

        assertNotSame(vector2, vector);
        assertEquals(vector.getX(), vector2.getX());
        assertEquals(vector.getY(), vector2.getY());
    }
}
