/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

// <editor-fold defaultstate="collapsed" desc="Imports">                          
import BE.Operator;
import BE.Order;
import BE.Sleeve;
import BE.StockItem;
import BLL.OperatorManager;
import BLL.OrderManager;
import BLL.SleeveManager;
import BLL.StockItemManager;
import GUI.Models.OrderTablemodel;
import GUI.Models.ProductionSleeveTableModel;
import GUI.Models.StockList2TableModel;
import GUI.Models.StockListTableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
// </editor-fold> 

/**
 * Graphical User Interface Overview klassen
 * 
 * @author Rashid, Daniel Mak og Klaus
 */
public class Overview extends javax.swing.JFrame implements Observer
{
    //<editor-fold defaultstate="collapsed" desc="Class Variables">
    private static Overview instance = null;
    private ResourceBundle rb = null;
    static OrderManager managerOrder = null;
    static StockItemManager managerStockItem = null;
    static SleeveManager managerSleeve = null;
    static OperatorManager managerOperator = null;
    private OrderTablemodel modelOrder = null;    
    private StockListTableModel modelEmptyStocklist = null;
    private ProductionSleeveTableModel modelProduction = null;
    private StockList2TableModel modelStocklist = null;
    private Order o;
    private Sleeve s;
    private Operator op;
    private String operator = null;
    //</editor-fold>

    /** 
     * Opretter en nye form a Overview
     */
    //<editor-fold defaultstate="collapsed" desc="Overview constructor">
    private Overview()
    {
        loadManagers();
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("GUI.Bundle");
        //        setExtendedState(MAXIMIZED_BOTH); MAXIMIZED WINDOW
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/icons/belman.png")).getImage());
        windowClose();
        setLocationRelativeTo(null);
        productionSleeveListSelectioner();
        pausedOrderTable();
        localeLanguage.setLocale(locale);
        mouseListener();
        //        mouseListener2();
        setTableColumnSize();
        setTableSelectionMode();
        comboboxModel();
        operator = cbxOperator.getSelectedItem().toString();
        updateGUILanguage();
    }
    //</editor-fold>

    /**
     * Metode til at returnere den eneste instans af denne klasse.
     */
    //<editor-fold defaultstate="collapsed" desc="Overview singleton getInstance();">
    public static Overview getInstance()
    {
        if (instance == null)
        {
            instance = new Overview();
        }
        return instance;
    }
    //</editor-fold>

    /**
     * Metode der loader vores managers
     */
    //<editor-fold defaultstate="collapsed" desc="Load Managers">
    private void loadManagers()
    {
        try
        {
            managerOrder = OrderManager.getInstance();
            managerOperator = OperatorManager.getInstance();
            managerStockItem = StockItemManager.getInstance();
            managerSleeve = SleeveManager.getInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //</editor-fold>

    /**
     * Metode der sætter kolonne størrelse på vores tabeller
     */
    // <editor-fold defaultstate="collapsed" desc="Set table column sizes">
    private void setTableColumnSize()     
    {
        tblProductionSleeve.getColumnModel().getColumn(0).setPreferredWidth(180);
        tblProductionSleeve.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblProductionSleeve.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblProductionSleeve.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(4).setPreferredWidth(150);
        tblProductionSleeve.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(6).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().getColumn(7).setPreferredWidth(100);
        tblProductionSleeve.getColumnModel().setColumnMargin(5);

        tblStockList.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblStockList.getColumnModel().getColumn(1).setPreferredWidth(80);
        tblStockList.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblStockList.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblStockList.getColumnModel().getColumn(4).setPreferredWidth(80);
        tblStockList.getColumnModel().getColumn(5).setPreferredWidth(80);

    }// </editor-fold> 

    /**
     * Metode der styrer selection mode på vores tabeller
     */
    //<editor-fold defaultstate="collapsed" desc="Set table selection mode">
    private void setTableSelectionMode()
    {
        tblOrderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblProductionSleeve.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblStockList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    //</editor-fold>

    /**
     * Metode der opdaterer sproget på overview
     */
    // <editor-fold defaultstate="collapsed" desc="Updates fields and labels when the language is changed"> 
    private void updateGUILanguage()                             
    {
        btnClose.setText(rb.getString("Overview.btnClose.text"));
        btnReset.setText(rb.getString("Overview.btnReset.text"));

        menuFile.setText(rb.getString("Overview.menuFile.text"));
        menuSettings.setText(rb.getString("Overview.menuSettings.text"));

        itemExit.setText(rb.getString("Overview.itemExit.text"));
        itemHelp.setText(rb.getString("Overview.itemHelp.text_1"));

        tpaneOverview.setTitleAt(0, rb.getString("Overview.pnlCutting2.TabConstraints.tabTitle"));
        lblLoggedIn.setText(rb.getString("Overview.lblLoggedIn.text") + operator);

        lblProductionOrder.setText(rb.getString("Overview.lblProductionOrder.text"));
        lblStock.setText(rb.getString("Overview.lblStock.text"));
        lblChangeOperator.setText(rb.getString("Overview.lblChangeOperator.text"));

        lblCustomerName.setText(rb.getString("Overview.lblCustomerName.text"));
        lblDate.setText(rb.getString("Overview.lblDate.text"));
        lblEmail.setText(rb.getString("Overview.lblEmail.text"));
        lblOrder.setText(rb.getString("Overview.lblOrder.text"));
        lblPhone.setText(rb.getString("Overview.lblPhone.text"));
        lblQuantity.setText(rb.getString("Overview.lblQuantity.text"));
        lblSalesOrderId.setText(rb.getString("Overview.lblSalesOrderId.text"));
        lblThickness.setText(rb.getString("Overview.lblThickness.text"));
        lblWidth.setText(rb.getString("Overview.lblWidth.text"));


//     TitledBorder border = (TitledBorder) pnlCustomerInfo.getBorder();

//          TitledBorder border2 = (TitledBorder) pnlMeasurements.getBorder();

//     TitledBorder border3 = (TitledBorder) pnlCutting2.getBorder();

//     TitledBorder border4 = (TitledBorder) pnlOrder.getBorder();

//     TitledBorder border5 = (TitledBorder) pnlOrderInfo.getBorder();
    }// </editor-fold> 

    /**
     * Metode der opdaterer vores LoggedIn label med vores operator string
     */
    //<editor-fold defaultstate="collapsed" desc="Update loggedIn label with Operator string">
    private void updateOperator()
    {
        lblLoggedIn.setText(rb.getString("Overview.lblLoggedIn.text") + operator);
    }
    //</editor-fold>

    /**
     * Metode der opdaterer vores operator string med den valgte operator i vores operator kombobox
     * Og derefter kalder updateOperator();
     */
    //<editor-fold defaultstate="collapsed" desc="Updates the Operator String from the Combobox">
    private void selectedOperator()
    {
        Operator selectedOperator = (Operator) cbxOperator.getSelectedItem();
        if (selectedOperator != null)
        {
            operator = selectedOperator.toString();
            updateOperator();
        }
    }
    //</editor-fold>

    /**
     * Metode der styrer vores kombobox, som viser alle operators der hentes via
     * operatorManager og derefter kalder selectedOperator(); hver gang der vælges
     * en ny operator
     */
    //<editor-fold defaultstate="collapsed" desc="Combobox model">
    private void comboboxModel()
    {
        try
        {
            DefaultComboBoxModel model = new DefaultComboBoxModel(managerOperator.getAllOperators().toArray());
            cbxOperator.setModel(model);
            
            cbxOperator.addItemListener(new ItemListener()
            {
                @Override
                public void itemStateChanged(ItemEvent e)
                {
                    if (e.getStateChange() == ItemEvent.SELECTED)
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
    //</editor-fold>

    /**
     * Metode der viser en confirmDialog, med ja og nej muligheder
     */
    //<editor-fold defaultstate="collapsed" desc="Confirm dialog when closing the window">
    private void closePressed()
    {
        String message = "Are you sure you want to exit?";
        int reply = JOptionPane.showConfirmDialog(null, message, getTitle(), JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }
    //</editor-fold>

    /**
     * Metode der tilføjer en windowListener til vores Overview frame, der kalder 
     * closePressed(); hvis vinduet skulle blive lukket
     */
    //<editor-fold defaultstate="collapsed" desc="Windowlistener - Window closing">
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
    //</editor-fold>

    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof OrderManager)
        {
            try
            {
                modelOrder.setCollection(managerOrder.getAll());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void pausedOrderTable()
    {
        try
        {
            managerOrder.addObserver(this);

            modelOrder = new OrderTablemodel(managerOrder.getPaused());
            tblOrderList.setModel(modelOrder);

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

                    Order o = modelOrder.getEventsByRow(selectedRow);

                    try
                    {
                        txtOrderId.setText(String.valueOf(o.getOrderId()));
                        txtDate.setText(String.valueOf(o.printDate(o.getDueDate())));
                        txtQuantity.setText(String.valueOf(o.getQuantity()));
                        txtThickness.setText(String.valueOf(o.getSleeve().getThickness()));
                        txtWidth.setText(String.valueOf(o.getWidth()));
                        txtSalesOrderId.setText(String.valueOf(o.getSalesOrder().getsOrderId()));
                        txtCustomerName.setText(String.valueOf(o.getSalesOrder().getCustName()));
                        txtEmail.setText(String.valueOf(o.getSalesOrder().getEmail()));
                        txtPhone.setText(String.valueOf(o.getSalesOrder().getPhone()));

                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void productionSleeveListSelectioner()
    {
        try
        {
            managerStockItem.addObserver(this);
            modelStocklist = new StockList2TableModel(managerStockItem.getAll());
            tblStockList.setModel(modelStocklist);

            managerOrder.addObserver(this);
            modelProduction = new ProductionSleeveTableModel(managerOrder.getAll());
            tblProductionSleeve.setModel(modelProduction);

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
                    Order o = modelProduction.getEventsByRow(selectedRow);
                    try
                    {
                        if (!managerStockItem.getItemByOrder(o).isEmpty())
                        {
                            modelStocklist = new StockList2TableModel(managerStockItem.getItemByOrder(o));
                            tblStockList.setModel(modelStocklist);
                        }
                        else
                        {
                            modelEmptyStocklist = new StockListTableModel();
                            tblStockList.setModel(modelEmptyStocklist);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            managerStockItem.addObserver(this);
            modelStocklist = new StockList2TableModel(managerStockItem.getAll());
            tblStockList.setModel(modelStocklist);
            tblStockList.getSelectionModel().addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent es)
                {
                    int selectedRow = tblStockList.getSelectedRow();
                    if (selectedRow == -1)
                    {
                        return;
                    }
                    StockItem s = modelStocklist.getEventsByRow(selectedRow);
                    try
                    {
                        if (!managerOrder.getOrderByStock(s).isEmpty())
                        {
                            modelProduction = new ProductionSleeveTableModel(managerOrder.getOrderByStock(s));
                            tblProductionSleeve.setModel(modelProduction);
                        }
                        else
                        {
                            modelEmptyStocklist = new StockListTableModel();
                            tblProductionSleeve.setModel(modelEmptyStocklist);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
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
     * Metode der tilføjer en mouseListener til vores Production sleeve tabel, 
     * som gør det muligt at dobbeltklikke i tabellen. Ved dobbeltklik åbner
     * Orderinfo med tre parametre fra Overview (order, sleeve og operator).
     */
    //<editor-fold defaultstate="collapsed" desc="Mouse listener til Production Sleeve tabellen">
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
                    o = modelProduction.getEventsByRow(selectedRow);
                    try
                    {
                        op = managerOperator.get(operator);
                    }
                    catch (SQLException ex)
                    {
                        ex.printStackTrace();
                    }
                    new OrderInfo(o, s, op).setVisible(true);
                }
                
            }
        });
    }
    //</editor-fold>
    
    /**
     * Metode der nulstiller vores stocklist og productionsleeve tabeller til standard
     * via vores getAll metode i StockItemManager og OrderManager og fjerner selection
     * i de to tabeller
     */
    //<editor-fold defaultstate="collapsed" desc="Reset Stocklist and ProductionSleeve tables">
    private void resetTables()
    {
        try
        {
            modelStocklist = new StockList2TableModel(managerStockItem.getAll());
            tblStockList.setModel(modelStocklist);
            modelProduction = new ProductionSleeveTableModel(managerOrder.getAll());
            tblProductionSleeve.setModel(modelProduction);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            tblProductionSleeve.clearSelection();
            tblStockList.clearSelection();
        }
    }
    //</editor-fold>
    
    /**
     * Metode der styrer hvornår reset knappen skal vises, afhænger af hvilken
     * tab der er valgt i vores tabbed pane
     */
    //<editor-fold defaultstate="collapsed" desc="Reset Button">
    private void resetButton()
    {
        if (tpaneOverview.getSelectedIndex() == 0)
        {
            btnReset.setVisible(true);
        }
        if (tpaneOverview.getSelectedIndex() == 1)
        {
            btnReset.setVisible(false);
        }
    }
    //</editor-fold>
    
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
        dateTime1 = new org.joda.time.DateTime();
        tpaneOverview = new javax.swing.JTabbedPane();
        pnlCutting2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductionSleeve = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStockList = new javax.swing.JTable();
        lblProductionOrder = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
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
        btnClose = new javax.swing.JButton();
        pnlLoggedIn = new javax.swing.JPanel();
        lblLoggedIn = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        localeLanguage = new com.toedter.components.JLocaleChooser();
        cbxOperator = new javax.swing.JComboBox();
        lblChangeOperator = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        itemExit = new javax.swing.JMenuItem();
        menuSettings = new javax.swing.JMenu();
        itemHelp = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("GUI/Bundle_en_GB"); // NOI18N
        setTitle(bundle.getString("Overview.title")); // NOI18N
        setPreferredSize(new java.awt.Dimension(1500, 650));
        setResizable(false);

        tpaneOverview.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                tpaneOverviewStateChanged(evt);
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

        tblStockList.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblStockList);

        lblProductionOrder.setText(bundle.getString("Overview.lblProductionOrder.text")); // NOI18N

        lblStock.setText(bundle.getString("Overview.lblStock.text")); // NOI18N

        javax.swing.GroupLayout pnlCutting2Layout = new javax.swing.GroupLayout(pnlCutting2);
        pnlCutting2.setLayout(pnlCutting2Layout);
        pnlCutting2Layout.setHorizontalGroup(
            pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCutting2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCutting2Layout.createSequentialGroup()
                        .addComponent(lblProductionOrder)
                        .addGap(0, 740, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 928, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStock))
                .addContainerGap())
        );
        pnlCutting2Layout.setVerticalGroup(
            pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCutting2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductionOrder)
                    .addComponent(lblStock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCutting2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        tpaneOverview.addTab(bundle.getString("Overview.pnlCutting2.TabConstraints.tabTitle"), pnlCutting2); // NOI18N

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
            .addComponent(scrOrderList, javax.swing.GroupLayout.DEFAULT_SIZE, 889, Short.MAX_VALUE)
        );
        pnlOrderListLayout.setVerticalGroup(
            pnlOrderListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrOrderList, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
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
                .addContainerGap(80, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlOrderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
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

        tpaneOverview.addTab(bundle.getString("Overview.pnlOrder.TabConstraints.tabTitle_1"), pnlOrder); // NOI18N

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

        btnReset.setText(bundle.getString("Overview.btnReset.text")); // NOI18N
        btnReset.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnResetActionPerformed(evt);
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

        lblChangeOperator.setText(bundle.getString("Overview.lblChangeOperator.text")); // NOI18N

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

        itemHelp.setText(bundle.getString("Overview.itemHelp.text_1")); // NOI18N
        itemHelp.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                itemHelpActionPerformed(evt);
            }
        });
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tpaneOverview)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlLoggedIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblChangeOperator)
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
                    .addComponent(pnlLoggedIn, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(localeLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxOperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblChangeOperator))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(tpaneOverview, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnReset))
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

    private void localeLanguageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_localeLanguageActionPerformed
    {//GEN-HEADEREND:event_localeLanguageActionPerformed
        rb = ResourceBundle.getBundle("GUI.Bundle", localeLanguage.getLocale());
        updateGUILanguage();
    }//GEN-LAST:event_localeLanguageActionPerformed

    private void tpaneOverviewStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_tpaneOverviewStateChanged
    {//GEN-HEADEREND:event_tpaneOverviewStateChanged
        resetButton();
    }//GEN-LAST:event_tpaneOverviewStateChanged

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnResetActionPerformed
    {//GEN-HEADEREND:event_btnResetActionPerformed
        resetTables();
    }//GEN-LAST:event_btnResetActionPerformed

    private void itemHelpActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_itemHelpActionPerformed
    {//GEN-HEADEREND:event_itemHelpActionPerformed
        About.getInstance().setVisible(true);
    }//GEN-LAST:event_itemHelpActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnReset;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbxOperator;
    private org.joda.time.DateTime dateTime1;
    private javax.swing.JMenuItem itemExit;
    private javax.swing.JMenuItem itemHelp;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblChangeOperator;
    private javax.swing.JLabel lblCustomerName;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblLoggedIn;
    private javax.swing.JLabel lblOrder;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblProductionOrder;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblSalesOrderId;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblThickness;
    private javax.swing.JLabel lblWidth;
    private com.toedter.components.JLocaleChooser localeLanguage;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuSettings;
    private javax.swing.JPanel pnlCustomerInfo;
    private javax.swing.JPanel pnlCutting2;
    private javax.swing.JPanel pnlLoggedIn;
    private javax.swing.JPanel pnlMeasurements;
    private javax.swing.JPanel pnlOrder;
    private javax.swing.JPanel pnlOrderInfo;
    private javax.swing.JPanel pnlOrderList;
    private javax.swing.JScrollPane scrOrderList;
    private javax.swing.JTable tblOrderList;
    private javax.swing.JTable tblProductionSleeve;
    private javax.swing.JTable tblStockList;
    private javax.swing.JTabbedPane tpaneOverview;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtOrderId;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSalesOrderId;
    private javax.swing.JTextField txtThickness;
    private javax.swing.JTextField txtWidth;
    // End of variables declaration//GEN-END:variables
}
