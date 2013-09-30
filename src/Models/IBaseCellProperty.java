/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Enums.AgentLifeState;
import java.util.List;

/**
 *
 * @author admin
 */
public interface IBaseCellProperty {
    
    public List<String> getPositiveSentences(int row, int col);
    public List<String> getNegativeSentences(int row, int col);
    public AgentLifeState getLifeState();
    
}
