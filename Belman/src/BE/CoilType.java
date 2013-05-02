/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BE;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */
public class CoilType 
{
    private final int id;
    private String code;
    private double width;
    private double thickness;
    private int materialId;
    
    /**
     * Den overordnede konstruktør til CoilType.
     * @param id
     * @param code
     * @param width
     * @param thickness
     * @param materialId
     */
    public CoilType(int id, String code, double width, double thickness, int materialId)
    {
        this.id = id;
        this.code = code;
        this.width = width;
        this.thickness = thickness;
        this.materialId = materialId;
        
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @return the code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * @return the width
     */
    public double getWidth()
    {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(double width)
    {
        this.width = width;
    }

    /**
     * @return the thickness
     */
    public double getThickness()
    {
        return thickness;
    }

    /**
     * @param thickness the thickness to set
     */
    public void setThickness(double thickness)
    {
        this.thickness = thickness;
    }

    /**
     * @return the materialId
     */
    public int getMaterialId()
    {
        return materialId;
    }

    /**
     * @param materialId the materialId to set
     */
    public void setMaterialId(int materialId)
    {
        this.materialId = materialId;
    }
}
