/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BE.Order;
import BE.Sleeve;
import BE.StockItem;
import BLL.MaterialManager;
import BLL.OperatorManager;
import BLL.OrderManager;
import BLL.SleeveManager;
import BLL.StockItemManager;
import GUI.Models.MaterialTableModel;
import GUI.Models.OrderTablemodel;
import GUI.Models.ProductionSleeveTableModel;
import GUI.Models.SleeveTableModel;
import GUI.Models.StockList2TableModel;
import GUI.Models.StockListTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Rashid
 */
public class Overview extends javax.swing.JFrame implements Observer
{
    
    static OrderManager omgr = null;
    static StockItemManager smgr = null;
    static MaterialManager mmgr = null;
    static SleeveManager slmgr = null;
    static OperatorManager opmgr = null;
    private OrderTablemodel omodel = null;
    private static Overview instance = null;
    private StockListTableModel smodel = null;
    private StockListTableModel smodel4 = null;
    private OrderTablemodel omodel2 = null;
    private SleeveTableModel slmodel = null;
    private ResourceBundle rb = null;
    private StockListTableModel smodel2 = null;
    private ProductionSleeveTableModel psmodel = null;
    private StockList2TableModel smodel3 = null;   
    Order o;

    /**
     * Creates new form Overview
     */
    private Overview()
    {
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("GUI.Bundle");
//        setExtendedState(MAXIMIZED_BOTH); MAXIMIZED WINDOW
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/icons/belman.png")).getImage());
//        loggedInAs();
        windowClose();
        setLocationRelativeTo(null);
        productionSleeveListSelectioner();
      
        localeLanguage.setLocale(locale);
        mouseListener();
        setTableColumnSize();
        setTableSelectionMode();
        updateGUILanguage();
        initiateCbx();
        
        
    }
    
    public static Overview getInstance()
    {
        if (instance == null)
        {
            instance = new Overview();
        }
        return instance;
    }
    
    private void setTableColumnSize()
    {
        tblProductionSleeve.getColumnModel().getColumn(0).setPreferredWidth(190);
        tblProductionSleeve.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblProductionSleeve.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(6).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(7).setPreferredWidth(100);
    }
    
    private void setTableSelectionMode()
    {
      
        tblProductionSleeve.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
        tblStockList3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void updateGUILanguage()
    {
        btnClose.setText(rb.getString("Overview.btnClose.text"));
        btnLogout.setText(rb.getString("Overview.btnLogout.text"));
       
        btnReset1.setText(rb.getString("Overview.btnReset1.text"));
        
        menuFile.setText(rb.getString("Overview.menuFile.text"));
        menuSettings.setText(rb.getString("Overview.menuSettings.text"));
        
     
        
        jTabbedPane1.setTitleAt(0, rb.getString("Overview.pnlCutting2.TabConstraints.tabTitle"));
     
        
      
        
        
        
        itemHelp.setText(rb.getString("Overview.itemHelp.text_1"));
        itemSettings.setText(rb.getString("Overview.itemSettings.text"));
        itemLogOut.setText(rb.getString("Overview.itemLogOut.text_1"));
        itemExit.setText(rb.getString("Overview.itemExit.text"));
        
       
    }
    
//    private void loggedInAs()
//    {
//        String operator = Login.getInstance().getOperator();
//        lblLoggedIn.setText("Logged in as " + operator);
//    }
    
    private void closePressed()
    {
        String message = "Are you sure you want to exit?";
        int reply = JOptionPane.showConfirmDialog(null, message, getTitle(), JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }
    
    private void logoutPressed()
    {
        String message = "Are you sure you want to log out?";
        int reply = JOptionPane.showConfirmDialog(this, message, getTitle(), JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
        {
            dispose();
            Login.getInstance().setVisible(true);
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

//     private void btnSavePressed()
//    {
//        try
//        {
//        
//        OrderType type = rbtnStart.isSelected() ? OrderType.START
//                    : rbtnPause.isSelected() ? OrderType.PAUSE
//                    : rbtnAfslut.isSelected() ? OrderType.AFSLUT
//                    : OrderType.PROGRESS;
//            o.setType(type);
//
//            omgr.update(o);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    private void logout()
//    {
//        String message = "Are you sure you want to log out?";
//        int reply = JOptionPane.showConfirmDialog(this, message, getTitle(), JOptionPane.YES_NO_OPTION);
//        if (reply == JOptionPane.YES_OPTION)
//        {
//            dispose();
//            Login.getInstance().setVisible(true);
//        }
//    }
    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof OrderManager)
        {
            try
            {
                omodel.setCollection(omgr.getAll());
            }
            catch (Exception e)
            {
            }
        }
    }
    
    
    
  
    
    private void productionSleeveListSelectioner()
    {
        try
        {
            
            smgr = StockItemManager.getInstance();
            smgr.addObserver(this);
            smodel3 = new StockList2TableModel(smgr.getAll());
            tblStockList3.setModel(smodel3);
            
            omgr = OrderManager.getInstance();
            omgr.addObserver(this);
            psmodel = new ProductionSleeveTableModel(omgr.getAll());
            tblProductionSleeve.setModel(psmodel);
            
            tblProductionSleeve.getSelectionModel().addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent es)
                {
                    int selectedRow = tblProductionSleeve.getSelectedRow();
                    if (selectedRow == -1)
                    {
                        return;
                    }
                    Order o = psmodel.getEventsByRow(selectedRow);
                    try
                    {
                        if (!smgr.getItemByOrder(o).isEmpty())
                        {
                            smodel3 = new StockList2TableModel(smgr.getItemByOrder(o));
                            tblStockList3.setModel(smodel3);

//                            if (!smgr.getItemBySleeve(s).isEmpty())
//                            {
//                                smodel2 = new StockListTableModel(smgr.getItemBySleeve(s));
//                                tblStockItem.setModel(smodel2);
//                            }

//                       tblOrderList1.getSelectionModel().addListSelectionListener(new ListSelect);                        
//                        }
//                    else
//                    {
////                        omodel2.
//                        tblOrderList1.setModel(omodel2);
                        }
                        else
                        {
                            smodel2 = new StockListTableModel();
                            tblStockList3.setModel(smodel2);
                        }
//                    Sleeve s = slmodel.getEventsByRow(selectedRow);
                    }
                    catch (Exception e)
                    {
                    }
                }
            });
            smgr = StockItemManager.getInstance();
            smgr.addObserver(this);
            smodel3 = new StockList2TableModel(smgr.getAll());
            tblStockList3.setModel(smodel3);
            tblStockList3.getSelectionModel().addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent es)
                {
                    int selectedRow = tblStockList3.getSelectedRow();
                    if (selectedRow == -1)
                    {
                        return;
                    }
                    StockItem s = smodel3.getEventsByRow(selectedRow);
                    try
                    {
                        if (!omgr.getOrderByStock(s).isEmpty())
                        {
                            psmodel = new ProductionSleeveTableModel(omgr.getOrderByStock(s));
                            tblProductionSleeve.setModel(psmodel);

//                            if (!smgr.getItemBySleeve(s).isEmpty())
//                            {
//                                smodel2 = new StockListTableModel(smgr.getItemBySleeve(s));
//                                tblStockItem.setModel(smodel2);
//                            }
//
//                       tblOrderList1.getSelectionModel().addListSelectionListener(new ListSelect);                        
//                        }
//                    else
//                    {
////                        omodel2.
//                        tblOrderList1.setModel(omodel2);
                        }
                        else
                        {
                            smodel4 = new StockListTableModel();
                            tblProductionSleeve.setModel(smodel4);
                        }
                    }
                    catch (Exception e)
                    {
                    }
                }
            });
        }
        catch (Exception e)
        {
        }
    }
    
    private void mouseListener()
    {
        tblProductionSleeve.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent me)
            {
                if (me.getClickCount() == 2)
                {
                    int selectedRow = tblProductionSleeve.getSelectedRow();
                    Order o = psmodel.getEventsByRow(selectedRow);
                    new OrderInfo(o).setVisible(true);
                }
                
            }
        });
    }
    
    private void initiateCbx()
    {
         try
        {
            opmgr = OperatorManager.getInstance();
        }        
        catch (IOException ex)
        {
            Logger.getLogger(Overview.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {            
            cbxOperator.setModel(new javax.swing.DefaultComboBoxModel(opmgr.getAllOperators().toArray()));
        }
        catch (SQLException ex)
        {
            Logger.getLogger(Overview.class.getName()).log(Level.SEVERE, null, ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlCutting2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductionSleeve = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStockList3 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        pnlLoggedIn = new javax.swing.JPanel();
        lblLoggedIn = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        btnReset1 = new javax.swing.JButton();
        localeLanguage = new com.toedter.components.JLocaleChooser();
        cbxOperator = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        itemExit = new javax.swing.JMenuItem();
        menuSettings = new javax.swing.JMenu();
        itemSettings = new javax.swing.JMenuItem();
        itemLogOut = new javax.swing.JMenuItem();
        seperatorSettings = new javax.swing.JPopupMenu.Separator();
        itemHelp = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("GUI/Bundle"); // NOI18N
        setTitle(bundle.getString("Overview.title")); // NOI18N
        setResizable(false);

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jTabbedPane1StateChanged(evt);
            }
        });

        tblProductionSleeve.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblProductionSleeve);

        tblStockList3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblStockList3);

        jLabel1.setText(bundle.getString("Overview.jLabel1.text")); // NOI18N

        jLabel2.setText(bundle.getString("Overview.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout pnlCutting2Layout = new javax.swing.GroupLayout(pnlCutting2);
        pnlCutting2.setLayout(pnlCutting2Layout);
        pnlCutting2Layout.setHorizontalGroup(
            pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCutting2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCutting2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        pnlCutting2Layout.setVerticalGroup(
            pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCutting2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(100, 100, 100))
        );

        jTabbedPane1.addTab(bundle.getString("Overview.pnlCutting2.TabConstraints.tabTitle"), pnlCutting2); // NOI18N

        btnClose.setText(bundle.getString("Overview.btnClose.text")); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCloseActionPerformed(evt);
            }
        });

        pnlLoggedIn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlLoggedIn.setPreferredSize(new java.awt.Dimension(245, 19));

        lblLoggedIn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblLoggedIn.setText(bundle.getString("Overview.lblLoggedIn.text")); // NOI18N

        javax.swing.GroupLayout pnlLoggedInLayout = new javax.swing.GroupLayout(pnlLoggedIn);
        pnlLoggedIn.setLayout(pnlLoggedInLayout);
        pnlLoggedInLayout.setHorizontalGroup(
            pnlLoggedInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoggedInLayout.createSequentialGroup()
                .addComponent(lblLoggedIn)
                .addGap(0, 168, Short.MAX_VALUE))
        );
        pnlLoggedInLayout.setVerticalGroup(
            pnlLoggedInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoggedInLayout.createSequentialGroup()
                .addComponent(lblLoggedIn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnLogout.setText(bundle.getString("Overview.btnLogout.text")); // NOI18N
        btnLogout.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnLogoutActionPerformed(evt);
            }
        });

        btnReset1.setText(bundle.getString("Overview.btnReset1.text")); // NOI18N
        btnReset1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnReset1ActionPerformed(evt);
            }
        });

        localeLanguage.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                localeLanguageActionPerformed(evt);
            }
        });

        cbxOperator.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxOperator.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cbxOperatorActionPerformed(evt);
            }
        });

        jLabel4.setText(bundle.getString("Overview.jLabel4.text")); // NOI18N

        menuFile.setText(bundle.getString("Overview.menuFile.text")); // NOI18N

        itemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_MASK));
        itemExit.setText(bundle.getString("Overview.itemExit.text")); // NOI18N
        itemExit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                itemExitActionPerformed(evt);
            }
        });
        menuFile.add(itemExit);

        menuBar.add(menuFile);

        menuSettings.setText(bundle.getString("Overview.menuSettings.text")); // NOI18N

        itemSettings.setText(bundle.getString("Overview.itemSettings.text")); // NOI18N
        itemSettings.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                itemSettingsActionPerformed(evt);
            }
        });
        menuSettings.add(itemSettings);

        itemLogOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK));
        itemLogOut.setText(bundle.getString("Overview.itemLogOut.text_1")); // NOI18N
        itemLogOut.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                itemLogOutActionPerformed(evt);
            }
        });
        menuSettings.add(itemLogOut);
        menuSettings.add(seperatorSettings);

        itemHelp.setText(bundle.getString("Overview.itemHelp.text_1")); // NOI18N
        menuSettings.add(itemHelp);

        menuBar.add(menuSettings);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReset1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlLoggedIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxOperator, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(localeLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLoggedIn, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(localeLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxOperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnLogout)
                    .addComponent(btnReset1))
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName(bundle.getString("Overview.jTabbedPane1.AccessibleContext.accessibleName")); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemExitActionPerformed
    {//GEN-HEADEREND:event_itemExitActionPerformed
        closePressed();
    }//GEN-LAST:event_itemExitActionPerformed
    
    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCloseActionPerformed
    {//GEN-HEADEREND:event_btnCloseActionPerformed
        closePressed();
    }//GEN-LAST:event_btnCloseActionPerformed
        
    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnLogoutActionPerformed
    {//GEN-HEADEREND:event_btnLogoutActionPerformed
        logoutPressed();
    }//GEN-LAST:event_btnLogoutActionPerformed
    
    private void itemLogOutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemLogOutActionPerformed
    {//GEN-HEADEREND:event_itemLogOutActionPerformed
        logoutPressed();
    }//GEN-LAST:event_itemLogOutActionPerformed
    
    private void itemSettingsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemSettingsActionPerformed
    {//GEN-HEADEREND:event_itemSettingsActionPerformed
        OperatorSettings.getInstance().setVisible(true);
    }//GEN-LAST:event_itemSettingsActionPerformed

    // RESET BUTTON DOES NOT WORK!!!!!!
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnResetActionPerformed
    {//GEN-HEADEREND:event_btnResetActionPerformed
        
      
    }//GEN-LAST:event_btnResetActionPerformed
    
    private void btnReset1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnReset1ActionPerformed
    {//GEN-HEADEREND:event_btnReset1ActionPerformed
      
            try
            {
                
                smodel3 = new StockList2TableModel(smgr.getAll());
                tblStockList3.setModel(smodel3);
                psmodel = new ProductionSleeveTableModel(omgr.getAll());
                tblProductionSleeve.setModel(psmodel);
//            tblSleeveList.repaint(); 

            }
            catch (Exception ex)
            {
                ex.getMessage();
            }
            finally
            {
                tblProductionSleeve.clearSelection();
                tblStockList3.clearSelection();
            }
        
        
        
        
    }//GEN-LAST:event_btnReset1ActionPerformed
            
    private void localeLanguageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_localeLanguageActionPerformed
    {//GEN-HEADEREND:event_localeLanguageActionPerformed
        rb = ResourceBundle.getBundle("GUI.Bundle", localeLanguage.getLocale());
        updateGUILanguage();
    }//GEN-LAST:event_localeLanguageActionPerformed
    
    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jTabbedPane1StateChanged
    {//GEN-HEADEREND:event_jTabbedPane1StateChanged
        if (jTabbedPane1.getSelectedIndex() == 0)
        {
            btnReset1.setVisible(true);
        }
        if (jTabbedPane1.getSelectedIndex() == 1)
        {
            btnReset1.setVisible(false);
        }
        if (jTabbedPane1.getSelectedIndex() == 2)
        {
            btnReset1.setVisible(true);
        }
        if (jTabbedPane1.getSelectedIndex() == 3)
        {
            btnReset1.setVisible(true);
        }
        if (jTabbedPane1.getSelectedIndex() == 4)
        {
            btnReset1.setVisible(false);
        }
        if (jTabbedPane1.getSelectedIndex() == 5)
        {
            btnReset1.setVisible(false);
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void cbxOperatorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cbxOperatorActionPerformed
    {//GEN-HEADEREND:event_cbxOperatorActionPerformed
      
        lblLoggedIn.setText("Logged in as " + cbxOperator.getItemAt(0).toString());        
    }//GEN-LAST:event_cbxOperatorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnReset1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbxOperator;
    private javax.swing.JMenuItem itemExit;
    private javax.swing.JMenuItem itemHelp;
    private javax.swing.JMenuItem itemLogOut;
    private javax.swing.JMenuItem itemSettings;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblLoggedIn;
    private com.toedter.components.JLocaleChooser localeLanguage;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuSettings;
    private javax.swing.JPanel pnlCutting2;
    private javax.swing.JPanel pnlLoggedIn;
    private javax.swing.JPopupMenu.Separator seperatorSettings;
    private javax.swing.JTable tblProductionSleeve;
    private javax.swing.JTable tblStockList3;
    // End of variables declaration//GEN-END:variables
}
