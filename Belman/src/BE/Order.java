/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.util.GregorianCalendar;

/**
 *
 * @author Daniel
 */
public class Order
{
    private final int orderID;
    private String order;
    private String prodOrder;
    private GregorianCalendar dueDate;
    private int quantity;
    private int materialID;
    private double thickness;
    private double width;
    private double circumference;
    
    public Order(int sOrderID,String sOrder, String prodOrder, GregorianCalendar dueDate, int quantity, int materialID, double thickness, double width, double circumference)
    {
        this.orderID = sOrderID;
        this.order = sOrder;
        this.prodOrder = prodOrder;
        this.dueDate = dueDate;
        this.quantity = quantity;
        this.materialID = materialID;
        this.thickness = thickness;
        this.width = width;
        this.circumference = circumference;
        
    }

    /**
     * @return the orderID
     */
    public int getOrderID()
    {
        return orderID;
    }

    /**
     * @return the order
     */
    public String getOrder()
    {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(String order)
    {
        this.order = order;
    }

    /**
     * @return the prodOrder
     */
    public String getProdOrder()
    {
        return prodOrder;
    }

    /**
     * @param prodOrder the prodOrder to set
     */
    public void setProdOrder(String prodOrder)
    {
        this.prodOrder = prodOrder;
    }

    /**
     * @return the dueDate
     */
    public GregorianCalendar getDueDate()
    {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(GregorianCalendar dueDate)
    {
        this.dueDate = dueDate;
    }

    /**
     * @return the quantity
     */
    public int getQuantity()
    {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    /**
     * @return the materialID
     */
    public int getMaterialID()
    {
        return materialID;
    }

    /**
     * @param materialID the materialID to set
     */
    public void setMaterialID(int materialID)
    {
        this.materialID = materialID;
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
     * @return the circumference
     */
    public double getCircumference()
    {
        return circumference;
    }

    /**
     * @param circumference the circumference to set
     */
    public void setCircumference(double circumference)
    {
        this.circumference = circumference;
    }
    
}
