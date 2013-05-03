package BE;

public enum OrderType


{

    START (1),
    PAUSE (2),
    AFSLUT (3),
    PROGRESS(0);

    public final int ID;

    private OrderType( int id ) {
        this.ID = id;
    }
}
