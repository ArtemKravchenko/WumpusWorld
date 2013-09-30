/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.InferenceAlgoritms;

import Models.Abstract.IPrintState;
import generated.KnowledgeBases;
import java.util.List;

/**
 *
 * @author admin
 */
public abstract class AbstractInferenceAlgorithm implements IPrintState {
    
    protected IKBaseSupervisorDelegate _kBaseSupervisorDelegate;
    protected List<String> _desiredCells;
    
    public abstract void execute(KnowledgeBases kBase);
    
    public void setKBaseSupervisorDelegate(IKBaseSupervisorDelegate delegate) {
        this._kBaseSupervisorDelegate = delegate;
    }
    
    public void setDesiredCells(List<String> desiredCells){
        this._desiredCells = desiredCells;
    }
    
    @Override
    public void printCurrentState() {
        this.writeLog("----- " + this.getClass().getName() + "(State) -----");
    }
}
