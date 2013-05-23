/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BE.Operator;
import BE.Order;
import BE.Sleeve;
import BLL.ErrorsOccuredManager;
import BLL.OrderManager;
import BLL.SleeveLogManager;
import BLL.SleeveManager;
import BLL.StockItemManager;
import GUI.Models.SleeveTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Mak, Klaus, Rashid og Daniel
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
    private static ErrorsOccuredManager emgr = null;
//    private GregorianCalendar date = new GregorianCalendar();
//    final DateFormat startTimeFormat = new SimpleDateFormat("HH:mm:ss");
//    final DateFormat endTimeFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
//    long starTime = System.currentTimeMillis();
//    long endTime = System.currentTimeMillis();
//    int day, month, year;
//    int hour, minute, second;
    private DateTime startTime, endTime;
    private DateTimeFormatter jodaTimeFormat = DateTimeFormat.forPattern("dd/MM/YYYY HH:mm:ss");
    private int elapsedMillisec, elapsedSec, elapsedMin, elapsedHour;
    private Timer timer;

    /**
     * Creates new form OrderInfo
     */
    public OrderInfo(Order o, Sleeve s, Operator op)
    {
        try
        {
            omgr = OrderManager.getInstance();
            sllmgr = SleeveLogManager.getInstance();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        order = o;
        sleeve = s;
        operator = op;
        initComponents();
        buttonState();
        windowClose();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/icons/belman.png")).getImage());
        txtOrderName.setText(o.getOrderName());
        txtOrderId.setText(String.valueOf(o.getOrderId()));
        txtId.setText(String.valueOf(op.getId()));
        txtName.setText(String.valueOf(op.getFirstName()));
        txtLastName.setText(String.valueOf(op.getLastName()));
        

        txtError.setText(o.getErrorOccured());
//        txtError.setText(omgr.getOrdersBySleeve(o.getSleeve()).get(0).getErrorOccured());


        lblSleeves.setText(String.valueOf("Sleeves to be made " + o.getConductedQuantity() + " / " + o.getQuantity()));

        try
        {
            txtHasCut.setText(String.valueOf(sllmgr.getQuantity(order.getSleeve(), operator.getId())));
            slmgr = SleeveManager.getInstance();
            slmgr.addObserver(this);
            slmodel = new SleeveTableModel(slmgr.getSleevesByOrder(o));
            tblSleeve.setModel(slmodel);
            omgr = OrderManager.getInstance();
            omgr.addObserver(this);
            smgr = StockItemManager.getInstance();
            smgr.addObserver(this);
            emgr = ErrorsOccuredManager.getInstance();
            emgr.addObserver(this);

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

                        btnPause.setEnabled(true);
                        btnFinish.setEnabled(true);
                        btnStart.setEnabled(true);
                    }
                    else
                    {
                        btnStart.setEnabled(true);
                    }
                    if (sleeve.getStartTime() != null)
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
                        txtEndTime.setText(sdf.format(sleeve.getEndTime().getTime()));
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

    private void buttonState()
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

    private void updateMessage()
    {
    }

    private void closePressed()
    {
        String message = "Are you sure you want to close the window?";
        int reply = JOptionPane.showConfirmDialog(null, message, getTitle(), JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
        {
            dispose();
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

        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOrderName = new javax.swing.JTextField();
        txtOrderId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSleeve = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lblSleeves = new javax.swing.JLabel();
        btnStart = new javax.swing.JButton();
        btnPause = new javax.swing.JButton();
        btnFinish = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtError = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtHasCut = new javax.swing.JTextField();
        txtStartTime = new javax.swing.JTextField();
        txtEndTime = new javax.swing.JTextField();
        txtTimeSpent = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Start time on cut: ");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Belman Manager");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Production Order and Sleeve Information"));

        jLabel1.setText("Order Name: ");

        jLabel2.setText("Order ID: ");

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
        jScrollPane1.setViewportView(tblSleeve);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtOrderId, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txtOrderName))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtOrderName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Cutting Console")));

        lblSleeves.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblSleeves.setText("Sleeves to be made:");

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnStartActionPerformed(evt);
            }
        });

        btnPause.setText("Pause");
        btnPause.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnPauseActionPerformed(evt);
            }
        });

        btnFinish.setText("Finish");
        btnFinish.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnFinishActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Employee cutting:");

        jLabel5.setText("First name:");

        jLabel6.setText("Last name:");

        jLabel7.setText("ID: ");

        jLabel9.setText("Has cut:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("End time on cut:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Time spent: ");

        txtError.setColumns(20);
        txtError.setRows(5);
        jScrollPane3.setViewportView(txtError);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Errors occured: ");

        txtId.setEditable(false);

        txtName.setEditable(false);

        txtLastName.setEditable(false);

        txtHasCut.setEditable(false);

        txtStartTime.setEditable(false);

        txtEndTime.setEditable(false);

        txtTimeSpent.setEditable(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Start time on cut: ");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel12))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel7))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                            .addComponent(txtName)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel9))
                                        .addGap(69, 69, 69)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtLastName)
                                            .addComponent(txtHasCut)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(btnStart)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnPause)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnFinish))
                                            .addComponent(lblSleeves)
                                            .addComponent(jLabel4))
                                        .addGap(0, 51, Short.MAX_VALUE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel13))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtStartTime, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTimeSpent)
                                    .addComponent(txtEndTime))))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblSleeves)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnStart)
                            .addComponent(btnPause)
                            .addComponent(btnFinish))
                        .addGap(19, 19, 19)
                        .addComponent(jLabel13))
                    .addComponent(txtStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEndTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtTimeSpent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHasCut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(29, 29, 29)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave))
        );

        btnOk.setText("Ok");
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 232, Short.MAX_VALUE)
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            elapsedMillisec = 0;
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
                    String displayTimer = String.format("%02d:%02d:%02d:%03d", elapsedHour, elapsedMin, elapsedSec, elapsedMillisec);
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
            String message = "Production Order " + order.getOrderId() + "'s status has been updated.";
            JOptionPane.showMessageDialog(this, message, "Update succesful", JOptionPane.INFORMATION_MESSAGE);
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
            String message = "Production Order " + order.getOrderId() + "'s status has been paused.";
            JOptionPane.showMessageDialog(this, message, "Pause succesful", JOptionPane.INFORMATION_MESSAGE);
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
        String message = txtError.getText();
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
        try
        {
            omgr.updateStatus(order);
            dispose();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnFinishActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFinish;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblSleeves;
    private javax.swing.JTable tblSleeve;
    private javax.swing.JTextField txtEndTime;
    private javax.swing.JTextArea txtError;
    private javax.swing.JTextField txtHasCut;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtOrderId;
    private javax.swing.JTextField txtOrderName;
    private javax.swing.JTextField txtStartTime;
    private javax.swing.JTextField txtTimeSpent;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg)
    {
    }
}
