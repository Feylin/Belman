/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.CoilType;
import BE.Material;
import BE.Order;
import BE.SalesOrder;
import BE.Sleeve;
import BE.StockItem;
import DAL.ProductionOrderDBManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class OrderManagerTest
{

    static ProductionOrderDBManager odbmgr = null;

    public OrderManagerTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
        System.out.println("-------Starting jUnittest-------");
        System.out.println("");
        System.out.println("");
        try
        {
            odbmgr = ProductionOrderDBManager.getInstance();
        }
        catch (IOException ex)
        {
            ex.getMessage();
        }
    }

    @AfterClass
    public static void tearDownClass()
    {
        
    }

    public void testConstructOrderManager()
    {
    }

    /**
     * Test of getInstance method, of class OrderManager.
     */
    @Test
    public void testGetInstance() throws Exception
    {

    }

    /**
     * Test of getPaused method, of class OrderManager.
     */
    @Test
    public void testGetPaused() throws Exception
    {
        System.out.println("Testing getPaused :" );
        assertNotNull(odbmgr);
        
        ArrayList<Order> o = odbmgr.getPaused();
        assertNotNull(o);
        
        for(int i = 0; i < odbmgr.getPaused().size(); i++)
        {
            assertEquals("PAUSED", o.get(i).getStatus()); 
        }
        System.out.println("Amount of paused orders: " + o.size());
        
        System.out.println("");
        System.out.println("");
    }

    /**
     * Test of update method, of class OrderManager.
     */
    @Test
    public void testUpdate() throws Exception
    {
    }

    /**
     * Test of getAll method, of class OrderManager.
     */
    @Test
    public void testGetAll() throws Exception
    {
        System.out.println("Testing getAll method :");
        assertNotNull(odbmgr);
        
        System.out.println("Total amount of orders : " + odbmgr.getAll().size());
        
        System.out.println("");
        System.out.println("");
    }

    /**
     * Test of updateStatus method, of class OrderManager.
     */
    @Test
    public void testUpdateStatus() throws Exception
    {
    }

    /**
     * Test of updateErrorMessage method, of class OrderManager.
     */
    @Test
    public void testUpdateErrorMessage() throws Exception
    {
    }

    /**
     * Test of getOrderByStock method, of class OrderManager.
     */
    @Test
    public void testGetOrderByStock() throws Exception
    {
        System.out.println("Testing getOrderByStock :" );
        assertNotNull(odbmgr);

        int id = 19296;
        String chargeNo = "68400 3";
        double length = 2500;
        int stockQuantity = 150;
        int coilTypeId = 1335;
        int sleeveId = 1335;
        String code = "RP-09/99";
        double width = 1250;
        double thickness = 0.4;
        double density = 7.9;
        String name = "14.541";
        int materialId = 10;
        
        StockItem s = new StockItem(id, chargeNo, length, stockQuantity, coilTypeId, sleeveId, new CoilType(code, width, thickness, materialId), new Material(density, name));        
        
        ArrayList<Order> o = odbmgr.getOrderByStock(s);
        assertNotNull(o);
        
        for(int i = 0; i < o.size(); i++)
        {
            assertEquals(s.getSleeveId(), o.get(i).getSleeve().getId());
        }
        
        System.out.println("The amount of orders assosiated with order" + s.getId() + " is " + o.size());
        System.out.println("");
        System.out.println("");
    }
}
