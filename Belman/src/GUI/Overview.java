/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BE.Order;
import BE.Sleeve;
import BE.StockItem;
import BLL.MaterialManager;
import BLL.OrderManager;
import BLL.SleeveManager;
import BLL.StockItemManager;
import GUI.Models.MaterialTableModel;
import GUI.Models.OrderTablemodel;
import GUI.Models.SleeveTableModel;
import GUI.Models.StockListTableModel;
import com.toedter.components.JLocaleChooser;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
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
    private OrderTablemodel omodel = null;
    private static Overview instance = null;
    private StockListTableModel smodel = null;
    private MaterialTableModel mmodel = null;
    private OrderTablemodel omodel2 = null;
    private SleeveTableModel slmodel = null;
    private ResourceBundle rb = null;
    private StockListTableModel smodel2 = null;
    Order o;

    /**
     * Creates new form Overview
     */
    private Overview()
    {
        rb = ResourceBundle.getBundle("GUI.Bundle");
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/icons/belman.png")).getImage());
        loggedInAs();
        windowClose();
        setLocationRelativeTo(null);
        orderListSelectioner();
        sleeveListSelectioner();
        stockItemListSelectioner();
        updateGUILanguage();
    }

    public static Overview getInstance()
    {
        if (instance == null)
        {
            instance = new Overview();
        }
        return instance;
    }

    private void updateGUILanguage()
    {
        btnClose.setText(rb.getString("Overview.btnClose.text"));
        btnLogout.setText(rb.getString("Overview.btnLogout.text"));
        btnFinishOrder.setText(rb.getString("Overview.btnFinishOrder.text"));
        
        menuFile.setText(rb.getString("Overview.menuFile.text"));
        menuSettings.setText(rb.getString("Overview.menuSettings.text"));
        
        jLabel6.setText(rb.getString("Overview.jLabel6.text"));
        
//        pnlCutting1.setLocale(Locale.UK);Overview.pnlCutting1.TabConstraints.tabTitle
//        pnlControlPanel.setT(Locale.UK);Overview.pnlControlPanel.border.title
//        pnlMeasurements.setTitle(Overview.pnlMeasurements.border.title);
//        pnlCustomerInfo.setLocale(Locale.UK);Overview.pnlCustomerInfo.border.title
//        pnlOrderStock.setLocale(Locale.UK);Overview.pnlOrderStock.border.title
//        pnlOrderInfo.setLocale(Locale.UK);Overview.pnlOrderInfo.border.title
//        pnlOrder.setLocale(Locale.UK);Overview.pnlOrder.TabConstraints.tabTitle
//        pnlInstock.setLocale(Locale.UK);Overview.pnlInstock.TabConstraints.tabTitle
//        pnlCutting3.setLocale(Locale.UK);Overview.pnlCutting3.TabConstraints.tabTitle
//        pnlCutting2.setLocale(Locale.UK);Overview.pnlCutting2.TabConstraints.tabTitle
        
        rbtnUrgent.setText(rb.getString("Overview.rbtnUrgent.text"));
        rbtnPending.setText(rb.getString("Overview.rbtnPending.text"));
        rbtnInProgress.setText(rb.getString("Overview.rbtnInProgress.text"));

//        pnlMeasurements.
//        pnlOrderInfo.set        
        
//        JPanalStockInfo.set
               
        itemHelp.setText(rb.getString("Overview.itemHelp.text_1"));
        itemSettings.setText(rb.getString("Overview.itemSettings.text"));
        itemLogOut.setText(rb.getString("Overview.itemLogOut.text_1"));
        itemExit.setText(rb.getString("Overview.itemExit.text"));
        
        lblCharge.setText(rb.getString("Overview.lblCharge.text"));
        lblQuantity.setText(rb.getString("Overview.lblQuantity.text"));
        lblLength1.setText(rb.getString("Overview.lblLength1.text"));
        lblWidth1.setText(rb.getString("Overview.lblWidth1.text"));
        lblQuantity2.setText(rb.getString("Overview.lblQuantity2.text"));
        lblSalesOrderId.setText(rb.getString("Overview.lblSalesOrderId.text"));
        lblCustomerName.setText(rb.getString("Overview.lblCustomerName.text"));
        lblDate.setText(rb.getString("Overview.lblDate.text"));
        lblThickness.setText(rb.getString("Overview.lblThickness.text"));
        lblWidth.setText(rb.getString("Overview.lblWidth.text"));
        lblOrder.setText(rb.getString("Overview.lblOrder.text"));
        lblLoggedIn.setText(rb.getString("Overview.lblLoggedIn.text"));
        lblPhone.setText(rb.getString("Overview.lblPhone.text"));
        lblEmail.setText(rb.getString("Overview.lblEmail.text"));
        lblSleeve.setText(rb.getString("Overview.lblSleeve.text"));
        lblThickness1.setText(rb.getString("Overview.lblThickness1.text"));
        lblCode.setText(rb.getString("Overview.lblCode.text"));
        lblDensity.setText(rb.getString("Overview.lblDensity.text"));
        lblName.setText(rb.getString("Overview.lblName.text"));
        lblMaterialID.setText(rb.getString("Overview.lblMaterialID.text"));       
        
        
    }
    
    private void loggedInAs()
    {
        String operator = Login.getInstance().getOperator();
        lblLoggedIn.setText("Logged in as " +operator);
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
        pnlOrder = new javax.swing.JPanel();
        pnlOrderList = new javax.swing.JPanel();
        scrOrderList = new javax.swing.JScrollPane();
        tblOrderList = new javax.swing.JTable();
        pnlOrderInfo = new javax.swing.JPanel();
        lblOrder = new javax.swing.JLabel();
        txtOrderId = new javax.swing.JTextField();
        lblQuantity = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        lblDate = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        pnlMeasurements = new javax.swing.JPanel();
        txtThickness = new javax.swing.JTextField();
        lblThickness = new javax.swing.JLabel();
        lblWidth = new javax.swing.JLabel();
        txtWidth = new javax.swing.JTextField();
        pnlCustomerInfo = new javax.swing.JPanel();
        lblSalesOrderId = new javax.swing.JLabel();
        lblCustomerName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        txtSalesOrderId = new javax.swing.JTextField();
        txtCustomerName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        pnlInstock = new javax.swing.JPanel();
        pnlInStockList = new javax.swing.JPanel();
        sclInStock = new javax.swing.JScrollPane();
        tblInStock = new javax.swing.JTable();
        JPanalStockInfo = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtMaterialName1 = new javax.swing.JTextField();
        lblMaterialID = new javax.swing.JLabel();
        txtMaterialID1 = new javax.swing.JTextField();
        lblCode = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        lblDensity = new javax.swing.JLabel();
        txtMaterialDenisity = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        txtThickness1 = new javax.swing.JTextField();
        lblThickness1 = new javax.swing.JLabel();
        lblWidth1 = new javax.swing.JLabel();
        txtWidth1 = new javax.swing.JTextField();
        lblLength1 = new javax.swing.JLabel();
        txtLength1 = new javax.swing.JTextField();
        lblStockQuantity = new javax.swing.JLabel();
        txtStockQuantity = new javax.swing.JTextField();
        lblKg = new javax.swing.JLabel();
        lblCharge = new javax.swing.JLabel();
        txtCharge = new javax.swing.JTextField();
        pnlCutting1 = new javax.swing.JPanel();
        scrSleeve = new javax.swing.JScrollPane();
        tblSleeveList = new javax.swing.JTable();
        pnlOrderStock = new javax.swing.JPanel();
        scrOrderStock = new javax.swing.JScrollPane();
        tblOrderList1 = new javax.swing.JTable();
        scrStockOrder = new javax.swing.JScrollPane();
        tblStockItem = new javax.swing.JTable();
        pnlControlPanel = new javax.swing.JPanel();
        btnFinishOrder = new javax.swing.JButton();
        rbtnUrgent = new javax.swing.JRadioButton();
        rbtnInProgress = new javax.swing.JRadioButton();
        rbtnPending = new javax.swing.JRadioButton();
        lblSleeve = new javax.swing.JLabel();
        pnlCutting2 = new javax.swing.JPanel();
        pnlCutting3 = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        localeLanguage = new com.toedter.components.JLocaleChooser();
        pnlLoggedIn = new javax.swing.JPanel();
        lblLoggedIn = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
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

        tblOrderList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String []
            {
                "Order List:"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }
        });
        scrOrderList.setViewportView(tblOrderList);

        javax.swing.GroupLayout pnlOrderListLayout = new javax.swing.GroupLayout(pnlOrderList);
        pnlOrderList.setLayout(pnlOrderListLayout);
        pnlOrderListLayout.setHorizontalGroup(
            pnlOrderListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrOrderList, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
        );
        pnlOrderListLayout.setVerticalGroup(
            pnlOrderListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrOrderList, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        pnlOrderInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.pnlOrderInfo.border.title"))); // NOI18N

        lblOrder.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblOrder.setText(bundle.getString("Overview.lblOrder.text")); // NOI18N

        txtOrderId.setEditable(false);

        lblQuantity.setText(bundle.getString("Overview.lblQuantity.text")); // NOI18N

        txtQuantity.setEditable(false);

        lblDate.setText(bundle.getString("Overview.lblDate.text")); // NOI18N

        txtDate.setEditable(false);

        pnlMeasurements.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.pnlMeasurements.border.title"))); // NOI18N

        txtThickness.setEditable(false);

        lblThickness.setText(bundle.getString("Overview.lblThickness.text")); // NOI18N

        lblWidth.setText(bundle.getString("Overview.lblWidth.text")); // NOI18N

        txtWidth.setEditable(false);

        javax.swing.GroupLayout pnlMeasurementsLayout = new javax.swing.GroupLayout(pnlMeasurements);
        pnlMeasurements.setLayout(pnlMeasurementsLayout);
        pnlMeasurementsLayout.setHorizontalGroup(
            pnlMeasurementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMeasurementsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMeasurementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblThickness)
                    .addComponent(lblWidth))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlMeasurementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtWidth, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                    .addComponent(txtThickness))
                .addContainerGap())
        );
        pnlMeasurementsLayout.setVerticalGroup(
            pnlMeasurementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMeasurementsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMeasurementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThickness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblThickness))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMeasurementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWidth)
                    .addComponent(txtWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pnlCustomerInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.pnlCustomerInfo.border.title"))); // NOI18N

        lblSalesOrderId.setText(bundle.getString("Overview.lblSalesOrderId.text")); // NOI18N

        lblCustomerName.setText(bundle.getString("Overview.lblCustomerName.text")); // NOI18N

        lblEmail.setText(bundle.getString("Overview.lblEmail.text")); // NOI18N

        lblPhone.setText(bundle.getString("Overview.lblPhone.text")); // NOI18N

        txtSalesOrderId.setEditable(false);

        txtCustomerName.setEditable(false);

        txtEmail.setEditable(false);

        txtPhone.setEditable(false);

        javax.swing.GroupLayout pnlCustomerInfoLayout = new javax.swing.GroupLayout(pnlCustomerInfo);
        pnlCustomerInfo.setLayout(pnlCustomerInfoLayout);
        pnlCustomerInfoLayout.setHorizontalGroup(
            pnlCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCustomerInfoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblSalesOrderId)
                        .addGap(18, 18, 18)
                        .addComponent(txtSalesOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCustomerInfoLayout.createSequentialGroup()
                        .addGroup(pnlCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmail)
                            .addComponent(lblCustomerName)
                            .addComponent(lblPhone))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                            .addComponent(txtPhone)
                            .addComponent(txtCustomerName, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        pnlCustomerInfoLayout.setVerticalGroup(
            pnlCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerInfoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSalesOrderId)
                    .addComponent(txtSalesOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomerName)
                    .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhone)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlOrderInfoLayout = new javax.swing.GroupLayout(pnlOrderInfo);
        pnlOrderInfo.setLayout(pnlOrderInfoLayout);
        pnlOrderInfoLayout.setHorizontalGroup(
            pnlOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCustomerInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMeasurements, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOrderInfoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnlOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDate)
                            .addComponent(lblOrder)
                            .addComponent(lblQuantity))
                        .addGap(18, 18, 18)
                        .addGroup(pnlOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtOrderId, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                            .addComponent(txtQuantity)
                            .addComponent(txtDate))))
                .addContainerGap())
        );
        pnlOrderInfoLayout.setVerticalGroup(
            pnlOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrder)
                    .addComponent(txtOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDate)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuantity)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlMeasurements, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlCustomerInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout pnlOrderLayout = new javax.swing.GroupLayout(pnlOrder);
        pnlOrder.setLayout(pnlOrderLayout);
        pnlOrderLayout.setHorizontalGroup(
            pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlOrderList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlOrderInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlOrderLayout.setVerticalGroup(
            pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlOrderInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlOrderList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab(bundle.getString("Overview.pnlOrder.TabConstraints.tabTitle"), pnlOrder); // NOI18N

        tblInStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String []
            {
                "In Stock list:"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }
        });
        sclInStock.setViewportView(tblInStock);

        javax.swing.GroupLayout pnlInStockListLayout = new javax.swing.GroupLayout(pnlInStockList);
        pnlInStockList.setLayout(pnlInStockListLayout);
        pnlInStockListLayout.setHorizontalGroup(
            pnlInStockListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInStockListLayout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(sclInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlInStockListLayout.setVerticalGroup(
            pnlInStockListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInStockListLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sclInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(213, 213, 213))
        );

        JPanalStockInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.JPanalStockInfo.border.title"))); // NOI18N
        JPanalStockInfo.setEnabled(false);

        lblName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblName.setText(bundle.getString("Overview.lblName.text")); // NOI18N

        txtMaterialName1.setEditable(false);

        lblMaterialID.setText(bundle.getString("Overview.lblMaterialID.text")); // NOI18N

        txtMaterialID1.setEditable(false);

        lblCode.setText(bundle.getString("Overview.lblCode.text")); // NOI18N

        txtCode.setEditable(false);

        lblDensity.setText(bundle.getString("Overview.lblDensity.text")); // NOI18N

        txtMaterialDenisity.setEditable(false);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.jPanel11.border.title"))); // NOI18N

        txtThickness1.setEditable(false);

        lblThickness1.setText(bundle.getString("Overview.lblThickness1.text")); // NOI18N

        lblWidth1.setText(bundle.getString("Overview.lblWidth1.text")); // NOI18N

        txtWidth1.setEditable(false);

        lblLength1.setText(bundle.getString("Overview.lblLength1.text")); // NOI18N

        txtLength1.setEditable(false);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblThickness1)
                    .addComponent(lblWidth1)
                    .addComponent(lblLength1))
                .addGap(27, 27, 27)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtWidth1)
                    .addComponent(txtThickness1)
                    .addComponent(txtLength1))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThickness1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblThickness1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWidth1)
                    .addComponent(txtWidth1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLength1)
                    .addComponent(txtLength1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        lblStockQuantity.setText(bundle.getString("Overview.lblStockQuantity.text")); // NOI18N

        txtStockQuantity.setEditable(false);
        txtStockQuantity.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                txtStockQuantityActionPerformed(evt);
            }
        });

        lblKg.setText(bundle.getString("Overview.lblKg.text")); // NOI18N

        lblCharge.setText(bundle.getString("Overview.lblCharge.text")); // NOI18N

        txtCharge.setEditable(false);

        javax.swing.GroupLayout JPanalStockInfoLayout = new javax.swing.GroupLayout(JPanalStockInfo);
        JPanalStockInfo.setLayout(JPanalStockInfoLayout);
        JPanalStockInfoLayout.setHorizontalGroup(
            JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanalStockInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JPanalStockInfoLayout.createSequentialGroup()
                        .addGroup(JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPanalStockInfoLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblName)
                                    .addComponent(lblMaterialID)
                                    .addComponent(lblCode)))
                            .addComponent(lblDensity)
                            .addComponent(lblStockQuantity)
                            .addComponent(lblCharge))
                        .addGap(18, 18, 18)
                        .addGroup(JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaterialID1)
                            .addComponent(txtCode)
                            .addComponent(txtMaterialName1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMaterialDenisity)
                            .addGroup(JPanalStockInfoLayout.createSequentialGroup()
                                .addComponent(txtStockQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblKg))
                            .addComponent(txtCharge))))
                .addContainerGap())
        );
        JPanalStockInfoLayout.setVerticalGroup(
            JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanalStockInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtMaterialName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaterialID)
                    .addComponent(txtMaterialID1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCode)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDensity)
                    .addComponent(txtMaterialDenisity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStockQuantity)
                    .addGroup(JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtStockQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblKg)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JPanalStockInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCharge)
                    .addComponent(txtCharge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlInstockLayout = new javax.swing.GroupLayout(pnlInstock);
        pnlInstock.setLayout(pnlInstockLayout);
        pnlInstockLayout.setHorizontalGroup(
            pnlInstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInstockLayout.createSequentialGroup()
                .addComponent(pnlInStockList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPanalStockInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );
        pnlInstockLayout.setVerticalGroup(
            pnlInstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInstockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JPanalStockInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlInstockLayout.createSequentialGroup()
                        .addComponent(pnlInStockList, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab(bundle.getString("Overview.pnlInstock.TabConstraints.tabTitle"), pnlInstock); // NOI18N

        tblSleeveList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String []
            {
                "Sleeve"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }
        });
        scrSleeve.setViewportView(tblSleeveList);

        pnlOrderStock.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.pnlOrderStock.border.title"))); // NOI18N

        tblOrderList1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String []
            {
                "Order List:"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }
        });
        scrOrderStock.setViewportView(tblOrderList1);

        tblStockItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String []
            {
                "Stock Item"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }
        });
        scrStockOrder.setViewportView(tblStockItem);

        pnlControlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.pnlControlPanel.border.title"))); // NOI18N

        btnFinishOrder.setText(bundle.getString("Overview.btnFinishOrder.text")); // NOI18N

        buttonGroup1.add(rbtnUrgent);
        rbtnUrgent.setText(bundle.getString("Overview.rbtnUrgent.text")); // NOI18N

        buttonGroup1.add(rbtnInProgress);
        rbtnInProgress.setText(bundle.getString("Overview.rbtnInProgress.text")); // NOI18N

        buttonGroup1.add(rbtnPending);
        rbtnPending.setText(bundle.getString("Overview.rbtnPending.text")); // NOI18N

        javax.swing.GroupLayout pnlControlPanelLayout = new javax.swing.GroupLayout(pnlControlPanel);
        pnlControlPanel.setLayout(pnlControlPanelLayout);
        pnlControlPanelLayout.setHorizontalGroup(
            pnlControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtnUrgent)
                    .addComponent(rbtnInProgress)
                    .addComponent(rbtnPending))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlPanelLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(btnFinishOrder)
                .addGap(25, 25, 25))
        );
        pnlControlPanelLayout.setVerticalGroup(
            pnlControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlPanelLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(rbtnPending)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnInProgress)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnUrgent)
                .addGap(18, 18, 18)
                .addComponent(btnFinishOrder)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout pnlOrderStockLayout = new javax.swing.GroupLayout(pnlOrderStock);
        pnlOrderStock.setLayout(pnlOrderStockLayout);
        pnlOrderStockLayout.setHorizontalGroup(
            pnlOrderStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderStockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrOrderStock, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrStockOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlControlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlOrderStockLayout.setVerticalGroup(
            pnlOrderStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderStockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOrderStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrStockOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlOrderStockLayout.createSequentialGroup()
                        .addGroup(pnlOrderStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrOrderStock, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlControlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(1, 1, 1))
        );

        lblSleeve.setText(bundle.getString("Overview.lblSleeve.text")); // NOI18N

        javax.swing.GroupLayout pnlCutting1Layout = new javax.swing.GroupLayout(pnlCutting1);
        pnlCutting1.setLayout(pnlCutting1Layout);
        pnlCutting1Layout.setHorizontalGroup(
            pnlCutting1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCutting1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCutting1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSleeve)
                    .addComponent(scrSleeve, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pnlOrderStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlCutting1Layout.setVerticalGroup(
            pnlCutting1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCutting1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblSleeve)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrSleeve, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlCutting1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlOrderStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(bundle.getString("Overview.pnlCutting1.TabConstraints.tabTitle"), pnlCutting1); // NOI18N

        javax.swing.GroupLayout pnlCutting2Layout = new javax.swing.GroupLayout(pnlCutting2);
        pnlCutting2.setLayout(pnlCutting2Layout);
        pnlCutting2Layout.setHorizontalGroup(
            pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 844, Short.MAX_VALUE)
        );
        pnlCutting2Layout.setVerticalGroup(
            pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 406, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(bundle.getString("Overview.pnlCutting2.TabConstraints.tabTitle"), pnlCutting2); // NOI18N

        javax.swing.GroupLayout pnlCutting3Layout = new javax.swing.GroupLayout(pnlCutting3);
        pnlCutting3.setLayout(pnlCutting3Layout);
        pnlCutting3Layout.setHorizontalGroup(
            pnlCutting3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 844, Short.MAX_VALUE)
        );
        pnlCutting3Layout.setVerticalGroup(
            pnlCutting3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 406, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(bundle.getString("Overview.pnlCutting3.TabConstraints.tabTitle"), pnlCutting3); // NOI18N

        btnClose.setText(bundle.getString("Overview.btnClose.text")); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCloseActionPerformed(evt);
            }
        });

        localeLanguage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dansk (Danmark)", "English (United Kingdom)", "Russian (Russia)", "Irish (Ireland)" }));
        localeLanguage.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                localeLanguageActionPerformed(evt);
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
                .addGap(0, 147, Short.MAX_VALUE))
        );
        pnlLoggedInLayout.setVerticalGroup(
            pnlLoggedInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLoggedIn)
        );

        btnLogout.setText(bundle.getString("Overview.btnLogout.text")); // NOI18N
        btnLogout.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnLogoutActionPerformed(evt);
            }
        });

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlLoggedIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(localeLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 3, Short.MAX_VALUE)
                        .addComponent(localeLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlLoggedIn, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnLogout))
                .addGap(5, 5, 5))
        );

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

    private void txtStockQuantityActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_txtStockQuantityActionPerformed
    {//GEN-HEADEREND:event_txtStockQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockQuantityActionPerformed

    private void localeLanguageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_localeLanguageActionPerformed
    {//GEN-HEADEREND:event_localeLanguageActionPerformed
        rb = ResourceBundle.getBundle("GUI.Bundle", localeLanguage.getLocale());
        updateGUILanguage();
    }//GEN-LAST:event_localeLanguageActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnLogoutActionPerformed
    {//GEN-HEADEREND:event_btnLogoutActionPerformed
        logoutPressed();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void itemLogOutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemLogOutActionPerformed
    {//GEN-HEADEREND:event_itemLogOutActionPerformed
        logoutPressed();
    }//GEN-LAST:event_itemLogOutActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanalStockInfo;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnFinishOrder;
    private javax.swing.JButton btnLogout;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenuItem itemExit;
    private javax.swing.JMenuItem itemHelp;
    private javax.swing.JMenuItem itemLogOut;
    private javax.swing.JMenuItem itemSettings;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCharge;
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDensity;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblKg;
    private javax.swing.JLabel lblLength1;
    private javax.swing.JLabel lblLoggedIn;
    private javax.swing.JLabel lblMaterialID;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblOrder;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblSalesOrderId;
    private javax.swing.JLabel lblSleeve;
    private javax.swing.JLabel lblStockQuantity;
    private javax.swing.JLabel lblThickness;
    private javax.swing.JLabel lblThickness1;
    private javax.swing.JLabel lblWidth;
    private javax.swing.JLabel lblWidth1;
    private com.toedter.components.JLocaleChooser localeLanguage;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuSettings;
    private javax.swing.JPanel pnlControlPanel;
    private javax.swing.JPanel pnlCustomerInfo;
    private javax.swing.JPanel pnlCutting1;
    private javax.swing.JPanel pnlCutting2;
    private javax.swing.JPanel pnlCutting3;
    private javax.swing.JPanel pnlInStockList;
    private javax.swing.JPanel pnlInstock;
    private javax.swing.JPanel pnlLoggedIn;
    private javax.swing.JPanel pnlMeasurements;
    private javax.swing.JPanel pnlOrder;
    private javax.swing.JPanel pnlOrderInfo;
    private javax.swing.JPanel pnlOrderList;
    private javax.swing.JPanel pnlOrderStock;
    private javax.swing.JRadioButton rbtnInProgress;
    private javax.swing.JRadioButton rbtnPending;
    private javax.swing.JRadioButton rbtnUrgent;
    private javax.swing.JScrollPane sclInStock;
    private javax.swing.JScrollPane scrOrderList;
    private javax.swing.JScrollPane scrOrderStock;
    private javax.swing.JScrollPane scrSleeve;
    private javax.swing.JScrollPane scrStockOrder;
    private javax.swing.JPopupMenu.Separator seperatorSettings;
    private javax.swing.JTable tblInStock;
    private javax.swing.JTable tblOrderList;
    private javax.swing.JTable tblOrderList1;
    private javax.swing.JTable tblSleeveList;
    private javax.swing.JTable tblStockItem;
    private javax.swing.JTextField txtCharge;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLength1;
    private javax.swing.JTextField txtMaterialDenisity;
    private javax.swing.JTextField txtMaterialID1;
    private javax.swing.JTextField txtMaterialName1;
    private javax.swing.JTextField txtOrderId;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSalesOrderId;
    private javax.swing.JTextField txtStockQuantity;
    private javax.swing.JTextField txtThickness;
    private javax.swing.JTextField txtThickness1;
    private javax.swing.JTextField txtWidth;
    private javax.swing.JTextField txtWidth1;
    // End of variables declaration//GEN-END:variables

    private void orderListSelectioner()
    {
        try
        {
            omgr = OrderManager.getInstance();
            omgr.addObserver(this);

            omodel = new OrderTablemodel(omgr.getAll());
            tblOrderList.setModel(omodel);
            tblOrderList.getSelectionModel().addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent es)
                {
                    int selectedRow = tblOrderList.getSelectedRow();
                    if (es.getValueIsAdjusting() || selectedRow < 0)
                    {
                        txtOrderId.setText("");
                        txtDate.setText("");
                        txtQuantity.setText("");
                        txtThickness.setText("");
                        txtWidth.setText("");
                        txtSalesOrderId.setText("");
                        txtCustomerName.setText("");
                        txtEmail.setText("");
                        txtPhone.setText("");

                    }

                    Order o = omodel.getEventsByRow(selectedRow);

                    try
                    {
                        txtOrderId.setText(String.valueOf(o.getOrderId()));
                        txtDate.setText(String.valueOf(o.printDate(o.getDueDate())));
                        txtQuantity.setText(String.valueOf(o.getQuantity()));
                        txtThickness.setText(String.valueOf(o.getThickness()));
                        txtWidth.setText(String.valueOf(o.getWidth()));
                        txtSalesOrderId.setText(String.valueOf(o.getSalesOrder().getsOrderId()));
                        txtCustomerName.setText(String.valueOf(o.getSalesOrder().getCustName()));
                        txtEmail.setText(String.valueOf(o.getSalesOrder().getEmail()));
                        txtPhone.setText(String.valueOf(o.getSalesOrder().getPhone()));

//                        switch (o.getType())
//                        {
//                            case START:
//                                rbtnStart.setSelected(true);
//                                break;
//                            case PAUSE:
//                                rbtnPause.setSelected(true);
//                                break;
//                            case AFSLUT:
//                                rbtnAfslut.setSelected(true);
//                                break;
//                            default:
//                                rbtnProgress.setSelected(true);
//                        }
                    }
                    catch (Exception ex)
                    {
                    }
                }
            });
        }
        catch (Exception e)
        {
        }
    }

    private void sleeveListSelectioner()
    {
        try
        {
            slmgr = SleeveManager.getInstance();
            slmgr.addObserver(this);
            slmodel = new SleeveTableModel(slmgr.getAll());
            tblSleeveList.setModel(slmodel);

            smgr = StockItemManager.getInstance();
            smgr.addObserver(this);

            omgr = OrderManager.getInstance();
            omgr.addObserver(this);


//          omodel2 = new OrderTablemodel(omgr.getAll());
//          tblOrderList1.setModel(omodel2);


            tblSleeveList.getSelectionModel().addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent es)
                {
                    int selectedRow = tblSleeveList.getSelectedRow();
                    Sleeve s = slmodel.getEventsByRow(selectedRow);
                    try
                    {
                        if (!omgr.getOrdersBySleeve(s).isEmpty())
                        {
                            omodel2 = new OrderTablemodel(omgr.getOrdersBySleeve(s));
                            tblOrderList1.setModel(omodel2);

                            if (!smgr.getItemBySleeve(s).isEmpty())
                            {
                                smodel2 = new StockListTableModel(smgr.getItemBySleeve(s));
                                tblStockItem.setModel(smodel2);
                            }

//                       tblOrderList1.getSelectionModel().addListSelectionListener(new ListSelect);                        
                        }
//                    else
//                    {
////                        omodel2.
//                        tblOrderList1.setModel(omodel2);
//                    }

//                    Sleeve s = slmodel.getEventsByRow(selectedRow);
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

    private void stockItemListSelectioner()
    {
        try
        {
            smgr = StockItemManager.getInstance();
            smgr.addObserver(this);

            smodel = new StockListTableModel(smgr.getAll());
            tblInStock.setModel(smodel);
            tblInStock.getSelectionModel().addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent es)
                {
                    int selectedRow = tblInStock.getSelectedRow();
                    if (es.getValueIsAdjusting() || selectedRow < 0)
                    {
                        txtMaterialName1.setText("");
                        txtMaterialID1.setText("");
                        txtCode.setText("");
                        txtMaterialDenisity.setText("");
                        txtStockQuantity.setText("");
                        txtCharge.setText("");
                        txtThickness1.setText("");
                        txtWidth1.setText("");
                        txtLength1.setText("");

                    }

                    StockItem s = smodel.getEventsByRow(selectedRow);

                    try
                    {
                        txtMaterialName1.setText(String.valueOf(s.getMaterial().getName()));
                        txtMaterialID1.setText(String.valueOf(s.getCoilType().getMaterialId()));
                        txtCode.setText(String.valueOf(s.getCoilType().getCode()));
                        txtMaterialDenisity.setText(String.valueOf(s.getMaterial().getDensity()));
                        txtStockQuantity.setText(String.valueOf(s.getStockQuantity()));
                        txtCharge.setText(String.valueOf(s.getChargeNo()));
                        txtThickness1.setText(String.valueOf(s.getCoilType().getThickness()));
                        txtWidth1.setText(String.valueOf(s.getCoilType().getWidth()));
                        txtLength1.setText(String.valueOf(s.getLength()));

                    }
                    catch (Exception ex)
                    {
                    }
                }
            });
        }
        catch (Exception e)
        {
        }
    }
}
