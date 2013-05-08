/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BE.Operator;
import BLL.LoginManager;
import BLL.OperatorManager;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Administrator
 */
public class OperatorSettings extends javax.swing.JDialog
{

    private static OperatorSettings instance = null;
    private static OperatorManager mgr;
    private static LoginManager lmgr;
    Operator o;

    /**
     * Creates new form OperatorSettings
     */
    private OperatorSettings()
    {
        loadManagers();
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/icons/belman.png")).getImage());
        closeListener();
        windowClose();
        addEnterKeyListener();
        showPassword();
        setLocationRelativeTo(null);
        operatorSettings();
        setModal(true);
    }

    public static OperatorSettings getInstance()
    {
        if (instance == null)
        {
            instance = new OperatorSettings();
        }
        return instance;
    }

    private void loadManagers()
    {
        try
        {
            mgr = OperatorManager.getInstance();
            lmgr = LoginManager.getInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void closeListener()
    {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
        getRootPane().getActionMap().put("close", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                closeAndDiscard();
            }
        });
    }

    private void windowClose()
    {
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                closeAndDiscard();
            }
        });
    }

    public void closeAndDiscard()
    {
        String message = "Close and discard input?";
        int reply = JOptionPane.showConfirmDialog(this, message, getTitle(), JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
        {
            dispose();
            clearFields();
        }
    }

    private void addEnterKeyListener()
    {
        KeyListener enterKey = new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent ke)
            {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    btnSave.doClick();
                }
            }
        };
        jpfOldPassword.addKeyListener(enterKey);
        jpfNewPassword.addKeyListener(enterKey);
        jpfConfirmPassword.addKeyListener(enterKey);
    }

    private void clearFields()
    {
        jpfOldPassword.setText("");
        jpfNewPassword.setText("");
        jpfConfirmPassword.setText("");
    }

    private void showPassword()
    {
        chkbShowPassword.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    jpfOldPassword.setEchoChar((char) 0);
                    jpfNewPassword.setEchoChar((char) 0);
                    jpfConfirmPassword.setEchoChar((char) 0);
                }
                else
                {
                    jpfOldPassword.setEchoChar('\u25CF'); //Bullet Character '•'
                    jpfNewPassword.setEchoChar('\u25CF'); //Bullet Character '•'
                    jpfConfirmPassword.setEchoChar('\u25CF'); //Bullet Character '•'
                }
            }
        });
    }

    private void operatorSettings()
    {
        String operator = Login.getInstance().getOperator();

        try
        {
            o = mgr.get(operator);
            txtUsername.setText(operator);
            txtFirstName.setText(o.getFirstName());
            txtLastName.setText(o.getLastName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void savePressed()
    {
        try
        {
            String operator = txtUsername.getText();
            char[] oldPass = jpfOldPassword.getPassword();
            String oldPassword = new String(oldPass);
            char[] newPass = jpfNewPassword.getPassword();
            String newPassword = new String(newPass);
            char[] confirmPass = jpfConfirmPassword.getPassword();
            String confirmPassword = new String(confirmPass);

            if (lmgr.checkLogin(operator, oldPassword))
            {
                if (newPassword.equals(confirmPassword))
                {
                    lmgr.updateLogin(operator, newPassword);
//                    dispose();
                    clearFields();
                    String message = operator + " was successfully updated!";
                    JOptionPane.showMessageDialog(null, message, "Operator Settings", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    String message = "Passwords do not match!";
                    JOptionPane.showMessageDialog(this, message, "Operator Settings", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                String message = "Old Password is wrong!";
                JOptionPane.showMessageDialog(this, message, "Operator Settings", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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

        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        pnlPersonal = new javax.swing.JPanel();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        lblUsername = new javax.swing.JLabel();
        pnlPassword = new javax.swing.JPanel();
        lblNewPassword = new javax.swing.JLabel();
        lblConfirmPassword = new javax.swing.JLabel();
        jpfNewPassword = new javax.swing.JPasswordField();
        jpfConfirmPassword = new javax.swing.JPasswordField();
        lblOldPassword = new javax.swing.JLabel();
        jpfOldPassword = new javax.swing.JPasswordField();
        chkbShowPassword = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Belman Manager");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCancelActionPerformed(evt);
            }
        });

        pnlPersonal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Personal Info"));

        lblFirstName.setText("First Name");

        txtFirstName.setEditable(false);

        lblLastName.setText("Last Name");

        txtLastName.setEditable(false);

        txtUsername.setEditable(false);

        lblUsername.setText("Username");

        javax.swing.GroupLayout pnlPersonalLayout = new javax.swing.GroupLayout(pnlPersonal);
        pnlPersonal.setLayout(pnlPersonalLayout);
        pnlPersonalLayout.setHorizontalGroup(
            pnlPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPersonalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFirstName)
                    .addComponent(lblLastName)
                    .addComponent(lblUsername))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUsername)
                    .addComponent(txtLastName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnlPersonalLayout.setVerticalGroup(
            pnlPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPersonalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsername))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFirstName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLastName))
                .addGap(37, 37, 37))
        );

        pnlPassword.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Password"));

        lblNewPassword.setText("New Password");

        lblConfirmPassword.setText("Confirm Password");

        lblOldPassword.setText("Old Password");

        chkbShowPassword.setText("Show Password?");

        javax.swing.GroupLayout pnlPasswordLayout = new javax.swing.GroupLayout(pnlPassword);
        pnlPassword.setLayout(pnlPasswordLayout);
        pnlPasswordLayout.setHorizontalGroup(
            pnlPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPasswordLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNewPassword)
                    .addComponent(lblConfirmPassword)
                    .addComponent(lblOldPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpfOldPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addComponent(jpfConfirmPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addComponent(jpfNewPassword)
                    .addComponent(chkbShowPassword, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnlPasswordLayout.setVerticalGroup(
            pnlPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPasswordLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jpfOldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOldPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNewPassword)
                    .addComponent(jpfNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jpfConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConfirmPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkbShowPassword)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnlPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSaveActionPerformed
    {//GEN-HEADEREND:event_btnSaveActionPerformed
        savePressed();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
    {//GEN-HEADEREND:event_btnCancelActionPerformed
        closeAndDiscard();
    }//GEN-LAST:event_btnCancelActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox chkbShowPassword;
    private javax.swing.JPasswordField jpfConfirmPassword;
    private javax.swing.JPasswordField jpfNewPassword;
    private javax.swing.JPasswordField jpfOldPassword;
    private javax.swing.JLabel lblConfirmPassword;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblNewPassword;
    private javax.swing.JLabel lblOldPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlPassword;
    private javax.swing.JPanel pnlPersonal;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
