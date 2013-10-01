/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.InferenceAlgoritms;

import Logic.Helper;
import Models.Roles.Pit;
import Models.Roles.Wumpus;
import generated.KnowledgeBases;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author admin
 */
public class ResolutionAlgorithm extends AbstractInferenceAlgorithm {

    public ResolutionAlgorithm () {
        this._desiredCells = new ArrayList<>();
    }
    
    @Override
    public void execute(KnowledgeBases kBase) {
        int oldKBaseSize = kBase.getSentences().size();
        String newCell = null;
        Boolean cellIsTrueInCurrentModel;
        for (String stringCell: this._desiredCells) {
            
            newCell = Helper.getEntityNameFromClass(new Pit().getClass().getName()) + ":" + stringCell;
            if (!this.kBaseContainClause(kBase, newCell)) {
                cellIsTrueInCurrentModel = this.resolution(kBase, newCell);
                if (cellIsTrueInCurrentModel) {
                    kBase.addSentence("!" + newCell);
                }
            }
            
            newCell = Helper.getEntityNameFromClass(new Wumpus().getClass().getName()) + ":" + stringCell;
            if (!this.kBaseContainClause(kBase, newCell)) {
                cellIsTrueInCurrentModel = this.resolution(kBase, newCell);
                if (cellIsTrueInCurrentModel) {
                    kBase.addSentence("!" + newCell);
                }
            }
        }
        
        if (kBase.getSentences().size() == oldKBaseSize) {
            this._kBaseSupervisorDelegate.kBaseDidNotChange();
        }
    }
    
    private Boolean kBaseContainClause(KnowledgeBases kBase ,String clause) {
        List<String> sentences = kBase.getSentences();
        for (String sentence : sentences) {
            if (sentence.contains(clause)) {
                if (sentence.contains(Helper.getDisjuction() + clause) || sentence.contains(clause  +Helper.getDisjuction())) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }
    
    private Boolean resolution(KnowledgeBases kBase, String notAlfa) {
        Set<String> clauses = getCNF(kBase);
        
        /*
        // First method
        Set<String> firstResolution = this.resolveEachWithOne(clauses, notAlfa);
        
        if (firstResolution.contains("")) {
            return true;
        }
        
        Set<String> secondResolution = this.resolvesEachOther(clauses);
        secondResolution = this.resolveEachWithOne(secondResolution, notAlfa);
        
        if (secondResolution.contains("")) {
            return true;
        } else {
            return false;
        }
        */
        // Second method
        clauses.add(notAlfa);
        Set<String> newSentences = new HashSet<>();
        while (true) {
           for (int i = 0; i < clauses.size(); i++) {
                String[] clausesArray = (String[]) clauses.toArray(new String[clauses.size()]);
                List<String> clausesList = new ArrayList<>(Arrays.asList(clausesArray));
                String currentClause = clausesList.get(i);
                List<String> restClauses = clausesList.subList(i + 1, clausesList.size());
                for (int j = 0; j < restClauses.size(); j++) {
                    String nextClause = restClauses.get(j);
                    Set<String> resolvents = this.resolve(currentClause, nextClause);
                    if (resolvents.size() == 1) {
                        this.writeLog("");
                    }
                    if (resolvents.size() == 2) {
                        this.writeLog("");
                    }
                    if (resolvents.size() == 3) {
                        this.writeLog("");
                    }
                    if (resolvents.size() > 3) {
                        this.writeLog("");
                    }
                    if (resolvents.contains("")) return true;
                    newSentences.addAll(resolvents);
                }
            }
            if (newSentences.containsAll(clauses)) return false;
            clauses.addAll(newSentences);
        }
    }
    
    private Set<String> resolvesEachOther(Set<String> clauses) {
        Set<String> resolvents = new HashSet<>();
        
        for (int i = 0; i < clauses.size(); i++) {
                List<String> clausesList = new ArrayList<>(Arrays.asList((String[]) clauses.toArray(new String[clauses.size()])));
                String currentClause = clausesList.get(i);
                List<String> restClauses = clausesList.subList(i + 1, clausesList.size());
                for (int j = 0; j < restClauses.size(); j++) {
                    String nextClause = restClauses.get(j);
                    resolvents.addAll(this.resolve(currentClause, nextClause));
                }
            }
        
        return resolvents;
    }
    
    private Set<String> resolveEachWithOne(Set<String> clauses, String notAlfa) {
        Set<String> resolvents = new HashSet<>();
        
        for (String clause: clauses) {
            Set<String> resolve = this.resolve(clause, notAlfa);
            resolvents.addAll(resolve);
        }
        
        return resolvents;
    }
    
    private Set<String> resolve(String clause1, String clause2) {
        Set<String> resolutions = new HashSet<>();
        String clause = null;
        
        
        String[] literalsOfFirstClause = clause1.split("\\" + Helper.getDisjuction());
        String[] literalsOfSecondClause = clause2.split("\\" + Helper.getDisjuction());
        
        if (literalsOfSecondClause.length == 1) {
            for (int i = 0; i < literalsOfSecondClause.length; i++) {
                clause = new String(clause1);
                for (int j = 0; j < literalsOfFirstClause.length; j++) {
                    if (literalsOfFirstClause[j].equals("!" + literalsOfSecondClause[i]) || 
                        literalsOfSecondClause[i].equals("!" + literalsOfFirstClause[j])) {
                        clause.replace(literalsOfSecondClause[i], "");
                    } 
                }
                if (clause.equals(clause1)) {
                    clause += Helper.getDisjuction() + literalsOfSecondClause[i];
                }
                resolutions.add(clause);
            }
        } else {
            for (int i = 0; i < literalsOfFirstClause.length; i++) {
                clause = new String(clause2);
                for (int j = 0; j < literalsOfSecondClause.length; j++) {
                    if (literalsOfFirstClause[i].equals("!" + literalsOfSecondClause[j]) || 
                        literalsOfSecondClause[j].equals("!" + literalsOfFirstClause[i])) {
                        clause.replace(literalsOfSecondClause[j], "");
                    } 
                }
                if (clause.equals(clause2)) {
                    clause += Helper.getDisjuction() + literalsOfFirstClause[i];
                }
                resolutions.add(clause);
            }
        }
        
        return resolutions;
    }
    
    private Set<String> getCNF(KnowledgeBases kBase) {
        Set<String> clauses = new HashSet<>();
        
        List<String> kBaseSentences = kBase.getSentences();
        String currentClause;
        for (String sentence: kBaseSentences) {
            currentClause = sentence;
            if (currentClause.contains("<=>")) {
                currentClause = this.eliminateBiconditional(currentClause);
            } else {
                if (currentClause.contains("=>")) {
                    currentClause = this.eliminateImplication(currentClause);
                }
                if (currentClause.contains("!(")) {
                    currentClause = this.openBracketsWithNegation(currentClause);
                }
                if (currentClause.contains("!!")) {
                    currentClause = this.eliminateDoubleNegatiation(currentClause);
                }
                if (currentClause.contains(Helper.getDisjuction()) && currentClause.contains(Helper.getConjuction())) {
                    currentClause = this.openBracketsWithDistributivityCon(currentClause);
                }
            }
            if (currentClause.contains(Helper.getConjuction())) {
                String[] array = currentClause.split("\\" + Helper.getConjuction());
                clauses.addAll(Arrays.asList(array));
                continue;
            }
            clauses.add(currentClause);
        }
        
        return clauses;
    }
    
    private String eliminateDoubleNegatiation(String clause) {
        return clause.replace("!!", "");
    }
    
    private String eliminateBiconditional(String clause) {
        this.writeLog("Eliminate biconditional for sentence: " + clause);
        String[] array = clause.split("\\<=>");
        String newClause = "(" + array[0] + "=>" + array[1] + ")" + Helper.getConjuction() + "(" + array[1] + "=>" + array[0] + ")";
        this.writeLog("Receive : " + newClause);
        
        String firstClause = this.eliminateImplication(array[0] + "=>" + array[1]);
        String secondClause = this.eliminateImplication(array[1] + "=>" + array[0]);
        
        if (firstClause.contains("!!")) {
            firstClause = this.eliminateDoubleNegatiation(firstClause);
        }
        if (secondClause.contains("!!")) {
            secondClause = this.eliminateDoubleNegatiation(secondClause);
        }
        
        if (firstClause.contains(Helper.getDisjuction()) && firstClause.contains(Helper.getConjuction())) {
            firstClause = this.openBracketsWithDistributivityCon(firstClause);
        }
        if (secondClause.contains(Helper.getDisjuction()) && secondClause.contains(Helper.getConjuction())) {
            secondClause = this.openBracketsWithDistributivityCon(secondClause);
        }
        
        newClause = firstClause + Helper.getConjuction() + secondClause;
        
        return newClause;
    }
    
    private String eliminateImplication(String clause) {
        this.writeLog("Eliminate implication for sentence: " + clause);
        String[] array = clause.split("\\=>");
        
        String newClause = "!" + array[0] + Helper.getDisjuction()+ array[1];
        this.writeLog("Receive : " + newClause);
        return newClause;
    } 
    
    private String openBracketsWithDistributivityCon(String clause) {
        this.writeLog("Open bracket with distributive conjuction for sentence: " + clause);
        String[] array = clause.split("\\" + Helper.getDisjuction());
        String[] array2 = null;
        String newClause = null;
        if (array[1].contains(Helper.getConjuction())) {
            array2 = array[1].replace("(", "").replace(")", "").split("\\" + Helper.getConjuction());
            newClause = array[0] + Helper.getDisjuction()+ array2[0] + Helper.getConjuction() + array[0] + Helper.getDisjuction()+ array2[1];
        }
        if (array[0].contains(Helper.getConjuction())) {
            array2 = array[0].replace("(", "").replace(")", "").split("\\" + Helper.getConjuction());
            newClause = array[1] + Helper.getDisjuction()+ array2[0] + Helper.getConjuction() + array[1] + Helper.getDisjuction()+ array2[1];
        }
        this.writeLog("Receive : " + newClause);
        return newClause;
    }
    
    private String openBracketsWithNegation(String clause) {
        this.writeLog("Open bracket with negatiation for sentence: " + clause);
        clause = clause.replace("!(", "").replace(")", "");
        String[] array;
        String splitSymbol = null, connectSymbol = null; 
        if (clause.contains(Helper.getConjuction())) {
            splitSymbol = Helper.getConjuction();
            connectSymbol = Helper.getDisjuction();
        } else if (clause.contains(Helper.getDisjuction())) {
            splitSymbol = Helper.getDisjuction();
            connectSymbol = Helper.getConjuction();
        }
        array = clause.split(splitSymbol);
        String newClause = "!" + array[0] + connectSymbol + "!" + array[1];
        this.writeLog("Receive :" + newClause);
        return newClause;
    }

    @Override
    public void printCurrentState() {
        this.writeLog("Desired cells:" + this._desiredCells.size());
        for (String cell: this._desiredCells) {
            this.writeLog(cell);
        }
    }

    @Override
    public void writeLog(String log) {
        System.out.println(log);
    }
    
}
