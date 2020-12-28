package world;

import exceptions.AddToCellException;
import exceptions.DeleteFromCellException;
import nature.OceanLife;
import nature.Plankton;

import java.util.ArrayList;

public class Cell {
    private final int x;
    private final int y;
    private static final int MAX_CAPACITY = 4;
    private ArrayList<OceanLife> dweller;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.dweller = new ArrayList<>(MAX_CAPACITY);
    }

    public void add(OceanLife obj) throws AddToCellException {
        if (isFull()) throw new AddToCellException("This cell is full.");
        if ((obj instanceof Plankton) && hasPlankton()) throw new AddToCellException("This cell already has plankton.");
        dweller.add(obj);
    }

    public void delete(OceanLife obj) throws DeleteFromCellException {
        if (isEmpty()) throw new DeleteFromCellException("This cell is empty.");
        if (!dweller.remove(obj)) throw new DeleteFromCellException("Object isn't found.");
    }

    public int size() {
        return dweller.size();
    }

    public boolean hasPlankton() {
        for (OceanLife i: dweller) {
            if (i instanceof Plankton) return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return dweller.isEmpty();
    }

    public boolean isFull() {
        return dweller.size() == MAX_CAPACITY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<OceanLife> getDweller() {
        return dweller;
    }

    public OceanLife get(int index) {
        return dweller.get(index);
    }

    public void setDweller(ArrayList<OceanLife> dweller) {
        this.dweller = dweller;
    }

    @Override
    public String toString() {
        StringBuilder animalsArray = new StringBuilder();
        if (!dweller.isEmpty()) {
            animalsArray = new StringBuilder(" = {\n\t\t" + dweller.get(0).toString());
            for (int i = 1; i < dweller.size(); i++) {
                animalsArray.append("\n\t\t").append(dweller.get(i).toString());
            }
            animalsArray.append("\n\t}");
        }
        return this.getClass().getSimpleName() + " (" + "x = " + x + ", y = " + y + ")" + animalsArray;
    }
}
