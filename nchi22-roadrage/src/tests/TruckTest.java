/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class TruckTest {
    /**
     * Truck we will be testing on.
     */
    private static Truck t;

    @BeforeAll
    public static void setUp() {
        t = new Truck(10, 11, Direction.NORTH);
    }

    @Test
    public void testTruckConstructorAndGetX() { //tests constructor, getX()
        assertEquals(10, t.getX());
    }

    @Test
    public void testTruckConstructorAndGetY() { //tests constructor, getY()
        assertEquals(11, t.getY());
    }

    @Test
    public void testTruckConstructorAndGetDirection() { //tests constructor, getDirection()
        assertEquals(Direction.NORTH, t.getDirection());
    }

    @Test
    public void testTruckConstructorAndGetDeathTime() { //tests constructor, getDeathTime()
        assertEquals(0, t.getDeathTime());
    }

    @Test
    public void testTruckConstructorAndIsAlive() { //tests constructor, isAlive()
        assertTrue(t.isAlive());
    }


    @Test
    public void xCannotBeNegative() { //tests exception for constructor
        assertThrows(IllegalArgumentException.class, () -> {
            final Truck badTruck = new Truck(-1, 1, Direction.WEST); });
    }

    @Test
    public void yCannotBeNegative() { //tests exception for constructor
        assertThrows(IllegalArgumentException.class, () -> {
            final Truck badTruck = new Truck(1, -1, Direction.WEST); });
    }

    @Test
    public void directionCannotBeNull() { //tests exception for constructor
        assertThrows(IllegalArgumentException.class, () -> {
            final Truck badTruck = new Truck(1, 1, null); });
    }

    @Test
    void truckDoesNotDieWhenCollideWithTruck() { //tests collide and isAlive()
        final Truck truck2 = new Truck(2, 2, Direction.WEST);
        t.collide(truck2);
        assertTrue(t.isAlive());
    }

    @Test
    void truckDoesNotDieWhenCollideWithAnyVehicle() { //tests collide and isAlive()
        final Atv atv = new Atv(1, 1, Direction.NORTH);
        final Bicycle bike = new Bicycle(1, 1, Direction.NORTH);
        final Car car = new Car(1, 1, Direction.NORTH);
        final Human human = new Human(1, 1, Direction.NORTH);
        final Taxi taxi = new Taxi(1, 1, Direction.NORTH);

        t.collide(atv);
        assertTrue(t.isAlive());
        t.reset();

        t.collide(bike);
        assertTrue(t.isAlive());
        t.reset();

        t.collide(car);
        assertTrue(t.isAlive());
        t.reset();

        t.collide(human);
        assertTrue(t.isAlive());
        t.reset();

        t.collide(taxi);
        assertTrue(t.isAlive());
    }

    @Test
    void getImageFileNameTest() { //tests getImagineFileName()
        assertEquals("truck.gif", t.getImageFileName());
    }

    @Test
    void setXSetYTest() { //tests setX(), setY()
        t.setX(5);
        assertEquals(5, t.getX());
        t.setY(6);
        assertEquals(6, t.getY());
    }

    @Test
    void setXSetYNegativeExceptionTest() { //tests setX(), setY()
        assertThrows(IllegalArgumentException.class, () -> t.setX(-1));
        assertThrows(IllegalArgumentException.class, () -> t.setY(-1));
    }

    @Test
    void setDirectionTest() { //tests setDirection()
        t.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, t.getDirection());
    }

    @Test
    void setDirectionExceptionTest() { //tests setDirection()
        assertThrows(IllegalArgumentException.class, () -> t.setDirection(null));
    }

    @Test
    void resetTest() { //tests reset()
        t.setX(1);
        t.setY(1);
        t.reset();
        assertEquals(10, t.getX());
        assertEquals(11, t.getY());
    }

    @Test
    void toStringTest() { //tests toString()
        assertEquals("Truck(10,11)Direction: NORTH", t.toString());
    }

    @Test
    void canPass() { //tests canPass()
        //list of allowed terrains
        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.STREET);
        // test each terrain type as a destination
        for (final Terrain terrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLight : Light.values()) {
                if (terrain == Terrain.LIGHT || terrain == Terrain.STREET) {
                    // trucks can pass light, grass under any light condition
                    assertTrue(t.canPass(terrain, currentLight));
                } else if (terrain == Terrain.CROSSWALK) {
                    // truck can pass CROSSWALK if the light is not red
                    if (currentLight == Light.RED) {
                        assertFalse(t.canPass(terrain, currentLight));
                    } else { // light is green or yellow
                        assertTrue(t.canPass(terrain, currentLight));
                    }
                } else if (!validTerrain.contains(terrain)) {
                    //Terrains that are not crosswalk, light or street
                    assertFalse(t.canPass(terrain, currentLight));
                }
            }
        }
    }

    @Test
    void chooseDirectionTest() { //if truck cannot go straight, left, right, it turns around
        final Map<Direction, Terrain> map = new HashMap<>();
        map.put(Direction.NORTH, Terrain.GRASS);
        map.put(Direction.WEST, Terrain.GRASS);
        map.put(Direction.EAST, Terrain.GRASS);
        map.put(Direction.SOUTH, Terrain.STREET);
        assertEquals(Direction.SOUTH, t.chooseDirection(map));
    }

    @Test
        //tests the random behavior of truck when choosing direction
    void chooseDirectionRandomnessTest() {
        final Map<Direction, Terrain> map = new HashMap<>();
        map.put(Direction.NORTH, Terrain.STREET);
        map.put(Direction.WEST, Terrain.STREET);
        map.put(Direction.EAST, Terrain.STREET);
        map.put(Direction.SOUTH, Terrain.STREET);
        final Direction d1 = t.chooseDirection(map);
        final Direction d2 = t.chooseDirection(map);
        final Direction d3 = t.chooseDirection(map);
        final Direction d4 = t.chooseDirection(map);
        final Direction d5 = t.chooseDirection(map);
        //if it wasn't random then all 5 outcomes would be the same
        assertFalse(areAllTheSame(d1, d2, d3, d4, d5));
    }

    //helper method to test chooseDirection
    private boolean areAllTheSame(final Direction d1, final Direction d2,
                                  final Direction d3, final Direction d4, final Direction d5) {
        return d1.equals(d2) && d2.equals(d3) && d3.equals(d4) && d4.equals(d5);
    }
}