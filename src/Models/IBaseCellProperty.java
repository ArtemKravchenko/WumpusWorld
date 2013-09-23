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
    
    public List<String> getSentences(int row, int col);
    public AgentLifeState getLifeState();
    
}
