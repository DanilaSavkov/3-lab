package nature;

import enums.FoodType;
import enums.Gender;
import exceptions.AddToCellException;
import exceptions.DeleteFromCellException;
import exceptions.EatingException;
import exceptions.SexException;
import world.Cell;
import world.OceanMap;
import world.Rule;

public abstract class Predator extends OceanLife {
    private int stepsLeftCount;
    private int hungerStepsLeftCount;
    private final int size;
    private final FoodType foodType;
    private final Gender gender;
    private final int stepsToDeath;
    private final int stepsToDeathByHunger;

    public Predator(Gender gender, Cell cell) throws AddToCellException {
        super(cell);
        this.size = getSize();
        this.foodType = getFoodType();
        this.gender = gender;
        this.stepsLeftCount = getStepsToDeath();
        this.stepsToDeath = getStepsToDeath();
        this.stepsToDeathByHunger = getStepsToDeathByHunger();
        this.hungerStepsLeftCount = getStepsToDeathByHunger();
    }

    public Predator(Gender gender, int stepsLeftCount, int hungerStepsLeftCount, Cell cell) throws AddToCellException {
        super(cell);
        this.size = getSize();
        this.foodType = getFoodType();
        this.gender = gender;
        this.stepsLeftCount = stepsLeftCount;
        this.stepsToDeath = getStepsToDeath();
        this.stepsToDeathByHunger = getStepsToDeathByHunger();
        this.hungerStepsLeftCount = hungerStepsLeftCount;
    }

    public void eat(OceanLife food, OceanMap map) throws EatingException {
        if (!Rule.canEatIt(this, food)) throw new EatingException("Predator can't eat this creation.");
        try {
            Cell current = map.getCell(this.getCell().getX(), this.getCell().getY());
            current.delete(food);
            this.hungerStepsLeftCount = stepsToDeathByHunger;
        } catch (DeleteFromCellException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean dying() {
        stepsLeftCount--;
        hungerStepsLeftCount--;
        return (stepsLeftCount <= 0) || (hungerStepsLeftCount <= 0);
    }

    @Override
    public abstract int getDistance();

    public abstract int getSize();

    public abstract FoodType getFoodType();

    public abstract int getStepsToDeath();

    public abstract int getStepsToDeathByHunger();

    public int getStepsLeftCount() {
        return stepsLeftCount;
    }

    public int getHungerStepsLeftCount() {
        return hungerStepsLeftCount;
    }

    public Gender getGender() {
        return gender;
    }

    public void setHungerStepsLeftCount(int hungerStepsLeftCount) {
        this.hungerStepsLeftCount = hungerStepsLeftCount;
    }

    @Override
    public String toString() {
        return super.toString() + " (steps = " + stepsLeftCount + "/" + hungerStepsLeftCount + ", gender = " + gender + ')';
    }
}
