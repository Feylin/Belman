package BE;

/**
 * BE.Material klassen
 * @author Daniel, Klaus, Mak, Rashid
 */
public class Material
{
    private int id;
    private double density;
    private String name;

    /**
     * Den overordnede konstruktør til Material
     *
     * @param id
     * @param density
     * @param name
     */
    public Material(int id, double density, String name)
    {
        this.id = id;
        this.density = density;
        this.name = name;
    }

    /**
     * Anden konstruktør til Material som opretter et material objekt med -1 id.
     * @param density
     * @param name
     */
    public Material(double density, String name)
    {
        this(-1, density, name);
    }

    /**
     * Tredje konstruktør til Material som opretter et material, kun med et name
     * @param name
     */
    public Material(String name)
    {
        this.name = name;
    }

    /**
     * @returnere name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name sætter name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @returnere density
     */
    public double getDensity()
    {
        return density;
    }

    /**
     * @param density sætter density
     */
    public void setDensity(double density)
    {
        this.density = density;
    }

    /**
     * @returnerne materialets id.
     */
    public int getId()
    {
        return id;
    }
    
    @Override
    public String toString()
    {
        return String.format(" %-5s", name);
    }
}
