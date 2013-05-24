package BE;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */
public class Operator
{

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private Sleeve sleeve;
    private int quantityCut;

    /**
     * Den overordnede konstruktør til Operator
     *
     * @param id
     * @param username
     * @param firstName
     * @param lastName
     * @param sleeve
     * @param quantityCut
     */
    public Operator(int id, String username, String firstName, String lastName, Sleeve sleeve, int quantityCut)
    {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sleeve = sleeve;
        this.quantityCut = quantityCut;
    }

    /**
     * @returnere username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username sætter username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @returnere firstName
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName sætter firstName
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @returnere lastName
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName sætter lastName
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * @returnere id
     */
    public int getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return username;
    }

    /**
     * @returnere et Sleeve objekt
     */
    public Sleeve getSleeve()
    {
        return sleeve;
    }
 
    /**
     * @return the quantityCut
     */
    public int getQuantityCut()
    {
        return quantityCut;
    }

    /**
     * @param quantityCut the quantityCut to set
     */
    public void setQuantityCut(int quantityCut)
    {
        this.quantityCut = quantityCut;
    }
}
