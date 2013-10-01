/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Logic.ActionManagment.IActionManager;
import Logic.ActionManagment.SimpleActionManager;
import Logic.Helper;
import Logic.InferenceAlgoritms.AlgorithmsManager;
import Logic.InferenceAlgoritms.IKBaseSupervisorDelegate;
import Logic.WumpusWorldGame;
import Models.Enums.AgentMoveState;
import Models.Enums.AgentAction;
import Models.Abstract.AbstractAgent;
import Models.Abstract.AbstractWorkSpaceCell;
import Models.Abstract.IAgentDelegate;
import Models.Enums.AgentLifeState;
import Models.Roles.Role;
import Models.Symptoms.Symptom;
import com.google.common.collect.Lists;
import generated.KnowledgeBases;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        this._arrow = true;
        try {
            // init algorithm
            this.initAlgorithm();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Init methods
    private void initAlgorithm() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String[] array = Helper.getAllAlgorithms();

        this._algoritmManager = new AlgorithmsManager(array);
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
                this._delegate.setAgentCell(this._cell.getY() - 1, this._cell.getX());
            } else if (this._currentState == AgentMoveState.FaceLeft) {
                this._delegate.setAgentCell(this._cell.getY(), this._cell.getX() - 1);
            } else if (this._currentState == AgentMoveState.FaceRight) {
                this._delegate.setAgentCell(this._cell.getY(), this._cell.getX() + 1);
            } else {
                this._delegate.setAgentCell(this._cell.getY() + 1, this._cell.getX());
            }
        } else if (action == AgentAction.Shoot) {
            this._arrow = false;
        } else {
            this._currentState = Helper.getStateAfterAction(action, this._currentState);
        }
         
    }

    // Logic
    public void tellKBasePercept(BaseWorkSpaceCell cell) {
        List<String> symptoms = new ArrayList<>(Arrays.asList(Helper.getAllSymptoms()));
        List<String> roles = new ArrayList<>(Arrays.asList(Helper.getAllRoles()));
  
        for (IBaseCellProperty property : cell.getProperties()) {
            String propertyString = property.getClass().toString().replace("class ", "");
            if (symptoms.contains(propertyString)) {
                symptoms.remove(propertyString);
            }
            if (roles.contains(propertyString)) {
                roles.remove(propertyString);
            }
            List<String> sentences = property.getPositiveSentences(cell.getY(), cell.getX());
            for (String sentence : sentences) {
                this.writeToKBase(sentence);
            }
        }
        for (String symptom: symptoms) {
            try {
                Symptom cs = (Symptom) Class.forName(symptom).newInstance();
                List<String> anotherSentences =  cs.getNegativeSentences(cell.getY(), cell.getX());
                for (String sentence: anotherSentences) {
                    this.writeToKBase(sentence);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Symptom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for (String role: roles) {
            try {
                Role cr = (Role) Class.forName(role).newInstance();
                List<String> anotherSentences =  cr.getNegativeSentences(cell.getY(), cell.getX());
                for (String sentence: anotherSentences) {
                    this.writeToKBase(sentence);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Symptom.class.getName()).log(Level.SEVERE, null, ex);
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
        if (this._actionManager.queueIsEmpty()) {
            // Generate all possible inferenc
            //this._inferenceAlgorithm.setDesiredCells(this._actionManager.getDesiredCells());
            //this._inferenceAlgorithm.execute(this._kBase);
        }
        // Second Step: Ask KBase, what is the next action
        AgentAction nextAction = this._actionManager.getNextAction(this._kBase);
        // Third Step: Make the current action
        this.makeAction(nextAction);

        this.printCurrentState();

    }
    
    @Override
    public final void writeLog(String log) {
        System.out.println(log);
        // TODO
    }

    @Override
    public void printCurrentState() {
        super.printCurrentState();
        if (this._arrow) { 
            this.writeLog("Agent contain arrow");
        } else {
            this.writeLog("Agent do not contain arrow");
        }
        this.writeLog("Current cell             : " + this._cell.toString());
        this.writeLog("Current move state       : " + this._currentState);
        this.writeLog("Knowledge Base           : " + this._kBase.getSentences().size());
        for (String sentence: this._kBase.getSentences()) {
            this.writeLog(sentence);
        }
        this._actionManager.printCurrentState();
        this._inferenceAlgorithm.printCurrentState();
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
    public void setCurrentCell(AbstractWorkSpaceCell cell) {
        this._cell = cell;
        ((SimpleActionManager)this._actionManager).setCurrentCell(Helper.getStringFromRowAndCol(this._cell.getY(), this._cell.getX()));
        ((SimpleActionManager)this._actionManager).setAgentMoveState(this._currentState);
        this._actionManager.addToVisitedCells(Helper.getStringFromRowAndCol(this._cell.getY(), this._cell.getX()));
    }

    @Override
    public void kBaseDidNotChange() {
        try {
            this._inferenceAlgorithm = this._algoritmManager.changeCurrentAlgorithm();
            this._inferenceAlgorithm.setKBaseSupervisorDelegate((Logic.InferenceAlgoritms.IKBaseSupervisorDelegate) this);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(WumpusWorldGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void writeToKBase(String sentence) {
        if (!this._kBase.getSentences().contains(sentence)) {
            this._kBase.addSentence(sentence);
            this.writeLog("The sentence '" + sentence + "' was added to Knowledge Base");
        } else {
            this.writeLog("The sentence '" + sentence + "' already contains");
        }
    }
}
