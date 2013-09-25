/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Logic.ActionManagment.IActionManager;
import Logic.ActionManagment.SimpleActionManager;
import Logic.Helper;
import Logic.InferenceAlgoritms.AbstractInferenceAlgorithm;
import Logic.InferenceAlgoritms.AlgorithmsManager;
import Logic.InferenceAlgoritms.IKBaseSupervisorDelegate;
import Logic.WumpusWorldGame;
import Models.Enums.AgentMoveState;
import Models.Enums.AgentAction;
import Models.Abstract.AbstractAgent;
import Models.Abstract.IAgentDelegate;
import Models.Enums.AgentLifeState;
import Models.Symptoms.Symptom;
import generated.KnowledgeBases;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 *
 * @author admin
 */
public class Agent extends AbstractAgent implements IKBaseSupervisorDelegate {

    // Variables
    private AgentMoveState _currentState;
    private Boolean _arrow;

    public Agent(KnowledgeBases kBase, IActionManager manager) {
        this._cell = new BaseWorkSpaceCell();
        this._currentState = AgentMoveState.FaceRight;
        this._kBase = kBase;
        this._actionManager = manager;
        try {
            // init algorithm
            this.initAlgorithm();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Init methods
    private void initAlgorithm() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()));
        Set<Class<? extends AbstractInferenceAlgorithm>> allAlgoritms = reflections.getSubTypesOf(AbstractInferenceAlgorithm.class);
  
        this._algoritmManager = new AlgorithmsManager(allAlgoritms.toArray());
        this._inferenceAlgorithm = this._algoritmManager.getFirstAlgorithm();
        this._inferenceAlgorithm.setKBaseSupervisorDelegate((Logic.InferenceAlgoritms.IKBaseSupervisorDelegate) this);
    }

    // Methods
    public void pushTheArrow() {
        this._arrow = false;
    }

    public Boolean arrowIsStillContaining() {
        return this._arrow;
    }

    public void makeAction(AgentAction action) {
        
        if (action == AgentAction.Grap) {
            this._delegate.targetWasReached(this._target);
        } else if (action == AgentAction.MoveForward) {
            if (this._currentState == AgentMoveState.FaceDown) {
                this._cell.setY(this._cell.getY() - 1);
            } else if (this._currentState == AgentMoveState.FaceLeft) {
                this._cell.setX(this._cell.getX() - 1);
            } else if (this._currentState == AgentMoveState.FaceRight) {
                this._cell.setX(this._cell.getX() + 1);
            } else {
                this._cell.setY(this._cell.getY() + 1);
            }
        } else if (action == AgentAction.Shoot) {
            this._arrow = false;
        } else {
            this._currentState = Helper.getStateAfterAction(action, this._currentState);
        }
         
    }

    // Logic
    public void tellKBasePercept(BaseWorkSpaceCell cell) {
        for (IBaseCellProperty property : cell.getProperties()) {
            List<String> sentences = property.getSentences(cell.getY(), cell.getX());
            for (String sentence : sentences) {
                this.writeToKBase(sentence);
            }
        }
    }

    @Override
    public void doNextStep(int stepCounter) {
        for (IBaseCellProperty property : ((BaseWorkSpaceCell)this._cell).getProperties()) {
            if (property.getLifeState() == AgentLifeState.Dead) {
                this.writeLog("You find the " + this._cell.toString() + "on cell: " + this._cell.getY() + "," + this._cell.getX() + " therefore you are dead " + "(step number: " + stepCounter + ")");
                this._delegate.agentWasKilled(this);
                return;
            }
        }
        // First Step: Take percept from current cell and Tell KBase about percept
        this.tellKBasePercept((BaseWorkSpaceCell)this._cell);
        // Generate all possible inferenc
        this._inferenceAlgorithm.setDesiredCells((List<String>) this._actionManager.getDesiredCells().keySet());
        this._inferenceAlgorithm.execute(this._kBase);
        // Second Step: Ask KBase, what is the next action
        ((SimpleActionManager)this._actionManager).setCurrentCell(Helper.getStringFromRowAndCol(this._cell.getY(), this._cell.getX()));
        ((SimpleActionManager)this._actionManager).setAgentMoveState(this._currentState);
        this._actionManager.addToVisitedCells(Helper.getStringFromRowAndCol(this._cell.getY(), this._cell.getX()));
        AgentAction nextAction = this._actionManager.getNextAction(this._kBase);
        // Third Step: Make the current action
        this.makeAction(nextAction);

        this.printCurrentState();

    }
    
    public final void writeLog(String log) {
        System.out.println(log);
        // TODO
    }

    @Override
    public void printCurrentState() {
    }

    // Overrides methods
    @Override
    public void setDelegate(IAgentDelegate delegate) {
        this._delegate = delegate;
    }

    @Override
    public int getX() {
        return this._cell.getX();
    }

    @Override
    public int getY() {
        return this._cell.getY();
    }

    @Override
    public void setX(int x) {
        this._cell.setX(x);
    }

    @Override
    public void setY(int y) {
        this._cell.setY(y);
    }

    @Override
    public void kBaseDidNotChange() {
        try {
            this._inferenceAlgorithm = this._algoritmManager.changeCurrentAlgorithm();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(WumpusWorldGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void writeToKBase(String sentence) {
        this._kBase.addSentence(sentence);
        this.writeLog("The sentence '" + sentence + "' was added to Knowledge Base");
    }
}
