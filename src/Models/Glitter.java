/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author admin
 */
public class Glitter extends Symptom {
    
    public Glitter() {
        this._lifeState = AgentLifeState.Alive;
        this._role = new Gold();
    }
    
    @Override
    public String toString() {
        return "Glitter";
    }
    
    public static String literal() {
        return "Glitter";
    }
}
