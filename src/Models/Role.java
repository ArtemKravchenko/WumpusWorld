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
public class Role extends WorkSpaceCell {
    
    @Override
    public List<String> getSentences(int lastRow, int lastCol) {
        List sentences = new ArrayList<>();
        
        sentences.add(this.toString() + ":" + Helper.getStringFromRowAndCol(this._row, this._col));
        
        return sentences;
    }
    
}
