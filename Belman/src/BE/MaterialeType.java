package BE;

public enum MaterialeType 

{

    Jern (1),
    Stål (2),
    Aluminium (3),
    RustfritStål (4),
    Tin (5),
    NONE (0);

    public final int ID;

    private MaterialeType( int id ) {
        this.ID = id;
    }
}
