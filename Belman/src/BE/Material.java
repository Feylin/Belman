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
    private double name;
    private String quality;
    
    public Material(double name, String quality)
    {
        this.name = name;
        this.quality = quality;       
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
