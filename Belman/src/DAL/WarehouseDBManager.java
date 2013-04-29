package DAL;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import BE.Warehouse;
import BE.MaterialeType;

import com.microsoft.sqlserver.jdbc.SQLServerException;

/**
 * Denne klasse håndterer data-lagring og -indlæsning i forbindelse med medlems-objekter
 */
public class WarehouseDBManager{

    private Connector connector;
    private static WarehouseDBManager instance;

    private WarehouseDBManager() throws FileNotFoundException, IOException {
        this.connector = Connector.getInstance();

    }

    /**
     * @return Den eneste instans af dette objekt
     * @throws FileNotFoundException Hvis config-filen ikke kan findes
     * @throws IOException Hvis der er en fejl under løsningen af config-filen
     */
    public static WarehouseDBManager getInstance() throws FileNotFoundException, IOException{
        if( instance == null ){
            instance = new WarehouseDBManager();
        }
        return instance;
    }

    public Warehouse allInWarehouse( Warehouse w ) throws SQLServerException, SQLException{
        try( Connection con = connector.getConnection() ){
            String sql = "INSERT INTO Warehouse VALUES(?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement( sql, PreparedStatement.RETURN_GENERATED_KEYS );
            ps.setString( 1, w.Warehouse() );
            ps.setString( 2, m.getLastName() );
            ps.setInt( 3, m.getYearOfBirth() );
            ps.setString( 4, m.getStreetName() );
            ps.setInt( 5, m.getStreetNo() );

            if( m.getType() == MaterialeType.NONE )
                ps.setNull( 10, java.sql.Types.INTEGER );
            else
                ps.setInt( 10, m.getType().ID );

            int affectedRows = ps.executeUpdate();
            if( affectedRows == 0 ){
                throw new SQLException( " " );
            }

            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int id = keys.getInt( 1 );

            return new Warehouse( id, m );
        }
    }

    /**
     * Henter et member baseret på et givent ID.
     *
     * @param warehouseID ID på det ordreID der skal hentes
     * @return Et member-objekt med relevant information, eller null hvis der ikke findes et member med det
     *         givne ID.
     * @throws SQLServerException Hvis der er en fejl i kommunikationen med SQL serveren.
     * @throws SQLException Hvis der er en fejl i SQL Query'et.
     */
    public Warehouse get( int warehouseID ) throws SQLServerException, SQLException{
        try( Connection con = connector.getConnection() ){
            String sql = "SELECT * FROM Warehouse WHERE id = ?";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, warehouseID );

            ResultSet rs = ps.executeQuery();
            if( rs.next() ){
                return getOneMember( rs );
            }
            return null;
        }
    }

    /**
     * @param type typen af medlemmer der skal hentes
     * @return en List<Member> af alle members i databasen af den givne type.
     * @throws SQLServerException Hvis der er en fejl i kommunikationen med SQL serveren.
     * @throws SQLException Hvis der er en fejl i SQL Query'et.
     */
    public List<Warehouse> getByType( MaterialeType type ) throws SQLServerException, SQLException{
        try( Connection con = connector.getConnection() ){
            String sql = "SELECT * FROM Warehouse WHERE memberType = ?";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, type.ID );

            List<Warehouse> typs = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while( rs.next() )
                typs.add( getOneMember( rs ) );
            return typs;
        }
    }

    /**
     * Henter alle members fra databasen
     *
     * @return alle members i databasen i en liste
     * @throws SQLServerException Hvis der er en fejl i kommunikationen med SQL serveren.
     * @throws SQLException Hvis der er en fejl i SQL Query'et.
     */
    public List<Warehouse> getAllMembers() throws SQLServerException, SQLException{
        try( Connection con = connector.getConnection() ){
            String sql = "SELECT * FROM Warehouse";
            PreparedStatement ps = con.prepareStatement( sql );
            ResultSet rs = ps.executeQuery();

            ArrayList<Warehouse> typs = new ArrayList<>();
            while( rs.next() ){
                typs.add( getOneMember( rs ) );
            }
            return typs;
        }
    }
//
//    /**
//     * Opdaterer databasen med værdierne fra objektet m
//     *
//     * @param m det member objekt der skal opdateres i databasen
//     * @throws SQLServerException Hvis der er en fejl i kommunikationen med SQL serveren.
//     * @throws SQLException Hvis der er en fejl i SQL Query'et.
//     */
//    public void updateMember( Warehouse w ) throws SQLServerException, SQLException{
//        try( Connection con = connector.getConnection() ){
//            String sql = "UPDATE Member SET firstName = ?, lastName = ?, yearOfBirth = ?, "
//                         + "streetName = ?, streetNo = ?, zipCode = ?, phoneNumber = ?, email = ?, "
//                         + "DTULicenseNo = ?, memberType = ? WHERE id = ?";
//
//            PreparedStatement ps = con.prepareStatement( sql );
//
//            ps.setString( 1, m.getFirstName() );
//            ps.setString( 2, m.getLastName() );
//            ps.setInt( 3, m.getYearOfBirth() );
//            ps.setString( 4, m.getStreetName() );
//            ps.setInt( 5, m.getStreetNo() );
//            int typeID = m.getType().ID;
//            if( typeID == 0 ) ps.setNull( 10, Types.INTEGER );
//            else ps.setInt( 10, m.getType().ID );
//            ps.setInt( 11, m.getId() );
//
//            int affectedRows = ps.executeUpdate();
//            if( affectedRows == 0 ){
//                throw new SQLException( "Unable to update member" );
//            }
//        }
//    }

//    /**
//     * Fjerner en member fra databasen med et givent id
//     *
//     * @param id ID'et på det member der skal fjernes
//     * @throws SQLServerException Hvis der er en fejl i kommunikationen med SQL serveren.
//     * @throws SQLException Hvis der er en fejl i SQL Query'et.
//     */
//    public void removeMember( int id ) throws SQLServerException, SQLException{
//        try( Connection con = connector.getConnection() ){
//            String sql ="DELETE FROM Payment WHERE memberid = ?;" + "DELETE FROM CourtReservation WHERE memberId = ?;"+"DELETE FROM Login WHERE memberId = ?;" + "DELETE FROM Member WHERE id = ?";
//            PreparedStatement ps = con.prepareStatement( sql );
//            ps.setInt( 1, id );
//            ps.setInt(2, id);
//            ps.setInt(3, id);
//            ps.setInt(4, id);
//
//            int affectedRows = ps.executeUpdate();
//            if( affectedRows == 0 ){
//                throw new SQLException( "Unable to remove Member" );
//            }
//
//        }
    }

    private Warehouse getOneMember( ResultSet rs ) throws SQLException{
        int id = rs.getInt( 1 );
        String firstName = rs.getString( 2 );
        String lastName = rs.getString( 3 );
        int yearOfBirth = rs.getInt( 4 );
        String streetName = rs.getString( 5 );

        int typeID = rs.getInt( 11 );
        MaterialeType type;
        switch( typeID ){
            case 1:
                type = MaterialeType.COACH;
                break;
            case 2:
                type = MaterialeType.RECREATIONAL;
                break;
            case 3:
                type = MaterialeType.STUDENT;
                break;
            case 4:
                type = MaterialeType.ELITE;
                break;
            case 5:
                type = MaterialeType.ADMIN;
                break;
            default:
                type = MaterialeType.NONE;
        }

        return new Warehouse( id,
                           firstName,
                           lastName,
                           yearOfBirth,
                           streetName,
                           DTULicenseNo,
                           type );
    }
}
