/*
 * GUI.java
 *
 * Created on February 22, 2010, 11:59 AM
 */

package drawingapp;

import javax.comm.*;
import java.io.*;
import java.util.*;

class TableVal{    
    private java.lang.Integer num,distance,angle;
    
    TableVal(int one,int two, int three){
        num=new java.lang.Integer(one);
        distance=new java.lang.Integer(two);
        angle=new java.lang.Integer(three);        
    }
    
    java.lang.Integer getNum(){
        return num;
    } 
    
    java.lang.Integer getDistance(){
        return distance;
    }
    
    java.lang.Integer getAngle(){
        return angle;
    }
}

class Point{
    
    Point(int newX, int newY){
        setX(newX);
        setY(newY);
    }
            
    public void setX(int newX){
        x=newX;
    };
    
    public void setY(int newY){
      y=newY;  
    };
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    private int x,y;
}

class myPanel extends javax.swing.JPanel{
    
    private java.util.ArrayList points=new java.util.ArrayList();
    private javax.swing.JTable table;
    private boolean drawVal;
    private boolean firstPoint;        
    
    myPanel(javax.swing.JTable aTable){
        
        table=aTable;
        
        setValues();
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dAreaMouseClicked(evt,points);
            }
        });
        
    }        
    
    public void setValues(){
        drawVal=false;
        firstPoint=true;        
        points.clear();
        
        
        
        javax.swing.table.DefaultTableModel model=(javax.swing.table.DefaultTableModel)table.getModel();
        
        int numRows=model.getRowCount();
        
        System.out.println("Into setVals Rowcount is "+numRows);
        
        if(numRows>0){
                                   
            for(int i=numRows-1;i>=0;i--)
                model.removeRow(i);
        }        
    }
    
    public void setDrawVal(){
        drawVal=!drawVal;
    }  
    
    public boolean getDrawVal(){
        return drawVal;
    }
    
    public javax.swing.JTable getTable(){
        return table;
    }
            
    public boolean getFirstPoint(){
        return firstPoint;
    }
    
    private void dAreaMouseClicked(java.awt.event.MouseEvent evt, java.util.ArrayList points) {
        
        if(drawVal){
            
            System.out.println("Into Mouse click and drawVal");        

            points.add(new Point(evt.getX(),evt.getY()));        
            
            System.out.println(evt.getX()+" "+evt.getY());

            if(firstPoint){            
                firstPoint=false;
            }
            
            repaint();    
        }   

    }    
            
    public void paintComponent(java.awt.Graphics g){
        super.paintComponent(g);
        
        java.awt.Graphics2D g2=(java.awt.Graphics2D)g;
        
        if(drawVal){
        
        int num=points.size();
        
        if(num>1){
            
            for(int i=0;i+1<num;i++){
                
                Point point1=(Point)points.get(i);
                Point point2=(Point)points.get(i+1);
                
                g2.setColor(java.awt.Color.RED);                
                g2.fillOval(point1.getX()-2, point1.getY()-2, 4, 4);
                g2.drawOval(point2.getX()-2, point2.getY()-2, 4, 4);
                
                g2.setStroke(new java.awt.BasicStroke(2));                
                g2.setColor(java.awt.Color.BLUE);
                g2.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());                                                               
               }
            
            int one;
            double two=0,three=0;
                                    
            Point point1=(Point)points.get(num-1);
            Point point2=(Point)points.get(num-2);            
            one=num-1;            
            
            int x1=point1.getX(),x2=point2.getX(),y1=point1.getY(),y2=point2.getY();
            
            two=java.lang.Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));   
            two=(two*255)/823;
                                   
            if(num-2==0){                                
                three=-180/Math.PI*Math.atan((double)(y2-y1)/(x2-x1));
            }
            else{
                Point point3=(Point)points.get(num-3);            
                
                int x3=point3.getX(),y3=point3.getY();            
                                
                /* v1=(x3-x2)x+(y3-y2)y;
                 * v2=(x1-x2)x+(y1-y2)y
                */
                three=-1*Math.atan2( (x3-x2)*(y1-y2)-(y3-y2)*(x1-x2),(x3-x2)*(x1-x2)+(y3-y2)*(y1-y2) )*180/Math.PI;
                
                if(three<0)
                    three+=360;
            }
            
            TableVal tVal=new TableVal(one,(int)two,(int)three);
            
            javax.swing.table.DefaultTableModel model=(javax.swing.table.DefaultTableModel)table.getModel();
            
            model.addRow(new Object[]{tVal.getNum(),tVal.getDistance(),tVal.getAngle()});
            
            }
         else if(num==1){                         
            Point point1=(Point)points.get(0);
            
            g2.setColor(java.awt.Color.RED);
            g2.fillOval(point1.getX()-2, point1.getY()-2, 4, 4);
            }
                
        }
               
    }
    
}

public class GUINew extends javax.swing.JFrame implements SerialPortEventListener {

  
    public GUINew() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        draw = new javax.swing.JToggleButton();
        send = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        clear = new javax.swing.JButton(); 
        portSet = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        aTable = new javax.swing.JTable();
        dArea = new myPanel(aTable);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(0, 204, 204));
        setName("Frame"); // NOI18N
        setResizable(false);
        setTitle("Drawing App");

        draw.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        draw.setText("Draw");
        draw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawActionPerformed(evt);
            }
        });
        
        portSet.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        portSet.setText("Port");
        portSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portSetActionPerformed(evt);
            }
        });

        send.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        send.setText("Send");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });        

        exit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        clear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        dArea.setBackground(new java.awt.Color(255, 255, 255));
        dArea.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        javax.swing.GroupLayout dAreaLayout = new javax.swing.GroupLayout(dArea);
        dArea.setLayout(dAreaLayout);
        dAreaLayout.setHorizontalGroup(
            dAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 686, Short.MAX_VALUE)
        );
        dAreaLayout.setVerticalGroup(
            dAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
        );

        scrollPane.setBackground(new java.awt.Color(255, 255, 255));

        aTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Num", "Dist", "Angle"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        aTable.setGridColor(new java.awt.Color(0, 0, 0));
        scrollPane.setViewportView(aTable);
        aTable.getColumnModel().getColumn(0).setResizable(false);
        aTable.getColumnModel().getColumn(2).setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11)
                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(draw, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(send, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(portSet, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92)
                        .addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(draw)
                    .addComponent(send)
                    .addComponent(portSet)
                    .addComponent(exit)
                    .addComponent(clear))
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addGap(170, 170, 170))
        );

        pack();
    }// </editor-fold>
    
private void exitActionPerformed(java.awt.event.ActionEvent evt) {    
    System.exit(0);
}
private void drawActionPerformed(java.awt.event.ActionEvent evt) {    
    dArea.setDrawVal();
}

private void clearActionPerformed(java.awt.event.ActionEvent evt) {
    dArea.setValues();
    dArea.repaint();
    
    draw.setSelected(false);
}

private void sendActionPerformed (java.awt.event.ActionEvent evt) {
    
    if(!isSet)
    {
        javax.swing.JOptionPane.showMessageDialog(this, "Please select port settings first\n by clicking Port button", "Important", javax.swing.JOptionPane.INFORMATION_MESSAGE);        
        return;
    }
    accessPort();
}

private void portSetActionPerformed (java.awt.event.ActionEvent evt) {
    
    isSet=true;
    
    prop=new portProp();
    prop.setVisible(true);        
}

public void accessPort(){
        
    
        System.out.println(prop.getCom()+" "+prop.getAppName()+" "+prop.getTimeOut()+" "+prop.getFlowControl()+" "+prop.getBaudRate()+" "+prop.getDataBits()+" "+prop.getStopBits()+" "+prop.getParity());
    
        try {
            
        CommPortIdentifier pid=CommPortIdentifier.getPortIdentifier(prop.getCom());
        
        System.out.println(prop.getCom()+" found");
        
        SerialPort sPort=(SerialPort) pid.open(prop.getAppName(),prop.getTimeOut());
        
        System.out.println("Opened serial port");

        sPort.disableReceiveFraming();
        sPort.disableReceiveThreshold();
        sPort.setSerialPortParams(prop.getBaudRate() , prop.getDataBits(), prop.getStopBits(),prop.getParity());
        sPort.setFlowControlMode(prop.getFlowControl());        

        is= sPort.getInputStream();
        
        sPort.addEventListener(this);
        sPort.notifyOnDataAvailable(true);        
        
        OutputStream os= sPort.getOutputStream();
        
        System.out.println("Got hold of both the streams");
        
        javax.swing.table.DefaultTableModel model=(javax.swing.table.DefaultTableModel)dArea.getTable().getModel();
        
        int numRows=model.getRowCount();
        
        System.out.println("Into accessPort Rowcount is "+numRows);
        
        os.flush();
        
        if(numRows>0){
                
            int i=0;
            
            for(i=0;i<=(numRows-1);i++){                
            
             os.write(((java.lang.Integer)model.getValueAt(i, 1)).byteValue());   
             os.write(((java.lang.Integer)model.getValueAt(i, 2)).byteValue());

             System.out.println((char)(((java.lang.Integer)model.getValueAt(i, 1)).intValue())+" "+((java.lang.Integer)model.getValueAt(i, 1)).byteValue()+" "+(char)(((java.lang.Integer)model.getValueAt(i, 2)).intValue())+" "+((java.lang.Integer)model.getValueAt(i, 2)).byteValue());             
            }                  
            
            byte num=new java.lang.Integer(255).byteValue();
            
            os.write(num);       
            
            System.out.println("Byte value of zero -> "+num+" Hope this works");
        }        
        
        os.close();        
    } 
    catch (Exception ex)
    {
        ex.printStackTrace();
    }
    
}

public void serialEvent(SerialPortEvent event) {
    
        switch(event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            System.out.println("Some data available");
            
            byte[] readBuffer = new byte[20];

            try {
                
                while (is.available() > 0) {
                    int numBytes = is.read(readBuffer);
                }
                
                System.out.println(new String(readBuffer));
                
            } 
            catch (IOException e) {
                    e.printStackTrace();
                }
            break;
        }
    }

    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUINew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JTable aTable;
    private javax.swing.JButton clear;
    private myPanel dArea;
    private javax.swing.JToggleButton draw;
    private javax.swing.JButton exit;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton send;
    private javax.swing.JButton portSet;
    // End of variables declaration         
    
    private portProp prop;
    private boolean isSet=false;        
    
    private InputStream is;
}
