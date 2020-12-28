package enums;

import java.util.Random;

public enum Gender {
    MALE, FEMALE;

    public static Gender random() {
        Gender result;
        Random random = new Random();
        boolean temp = random.nextBoolean();
        if (temp) result = Gender.FEMALE;
        else result = Gender.MALE;
        return result;
    }

    @Override
    public String toString() {
        if (this == Gender.MALE) return "MALE";
        else return "FEMALE";
    }
}
