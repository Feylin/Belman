/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */
public class Material
{

    private int id;
    private double density;
    private String name;

    /**
     * Den overordnede konstruktør til Material
     *
     * @param id
     * @param density
     * @param name
     */
    public Material(int id, double density, String name)
    {
        this.id = id;
        this.density = density;
        this.name = name;
    }

    public Material(double density, String name)
    {
        this(-1, density, name);
    }

    public Material(String name)
    {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the density
     */
    public double getDensity()
    {
        return density;
    }

    /**
     * @param density the density to set
     */
    public void setDensity(double density)
    {
        this.density = density;
    }

    /**
     * @returnerne materialets id.
     */
    public int getId()
    {
        return id;
    }
    
    @Override
    public String toString()
    {
        return String.format(" %-5s", name);
    }
}
