/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Abstract;

/**
 *
 * @author admin
 */
public abstract class AbstractEntity {
    
    protected AbstractWorkSpaceCell _cell;
    
    public abstract int getX();
    public abstract int getY();
    
    public abstract void setCurrentCell(AbstractWorkSpaceCell cell);
    
}
