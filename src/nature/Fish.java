package nature;

import enums.FoodType;
import enums.Gender;
import exceptions.AddToCellException;
import world.Cell;

public class Fish extends Predator {
    private static final int DISTANCE = 1;
    private static final int SIZE = 200;
    private static final FoodType FOOD_TYPE = FoodType.PLANKTON;
    private static final int STEPS_TO_DEATH = 50;
    private static final int STEPS_TO_DEATH_BY_HUNGER = 10;

    public Fish(Gender gender, Cell cell) throws AddToCellException {
        super(gender, cell);
    }

    public Fish(Gender gender, int stepsLeftCount, int hungerStepsLeftCount, Cell cell) throws AddToCellException {
        super(gender, stepsLeftCount, hungerStepsLeftCount, cell);
    }

    @Override
    public int getDistance() {
        return DISTANCE;
    }

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public FoodType getFoodType() {
        return FOOD_TYPE;
    }

    @Override
    public int getStepsToDeath() {
        return STEPS_TO_DEATH;
    }

    @Override
    public int getStepsToDeathByHunger() {
        return STEPS_TO_DEATH_BY_HUNGER;
    }
}
