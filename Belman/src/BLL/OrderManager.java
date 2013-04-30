/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Order;
import DAL.ProductionOrderDBManager;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Daniel
 */
public class OrderManager
{
    private ProductionOrderDBManager ODB;
    
    private OrderManager()
    {
        
    }
    
    public Order getOrderById()
    {
        return ODB.getOrder();
    }
    
    public ArrayList<Order> allOrders()
    {
        return ODB.getAll();
    }
}
