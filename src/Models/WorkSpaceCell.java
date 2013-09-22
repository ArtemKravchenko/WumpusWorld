/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Logic.Helper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class WorkSpaceCell {
    
    protected int _upBound;
    protected int _sideBound;
    protected int _row;
    protected int _col;
    protected List<CellProperty> _properties;

    
    public void setRow(int row) {
        this._row = row;
    }
    
    public void setCol(int col) {
        this._col = col;
    }
    
    public int getRow() {
        return this._row;
    }
    
    public int getCol() {
        return this._col;
    }
    
    public List<CellProperty> getProperties() {
        return this._properties;
    }
}
