/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Abstract;

import Logic.ActionManagment.IActionManager;
import Logic.InferenceAlgoritms.AbstractInferenceAlgorithm;
import Logic.InferenceAlgoritms.AlgorithmsManager;
import generated.KnowledgeBases;

/**
 *
 * @author admin
 */
public abstract class AbstractAgent extends AbstractEntity {
    
    protected ITarget _target;
    protected IAgentDelegate _delegate;
    protected IActionManager _actionManager;
    protected KnowledgeBases _kBase;
    protected AbstractInferenceAlgorithm _inferenceAlgorithm;
    protected AlgorithmsManager _algoritmManager;
    
    public abstract void setDelegate(IAgentDelegate delegate);
    public abstract void printCurrentState();
    public abstract void doNextStep(int stepCounter);
    protected abstract void writeToKBase(String sentence);
}
