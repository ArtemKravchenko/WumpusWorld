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
import java.util.logging.Level;
import java.util.logging.Logger;

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
        sentence += "<=>";
        
        int rightCell = col + 1;
        int upCell = row + 1;
        int leftCell = col - 1;
        int downCell = row - 1;
        
        if (rightCell >= 0 && rightCell < 4) {
            sentence += Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, rightCell);
            sentence += Helper.getDisjuction();
        }
        if (upCell >= 0 && upCell < 4) {
            sentence += Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(upCell, col);
            sentence += Helper.getDisjuction();
        }
        if (leftCell >= 0 && leftCell < 4) { 
            sentence += Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, leftCell);
            sentence += Helper.getDisjuction();
        }
        if (downCell >= 0 && downCell < 4) {
            sentence += Helper.getEntityNameFromClass(this._role.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(downCell, col);
        } else {
            String lastSymbol = sentence.substring(sentence.length() - Helper.getDisjuction().length(), sentence.length());
            if (lastSymbol.equals(Helper.getDisjuction())) {
                sentence = sentence.substring(0, sentence.length() - Helper.getDisjuction().length());
            }
        }
   
        sentences.add(sentence);
        
        String[] symptoms = Helper.getAllSymptoms();
        for (String symptom: symptoms) {
            if (!symptom.equals(this.getClass().getName())) {
                try {
                    Symptom cs = (Symptom) Class.forName(symptom).newInstance();
                    List<String> anotherSentences =  cs.getNegativeSentences(row, col);
                    sentences.addAll(anotherSentences);
                } catch (        ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(Symptom.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
    
}
