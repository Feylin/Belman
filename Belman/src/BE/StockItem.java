/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */
public class StockItem
{

    private final int id;
    private String chargeNo;
    private double length;
    private double stockQuantity;
    private int coilTypeId;
    private int sleeveId;

    public StockItem(int id, String chargeNo, double length, double stockQuantity, int coilTypeId, int sleeveId)
    {
        this.id = id;
        this.chargeNo = chargeNo;
        this.length = length;
        this.stockQuantity = stockQuantity;
        this.coilTypeId = coilTypeId;
        this.sleeveId = sleeveId;

    }

    public StockItem(int id, StockItem item)
    {
        this(id,
                item.getChargeNo(),
                item.getLength(),
                item.getStockQuantity(),
                item.getCoilTypeId(),
                item.getSleeveId());
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @return the chargeNo
     */
    public String getChargeNo()
    {
        return chargeNo;
    }

    /**
     * @param chargeNo the chargeNo to set
     */
    public void setChargeNo(String chargeNo)
    {
        this.chargeNo = chargeNo;
    }

    /**
     * @return the length
     */
    public double getLength()
    {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(double length)
    {
        this.length = length;
    }

    /**
     * @return the stockQuantity
     */
    public double getStockQuantity()
    {
        return stockQuantity;
    }

    /**
     * @param stockQuantity the stockQuantity to set
     */
    public void setStockQuantity(double stockQuantity)
    {
        this.stockQuantity = stockQuantity;
    }

    /**
     * @return the coilTypeId
     */
    public int getCoilTypeId()
    {
        return coilTypeId;
    }

    /**
     * @param coilTypeId the coilTypeId to set
     */
    public void setCoilTypeId(int coilTypeId)
    {
        this.coilTypeId = coilTypeId;
    }

    /**
     * @return the sleeveId
     */
    public int getSleeveId()
    {
        return sleeveId;
    }

    /**
     * @param sleeveId the sleeveId to set
     */
    public void setSleeveId(int sleeveId)
    {
        this.sleeveId = sleeveId;
    }
}
