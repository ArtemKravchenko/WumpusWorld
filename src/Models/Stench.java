/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author admin
 */
public class Stench extends Symptom {
    
    public Stench () {
        this._lifeState = AgentLifeState.Alive;
    }
    
    public @Override String toString() {
        return "Stench";
    }
    
}
