/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author admin
 */
public class Breeze extends Symptom {
    
    public Breeze () {
        this._lifeState = AgentLifeState.Alive;
        this._role = new Pit();
    }
    
    @Override
    public String toString() {
        return "Breeze";
    }
    
    public static int weight() {
        return 1000;
    }
    
    public static String literal() {
        return "Breeze";
    }
}
