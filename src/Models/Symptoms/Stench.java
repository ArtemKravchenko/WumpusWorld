/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Symptoms;

import Models.Roles.Wumpus;

/**
 *
 * @author admin
 */
public class Stench extends Symptom {
    
    public Stench () {
        this._role = new Wumpus();
    }
    
    public static int antiWeight(){
        return 0;
    }
    
    public static int weight () {
        return 1000;
    }
}
