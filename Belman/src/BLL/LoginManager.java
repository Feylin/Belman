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

    public void addLogin(int operatorId, String password) throws SQLServerException, SQLException
    {
        String encryptPass = encryptPass(operatorId, password);
        access.addLogin(operatorId, encryptPass);
    }

    public void updateLogin(int operatorId, String newPassword) throws SQLServerException, SQLException
    {
        String encryptPass = encryptPass(operatorId, newPassword);
        access.updateLogin(operatorId, encryptPass);
    }

    public boolean checkLogin(int operatorId, String password) throws SQLException
    {
        String encryptPass = encryptPass(operatorId, password);
        return access.checkLogin(operatorId, encryptPass);
    }

    public String encryptPass(int operatorId, String password)
    {
        StringBuilder stringbuilder = new StringBuilder();
        char[] passwordChars = password.toCharArray();
        int counter = 1;
        for (Character c : passwordChars)
        {
            stringbuilder.append("").append(c.hashCode() * (operatorId / counter));
            counter++;
        }
        return stringbuilder.toString();
    }
}
