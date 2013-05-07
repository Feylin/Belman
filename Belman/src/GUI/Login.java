/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BE.Operator;
import BLL.LoginManager;
import BLL.OperatorManager;
import GUI.Models.OperatorListModel;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Feylin
 */
public class Login extends javax.swing.JFrame
{

    private String operator;
    private static Login instance = null;
    private static OperatorManager mgr = null;
    private static LoginManager lmgr = null;
    private OperatorListModel model = null;

    /**
     * Creates new form Login
     */
    private Login()
    {
        loadManagers();
        setUndecorated(true);
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/icons/belman.png")).getImage());
        addEnterKeyListener();
        lstOperators.grabFocus();
        setLocationRelativeTo(null);
        windowClose();
        listSelection();
    }

    private void loadManagers()
    {
        try
        {
            lmgr = LoginManager.getInstance();
            mgr = OperatorManager.getInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Login getInstance()
    {
        if (instance == null)
        {
            instance = new Login();
        }
        return instance;
    }

    public String getOperator()
    {
        return operator;
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
                    loginPressed();
                }
            }
        };
        jpfPassword.addKeyListener(enterKey);
        lstOperators.addKeyListener(enterKey);
    }

    private void defaultCursor()
    {
//        Welcome.getFrames()[0].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setCursor(Cursor.getDefaultCursor());
    }

    private void loadingCursor()
    {
//        Welcome.getFrames()[0].setCursor(new Cursor(Cursor.WAIT_CURSOR));
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    private void loginPressed()
    {
        if (txtOperator.getText().length() != 0)
        {
            String username = txtOperator.getText();
            char[] pass = jpfPassword.getPassword();
            String password = new String(pass);

            try
            {
                if (lmgr.checkLogin(username, password) == true)
                {
                    operator = username;

                    String message = "Welcome back " + operator + "\nBe productive =)";
                    JOptionPane.showMessageDialog(this, message, "Login Successful", JOptionPane.INFORMATION_MESSAGE);

                    loadingCursor();
                    Overview.getInstance().setVisible(true);
                    clearFields();
                    dispose();
                }
                else
                {
                    jpfPassword.setText("");
                    String message = "Wrong Password";
                    JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                defaultCursor();
            }
        }
        else
        {
            String message = "Please pick an operator";
            JOptionPane.showMessageDialog(this, message, getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closePressed()
    {
        String message = "Are you sure you want to exit?";
        int reply = JOptionPane.showConfirmDialog(null, message, getTitle(), JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }

    private void windowClose()
    {
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                closePressed();
            }
        });
    }

    private void clearFields()
    {
        jpfPassword.setText("");
        txtOperator.setText("");
        lstOperators.clearSelection();
    }

    private void selectedOperator()
    {
        Operator selectedOperator = (Operator) lstOperators.getSelectedValue();
        if (selectedOperator != null)
        {
            txtOperator.setText(selectedOperator.toString());
        }
    }

    private void listSelection()
    {
        try
        {
            model = new OperatorListModel(mgr.getAllOperators());
            lstOperators.setModel(model);

            lstOperators.addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent lse)
                {
                    if (!(lse.getValueIsAdjusting() || lstOperators.isSelectionEmpty()))
                    {
                        selectedOperator();
                    }
                }
            });
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

        lblBelman = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        lblOperator = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        jpfPassword = new javax.swing.JPasswordField();
        txtOperator = new javax.swing.JTextField();
        btnSignup = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        scrOperators = new javax.swing.JScrollPane();
        lstOperators = new javax.swing.JList();
        lblOperators = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Belman Manager");
        setResizable(false);

        lblBelman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Belman logo_02.2010.png"))); // NOI18N

        lblLogin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblLogin.setText("Login");

        lblOperator.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblOperator.setText("Operator");

        lblPassword.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPassword.setText("Password");

        txtOperator.setEnabled(false);

        btnSignup.setText("Sign up");

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnLoginActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/rsz_offbutton.png"))); // NOI18N
        btnClose.setContentAreaFilled(false);
        btnClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCloseActionPerformed(evt);
            }
        });

        scrOperators.setViewportView(lstOperators);

        lblOperators.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblOperators.setText("Operators");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblOperators, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblOperator))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jpfPassword)
                                            .addComponent(txtOperator, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(scrOperators, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblBelman)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBelman)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblOperators)
                            .addComponent(lblLogin))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrOperators, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 36, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtOperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblOperator))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jpfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPassword))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSignup)
                            .addComponent(btnLogin))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCloseActionPerformed
    {//GEN-HEADEREND:event_btnCloseActionPerformed
        closePressed();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnLoginActionPerformed
    {//GEN-HEADEREND:event_btnLoginActionPerformed
        loginPressed();
    }//GEN-LAST:event_btnLoginActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSignup;
    private javax.swing.JPasswordField jpfPassword;
    private javax.swing.JLabel lblBelman;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblOperator;
    private javax.swing.JLabel lblOperators;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JList lstOperators;
    private javax.swing.JScrollPane scrOperators;
    private javax.swing.JTextField txtOperator;
    // End of variables declaration//GEN-END:variables
}
