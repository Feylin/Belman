/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Operator;
import BE.Sleeve;
import DAL.SleeveDBManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Observable;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */
public class SleeveLogManager extends Observable
{
    private SleeveDBManager accessor;
    private static SleeveLogManager instance;

    private SleeveLogManager() throws IOException
    {
        accessor = SleeveDBManager.getInstance();
    }
    
    public static SleeveLogManager getInstance() throws FileNotFoundException, IOException
    {
        if (instance == null)
        {
            instance = new SleeveLogManager();
        }
        return instance;
    }
    
    public void addLog(int id, Operator op, int hasCut, int timeSpent) throws SQLException
    {
        accessor.addLog(id, op, hasCut, timeSpent);
    }
    
    public int getQuantity(Sleeve s, int opid) throws SQLException
    {
        return accessor.getQuantity(s, opid);
    }
}
