package world;

import nature.OceanLife;
import nature.Plankton;
import nature.Predator;

import java.util.ArrayList;

public final class Rule {
    // передвижение
    private static boolean planktonRule(OceanLife obj, Cell cell) {
        if (!(obj instanceof Plankton)) return true;
        else return !cell.hasPlankton();
    }

    private static boolean movingIsPossible(OceanLife obj, Cell cell) {
        return (distance(obj.getCell(), cell) <= obj.getDistance()) && !cell.isFull() && planktonRule(obj, cell);
    }

    public static ArrayList<Cell> movingOptions(OceanMap map, OceanLife obj) {
        ArrayList<Cell> possible = new ArrayList<>();
        for (int i = 0; i < map.getLength(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Cell temp = map.getCell(i, j);
                if (movingIsPossible(obj, temp)) {
                    possible.add(temp);
                }
            }
        }
        return possible;
    }

    private static int distance(Cell first, Cell second) {
        int xDifference = Math.abs(first.getX() - second.getX());
        int yDifference = Math.abs(first.getY() - second.getY());
        return xDifference + yDifference;
    }

    public static boolean canMoveTo(OceanLife obj, Cell cell) {
        return obj.getDistance() >= distance(obj.getCell(), cell);
    }

    // питание
    public static boolean canEatIt(Predator predator, OceanLife food) {
        if (predator.getCell() != food.getCell()) return false;
        if (!inTaste(predator, food)) return false;
        else if (food instanceof Plankton) return true;
        else {
            Predator temp = (Predator) food;
            return predator.getSize() > temp.getSize();
        }
    }

    private static boolean inTaste(Predator predator, OceanLife food) {
        String first = predator.getFoodType().toString();
        String second = food.getClass().getSuperclass().getSimpleName();
        if (food instanceof Plankton) return first.equals(food.getClass().getSimpleName());
        return first.equals(second);
    }

    public static ArrayList<OceanLife> eatingOptions(Predator predator) {
        ArrayList<OceanLife> result = predator.getCell().getDweller();
        result.remove(predator);
        for (int i = 0; i < result.size(); i++) {
            OceanLife temp = result.get(i);
            if (!canEatIt(predator, temp)) result.remove(temp);
        }
        return result;
    }

    // размножение
    private static boolean canMultiplyWith(Predator first, Predator second) {
        return (first.getClass() == second.getClass()) && (first.getGender() != ((Predator) second).getGender());
    }

    private static boolean canMultiplyTo(Cell cell) {
        return !cell.isFull();
    }

    public static Predator multiplyOption(Predator predator) {
        for (int i = 0; i < predator.getCell().size(); i++) {
            OceanLife partner = predator.getCell().get(i);
            if (!(partner instanceof Plankton)) {
                partner = (Predator) predator.getCell().get(i);
                if (canMultiplyWith(predator, (Predator) partner) && canMultiplyTo(predator.getCell()))
                    return (Predator) partner;
            }
        }
        return null;
    }

    // размножение планктона
    public static Plankton canHillSomeone(Plankton plankton, OceanMap map) {
        for (Cell x : aroundPlankton(plankton, map)) {
            Plankton temp = findDyingPlankton(x);
            if (x.hasPlankton() && findDyingPlankton(x) != null) {
                return temp;
            }
        }
        return null;
    }

    private static Plankton findDyingPlankton(Cell cell) {
        for (OceanLife obj : cell.getDweller()) {
            if (obj instanceof Plankton) {
                Plankton temp = (Plankton) obj;
                if ((double) temp.getHealthPoints() / temp.getMaxHP() <= 1 / 3.) return temp;
            }
        }
        return null;
    }

    public static Cell findCellForPlankton(Plankton plankton, OceanMap map) {
        for (Cell cell : aroundPlankton(plankton, map)) {
            if (!cell.hasPlankton() && !cell.isFull() && cell != plankton.getCell()) return cell;
        }
        return null;
    }

    private static ArrayList<Cell> aroundPlankton(Plankton plankton, OceanMap map) {
        ArrayList<Cell> possible = new ArrayList<>();
        for (int i = 0; i < map.getLength(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Cell temp = map.getCell(i, j);
                if (canMoveTo(plankton, temp)) possible.add(temp);
            }
        }
        return possible;
    }
}
