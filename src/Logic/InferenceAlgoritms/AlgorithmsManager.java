/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.InferenceAlgoritms;

/**
 *
 * @author admin
 */
public class AlgorithmsManager {
    
    private String[] _algorithms;
    private int _indexOfAlgorithm;
    
    public AlgorithmsManager(String[] algorithms) {
        this._algorithms = algorithms;
        this._indexOfAlgorithm = 0;
    }

    public AlgorithmsManager(Object[] toArray) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private AbstractInferenceAlgorithm getClassInstanceForName(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class c = Class.forName(className);
        AbstractInferenceAlgorithm instance = (AbstractInferenceAlgorithm)c.newInstance();
        return instance;
    }
    
    public AbstractInferenceAlgorithm getFirstAlgorithm() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        return this.getClassInstanceForName(this._algorithms[0]);
    }
    
    public AbstractInferenceAlgorithm changeCurrentAlgorithm() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this._indexOfAlgorithm++;
        if (this._indexOfAlgorithm >= this._algorithms.length) {
            this._indexOfAlgorithm = 0;
        }
        AbstractInferenceAlgorithm algorithm = this.getClassInstanceForName(this._algorithms[this._indexOfAlgorithm]);
        if (algorithm.getClass().isInstance(ResolutionAlgorithm.class)) {
            return changeCurrentAlgorithm();
        }
        return algorithm;
    }
    
}
