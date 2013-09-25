/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Symptoms;

import Models.Gold;

/**
 *
 * @author admin
 */
public class Glitter extends Symptom {
    
    public Glitter() {
        this._role = new Gold();
    }
    
    public static int antiWeight(){
        return -1000;
    }
    
    public static int weight () {
        return 10000;
    }
}
