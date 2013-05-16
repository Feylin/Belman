/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAL;

import BE.Material;
import BE.Order;
import BE.Sleeve;
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
            String sql = "SELECT * FROM Sleeve, Material, ProductionOrder WHERE Sleeve.materialId = Material.id AND Sleeve.pOrderId = ProductionOrder.pOrderId AND ProductionOrder.pOrder = ?";
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
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(rs.getTimestamp(START_TIME));
        GregorianCalendar gc2 = new GregorianCalendar();
        gc2.setTime(rs.getTimestamp(END_TIME));
        double thickness = rs.getDouble(THICKNESS);
        double circumference = rs.getDouble(CIRCUMFERENCE);
        int materialId = rs.getInt(MATERIAL_ID);
        int pOrderId = rs.getInt(P_ORDER_ID);
        String materialName = rs.getString(NAME);
        
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
