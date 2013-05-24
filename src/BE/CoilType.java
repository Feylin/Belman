package BE;

/**
 * BE.CoilType klasse
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
     *
     * @param id
     * @param code
     * @param width
     * @param thickness
     * @param materialId
     */
    public CoilType(int id, String code, double width, double thickness
            , int materialId)
    {
        this.id = id;
        this.code = code;
        this.width = width;
        this.thickness = thickness;
        this.materialId = materialId;
    }

    /**
     * Anden konstruktør til coilType som opretter et CoilType objekt med en id
     * @param id
     * @param c
     */
    public CoilType(int id, CoilType c)
    {
        this(id,
                c.getCode(),
                c.getWidth(),
                c.getThickness(),
                c.getMaterialId());
    }

    /**
     * Tredje konstruktør til CoilType som opretter et CoilType objekt
     * med en id på -1
     * @param code
     * @param width
     * @param thickness
     * @param materialId
     */
    public CoilType(String code, double width, double thickness, int materialId)
    {
        this(-1, code, width, thickness, materialId);
    }

    /**
     * @returnere id for en CoilType
     */
    public int getId()
    {
        return id;
    }

    /**
     * @returnere code for en CoilType
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code sætter en ny code
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * @returnere width for en CoilType
     */
    public double getWidth()
    {
        return width;
    }

    /**
     * @param width sætter en ny width
     */
    public void setWidth(double width)
    {
        this.width = width;
    }

    /**
     * @returnere tykkelsen for en CoilType
     */
    public double getThickness()
    {
        return thickness;
    }

    /**
     * @param thickness sætter en ny thickness
     */
    public void setThickness(double thickness)
    {
        this.thickness = thickness;
    }

    /**
     * @returnere materialId for en CoilType
     */
    public int getMaterialId()
    {
        return materialId;
    }

    /**
     * @param materialId sætter en ny materialId
     */
    public void setMaterialId(int materialId)
    {
        this.materialId = materialId;
    }
}
