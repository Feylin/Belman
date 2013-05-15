/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Models;

import BE.StockItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author bhp
 */
public class StockListTableModel extends AbstractTableModel
{
//    private final String[] header = {"ID","Event Name","Messege", "Date"};
//    private final Class[] columnTypes = {Integer.class, String.class, String.class, String.class};
    
    private final String[] header = {"Stock List", "Materiale"};
    private final Class[] columnTypes = {String.class, String.class};

    private ArrayList<StockItem> info;

    public StockListTableModel()
    {
        
    }

    

   

    @Override
    public void setValueAt(Object o, int row, int col)
    {
        StockItem s = info.get(row);
        switch (col)
        {
//            case 0 : s.getMaterialName();
        }
    }

    @Override
    public int getRowCount()
    {
        return 0;
    }

    @Override
    public int getColumnCount()
    {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  
}
