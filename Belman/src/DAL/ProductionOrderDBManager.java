/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Order;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void add(Order order) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "INSERT INTO Order(sOrder, pOrderId, pOrder, dueDate, quantity, materialId, thickness, width, circumference) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, order.getOrder());
            ps.setInt(2, order.getProdOrderID());
            ps.setDouble(3, order.getProdOrder());
            ps.setString(4, convertDateToSQL(order.getDueDate()));
            ps.setInt(5, order.getQuantity());
            ps.setInt(6, order.getMaterialID());
            ps.setDouble(7, order.getThickness());
            ps.setDouble(8, order.getWidth());
            ps.setDouble(9, order.getCircumference());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new SQLException("Unable to add an Order");
            }
        }
    }

    public ArrayList<Order> getAll() throws SQLException, IOException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM ProductionOrder";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next())
            {
                orders.add(getOneOrder(rs));
            }
            return orders;
        }
    }
    
    

    protected Order getOneOrder(ResultSet rs) throws SQLException, FileNotFoundException, IOException
    {


        double sOrder = rs.getDouble("sOrder");
        int prodOrderId = rs.getInt("pOrderId");
        double prodOrder = rs.getDouble("pOrder");
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(rs.getTimestamp("dueDate"));
        int quantity = rs.getInt("quantity");
        int materialId = rs.getInt("materialId");
        double thickness = rs.getDouble("thickness");
        double width = rs.getDouble("width");
        double circumference = rs.getDouble("circumference");

        return new Order(sOrder, prodOrderId,prodOrder, gc, quantity, materialId, thickness, width, circumference);  
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
