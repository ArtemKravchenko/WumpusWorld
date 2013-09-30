/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Abstract;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public abstract class AbstractWorkSpace {
    
    protected List<AbstractWorkSpaceCell> _workSpaceCells;
    public AbstractWorkSpace() {
        this._workSpaceCells = new ArrayList<>(); 
    }
    
    public abstract void addWorkCell(AbstractWorkSpaceCell cell);
    public abstract List<AbstractWorkSpaceCell> getWorkSpaceCells();
    
}
