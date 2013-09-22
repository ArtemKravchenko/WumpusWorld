/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.ActionManagment;

import Models.AgentAction;
import Models.CellProperty;
import generated.KnowledgeBases;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import Logic.Helper;
import Models.Breeze;
import Models.Glitter;
import Models.Gold;
import Models.Pit;
import Models.Stench;
import Models.WorkSpaceCell;
import Models.Wumpus;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author admin
 */
public class SimpleActionManager implements IActionManager {
    
    private Queue<AgentAction> _actionQueue;
    private WorkSpaceCell _targetCell;
    private List<String> _visitedcCells;
    
    public SimpleActionManager() {
        this._actionQueue = new ArrayDeque<>();
        this._visitedcCells = new ArrayList<>();
    }
    
    @Override
    public AgentAction getNextAction(KnowledgeBases kBase) {
        this.prepeareActionArrayForAccesCell(kBase);
        return this._actionQueue.poll();
    }
    
    private final int kEmptyCellWeight = 10000;
    
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
                array = sentence.split(":");
                literal = array[0];
                if (literal.equals(Gold.literal())) {
                    this.setTargetCell(array[1]);
                    return;
                }
                
                if (literal.equals(Glitter.literal())) {
                    int tmp = emptyCellsMap.get(array[1]);
                    tmp+=Glitter.weight();
                    emptyCellsMap.put(literal, tmp);
                }
                
                if (literal.equals("!" + Pit.literal())) {
                    int tmp = emptyCellsMap.get(array[1]);
                    tmp+=CellProperty.weight();
                    emptyCellsMap.put(literal, tmp);
                }
                
                if (literal.equals("!" + Wumpus.literal())) {
                    int tmp = emptyCellsMap.get(array[1]);
                    tmp+=CellProperty.weight();
                    emptyCellsMap.put(literal, tmp);
                }
                
                if (literal.equals(Breeze.literal())) {
                    int tmp = emptyCellsMap.get(array[1]);
                    tmp+=Breeze.weight();
                    emptyCellsMap.put(literal, tmp);
                }
                
                if (literal.equals(Stench.literal())) {
                    int tmp = emptyCellsMap.get(array[1]);
                    tmp+=Stench.weight();
                    emptyCellsMap.put(literal, tmp);
                }
                
                if (literal.equals(Wumpus.literal())) {
                    int tmp = emptyCellsMap.get(array[1]);
                    tmp+=Wumpus.weight();
                    emptyCellsMap.put(literal, tmp);
                }
                
                if (literal.equals(Pit.literal())) {
                    int tmp = emptyCellsMap.get(array[1]);
                    tmp+=Pit.weight();
                    emptyCellsMap.put(literal, tmp);
                }
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
        
        /*
         for (String availableCell : this._avalibleAndNotAttendedCellsForSteps) {
                        if (array[1].equals(availableCell)) {
                            sentenceArray = sentence.split(":");
                            String cellStringType = sentenceArray[0].replace("!", "");
                            Class c = Class.forName(cellStringType);
                            CellProperty cell = (CellProperty) c.newInstance();
                            if (cell.toString().equals(Gold.literal())) {
                                
                            } else if (cell.toString().equals(Wumpus.literal())) {
                                
                            } else if (!cell.toString().equals(Pit.literal())) {
                                
                            }
                        }
                    }
         */
    }
    
    private void setTargetCell(String cell) {
        int[] rowAndCol = Helper.getRowAndColFromString(cell);
        this._targetCell = new WorkSpaceCell();
        this._targetCell.setRow(rowAndCol[0]);
        this._targetCell.setCol(rowAndCol[1]);
    }
    
    @Override
    public void addToVisitedCells(String cellCoordinat){
        if (!this._visitedcCells.contains(cellCoordinat)) {
            this._visitedcCells.add(cellCoordinat);
        }
    }
    
}
