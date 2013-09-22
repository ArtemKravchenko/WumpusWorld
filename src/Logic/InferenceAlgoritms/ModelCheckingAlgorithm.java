/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.InferenceAlgoritms;

import generated.KnowledgeBases;

/**
 *
 * @author admin
 */
public class ModelCheckingAlgorithm extends FindNewStateAlgorithm {

    @Override
    public void execute(KnowledgeBases kBase) {
        int oldKBaseSize = kBase.getSentences().size();
        
        if (kBase.getSentences().size() == oldKBaseSize) {
            this._kBaseSupervisorDelegate.kBaseDidNotChange();
        }
    }
    
}
