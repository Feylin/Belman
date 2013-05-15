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
import GUI.Models.ProductionSleeveTableModel;
import GUI.Models.SleeveTableModel;
import GUI.Models.StockList2TableModel;
import GUI.Models.StockListTableModel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.SwingUtilities;
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
    private OrderTablemodel omodel = null;
    private static Overview instance = null;
    private StockListTableModel smodel = null;
    private MaterialTableModel mmodel = null;
    private OrderTablemodel omodel2 = null;
    private SleeveTableModel slmodel = null;
    private ResourceBundle rb = null;
    private StockListTableModel smodel2 = null;
    private ProductionSleeveTableModel psmodel = null;
    private StockList2TableModel smodel3 = null;
    private int clickCount;
    private boolean doubleClick;
    private boolean leftClick;
    
    
    
    Order o;
    private Timer timer;

    /**
     * Creates new form Overview
     */
    private Overview()
    {
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("GUI.Bundle");
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/icons/belman.png")).getImage());
//        loggedInAs();
        windowClose();
        setLocationRelativeTo(null); 
        productionSleeveListSelectioner();
        orderListSelectioner();
        localeLanguage.setLocale(locale);
        mouseListener();

       
        updateGUILanguage();
       
            
        
        tblProductionSleeve.getColumnModel().getColumn(0).setPreferredWidth(190);
        tblProductionSleeve.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblProductionSleeve.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(6).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(7).setPreferredWidth(100);
        
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

        lblKg.setText(rb.getString("Overview.jLabel6.text"));
        
        jTabbedPane1.setTitleAt(0, rb.getString("Overview.pnlOrder.TabConstraints.tabTitle"));
        jTabbedPane1.setTitleAt(1, rb.getString("Overview.pnlInstock.TabConstraints.tabTitle"));
        jTabbedPane1.setTitleAt(2, rb.getString("Overview.pnlCutting1.TabConstraints.tabTitle"));
        jTabbedPane1.setTitleAt(3, rb.getString("Overview.pnlCutting2.TabConstraints.tabTitle"));
        jTabbedPane1.setTitleAt(4, rb.getString("Overview.pnlCutting3.TabConstraints.tabTitle"));

        TitledBorder border = (TitledBorder) pnlControlPanel.getBorder();
        border.setTitle(rb.getString("Overview.pnlControlPanel.border.title"));
        TitledBorder border2 = (TitledBorder) pnlMeasurements2.getBorder();
        border2.setTitle(rb.getString("Overview.pnlMeasurements2.border.title"));
        TitledBorder border3 = (TitledBorder) pnlMeasurements.getBorder();
        border3.setTitle(rb.getString("Overview.pnlMeasurements.border.title"));
        TitledBorder border4 = (TitledBorder) pnlCustomerInfo.getBorder();
        border4.setTitle(rb.getString("Overview.pnlCustomerInfo.border.title"));
        TitledBorder border5 = (TitledBorder) pnlOrderInfo.getBorder();
        border5.setTitle(rb.getString("Overview.pnlOrderInfo.border.title"));
        TitledBorder border6 = (TitledBorder) pnlOrderStock.getBorder();
        border6.setTitle(rb.getString("Overview.pnlOrderStock.border.title"));
        TitledBorder border7 = (TitledBorder) pnlOrderStock.getBorder();
        border7.setTitle(rb.getString("Overview.pnlControlPanel1.border.title"));
        

        rbtnUrgent.setText(rb.getString("Overview.rbtnUrgent.text"));
        rbtnPending.setText(rb.getString("Overview.rbtnPending.text"));
        rbtnInProgress.setText(rb.getString("Overview.rbtnInProgress.text"));
        rbtnContinue.setText(rb.getString("Overview.rbtnContinue.text"));
        rbtnInPauseAgain.setText(rb.getString("Overview.rbtnInPauseAgain.text"));
        rbtnFinish.setText(rb.getString("Overview.rbtnFinish.text"));
               
        itemHelp.setText(rb.getString("Overview.itemHelp.text_1"));
        itemSettings.setText(rb.getString("Overview.itemSettings.text"));
        itemLogOut.setText(rb.getString("Overview.itemLogOut.text_1"));
        itemExit.setText(rb.getString("Overview.itemExit.text"));

        lblCharge.setText(rb.getString("Overview.lblCharge.text"));
        lblQuantity.setText(rb.getString("Overview.lblQuantity.text"));
        lblLength1.setText(rb.getString("Overview.lblLength1.text"));
        lblWidth1.setText(rb.getString("Overview.lblWidth1.text"));
        lblStockQuantity.setText(rb.getString("Overview.lblStockQuantity.text"));
        lblSalesOrderId.setText(rb.getString("Overview.lblSalesOrderId.text"));
        lblCustomerName.setText(rb.getString("Overview.lblCustomerName.text"));
        lblDate.setText(rb.getString("Overview.lblDate.text"));
        lblThickness.setText(rb.getString("Overview.lblThickness.text"));
        lblWidth.setText(rb.getString("Overview.lblWidth.text"));
        lblOrder.setText(rb.getString("Overview.lblOrder.text"));
        lblLoggedIn.setText(rb.getString("Overview.lblLoggedIn.text") + Login.getInstance().getOperator());
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
        lblLoggedIn.setText("Logged in as " + operator);
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
          
            tblStockList2.setModel(smodel2);
            

            omgr = OrderManager.getInstance();
            omgr.addObserver(this);
            omodel2 = new OrderTablemodel(omgr.getAll());
            tblOrderList1.setModel(omodel2);


//          omodel2 = new OrderTablemodel(omgr.getAll());
//          tblOrderList1.setModel(omodel2);

            tblSleeveList.getSelectionModel().addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent es)
                {
                    int selectedRow = tblSleeveList.getSelectedRow();
                    if (selectedRow == -1)
                    {
                        return;
                    }
                    
                    Sleeve s = slmodel.getEventsByRow(selectedRow);
                    
                    try
                    {
                        if (!omgr.getOrdersBySleeve(s).isEmpty())
                        {
                            omodel2 = new OrderTablemodel(omgr.getOrdersBySleeve(s));
                            tblOrderList1.setModel(omodel2);

                            if (!smgr.getItemBySleeve(s).isEmpty())
                            {
                              
                                tblStockList2.setModel(smodel2);
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
                            smodel2 = new StockListTableModel();            
                            tblStockList3.setModel(smodel2);
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
                    OrderInfo.getInstance().setVisible(true);
                }
            }
        });
    }
    
    // SKAL MÅSKE BRUGES!!!!!!!!
//    
//      public void mouseClicked(MouseEvent evt) { 
//    if (evt.getButton()==MouseEvent.BUTTON1){
//        leftClick = true; clickCount = 0;
//        if(evt.getClickCount() == 2) doubleClick=true;
//        Integer timerinterval = (Integer)Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");
//
//                   timer = new Timer(timerinterval, new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {  
//                        if(doubleClick){
//                           
//                            clickCount++;
//                            if(clickCount == 2){
//                                rfbProto.capture();
//                                clickCount=0;
//                                doubleClick = false;
//                            }
//
//                        } else {
//
//                            sb = new StringBuffer();
//                            sb.append("Left Mouse");
//                            System.out.println("single click.");
//                            rfbProto.capture();
//                        }
//                    }               
//                });
//                timer.setRepeats(false);
//                timer.start();
//                if(evt.getID()==MouseEvent.MOUSE_RELEASED) timer.stop();
//    }           
//    }

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
        tblStockList = new javax.swing.JTable();
        JPanalStockInfo = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtMaterialName1 = new javax.swing.JTextField();
        lblMaterialID = new javax.swing.JLabel();
        txtMaterialID1 = new javax.swing.JTextField();
        lblCode = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        lblDensity = new javax.swing.JLabel();
        txtMaterialDenisity = new javax.swing.JTextField();
        pnlMeasurements2 = new javax.swing.JPanel();
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
        tblStockList2 = new javax.swing.JTable();
        pnlControlPanel = new javax.swing.JPanel();
        btnFinishOrder = new javax.swing.JButton();
        rbtnUrgent = new javax.swing.JRadioButton();
        rbtnInProgress = new javax.swing.JRadioButton();
        rbtnPending = new javax.swing.JRadioButton();
        lblSleeve = new javax.swing.JLabel();
        pnlCutting2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductionSleeve = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStockList3 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlCutting3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        scrOrderList1 = new javax.swing.JScrollPane();
        tblOrderList2 = new javax.swing.JTable();
        JPanalStockInfo1 = new javax.swing.JPanel();
        lblName1 = new javax.swing.JLabel();
        txtMaterialName2 = new javax.swing.JTextField();
        lblMaterialID1 = new javax.swing.JLabel();
        txtMaterialID2 = new javax.swing.JTextField();
        lblCode1 = new javax.swing.JLabel();
        txtCode1 = new javax.swing.JTextField();
        lblDensity1 = new javax.swing.JLabel();
        txtMaterialDenisity1 = new javax.swing.JTextField();
        pnlMeasurements3 = new javax.swing.JPanel();
        txtThickness2 = new javax.swing.JTextField();
        lblThickness2 = new javax.swing.JLabel();
        lblWidth2 = new javax.swing.JLabel();
        txtWidth2 = new javax.swing.JTextField();
        lblLength2 = new javax.swing.JLabel();
        txtLength2 = new javax.swing.JTextField();
        lblStockQuantity1 = new javax.swing.JLabel();
        txtStockQuantity1 = new javax.swing.JTextField();
        lblKg1 = new javax.swing.JLabel();
        lblCharge1 = new javax.swing.JLabel();
        txtCharge1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        pnlControlPanel1 = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        rbtnFinish = new javax.swing.JRadioButton();
        rbtnInPauseAgain = new javax.swing.JRadioButton();
        rbtnContinue = new javax.swing.JRadioButton();
        pnlOrderInfo1 = new javax.swing.JPanel();
        lblOrder1 = new javax.swing.JLabel();
        txtOrderId1 = new javax.swing.JTextField();
        lblQuantity1 = new javax.swing.JLabel();
        txtQuantity1 = new javax.swing.JTextField();
        lblDate1 = new javax.swing.JLabel();
        txtDate1 = new javax.swing.JTextField();
        pnlMeasurements1 = new javax.swing.JPanel();
        txtThickness3 = new javax.swing.JTextField();
        lblThickness3 = new javax.swing.JLabel();
        lblWidth3 = new javax.swing.JLabel();
        txtWidth3 = new javax.swing.JTextField();
        pnlCustomerInfo1 = new javax.swing.JPanel();
        lblSalesOrderId1 = new javax.swing.JLabel();
        lblCustomerName1 = new javax.swing.JLabel();
        lblEmail1 = new javax.swing.JLabel();
        lblPhone1 = new javax.swing.JLabel();
        txtSalesOrderId1 = new javax.swing.JTextField();
        txtCustomerName1 = new javax.swing.JTextField();
        txtEmail1 = new javax.swing.JTextField();
        txtPhone1 = new javax.swing.JTextField();
        btnClose = new javax.swing.JButton();
        pnlLoggedIn = new javax.swing.JPanel();
        lblLoggedIn = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        btnReset1 = new javax.swing.JButton();
        localeLanguage = new com.toedter.components.JLocaleChooser();
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
            .addComponent(scrOrderList, javax.swing.GroupLayout.DEFAULT_SIZE, 957, Short.MAX_VALUE)
        );
        pnlOrderListLayout.setVerticalGroup(
            pnlOrderListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrOrderList)
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
                        .addComponent(lblSalesOrderId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
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
                .addContainerGap(197, Short.MAX_VALUE))
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
                .addComponent(pnlCustomerInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlOrderLayout = new javax.swing.GroupLayout(pnlOrder);
        pnlOrder.setLayout(pnlOrderLayout);
        pnlOrderLayout.setHorizontalGroup(
            pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlOrderList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlOrderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlOrderLayout.setVerticalGroup(
            pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlOrderInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlOrderList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab(bundle.getString("Overview.pnlOrder.TabConstraints.tabTitle"), pnlOrder); // NOI18N

        tblStockList.setModel(new javax.swing.table.DefaultTableModel(
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
        sclInStock.setViewportView(tblStockList);

        javax.swing.GroupLayout pnlInStockListLayout = new javax.swing.GroupLayout(pnlInStockList);
        pnlInStockList.setLayout(pnlInStockListLayout);
        pnlInStockListLayout.setHorizontalGroup(
            pnlInStockListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInStockListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sclInStock, javax.swing.GroupLayout.DEFAULT_SIZE, 951, Short.MAX_VALUE))
        );
        pnlInStockListLayout.setVerticalGroup(
            pnlInStockListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInStockListLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sclInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
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

        pnlMeasurements2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.pnlMeasurements2.border.title"))); // NOI18N

        txtThickness1.setEditable(false);

        lblThickness1.setText(bundle.getString("Overview.lblThickness1.text")); // NOI18N

        lblWidth1.setText(bundle.getString("Overview.lblWidth1.text")); // NOI18N

        txtWidth1.setEditable(false);

        lblLength1.setText(bundle.getString("Overview.lblLength1.text")); // NOI18N

        txtLength1.setEditable(false);

        javax.swing.GroupLayout pnlMeasurements2Layout = new javax.swing.GroupLayout(pnlMeasurements2);
        pnlMeasurements2.setLayout(pnlMeasurements2Layout);
        pnlMeasurements2Layout.setHorizontalGroup(
            pnlMeasurements2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMeasurements2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMeasurements2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblThickness1)
                    .addComponent(lblWidth1)
                    .addComponent(lblLength1))
                .addGap(27, 27, 27)
                .addGroup(pnlMeasurements2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtWidth1)
                    .addComponent(txtThickness1)
                    .addComponent(txtLength1))
                .addContainerGap())
        );
        pnlMeasurements2Layout.setVerticalGroup(
            pnlMeasurements2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMeasurements2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMeasurements2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThickness1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblThickness1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMeasurements2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWidth1)
                    .addComponent(txtWidth1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMeasurements2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                    .addComponent(pnlMeasurements2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                .addComponent(txtStockQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
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
                .addComponent(pnlMeasurements2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlInstockLayout = new javax.swing.GroupLayout(pnlInstock);
        pnlInstock.setLayout(pnlInstockLayout);
        pnlInstockLayout.setHorizontalGroup(
            pnlInstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInstockLayout.createSequentialGroup()
                .addComponent(pnlInStockList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JPanalStockInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlInstockLayout.setVerticalGroup(
            pnlInstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInstockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInstockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JPanalStockInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlInStockList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        tblStockList2.setModel(new javax.swing.table.DefaultTableModel(
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
        scrStockOrder.setViewportView(tblStockList2);

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(scrOrderStock, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(scrStockOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlControlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlOrderStockLayout.setVerticalGroup(
            pnlOrderStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderStockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOrderStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrStockOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                    .addComponent(scrOrderStock))
                .addGap(1, 1, 1))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOrderStockLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlControlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblSleeve.setText(bundle.getString("Overview.lblSleeve.text")); // NOI18N

        javax.swing.GroupLayout pnlCutting1Layout = new javax.swing.GroupLayout(pnlCutting1);
        pnlCutting1.setLayout(pnlCutting1Layout);
        pnlCutting1Layout.setHorizontalGroup(
            pnlCutting1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCutting1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCutting1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCutting1Layout.createSequentialGroup()
                        .addComponent(lblSleeve)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(scrSleeve, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlOrderStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlCutting1Layout.setVerticalGroup(
            pnlCutting1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCutting1Layout.createSequentialGroup()
                .addGroup(pnlCutting1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCutting1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblSleeve)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrSleeve))
                    .addGroup(pnlCutting1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlOrderStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab(bundle.getString("Overview.pnlCutting1.TabConstraints.tabTitle"), pnlCutting1); // NOI18N

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

        javax.swing.GroupLayout pnlCutting3Layout = new javax.swing.GroupLayout(pnlCutting3);
        pnlCutting3.setLayout(pnlCutting3Layout);
        pnlCutting3Layout.setHorizontalGroup(
            pnlCutting3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1537, Short.MAX_VALUE)
        );
        pnlCutting3Layout.setVerticalGroup(
            pnlCutting3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 595, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(bundle.getString("Overview.pnlCutting3.TabConstraints.tabTitle"), pnlCutting3); // NOI18N

        tblOrderList2.setModel(new javax.swing.table.DefaultTableModel(
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
        scrOrderList1.setViewportView(tblOrderList2);

        JPanalStockInfo1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.JPanalStockInfo1.border.title"))); // NOI18N
        JPanalStockInfo1.setEnabled(false);

        lblName1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblName1.setText(bundle.getString("Overview.lblName1.text")); // NOI18N

        txtMaterialName2.setEditable(false);

        lblMaterialID1.setText(bundle.getString("Overview.lblMaterialID1.text")); // NOI18N

        txtMaterialID2.setEditable(false);

        lblCode1.setText(bundle.getString("Overview.lblCode1.text")); // NOI18N

        txtCode1.setEditable(false);

        lblDensity1.setText(bundle.getString("Overview.lblDensity1.text")); // NOI18N

        txtMaterialDenisity1.setEditable(false);

        pnlMeasurements3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.pnlMeasurements3.border.title"))); // NOI18N

        txtThickness2.setEditable(false);

        lblThickness2.setText(bundle.getString("Overview.lblThickness2.text")); // NOI18N

        lblWidth2.setText(bundle.getString("Overview.lblWidth2.text")); // NOI18N

        txtWidth2.setEditable(false);

        lblLength2.setText(bundle.getString("Overview.lblLength2.text")); // NOI18N

        txtLength2.setEditable(false);

        javax.swing.GroupLayout pnlMeasurements3Layout = new javax.swing.GroupLayout(pnlMeasurements3);
        pnlMeasurements3.setLayout(pnlMeasurements3Layout);
        pnlMeasurements3Layout.setHorizontalGroup(
            pnlMeasurements3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMeasurements3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMeasurements3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblThickness2)
                    .addComponent(lblWidth2)
                    .addComponent(lblLength2))
                .addGap(27, 27, 27)
                .addGroup(pnlMeasurements3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtWidth2)
                    .addComponent(txtThickness2)
                    .addComponent(txtLength2))
                .addContainerGap())
        );
        pnlMeasurements3Layout.setVerticalGroup(
            pnlMeasurements3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMeasurements3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMeasurements3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThickness2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblThickness2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMeasurements3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWidth2)
                    .addComponent(txtWidth2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMeasurements3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLength2)
                    .addComponent(txtLength2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        lblStockQuantity1.setText(bundle.getString("Overview.lblStockQuantity1.text")); // NOI18N

        txtStockQuantity1.setEditable(false);
        txtStockQuantity1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                txtStockQuantity1ActionPerformed(evt);
            }
        });

        lblKg1.setText(bundle.getString("Overview.lblKg1.text")); // NOI18N

        lblCharge1.setText(bundle.getString("Overview.lblCharge1.text")); // NOI18N

        txtCharge1.setEditable(false);

        javax.swing.GroupLayout JPanalStockInfo1Layout = new javax.swing.GroupLayout(JPanalStockInfo1);
        JPanalStockInfo1.setLayout(JPanalStockInfo1Layout);
        JPanalStockInfo1Layout.setHorizontalGroup(
            JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanalStockInfo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlMeasurements3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(JPanalStockInfo1Layout.createSequentialGroup()
                        .addGroup(JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPanalStockInfo1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblName1)
                                    .addComponent(lblMaterialID1)
                                    .addComponent(lblCode1)))
                            .addComponent(lblDensity1)
                            .addComponent(lblStockQuantity1)
                            .addComponent(lblCharge1))
                        .addGap(18, 18, 18)
                        .addGroup(JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaterialID2)
                            .addComponent(txtCode1)
                            .addComponent(txtMaterialName2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMaterialDenisity1)
                            .addGroup(JPanalStockInfo1Layout.createSequentialGroup()
                                .addComponent(txtStockQuantity1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblKg1))
                            .addComponent(txtCharge1))))
                .addContainerGap())
        );
        JPanalStockInfo1Layout.setVerticalGroup(
            JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanalStockInfo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName1)
                    .addComponent(txtMaterialName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaterialID1)
                    .addComponent(txtMaterialID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCode1)
                    .addComponent(txtCode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDensity1)
                    .addComponent(txtMaterialDenisity1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStockQuantity1)
                    .addGroup(JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtStockQuantity1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblKg1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JPanalStockInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCharge1)
                    .addComponent(txtCharge1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(pnlMeasurements3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(scrOrderList1, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPanalStockInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrOrderList1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(JPanalStockInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText(bundle.getString("Overview.jLabel3.text")); // NOI18N

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.setText(bundle.getString("Overview.jTextField1.text")); // NOI18N

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField2.setText(bundle.getString("Overview.jTextField2.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 669, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(404, 404, 404)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(233, Short.MAX_VALUE))
        );

        pnlControlPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.pnlControlPanel1.border.title"))); // NOI18N

        btnOk.setText(bundle.getString("Overview.btnOk.text")); // NOI18N

        buttonGroup1.add(rbtnFinish);
        rbtnFinish.setText(bundle.getString("Overview.rbtnFinish.text")); // NOI18N
        rbtnFinish.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                rbtnFinishActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnInPauseAgain);
        rbtnInPauseAgain.setText(bundle.getString("Overview.rbtnInPauseAgain.text")); // NOI18N

        buttonGroup1.add(rbtnContinue);
        rbtnContinue.setText(bundle.getString("Overview.rbtnContinue.text")); // NOI18N

        javax.swing.GroupLayout pnlControlPanel1Layout = new javax.swing.GroupLayout(pnlControlPanel1);
        pnlControlPanel1.setLayout(pnlControlPanel1Layout);
        pnlControlPanel1Layout.setHorizontalGroup(
            pnlControlPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControlPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlControlPanel1Layout.createSequentialGroup()
                        .addComponent(rbtnFinish)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(btnOk))
                    .addGroup(pnlControlPanel1Layout.createSequentialGroup()
                        .addGroup(pnlControlPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rbtnInPauseAgain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbtnContinue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        pnlControlPanel1Layout.setVerticalGroup(
            pnlControlPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbtnContinue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtnInPauseAgain)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlControlPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnFinish)
                    .addComponent(btnOk))
                .addGap(56, 56, 56))
        );

        pnlOrderInfo1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.pnlOrderInfo1.border.title"))); // NOI18N

        lblOrder1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblOrder1.setText(bundle.getString("Overview.lblOrder1.text")); // NOI18N

        txtOrderId1.setEditable(false);

        lblQuantity1.setText(bundle.getString("Overview.lblQuantity1.text")); // NOI18N

        txtQuantity1.setEditable(false);

        lblDate1.setText(bundle.getString("Overview.lblDate1.text")); // NOI18N

        txtDate1.setEditable(false);

        pnlMeasurements1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.pnlMeasurements1.border.title"))); // NOI18N

        txtThickness3.setEditable(false);

        lblThickness3.setText(bundle.getString("Overview.lblThickness3.text")); // NOI18N

        lblWidth3.setText(bundle.getString("Overview.lblWidth3.text")); // NOI18N

        txtWidth3.setEditable(false);

        javax.swing.GroupLayout pnlMeasurements1Layout = new javax.swing.GroupLayout(pnlMeasurements1);
        pnlMeasurements1.setLayout(pnlMeasurements1Layout);
        pnlMeasurements1Layout.setHorizontalGroup(
            pnlMeasurements1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMeasurements1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMeasurements1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblThickness3)
                    .addComponent(lblWidth3))
                .addGap(26, 26, 26)
                .addGroup(pnlMeasurements1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtThickness3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWidth3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMeasurements1Layout.setVerticalGroup(
            pnlMeasurements1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMeasurements1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMeasurements1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThickness3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblThickness3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMeasurements1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWidth3)
                    .addComponent(txtWidth3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pnlCustomerInfo1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("Overview.pnlCustomerInfo1.border.title"))); // NOI18N

        lblSalesOrderId1.setText(bundle.getString("Overview.lblSalesOrderId1.text")); // NOI18N

        lblCustomerName1.setText(bundle.getString("Overview.lblCustomerName1.text")); // NOI18N

        lblEmail1.setText(bundle.getString("Overview.lblEmail1.text")); // NOI18N

        lblPhone1.setText(bundle.getString("Overview.lblPhone1.text")); // NOI18N

        txtSalesOrderId1.setEditable(false);

        txtCustomerName1.setEditable(false);

        txtEmail1.setEditable(false);

        txtPhone1.setEditable(false);

        javax.swing.GroupLayout pnlCustomerInfo1Layout = new javax.swing.GroupLayout(pnlCustomerInfo1);
        pnlCustomerInfo1.setLayout(pnlCustomerInfo1Layout);
        pnlCustomerInfo1Layout.setHorizontalGroup(
            pnlCustomerInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerInfo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCustomerInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmail1)
                    .addComponent(lblCustomerName1)
                    .addComponent(lblPhone1)
                    .addComponent(lblSalesOrderId1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCustomerInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSalesOrderId1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(txtEmail1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPhone1)
                    .addComponent(txtCustomerName1))
                .addGap(10, 10, 10))
        );
        pnlCustomerInfo1Layout.setVerticalGroup(
            pnlCustomerInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerInfo1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlCustomerInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSalesOrderId1)
                    .addComponent(txtSalesOrderId1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCustomerInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomerName1)
                    .addComponent(txtCustomerName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCustomerInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail1)
                    .addComponent(txtEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCustomerInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPhone1)
                    .addComponent(txtPhone1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlOrderInfo1Layout = new javax.swing.GroupLayout(pnlOrderInfo1);
        pnlOrderInfo1.setLayout(pnlOrderInfo1Layout);
        pnlOrderInfo1Layout.setHorizontalGroup(
            pnlOrderInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOrderInfo1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(pnlOrderInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlCustomerInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlOrderInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pnlMeasurements1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlOrderInfo1Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addGroup(pnlOrderInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlOrderInfo1Layout.createSequentialGroup()
                                    .addComponent(lblOrder1)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtOrderId1))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOrderInfo1Layout.createSequentialGroup()
                                    .addComponent(lblQuantity1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtQuantity1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOrderInfo1Layout.createSequentialGroup()
                                    .addComponent(lblDate1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(24, 24, 24))))
                .addGap(3, 3, 3))
        );
        pnlOrderInfo1Layout.setVerticalGroup(
            pnlOrderInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderInfo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOrderInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrder1)
                    .addComponent(txtOrderId1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlOrderInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDate1)
                    .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlOrderInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuantity1)
                    .addComponent(txtQuantity1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(pnlMeasurements1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlCustomerInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(pnlControlPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlOrderInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlOrderInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pnlControlPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(bundle.getString("Overview.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

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
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
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
                    .addComponent(pnlLoggedIn, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(localeLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnLogout)
                    .addComponent(btnReset1))
                .addContainerGap())
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
       
        try
        {   
            slmodel = new SleeveTableModel(slmgr.getAll());
            tblSleeveList.setModel(slmodel);
            
            tblStockList2.setModel(smodel);
            omodel2 = new OrderTablemodel(omgr.getAll());
            tblOrderList1.setModel(omodel);               
//            tblSleeveList.repaint(); 
            
        }
        catch (Exception ex)
        {
           ex.getMessage();
        }
        finally
        {
            tblOrderList1.clearSelection();
            tblStockList2.clearSelection();
            tblSleeveList.clearSelection();  
        }
        
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnReset1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnReset1ActionPerformed
    {//GEN-HEADEREND:event_btnReset1ActionPerformed
         if(
                 jTabbedPane1.getSelectedIndex() == 3)
                 
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
        
 if(
         jTabbedPane1.getSelectedIndex() == 2)
 {
try
{slmodel = new SleeveTableModel(slmgr.getAll());
            tblSleeveList.setModel(slmodel);
 
            tblStockList2.setModel(smodel);
            omodel2 = new OrderTablemodel(omgr.getAll());
            tblOrderList1.setModel(omodel);               
//            tblSleeveList.repaint(); 
}
        
        catch (Exception ex)
        {
           ex.getMessage();
        }
        finally
        {
            tblOrderList1.clearSelection();
            tblStockList2.clearSelection();
            tblSleeveList.clearSelection();  
        }
 }
        
 
        
    }//GEN-LAST:event_btnReset1ActionPerformed

    private void txtStockQuantity1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_txtStockQuantity1ActionPerformed
    {//GEN-HEADEREND:event_txtStockQuantity1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockQuantity1ActionPerformed

    private void rbtnFinishActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rbtnFinishActionPerformed
    {//GEN-HEADEREND:event_rbtnFinishActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnFinishActionPerformed

    private void localeLanguageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_localeLanguageActionPerformed
    {//GEN-HEADEREND:event_localeLanguageActionPerformed
         rb = ResourceBundle.getBundle("GUI.Bundle", localeLanguage.getLocale());
        updateGUILanguage();
    }//GEN-LAST:event_localeLanguageActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanalStockInfo;
    private javax.swing.JPanel JPanalStockInfo1;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnFinishOrder;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnReset1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenuItem itemExit;
    private javax.swing.JMenuItem itemHelp;
    private javax.swing.JMenuItem itemLogOut;
    private javax.swing.JMenuItem itemSettings;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblCharge;
    private javax.swing.JLabel lblCharge1;
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblCode1;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblCustomerName1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDate1;
    private javax.swing.JLabel lblDensity;
    private javax.swing.JLabel lblDensity1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEmail1;
    private javax.swing.JLabel lblKg;
    private javax.swing.JLabel lblKg1;
    private javax.swing.JLabel lblLength1;
    private javax.swing.JLabel lblLength2;
    private javax.swing.JLabel lblLoggedIn;
    private javax.swing.JLabel lblMaterialID;
    private javax.swing.JLabel lblMaterialID1;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblName1;
    private javax.swing.JLabel lblOrder;
    private javax.swing.JLabel lblOrder1;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblPhone1;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblQuantity1;
    private javax.swing.JLabel lblSalesOrderId;
    private javax.swing.JLabel lblSalesOrderId1;
    private javax.swing.JLabel lblSleeve;
    private javax.swing.JLabel lblStockQuantity;
    private javax.swing.JLabel lblStockQuantity1;
    private javax.swing.JLabel lblThickness;
    private javax.swing.JLabel lblThickness1;
    private javax.swing.JLabel lblThickness2;
    private javax.swing.JLabel lblThickness3;
    private javax.swing.JLabel lblWidth;
    private javax.swing.JLabel lblWidth1;
    private javax.swing.JLabel lblWidth2;
    private javax.swing.JLabel lblWidth3;
    private com.toedter.components.JLocaleChooser localeLanguage;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuSettings;
    private javax.swing.JPanel pnlControlPanel;
    private javax.swing.JPanel pnlControlPanel1;
    private javax.swing.JPanel pnlCustomerInfo;
    private javax.swing.JPanel pnlCustomerInfo1;
    private javax.swing.JPanel pnlCutting1;
    private javax.swing.JPanel pnlCutting2;
    private javax.swing.JPanel pnlCutting3;
    private javax.swing.JPanel pnlInStockList;
    private javax.swing.JPanel pnlInstock;
    private javax.swing.JPanel pnlLoggedIn;
    private javax.swing.JPanel pnlMeasurements;
    private javax.swing.JPanel pnlMeasurements1;
    private javax.swing.JPanel pnlMeasurements2;
    private javax.swing.JPanel pnlMeasurements3;
    private javax.swing.JPanel pnlOrder;
    private javax.swing.JPanel pnlOrderInfo;
    private javax.swing.JPanel pnlOrderInfo1;
    private javax.swing.JPanel pnlOrderList;
    private javax.swing.JPanel pnlOrderStock;
    private javax.swing.JRadioButton rbtnContinue;
    private javax.swing.JRadioButton rbtnFinish;
    private javax.swing.JRadioButton rbtnInPauseAgain;
    private javax.swing.JRadioButton rbtnInProgress;
    private javax.swing.JRadioButton rbtnPending;
    private javax.swing.JRadioButton rbtnUrgent;
    private javax.swing.JScrollPane sclInStock;
    private javax.swing.JScrollPane scrOrderList;
    private javax.swing.JScrollPane scrOrderList1;
    private javax.swing.JScrollPane scrOrderStock;
    private javax.swing.JScrollPane scrSleeve;
    private javax.swing.JScrollPane scrStockOrder;
    private javax.swing.JPopupMenu.Separator seperatorSettings;
    private javax.swing.JTable tblOrderList;
    private javax.swing.JTable tblOrderList1;
    private javax.swing.JTable tblOrderList2;
    private javax.swing.JTable tblProductionSleeve;
    private javax.swing.JTable tblSleeveList;
    private javax.swing.JTable tblStockList;
    private javax.swing.JTable tblStockList2;
    private javax.swing.JTable tblStockList3;
    private javax.swing.JTextField txtCharge;
    private javax.swing.JTextField txtCharge1;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtCode1;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtCustomerName1;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtDate1;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmail1;
    private javax.swing.JTextField txtLength1;
    private javax.swing.JTextField txtLength2;
    private javax.swing.JTextField txtMaterialDenisity;
    private javax.swing.JTextField txtMaterialDenisity1;
    private javax.swing.JTextField txtMaterialID1;
    private javax.swing.JTextField txtMaterialID2;
    private javax.swing.JTextField txtMaterialName1;
    private javax.swing.JTextField txtMaterialName2;
    private javax.swing.JTextField txtOrderId;
    private javax.swing.JTextField txtOrderId1;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPhone1;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtQuantity1;
    private javax.swing.JTextField txtSalesOrderId;
    private javax.swing.JTextField txtSalesOrderId1;
    private javax.swing.JTextField txtStockQuantity;
    private javax.swing.JTextField txtStockQuantity1;
    private javax.swing.JTextField txtThickness;
    private javax.swing.JTextField txtThickness1;
    private javax.swing.JTextField txtThickness2;
    private javax.swing.JTextField txtThickness3;
    private javax.swing.JTextField txtWidth;
    private javax.swing.JTextField txtWidth1;
    private javax.swing.JTextField txtWidth2;
    private javax.swing.JTextField txtWidth3;
    // End of variables declaration//GEN-END:variables
}
