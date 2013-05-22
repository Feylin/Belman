/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author Administrator
 */
public class Operator
{
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private Sleeve sleeve;
    private int quantityCut;

    public Operator(int id, String username, String firstName, String lastName, Sleeve sleeve, int quantityCut)
    {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sleeve = sleeve;
        this.quantityCut = quantityCut;
    }

    public Operator(String username, String firstName, String lastName, Sleeve sleeve, int quantityCut)
    {
        this(-1, username, firstName, lastName, sleeve, quantityCut);
    }

    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return the firstName
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * @return the id
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
     * @return the sleeve
     */
    public Sleeve getSleeve()
    {
        return sleeve;
    }

    /**
     * @param sleeve the sleeve to set
     */
    public void setSleeve(Sleeve sleeve)
    {
        this.sleeve = sleeve;
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
