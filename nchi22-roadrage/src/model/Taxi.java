/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;
/**
 * Taxi class that defines taxis' behavior.
 * @author Rainie Chi
 * @version 11 Nov 2022
 */
public class Taxi extends Car {
    /**
     * Timer to keep track of how long taxi's been waiting for red crosswalk light.
     */
    private int myRedLightTimer = 3;
    /**
     * Taxi constructor.
     * @param theX Taxi's initial x coordinate
     * @param theY Taxi's  initial y coordinate
     * @param theDirection Taxi's initial direction
     */
    public Taxi(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection);
    }
    /**
     * Taxis stop for red lights;
     * Taxis stop for (temporarily) red crosswalk lights: stays still and does not
     * move for 3 clock cycles or until the crosswalk light turns green
     * , whichever occurs first.
     * @param theTerrain The terrain.
     * @param theLight The light color.
     * @return whether taxi may move onto the given type of
     *         terrain when the street lights are the given color.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED && myRedLightTimer != 0) {
            myRedLightTimer--;
            return false;
        } else if (theTerrain == Terrain.CROSSWALK) {
            if (myRedLightTimer != 3) {
                myRedLightTimer = 3;
            }
            return true;
        } else {
            return theTerrain == Terrain.STREET
                    || theTerrain == Terrain.LIGHT && theLight != Light.RED;
        }
    }
    /**
     * toString that shows name of vehicle, coordinates, current direction
     * , and red light timer count.
     * @return current status of the vehicle
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append("(").append(getX()).
                append(",").append(getY()).append(")").append("Direction: ").
                append(getDirection()).append("Red Light Timer: ").append(myRedLightTimer);
        return sb.toString();
    }
}
