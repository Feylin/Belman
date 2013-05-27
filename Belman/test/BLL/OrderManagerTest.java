/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Order;
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
        System.out.println("Testing getInstance : ");
        try
        {
            odbmgr = ProductionOrderDBManager.getInstance();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("ERROR - omgr threw a fileNotFoundException: " + ex.getMessage());
        }
        catch (IOException ex)
        {
            System.out.println("ERROR - omgr threw a inputOutputException: " + ex.getMessage());

        }
        System.out.println("Number of orders : " + odbmgr.getAll().size());
        assertNotNull(odbmgr);
        
        System.out.println("");
        System.out.println("");
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
        System.out.println("Testing update method: EMPTY");
        
        
        System.out.println("");
        System.out.println("");
        
        
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
    }
}
