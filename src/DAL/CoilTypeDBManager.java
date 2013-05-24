/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.CoilType;
import BE.StockItem;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Rashid
 */
public class CoilTypeDBManager
{
    private static final String ID = "id";
    private static final String CODE = "code";
    private static final String WIDTH = "width";
    private static final String THICKNESS = "thickness";
    private static final String MATERIAL_ID = "materialId";
    
    private Connector connector;
    private static CoilTypeDBManager instance;

    public CoilTypeDBManager() throws IOException
    {
        connector = Connector.getInstance();
    }

    public static CoilTypeDBManager getInstance() throws IOException
    {
        if (instance == null)
        {
            instance = new CoilTypeDBManager();
        }
        return instance;
    }

    public CoilType add(CoilType c) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "INSERT INTO CoilType(code, width, thickness, materialId) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getCode());
            ps.setDouble(2, c.getWidth());
            ps.setDouble(3, c.getThickness());
            ps.setInt(4, c.getMaterialId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new SQLException("Unable to add an Coil");
            }
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            return new CoilType(id, c);
        }
    }

    public ArrayList<CoilType> getAllItems() throws SQLException, IOException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM CoilType";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<CoilType> types = new ArrayList<>();
            while (rs.next())
            {
                types.add(getOneItem(rs));
            }
            return types;
        }

    }
//
//    public void remove(int id) throws SQLException
//    {
//        try (Connection con = connector.getConnection())
//        {
//            String sql = "DELETE * FROM StockItem WHERE id = ?";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setInt(1, id);
//
//            int affectedRows = ps.executeUpdate();
//            if (affectedRows == 0)
//            {
//                throw new SQLException("Unable to remove StockItem!");
//            }
//        }
//    }

    public ArrayList<CoilType> getCoilTypeByStockItem(StockItem s) throws SQLException, IOException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM CoilType, StockItem WHERE StockItem.ID = ? AND StockItem.CoiltypeId = CoilType.Id";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, s.getId());

            ArrayList<CoilType> items = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                items.add(getOneItem(rs));
            }
            return items;
        }
    }

    protected CoilType getOneItem(ResultSet rs) throws SQLException, IOException
    {

        int id = rs.getInt(ID);
        String code = rs.getString(CODE);
        double width = rs.getInt(WIDTH);
        double thickness = rs.getDouble(THICKNESS);
        int materialId = rs.getInt(MATERIAL_ID);

        return new CoilType(id, code, width, thickness, materialId);
    }
}
