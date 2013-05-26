package GUI;

import BE.Operator;
import BE.Order;
import BE.Sleeve;
import BLL.OrderManager;
import BLL.SleeveLogManager;
import BLL.SleeveManager;
import BLL.StockItemManager;
import GUI.Models.SleeveTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Graphical User Interface OrderInfo klassen.
 *
 * @author Daniel, Klaus, Mak, Rashid
 */
public class OrderInfo extends javax.swing.JFrame implements Observer
{

    private Order order;
    private Sleeve sleeve;
    private Operator operator;
    private SleeveTableModel slmodel = null;
    private static SleeveManager slmgr = null;
    private static StockItemManager smgr = null;
    private static SleeveLogManager sllmgr = null;
    private static OrderManager omgr = null;
    private DateTime startTime, endTime;
    private DateTimeFormatter jodaTimeFormat = DateTimeFormat.forPattern("dd/MM/YYYY HH:mm:ss");
    private int elapsedSec, elapsedMin, elapsedHour;
    private Timer timer;

    /**
     * Opretter en ny form af OrderInfo
     *
     * @param o
     * @param s
     * @param op
     */
    public OrderInfo(Order o, Sleeve s, Operator op)
    {
        order = o;
        sleeve = s;
        operator = op;
        loadManagers();
        initComponents();
        initialButtonState();
        windowClose();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/icons/belman.png")).getImage());
        txtOrderName.setText(o.getOrderName());
        txtOrderId.setText(String.valueOf(o.getOrderId()));
        txtId.setText(String.valueOf(op.getId()));
        txtName.setText(String.valueOf(op.getFirstName()));
        txtLastName.setText(String.valueOf(op.getLastName()));


        txtfErrors.setText(o.getErrorOccured());

        lblSleeves.setText(String.valueOf("Sleeves to be made " + o.getConductedQuantity() + " / " + o.getQuantity()));

        try
        {
            txtHasCut.setText(String.valueOf(sllmgr.getQuantity(order.getSleeve(), operator.getId())));
            slmgr.addObserver(this);
            slmodel = new SleeveTableModel(slmgr.getSleevesByOrder(o));
            tblSleeve.setModel(slmodel);
            omgr.addObserver(this);
            smgr.addObserver(this);

            tblSleeve.getSelectionModel().addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent es)
                {
                    int selectedRow = tblSleeve.getSelectedRow();
                    if (selectedRow == -1)
                    {
                        return;
                    }
                    sleeve = slmodel.getEventsByRow(selectedRow);
                    if (sleeve.getStartTime() != null)
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
                        txtStartTime.setText(sdf.format(sleeve.getStartTime().getTime()));
                        txtEndTime.setText(sdf.format(sleeve.getEndTime().getTime()));

                        btnPause.setEnabled(true);
                        btnFinish.setEnabled(true);
                        btnStart.setEnabled(true);

                        String status = "Finished";
                        if (order.getConductedQuantity() == order.getQuantity() && order.getStatus().equalsIgnoreCase(status))
                        {
                            btnPause.setEnabled(false);
                            btnFinish.setEnabled(false);
                            btnStart.setEnabled(false);
                        }
                        else if (order.getConductedQuantity() == order.getQuantity())
                        {
                            btnPause.setEnabled(false);
                            btnFinish.setEnabled(true);
                            btnStart.setEnabled(false);
                        }
                    }
                    else
                    {
                        btnStart.setEnabled(true);
                    }

                }
            });

//                    try
//                    {
//                        if (!omgr.getOrdersBySleeve(s).isEmpty())
//                        {
//                            omodel2 = new OrderTablemodel(omgr.getOrdersBySleeve(s));
//                            tblOrderList1.setModel(omodel2);
//
//                            if (!smgr.getItemBySleeve(s).isEmpty())
//                            {
//
//                                tblStockList2.setModel(smodel2);
//                            }
//
////                       tblOrderList1.getSelectionModel().addListSelectionListener(new ListSelect);                        
//                        }
////                    else
////                    {
//////                        omodel2.
////                        tblOrderList1.setModel(omodel2);
////                    }
////                    Sleeve s = slmodel.getEventsByRow(selectedRow);
//                    }
//                    catch (Exception e)
//                    {
//                    }
//                }
//            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

//        txtDueDate.setText(o.printDate(o.getDueDate()));
//        txtQuantity.setText("" + o.getQuantity());
    }

    private void loadManagers()
    {
        try
        {
            omgr = OrderManager.getInstance();
            sllmgr = SleeveLogManager.getInstance();
            smgr = StockItemManager.getInstance();
            slmgr = SleeveManager.getInstance();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void initialButtonState()
    {
        if (txtStartTime.getText().isEmpty())
        {
            btnStart.setEnabled(false);
            btnPause.setEnabled(false);
            btnFinish.setEnabled(false);
        }
//        else
//        {
//            jButton1.setEnabled(false);
//            jButton2.setEnabled(true);
//            jButton3.setEnabled(true);
//        }
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

    private void closePressed()
    {
        String option = "In progress";
        if (order.getStatus().equalsIgnoreCase(option))
        {
            String error = "Order is still in progress";
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            String message = "Are you sure you want to close the window?";
            int reply = JOptionPane.showConfirmDialog(null, message, getTitle(), JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION)
            {
                dispose();
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

        pnlProductionInformation = new javax.swing.JPanel();
        lblOrderName = new javax.swing.JLabel();
        lblOrderId = new javax.swing.JLabel();
        txtOrderName = new javax.swing.JTextField();
        txtOrderId = new javax.swing.JTextField();
        scrpSleeve = new javax.swing.JScrollPane();
        tblSleeve = new javax.swing.JTable();
        pnlCuttingConsole = new javax.swing.JPanel();
        lblSleeves = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        btnPause = new javax.swing.JButton();
        btnFinish = new javax.swing.JButton();
        lblEmployeeCutting = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        lblHasCut = new javax.swing.JLabel();
        lblEndTime = new javax.swing.JLabel();
        lblTimeSpent = new javax.swing.JLabel();
        scrpErrors = new javax.swing.JScrollPane();
        txtfErrors = new javax.swing.JTextArea();
        lblErrors = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtHasCut = new javax.swing.JTextField();
        txtStartTime = new javax.swing.JTextField();
        txtEndTime = new javax.swing.JTextField();
        txtTimeSpent = new javax.swing.JTextField();
        lblStartTime = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("GUI/Bundle"); // NOI18N
        setTitle(bundle.getString("OrderInfo.title")); // NOI18N
        setLocationByPlatform(true);

        pnlProductionInformation.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("OrderInfo.pnlProductionInformation.border.title"))); // NOI18N

        lblOrderName.setText(bundle.getString("OrderInfo.lblOrderName.text")); // NOI18N

        lblOrderId.setText(bundle.getString("OrderInfo.lblOrderId.text")); // NOI18N

        txtOrderName.setEditable(false);

        txtOrderId.setEditable(false);

        tblSleeve.setModel(new javax.swing.table.DefaultTableModel(
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
        scrpSleeve.setViewportView(tblSleeve);

        javax.swing.GroupLayout pnlProductionInformationLayout = new javax.swing.GroupLayout(pnlProductionInformation);
        pnlProductionInformation.setLayout(pnlProductionInformationLayout);
        pnlProductionInformationLayout.setHorizontalGroup(
            pnlProductionInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductionInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlProductionInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(scrpSleeve, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlProductionInformationLayout.createSequentialGroup()
                        .addGroup(pnlProductionInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOrderName)
                            .addComponent(lblOrderId))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlProductionInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtOrderId, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txtOrderName))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnlProductionInformationLayout.setVerticalGroup(
            pnlProductionInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductionInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlProductionInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrderName)
                    .addComponent(txtOrderName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProductionInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrderId)
                    .addComponent(txtOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrpSleeve)
                .addContainerGap())
        );

        pnlCuttingConsole.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), bundle.getString("OrderInfo.pnlCuttingConsole.border.border.title")))); // NOI18N

        lblSleeves.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblSleeves.setText(bundle.getString("OrderInfo.lblSleeves.text")); // NOI18N

        btnStart.setText(bundle.getString("OrderInfo.btnStart.text")); // NOI18N
        btnStart.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnStartActionPerformed(evt);
            }
        });

        btnPause.setText(bundle.getString("OrderInfo.btnPause.text")); // NOI18N
        btnPause.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnPauseActionPerformed(evt);
            }
        });

        btnFinish.setText(bundle.getString("OrderInfo.btnFinish.text")); // NOI18N
        btnFinish.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnFinishActionPerformed(evt);
            }
        });

        lblEmployeeCutting.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEmployeeCutting.setText(bundle.getString("OrderInfo.lblEmployeeCutting.text")); // NOI18N

        lblFirstName.setText(bundle.getString("OrderInfo.lblFirstName.text")); // NOI18N

        lblLastName.setText(bundle.getString("OrderInfo.lblLastName.text")); // NOI18N

        lblId.setText(bundle.getString("OrderInfo.lblId.text")); // NOI18N

        lblHasCut.setText(bundle.getString("OrderInfo.lblHasCut.text")); // NOI18N

        lblEndTime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEndTime.setText(bundle.getString("OrderInfo.lblEndTime.text")); // NOI18N

        lblTimeSpent.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTimeSpent.setText(bundle.getString("OrderInfo.lblTimeSpent.text")); // NOI18N

        txtfErrors.setColumns(20);
        txtfErrors.setRows(5);
        scrpErrors.setViewportView(txtfErrors);

        lblErrors.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblErrors.setText(bundle.getString("OrderInfo.lblErrors.text")); // NOI18N

        txtId.setEditable(false);

        txtName.setEditable(false);

        txtLastName.setEditable(false);

        txtHasCut.setEditable(false);

        txtStartTime.setEditable(false);

        txtEndTime.setEditable(false);

        txtTimeSpent.setEditable(false);

        lblStartTime.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblStartTime.setText(bundle.getString("OrderInfo.lblStartTime.text")); // NOI18N

        btnSave.setText(bundle.getString("OrderInfo.btnSave.text")); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCuttingConsoleLayout = new javax.swing.GroupLayout(pnlCuttingConsole);
        pnlCuttingConsole.setLayout(pnlCuttingConsoleLayout);
        pnlCuttingConsoleLayout.setHorizontalGroup(
            pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuttingConsoleLayout.createSequentialGroup()
                .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCuttingConsoleLayout.createSequentialGroup()
                        .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCuttingConsoleLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblErrors))
                            .addGroup(pnlCuttingConsoleLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlCuttingConsoleLayout.createSequentialGroup()
                                        .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblFirstName)
                                            .addComponent(lblId))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                            .addComponent(txtName)))
                                    .addGroup(pnlCuttingConsoleLayout.createSequentialGroup()
                                        .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblLastName)
                                            .addComponent(lblHasCut))
                                        .addGap(62, 62, 62)
                                        .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtLastName)
                                            .addComponent(txtHasCut)))
                                    .addGroup(pnlCuttingConsoleLayout.createSequentialGroup()
                                        .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlCuttingConsoleLayout.createSequentialGroup()
                                                .addComponent(btnStart)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnPause)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnFinish))
                                            .addComponent(lblSleeves)
                                            .addComponent(lblEmployeeCutting))
                                        .addGap(0, 34, Short.MAX_VALUE))))
                            .addGroup(pnlCuttingConsoleLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblStartTime)
                                    .addComponent(lblEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTimeSpent, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtStartTime, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtEndTime)
                                    .addComponent(txtTimeSpent, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(14, 14, 14))
                    .addGroup(pnlCuttingConsoleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrpErrors))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCuttingConsoleLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave)))
                .addContainerGap())
        );
        pnlCuttingConsoleLayout.setVerticalGroup(
            pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCuttingConsoleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlCuttingConsoleLayout.createSequentialGroup()
                        .addComponent(lblSleeves)
                        .addGap(18, 18, 18)
                        .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnStart)
                            .addComponent(btnPause)
                            .addComponent(btnFinish))
                        .addGap(19, 19, 19)
                        .addComponent(lblStartTime))
                    .addComponent(txtStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEndTime)
                    .addComponent(txtEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTimeSpent)
                    .addComponent(txtTimeSpent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblEmployeeCutting)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLastName, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCuttingConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHasCut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHasCut))
                .addGap(29, 29, 29)
                .addComponent(lblErrors)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrpErrors, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave))
        );

        btnOk.setText(bundle.getString("OrderInfo.btnOk.text")); // NOI18N
        btnOk.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlProductionInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(pnlCuttingConsole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlProductionInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlCuttingConsole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnOk)
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStartActionPerformed
    {//GEN-HEADEREND:event_btnStartActionPerformed
//        if (tblSleeve.getSelectedRow() == -1)
//        {
//            String message = "Please select a Sleeve to the left";
//            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        else
//        {
        btnPause.setEnabled(true);
        btnFinish.setEnabled(true);

//        endTime = new DateTime();

        try
        {
            txtStartTime.setText(jodaTimeFormat.print(startTime));
            startTime = jodaTimeFormat.parseDateTime(txtStartTime.getText());

            GregorianCalendar startTimeCalendar = startTime.toGregorianCalendar();

            sleeve.setStartTime(startTimeCalendar);
            slmgr.updateSleeveStartTime(sleeve);
        }
        catch (Exception e)
        {
            String message = "Unable to update sleeve with id " + sleeve.getId();
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

//        jTextField5.setText(DateFormat.(System.currentTimeMillis(), "MM/dd/yy HH:mm"));

        if (txtTimeSpent.getText().isEmpty())
        {
            elapsedHour = 0;
            elapsedMin = 0;
            elapsedSec = 0;
            timer = new Timer(1000, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    elapsedSec++;
//                    if (elapsedMillisec > 999)
//                    {
//                        elapsedMillisec = 0;
//                        elapsedSec++;
//                    }
                    if (elapsedSec > 59)
                    {
                        elapsedSec = 0;
                        elapsedMin++;
                    }
                    if (elapsedMin > 59)
                    {
                        elapsedMin = 0;
                        elapsedHour++;
                    }
                    String displayTimer = String.format("%02d:%02d:%02d", elapsedHour, elapsedMin, elapsedSec);
                    txtTimeSpent.setText(displayTimer);

                }
            });
            timer.setInitialDelay(0);
            timer.start();
        }
        else
        {
            timer.start();
        }

        String option = "Pending";
        String option2 = "Paused";
        if (order.getStatus().equalsIgnoreCase(option) || order.getStatus().equalsIgnoreCase(option2))
        {
            String status = "in Progress";
            order.setStatus(status.toUpperCase());
            try
            {
                omgr.updateStatus(order);
            }
            catch (Exception e)
            {
                String message = "Unable to update order";
                JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            String message = "Production Order " + order.getOrderId() + "'s status is already: In progress.";
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
//        }
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnPauseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPauseActionPerformed
    {//GEN-HEADEREND:event_btnPauseActionPerformed
//        endTime = new DateTime();
//        if (tblSleeve.getSelectedRow() == -1)
//        {
//            String message = "Please select a Sleeve to the left";
//            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        else
//        {
//            jButton1.setEnabled(true);

        timer.stop();

        try
        {
            txtEndTime.setText(jodaTimeFormat.print(endTime));
            endTime = jodaTimeFormat.parseDateTime(txtEndTime.getText());

            GregorianCalendar endTimeCalendar = endTime.toGregorianCalendar();

            sleeve.setEndTime(endTimeCalendar);
            slmgr.updateSleeveEndTime(sleeve);
        }
        catch (Exception e)
        {
            String message = "Unable to update sleeve with id " + sleeve.getId();
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

        String option = "In Progress";
        if (order.getStatus().equalsIgnoreCase(option))
        {
            String status = "Paused";
            order.setStatus(status.toUpperCase());
            try
            {
                omgr.updateStatus(order);
            }
            catch (Exception e)
            {
                String message = "Unable to update order";
                JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
            new SleeveInfo(order, operator).setVisible(true);
        }
        else
        {
            String message = "Production Order " + order.getOrderId() + "'s status is not in progress or already paused.";
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
//        }
    }//GEN-LAST:event_btnPauseActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        closePressed();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String message = txtfErrors.getText();
        try
        {
            omgr.updateErrorMessage(order, message);
            order.setErrorOccured(message);
        }
        catch (Exception e)
        {
            String error = "Unable to update error message";
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }

        String popup = "The error message has been saved";
        JOptionPane.showMessageDialog(this, popup, "Save successful", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnFinishActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnFinishActionPerformed
    {//GEN-HEADEREND:event_btnFinishActionPerformed

        String status = "Finished";
        order.setStatus(status.toUpperCase());
        int quantity = order.getQuantity();
        order.setConductedQuantity(quantity);
        try
        {
            omgr.update(order);
//            omgr.updateStatus(order);
            dispose();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnFinishActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFinish;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel lblEmployeeCutting;
    private javax.swing.JLabel lblEndTime;
    private javax.swing.JLabel lblErrors;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblHasCut;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblOrderId;
    private javax.swing.JLabel lblOrderName;
    private javax.swing.JLabel lblSleeves;
    private javax.swing.JLabel lblStartTime;
    private javax.swing.JLabel lblTimeSpent;
    private javax.swing.JPanel pnlCuttingConsole;
    private javax.swing.JPanel pnlProductionInformation;
    private javax.swing.JScrollPane scrpErrors;
    private javax.swing.JScrollPane scrpSleeve;
    private javax.swing.JTable tblSleeve;
    private javax.swing.JTextField txtEndTime;
    private javax.swing.JTextField txtHasCut;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtOrderId;
    private javax.swing.JTextField txtOrderName;
    private javax.swing.JTextField txtStartTime;
    private javax.swing.JTextField txtTimeSpent;
    private javax.swing.JTextArea txtfErrors;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg)
    {
    }
}
