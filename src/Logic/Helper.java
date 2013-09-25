/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Models.Abstract.AbstractWorkSpaceCell;
import Models.BaseWorkSpaceCell;
import Models.Symptoms.Breeze;
import Models.Symptoms.Glitter;
import Models.Gold;
import Models.Roles.Pit;
import Models.Symptoms.Stench;
import Models.Roles.Wall;
import Models.EmptyCellProperty;
import Models.Enums.AgentAction;
import Models.Enums.AgentMoveState;
import Models.Roles.Wumpus;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class Helper {
    
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static Date date = new Date();
    
    public static BaseWorkSpaceCell getRowAndColFromString(String string) {
        BaseWorkSpaceCell cell = new BaseWorkSpaceCell();
        
        String[] array = string.split("\\,");
        String row = array[0].replace("[", "");
        String col = array[1].replace("]", "");
        
        cell.setY(Integer.parseInt(row));
        cell.setX(Integer.parseInt(col));
        
        return cell;
    }
    
    public static String getEntityNameFromClass(String className) {
        String[] array = className.split("\\.");
        return array[array.length - 1];
    }
    
    public static void printCurrentTime(){
        System.out.println(dateFormat.format(date));
    }
    
    public static String getStringFromRowAndCol(int row, int col) {
        return "[" + row + "," + col + "]";
    }
    
    public static String getConjuction() {
        return "(con)";
    }
    
    public static String getDisjuction() {
        return "(dis)";
    }
    
    public static AgentMoveState getStateAfterAction(AgentAction action, AgentMoveState state) {
        
        if (action == AgentAction.TurnLeft) {
            if (state == AgentMoveState.FaceDown) {
                return AgentMoveState.FaceRight;
            } else if (state == AgentMoveState.FaceLeft) {
                return AgentMoveState.FaceDown;
            } else if (state == AgentMoveState.FaceRight) {
                return AgentMoveState.FaceUp;
            } else {
                return AgentMoveState.FaceLeft;
            }
        } else if (action == AgentAction.TurnRight) {
            if (state == AgentMoveState.FaceDown) {
                return AgentMoveState.FaceLeft;
            } else if (state == AgentMoveState.FaceLeft) {
                return AgentMoveState.FaceUp;
            } else if (state == AgentMoveState.FaceRight) {
                return AgentMoveState.FaceDown;
            } else {
                return AgentMoveState.FaceRight;
            }
        } else {
            return state;
        } 
    }
    
    public static List<AgentAction> getListMoveStateForReachableDesired(AgentMoveState current, AgentMoveState desired) {
        List<AgentAction> actionsList = new ArrayList<>();
        AgentMoveState tmp;
        
        if (current == AgentMoveState.FaceDown && desired == AgentMoveState.FaceUp ||
            current == AgentMoveState.FaceUp && desired == AgentMoveState.FaceDown) {
            actionsList.add(AgentAction.TurnLeft);
            tmp = AgentMoveState.FaceLeft;
        } else {
            tmp = desired;
        }
        
        if (current == AgentMoveState.FaceDown && tmp == AgentMoveState.FaceLeft ||
            current == AgentMoveState.FaceLeft && tmp == AgentMoveState.FaceUp   || 
            current == AgentMoveState.FaceUp && tmp == AgentMoveState.FaceRight    ||
            current == AgentMoveState.FaceRight && tmp == AgentMoveState.FaceDown) {
            actionsList.add(AgentAction.TurnRight);
        }
        
        if (current == AgentMoveState.FaceDown && tmp == AgentMoveState.FaceRight ||
            current == AgentMoveState.FaceRight && tmp == AgentMoveState.FaceUp   || 
            current == AgentMoveState.FaceUp && tmp == AgentMoveState.FaceLeft    ||
            current == AgentMoveState.FaceLeft && tmp == AgentMoveState.FaceDown) {
            actionsList.add(AgentAction.TurnLeft);
        } 
        
        return actionsList;
    }
    
    public static AgentMoveState getCorrectMoveState(String currentCell, String nextCell) {
        BaseWorkSpaceCell currentWorkSpaceCell = Helper.getRowAndColFromString(currentCell);
        BaseWorkSpaceCell nextWorkSpaceCell = Helper.getRowAndColFromString(nextCell);
        
        if (currentWorkSpaceCell.getX() == nextWorkSpaceCell.getX()) {
            if (currentWorkSpaceCell.getY() > nextWorkSpaceCell.getY()) {
                return AgentMoveState.FaceDown;
            } else {
                return AgentMoveState.FaceUp;
            }
        } else if (currentWorkSpaceCell.getY() == nextWorkSpaceCell.getY()) {
            if (currentWorkSpaceCell.getX() > nextWorkSpaceCell.getX()) {
                return AgentMoveState.FaceLeft;
            } else {
                return AgentMoveState.FaceRight;
            }
        } 
        return null;
    }
}
