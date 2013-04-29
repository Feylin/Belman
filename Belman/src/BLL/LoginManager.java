package BLL;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import dal.LoginAccess;
import exceptions.TBFileNotFoundException;
import exceptions.TBIOException;
import exceptions.TBSQLException;
import exceptions.TieBreakException;

public class LoginManager{

    private LoginAccess accessor;
    private static LoginManager instance;

    private LoginManager() throws FileNotFoundException, IOException {
        accessor = LoginAccess.getInstance();
    }

    public static LoginManager getInstance() throws TBFileNotFoundException, TBIOException{
        try{
            if( instance == null ) instance = new LoginManager();
            return instance;
        }
        catch( FileNotFoundException e ){
            throw new TBFileNotFoundException(e);
        }
        catch( IOException e ){
            throw new TBIOException(e);
        }
    }

    public void addLogin( int memberID, String password ) throws TieBreakException{
        try{
            String encryptedPass = encryptPass( memberID, password );
            accessor.addLogin( memberID, encryptedPass );
        }
        catch( SQLException e ){
            throw new TBSQLException(e);
        }
    }

    public void updateLogin( int memberID, String newPass ) throws TieBreakException{
        try{
            String encryptedPass = encryptPass (memberID, newPass);
            accessor.updateLogin( memberID, encryptedPass );
        }
        catch( SQLException e ){
            throw new TBSQLException(e);
        }
    }

    public boolean checkLogin( int memberID, String password ) throws TieBreakException{
        try{
            String encryptedPass = encryptPass( memberID, password );
            return accessor.checkLogin( memberID, encryptedPass );
        }
        catch( SQLException e ){
            throw new TBSQLException(e);
        }
    }

    public String encryptPass( int memberID, String password ){
        StringBuilder sb = new StringBuilder();
        char[] passChars = password.toCharArray();
        int counter = 1;
        for( Character c : passChars ){
            sb.append( "" + (c.hashCode() * (memberID / counter)) );
            counter++;
        }
        return sb.toString();
    }
}
