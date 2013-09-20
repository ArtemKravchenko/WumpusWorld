/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author admin
 */
public class Wall extends Role {
    
    public Wall () {
        this._lifeState = AgentLifeState.Bump;
    }
    
    @Override
    public String toString() {
        return "Wall";
    }
    
    public static String literal() {
        return "Wall";
    }
    
}
