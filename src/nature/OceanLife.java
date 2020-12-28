package nature;

import exceptions.AddToCellException;
import exceptions.DeleteFromCellException;
import exceptions.MovingException;
import world.Cell;
import world.OceanMap;
import world.Rule;

public abstract class OceanLife {
    private final int distance;
    private Cell cell;

    public OceanLife(Cell cell) throws AddToCellException {
        this.distance = getDistance();
        this.cell = cell;
        cell.add(this);
    }

    public void moveTo(OceanMap map, Cell cell) throws MovingException {
        if (!Rule.canMoveTo(this, cell)) throw new MovingException("This creature can't move to the cell.");
        try {
            Cell current = map.getCell(this.getCell().getX(), this.getCell().getY());
            cell.add(this);
            current.delete(this);
            this.cell = cell;
        } catch (AddToCellException e) {
            throw new MovingException("This creature can't move to the cell.", e);
        } catch (DeleteFromCellException e) {
            throw new MovingException("This creature can't move from the cell", e);
        }
    }

    public abstract boolean dying();

    public abstract int getDistance();

    public Cell getCell() {
        return cell;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
