/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author admin
 */
public class Symptom extends WorkSpaceCell {
    
    private Role _role;
    
    public Role getRole () {
        return this._role;
    }
    
    public @Override String toString() {
        return "Symptom";
    }
    
}
