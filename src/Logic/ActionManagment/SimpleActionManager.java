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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author admin
 */
public class SimpleActionManager implements IActionManager {
    
    private Queue<AgentAction> _actionQueue;
    private BaseWorkSpaceCell _targetCell;
    private List<String> _visitedcCells;
    private List<String> _desiredCells;
    
    public SimpleActionManager() {
        this._actionQueue = new ArrayDeque<>();
        this._visitedcCells = new ArrayList<>();
        this._desiredCells = new ArrayList<>();
    }
    
    @Override
    public AgentAction getNextAction(KnowledgeBases kBase) {
        this.prepeareActionArrayForAccesCell(kBase);
        return this._actionQueue.poll();
    }
    
    private void prepeareActionArrayForAccesCell(KnowledgeBases kBase) {
        
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
        Map<String, Integer> emptyCellsMap = new HashMap<>();
        String literal;
        
        for (String sentence: sentences) {
            if (!sentence.contains("=>")) {
                array = sentence.split("\\:");
                literal = array[0];
                
            }
        }
        
        Set<String> keySet = emptyCellsMap.keySet();
        for (String cell: keySet) {
            if (emptyCellsMap.get(cell) == 2) {
                this.setTargetCell(cell);
                return;
            }
        }
        
        this.setTargetCell((String)(keySet.toArray()[0]));
        
    }
    
    private void setTargetCell(String cell) {
        this._targetCell = Helper.getRowAndColFromString(cell);
    }
    
    @Override
    public List<String> getDesiredCells() {
        return this._desiredCells;
    }
    
    @Override
    public void addToVisitedCells(String cellCoordinat){
        if (!this._visitedcCells.contains(cellCoordinat)) {
            this._visitedcCells.add(cellCoordinat);
        }
    }
    
}
