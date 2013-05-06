/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import BLL.LoginManager;

/**
 *
 * @author Rashid
 */
public class Test
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            int id = 1;
            int newId = 3;
            String password = "a";
            String newPassword = "4ads3";
//            LoginManager.getInstance().addLogin(newId, password);
            LoginManager.getInstance().updateLogin(newId, newPassword);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
