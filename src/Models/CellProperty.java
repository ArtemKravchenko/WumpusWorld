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

public class CellProperty {
    
    protected AgentLifeState _lifeState;
    
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
    
    public static int weight () {
        return 10000;
    }
    
    public List<String> getSentences(int row, int col) {
        List sentences = new ArrayList<>();
        
        sentences.add("!" + Pit.literal() + ":" + Helper.getStringFromRowAndCol(row, col));
        sentences.add("!" + Wumpus.literal() + ":" + Helper.getStringFromRowAndCol(row, col));
        sentences.add("!" + Breeze.literal() + ":" + Helper.getStringFromRowAndCol(row, col));
        sentences.add("!" + Stench.literal() + ":" + Helper.getStringFromRowAndCol(row, col));
        sentences.add("!" + Gold.literal() + ":" + Helper.getStringFromRowAndCol(row, col));
        sentences.add("!" + Glitter.literal() + ":" + Helper.getStringFromRowAndCol(row, col));
        
        return  sentences;
    }
}
