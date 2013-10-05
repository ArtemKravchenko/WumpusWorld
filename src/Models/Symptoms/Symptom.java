/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Symptoms;

import Logic.Helper;
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
    public List<String> getPositiveSentences(int row, int col) {
    
        List sentences = new ArrayList<> ();
        
        // Add Literals
        String sentence = "";
        
        sentence += Helper.getEntityNameFromClass(this.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, col);
        sentences.add(sentence);
        
        // Add expression
        sentence = "";
        
        sentence += Helper.getEntityNameFromClass(this.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, col);
        sentence += "=>";
        
        int rightCell = col + 1;
        int upCell = row + 1;
        int leftCell = col - 1;
        int downCell = row - 1;
        int counter = 0;
        
        if (rightCell >= 0 && rightCell < 4) {
            sentence += Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, rightCell);
            sentence += Helper.getDisjuction();
            counter++;
        }
        if (upCell >= 0 && upCell < 4) {
            sentence += Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(upCell, col);
            sentence += Helper.getDisjuction();
            counter++;
        }
        if (leftCell >= 0 && leftCell < 4) { 
            sentence += Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, leftCell);
            sentence += Helper.getDisjuction();
            counter++;
        }
        if (downCell >= 0 && downCell < 4) {
            sentence += Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(downCell, col);
            counter++;
        } else {
            String lastSymbol = sentence.substring(sentence.length() - Helper.getDisjuction().length(), sentence.length());
            if (lastSymbol.equals(Helper.getDisjuction())) {
                sentence = sentence.substring(0, sentence.length() - Helper.getDisjuction().length());
            }
        }
   
        if (counter == 1) {
            String[] array = sentence.split("\\=>");
            sentences.add(array[1]);
        } else {
            sentences.add(sentence);    
        }
             
        return sentences;
    }

    @Override
    public AgentLifeState getLifeState() {
        return AgentLifeState.Alive;
    }
    @Override
    public List<String> getNegativeSentences(int row, int col) {
        List sentences = new ArrayList<> ();
        
        String sentence = "";
        
        sentence += "!" + Helper.getEntityNameFromClass(this.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, col);
        sentences.add(sentence);
        
        int rightCell = col + 1;
        int upCell = row + 1;
        int leftCell = col - 1;
        int downCell = row - 1;
        
        if (rightCell >= 0 && rightCell < 4) {
            sentences.add("!" + Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, rightCell));
        }
        if (upCell >= 0 && upCell < 4) {
            sentences.add("!" + Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(upCell, col));
        }
        if (leftCell >= 0 && leftCell < 4) { 
            sentences.add("!" + Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, leftCell));
        }
        if (downCell >= 0 && downCell < 4) {
            sentences.add("!" + Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(downCell, col));
        } 
        
        sentences.add(sentence);
        
        return sentences;
    }
    
    /*
    @Override
    public List<String> getNegativeSentences(int row, int col) {
        List sentences = new ArrayList<> ();
        
        String sentence = "";
        
        sentence += "!" + Helper.getEntityNameFromClass(this.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, col);
        sentences.add(sentence);
        // Add expression
        sentence = "";
        
        sentence += "!" + Helper.getEntityNameFromClass(this.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, col);
        sentence += "<=>";
        
        int rightCell = col + 1;
        int upCell = row + 1;
        int leftCell = col - 1;
        int downCell = row - 1;
        
        if (rightCell >= 0 && rightCell < 4) {
            sentence += "!" + Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, rightCell);
            sentence += Helper.getConjuction();
        }
        if (upCell >= 0 && upCell < 4) {
            sentence += "!" + Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(upCell, col);
            sentence += Helper.getConjuction();
        }
        if (leftCell >= 0 && leftCell < 4) { 
            sentence += "!" + Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, leftCell);
            sentence += Helper.getConjuction();
        }
        if (downCell >= 0 && downCell < 4) {
            sentence += "!" + Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(downCell, col);
        } else {
            String lastSymbol = sentence.substring(sentence.length() - Helper.getConjuction().length(), sentence.length());
            if (lastSymbol.equals(Helper.getConjuction())) {
                sentence = sentence.substring(0, sentence.length() - Helper.getConjuction().length());
            }
        }
        
        sentences.add(sentence);
        
        return sentences;
    }
    */
}
