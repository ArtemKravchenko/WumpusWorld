/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author admin
 */
public class Wumpus extends Role {

    public Wumpus () {
        this._lifeState = AgentLifeState.Dead;
    }
    
    @Override
    public String toString() {
        return "Wall";
    }
    
    public static String literal() {
        return "Wall";
    }
    
}
