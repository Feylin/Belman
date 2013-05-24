/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */
public class ErrorsOccured 
{
    private int ProductionOrder;
  private String info;
  
  public ErrorsOccured(int ProductionOrder, String info)
  {
      this.ProductionOrder = ProductionOrder;
      this.info = info;
  }

    /**
     * @param ProductionOrder the ProductionOrder to set
     */
    public void setProductionOrder(int ProductionOrder) {
        this.ProductionOrder = ProductionOrder;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }
    
    /**
     * @return the ProductionOrder
     */
    public int getProductionOrder() {
        return ProductionOrder;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }
      
}
