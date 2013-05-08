/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Operator;
import DAL.OperatorDBManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class OperatorManager
{
    private OperatorDBManager accessor;
    private static OperatorManager instance;

    public OperatorManager() throws IOException
    {
        accessor = OperatorDBManager.getInstance();
    }

    public static OperatorManager getInstance() throws FileNotFoundException, IOException
    {
        if (instance == null)
        {
            instance = new OperatorManager();
        }
        return instance;
    }

    public ArrayList<Operator> getAllOperators() throws SQLException
    {
        return accessor.getAllOperators();
    }
}
