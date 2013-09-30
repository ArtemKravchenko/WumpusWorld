/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Abstract;

/**
 *
 * @author admin
 */
public interface IAgentDelegate {
    
    public void agentWasKilled(AbstractAgent agent); 
    public void targetWasReached(ITarget target);
    public void setAgentCell(int y, int x);
}
