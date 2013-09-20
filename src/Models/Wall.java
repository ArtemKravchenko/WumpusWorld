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
    
    public @Override String toString() {
        return "Wall";
    }
    
}
