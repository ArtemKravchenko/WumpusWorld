/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Logic.Helper;
import Models.Abstract.AbstractWorkSpaceCell;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class BaseWorkSpaceCell extends AbstractWorkSpaceCell {
 
    protected List<IBaseCellProperty> _properties;
    
    public BaseWorkSpaceCell() {
        this._properties = new ArrayList<>();
        this._properties.add(new EmptyCellProperty());
    }
    
    public List<IBaseCellProperty> getProperties() {
        return this._properties;
    }
    
    public void addProperty(IBaseCellProperty property) {
        if (this._properties.size() == 1 && Helper.getEntityNameFromClass(this._properties.get(0).getClass().getName()).equals(Helper.getEntityNameFromClass(EmptyCellProperty.class.getName()))) {
            this._properties.remove(0);
        }
        
        this._properties.add(property);
    }

    @Override
    public int getX() {
        return this._x;
    }

    @Override
    public int getY() {
        return this._y;
    }

    @Override
    public void setCurrentCell(int y, int x) {
        this._x = x;
        this._y = y;
    }
    
    @Override
    public String toString() {
        return "[" + this._y + "," + this._x + "]";
    }

    @Override
    public AbstractWorkSpaceCell getCurrentCell() {
        return this;
    }
}
