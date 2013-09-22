/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.ActionManagment;

import Models.AgentAction;
import generated.KnowledgeBases;

/**
 *
 * @author admin
 */
public interface IActionManager {
 
    public void addToVisitedCells(String cellCoordinat);
    public AgentAction getNextAction(KnowledgeBases kBase);
    
}
