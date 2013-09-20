/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author admin
 */
public class Pit extends Role {
    
    public Pit () {
        this._lifeState = AgentLifeState.Dead;
    }
    
    public @Override String toString() {
        return "Pit";
    }
    
}
