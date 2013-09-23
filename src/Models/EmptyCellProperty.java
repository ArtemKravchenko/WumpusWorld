/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Roles.Wumpus;
import Models.Roles.Pit;
import Models.Symptoms.Stench;
import Models.Symptoms.Breeze;
import Models.Symptoms.Glitter;
import Models.Enums.AgentLifeState;
import Logic.Helper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */

public class EmptyCellProperty implements IBaseCellProperty {
    
    @Override
    public AgentLifeState getLifeState() {
        return AgentLifeState.Alive;
    }
    
    public static int weight () {
        return 10000;
    }
    
    @Override
    public List<String> getSentences(int row, int col) {
        List sentences = new ArrayList<>();
        
        sentences.add("!" + Helper.getEntityNameFromClass(Pit.class.getName()) + ":" + Helper.getStringFromRowAndCol(row, col));
        sentences.add("!" + Helper.getEntityNameFromClass(Wumpus.class.getName())+ ":" + Helper.getStringFromRowAndCol(row, col));
        sentences.add("!" + Helper.getEntityNameFromClass(Breeze.class.getName()) + ":" + Helper.getStringFromRowAndCol(row, col));
        sentences.add("!" + Helper.getEntityNameFromClass(Stench.class.getName()) + ":" + Helper.getStringFromRowAndCol(row, col));
        sentences.add("!" + Helper.getEntityNameFromClass(Gold.class.getName()) + ":" + Helper.getStringFromRowAndCol(row, col));
        sentences.add("!" + Helper.getEntityNameFromClass(Glitter.class.getName()) + ":" + Helper.getStringFromRowAndCol(row, col));
        
        return  sentences;
    }
}
