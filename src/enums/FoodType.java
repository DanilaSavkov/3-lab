package enums;

import nature.Plankton;
import nature.Predator;

public enum FoodType {
    PLANKTON(Plankton.class), PREDATOR(Predator.class);
    Class<?> type;

    FoodType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.getSimpleName();
    }
}