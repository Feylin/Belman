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
    private double name;
    private String quality;
    private int id;
    
    /**
     *
     * @param id
     * @param density
     * @param name
     */
    public Material(int id, double density, double name )
    {
        this.id = id;
        this.density = density;
        this.name = name;
        
    }
    
    public Material(double name, String quality)
    {
        this.name = name;
        this.quality = quality;       
    }

    public Material(double name)
    {
        this.name = name;
    }

    /**
     * @return the name
     */
    public double getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(double name)
    {
        this.name = name;
    }

    /**
     * @return the quality
     */
    public String getQuality()
    {
        return quality;
    }

    /**
     * @param quality the quality to set
     */
    public void setQuality(String quality)
    {
        this.quality = quality;
    }


}
