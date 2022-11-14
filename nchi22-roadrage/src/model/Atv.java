/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * ATV class that defines ATV behavior.
 * @author Rainie Chi
 * @version 11 Nov 2022
 */
public class Atv extends AbstractVehicle {
    /**
     * ATV's death time: 25.
     */
    private static final int DEATH_TIME = 25;

    /**
     * ATV constructor.
     * @param theX ATV's initial x coordinate
     * @param theY ATV's initial y coordinate
     * @param theDirection ATV's initial direction
     */
    public Atv(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME);
    }

    /**
     * ATVs can travel on any terrain except walls.
     * ATV’s drive through all traffic lights and crosswalk lights without stopping.
     * @param theTerrain The terrain.
     * @param theLight The light color.
     * @return whether ATV may move onto the given type of
     *         terrain when the street lights are the given color.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain != Terrain.WALL;
    }

    /**
     * ATV's randomly select to go straight, turn left
     * , or turn right.  ATV’s never reverse direction.
     *
     * @param theNeighbors The map of neighboring terrain.
     * @return the direction this ATV would like to move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> canGoList = new ArrayList<>();
        for (final Direction key : theNeighbors.keySet()) {
            if (key != getDirection().reverse() && theNeighbors.get(key) != Terrain.WALL) {
                canGoList.add(key);
            }
        }
        return canGoList.get(RAND.nextInt(canGoList.size()));
    }
}
