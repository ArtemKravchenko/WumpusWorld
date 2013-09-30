/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Roles;

import Logic.Helper;
import Models.Enums.AgentLifeState;
import Models.IBaseCellProperty;
import Models.Symptoms.Symptom;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class Role implements IBaseCellProperty {
    
    
    @Override
    public List<String> getPositiveSentences(int row, int col) {
        List<String> sentences = new ArrayList<>();
        
        sentences.add(Helper.getEntityNameFromClass(this.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, col));
        
        return sentences;
    }

    @Override
    public AgentLifeState getLifeState() {
        return AgentLifeState.Dead;
    }

    @Override
    public List<String> getNegativeSentences(int row, int col) {
        List<String> sentences = new ArrayList<>();
        
        sentences.add("!" + Helper.getEntityNameFromClass(this.getClass().getName()) + ":" + Helper.getStringFromRowAndCol(row, col));
        
        return sentences;
    }
    
    public Symptom getSymptom() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String[] symptoms = Helper.getAllSymptoms();
        for (String s: symptoms) {
            Symptom symptom = (Symptom)Class.forName(s).newInstance();
            if (symptom.getRole().getClass().equals(this.getClass())) {
                return symptom;
            }
        }
        return null;
    }
    
}
