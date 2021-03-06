/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Material;
import BE.Order;
import BE.SalesOrder;
import BE.Sleeve;
import BE.StockItem;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Mak
 */
public class ProductionOrderDBManager
{

    private Connector connector;
    private static ProductionOrderDBManager instance;

    public ProductionOrderDBManager() throws IOException
    {
        connector = Connector.getInstance();
    }

    public static ProductionOrderDBManager getInstance() throws IOException
    {
        if (instance == null)
        {
            instance = new ProductionOrderDBManager();
        }
        return instance;
    }

    public Order add(Order order) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "INSERT INTO ProductionOrder(sOrderId, pOrder, dueDate, quantity, conductedQuantity "
                    + " thickness, width, status) VALUES (?,?,?,?,?,?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, order.getsOrderId());
            ps.setString(2, order.getOrderName());
            ps.setString(3, convertDateToSQL(order.getDueDate()));
            ps.setInt(4, order.getQuantity());
            ps.setInt(5, order.getConductedQuantity());
            ps.setDouble(6, order.getWidth());
            ps.setString(7, order.getStatus());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new SQLException("Unable to add an Order");
            }
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            return new Order(id, order);
        }
    }

    public ArrayList<Order> getAll() throws SQLException, IOException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM ProductionOrder, SalesOrder, Sleeve, Material WHERE ProductionOrder.pOrderId = Sleeve.pOrderId AND Sleeve.materialId = Material.id AND ProductionOrder.sOrderId = SalesOrder.sOrderId ORDER BY UrgentFlag DESC, ProductionOrder.dueDate";
            PreparedStatement ps = con.prepareStatement( sql );
            ResultSet rs = ps.executeQuery();

            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next())
            {
                orders.add(getOneOrder(rs));
            }
            return orders;
        }
    }

    public ArrayList<Order> getOrderByStock(StockItem s) throws SQLException, IOException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM ProductionOrder, SalesOrder, Sleeve, Material, StockItem, CoilType WHERE ProductionOrder.sOrderId = SalesOrder.sOrderId AND ProductionOrder.pOrderId = Sleeve.pOrderId AND Sleeve.materialId = Material.id AND Material.id = CoilType.materialId AND CoilType.id = StockItem.coilTypeId AND Sleeve.thickness = CoilType.thickness AND StockItem.chargeNo = ? ORDER BY ProductionOrder.dueDate, Sleeve.materialId, Sleeve.thickness";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getChargeNo());

            ResultSet rs = ps.executeQuery();

            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next())
            {
                orders.add(getOneOrder(rs));
            }
            return orders;
        }
    }
    
    public ArrayList<Order> getOrderBySleeve (Sleeve s) throws SQLException, IOException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM ProductionOrder, StockItem, CoilType, Material, Sleeve, SalesOrder WHERE ProductionOrder.pOrderId = Sleeve.pOrderId AND Sleeve.Id = StockItem.sleeveId AND StockItem.coilTypeId = CoilType.id AND CoilType.materialId = Material.id AND ProductionOrder.sOrderId = SalesOrder.sOrderId AND Sleeve.id = ?";        
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, s.getId());

            ResultSet rs = ps.executeQuery();

            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next())
            {
                orders.add(getOneOrder(rs));
            }
            return orders;
        }
    }      
    
    

    public void remove(int prodOrderId) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "DELETE * FROM ProductionOrder WHERE pOrderId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, prodOrderId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new SQLException("Unable to remove Order!");
            }
        }
    }
    
    public void updateStatus(Order o) throws SQLException
    {
        try(Connection con = connector.getConnection())
        {
            String sql = "UPDATE ProductionOrder SET ProductionOrder.status = ? WHERE ProductionOrder.pOrderId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, o.getStatus());
            ps.setInt(2, o.getOrderId());  
            
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                 throw new SQLException( "Unable to update Status" );
            }
        }
    }
    
    public void updateErrorMessage(Order o, String message) throws SQLException
    {
        try(Connection con = connector.getConnection())
        {
            String sql = "UPDATE ProductionOrder SET ProductionOrder.errorOccured = ? WHERE ProductionOrder.pOrderId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, message);
            ps.setInt(2, o.getOrderId());  
            
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                 throw new SQLException( "Unable to update Error message" );
            }
        }
    }
        

    public void update(Order o) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "UPDATE ProductionOrder SET sOrderId = ?, dueDate = ?, quantity = ?, conductedQuantity = ?, width = ?, status = ? WHERE pOrder = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, o.getsOrderId());
            ps.setString(2, convertDateToSQL(o.getDueDate()));
            ps.setInt(3, o.getQuantity());
            ps.setInt(4, o.getConductedQuantity());
            ps.setDouble(5, o.getWidth());
            ps.setString(6, o.getStatus());
            ps.setString(7, o.getOrderName());           

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new SQLException("Unable to update order");
            }
//            ps.setString(9, o.getType().toString());
        }
    }
    
      public ArrayList<Order> getPaused() throws SQLException, IOException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM ProductionOrder, SalesOrder, Sleeve, Material WHERE ProductionOrder.sOrderId = SalesOrder.sOrderId AND ProductionOrder.pOrderId = Sleeve.pOrderId AND Sleeve.materialId = Material.id AND ProductionOrder.status = 'PAUSED' ORDER BY ProductionOrder.urgentFlag DESC, ProductionOrder.dueDate, Sleeve.materialId, sleeve.thickness";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            ArrayList<Order> orders1 = new ArrayList<>();
            while (rs.next())
            {
                 orders1.add(getOneOrder(rs));
                 
            }
            return orders1;
        }
        
    }
      
     

    protected Order getOneOrder(ResultSet rs) throws SQLException, FileNotFoundException, IOException
    {
        int sOrderID = rs.getInt("sOrderId");
        int prodOrderId = rs.getInt("pOrderId");
        String pOrder = rs.getString("pOrder");
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(rs.getTimestamp("dueDate"));
        int quantity = rs.getInt("quantity");
        int conductedQuantity = rs.getInt("conductedQuantity");
        String errorOccured = rs.getString("errorOccured");
       
        
        double width = rs.getDouble("width");
        String status = rs.getString("status");
        boolean urgent = rs.getBoolean("urgentFlag");
        
        int sOrderId = rs.getInt("sOrderId");
        String custName = rs.getString("sOrder");
        String email = rs.getString("email");
        int phone = rs.getInt("phone");
        
        double thickness = rs.getDouble("thickness");
        double circumference = rs.getDouble("circumference");
        String materialName = rs.getString("name");                     
        
        return new Order(sOrderID, prodOrderId, pOrder, gc, quantity, conductedQuantity, width, status, urgent, new SalesOrder(sOrderId, custName, email, phone), new Sleeve(-1, null, null, thickness, circumference, -1, -1, new Material(materialName)), errorOccured);
    }

    protected String convertDateToSQL(GregorianCalendar date)
    {
        String str = String.format("%04d%02d%02d %02d:%02d:%02d",
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH) + 1,
                date.get(Calendar.DAY_OF_MONTH),
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE),
                0);
        return str;
    }
}
