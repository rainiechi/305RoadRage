/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Truck class that defines trucks' behavior.
 * @author Rainie Chi
 * @version 11 Nov 2022
 */
public class Truck extends AbstractVehicle {
    /**
     * Truck's death time: 0.
     */
    private static final int DEATH_TIME = 0;
    /**
     * Truck constructor.
     * @param theX Truck's initial x coordinate
     * @param theY Truck's  initial y coordinate
     * @param theDirection Truck's initial direction
     */
    public Truck(final int theX, final int theY, final  Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME);
    }
    /**
     * Trucks drive through all traffic lights without stopping.
     * Stop for red crosswalk lights, but drive through yellow or green
     * crosswalk lights without stopping.
     * @param theTerrain The terrain.
     * @param theLight The light color.
     * @return whether Truck may move onto the given type of
     *         terrain when the street lights are the given color.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final  Light theLight) {
        return theTerrain == Terrain.STREET
                || theTerrain == Terrain.LIGHT
                || theTerrain == Terrain.CROSSWALK && theLight != Light.RED;
    }
    /**
     * Trucks randomly select to go straight, turn left, or turn right.
     * As a last resort, if none of these three directions is legal
     * (all not streets, lights, or crosswalks), the truck turns around.
     *
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction this truck would like to move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> canGoList = new ArrayList<>();
        for (final Direction key : theNeighbors.keySet()) {
            if (key != getDirection().reverse() && isAllowedTerrain(theNeighbors.get(key))) {
                canGoList.add(key);
            }
        }
        if (!canGoList.isEmpty()) {
            return canGoList.get(RAND.nextInt(canGoList.size()));
        } else {
            return getDirection().reverse();
        }
    }
    /**
     * Helper method to determine if truck can go on given terrain.
     * @param theTerrain the terrain we are checking
     * @return whether the truck can go on the terrain
     */
    private boolean isAllowedTerrain(final Terrain theTerrain) {
        return theTerrain == Terrain.LIGHT
                || theTerrain == Terrain.CROSSWALK
                || theTerrain == Terrain.STREET;
    }

}
