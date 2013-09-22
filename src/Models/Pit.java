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
    
    @Override
    public String toString() {
        return "Pit";
    }
    
    public static int weight () {
        return 1;
    }
    
    public static String literal() {
        return "Pit";
    }
    
}
