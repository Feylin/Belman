/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.util.GregorianCalendar;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */
public class Sleeve {

    private final int id;
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private double thickness;
    private double circumference;
    private int materialId;
    private int pOrderId;
    private Material material;

    /**
     * Den overordnede konstruktør til Sleeve.
     *
     * @param id
     * @param startTime
     * @param endTime
     * @param thickness
     * @param circumference
     * @param materialId
     * @param pOrderId
     */
    public Sleeve(int id, GregorianCalendar startTime, GregorianCalendar endTime, double thickness, double circumference, int materialId, int pOrderId, Material material)
    {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.thickness = thickness;
        this.circumference = circumference;
        this.materialId = materialId;
        this.pOrderId = pOrderId;
        this.material = material;

    }

    public Sleeve(int id, GregorianCalendar startTime, GregorianCalendar endTime, double thickness, double circumference, int materialId, int pOrderId)
    {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.thickness = thickness;
        this.circumference = circumference;
        this.materialId = materialId;
        this.pOrderId = pOrderId; 
   
    }
    
    public Sleeve(int id, Sleeve s) {
        this(id,
                s.getStartTime(),
                s.getEndTime(),
                s.getThickness(),
                s.getCircumference(),
                s.getMaterialId(),
                s.getpOrderId(),
                s.getMaterial());
    }

    public Sleeve(GregorianCalendar startTime, GregorianCalendar endTime, double thickness, double circumference, int materialId, int pOrderId, Material material)
    {
        this(-1, null, null, -1, circumference, -1, -1,material);
    }

//    public Sleeve(GregorianCalendar startTime, GregorianCalendar endTime, double thickness, double circumference, int materialId, int pOrderId, Material material)
//    {
//        this(-1, -1, -1, -1, circumference, -1, -1, material);
//    }
//    public Sleeve(GregorianCalendar startTime, GregorianCalendar endTime, double thickness, double circumference, int materialId, int pOrderId, Material material)
//    {        
//        this(-1, null, null, -1, circumference, -1, -1, material);
//    }
//    public Sleeve(int id, GregorianCalendar startTime, GregorianCalendar endTime, double thickness, double circumference, int materialId, int pOrderId, Material material)
//    {
//        this(id,
//                startTime,
//                endTime,
//                thickness,
//                circumference,
//                materialId,
//                pOrderId,
//                material);        
//    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the startTime
     */
    public GregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(GregorianCalendar startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public GregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(GregorianCalendar endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the thickness
     */
    public double getThickness() {
        return thickness;
    }

    /**
     * @param thickness the thickness to set
     */
    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    /**
     * @return the circumference
     */
    public double getCircumference() {
        return circumference;
    }

    /**
     * @param circumference the circumference to set
     */
    public void setCircumference(double circumference) {
        this.circumference = circumference;
    }

    /**
     * @return the materialId
     */
    public int getMaterialId() {
        return materialId;
    }

    /**
     * @param materialId the materialId to set
     */
    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    /**
     * @return the pOrderId
     */
    public int getpOrderId() {
        return pOrderId;
    }

    /**
     * @param pOrderId the pOrderId to set
     */
    public void setpOrderId(int pOrderId) {
        this.pOrderId = pOrderId;
    }

    public Material getMaterialName() {
        return getMaterial();
    }

    /**
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(Material material) {
        this.material = material;
    }
}
