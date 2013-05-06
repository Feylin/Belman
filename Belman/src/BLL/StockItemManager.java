/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BLL;

import BE.Sleeve;
import BE.StockItem;
import DAL.StockItemDBManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Mak
 */
public class StockItemManager extends Observable
{
    private static StockItemDBManager accessor;
    private static StockItemManager instance;
    
    public StockItemManager() throws IOException
    {
        accessor = StockItemDBManager.getInstance();
    }
    
    public static StockItemManager getInstance() throws FileNotFoundException, IOException{
        if( instance == null ) instance = new StockItemManager();
        return instance;
    }
    
    public StockItem add(StockItem item) throws SQLException
    {
        StockItem i = accessor.add(item); 
        setChanged();
        notifyObservers();
        return i;
    }
    
    public ArrayList<StockItem> getAll() throws IOException, SQLException
    {
       return accessor.getAllItems();        
    }
    
    public ArrayList<StockItem> getItemBySleeve(Sleeve s) throws IOException, SQLException
    {
        return accessor.getItemBySleeve(s);
    }
    
    public void remove(int id) throws SQLException
    {
        accessor.remove(id);
        setChanged();
        notifyObservers();
    }
    
    public ArrayList<StockItem> getItemByMaterial(double materialName) throws IOException, SQLException
    {
        return accessor.getItemByMaterial(materialName);
    }
    
    
}
