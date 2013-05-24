/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Models;

import BE.Operator;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */

public class OperatorListModel extends AbstractListModel
{
    private ArrayList<Operator> operators = new ArrayList<>();
    
    public OperatorListModel(ArrayList<Operator> initialOperatorList)
    {
        operators = initialOperatorList;
        fireContentsChanged(operators, 0, operators.size());
    }
    
    @Override
    public int getSize()
    {
        return operators.size();
    }

    @Override
    public Object getElementAt(int i)
    {
        return operators.get(i);
    }
    
    public void updateOperatorList(ArrayList<Operator> operatorList)
    {
        operators = operatorList;
        fireContentsChanged(operators, 0, operators.size());
    }
}
