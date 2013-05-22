/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.ErrorsOccured;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Rashid
 */
public class ErrorsOccuredDBManager {
    
private Connector connector;
    private static ErrorsOccuredDBManager instance;

    public ErrorsOccuredDBManager() throws IOException
    {
        connector = Connector.getInstance();
    }

    public static ErrorsOccuredDBManager getInstance() throws IOException
    {
        if (instance == null)
        {
            instance = new ErrorsOccuredDBManager();
        }
        return instance;
    }

    public ErrorsOccured add(ErrorsOccured errorsOccured) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "INSERT INTO ErrorsOccured(ProductionOrder, info) VALUES (?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, errorsOccured.getProductionOrder());
            ps.setString(2, errorsOccured.getInfo());
            
            

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new SQLException(" ");
            }
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);

        }
        return null;
    }
    
}
