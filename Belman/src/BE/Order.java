/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */
public class Order
{
    private final int sOrderId;
    private final int orderId;
    private String orderName;
    private GregorianCalendar dueDate;
    private int quantity;
    private double width;
    private String status;
    private SalesOrder salesOrder;
    private Sleeve sleeve;

    /**
     * Overodnede konstruktør for Order.
     *
     * @param sOrderId
     * @param orderId
     * @param orderName
     * @param dueDate
     * @param quantity
     * @param thickness
     * @param width
     * @param status
     */
    public Order(int sOrderId, int orderId, String orderName, GregorianCalendar dueDate, int quantity, double width, String status, SalesOrder salesOrder, Sleeve sleeve)
    {
        this.sOrderId = sOrderId;
        this.orderId = orderId;
        this.orderName = orderName;
        this.dueDate = dueDate;
        this.quantity = quantity;
        this.width = width;
        this.status = status;
        this.salesOrder = salesOrder;
        this.sleeve = sleeve;
    }

    public Order(int orderId, Order o)
    {
        this(orderId,
                o.getOrderId(),
                o.getOrderName(),
                o.getDueDate(),
                o.getQuantity(),
                o.getWidth(),
                o.getStatus(),
                o.getSalesOrder(),
                o.getSleeve());
    }

    public String printDate(GregorianCalendar gc)
    {
        return String.format("%02d-%02d-%04d",
                gc.get(Calendar.DAY_OF_MONTH),
                gc.get(Calendar.MONTH),
                gc.get(Calendar.YEAR));
    }

    /**
     * @return the sOrderId
     */
    public int getsOrderId()
    {
        return sOrderId;
    }

    /**
     * @return the orderId
     */
    public int getOrderId()
    {
        return orderId;
    }

    /**
     * @return the orderName
     */
    public String getOrderName()
    {
        return orderName;
    }

    /**
     * @param orderName the orderName to set
     */
    public void setOrderName(String orderName)
    {
        this.orderName = orderName;
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
     * @return the status
     */
    public String getStatus()
    {
        return status;
    }
    
    public SalesOrder getSalesOrder()
    {
        return salesOrder;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status)
    {
        this.status = status;
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
    
}
