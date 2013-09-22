/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Logic.ActionManagment.IActionManager;
import Logic.InferenceAlgoritms.AlgorithmsManager;
import Logic.InferenceAlgoritms.IKBaseSupervisorDelegate;
import Logic.InferenceAlgoritms.FindNewStateAlgorithm;
import Models.Agent;
import Models.AgentAction;
import Models.AgentLifeState;
import Models.AgentMoveState;
import Models.CellProperty;
import Models.WorkSpaceCell;
import generated.KnowledgeBases;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class WumpusWorldGame implements IKBaseSupervisorDelegate {
    
    // main variables 
    private WorkSpaceCell[][] _workspace;
    private KnowledgeBases _knowledgeBase;
    
    private Agent _agent;
    private FindNewStateAlgorithm _algorithm;
    private IActionManager _actionManager;
    
    private int _stepCounter;
    
    private AlgorithmsManager _algorithmsManager;
    
    
    public WumpusWorldGame (WorkSpaceCell[][] workspace, KnowledgeBases knowledgeBase) {
        this._workspace = workspace;
        this._knowledgeBase = knowledgeBase;
        this._stepCounter = 1; 
        this._agent = new Agent();
        this.writeLog("Initialize a game parameters");
    }
    
    // Init algoritm method
    
    private void initAlgorithm () throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        String[] array = new String[] {"ModelCheckingAlgorithm"};
        this._algorithmsManager = new AlgorithmsManager(array);
        this._algorithm = this._algorithmsManager.getFirstAlgorithm();
        this._algorithm.setKBaseSupervisorDelegate((Logic.InferenceAlgoritms.IKBaseSupervisorDelegate) this);
    }
    
    // Playing game method
    public void starteGame() {
        this.writeLog("Game was started");
        
        this._agent.setRow(0);
        this._agent.setCol(0);
        this._agent.setCurrentState(AgentMoveState.FaceRight);
        
        this.printCurrentGameState();
    }
    
    public void simulateGame(){
        WorkSpaceCell cell;
        Boolean gameIsContinue = true;
        while (gameIsContinue) {
            cell = this._workspace[this._agent.getRow()][this._agent.getCol()];
            gameIsContinue = this.doNextStep(cell);
        }
    }
    
    public void tellKBasePercept(WorkSpaceCell cell) {
        
        for (CellProperty property: cell.getProperties()) {
            List<String> sentences = property.getSentences(cell.getRow(), cell.getCol());
            for (String sentence: sentences) {
                this.writeToKBase(sentence);
            }
        }
        
    }
    
    public Boolean doNextStep(WorkSpaceCell cell){
        if (!this._agent.isGoldFound()) {
            for (CellProperty property: cell.getProperties()) {
                if (property.getLifeState() == AgentLifeState.Dead) {
                    this.writeLog("You find the " + cell.toString() + "on cell: " + cell.getRow() + "," + cell.getCol() + " therefore you are dead");
                    return false;
                }
            }
            // First Step: Take percept from current cell and Tell KBase about percept
            this.tellKBasePercept(cell);
            // Generate all possible inference
            this._algorithm.execute(this._knowledgeBase);
            // Second Step: Ask KBase, what is the next action
            this._actionManager.addToVisitedCells(Helper.getStringFromRowAndCol(cell.getRow(), cell.getCol()));
            AgentAction nextAction = this._actionManager.getNextAction(this._knowledgeBase);
            // Third Step: Make the current action
            this._agent.setLastAction(nextAction);
                
            this.printCurrentGameState();
            
        } else {
            this.writeLog("Gold was founded on cell " + Helper.getStringFromRowAndCol(this._agent.getRow(), this._agent.getCol()) );
            return false;
        }
        return true;
    }
    
    private void writeToKBase(String sentence) {
            this._knowledgeBase.addSentence(sentence);
            this.writeLog("The sentence '" + sentence + "' was added to Knowledge Base");
    }
    
    public final void writeLog(String log) {
        System.out.println(log + " (step number " + this._stepCounter + ")");
        // TODO
    }
    
    public final void printCurrentGameState() {
        Helper.printCurrentTime();
        System.out.println();
        this._agent.printCurrentState();
        System.out.println("------------------------------------------");
    }

    @Override
    public void kBaseDidNotChange() {
        try {
            this._algorithm = this._algorithmsManager.changeCurrentAlgorithm();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(WumpusWorldGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
