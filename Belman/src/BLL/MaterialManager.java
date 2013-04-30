/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BLL;

import BE.Material;
import DAL.MaterialDBManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Mak
 */
public class MaterialManager extends Observable{
    
    private MaterialDBManager accessor;
    private static MaterialManager instance;
    
    public MaterialManager() throws IOException
    {
        accessor = MaterialDBManager.getInstance();
    }
    
     public static MaterialManager getInstance() throws FileNotFoundException, IOException{
        if( instance == null ) instance = new MaterialManager();
        return instance;
    }
     
     public ArrayList<Material> getAllMaterials() throws IOException, SQLException             
    {
       return accessor.getAllMaterials();      
    }
     
}
