/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Tablemodels;

import BE.Material;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author bhp
 */
public class MaterielTableModel extends AbstractTableModel
{
//    private final String[] header = {"ID","Event Name","Messege", "Date"};
//    private final Class[] columnTypes = {Integer.class, String.class, String.class, String.class};
    
    private final String[] header = {"Material Id"," Material Name"};
    private final Class[] columnTypes = {Integer.class, String.class};


    private ArrayList<Material> info;

    public MaterielTableModel(ArrayList<Material> allInfo)
    {
        info = allInfo;
    }

    public MaterielTableModel(List<Material> all)
    {
        info = (ArrayList<Material>) all;
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
       Material m = info.get(row);
       switch (col)
       {
           case 0 : return m.getId();
           case 1 : return m.getName();
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
        Material m = info.get(row);
        switch (col)
        {
            case 0 : m.getId();
            case 1 : m.getName();
        }
    }

    public Material getEventsByRow(int row)
    {
        return info.get(row);
    }

    public void setCollection(Collection<Material> material)
    {
        info = new ArrayList<>(material);
        fireTableDataChanged();
    }
     public Material getMaterialByRow(int row)
    {
        if( row < 0 ) return null;
        return info.get(row);
    }
}
