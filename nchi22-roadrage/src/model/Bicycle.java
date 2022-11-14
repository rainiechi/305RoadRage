/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;

import java.util.Map;
/**
 * Bicycle class that defines bicycles' behavior.
 * @author Rainie Chi
 * @version 11 Nov 2022
 */
public class Bicycle extends AbstractVehicle {
    /**
     * Bicycle's death time: 35.
     */
    private static final int DEATH_TIME = 35;
    /**
     * Bicycle constructor.
     * @param theX Bicycle's initial x coordinate
     * @param theY Bicycle's  initial y coordinate
     * @param theDirection Bicycle's initial direction
     */
    public Bicycle(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME);
    }

    /**
     * Bicycles can travel on streets and through lights and crosswalk lights
     * , but they prefer to travel on trails.
     * Bicycles stop for yellow and red lights
     * @param theTerrain The terrain.
     * @param theLight The light color.
     * @return whether Bicycle may move onto the given type of
     *         terrain when the street lights are the given color.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain == Terrain.STREET
                || (theTerrain == Terrain.LIGHT
                || theTerrain == Terrain.CROSSWALK) && theLight == Light.GREEN
                || theTerrain == Terrain.TRAIL;
    }

    /**
     * Bicycles can travel on streets and through lights and crosswalk lights
     * , but they prefer to travel on trails.
     *
     * If a trail neighbors (not reverse) the bike, the bike
     * will always go towards the direction.
     *
     * If there is no trail ahead, to the left, or to the right, the bicycle
     * prefers to move straight ahead on
     * If it cannot move straight ahead, it turns left;
     * If it cannot turn left, it turns right.
     * If none of these three directions is legal, the bicycle turns around.
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction this bicycle would like to move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        for (final Direction key : theNeighbors.keySet()) {
            if (key != getDirection().reverse() && theNeighbors.get(key) == Terrain.TRAIL) {
                return key;
            }
        }
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
     * Helper method to determine if bicycle can go on given terrain.
     * Bikes can go on lights, crosswalks, streets.
     * @param theTerrain the terrain we are checking
     * @return whether the bike can go on the terrain
     */
    private boolean isAllowedTerrain(final Terrain theTerrain) {
        return theTerrain == Terrain.LIGHT
                || theTerrain == Terrain.CROSSWALK
                || theTerrain == Terrain.STREET;
    }

}
