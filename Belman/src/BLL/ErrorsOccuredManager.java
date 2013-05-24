/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Order;
import DAL.ErrorsOccuredDBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Observable;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */
public class ErrorsOccuredManager extends Observable {
        
    private ErrorsOccuredDBManager accessor;
    private static ErrorsOccuredManager instance;
    
    private ErrorsOccuredManager() throws IOException
    {
        accessor = ErrorsOccuredDBManager.getInstance();
    }
    
    public static ErrorsOccuredManager getInstance() throws IOException{
        if( instance == null ) instance = new ErrorsOccuredManager();
        return instance;
    }
    
//    public void add (String message) throws SQLException
//    {
//        accessor.add(message);
//    }
}

