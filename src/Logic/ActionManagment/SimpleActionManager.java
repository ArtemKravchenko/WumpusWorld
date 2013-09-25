/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.ActionManagment;

import Logic.Graph;
import Models.Enums.AgentAction;
import generated.KnowledgeBases;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import Logic.Helper;
import Models.BaseWorkSpaceCell;
import Models.Enums.AgentMoveState;
import Models.IBaseCellProperty;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class SimpleActionManager implements IActionManager {
    
    private Queue<AgentAction> _actionQueue;
    private String _targetCell;
    private List<String> _visitedcCells;
    private HashMap<String, Integer> _desiredCells;
    private Graph<String> _workSpaceGraph;
    private String _currentCell;
    private AgentMoveState _currentMoveState;
    
    public SimpleActionManager() {
        this._actionQueue = new ArrayDeque<>();
        this._visitedcCells = new ArrayList<>();
        this._desiredCells = new HashMap<>();
        this._workSpaceGraph = new Graph<>();
    }
    
    @Override
    public AgentAction getNextAction(KnowledgeBases kBase) {
        if (this._actionQueue.isEmpty()) {
            this._targetCell = null;
            try {
                this.prepeareActionQueueForAccesCell(kBase);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(SimpleActionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this._actionQueue.poll();
    }
    
    /*
     * Logic:
     * Consider only those sentences, that consist of one literal
     * We split current sentence on position and symbol
     * If position of current sentence do not contains in visited cells, then we add him to desired cells
     * Increase integer value of desired cell on weight that correspond Entity weight getting from knowledge base
     */
    
    private void prepeareActionQueueForAccesCell(KnowledgeBases kBase) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Get all sentences from KBase
        List<String> sentences = new ArrayList<>(kBase.getSentences());
        
        // Evaluation of cell
        this.updateDesiredCells(sentences);
        
        // Set target cell
        String keyOfMaximalCounter = this.getKeyWithMaximalValue();
        this.setTargetCell(keyOfMaximalCounter);
        
        // Generate optimal route to target cell
        this.fillQueueOfActions();
    }
    
    private void fillQueueOfActions() {  
        // Prepeare work space graph for searching
        this.updateGraphWithVisitedCells();
        // Find shortes path to target cell
        List<String> shortestPath = this._workSpaceGraph.getShortesPath(this._currentCell, this._targetCell);
        // Fill action queue correspond for path
        this.fillQueueOfActionsBasedOnPath(shortestPath);
    }
    
    private void fillQueueOfActionsBasedOnPath(List<String> path) {
        if (path.get(0).equals(this._currentCell)) {
            path.remove(0);
        }
        String current = this._currentCell;
        String next = "";
        for (String cell: path) {
            next = cell;
            List<AgentAction> actions = this.getListOfActionsToNeighbor(current, next);
            for (AgentAction action: actions) {
                this._currentMoveState = Helper.getStateAfterAction(action, this._currentMoveState);
                this._actionQueue.add(action);
            }
            current = next;
        }
    }
    
    private List<AgentAction> getListOfActionsToNeighbor(String currentCell, String nextCell) {
        List<AgentAction> actionsList = new ArrayList<>();
        // Find correct direction
        AgentMoveState requiredState = Helper.getCorrectMoveState(currentCell, nextCell);
        if (!this._currentMoveState.equals(requiredState)) {
            actionsList.addAll(Helper.getListMoveStateForReachableDesired(this._currentMoveState, requiredState));
        }
        
        // Add last Action for transition
        actionsList.add(AgentAction.MoveForward);
        
        return actionsList;
    }
    
    
    private void updateGraphWithVisitedCells() {
        if (this._workSpaceGraph.getVertices().isEmpty()) {
            for (String cell: this._visitedcCells) {
                this._workSpaceGraph.addVertex(cell);
            }
            for (String cell: this._visitedcCells) {
                this.addEdgeToGrahForCell(cell);
            }
        } else if (this._workSpaceGraph.getVertices().size() < this._visitedcCells.size()) {
            for (int i = this._workSpaceGraph.getVertices().size(); i < this._visitedcCells.size(); i++) {
                this._workSpaceGraph.addVertex(this._visitedcCells.get(i));
            }
            for (int i = this._workSpaceGraph.getVertices().size(); i < this._visitedcCells.size(); i++) {
                this.addEdgeToGrahForCell(this._visitedcCells.get(i));
            }
        }
    }
    
    private void addEdgeToGrahForCell(String cell) {
        BaseWorkSpaceCell workSpaceCell = Helper.getRowAndColFromString(cell);
        List<String> neighborhood = new ArrayList<>();
        
        String upNeighbor = Helper.getStringFromRowAndCol(workSpaceCell.getY() + 1, workSpaceCell.getX());
        String rightNeighbor = Helper.getStringFromRowAndCol(workSpaceCell.getY(), workSpaceCell.getX() + 1);
        String leftNeighbor = Helper.getStringFromRowAndCol(workSpaceCell.getY() - 1, workSpaceCell.getX());
        String downNeighbor = Helper.getStringFromRowAndCol(workSpaceCell.getY(), workSpaceCell.getX() - 1);
                
        if (this._visitedcCells.contains(upNeighbor)) {
            neighborhood.add(upNeighbor);
        }
        if (this._visitedcCells.contains(rightNeighbor)) {
            neighborhood.add(rightNeighbor);
        }
        if (this._visitedcCells.contains(leftNeighbor)) {
            neighborhood.add(leftNeighbor);
        }
        if (this._visitedcCells.contains(downNeighbor)) {
            neighborhood.add(downNeighbor);
        }
        
        for (String neighbor: neighborhood) {
            this._workSpaceGraph.addEdge(cell, neighbor);
        }
    }
    
    private String getKeyWithMaximalValue() {
        String keyOfMaximalCounter = "";
        int max = 0;
        for (String key: this._desiredCells.keySet()) {
            if (this._desiredCells.get(key) > max) {
                max = this._desiredCells.get(key);
                keyOfMaximalCounter = key;
            }
        }
        
        return keyOfMaximalCounter;
    }
    
    private void updateDesiredCells(List<String> sentences) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String[] array;
        String literal;
        String position;
        
        for (String sentence: sentences) {
            if (!sentence.contains("=>")) {
                array = sentence.split("\\:");
                literal = array[0];
                position = array[1];
                if (!this._visitedcCells.contains(position)) {
                    if (!this._desiredCells.keySet().contains(position)) {
                        this._desiredCells.put(position, 0);
                    }
                    int counter = this._desiredCells.get(position);
                    Class<IBaseCellProperty> c = (Class<IBaseCellProperty>) Class.forName(literal);
                    Method method = (!literal.contains("!")) ? c.getClass().getMethod("weight", null): c.getClass().getMethod("antiWeight", null);
                    method.invoke(c, null);
                }
            }
        }
    } 
    
    private void setTargetCell(String cell) {
        this._targetCell = cell;
    }
    
    public void setCurrentCell(String cell) {
        this._currentCell = cell;
    }
    
    public void setAgentMoveState(AgentMoveState state) {
        this._currentMoveState = state;
    }
    
    @Override
    public HashMap<String, Integer> getDesiredCells() {
        return this._desiredCells;
    }
    
    @Override
    public void addToVisitedCells(String cellCoordinat){
        if (!this._visitedcCells.contains(cellCoordinat)) {
            this._visitedcCells.add(cellCoordinat);
        }
    }
    
}
