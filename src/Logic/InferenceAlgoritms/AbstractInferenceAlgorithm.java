/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.InferenceAlgoritms;

import generated.KnowledgeBases;
import java.util.List;

/**
 *
 * @author admin
 */
public abstract class AbstractInferenceAlgorithm {
    
    protected IKBaseSupervisorDelegate _kBaseSupervisorDelegate;
    protected List<String> _desiredCells;
    
    public abstract void execute(KnowledgeBases kBase);
    
    public void setKBaseSupervisorDelegate(IKBaseSupervisorDelegate delegate) {
        this._kBaseSupervisorDelegate = delegate;
    }
    
    public void setDesiredCells(List<String> desiredCells){
        this._desiredCells = desiredCells;
    }
}
