/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BE.Order;
import BE.Operator;
import BE.OrderType;
import BE.ErrorsOccured;
import BLL.OrderManager;
import BLL.SleeveManager;
import BLL.ErrorsOccuredManager;
import BLL.StockItemManager;
import GUI.Models.SleeveTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Mak
 */
public class OrderInfo extends javax.swing.JFrame implements Observer
{

    private Order order;
    private Operator operator;
    private SleeveTableModel slmodel = null;
    static SleeveManager slmgr = null;
    static StockItemManager smgr = null;
    static OrderManager omgr = null;
    private GregorianCalendar date = new GregorianCalendar();
    final DateFormat startTimeFormat = new SimpleDateFormat("HH:mm:ss");
    final DateFormat endTimeFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
//    long starTime = System.currentTimeMillis();
//    long endTime = System.currentTimeMillis();
    int day, month, year;
    int hour, minute, second;
    DateTime jodaTime = new DateTime();
    DateTimeFormatter jodaTimeFormat = DateTimeFormat.forPattern("dd/MM/YYYY HH:mm:ss");
    private int elapsedMin;
    private int elapsedSec;
    private int elapsedHour;
    static ErrorsOccured eomgr;
    Timer timer;
    OrderType ot;
    Order o;
    Operator op;


    /**
     * Creates new form OrderInfo
     */
    public OrderInfo(Order o)
    {
        order = o;
        initComponents();
        buttonState();
        windowClose();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/icons/belman.png")).getImage());
        txtOrderName.setText(o.getOrderName());
        txtOrderId.setText(String.valueOf(o.getOrderId()));

        
        lblSleeves.setText(String.valueOf("Sleeves to be made " + o.getConductedQuantity() + " / " + o.getQuantity()));

        try
        {

            slmgr = SleeveManager.getInstance();
            slmgr.addObserver(this);
            slmodel = new SleeveTableModel(slmgr.getSleevesByOrder(o));
            tblSleeve.setModel(slmodel);
            omgr = OrderManager.getInstance();
            omgr.addObserver(this);
            smgr = StockItemManager.getInstance();
            smgr.addObserver(this);
            
           


//            tblSleeve.getSelectionModel().addListSelectionListener(new ListSelectionListener()
//            {
//                @Override
//                public void valueChanged(ListSelectionEvent es)
//                {
//                    int selectedRow = tblSleeveList.getSelectedRow();
//                    if (selectedRow == -1)
//                    {
//                        return;
//                    }
//
//                    Sleeve s = slmodel.getEventsByRow(selectedRow);
//
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
        }

//        txtDueDate.setText(o.printDate(o.getDueDate()));
//        txtQuantity.setText("" + o.getQuantity());
    }

    //      private void ErrorsOccured()
//      {
//          
//          String info = jTextArea2.getText();
//          ErrorsOccured error = new ErrorsOccured(order.getOrderId(), info);
//          eomgr.updateError(error);
//      }

    
    private void buttonState()
    {
        if (jTextField5.getText().isEmpty())
        {
            btnPause.setEnabled(false);
            btnFinish.setEnabled(false);
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
     
     private void btnSavePressed()
    {
        try
        {
//            o.setConductedQuantity(txtconductedQuantity.getText());
            
         OrderType  type = btnStart.isSelected() ? OrderType.START
                           : btnPause.isSelected() ? OrderType.PAUSE
                           : btnFinish.isSelected() ? OrderType.AFSLUT
                           : OrderType.PROGRESS;


//            ot.setType(type);
            
            omgr.update(o);
            
        }
        catch (Exception e)
        {
//            ExceptionHandler.handle(e);
        }
    }
    
      private void btnSearchPressed()
    {
//        String strID = txtMemberID.getText();
//        if (strID.length() < 4)
        {
            String message = " ";
            JOptionPane.showMessageDialog(this, message, " Edit Order status", JOptionPane.ERROR_MESSAGE);
        }
//        else
        {
            int memberID;
//            try
            {
//                memberID = Integer.parseInt(strID);
//                m = mgr.get(memberID);
//
//                switch (m.getType())
//                {                              
//                    case START:
//                        btnStart.setSelected(true);
//                        break;
//                    case PAUSE:
//                        btnPause.setSelected(true);
//                        break;
//                    case AFSLUT:                           //      skal de tilfÃ¸jes i
//                        btnFinish.setSelected(true);       //      denne switch.
//                        break;
//                    default:
////                        btnNone.setSelected(true);
//                }
//
//            }
//            catch (Exception e)
//            {
//                ExceptionHandler.handle(e);
            }
        }
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
    private void initComponents() {

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
        jTextArea2 = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        txtFirstName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Start time on cut: ");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Belman Manager");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Production Order and Sleeve Information"));

        jLabel1.setText("Order Name: ");

        jLabel2.setText("Order ID: ");

        txtOrderName.setEditable(false);
        txtOrderName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrderNameActionPerformed(evt);
            }
        });

        txtOrderId.setEditable(false);

        tblSleeve.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
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
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnPause.setText("Pause");
        btnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPauseActionPerformed(evt);
            }
        });

        btnFinish.setText("Finish");

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

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Errors occured: ");

        jTextField1.setEditable(false);

        txtFirstName.setEditable(false);

        txtLastName.setEditable(false);

        jTextField4.setEditable(false);

        jTextField5.setEditable(false);
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jTextField6.setEditable(false);

        jTextField7.setEditable(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Start time on cut: ");

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
                                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                            .addComponent(txtFirstName)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel9))
                                        .addGap(57, 57, 57)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtLastName)
                                            .addComponent(jTextField4)))
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
                                        .addGap(0, 0, Short.MAX_VALUE))))
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
                                    .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField7)
                                    .addComponent(jTextField6))))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3)))
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
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(29, 29, 29)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnOk.setText("Ok");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtOrderNameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_txtOrderNameActionPerformed
    {//GEN-HEADEREND:event_txtOrderNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOrderNameActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTextField5ActionPerformed
    {//GEN-HEADEREND:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnStartActionPerformed
    {//GEN-HEADEREND:event_btnStartActionPerformed
        Date date = new Date();
//        String time = startTimeFormat.format(date);
//        jTextField5.setText(time);

        btnPause.setEnabled(true);
        btnFinish.setEnabled(true);

//        day = date.get(Calendar.DAY_OF_MONTH);
//        month = date.get(Calendar.MONTH) + 1;
//        year = date.get(Calendar.YEAR);
//        hour = date.get(Calendar.HOUR_OF_DAY);
//        minute = date.get(Calendar.MINUTE);
//        second = date.get(Calendar.SECOND);
//        String dates = day +"/" +month +"/" +year;
//        String time = hour +":" +minute +":" +second;
//        
//        jTextField5.setText(dates +" " +time);

        jTextField5.setText(jodaTimeFormat.print(jodaTime));

//        jTextField5.setText(DateFormat.(System.currentTimeMillis(), "MM/dd/yy HH:mm"));

        if (jTextField7.getText().isEmpty())
        {
            elapsedHour  = 0;
            elapsedMin  = 0;
            elapsedSec  = 0;
            timer = new Timer(1000, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    elapsedSec++;
                    if (elapsedSec < 59)
                    {
                        elapsedSec = 0;
                        elapsedSec++;
                    }
               
                    if (elapsedMin  == 59)
                    {
                        elapsedMin = 0;
                        elapsedHour++;
                    }
                   String displayTimer = String.format("%02d:%02d:%02d", elapsedHour, elapsedMin, elapsedSec);
                    jTextField7.setText(displayTimer);

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
        if (order.getStatus().equals(option) || order.getStatus().equals(option2))
        {
            String status = "In Progress";
            order.setStatus(status.toUpperCase());
            omgr.updateStatus(order);
            String message = "Production Order " + order.getOrderId() + "'s status has been updated.";
            JOptionPane.showMessageDialog(this, message, "Update succesfull", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            String message = "Production Order " + order.getOrderId() + "'s status is already: In progress.";
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnPauseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPauseActionPerformed
    {//GEN-HEADEREND:event_btnPauseActionPerformed
        new SleeveInfo(o).setVisible(true);
        Date dater = new Date();
        String time = endTimeFormat.format(dater);
        jTextField6.setText(time);
        timer.stop();

        String option = "In Progress";
        if (order.getStatus().equalsIgnoreCase(option))
        {
            String status =("Paused");
            order.setStatus(status.toUpperCase());
            omgr.updateStatus(order);
            String message = "Production Order " + order.getOrderId() + " status has been paused.";
            JOptionPane.showMessageDialog(this, message, "Pause succesfull", JOptionPane.INFORMATION_MESSAGE);
            new SleeveInfo(o).setVisible(true);
        }
        else
        {
            String message = "Production Order " + order.getOrderId() + " status is not in progress or already paused.";
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnPauseActionPerformed
private void employeeName()
    {
//        jTextField2.setText(na.getString());
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
    }

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        closePressed();
    }//GEN-LAST:event_btnOkActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFinish;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnPause;
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
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JLabel lblSleeves;
    private javax.swing.JTable tblSleeve;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtOrderId;
    private javax.swing.JTextField txtOrderName;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg)
    {
    }
}
