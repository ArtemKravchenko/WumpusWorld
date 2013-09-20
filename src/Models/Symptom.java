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
public class Symptom extends WorkSpaceCell {
    
    protected Role _role;
    
    public Role getRole () {
        return this._role;
    }
    
    @Override
    public List<String> getSentences(int lastRow, int lastCol) {
    
        List sentences = new ArrayList<> ();
        
        // Add Literals
        String sentence = "";
        
        sentence += this.toString() + ":" + Helper.getStringFromRowAndCol(this._row, this._col);
        sentences.add(sentence);
        
        // Add expression
        sentence = "";
        Boolean needConjuction = false;
        
        sentence += this.toString() + ":" + Helper.getStringFromRowAndCol(this._row, this._col);
        sentence += "=>";
        
        int rightCell = this._row + 1;
        int upCell = this._col + 1;
        int leftCell = this._row - 1;
        int downCell = this._col - 1;
        
        if (rightCell != lastRow && rightCell < this._sideBound) {
            sentence += this._role.toString() + ":" + Helper.getStringFromRowAndCol(rightCell, this._col);
            needConjuction = true;
        }
        if (upCell != lastRow && upCell < this._upBound) {
            if (needConjuction) {
                sentence += "(con)";
            }
            sentence += this._role.toString() + ":" + Helper.getStringFromRowAndCol(this._row, upCell);
        }
        if (leftCell != lastRow && leftCell > 0) {
            if (needConjuction) {
                sentence += "(con)";
            }
            sentence += this._role.toString() + ":" + Helper.getStringFromRowAndCol(leftCell, this._col);
        }
        if (downCell != lastCol && downCell > 0) {
            if (needConjuction) {
                sentence += "(con)";
            }
            sentence += this._role.toString() + ":" + Helper.getStringFromRowAndCol(this._row, downCell);
        }
        
        sentences.add(sentence);
        
        return sentences;
    }
    
}
