/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tablemodels;

import BE.Order;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author bhp
 */
public class OrderTablemodel extends AbstractTableModel
{
//    private final String[] header = {"ID","Event Name","Messege", "Date"};
//    private final Class[] columnTypes = {Integer.class, String.class, String.class, String.class};
    
    private final String[] header = {"Order", "Status"};
    private final Class[] columnTypes = {String.class, String.class};


    private ArrayList<Order> info;

    public OrderTablemodel(ArrayList<Order> allInfo)
    {
        info = allInfo;
    }

    public OrderTablemodel(List<Order> all)
    {
        info = (ArrayList<Order>) all;
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
       
       Order o = info.get(row);
       switch (col)
       {
           case 0 : return o.getOrderName();
           case 1 : return o.getStatus();
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

//    @Override
//    public boolean isCellEditable(int row, int col)
//    {
//        return (col > 0);
//    }

    @Override
    public void setValueAt(Object o, int row, int col)
    {
        Order or = info.get(row);
        switch (col)
        {
            case 0 : or.getOrderName(); break;
            case 1 : or.getStatus(); break;
        }
    }

    public Order getEventsByRow(int row)
    {
        return info.get(row);
    }
     

    public void setCollection(Collection<Order> order)
    {
        info = new ArrayList<>(order);
        fireTableDataChanged();
    }
}
