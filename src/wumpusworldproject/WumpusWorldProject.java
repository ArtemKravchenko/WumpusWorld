/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworldproject;

import Logic.ActionManagment.IActionManager;
import Logic.ActionManagment.SimpleActionManager;
import Logic.WumpusWorldGame;
import Models.Agent;
import Models.BaseWorkSpace;
import Models.BaseWorkSpaceCell;
import Models.Gold;
import Models.Roles.Pit;
import Models.Roles.Wumpus;
import generated.KnowledgeBases;

/**
 *
 * @author admin
 */
public class WumpusWorldProject {

    
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        initGame();
       
    }
    
    static void initGame() throws ClassNotFoundException, InstantiationException, IllegalAccessException{ 
        // Init kBase
        
        KnowledgeBases kBase = new KnowledgeBases();
        IActionManager manager = new SimpleActionManager();
        
        Agent agent = new Agent(kBase, manager);
        BaseWorkSpace workSpace  = new BaseWorkSpace();
        
        BaseWorkSpaceCell cell = null;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cell = new BaseWorkSpaceCell();
                cell.setCurrentCell(i, j);
                workSpace.addWorkCell(cell);
            }
        }
        
        workSpace.addPropertyToCell(2, 0, new Wumpus());
        workSpace.addPropertyToCell(0, 2, new Pit());
        workSpace.addPropertyToCell(2, 2, new Gold());
        workSpace.printCurrentState();
        
        WumpusWorldGame game = new WumpusWorldGame(workSpace, agent);
        game.starteGame();
        game.simulateGame();
    }
}
