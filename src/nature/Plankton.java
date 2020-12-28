package nature;

import exceptions.AddToCellException;
import world.Cell;

public class Plankton extends OceanLife {
    private int healthPoints;
    private static final int DISTANCE = 1;
    private static final int MAX_HP = 100;
    private static final int DYING_VALUE = 10;

    public Plankton(Cell cell) throws AddToCellException {
        super(cell);
        this.healthPoints = MAX_HP;
    }

    public Plankton(int healthPoints, Cell cell) throws AddToCellException {
        super(cell);
        this.healthPoints = healthPoints;
    }

    @Override
    public boolean dying() {
        healthPoints -= DYING_VALUE;
        return healthPoints <= 0;
    }

    public double hpPercentage() {
        return (double) healthPoints / MAX_HP;
    }

    @Override
    public int getDistance() {
        return DISTANCE;
    }

    public int getMaxHP() {
        return MAX_HP;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints() {
        this.healthPoints = MAX_HP;
    }

    @Override
    public String toString() {
        return super.toString() + " (HP = " + healthPoints + ")";
    }
}
