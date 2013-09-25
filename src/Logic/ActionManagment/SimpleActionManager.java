/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.ActionManagment;

import Models.Enums.AgentAction;
import generated.KnowledgeBases;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import Logic.Helper;
import Models.BaseWorkSpaceCell;
import Models.IBaseCellProperty;
import Models.Symptoms.Symptom;
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
    private BaseWorkSpaceCell _targetCell;
    private List<String> _visitedcCells;
    private HashMap<String, Integer> _desiredCells;
    
    public SimpleActionManager() {
        this._actionQueue = new ArrayDeque<>();
        this._visitedcCells = new ArrayList<>();
        this._desiredCells = new HashMap<>();
    }
    
    @Override
    public AgentAction getNextAction(KnowledgeBases kBase) {
        try {
            this.prepeareActionArrayForAccesCell(kBase);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(SimpleActionManager.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void prepeareActionArrayForAccesCell(KnowledgeBases kBase) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        if (this._actionQueue.size() == 0) {
            this._targetCell = null;
        }
        
        // Get all sentences from KBase
        List<String> sentences = new ArrayList<>(kBase.getSentences());
        
        // Remove sentence that we already looked
        if (this._visitedcCells.size() > 0) {
            for (String visitedCell : this._visitedcCells) {
                if (sentences.contains(visitedCell)) {
                    sentences.remove(visitedCell);
                }
            }
        }
        
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
        
        String keyOfMaximalCounter = "";
        int max = 0;
        for (String key: this._desiredCells.keySet()) {
            if (this._desiredCells.get(key) > max) {
                max = this._desiredCells.get(key);
                keyOfMaximalCounter = key;
            }
        }
        
        this.setTargetCell(keyOfMaximalCounter);
        // TODO
    }
    
    private void setTargetCell(String cell) {
        this._targetCell = Helper.getRowAndColFromString(cell);
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
