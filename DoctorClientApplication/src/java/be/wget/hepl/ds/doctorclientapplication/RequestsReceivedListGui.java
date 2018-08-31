/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wget.hepl.ds.doctorclientapplication;

import be.wget.hepl.ds.ejbremoteinterfaces.AnalysisSessionBeanRemote;
import be.wget.hepl.ds.entitiesdataobjects.Analysis;
import be.wget.hepl.ds.entitiesdataobjects.Request;
import be.wget.hepl.ds.entitiesdataobjects.RequestedAnalysis;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wget
 */
public class RequestsReceivedListGui extends javax.swing.JDialog implements ListSelectionListener{

    private ArrayList<Request> requests;
    private AnalysisSessionBeanRemote analysisSessionBean;
    
    /**
     * Creates new form RequestsReceivedListGui
     */
    public RequestsReceivedListGui(java.awt.Frame parent, ArrayList<Request> requests, AnalysisSessionBeanRemote analysisSessionBean) {
        super(parent, true);
        initComponents();
        this.requestsReceivedTable.getSelectionModel().addListSelectionListener(this);
        
        
        this.requests = requests;
        this.analysisSessionBean = analysisSessionBean;
        
        
        DefaultTableModel dtm = (DefaultTableModel) this.requestsReceivedTable.getModel();
        for (Request request: requests) {
            dtm.addRow(new Object[] {request.getId(), request.getDatetimeRequest(), request.getPatientId()});
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

        requestsReceivedLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        requestsReceivedTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        requestsReceivedLabel.setText("Requests received:");

        requestsReceivedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Date", "Patient id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
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
        jScrollPane1.setViewportView(requestsReceivedTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(requestsReceivedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(requestsReceivedLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel requestsReceivedLabel;
    private javax.swing.JTable requestsReceivedTable;
    // End of variables declaration//GEN-END:variables

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() != this.requestsReceivedTable.getSelectionModel()) {
            return;
        }

        // Happens when reordering table content or when declining order when
        // the order has been viewed and we have list focus.
        if (this.requestsReceivedTable.getSelectedRow() == -1) {
            return;
        }

        int requestId = (Integer)this.requestsReceivedTable.getValueAt(this.requestsReceivedTable.getSelectedRow(), 0);
        
        ArrayList<RequestedAnalysis> filteredRequestedAnalysis = this.analysisSessionBean.getRequestsResults(requestId);
        
        ArrayList<Analysis> defaultAnalysis = this.analysisSessionBean.getAvailableAnalysis();
        
        ResultsReceivedListGui resultsReceivedListGui = new ResultsReceivedListGui(this, filteredRequestedAnalysis, defaultAnalysis);
        resultsReceivedListGui.setVisible(true);
    }
}
