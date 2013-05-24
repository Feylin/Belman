/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BE;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */
public class SalesOrder 
{
    private final int sOrderId;
    private String custName;
    private String email;
    private int phone;
    
    public SalesOrder(int sOrderId, String custName, String email, int phone)
    {
        this.sOrderId = sOrderId;
        this.custName = custName;
        this.email = email;
        this.phone = phone;        
    }

    /**
     * @return the sOrderId
     */
    public int getsOrderId()
    {
        return sOrderId;
    }

    /**
     * @return the sOrder
     */
    public String getsOrder()
    {
        return getCustName();
    }

    /**
     * @param sOrder the sOrder to set
     */
    public void setsOrder(String sOrder)
    {
        this.setCustName(sOrder);
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public int getPhone()
    {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(int phone)
    {
        this.phone = phone;
    }
    
    @Override
    public String toString()
    {
        return String.format("%-5d %-5s %-5s %-5d", sOrderId, getCustName(), email, phone);
    }

    /**
     * @return the custName
     */
    public String getCustName()
    {
        return custName;
    }

    /**
     * @param custName the custName to set
     */
    public void setCustName(String custName)
    {
        this.custName = custName;
    }
}
