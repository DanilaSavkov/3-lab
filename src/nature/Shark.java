package nature;

import enums.FoodType;
import enums.Gender;
import exceptions.AddToCellException;
import world.Cell;

public class Shark extends Predator {
    private static final int DISTANCE = 2;
    private static final int SIZE = 550;
    private static final FoodType FOOD_TYPE = FoodType.PREDATOR;
    private static final int STEPS_TO_DEATH = 100;
    private static final int STEPS_TO_DEATH_BY_HUNGER = 15;

    public Shark(Gender gender, Cell cell) throws AddToCellException {
        super(gender, cell);
    }

    public Shark(Gender gender, int stepsLeftCount, int hungerStepsLeftCount, Cell cell) throws AddToCellException {
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
