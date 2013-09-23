/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Symptoms;

import Models.Roles.Pit;

/**
 *
 * @author admin
 */
public class Breeze extends Symptom {
    
    public Breeze () {
        this._role = new Pit();
    }
    
    public static int weight() {
        return 1000;
    }
}
