/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Models.Breeze;
import Models.Glitter;
import Models.Gold;
import Models.Pit;
import Models.Stench;
import Models.Wall;
import Models.CellProperty;
import Models.Wumpus;
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
    
    public static int[] getRowAndColFromString(String string) {
        int[] rowAndCol = new int[2];
        
        String[] array = string.split(",");
        String row = array[0].replace("[", "");
        String col = array[1].replace("]", "");
        
        rowAndCol[0] = Integer.parseInt(row);
        rowAndCol[1] = Integer.parseInt(col);
        
        return rowAndCol;
    }
    
    public static void printCurrentTime(){
        System.out.println(dateFormat.format(date));
    }
    
    public static String getStringFromRowAndCol(int row, int col) {
        return "[" + row + "," + col + "]";
    }
    
    public static CellProperty getCellFromString(String string) {
        CellProperty cell = null;
        
        String[] array = string.split(":");
        String cellStringType = array[0].replace("!", "");
        
        if (cellStringType.equals(Pit.literal())) {
            cell = new Pit();
        } if (cellStringType.equals(Wumpus.literal())) {
            cell = new Wumpus();
        } if (cellStringType.equals(Wall.literal())) {
            cell = new Wall();
        } if (cellStringType.equals(Breeze.literal())) {
            cell = new Breeze();
        } if (cellStringType.equals(Stench.literal())) {
            cell = new Stench();
        } if (cellStringType.equals(Glitter.literal())) {
            cell = new Glitter();
        } if (cellStringType.equals(Gold.literal())) {
            cell = new Gold();
        } else {
            cell = new CellProperty();
        }
        
        return cell;
    } 
}
