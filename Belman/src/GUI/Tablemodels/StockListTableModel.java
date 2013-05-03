/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tablemodels;

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
    
    private final String[] header = {"Stock List"};
    private final Class[] columnTypes = {String.class};


    private ArrayList<StockItem> info;

    public StockListTableModel(ArrayList<StockItem> allInfo)
    {
        info = allInfo;
    }

    public StockListTableModel(List<StockItem> all)
    {
        info = (ArrayList<StockItem>) all;
    }

    @Override
    public int getRowCount()
    {
        return info.size();
    }

    @Override
    public int getColumnCount()
    {
        return header.length;
    }

    @Override
    public Object getValueAt(int row, int col)
    {
       StockItem s = info.get(row);
       switch (col)
       {
//           case 0: return s.getMaterialName();
       }
       return null;
    }

    @Override
    public String getColumnName(int col)
    {
        return header[col];
    }

    @Override
    public Class<?> getColumnClass(int col)
    {
        return columnTypes[col];
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return (col > 0);
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

    public StockItem getEventsByRow(int row)
    {
        return info.get(row);
    }

    public void setCollection(Collection<StockItem> stockItem)
    {
        info = new ArrayList<>(stockItem);
        fireTableDataChanged();
    }
}
