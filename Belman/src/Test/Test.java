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
            String newId = "Klaus Roswall";
            String password = "a";
            String newPassword = "admin";
//            LoginManager.getInstance().addLogin("Mak Jakubovic", newPassword);
//            LoginManager.getInstance().addLogin("Klaus Andresen", newPassword);
//            LoginManager.getInstance().addLogin("Daniel Foght Jensen", newPassword);
//            LoginManager.getInstance().addLogin("Rashid Abdel-Majid", newPassword);
            if (LoginManager.getInstance().checkLogin("Klaus Andresen", newPassword) == true)
            {
                System.out.println("correct");
            }
            else
            {
                System.out.println("false");
            }
//            LoginManager.getInstance().updateLogin(newId, newPassword);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
