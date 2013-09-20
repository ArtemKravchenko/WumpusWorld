/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author admin
 */
public class Gold extends Role {
    
    public Gold(){
        this._lifeState = AgentLifeState.Alive;
    }
    
    @Override
    public String toString() {
        return "Gold";
    }
    
    public static String literal() {
        return "Gold";
    }
    
}
