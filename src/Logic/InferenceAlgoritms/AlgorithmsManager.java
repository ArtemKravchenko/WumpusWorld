/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.InferenceAlgoritms;

import Logic.InferenceAlgoritms.FindNewStateAlgorithm;

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
    
    private FindNewStateAlgorithm getClassInstanceForName(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class c = Class.forName(className);
        FindNewStateAlgorithm instance = (FindNewStateAlgorithm)c.newInstance();
        return instance;
    }
    
    public FindNewStateAlgorithm getFirstAlgorithm() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        return this.getClassInstanceForName(this._algorithms[0]);
    }
    
    public FindNewStateAlgorithm changeCurrentAlgorithm() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (this._indexOfAlgorithm >= this._algorithms.length) {
            this._indexOfAlgorithm = 0;
        }
        return this.getClassInstanceForName(this._algorithms[++this._indexOfAlgorithm]);
    }
    
}
