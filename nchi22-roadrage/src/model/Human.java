/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Human class that defines humans' behavior.
 * @author Rainie Chi
 * @version 11 Nov 2022
 */
public class Human extends AbstractVehicle {
    /**
     * Human's death time: 45.
     */
    private static final int DEATH_TIME = 45;
    /**
     * Human constructor.
     * @param theX Human's initial x coordinate
     * @param theY Human's  initial y coordinate
     * @param theDirection Human's initial direction
     */
    public Human(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME);
    }
    /**
     * Human are always on grass or crosswalks.
     * Humans travel through crosswalks when the crosswalk light is yellow or red.
     * @param theTerrain The terrain.
     * @param theLight The light color.
     * @return whether Human may move onto the given type of
     *         terrain when the street lights are the given color.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain == Terrain.GRASS
                || theTerrain == Terrain.CROSSWALK && theLight != Light.GREEN;
    }
    /**
     * Humans move in a random direction (straight, left, or right).
     * A human never reverses direction unless there is no other option.
     * f a human is next to a crosswalk it will always choose
     * to turn to face in the direction of the crosswalk.
     *
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction this human would like to move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> canGoList = new ArrayList<>();
        for (final Direction key : theNeighbors.keySet()) {
            if (key != getDirection().reverse()
                    && theNeighbors.get(key) == Terrain.CROSSWALK) {
                return key;
            } else if (key != getDirection().reverse()
                    && isAllowedTerrain(theNeighbors.get(key))) {
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
     * Helper method to determine if human can go on given terrain.
     * Humans can only go on crosswalks and grasss.
     * @param theTerrain the terrain we are checking
     * @return whether the human can go on the terrain
     */
    private boolean isAllowedTerrain(final Terrain theTerrain) {
        return theTerrain == Terrain.CROSSWALK || theTerrain == Terrain.GRASS;
    }

}
