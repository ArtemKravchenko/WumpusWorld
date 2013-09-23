/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Models.Abstract.AbstractWorkSpaceCell;
import Models.BaseWorkSpaceCell;
import Models.Symptoms.Breeze;
import Models.Symptoms.Glitter;
import Models.Gold;
import Models.Roles.Pit;
import Models.Symptoms.Stench;
import Models.Roles.Wall;
import Models.EmptyCellProperty;
import Models.Roles.Wumpus;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author admin
 */
public class Helper {
    
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static Date date = new Date();
    
    public static BaseWorkSpaceCell getRowAndColFromString(String string) {
        BaseWorkSpaceCell cell = new BaseWorkSpaceCell();
        
        String[] array = string.split("\\,");
        String row = array[0].replace("[", "");
        String col = array[1].replace("]", "");
        
        cell.setY(Integer.parseInt(row));
        cell.setX(Integer.parseInt(col));
        
        return cell;
    }
    
    public static String getEntityNameFromClass(String className) {
        String[] array = className.split("\\.");
        return array[array.length - 1];
    }
    
    public static void printCurrentTime(){
        System.out.println(dateFormat.format(date));
    }
    
    public static String getStringFromRowAndCol(int row, int col) {
        return "[" + row + "," + col + "]";
    }
    
    public static String getConjuction() {
        return "(con)";
    }
    
    public static String getDisjuction() {
        return "(dis)";
    }
}
