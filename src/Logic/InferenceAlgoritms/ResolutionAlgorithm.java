/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.InferenceAlgoritms;

import Logic.Helper;
import generated.KnowledgeBases;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ResolutionAlgorithm extends AbstractInferenceAlgorithm {

    @Override
    public void execute(KnowledgeBases kBase) {
        int oldKBaseSize = kBase.getSentences().size();
        
        // TODO
        
        if (kBase.getSentences().size() == oldKBaseSize) {
            this._kBaseSupervisorDelegate.kBaseDidNotChange();
        }
    }
    
    private List<String> getResolution(String clause1, String clause2) {
        List<String> resolutions = new ArrayList<>();
        
        
        
        return new ArrayList<>();
    }
    
    private List<String> getCNF(String sentence) throws Exception {
        if (!sentence.contains("<=>")) {
            throw new Exception();
        }
        
        return null;
    }
    
}
