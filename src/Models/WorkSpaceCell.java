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
    
    @Override
    public String toString() {
        return "WorkSpaceCell";
    }
    
    public static String literal() {
        return "WorkSpaceCell";
    }
    
    public List<String> getSentences(int lastRow, int lastCol) {
        List sentences = new ArrayList<>();
        
        sentences.add("!" + Pit.literal() + ":" + Helper.getStringFromRowAndCol(this._row, this._col));
        sentences.add("!" + Wumpus.literal() + ":" + Helper.getStringFromRowAndCol(this._row, this._col));
        sentences.add("!" + Breeze.literal() + ":" + Helper.getStringFromRowAndCol(this._row, this._col));
        sentences.add("!" + Stench.literal() + ":" + Helper.getStringFromRowAndCol(this._row, this._col));
        sentences.add("!" + Gold.literal() + ":" + Helper.getStringFromRowAndCol(this._row, this._col));
        sentences.add("!" + Glitter.literal() + ":" + Helper.getStringFromRowAndCol(this._row, this._col));
        
        return  sentences;
    }
}
