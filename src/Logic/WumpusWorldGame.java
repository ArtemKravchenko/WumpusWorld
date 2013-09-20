/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Models.Agent;
import Models.AgentAction;
import Models.AgentLifeState;
import Models.AgentMoveState;
import Models.Gold;
import Models.WorkSpaceCell;
import Models.Wumpus;
import generated.KnowledgeBases;
import java.util.ArrayList;
import java.util.List;

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
    
    private List<String> _avalibleAndNotAttendedCellsForSteps;
    
    private List<AgentAction> _actionsArray;
    
    public WumpusWorldGame (WorkSpaceCell[][] workspace, KnowledgeBases knowledgeBase) {
        this._workspace = workspace;
        this._knowledgeBase = knowledgeBase;
        this._stepCounter = 1; 
        this._avalibleAndNotAttendedCellsForSteps = new ArrayList<>();
        this._agent = new Agent();
        this._actionsArray = new ArrayList<>();
        this.writeLog("Initialize a game parameters");
    }
    
    // Playing game method
    public void starteGame() {
        this.writeLog("Game was started");
        
        this._agent.setRow(0);
        this._agent.setCol(0);
        this._agent.setCurrentState(AgentMoveState.FaceRight);
        
        this._avalibleAndNotAttendedCellsForSteps.add(Helper.getStringFromRowAndCol(0,1));
        this._avalibleAndNotAttendedCellsForSteps.add(Helper.getStringFromRowAndCol(1,0));
        this.printCurrentGameState();
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
        List<String> sentences = cell.getSentences(cell.getRow(), cell.getCol());
        for (String sentence: sentences) {
            this._knowledgeBase.addSentence(sentence);
            this.writeLog("The sentence '" + sentence + "' was added to Knowledge Base");
        }
    }
    
    public void doNextStep(WorkSpaceCell cell) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (!this._agent.isGoldFound()) {
            if (cell.getLifeState() != AgentLifeState.Dead) {
                this.tellKBasePercept(cell);
                if (this._actionsArray.isEmpty()) {
                    this._algorithm.execute();
                    this.prepeareActionArrayForAccesCell();  
                } 
                AgentAction currentAction = this._actionsArray.get(0);
                this._actionsArray.remove(0);
                this._agent.setLastAction(currentAction);
                
                this.printCurrentGameState();
            } else {
                this.writeLog("You find the " + cell.toString() + "on cell: " + cell.getRow() + "," + cell.getCol() + " therefore you are dead");
            }
        } else {
            this.writeLog("Gold was founded on cell " + Helper.getStringFromRowAndCol(this._agent.getRow(), this._agent.getCol()) );
        }
    }
    
    private void prepeareActionArrayForAccesCell() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        List<String> sentences = this._knowledgeBase.getSentences();
        String[] array;
        String[] sentenceArray;
        for (String sentence: sentences) {
            array = sentence.split("<=>");
            if (array.length == 1) {
                array = sentence.split("=>");
                if (array.length == 1) {
                    array = sentence.split(":");
                    for (String availableCell : this._avalibleAndNotAttendedCellsForSteps) {
                        if (array[1].equals(availableCell)) {
                            sentenceArray = sentence.split(":");
                            String cellStringType = sentenceArray[0].replace("!", "");
                            Class c = Class.forName(cellStringType);
                            WorkSpaceCell cell = (WorkSpaceCell) c.newInstance();
                            if (cell.toString().equals(Gold.literal())) {
                                
                            } else if (cell.toString().equals(Wumpus.literal())) {
                                
                            } else {
                                
                            }
                        }
                    }
                }
            } 
        }
    }
    
    private AgentAction chooseTheActionDependOnCell(WorkSpaceCell cell) {
        
        
        return null;
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
    
    public final void writeLog(String log) {
        System.out.println(log + " (step number " + this._stepCounter + ")");
        // TODO
    }
    
    public final void printCurrentGameState() {
        this.writeLog("Agent stay on cell " + Helper.getStringFromRowAndCol(this._agent.getRow(), this._agent.getCol()));
        
        this.writeLog("Current cells are available and unsteped");
        for (String string: this._avalibleAndNotAttendedCellsForSteps) {
            System.out.println(string);
        }
        
        System.out.println("------------------------------------------");
    }
}
