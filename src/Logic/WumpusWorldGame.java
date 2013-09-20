/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Models.Agent;
import Models.AgentLifeState;
import Models.WorkSpaceCell;
import generated.KnowledgeBases;

/**
 *
 * @author admin
 */
public class WumpusWorldGame {
    
    // main variables 
    private WorkSpaceCell[][] _workspace;
    private KnowledgeBases _knowledgeBase;
    
    private Agent _agent;
    private FindNewStateAlgorithm _algorithm;
    
    private int _stepCounter;
    
    public WumpusWorldGame (WorkSpaceCell[][] workspace, KnowledgeBases knowledgeBase) {
        this._workspace = workspace;
        this._knowledgeBase = knowledgeBase;
    }
    
    // Playing game method
    public void startGame() {
        this.writeLog("Game is started");
    }
    
    /*
     * Algorithm:
     * 1.) Take percept from neighborhood cells
     * 2.) Write new sentence in KBase
     * 3.) Make dission for steps
     * 4.) If current cell is gold - make grab, anothe - go to step 1.
     * 
    */
    public void simulateGame() {
        
    }
    
    public void tellKBasePercept(WorkSpaceCell cell) {
        
    }
    
    public void doNextStep(WorkSpaceCell cell) {
        
        this._stepCounter++;
        // TODO
        if (cell.getLifeState() != AgentLifeState.Dead) {
            
        } else {
            this.writeLog("You find the " + cell.toString() + "on cell: " + cell.getRow() + "," + cell.getCol() + " therefore you are deed");
        }
        
        this.writeLog("Did step number " + this._stepCounter);
    }
    
    public int getCurrentAgentRow() {
        return this._agent.getRow();
    }
    
    public int getCurrentAgentCol () {
        return this._agent.getCol();
    }
    
    private void writeToKBase(String expression) {
        this.writeLog("Expression: " + expression + "was be writed to KBase");
    }
    
    private void rewriteCurrentAgentPosition(int row, int col) {
        this.writeLog("Agent was steped to cell: " + row + "," + col);
        this._agent.setCol(col);
        this._agent.setRow(row);
    }
    
    public void writeLog(String log) {
        System.out.print(log);
        // TODO
    }
}
