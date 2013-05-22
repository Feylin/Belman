/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.ErrorsOccured;
import BE.Order;
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
    private String message;

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

    public ErrorsOccured add(Order o, String message) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "INSERT INTO ErrorsOccured(pOrder, info) VALUES (?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, o.getOrderId());
            ps.setString(2, message);
            
            

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
