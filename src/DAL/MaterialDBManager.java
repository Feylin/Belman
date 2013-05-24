/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DAL;

import BE.Material;
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
public class MaterialDBManager 
{
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DENSITY = "density";
    
    private Connector connector;
    private static MaterialDBManager instance;
    
    public MaterialDBManager() throws IOException
    {
        connector = Connector.getInstance();
    }

    public static MaterialDBManager getInstance() throws IOException
    {
        if (instance == null)
        {
            instance = new MaterialDBManager();
        }
        return instance;
    }
    
    public ArrayList<Material> getAllMaterials() throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM Material";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<Material> materials = new ArrayList<>();
            while (rs.next())
            {
                materials.add(getOneMaterial(rs));
            }
            return materials;
        }
    }
    
    protected Material getOneMaterial(ResultSet rs) throws SQLException
    {
        int id = rs.getInt(ID);
        String name = rs.getString(NAME);
        double density = rs.getDouble(DENSITY);
        
        return new Material(id, density, name);
    }
    
}
