/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Abstract.ITarget;
import Models.Roles.Role;
import Models.Enums.AgentLifeState;

/**
 *
 * @author admin
 */
public class Gold extends Role implements ITarget {
    
    public static int weight(){
        return 1000000;
    }
    
    public static int antiWeight(){
        return 0;
    }
    
    @Override
    public AgentLifeState getLifeState() {
        return AgentLifeState.Alive;
    }
    
}
