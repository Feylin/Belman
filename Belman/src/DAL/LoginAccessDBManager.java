package DAL;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class LoginAccessDBManager
{

    private Connector connector;
    private static LoginAccessDBManager instance;

    private LoginAccessDBManager() throws FileNotFoundException, IOException
    {
        connector = Connector.getInstance();
    }

    public static LoginAccessDBManager getInstance() throws FileNotFoundException, IOException
    {
        if (instance == null)
        {
            instance = new LoginAccessDBManager();
        }
        return instance;
    }

    public void addLogin(int memberID, String encryptedPass) throws SQLServerException, SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "INSERT INTO Login VALUES(?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, memberID);
            ps.setString(2, encryptedPass);

            int affectedRows = ps.executeUpdate();
            if (affectedRows <= 0)
            {
                throw new SQLException();
            }
        }
    }

    public void updateLogin(int memberID, String newPass) throws SQLServerException, SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "UPDATE Login SET password = ? WHERE memberId = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, newPass);
            ps.setInt(2, memberID);

            int affectedRows = ps.executeUpdate();
            if (affectedRows <= 0)
            {
                throw new SQLException();
            }
        }
    }

    public boolean checkLogin(int memberID, String hash) throws SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT * FROM Login WHERE memberId = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, memberID);
            ps.setString(2, hash);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    @Deprecated
    public int getSalt(int memberID) throws SQLServerException, SQLException
    {
        try (Connection con = connector.getConnection())
        {
            String sql = "SELECT Salt FROM Login WHERE memberId = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, memberID);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getInt(1);
            }
            throw new SQLException("The specified member does not have a login!");
        }
    }
}
