/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Material;
import BE.Operator;
import BE.Order;
import BE.Sleeve;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */

public class SleeveDBManager
{
    private static final String ID = "id";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";
    private static final String THICKNESS = "thickness";
    private static final String CIRCUMFERENCE = "circumference";
    private static final String MATERIAL_ID = "materialId";
    private static final String P_ORDER_ID = "pOrderId";
    private static final String NAME = "name";
    private Connector connector;
    private static SleeveDBManager instance;

    public SleeveDBManager() throws IOException
    {
        connector = Connector.getInstance();
    }

    public static SleeveDBManager getInstance() throws IOException
    {
        if (instance == null)
        {
            instance = new SleeveDBManager();
        }
        return instance;
    }

    public ArrayList<Sleeve> getSleevesByOrder(Order o) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT Sleeve.*, Material.Name FROM Sleeve, Material, ProductionOrder WHERE Sleeve.materialId = Material.id AND Sleeve.pOrderId = ProductionOrder.pOrderId AND ProductionOrder.pOrder = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, o.getOrderName());
            ResultSet rs = ps.executeQuery();

            ArrayList<Sleeve> sleeves = new ArrayList<>();
            while (rs.next())
            {
                sleeves.add(getOneSleeve(rs));
            }
            return sleeves;
        }
    }

    public Sleeve getOneSleeve(ResultSet rs) throws SQLException
    {
        int id = rs.getInt(ID);
        GregorianCalendar gc = null;
        Date date = rs.getTimestamp("startTime");
        if(date != null)
        {
            gc = new GregorianCalendar();
            gc.setTime(date);
        }
        GregorianCalendar gc2 = null;
        date = rs.getTimestamp("endTime");
        if(date != null)
        {
            gc2 = new GregorianCalendar();
            gc2.setTime(date);
        }       
        double thickness = rs.getDouble("thickness");
        double circumference = rs.getDouble(CIRCUMFERENCE);
        int materialId = rs.getInt(MATERIAL_ID);
        int pOrderId = rs.getInt(P_ORDER_ID);
        String materialName = rs.getString(NAME);

        return new Sleeve(id, gc, gc2, thickness, circumference, materialId, pOrderId, new Material(materialName));
    }

    public Sleeve get(int id) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM Sleeve where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getSleeve(rs);
            }
            return null;
        }
    }

    public Sleeve getSleeve(ResultSet rs) throws SQLException
    {
        int id = rs.getInt(ID);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(rs.getTimestamp(START_TIME));
        GregorianCalendar gc2 = new GregorianCalendar();
        gc2.setTime(rs.getTimestamp(END_TIME));
        double thickness = rs.getDouble(THICKNESS);
        double circumference = rs.getDouble(CIRCUMFERENCE);
        int materialId = rs.getInt(MATERIAL_ID);
        int pOrderId = rs.getInt(P_ORDER_ID);

        return new Sleeve(id, gc, gc2, thickness, circumference, materialId, pOrderId);
    }

    public void updateSleeve(Sleeve s) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "UPDATE Sleeve SET startTime = ?, endTime = ?, thickness = ?, circumference = ?, materialid = ?, porderid = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            Date startDate = new Date();
            Timestamp startTime = new Timestamp(startDate.getTime());
            Date endDate = new Date();
            Timestamp endTime = new Timestamp(endDate.getTime());
            ps.setTimestamp(1, startTime, s.getStartTime());
            ps.setTimestamp(2, endTime, s.getEndTime());
            ps.setDouble(3, s.getThickness());
            ps.setDouble(4, s.getCircumference());
            ps.setInt(5, s.getMaterialId());
            ps.setInt(6, s.getpOrderId());
            ps.setInt(7, s.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new SQLException("Unable to update sleeve");
            }
        }
    }
    
    public void updateSleeveStartTim(Sleeve s) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "UPDATE Sleeve SET startTime = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            Date startDate = new Date();
            Timestamp startTime = new Timestamp(startDate.getTime());
            ps.setTimestamp(1, startTime);
            ps.setInt(2, s.getId());
            
            int affectedrows = ps.executeUpdate();
            if (affectedrows == 0)
            {
                throw new SQLException("Unable to update sleeve start time");
            }
        }
    }
    
    public void updateSleeveEndTime(Sleeve s) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "UPDATE Sleeve SET endTime = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            Date endDate = new Date();
            Timestamp endTime = new Timestamp(endDate.getTime());
            ps.setTimestamp(1, endTime);
            ps.setInt(2, s.getId());
            
            int affectedrows = ps.executeUpdate();
            if (affectedrows == 0)
            {
                throw new SQLException("Unable to update sleeve end time");
            }
        }
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

    public void addLog(int id, Operator op, int hasCut, int timeSpent) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "INSERT INTO SleeveLog (sleeveId, quantity, operatorId, timeSpent) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setInt(2, hasCut);
            ps.setInt(3, op.getId());
            ps.setInt(4, timeSpent);

            int affectedRows = ps.executeUpdate();
            if (affectedRows <= 0)
            {
                throw new SQLException();
            }
        }
    }
    
    public int getQuantity(Sleeve s, int opid) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM SleeveLog WHERE sleeveid = ? AND operatorId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, s.getId());
            ps.setInt(2, opid);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int quantity = rs.getInt("quantity");
                return quantity;
            }
            return 0;
        }
    }
}
