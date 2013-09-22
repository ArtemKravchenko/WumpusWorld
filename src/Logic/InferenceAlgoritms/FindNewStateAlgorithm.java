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
public abstract class FindNewStateAlgorithm {
    
    protected IKBaseSupervisorDelegate _kBaseSupervisorDelegate;
    
    public abstract void execute(KnowledgeBases kBase);
    public void setKBaseSupervisorDelegate(IKBaseSupervisorDelegate delegate) {
        this._kBaseSupervisorDelegate = delegate;
    }
}
