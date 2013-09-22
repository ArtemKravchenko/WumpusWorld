/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author admin
 */
public class Agent {

    // Variables
    private AgentAction _lastAction;
    private AgentMoveState _currentState;
    private Boolean _arrow;
    private Boolean _isGoldFound;
    private WorkSpaceCell _currentCell;
    
    public Agent () {
        this._isGoldFound = false;
        this._currentCell = new WorkSpaceCell();
    }
    
    // Methods
    public void pushTheArrow () {
        this._arrow = false;
    }
    
    public Boolean arrowIsStillContaining() {
        return this._arrow;
    }
    
    public Boolean isGoldFound() {
        return this._isGoldFound;
    }
    
    public void setCurrentState(AgentMoveState state) {
        this._currentState = state;
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
            if (this._currentState == AgentMoveState.FaceDown) {
                this._currentCell.setRow(this._currentCell.getRow() - 1);
            } else if (this._currentState == AgentMoveState.FaceLeft) {
                this._currentCell.setCol(this._currentCell.getCol() - 1);
            } else if (this._currentState == AgentMoveState.FaceRight) {
                this._currentCell.setCol(this._currentCell.getCol() + 1);
            } else {
                this._currentCell.setRow(this._currentCell.getRow() + 1);
            }
        } else {
            this._isGoldFound = true;
        }
    }
   
    public void setRow(int row) {
        this._currentCell.setRow(row);
    }
    
    public void setCol(int col) {
        this._currentCell.setCol(col);
    }
    
    public int getRow() {
        return this._currentCell.getRow();
    }
    
    public int getCol() {
        return this._currentCell.getCol();
    }
    
    public void printCurrentState() {
        
    }
    
    @Override
    public String toString() {
        return "Agent";
    }
    
    public static String literal() {
        return "Agent";
    }
    
}
