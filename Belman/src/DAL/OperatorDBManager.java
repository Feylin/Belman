/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Material;
import BE.Operator;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class OperatorDBManager
{
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    
    private Connector connector;
    private static OperatorDBManager instance;
    
    public OperatorDBManager() throws IOException
    {
        connector = Connector.getInstance();
    }

    public static OperatorDBManager getInstance() throws IOException
    {
        if (instance == null)
        {
            instance = new OperatorDBManager();
        }
        return instance;
    }
    
    public ArrayList<Operator> getAllOperators() throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM Operator";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<Operator> operators = new ArrayList<>();
            while (rs.next())
            {
                operators.add(getOneOperator(rs));
            }
            return operators;
        }
    }   

    public Operator get(String username) throws SQLServerException, SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM Operator WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getOneOperator(rs);
            }
            return null;
        }
    }
    
    private Operator getOneOperator(ResultSet rs) throws SQLException
    {
        int id = rs.getInt(ID);
        String username = rs.getString(USERNAME);
        String firstName = rs.getString(FIRST_NAME);
        String lastName = rs.getString(LAST_NAME);
        
        return new Operator(id, username, firstName, lastName);
    }
}
