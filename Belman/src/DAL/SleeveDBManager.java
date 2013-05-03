/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAL;

import BE.Material;
import BE.Sleeve;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import sun.util.calendar.Gregorian;

/**
 *
 * @author Mak
 */
public class SleeveDBManager 
{
    
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
    
    public ArrayList<Sleeve> getAllSleeves() throws SQLException
    {
         try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM Sleeve, Material WHERE Sleeve.materialId = Material.id";
            PreparedStatement ps = con.prepareStatement(sql);
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
        int id = rs.getInt("id");
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(rs.getTimestamp("startTime"));
        GregorianCalendar gc2 = new GregorianCalendar();
        gc2.setTime(rs.getTimestamp("endTime"));
        double thickness = rs.getDouble("thickness");
        double circumference = rs.getDouble("circumference");
        int materialId = rs.getInt("materialId");
        int pOrderId = rs.getInt("pOrderId");
        String materialName = rs.getString("name");
        
        return new Sleeve(id, gc, gc2, thickness, circumference, materialId, pOrderId, new Material(materialName));
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
