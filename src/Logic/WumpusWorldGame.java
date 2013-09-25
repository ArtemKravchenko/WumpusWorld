/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Models.Abstract.AbstractAgent;
import Models.Abstract.AbstractEnviropment;
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
        
        this._agent.setDelegate(this);
        
        this._stepCounter = 0; 
        this._agent = agent;
        this._workSpace = workSpace;
        this.writeLog("Initialize a game parameters");
    }
    
    // Playing game method
    public void starteGame() {
        this.writeLog("Game was started");
        
        this._agent.setY(0);
        this._agent.setX(0);
        
        this.printCurrentGameState();
    }
    
    public void doNextStep() {
        this._agent.doNextStep(this._stepCounter++);
        this.printCurrentGameState();
    }
    
    public void simulateGame(){
        if (this._goldWasFound) {
            this.writeLog("The gold was found!");
            return;
        }
        if (this._agentWasKilled) {
            this.writeLog("The agent was killed!");
            return;
        }
        this.doNextStep();
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
}
