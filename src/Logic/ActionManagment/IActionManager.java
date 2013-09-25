/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.ActionManagment;

import Models.Enums.AgentAction;
import generated.KnowledgeBases;
import java.util.HashMap;

/**
 *
 * @author admin
 */
public interface IActionManager {
 
    public void addToVisitedCells(String cellCoordinat);
    public AgentAction getNextAction(KnowledgeBases kBase);
    public HashMap<String, Integer> getDesiredCells();
    
}
