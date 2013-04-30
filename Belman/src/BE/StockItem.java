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
    private final int ID;
    private String Code;
    private int MaterialID;
    private double MaterialName;
    private double MaterialDensity;
    private String ChargeNr;
    private int Length;
    private int Width;
    private double Thickness;
    private double StockQuantity;
   
    public StockItem(int ID, String Code,int MaterialID, double MaterialName, double MaterialDensity, String ChargeNr, int Length, int Width, double Thickness, double StockQuantity)
    {
        this.ID = ID;
        this.Code = Code;
        this.MaterialID = MaterialID;
        this.MaterialName = MaterialName;
        this.MaterialDensity = MaterialDensity;
        this.ChargeNr = ChargeNr;
        this.Length = Length;
        this.Width = Width;
        this.Thickness = Thickness;
        this.StockQuantity = StockQuantity;        
    }

    /**
     * @return the ID
     */
    public int getID()
    {
        return ID;
    }

    /**
     * @return the Code
     */
    public String getCode()
    {
        return Code;
    }

    /**
     * @param Code the Code to set
     */
    public void setCode(String Code)
    {
        this.Code = Code;
    }

    /**
     * @return the MaterialID
     */
    public int getMaterialID()
    {
        return MaterialID;
    }

    /**
     * @param MaterialID the MaterialID to set
     */
    public void setMaterialID(int MaterialID)
    {
        this.MaterialID = MaterialID;
    }

    /**
     * @return the MaterialName
     */
    public double getMaterialName()
    {
        return MaterialName;
    }

    /**
     * @param MaterialName the MaterialName to set
     */
    public void setMaterialName(double MaterialName)
    {
        this.MaterialName = MaterialName;
    }

    /**
     * @return the MaterialDensity
     */
    public double getMaterialDensity()
    {
        return MaterialDensity;
    }

    /**
     * @param MaterialDensity the MaterialDensity to set
     */
    public void setMaterialDensity(double MaterialDensity)
    {
        this.MaterialDensity = MaterialDensity;
    }

    /**
     * @return the ChargeNr
     */
    public String getChargeNr()
    {
        return ChargeNr;
    }

    /**
     * @param ChargeNr the ChargeNr to set
     */
    public void setChargeNr(String ChargeNr)
    {
        this.ChargeNr = ChargeNr;
    }

    /**
     * @return the Length
     */
    public int getLength()
    {
        return Length;
    }

    /**
     * @param Length the Length to set
     */
    public void setLength(int Length)
    {
        this.Length = Length;
    }

    /**
     * @return the Width
     */
    public int getWidth()
    {
        return Width;
    }

    /**
     * @param Width the Width to set
     */
    public void setWidth(int Width)
    {
        this.Width = Width;
    }

    /**
     * @return the Thickness
     */
    public double getThickness()
    {
        return Thickness;
    }

    /**
     * @param Thickness the Thickness to set
     */
    public void setThickness(double Thickness)
    {
        this.Thickness = Thickness;
    }

    /**
     * @return the StockQuantity
     */
    public double getStockQuantity()
    {
        return StockQuantity;
    }

    /**
     * @param StockQuantity the StockQuantity to set
     */
    public void setStockQuantity(double StockQuantity)
    {
        this.StockQuantity = StockQuantity;
    }
    
}
