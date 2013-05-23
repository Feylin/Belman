/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Material;
import BE.Operator;
import BE.Sleeve;
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
    private static final String OPERATORID = "OperatorId";
    private static final String USERNAME = "username";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String SLEEVEID = "id";
    private static final String QUANTITYCUT = "quantityCut";
    
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
                int operatorId = rs.getInt("operatorId");
                String username = rs.getString("username");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int quantityCut = rs.getInt("quantityCut");
                
                Operator op = new Operator(operatorId, username, firstName, lastName, null, quantityCut);
                operators.add(op);               
            }            
            return operators;
        }
    }   
    
    public void updateHasCut(Operator op, int hasCut) throws SQLException
    {
        try(Connection con = connector.getConnection())
        {
            String sql = "UPDATE Operator SET Operator.quantityCut = ? WHERE Operator.operatorId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, hasCut);
            ps.setInt(2, op.getId());  
            
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                 throw new SQLException( "Unable to update Operator" );
            }
        }
    }

    public Operator get(String username) throws SQLServerException, SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM Operator, Sleeve WHERE username = ?";
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
        int id = rs.getInt(OPERATORID);
        String username = rs.getString(USERNAME);
        String firstName = rs.getString(FIRST_NAME);
        String lastName = rs.getString(LAST_NAME);
        int sleeveId = rs.getInt(SLEEVEID);
        int quantityCut = rs.getInt(QUANTITYCUT);
        
        return new Operator(id, username, firstName, lastName, new Sleeve(sleeveId, null, null, -1, -1, -1, -1, null), quantityCut);
    }
}
