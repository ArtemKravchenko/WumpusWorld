/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Roles.Role;
import Models.Symptoms.Symptom;
import Models.Enums.AgentLifeState;
import Logic.Helper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public List<String> getPositiveSentences(int row, int col) {
        List sentences = new ArrayList<>();
        /*
        String[] roles = Helper.getAllRoles();
        String[] symptoms = Helper.getAllSymptoms();
        
        for (String role: roles) {
            List<String> tmpSentences = null;
            try {
                tmpSentences = ((Role)Class.forName(role).newInstance()).getNegativeSentences(row, col);
            } catch (    ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(EmptyCellProperty.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (String sentence: tmpSentences) {
                sentences.add(sentence);
            }
        }
        
        for (String symptom: symptoms) {
            List<String> tmpSentences = null;
            try {
                if (!symptom.equals(Helper.getEntityNameFromClass(Gold.class.getName()))) {
                    tmpSentences = ((Symptom)Class.forName(symptom).newInstance()).getNegativeSentences(row, col);
                }
            } catch (    ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(EmptyCellProperty.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (String sentence: tmpSentences) {
                sentences.add(sentence);
            }
        }
        */
        return  sentences;
    }

    @Override
    public List<String> getNegativeSentences(int row, int col) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
