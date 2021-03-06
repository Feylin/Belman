package APP;

import GUI.Login;
import GUI.Overview;
import java.awt.EventQueue;
import javax.swing.UIManager;

/**
 * Belman Projekt 2012
 * @author Daniel, Klaus, Mak, Rashid
 */
public class BelmanProject
{

    /**
     * Belman Project main metode.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    Overview.getInstance().setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}