/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Material;
import BE.StockItem;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mak
 */
public class StockItemDBManager
{

    private Connector connector;
    private static StockItemDBManager instance;

    public StockItemDBManager() throws IOException
    {
        connector = Connector.getInstance();
    }

    public static StockItemDBManager getInstance() throws IOException
    {
        if (instance == null)
        {
            instance = new StockItemDBManager();
        }
        return instance;
    }

    public StockItem add(StockItem item) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "INSERT INTO StockItem(code, materialId, materialName, materialDensity, chargeNo,"
                    + " length, width, thickness, stockQuantity) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, item.getCode());
            ps.setInt(2, item.getMaterialId());
            ps.setString(3, item.getMaterialName());
            ps.setDouble(4, item.getMaterialDensity());
            ps.setString(5, item.getChargeNr());
            ps.setDouble(6, item.getLength());
            ps.setDouble(7, item.getWidth());
            ps.setDouble(8, item.getThickness());
            ps.setDouble(9, item.getStockQuantity());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new SQLException("Unable to add an StockItem");
            }
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

            return new StockItem(id, item);
        }
    }

    public ArrayList<StockItem> getAllItems() throws SQLException, IOException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM StockItem, Material WHERE StockItem.MaterialId = Material.id";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<StockItem> items = new ArrayList<>();
            while (rs.next())
            {
                items.add(getOneItem(rs));
            }
            return items;
        }

    }

    public void remove(int id) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "DELETE * FROM StockItem WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new SQLException("Unable to remove StockItem!");
            }
        }
    }

    public ArrayList<StockItem> getItemByMaterial(double materialName) throws SQLException, IOException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM StockItem WHERE materialName = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, materialName);

            ArrayList<StockItem> items = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                items.add(getOneItem(rs));
            }
            return items;
        }
    }

    protected StockItem getOneItem(ResultSet rs) throws SQLException, IOException
    {

        int id = rs.getInt("id");
        String code = rs.getString("code");
        int materialId = rs.getInt("id");
        String materialName = rs.getString("name");
        double materialDensity = rs.getDouble("density");
        String chargeNo = rs.getString("chargeNo");
        double length = rs.getDouble("length");
        double width = rs.getDouble("width");
        double thickness = rs.getDouble("thickness");
        double stockQuantity = rs.getDouble("stockQuantity");

        return new StockItem(id, code, new Material(materialId, materialDensity, materialName), chargeNo, length, width, thickness, stockQuantity);
    }
}
