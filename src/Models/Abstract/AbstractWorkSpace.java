/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Abstract;

/**
 *
 * @author admin
 */
public abstract class AbstractWorkSpace {
    
    protected AbstractWorkSpaceCell[] _workSpaceCells;
    
    public abstract AbstractWorkSpaceCell[] getWorkSpaceCells();
    public abstract void setWorkSpaceCells(AbstractWorkSpaceCell[] worSpace);
    
}
