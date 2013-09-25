/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Roles;

import Models.Enums.AgentLifeState;

/**
 *
 * @author admin
 */
public class Wall extends Role {
    
    public static int antiWeight(){
        return 0;
    }
    
    public static int weight () {
        return -500;
    }
    
    @Override
    public AgentLifeState getLifeState() {
        return AgentLifeState.Bump;
    }
    
}
