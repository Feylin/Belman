/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package APP;

import GUI.Overview;
import java.awt.EventQueue;
import javax.swing.UIManager;

/**
 *
 * @author Rashid
 */
public class BelmanProject
{

    /**
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