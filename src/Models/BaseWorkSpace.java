/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Models.Abstract.AbstractWorkSpace;
import Models.Abstract.AbstractWorkSpaceCell;

/**
 *
 * @author admin
 */
public class BaseWorkSpace extends AbstractWorkSpace {

    @Override
    public AbstractWorkSpaceCell[] getWorkSpaceCells() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setWorkSpaceCells(AbstractWorkSpaceCell[] worSpace) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public BaseWorkSpaceCell getCell(String cell) {
        return new BaseWorkSpaceCell();
    }
}
