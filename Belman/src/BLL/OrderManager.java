/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Order;
import DAL.ProductionOrderDBManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

/**
 *
 * @author Daniel
 */
public class OrderManager extends Observable
{
    private ProductionOrderDBManager accessor;
    private static OrderManager instance;
    
    private OrderManager() throws IOException
    {
        accessor = ProductionOrderDBManager.getInstance();
    }
    
    public static OrderManager getInstance() throws FileNotFoundException, IOException{
        if( instance == null ) instance = new OrderManager();
        return instance;
    }
    
    public Order add(Order order) throws SQLException
    {
        Order o = accessor.add(order); 
        setChanged();
        notifyObservers();
        return o;
    }
    
    public ArrayList<Order> getAll() throws IOException, SQLException
    {
       return accessor.getAll();        
    }
    
    public void remove(int prodOrderId) throws SQLException
    {
        accessor.remove(prodOrderId);
        setChanged();
        notifyObservers();
    }
}
