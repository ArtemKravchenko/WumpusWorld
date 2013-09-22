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
public class Symptom extends CellProperty {
    
    protected Role _role;
    
    public Role getRole () {
        return this._role;
    }
    
    @Override
    public List<String> getSentences(int row, int col) {
    
        List sentences = new ArrayList<> ();
        
        // Add Literals
        String sentence = "";
        
        sentence += this.toString() + ":" + Helper.getStringFromRowAndCol(row, col);
        sentences.add(sentence);
        
        // Add expression
        sentence = "";
        Boolean needConjuction = false;
        
        sentence += this.toString() + ":" + Helper.getStringFromRowAndCol(row, col);
        sentence += "=>";
        
        int rightCell = row + 1;
        int upCell = col + 1;
        int leftCell = row - 1;
        int downCell = col - 1;
        
        sentence += this._role.toString() + ":" + Helper.getStringFromRowAndCol(row, rightCell);

        sentence += "(con)";
        sentence += this._role.toString() + ":" + Helper.getStringFromRowAndCol(upCell, col);
        
        sentence += "(con)";
        sentence += this._role.toString() + ":" + Helper.getStringFromRowAndCol(row, leftCell);
       
        sentence += "(con)";
        sentence += this._role.toString() + ":" + Helper.getStringFromRowAndCol(downCell, col);
        
        sentences.add(sentence);
        
        return sentences;
    }
    
}
