/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author admin
 */
public class Agent extends Role {

    // Variables
    private AgentAction _lastAction;
    private AgentMoveState _currentState;
    private Boolean _arrow;
    
    // Methods
    public void pushTheArrow () {
        this._arrow = false;
        this._lifeState = AgentLifeState.Alive;
        this._col = 1;
        this._row = 1;
        // TODO something
    }
    
    public Boolean arrowIsStillContaining() {
        return this._arrow;
    }
    
    public void setLastAction(AgentAction action) {
        this._lastAction = action;
        
        if (action == AgentAction.TurnLeft) {
            if (this._currentState == AgentMoveState.FaceDown) {
                this._currentState = AgentMoveState.FaceRight;
            } else if (this._currentState == AgentMoveState.FaceLeft) {
                this._currentState = AgentMoveState.FaceDown;
            } else if (this._currentState == AgentMoveState.FaceRight) {
                this._currentState = AgentMoveState.FaceUp;
            } else {
                this._currentState = AgentMoveState.FaceLeft;
            }
        } else if (action == AgentAction.TurnRight) {
            if (this._currentState == AgentMoveState.FaceDown) {
                this._currentState = AgentMoveState.FaceLeft;
            } else if (this._currentState == AgentMoveState.FaceLeft) {
                this._currentState = AgentMoveState.FaceUp;
            } else if (this._currentState == AgentMoveState.FaceRight) {
                this._currentState = AgentMoveState.FaceDown;
            } else {
                this._currentState = AgentMoveState.FaceRight;
            }
        } else if (action == AgentAction.Shoot) {
            this._arrow = false;
        } else if (action == AgentAction.MoveForward) {
            // TODO
        } else {
            // TODO
        }
    }
   
    public @Override String toString() {
        return "Agent";
    }
}
