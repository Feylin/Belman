/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import DAL.LoginAccessDBManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Rashid
 */
public class LoginManager
{

    private LoginAccessDBManager access;
    private static LoginManager instance;

    private LoginManager() throws FileNotFoundException, IOException
    {
        access = LoginAccessDBManager.getInstance();
    }

    public static LoginManager getInstance() throws FileNotFoundException, IOException
    {
        if (instance == null)
        {
            instance = new LoginManager();
        }
        return instance;
    }

    public void addLogin(String username, String password) throws SQLServerException, SQLException
    {
        String encryptPass = encryptPass(username, password);
        access.addLogin(username, encryptPass);
    }

    public void updateLogin(String username, String newPassword) throws SQLServerException, SQLException
    {
        String encryptPass = encryptPass(username, newPassword);
        access.updateLogin(username, encryptPass);
    }

    public boolean checkLogin(String username, String password) throws SQLException
    {
        String encryptPass = encryptPass(username, password);
        return access.checkLogin(username, encryptPass);
    }

    public String encryptPass(String username, String password)
    {
        StringBuilder stringbuilder = new StringBuilder();
        char[] passwordChars = password.toCharArray();
        int counter = 1;
        for (Character c : passwordChars)
        {
            stringbuilder.append("").append(c.hashCode() * (username.length() / counter));
            counter++;
        }
        return stringbuilder.toString();
    }
}
