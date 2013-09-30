/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Logic.Helper;
import Models.Abstract.AbstractWorkSpace;
import Models.Abstract.AbstractWorkSpaceCell;
import Models.Abstract.IPrintState;
import Models.Roles.Role;
import java.util.List;

/**
 *
 * @author admin
 */
public class BaseWorkSpace extends AbstractWorkSpace implements IPrintState {

    @Override
    public List<AbstractWorkSpaceCell> getWorkSpaceCells() {
        return this._workSpaceCells;
    }
    
    public BaseWorkSpaceCell getCell(String cell) {
        for (int i = 0; i < this._workSpaceCells.size(); i++) {
            if (Helper.getStringFromRowAndCol(this._workSpaceCells.get(i).getY(), this._workSpaceCells.get(i).getX()).equals(cell)) {
                return (BaseWorkSpaceCell)this._workSpaceCells.get(i);
            }
        }
        return null;
    }

    @Override
    public void addWorkCell(AbstractWorkSpaceCell cell) {
        this._workSpaceCells.add(cell);
    }
    
    public AbstractWorkSpaceCell getWorkSpaceCell(int y, int x) {
        for (AbstractWorkSpaceCell cell: this._workSpaceCells) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell;
            }
        }
        return null;
    }
    
    public void addPropertyToCell(int row, int col, Role property) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        int rightCell = col + 1;
        int upCell = row + 1;
        int leftCell = col - 1;
        int downCell = row - 1;
        for (int i = 0; i < this._workSpaceCells.size(); i++) {
            AbstractWorkSpaceCell cell = this._workSpaceCells.get(i);
            if (cell.getX() == col && cell.getY() == row) {
                ((BaseWorkSpaceCell)cell).addProperty(property);
                continue;
            }
            if (cell.getX() == rightCell && cell.getY() == row && rightCell >= 0 && rightCell < 4) {
                ((BaseWorkSpaceCell)cell).addProperty(((Role)property).getSymptom());
                continue;
            }
            if (cell.getX() == col && cell.getY() == upCell && upCell >= 0 && upCell < 4) {
                ((BaseWorkSpaceCell)cell).addProperty(((Role)property).getSymptom());
                continue;
            }
            if (cell.getX() == leftCell && cell.getY() == row && leftCell >= 0 && leftCell < 4) {
                ((BaseWorkSpaceCell)cell).addProperty(((Role)property).getSymptom());
                continue;
            }
            if (cell.getX() == col && cell.getY() == downCell && downCell >= 0 && downCell < 4) {
                ((BaseWorkSpaceCell)cell).addProperty(((Role)property).getSymptom());
                continue;
            }
         }
    }

    @Override
    public void printCurrentState() {
        this.writeLog("----- Workspace (State) -----");
        this.writeLog("Workspace cells :" + this._workSpaceCells.size());
        for (AbstractWorkSpaceCell cell : this._workSpaceCells) {
            this.writeLog(Helper.getStringFromRowAndCol(cell.getY(), cell.getX()));
            if (!((BaseWorkSpaceCell)cell)._properties.isEmpty()) {
                this.writeLog("  Cell properties: " + ((BaseWorkSpaceCell)cell)._properties.size());
                for (IBaseCellProperty property: ((BaseWorkSpaceCell)cell)._properties) {
                    this.writeLog("   " + Helper.getEntityNameFromClass(property.getClass().getName()));
                }
            }
        }
    }

    @Override
    public void writeLog(String log) {
        System.out.println(log);
        // TODO 
    }
}
