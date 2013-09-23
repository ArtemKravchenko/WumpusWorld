/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Symptoms;

import Logic.Helper;
import Models.EmptyCellProperty;
import Models.Enums.AgentLifeState;
import Models.IBaseCellProperty;
import Models.Roles.Role;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class Symptom implements IBaseCellProperty {
    
    protected Role _role;
    
    public Role getRole () {
        return this._role;
    }
    
    @Override
    public List<String> getSentences(int row, int col) {
    
        List sentences = new ArrayList<> ();
        
        // Add Literals
        String sentence = "";
        
        sentence += Helper.getEntityNameFromClass(this.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, col);
        sentences.add(sentence);
        
        // Add expression
        sentence = "";
        Boolean needConjuction = false;
        
        sentence += Helper.getEntityNameFromClass(this.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, col);
        sentence += "=>";
        
        int rightCell = row + 1;
        int upCell = col + 1;
        int leftCell = row - 1;
        int downCell = col - 1;
        
        sentence += Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, rightCell);

        sentence += Helper.getConjuction();
        sentence += Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(upCell, col);
        
        sentence += Helper.getConjuction();
        sentence += Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, leftCell);
       
        sentence += Helper.getConjuction();
        sentence += Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(downCell, col);
        
        sentences.add(sentence);
        
        return sentences;
    }

    @Override
    public AgentLifeState getLifeState() {
        return AgentLifeState.Alive;
    }
    
}
