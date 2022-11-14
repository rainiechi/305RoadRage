/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;

import java.util.Map;
/**
 * Car class that defines cars' behavior.
 * @author Rainie Chi
 * @version 11 Nov 2022
 */
public class Car extends AbstractVehicle {
    /**
     * Car's death time: 15.
     */
    private static final int DEATH_TIME = 15;
    /**
     * Car constructor.
     * @param theX Car's initial x coordinate
     * @param theY Car's  initial y coordinate
     * @param theDirection Car's initial direction
     */
    public Car(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME);
    }

    /**
     * Cars stop for red lights, ignore yellow and green lights.
     * stop for red and yellow crosswalk lights, but drive through
     * green crosswalk lights without stopping.
     * @param theTerrain The terrain.
     * @param theLight The light color.
     * @return whether Car may move onto the given type of
     *         terrain when the street lights are the given color.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain == Terrain.STREET
                || theTerrain == Terrain.LIGHT && theLight != Light.RED
                || theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN;
    }

    /**
     * A car prefers to drive straight ahead on the street if it can.
     * If it cannot move straight ahead, it turns left if possible;
     * if it cannot turn left, it turns right if possible; as a last resort, it turns around.
     *
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction this Car would like to move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction result;
        if (isAllowedTerrain(theNeighbors.get(getDirection()))) {
            result = getDirection();
        } else if (isAllowedTerrain(theNeighbors.get(getDirection().left()))) {
            result = getDirection().left();
        } else if (isAllowedTerrain(theNeighbors.get(getDirection().right()))) {
            result = getDirection().right();
        } else {
            result = getDirection().reverse();
        }
        return result;
    }

    /**
     * Helper method to determine if car can go on given terrain.
     * Cars can go on light, crosswalk, and street.
     * @param theTerrain the terrain we are checking
     * @return whether the car can go on the terrain
     */
    private boolean isAllowedTerrain(final Terrain theTerrain) {
        return theTerrain == Terrain.LIGHT
                || theTerrain == Terrain.CROSSWALK
                || theTerrain == Terrain.STREET;
    }
}
