/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.InferenceAlgoritms;

import Logic.Helper;
import generated.KnowledgeBases;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class HeuristicAlgorithm extends AbstractInferenceAlgorithm {

    @Override
    public void execute(KnowledgeBases kBase) {
        int oldKBaseSize = kBase.getSentences().size();
        
        List<String> kBaseSentences = new ArrayList<>(kBase.getSentences());
        List<String> singleSymbolSentences = new ArrayList<>();
        List<String> moreThenOneSymbolSentences = new ArrayList<>();
        
        for (String sentence : kBaseSentences) {
            if (sentence.contains(Helper.getDisjuction()) || sentence.contains(Helper.getConjuction())) {
                moreThenOneSymbolSentences.add(sentence);
            } else {
                singleSymbolSentences.add(sentence);
            }
        }
        
        if (!moreThenOneSymbolSentences.isEmpty()) {
            for (String  moreThenOneSymbolSentence: moreThenOneSymbolSentences ) {
                String[] array = null;
                array = moreThenOneSymbolSentence.split("\\=>");
                if (array.length == 0) {
                    try { 
                        throw new Exception();
                    } catch (Exception ex) {
                        Logger.getLogger(HeuristicAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                List<String> symbols = new ArrayList<>(Arrays.asList(array[1].split("\\" + Helper.getDisjuction())));
                List<String> alreadyExist = new ArrayList<>(); 
                for (String oneSymbolSentence : singleSymbolSentences) {
                    for (String symbol : symbols) {
                        if (oneSymbolSentence.equals("!" + symbol)) {
                            alreadyExist.add(symbol);
                        }
                    }
                }
                if (alreadyExist.size() == symbols.size() - 1) {
                    for (String symbol: alreadyExist) {
                        symbols.remove(symbol);
                    }
                    if (symbols.size() == 1) {
                        if (!kBase.getSentences().contains(symbols.get(0))) {
                            kBase.addSentence(symbols.get(0));
                            this.writeLog("The sentence '" + symbols.get(0) + "' was added to Knowledge Base");
                        } else {
                            this.writeLog("The sentence '" + symbols.get(0) + "' already contains");
                        }
                    }
                }
            }
        }
        
        if (kBase.getSentences().size() == oldKBaseSize) {
            //this._kBaseSupervisorDelegate.kBaseDidNotChange();  TODO
        }
    }

    @Override
    public void printCurrentState() {
        this.writeLog("Heuristic Algorithm");
    }

    @Override
    public void writeLog(String log) {
        System.out.println(log);
    }
    
}
