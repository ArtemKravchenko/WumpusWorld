/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Roles;

import Logic.Helper;
import Models.Enums.AgentLifeState;
import Models.IBaseCellProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class Role implements IBaseCellProperty {
    
    @Override
    public List<String> getSentences(int row, int col) {
        List sentences = new ArrayList<>();
        
        sentences.add(Helper.getEntityNameFromClass(this.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, col));
        
        return sentences;
    }

    @Override
    public AgentLifeState getLifeState() {
        return AgentLifeState.Dead;
    }
    
}
