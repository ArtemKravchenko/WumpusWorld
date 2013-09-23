/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Abstract;

/**
 *
 * @author admin
 */
public abstract class AbstractWorkSpaceCell {
    
    protected int _x;
    protected int _y;
    
    public abstract int getX();
    public abstract int getY();
    
    public abstract void setX(int x);
    public abstract void setY(int y);
}
