/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Material;
import BE.Order;
import BE.Sleeve;
import BE.StockItem;
import DAL.ProductionOrderDBManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */
public class OrderManager extends Observable
{
    private ProductionOrderDBManager accessor;
    private static OrderManager instance;

    private OrderManager() throws IOException
    {
        accessor = ProductionOrderDBManager.getInstance();
    }

    public static OrderManager getInstance() throws FileNotFoundException, IOException
    {
        if (instance == null)
        {
            instance = new OrderManager();
        }
        return instance;
    }

    public Order add(Order order) throws SQLException
    {
        Order o = accessor.add(order);
        setChanged();
        notifyObservers();
        return o;
    }
    
     public ArrayList<Order> getPaused() throws IOException, SQLException
    {
        return accessor.getPaused();
    }

    /**
     * Opdaterer en ordre i databasen
     *
     * @param member Medlemsobjektet for det medlem der skal opdateres. Dete
     * objekt bør indeholde de nye informationer (navn, adresse, telefonnummer
     * m.v.)
     * @throws TBSQLException
     */
    public void update(Order order) throws SQLException
    {
        try
        {
            accessor.update(order);
            setChanged();
            notifyObservers();
        }
        catch (SQLException e)
        {            
        }
    }

    public ArrayList<Order> getAll() throws IOException, SQLException
    {
        return accessor.getAll();
    }
    
    public void updateStatus(Order o) throws SQLException
    {
            accessor.updateStatus(o);
            setChanged();
            notifyObservers();             
    }
    
    public void updateErrorMessage(Order o, String message) throws SQLException
    {   
            accessor.updateErrorMessage(o, message);
            setChanged();
            notifyObservers();       
    }

    public ArrayList<Order> getOrderByMaterial(StockItem s) throws SQLException, IOException
    {
        return accessor.getOrderByStock(s);
    }
    
    public ArrayList<Order> getOrdersBySleeve(Sleeve s) throws IOException, SQLException
    {
        return accessor.getOrderBySleeve(s);
    }
    
    public ArrayList<Order> getOrderByStock(StockItem s) throws IOException, SQLException
    {
        return accessor.getOrderByStock(s);
    }
    

    public void remove(int prodOrderId) throws SQLException
    {
        accessor.remove(prodOrderId);
        setChanged();
        notifyObservers();
    }
}
