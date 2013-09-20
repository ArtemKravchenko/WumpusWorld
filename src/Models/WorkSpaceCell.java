/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author admin
 */

public class WorkSpaceCell {
    
    protected AgentLifeState _lifeState;
    protected int _row;
    protected int _col;
    
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
    
    public AgentLifeState getLifeState() {
        return this._lifeState;
    }
    
    public @Override String toString() {
        return "WorkSpaceCell";
    }
    
}
