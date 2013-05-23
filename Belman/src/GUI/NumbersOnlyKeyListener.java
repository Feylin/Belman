package gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NumbersOnlyKeyListener extends KeyAdapter{

    @Override
    public void keyTyped( KeyEvent ke ){
        if( !Character.isDigit( ke.getKeyChar() )) ke.consume();
    }
}
