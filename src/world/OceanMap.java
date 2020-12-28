package world;

import enums.Action;
import enums.Gender;
import exceptions.AddToCellException;
import exceptions.DeleteFromCellException;
import exceptions.EatingException;
import exceptions.MovingException;
import nature.*;

import java.util.ArrayList;

public class OceanMap {
    private final int length; // x
    private final int width; // y
    private final ArrayList<ArrayList<Cell>> cellsArray;

    public OceanMap(int length, int width) {
        this.length = length;
        this.width = width;
        this.cellsArray = new ArrayList<>();
        for (int x = 0; x < length; x++) {
            ArrayList<Cell> tempList = new ArrayList<>();
            for (int y = 0; y < width; y++) {
                Cell temp = new Cell(x, y);
                tempList.add(temp);
            }
            cellsArray.add(tempList);
        }
    }

    public void step() {
        ArrayList<OceanLife> checked = new ArrayList<>();
        for (ArrayList<Cell> x : cellsArray) {
            for (Cell cell : x) {
                for (int i = 0; i < cell.size(); i++) {
                    OceanLife dweller = cell.get(i);
                    if (!checked.contains(dweller)) {
                        if (!dweller.dying()) {
                            if (!(dweller instanceof Plankton))
                                predatorActions((Predator) dweller, checked);
                            else {
                                planktonActions((Plankton) dweller, checked);
                            }
                            checked.add(dweller);
                        } else delete(dweller);
                        System.gc();
                    }
                }
            }
        }
    }

    private boolean moving(OceanLife dweller) {
        ArrayList<Cell> options = Rule.movingOptions(this, dweller);
        return movingOptionsRecursion(options, dweller);
    }

    private boolean movingOptionsRecursion(ArrayList<Cell> options, OceanLife obj) {
        if (options.size() != 0) {
            int random = (int) (Math.random() * options.size());
            Cell choice = options.get(random);
            try {
                obj.moveTo(this, choice);
                return true;
            } catch (MovingException e) {
                options.remove(choice);
                movingOptionsRecursion(options, obj);
            }
        }
        return false;
    }

    private boolean eating(Predator predator) {
        ArrayList<OceanLife> options = Rule.eatingOptions(predator);
        return eatingOptionsRecursion(options, predator);
    }

    private boolean eatingOptionsRecursion(ArrayList<OceanLife> options, Predator predator) {
        if (options.size() != 0) {
            int random = (int) (Math.random() * options.size());
            OceanLife choice = options.get(random);
            try {
                predator.eat(choice, this);
                return true;
            } catch (EatingException e) {
                options.remove(choice);
                eatingOptionsRecursion(options, predator);
            }
        }
        return false;
    }

    private boolean sex(Predator predator, ArrayList<OceanLife> checked) {
        Predator partner = Rule.multiplyOption(predator);
        if (partner == null) return false;
        checked.add(partner);
        Cell bornTo = this.getCell(predator.getCell().getX(), predator.getCell().getY());
        double deathLine = 2 / 3.;
        predator.setHungerStepsLeftCount((int) (deathLine * predator.getStepsToDeathByHunger()));
        partner.setHungerStepsLeftCount((int) (deathLine * predator.getStepsToDeathByHunger()));
        try {
            Predator baby;
            if (predator instanceof Shark) {
                baby = new Shark(Gender.random(), bornTo);
                checked.add(baby);
            } else if (predator instanceof Fish) {
                baby = new Fish(Gender.random(), bornTo);
                checked.add(baby);
            }
            return true;
        } catch (AddToCellException e) {
            return false;
        }
//        return false;
    }

    private Action predatorPriority(Predator predator) {
        double deathLine = 2 / 3.;
        if (predator.getHungerStepsLeftCount() == predator.getStepsToDeathByHunger() - 1 && predator.getStepsLeftCount() == predator.getStepsToDeath() - 1)
            return Action.MOVE;
        if ((double) predator.getHungerStepsLeftCount() / predator.getStepsToDeathByHunger() <= deathLine)
            return Action.EAT;
        else return Action.SEX;
    }

    private void predatorActions(Predator predator, ArrayList<OceanLife> checked) {
        switch (predatorPriority(predator)) {
            case SEX:
                if (sex(predator, checked)) break;
            case EAT:
                if (eating(predator)) break;
            case MOVE:
                if (moving(predator)) break;
            default:
                break;
        }
    }

    private boolean sex(Plankton plankton, ArrayList<OceanLife> checked) {
        Cell tempCell = Rule.findCellForPlankton(plankton, this);
        if (plankton.hpPercentage() <= 1 / 5. && tempCell != null) {
            try {
                Cell bornTo = this.getCell(tempCell.getX(), tempCell.getY());
                Plankton newPlankton = new Plankton(bornTo);
                checked.add(newPlankton);
                return true;
            } catch (AddToCellException e) {
                return false;
            }
        }
        return false;
    }

    private Action planktonPriority(Plankton plankton) {
        if (Rule.canHillSomeone(plankton, this) != null) return Action.SEX;
        else return Action.MOVE;
    }

    private void planktonActions(Plankton plankton, ArrayList<OceanLife> checked) {
        switch (planktonPriority(plankton)) {
            case SEX:
                Plankton temp = Rule.canHillSomeone(plankton, this);
                if (sex(plankton, checked)) break;
                else if (temp != null) {
                    temp.setHealthPoints();
                    break;
                }
            case MOVE:
                if (moving(plankton)) break;
            default:
                break;
        }
    }

    private void delete(OceanLife obj) {
        Cell current = getCell(obj.getCell().getX(), obj.getCell().getY());
        try {
            current.delete(obj);
        } catch (DeleteFromCellException e) {
            obj = null;
            System.gc();
        }
    }

    public Cell getCell(int x, int y) {
        return this.getCellsArray().get(x).get(y);
    }

    public Cell getCell(OceanLife obj) {
        return getCell(obj.getCell().getX(), obj.getCell().getY());
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<ArrayList<Cell>> getCellsArray() {
        return cellsArray;
    }

    @Override
    public String toString() {
        String result = this.getClass().getSimpleName() + " (length = " + length + ", width = " + width + ") = {\n";
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                Cell temp = getCell(x, y);
                if (!temp.isEmpty()) {
                    result += '\t' + temp.toString() + '\n';
                }
            }
        }
        result += '}';
        return result;
    }
}
