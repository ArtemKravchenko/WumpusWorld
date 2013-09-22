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
public class Role extends CellProperty {
    
    @Override
    public List<String> getSentences(int row, int col) {
        List sentences = new ArrayList<>();
        
        sentences.add(this.toString() + ":" + Helper.getStringFromRowAndCol(row, col));
        
        return sentences;
    }
    
}
