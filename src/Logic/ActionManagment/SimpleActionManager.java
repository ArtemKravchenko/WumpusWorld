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
    }
    
    @Override
    public AgentAction getNextAction(KnowledgeBases kBase) {
        this.writeLog("-- Simple Action Manager (Get Next Action) --");
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
        this.evaluateCounterOfDesiredCells(sentences);
        
        // Set target cell
        String keyOfMaximalCounter = this.getKeyWithMaximalValue();
        if (!keyOfMaximalCounter.equals("")) {
            // Set target cell
            this.setTargetCell(keyOfMaximalCounter);
        
            // Generate optimal route to target cell
            this.fillQueueOfActions();
        } else {
            this._actionQueue.add(AgentAction.MoveForward);
        }
    }
    
    private void fillQueueOfActions() {  
        // Prepeare work space graph for searching
        this.updateGraphWithVisitedCells();
        // Find shortes path to target cell
        Queue<String> shortestPath = this._workSpaceGraph.getShortesPath(this._currentCell, this._targetCell);
        // Fill action queue correspond for path
        this.fillQueueOfActionsBasedOnPath(shortestPath);
    }
    
    private void fillQueueOfActionsBasedOnPath(Queue<String> path) {
        String current = this._currentCell;
        String next = null;
        while(!path.isEmpty()) {
            next = path.poll();
            if (!next.equals(this._currentCell)) {
                List<AgentAction> actions = this.getListOfActionsToNeighbor(current, next);
                for (AgentAction action: actions) {
                    this._currentMoveState = Helper.getStateAfterAction(action, this._currentMoveState);
                    this._actionQueue.add(action);
                }
                current = next;
            }
        }
    }
    
    private List<AgentAction> getListOfActionsToNeighbor(String currentCell, String nextCell) {
        List<AgentAction> actionsList = new ArrayList<>();
        // Find correct direction
        AgentMoveState requiredState = Helper.getCorrectMoveState(currentCell, nextCell);
        if (!this._currentMoveState.equals(requiredState)) {
            List<AgentAction> actions = Helper.getListMoveStateForReachableDesired(this._currentMoveState, requiredState);
            actionsList.addAll(actions);
        }
        
        // Add last Action for transition
        actionsList.add(AgentAction.MoveForward);
        
        return actionsList;
    }
    
    private void updateGraphWithVisitedCells() {
        this._workSpaceGraph = new Graph();
        // Update vertices
        for (String cell: this._visitedcCells) {
            this._workSpaceGraph.addVertex(cell);
        }
        // Update edges
        for (String cell: this._visitedcCells) {
            this.addEdgeToGrahForCell(cell);
        }
    }
    
    private void addEdgeToGrahForCell(String cell) {
        BaseWorkSpaceCell workSpaceCell = Helper.getRowAndColFromString(cell);
        List<String> neighborhood = new ArrayList<>();
        
        String upNeighbor = Helper.getStringFromRowAndCol(workSpaceCell.getY() + 1, workSpaceCell.getX());
        String rightNeighbor = Helper.getStringFromRowAndCol(workSpaceCell.getY(), workSpaceCell.getX() + 1);
        String leftNeighbor = Helper.getStringFromRowAndCol(workSpaceCell.getY() - 1, workSpaceCell.getX());
        String downNeighbor = Helper.getStringFromRowAndCol(workSpaceCell.getY(), workSpaceCell.getX() - 1);
                
        if (this._visitedcCells.contains(upNeighbor) || this._desiredCells.containsKey(upNeighbor)) {
            neighborhood.add(upNeighbor);
        }
        if (this._visitedcCells.contains(rightNeighbor) || this._desiredCells.containsKey(rightNeighbor)) {
            neighborhood.add(rightNeighbor);
        }
        if (this._visitedcCells.contains(leftNeighbor) || this._desiredCells.containsKey(leftNeighbor)) {
            neighborhood.add(leftNeighbor);
        }
        if (this._visitedcCells.contains(downNeighbor) || this._desiredCells.containsKey(downNeighbor)) {
            neighborhood.add(downNeighbor);
        }
        
        for (String neighbor: neighborhood) {
            this._workSpaceGraph.addEdge(cell, neighbor);
            this._workSpaceGraph.setWeight(cell, neighbor, 1.0);
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
    
    private void evaluateCounterOfDesiredCells(List<String> sentences) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {        
        for (String sentence: sentences) {
            if (!sentence.contains("=>")) {
                this.evaluateSentence(sentence);
            } else {
                String[] array = sentence.split("\\=>");
                String rightSide = array[1];
                String[] literals = rightSide.split("\\" + Helper.getConjuction());
                if (literals.length > 1) {
                    for (String literal: literals) {
                        this.evaluateSentence(literal);
                    }
                }
            }
        }
    } 
    
    private void evaluateSentence(String sentence) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String[] array;
        String literal;
        String position;
        
        array = sentence.split("\\:");
        if (array.length == 1) {
            this.writeLog("");
        }
        literal = array[0];
        position = array[1];
        if (!this._visitedcCells.contains(position)) {
            if (!this._desiredCells.keySet().contains(position)) {
                this._desiredCells.put(position, 0);
            }
            int counter = this._desiredCells.get(position);
            String tmpLiteral = literal.replace("!", "");
            tmpLiteral = this.getFullClassName(tmpLiteral);
            Class<IBaseCellProperty> c = (Class<IBaseCellProperty>) Class.forName(tmpLiteral);
            Method method = (!literal.contains("!")) ? c.getMethod("weight", (Class<?>) null): c.getMethod("antiWeight", (Class<?>[])null);
            counter += (int) method.invoke((Object) null);
            this._desiredCells.put(position, counter);
        }
    }
    
    private String getFullClassName(String className) {
        String[] symptoms = Helper.getAllSymptoms();
        for (String symptom: symptoms) {
            if (symptom.contains(className)) {
                return symptom;
            }
        }
        String[] roles = Helper.getAllRoles();
        for (String role: roles) {
            if (role.contains(className)) {
                return role;
            }
        }
        
        return null;
    }
    
    private void updateDesiredCells() {
        int rightCell = Helper.getRowAndColFromString(this._currentCell).getX() + 1;
        int upCell = Helper.getRowAndColFromString(this._currentCell).getY() + 1;
        int leftCell = Helper.getRowAndColFromString(this._currentCell).getX() - 1;
        int downCell = Helper.getRowAndColFromString(this._currentCell).getY() - 1;
        
        if (rightCell >= 0 && rightCell < 4) {
            String rightCellString = Helper.getStringFromRowAndCol(Helper.getRowAndColFromString(this._currentCell).getY(), rightCell);
            if (!this._desiredCells.containsKey(rightCellString) && !this._visitedcCells.contains(rightCellString)) {
                this._desiredCells.put(rightCellString, 0);
                this.writeLog("Cell " + rightCellString + " was added to desired cells");
            }
        }
        if (upCell >= 0 && upCell < 4) {
            String upCellString = Helper.getStringFromRowAndCol(upCell, Helper.getRowAndColFromString(this._currentCell).getX());
            if (!this._desiredCells.containsKey(upCellString) && !this._visitedcCells.contains(upCellString)) {
                this._desiredCells.put(upCellString, 0);
                this.writeLog("Cell " + upCellString + " was added to desired cells");
            }
        }
        if (leftCell >= 0 && leftCell < 4) { 
            String leftCellString = Helper.getStringFromRowAndCol(Helper.getRowAndColFromString(this._currentCell).getY(), leftCell);
            if (!this._desiredCells.containsKey(leftCellString) && !this._visitedcCells.contains(leftCellString)) {
                this._desiredCells.put(leftCellString, 0);
                this.writeLog("Cell " + leftCellString + " was added to desired cells");
            }
        }
        if (downCell >= 0 && downCell < 4) {
            String downCellString = Helper.getStringFromRowAndCol(downCell, Helper.getRowAndColFromString(this._currentCell).getX());
            if (!this._desiredCells.containsKey(downCellString) && !this._visitedcCells.contains(downCellString)) {
                this._desiredCells.put(downCellString, 0);
                this.writeLog("Cell " + downCellString + " was added to desired cells");
            }
        } 
    }
    
    private void setTargetCell(String cell) {
        this._targetCell = cell;
    }
    
    public void setCurrentCell(String cell) {
        this._currentCell = cell;
        this._visitedcCells.add(cell);
        this._desiredCells.remove(cell);
        this.updateDesiredCells();
    }
    
    public void setAgentMoveState(AgentMoveState state) {
        this._currentMoveState = state;
    }
    
    @Override
    public List<String> getDesiredCells() {
        List<String> desiredCells = new ArrayList<>();
        for (String key: this._desiredCells.keySet()) {
            desiredCells.add(key);
        }
        return desiredCells;
    }
    
    @Override
    public void addToVisitedCells(String cellCoordinat){
        if (!this._visitedcCells.contains(cellCoordinat)) {
            this._visitedcCells.add(cellCoordinat);
            this.writeLog("Cell " + cellCoordinat + "was added to visisted cells" );
        }
    }

    @Override
    public void printCurrentState() {
        this.writeLog("----- Simple Action Manager (State) ------");
        this.writeLog("Current cell         : " + this._currentCell);
        this.writeLog("Current move state   : " + this._currentMoveState);
        this.writeLog("Target cell          : " + this._targetCell);
        this.writeLog("Visited cells        : " + this._visitedcCells.size());
        for (String cell: this._visitedcCells) {
            this.writeLog(cell);
        }
        this.writeLog("Desired cells        : " + this._desiredCells.size());
        for (String cell: this._desiredCells.keySet()) {
            this.writeLog(cell + ": " + this._desiredCells.get(cell));
        }
        this.writeLog("Actions queue        : " + this._actionQueue.size());
        for (AgentAction action: this._actionQueue) {
            this.writeLog(action.toString());
        }
        this.writeLog("-------------------------------------------");
    }

    @Override
    public void writeLog(String log) {
        System.out.println(log);
        // TODO
    }

    @Override
    public Boolean queueIsEmpty() {
        return this._actionQueue.isEmpty();
    }
    
}
