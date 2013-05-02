/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author Daniel
 */
public class StockItem
{

    private int id;
    private String code;
    private int materialId;
    private String materialName;
    private double materialDensity;
    private Material material;
    private String chargeNr;
    private double length;
    private double width;
    private double thickness;
    private double stockQuantity;

    public StockItem(int Id, String Code, int materialId, String materialName, double materialDensity, String chargeNr, double length, double width, double thickness, double stockQuantity)
    {
        this.id = Id;
        this.code = Code;
        this.materialId = materialId;
        this.materialName = materialName;
        this.materialDensity = materialDensity;
        this.chargeNr = chargeNr;
        this.length = length;
        this.width = width;
        this.thickness = thickness;
        this.stockQuantity = stockQuantity;
    }

    public StockItem(int Id, String Code, Material material, String chargeNr, double length, double width, double thickness, double stockQuantity)
    {
        this.id = Id;
        this.code = Code;
        this.material = material;      
        this.chargeNr = chargeNr;
        this.length = length;
        this.width = width;
        this.thickness = thickness;
        this.stockQuantity = stockQuantity;

    }

    public StockItem(int id, StockItem item)
    {
        this(id,
                item.getCode(),
                item.getMaterialId(),
                item.getMaterialName(),
                item.getMaterialDensity(),
                item.getChargeNr(),
                item.getLength(),
                item.getWidth(),
                item.getThickness(),
                item.getStockQuantity());
    }

    /**
     * @return the ID
     */
    public int getID()
    {
        return id;
    }

    /**
     * @return the Code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code the Code to set
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * @return the MaterialID
     */
    public int getMaterialId()
    {
        return material.getId();
    }

    /**
     * @param materialId the MaterialID to set
     */
    public void setMaterialId(int materialId)
    {
        this.materialId = materialId;
    }

    /**
     * @return the MaterialName
     */
    public String getMaterialName()
    {
        return material.getName();
    }

    /**
     * @param materialName the MaterialName to set
     */
    public void setMaterialName(String materialName)
    {
        this.materialName = materialName;
    }

    /**
     * @return the MaterialDensity
     */
    public double getMaterialDensity()
    {
        return material.getDensity();
    }

    /**
     * @param materialDensity the MaterialDensity to set
     */
    public void setMaterialDensity(double materialDensity)
    {
        this.materialDensity = materialDensity;
    }

    /**
     * @return the ChargeNr
     */
    public String getChargeNr()
    {
        return chargeNr;
    }

    /**
     * @param chargeNr the ChargeNr to set
     */
    public void setChargeNr(String chargeNr)
    {
        this.chargeNr = chargeNr;
    }

    /**
     * @return the Length
     */
    public double getLength()
    {
        return length;
    }

    /**
     * @param length the Length to set
     */
    public void setLength(double length)
    {
        this.length = length;
    }

    /**
     * @return the Width
     */
    public double getWidth()
    {
        return width;
    }

    /**
     * @param width the Width to set
     */
    public void setWidth(double width)
    {
        this.width = width;
    }

    /**
     * @return the Thickness
     */
    public double getThickness()
    {
        return thickness;
    }

    /**
     * @param thickness the Thickness to set
     */
    public void setThickness(double thickness)
    {
        this.thickness = thickness;
    }

    /**
     * @return the StockQuantity
     */
    public double getStockQuantity()
    {
        return stockQuantity;
    }

    /**
     * @param stockQuantity the StockQuantity to set
     */
    public void setStockQuantity(double stockQuantity)
    {
        this.stockQuantity = stockQuantity;
    }
}
