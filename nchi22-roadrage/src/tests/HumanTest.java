///*
// * TCSS 305 - Road Rage
// */
package tests;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import model.Direction;
import model.Human;
import model.Light;
import model.Terrain;
import org.junit.jupiter.api.Test;
/**
 * Unit tests for class Human.
 *
 * @author Alan Fowler (acfowler@uw.edu)
 * @author Tom Capaul (tcapaul@uw.edu)
 * @version Fall 2022
 */
public class HumanTest {
    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;

    /**
     * Test method for Human constructor.
     */
    @Test
    public void testHumanConstructor() {
        final Human h = new Human(10, 11, Direction.NORTH);

        assertEquals(10, h.getX(), "Human x coordinate not initialized correctly!");
        assertEquals(11, h.getY(), "Human y coordinate not initialized correctly!");
        assertEquals(Direction.NORTH, h.getDirection(), "Human direction not initialized correctly!");
        assertEquals(45, h.getDeathTime(), "Human death time not initialized correctly!");
        assertTrue(h.isAlive(), "Human isAlive() fails initially!");
    }

    /**
     * Test method for Human setters.
     */
    @Test
    public void testHumanSetters() {
        final Human h = new Human(10, 11, Direction.NORTH);

        h.setX(12);
        assertEquals(12, h.getX(), "Human setX failed!");
        h.setY(13);
        assertEquals(13, h.getY(), "Human setY failed!");
        h.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, h.getDirection(), "Human setDirection failed!");
    }

    /**
     * Test method for {@link Human#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {

        // Humans can move to GRASS or to CROSSWALKS
        // so we need to test both of those conditions

        // Humans should NOT choose to move to other terrain types
        // so we need to test that Humans never move to other terrain types

        // Humans should only reverse direction if no other option is available
        // so we need to be sure to test that requirement also

        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.GRASS);
        validTerrain.add(Terrain.CROSSWALK);

        final Human human = new Human(0, 0, Direction.NORTH);
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.GRASS) {

                    // humans can pass GRASS under any light condition
                    assertTrue(human.canPass(destinationTerrain,
                                    currentLightCondition),
                            "Human should be able to pass GRASS with light " +
                                    currentLightCondition);
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                    // humans can pass CROSSWALK
                    // if the light is YELLOW or RED but not GREEN
                    if (currentLightCondition == Light.GREEN) {
                        assertFalse(human.canPass(destinationTerrain,
                                        currentLightCondition),
                                "Human should NOT be able to pass " +
                                        destinationTerrain
                                        + ", with light " + currentLightCondition);
                    } else { // light is yellow or red
                        assertTrue(human.canPass(destinationTerrain,
                                        currentLightCondition),
                                "Human should be able to pass " +
                                        destinationTerrain
                                        + ", with light " + currentLightCondition);
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {

                    assertFalse(human.canPass(destinationTerrain,
                                    currentLightCondition),
                            "Human should NOT be able to pass " + destinationTerrain
                                    + ", with light " + currentLightCondition);
                }
            }
        }
    }
}