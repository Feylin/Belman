package BLL;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Observable;

import BE.Warehouse;
import BE.MaterialeType;
import BE.OrdreInfo;
import DAL.WarehouseDBManager;


/**
 * Denne klasse håndterer logikken i forbindelse med medlems-objekter
 */
public class WarehouseManager extends Observable{

    private WarehouseManager accessor;

    private static WarehouseManager instance;

    private WarehouseManager() throws TieBreakException {
        try{
            accessor = WarehouseManager.getInstance();
        }
        catch( FileNotFoundException e ){
            throw new TBFileNotFoundException( e );
        }
        catch( IOException e ){
            throw new TBIOException( e );
        }

    }

    /**
     * @return Dene eneste instans af dete objekt
     * @throws FileNotFoundException Hvis config-filen ikke kan findes
     * @throws IOException Hvis der er en fejl under løsningen af config-filen
     */
    public static WarehouseManager getInstance() throws TieBreakException{
        if( instance == null )
            instance = new WarehouseManager();
        return instance;
    }

    /**
     * Tilføjer et medlem til databasen
     *
     * @param member Medlemmet, der skal tilføjes
     * @return Et nyt medlems-objekt med det tilsvarende ID fra databasen
     * @throws TBSQLException
     */
    public WarehouseManager add( WarehouseManager member ) throws TBSQLException{
        try{
            WarehouseManager w = accessor.addMember( member );
            setChanged();
            notifyObservers();
            return w;
        }
        catch( SQLException e ){
            throw new TBSQLException( e );
        }
    }

    /**
     * Henter et medlem fra databasen, baseret pø det givne ID
     *
     * @param memberID ID'et pø det medlemsobjekt der skal indhentes
     * @return En java-objekt-reprøsentation af medlemmet i databasen
     * @throws TBSQLException
     */
    public WarehouseManager get( int memberID ) throws TBSQLException{
        try{
            return accessor.get( memberID );
        }
        catch( SQLException e ){
            throw new TBSQLException( e );
        }
    }

    /**
     * @param type typen af members der skal hentes
     * @return alle members af den givne type i databasen
     * @throws TBSQLException
     */
    public List<WarehouseManager> getByType( MemberType type ) throws TBSQLException{
        try{
            return accessor.getByType( type );
        }
        catch( SQLException e ){
            throw new TBSQLException( e );
        }
    }

    /**
     * @return Alle medlemmerne i databasen
     */
    public List<WarehouseManager> getAll() throws TBSQLException{
        try{
            return accessor.getAllMembers();
        }
        catch( SQLException e ){
            throw new TBSQLException( e );
        }
    }

    /**
     * Opdaterer et medlem i databasen
     *
     * @param member Medlemsobjektet for det medlem der skal opdateres. Dete objekt bør indeholde de nye
     *            informationer (navn, adresse, telefonnummer m.v.)
     * @throws TBSQLException
     */
    public void update( WarehouseManager member ) throws TBSQLException{
        try{
            accessor.updateMember( member );
            setChanged();
            notifyObservers();
        }
        catch( SQLException e ){
            throw new TBSQLException( e );
        }
    }

    /**
     * Fjerner et medlem fra databasen
     *
     * @param memberId ID'et, i databasen, pø det medlem som skal fjernes
     * @throws TBSQLException
     */
    public void remove( int memberId ) throws TBSQLException{
        try{
            accessor.removeMember( memberId );
            setChanged();
            notifyObservers();
        }
        catch( SQLException e ){
            throw new TBSQLException( e );
        }
    }

}
