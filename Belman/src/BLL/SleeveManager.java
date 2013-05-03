/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Sleeve;
import DAL.SleeveDBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Rashid
 */
public class SleeveManager extends Observable
{
    
    private SleeveDBManager accessor;
    private static SleeveManager instance;
    
    private SleeveManager() throws IOException
    {
        accessor = SleeveDBManager.getInstance();
    }
    
    public static SleeveManager getInstance() throws IOException{
        if( instance == null ) instance = new SleeveManager();
        return instance;
    }
    
     public ArrayList<Sleeve> getAll() throws IOException, SQLException
    {
       return accessor.getAllSleeves();       
    }
}
