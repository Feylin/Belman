/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author Daniel
 */
public class Material
{
    private double density;
    private String name; 
    private int id;
    
    /**
     *
     * @param id
     * @param density
     * @param name
     */
    public Material(int id, double density, String name )
    {
        this.id = id;
        this.density = density;
        this.name = name;        
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
     * @return the id
     */
    public int getId()
    {
        return id;
    }


}
