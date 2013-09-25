/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.InferenceAlgoritms;

import Logic.Helper;
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

    @Override
    public void execute(KnowledgeBases kBase) {
        int oldKBaseSize = kBase.getSentences().size();
        
        for (String cell: this._desiredCells) {
            Boolean cellIsTrueInCurrentModel = this.resolution(kBase, cell);
            if (cellIsTrueInCurrentModel) {
                kBase.addSentence(cell);
            }
        }
        
        if (kBase.getSentences().size() == oldKBaseSize) {
            this._kBaseSupervisorDelegate.kBaseDidNotChange();
        }
    }
    
    private Boolean resolution(KnowledgeBases kBase, String alfa) {
        Set<String> clauses = getCNF(kBase, alfa);
        Set<String> newSentences = new HashSet<>();
        
        while (true) {
            for (int i = 0; i < clauses.size(); i++) {
                List<String> clausesList = new ArrayList<>(Arrays.asList((String[]) clauses.toArray()));
                String currentClause = clausesList.get(i);
                List<String> restClauses = clausesList.subList(i + 1, clausesList.size() - 1);
                for (int j = 0; j < restClauses.size(); j++) {
                    String resolvents = this.resolve(currentClause, restClauses.get(j));
                    if (resolvents.equals("")) return true;
                    newSentences.add(resolvents);
                }
            }
            if (newSentences.containsAll(clauses)) return false;
            clauses.addAll(newSentences);
        }
    }
    
    private String resolve(String clause1, String clause2) {
        String resolution = "";
        
        String[] literalsOfFirstClause = clause1.split("\\" + Helper.getConjuction());
        String[] literalsOfSecondClause = clause2.split("\\" + Helper.getConjuction());
        
        for (int i = 0; i < literalsOfFirstClause.length; i++) {
            for (int j = 0; j < literalsOfSecondClause.length; j++) {
                if (literalsOfFirstClause[i].equals("!" + literalsOfSecondClause[j]) || 
                    literalsOfSecondClause[j].equals("!" + literalsOfFirstClause[i])) {
                    resolution += "";
                } else {
                    resolution += literalsOfFirstClause[i] + Helper.getConjuction() + literalsOfSecondClause;
                }
            }
        }
        
        return resolution;
    }
    
    private Set<String> getCNF(KnowledgeBases kBase, String alfa) {
        Set<String> clauses = new HashSet<>();
        
        List<String> kBaseSentences = kBase.getSentences();
        String currentClause;
        for (String sentence: kBaseSentences) {
            currentClause = sentence;
            if (currentClause.contains("<=>")) {
                currentClause = this.eliminateBiconditional(currentClause);
            } 
            if (currentClause.contains("=>")) {
                currentClause = this.eliminateImplication(currentClause);
            }
            if (currentClause.contains("!(")) {
                currentClause = this.openBracketsWithNegation(currentClause);
            }
            if (currentClause.contains(Helper.getConjuction() + "(") || currentClause.contains(")" + Helper.getConjuction())) {
                if (currentClause.contains(Helper.getDisjuction())) {
                    currentClause = this.openBracketsWithDistributivityCon(currentClause);
                }
            }
            if (currentClause.contains("!(!")) {
                currentClause = this.eliminateDoubleNegatiation(currentClause);
            }
            
            String[] array = currentClause.split("\\" + Helper.getDisjuction());
            clauses.addAll(Arrays.asList(array));
        }
        
        return clauses;
    }
    
    private String eliminateDoubleNegatiation(String clause) {
        return clause.replace("!!", "");
    }
    
    private String eliminateBiconditional(String clause) {
        String[] array = clause.split("\\<=>");
        String newClause = "(" + array[0] + "=>" + array[1] + ")" + Helper.getDisjuction() + "(" + array[1] + "=>" + array[0] + ")";
        
        return newClause;
    }
    
    private String eliminateImplication(String clause) {
        String[] array = clause.split("\\=>");
        String newClause = "!" + array[0] + Helper.getConjuction() + array[1];
        
        return newClause;
    } 
    
    private String openBracketsWithDistributivityCon(String clause) {
        String[] array = clause.split("\\" + Helper.getConjuction());
        String[] array2 = array[1].replace("(", "").replace(")", "").split("\\" + Helper.getDisjuction());
        String newClaues = "(" + array[0] + Helper.getConjuction()+ array2[0] + ")" + Helper.getDisjuction() +  "(" + array[0] + Helper.getConjuction() + array2[1] + ")";
        
        return "";
    }
    
    private String openBracketsWithNegation(String clause) {
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
        
        return newClause;
    }
}
