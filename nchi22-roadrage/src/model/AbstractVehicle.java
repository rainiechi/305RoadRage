/*
 * TCSS 305 Autumn 2022
 * Assignment 3
 */
package model;

import java.util.Locale;
import java.util.Random;
/**
 * Abstract vehicle class that defines common behavior for all vehicles.
 * @author Rainie Chi
 * @version 11 Nov 2022
 */
public abstract class AbstractVehicle implements Vehicle {
    /**
     * Random that will be used in some vehicles to achieve random behavior.
     */
    protected static final Random RAND = new Random();
    /**
     * Vehicle's current x coordinate.
     */
    private int myX;
    /**
     * Vehicle's current y coordinate.
     */
    private int myY;
    /**
     * The direction the vehicle is facing.
     */
    private Direction myDirection;
    /**
     * Vehicle's spawning x coordinate.
     */
    private final int myInitialX;
    /**
     * Vehicle's spawning y coordinate.
     */
    private final int myInitialY;
    /**
     * Vehicle's spawning direction.
     */
    private final Direction myInitialDirection;
    /**
     * Vehicle's death time.
     */
    private final int myDeathTime;
    /**
     * Vehicle's poke count from the time of death and respawn.
     */
    private int myPokeCount;
    /**
     * Whether the vehicle is alive or dead.
     */
    private boolean myAlive;

    /**
     * Abstract vehicle constructor.
     * @param theX x coordinate of where the vehicle first spawns
     * @param theY y coordinate of where the vehicle first spawns
     * @param theDirection direction the vehicle faces when first spawns
     * @param theDeathTime the vehicle's death time
     */
    protected AbstractVehicle(final int theX, final int theY
            , final Direction theDirection, final int theDeathTime) {
        if (theX < 0 || theY < 0) {
            throw new IllegalArgumentException("the X, Y coordinates must not "
                    + "be negative numbers: " + theX + ", " + theY);
        }
        if (theDeathTime < 0) {
            throw new IllegalArgumentException("the death time must"
                    + " not be a negative number: " + theDeathTime);
        }
        if (theDirection == null) {
            throw new IllegalArgumentException("the direction must not be null.");
        }
        myInitialX = theX;
        myInitialY = theY;
        myInitialDirection = theDirection;
        myDeathTime = theDeathTime;
        reset();
    }

    /**
     * Called when this Vehicle collides with the specified other Vehicle.
     *
     * @param theOther The other object.
     */
    @Override
    public void collide(final Vehicle theOther) {
        if (isAlive() && theOther.isAlive() && getDeathTime() > theOther.getDeathTime()) {
            myAlive = false;
        }
    }

    /**
     * Returns the number of updates between this vehicle's death and when it
     * should be revived.
     *
     * @return the number of updates.
     */
    @Override
    public int getDeathTime() {
        return myDeathTime;
    }

    /**
     * Returns the file name of the image for this Vehicle object, such as
     * "car.gif".
     *
     * @return the file name.
     */
    @Override
    public String getImageFileName() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName().toLowerCase(Locale.US));
        if (!isAlive()) {
            sb.append("_dead");
        }
        sb.append(".gif");
        return sb.toString();
    }

    /**
     * Returns this Vehicle object's direction.
     *
     * @return the direction.
     */
    @Override
    public Direction getDirection() {
        return myDirection;
    }

    /**
     * Returns this Vehicle object's x-coordinate.
     *
     * @return the x-coordinate.
     */
    @Override
    public int getX() {
        return myX;
    }

    /**
     * Returns this Vehicle object's y-coordinate.
     *
     * @return the y-coordinate.
     */
    @Override
    public int getY() {
        return myY;
    }

    /**
     * Returns whether this Vehicle object is alive and should move on the map.
     *
     * @return true if the object is alive, false otherwise.
     */
    @Override
    public boolean isAlive() {
        return myAlive;
    }

    /**
     * Called by the UI to notify a dead vehicle that 1 movement round has
     * passed, so that it will become 1 move closer to revival.
     */
    @Override
    public void poke() {
        myPokeCount++;
        if (myPokeCount == myDeathTime) {
            myAlive = true;
            myDirection = Direction.random();
            myPokeCount = 0;
        }
    }

    /**
     * Moves this vehicle back to its original position.
     */
    @Override
    public void reset() {
        myX = myInitialX;
        myY = myInitialY;
        myDirection = myInitialDirection;
        myPokeCount = 0;
        myAlive = true;
    }

    /**
     * Sets this object's facing direction to the given value.
     *
     * @param theDir The new direction.
     */
    @Override
    public void setDirection(final Direction theDir) {
        if (theDir == null) {
            throw new IllegalArgumentException("the direction must not be null.");
        }
        myDirection = theDir;
    }

    /**
     * Sets this object's x-coordinate to the given value.
     *
     * @param theX The new x-coordinate.
     */
    @Override
    public void setX(final int theX) {
        if (theX < 0) {
            throw new IllegalArgumentException("the X"
                    + " coordinate must not be a negative number: " + theX);
        }
        myX = theX;
    }

    /**
     * Sets this object's y-coordinate to the given value.
     *
     * @param theY The new y-coordinate.
     */
    @Override
    public void setY(final int theY) {
        if (theY < 0) {
            throw new IllegalArgumentException("the Y coordinate must not be "
                    + "a negative number: " + theY);
        }
        myY = theY;
    }

    /**
     * toString that shows name of vehicle, coordinates, current direction.
     *
     * @return current status of the vehicle
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append("(").append(getX()).
                append(",").append(getY()).append(")").append("Direction: ").
                append(getDirection());
        return sb.toString();
    }
}
