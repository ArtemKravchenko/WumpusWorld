/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Models.Abstract.AbstractAgent;
import Models.Abstract.AbstractEnviropment;
import Models.Abstract.IAgentDelegate;
import Models.Abstract.ITarget;
import Models.Agent;
import Models.BaseWorkSpace;

/**
 *
 * @author admin
 */
public class WumpusWorldGame extends AbstractEnviropment {
   
    private int _stepCounter;
    private Boolean _goldWasFound;
    private Boolean _agentWasKilled;
    
    public WumpusWorldGame (BaseWorkSpace workSpace, Agent agent) {
        this._agentWasKilled = false;
        this._goldWasFound = false;
        
        this._agent = agent;
        this._agent.setDelegate((IAgentDelegate)this);
        
        this._stepCounter = 0; 
        this._workSpace = workSpace;
        this.writeLog("Initialize a game parameters");
    }
    
    // Playing game method
    public void starteGame() {
        this.writeLog("Game was started");
        
        this._agent.setCurrentCell(((BaseWorkSpace)this._workSpace).getWorkSpaceCell(0, 0));
        
        this.printCurrentGameState();
    }
    
    public void doNextStep() {
        this._agent.doNextStep(this._stepCounter++);
        this.printCurrentGameState();
    }
    
    public void simulateGame(){
        while (!this._goldWasFound || !this._agentWasKilled) {
            this.doNextStep();
        }
        if (this._goldWasFound) {
            this.writeLog("The gold was found!");
            return;
        }
        if (this._agentWasKilled) {
            this.writeLog("The agent was killed!");
            return;
        }
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
    public void agentWasKilled(AbstractAgent agent) {
        this._agentWasKilled = true;
    }

    @Override
    public void targetWasReached(ITarget target) {
        this._goldWasFound = true;
    }

    @Override
    public void setAgentCell(int y, int x) {
        this._agent.setCurrentCell(((BaseWorkSpace)this._workSpace).getWorkSpaceCell(y, x));
    }
}
