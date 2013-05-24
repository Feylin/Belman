package BE;

/**
 * BE.ErrorsOccured klasse 
 * @author Rashid, Klaus, Mak, Daniel
 */
public class ErrorsOccured
{

    private int ProductionOrder;
    private String info;

    
    /**
     * Den overordnede konstruktør til ErrorsOccured klassen
     * @param ProductionOrder
     * @param info
     */
    public ErrorsOccured(int ProductionOrder, String info)
    {
        this.ProductionOrder = ProductionOrder;
        this.info = info;
    }

    /**
     * @param ProductionOrder sætter en ny productionOrder
     */
    public void setProductionOrder(int ProductionOrder)
    {
        this.ProductionOrder = ProductionOrder;
    }

    /**
     * @param info sætter ny info
     */
    public void setInfo(String info)
    {
        this.info = info;
    }

    /**
     * @returnere productionOrder
     */
    public int getProductionOrder()
    {
        return ProductionOrder;
    }

    /**
     * @returnere info
     */
    public String getInfo()
    {
        return info;
    }
}
