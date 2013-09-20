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
        this._role = new Wumpus();
    }
    
    @Override
    public String toString() {
        return "Stench";
    }
    
    public static String literal() {
        return "Stench";
    }
    
}
