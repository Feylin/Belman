///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package BLL;
//
//
//import BE.CoilType;
//import BE.StockItem;
//import BE.Material;
//import DAL.CoilTypeDBManager;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Observable;
///**
// *
// * @author Rashid
// */
//public class CoilTypeManager
//{
//    
//    private CoilTypeDBManager accessor;
//    private static OrderManager instance;
//    
//    private CoilTypeManager() throws IOException
//    {
//        accessor = CoilTypeDBManager.getInstance();
//    }
//    
//    public static OrderManager getInstance() throws FileNotFoundException, IOException{
//        if( instance == null ) instance = new CoilTypeManager();
//        return instance;
//    }
//    
//    public Order add(Order order) throws SQLException
//    {
//        Order o = accessor.add(order); 
//        setChanged();
//        notifyObservers();
//        return o;
//    }
//    
//      /**
//     * Opdaterer en ordre i databasen
//     *
//     * @param member Medlemsobjektet for det medlem der skal opdateres. Dete
//     * objekt bør indeholde de nye informationer (navn, adresse, telefonnummer
//     * m.v.)
//     * @throws TBSQLException
//     */
//    public void update(Order order) throws SQLException
//    {
//        try
//        {
//            accessor.update(order);
//            setChanged();
//            notifyObservers();
//        }
//        catch (SQLException e)
//        {
//        }
//    }
//    
//    public ArrayList<Order> getAll() throws IOException, SQLException
//    {
//       return accessor.getAll();        
//    }
//    
//    public ArrayList<Order> getOrderByMaterial(Material m) throws SQLException, IOException
//    {
//        return accessor.getOrderByMaterial(m);
//    }
//    
//    public void remove(int prodOrderId) throws SQLException
//    {
//        accessor.remove(prodOrderId);
//        setChanged();
//        notifyObservers();
//    }
//}
