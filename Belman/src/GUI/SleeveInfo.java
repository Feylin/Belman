/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BE.Operator;
import BE.Order;
import BLL.OperatorManager;
import BLL.OrderManager;
import BLL.SleeveLogManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel, Klaus, Mak, Rashid
 */

public class SleeveInfo extends JDialog
{
    private Order order;    
    private Operator operator;
    private OrderManager omgr = null;
    private OperatorManager opmgr = null;
    private SleeveLogManager slmgr = null;

    /**
     * Creates new form SleeveInfo
     */
    public SleeveInfo(Order o, Operator op)
    {
        setModal(true);
        operator = op;
        order = o;
        loadManagers();
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/icons/belman.png")).getImage());
        numbersOnlyKeyListener();
        setLocationByPlatform(true);

        txtOf.setText(String.valueOf(o.getQuantity()));
    }
    
    private void loadManagers()
    {
        try
        {
            omgr = OrderManager.getInstance();
            opmgr = OperatorManager.getInstance();
            slmgr = SleeveLogManager.getInstance();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void numbersOnlyKeyListener()
    {
        txtSleevesMade.addKeyListener(new gui.NumbersOnlyKeyListener());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        btnOk = new javax.swing.JButton();
        lblSleevesMade = new javax.swing.JLabel();
        txtSleevesMade = new javax.swing.JTextField();
        lblOf = new javax.swing.JLabel();
        txtOf = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Belman Manager");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnOk.setText("Ok");
        btnOk.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnOkActionPerformed(evt);
            }
        });

        lblSleevesMade.setText("Sleeves Made:");

        lblOf.setText("Of");

        txtOf.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSleevesMade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSleevesMade, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOf)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOf, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSleevesMade)
                    .addComponent(txtSleevesMade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOf)
                    .addComponent(txtOf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOk)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        int cut = Integer.parseInt(txtSleevesMade.getText()) + order.getConductedQuantity();

        if (cut < order.getQuantity())
        {
            order.setConductedQuantity(cut);
            try
            {
                omgr.update(order);
                slmgr.addLog(order.getSleeve().getId(), operator, cut, 0);
                dispose();
            }
            catch (SQLException ex)
            {
               ex.printStackTrace();
            } 
        }
        else if (cut == order.getQuantity())
        {
            order.setConductedQuantity(cut);
            String status = "Finished";
            order.setStatus(status.toUpperCase());
            try
            {
                omgr.update(order);
                slmgr.addLog(order.getSleeve().getId(), operator, cut, 0);
                dispose();
            }
            catch (SQLException ex)
            {
               ex.printStackTrace();
            }
        }
        else
        {
            String message = "You cant cut more than the total quantity";
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
//            if (reply == JOptionPane.CANCEL_OPTION)
//            {
//                dispose();
//            }

        }
    }//GEN-LAST:event_btnOkActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel lblOf;
    private javax.swing.JLabel lblSleevesMade;
    private javax.swing.JTextField txtOf;
    private javax.swing.JTextField txtSleevesMade;
    // End of variables declaration//GEN-END:variables
}
