package BLL;

import BE.Operator;
import DAL.OperatorDBManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Buisness Logic Layer OperatorManager klassen.
 * @author Daniel, Klaus, Mak, Rashid
 */
public class OperatorManager extends Observable
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
    
    public Operator get(String username) throws SQLException
    {
        return accessor.get(username);
    }
    
    public void updateHasCut(Operator op, int hasCut) throws SQLException
    {
         accessor.updateHasCut(op, hasCut);
         setChanged();
         notifyObservers();
    }
}
