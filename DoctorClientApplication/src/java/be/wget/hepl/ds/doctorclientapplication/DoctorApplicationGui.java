/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wget.hepl.ds.doctorclientapplication;

import be.wget.hepl.ds.ejbremoteinterfaces.AnalysisSessionBeanRemote;
import be.wget.hepl.ds.ejbremoteinterfaces.PatientSessionBeanRemote;
import be.wget.hepl.ds.entitiesdataobjects.Analysis;
import be.wget.hepl.ds.entitiesdataobjects.Patient;
import be.wget.hepl.ds.entitiesdataobjects.Request;
import be.wget.hepl.ds.entitiesdataobjects.RequestedAnalysis;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author wget
 */
public class DoctorApplicationGui
        extends javax.swing.JFrame
        implements ListSelectionListener, MessageListener {

    private AnalysisSessionBeanRemote analysisSessionBean;
    private PatientSessionBeanRemote patientSessionBean;
    private ArrayList<Patient> patientModel;
    
    private ArrayList<Request> requestResultsReceived;
    
    private ArrayList<Analysis> defaultAnalyses;
    
    /**
     * Creates new form PatientsModifyGui
     */
    public DoctorApplicationGui() {
        initComponents();
        
        this.setTitle("Doctor application");
        this.setLocationRelativeTo(null);

        this.patientModel = new ArrayList<>();
        this.requestResultsReceived = new ArrayList<>();
        this.defaultAnalyses = new ArrayList<>();
        this.patientsList.setModel(new DefaultListModel<>());
        this.patientsList.addListSelectionListener(this);
        this.patientUpdateButton.setEnabled(false);
        
        Context context;
        try {
            context = new InitialContext();
            this.analysisSessionBean =
                (AnalysisSessionBeanRemote)context.lookup("ejb/AnalysisSessionBean");
            this.patientSessionBean =
                (PatientSessionBeanRemote)context.lookup("ejb/PatientSessionBean");
            System.out.println(analysisSessionBean.sayHello("wget"));
            System.out.println(analysisSessionBean.getCallerPrincipalName());

            ConnectionFactory connectionFactory = 
                (ConnectionFactory)context.lookup("java:comp/DefaultJMSConnectionFactory");
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            Destination destination =
                (Destination)context.lookup("jms/be.wget.hepl.ds.topic");
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(this);
            connection.start();
            
            DoctorConnectionGui doctorConnectionGui = new DoctorConnectionGui(this);
            doctorConnectionGui.setVisible(true);
            //if (doctorConnectionGui.isConnectionRequested()) {
                this.analysisSessionBean.loginDoctor();
                
                this.defaultAnalyses = this.analysisSessionBean.getAvailableAnalysis();
            //} else {
            //    System.exit(0);
            //}
            
        } catch (NamingException ex) {
            Logger.getLogger(DoctorApplicationGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(DoctorApplicationGui.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.getPatientsModel();
        this.updatePatientsListUi();
    }
    
    private void getPatientsModel() {
        this.patientModel = this.patientSessionBean.getPatients();
    }
    
    private void updatePatientsListUi() {
        DefaultListModel<String> patientListModel = (DefaultListModel<String>)patientsList.getModel();
        patientListModel.clear();
        for (Patient patient: this.patientModel) {
            patientListModel.addElement(
                patient.getId() +
                ": " +
                patient.getFirstname() +
                " " +
                patient.getLastname() +
                " (login: " +
                patient.getLogin() + ")");
        }
        if (this.patientModel.isEmpty()) {
            this.patientUpdateButton.setEnabled(false);
        } else {
            this.patientUpdateButton.setEnabled(true);
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

        patientsListLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        patientsList = new javax.swing.JList<>();
        modifySelectPatientLabel = new javax.swing.JLabel();
        patientFirstNameLabel = new javax.swing.JLabel();
        patientFirstNameTextfield = new javax.swing.JTextField();
        patientLastNameLabel = new javax.swing.JLabel();
        patientLastNameTextfield = new javax.swing.JTextField();
        patientLoginLabel = new javax.swing.JLabel();
        patientLoginTextfield = new javax.swing.JTextField();
        patientUpdateButton = new javax.swing.JButton();
        patientAddButton = new javax.swing.JButton();
        selectPatientButton = new javax.swing.JButton();
        readRequestReceivedButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        patientsListLabel.setText("Patients available in the database:");

        jScrollPane1.setViewportView(patientsList);

        modifySelectPatientLabel.setText("Modify patient:");

        patientFirstNameLabel.setText("First name:");

        patientLastNameLabel.setText("Last name:");

        patientLoginLabel.setText("Login:");

        patientUpdateButton.setText("Update patient");
        patientUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientUpdateButtonActionPerformed(evt);
            }
        });

        patientAddButton.setText("Add patient");
        patientAddButton.setPreferredSize(new java.awt.Dimension(140, 31));
        patientAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientAddButtonActionPerformed(evt);
            }
        });

        selectPatientButton.setText("Select patient");
        selectPatientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectPatientButtonActionPerformed(evt);
            }
        });

        readRequestReceivedButton.setText("Read request received");
        readRequestReceivedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readRequestReceivedButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(patientsListLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(modifySelectPatientLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(patientFirstNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(patientLastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(patientLoginLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(patientLastNameTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                            .addComponent(patientLoginTextfield)
                            .addComponent(patientFirstNameTextfield)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(patientAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectPatientButton, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(readRequestReceivedButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(patientUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(patientsListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(modifySelectPatientLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(patientFirstNameTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patientFirstNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(patientLastNameLabel)
                    .addComponent(patientLastNameTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(patientLoginLabel)
                    .addComponent(patientLoginTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(patientAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patientUpdateButton)
                    .addComponent(selectPatientButton)
                    .addComponent(readRequestReceivedButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void patientUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientUpdateButtonActionPerformed
        if (this.patientsList.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(
                this,
                "You need to select a patient in order to be able to update him/her.",
                "No patient selected",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (this.patientLoginTextfield.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "The login cannot be empty",
                "Login empty",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Patient patient = this.patientModel.get(this.patientsList.getSelectedIndex());
        patient.setFirstname(this.patientFirstNameTextfield.getText());
        patient.setLastname(this.patientLastNameTextfield.getText());
        patient.setLogin(this.patientLoginTextfield.getText());
        System.out.println("DEBUG " + patient.getId());
        
        // Update model
        this.patientSessionBean.setPatient(patient);
        this.patientModel = this.patientSessionBean.getPatients();
        
        // Update UI
        this.updatePatientsListUi();
    }//GEN-LAST:event_patientUpdateButtonActionPerformed

    private void patientAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientAddButtonActionPerformed
        
        if (this.patientLoginTextfield.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "The login cannot be empty",
                "Login empty",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Patient patient = new Patient();
        patient.setFirstname(this.patientFirstNameTextfield.getText());
        patient.setLastname(this.patientLastNameTextfield.getText());
        patient.setLogin(this.patientLoginTextfield.getText());
        
        // Update model
        this.patientSessionBean.setPatient(patient);
        this.patientModel = this.patientSessionBean.getPatients();
        
        // Update UI
        this.updatePatientsListUi();
    }//GEN-LAST:event_patientAddButtonActionPerformed

    private void selectPatientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectPatientButtonActionPerformed
        if (this.patientsList.getSelectedIndex() == -1) {
            return;
        }
        
        Patient patient = this.patientModel.get(this.patientsList.getSelectedIndex());
        
        this.analysisSessionBean.setPatient(patient);

        ArrayList<Analysis> analyses = this.analysisSessionBean.getAvailableAnalysis();
        AnalysisSelectorGui analysisListGui = new AnalysisSelectorGui(this, analyses);
        analysisListGui.setVisible(true);
        if (analysisListGui.hasBeenCancelled()) {
            analysisListGui.dispose();
            return;
        }
        
        ArrayList<Analysis> selectedAnalyses = analysisListGui.getSelectedAnalyses();
        if (selectedAnalyses.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "You haven't selected an analysis. Please select one.",
                "No analysis selected",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean isUrgent = analysisListGui.isUrgent();
        analysisListGui.dispose();
        
        Request request = this.analysisSessionBean.setRequestedAnalysis(selectedAnalyses, isUrgent);
        JOptionPane.showMessageDialog(
            this,
            "Your request id is " + request.getId(),
            "Request id",
            JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_selectPatientButtonActionPerformed

    private void readRequestReceivedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readRequestReceivedButtonActionPerformed

        
        RequestsReceivedListGui requestsReceivedListGui = new RequestsReceivedListGui(this, this.requestResultsReceived, this.analysisSessionBean);
        requestsReceivedListGui.setVisible(true);
    }//GEN-LAST:event_readRequestReceivedButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel modifySelectPatientLabel;
    private javax.swing.JButton patientAddButton;
    private javax.swing.JLabel patientFirstNameLabel;
    private javax.swing.JTextField patientFirstNameTextfield;
    private javax.swing.JLabel patientLastNameLabel;
    private javax.swing.JTextField patientLastNameTextfield;
    private javax.swing.JLabel patientLoginLabel;
    private javax.swing.JTextField patientLoginTextfield;
    private javax.swing.JButton patientUpdateButton;
    private javax.swing.JList<String> patientsList;
    private javax.swing.JLabel patientsListLabel;
    private javax.swing.JButton readRequestReceivedButton;
    private javax.swing.JButton selectPatientButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() != this.patientsList) {
            return;
        }
        // Happens when redisplaying the table and not necessary when the user
        // selects an user from the list.
        if (this.patientsList.getSelectedIndex() == -1) {
            return;
        }
        
        Patient patient = this.patientModel.get(this.patientsList.getSelectedIndex());
        this.patientFirstNameTextfield.setText(patient.getFirstname());
        this.patientLastNameTextfield.setText(patient.getLastname());
        this.patientLoginTextfield.setText(patient.getLogin());
    }

    @Override
    public void onMessage(Message message) {
        try {
            
            if (!((ObjectMessage)message).getStringProperty("destination").equals("doctorapp")) {
                return;
            }
            
            Request requestReceived = (Request)((ObjectMessage)message).getObject();
            
            this.requestResultsReceived.add(requestReceived);
            
            if (requestReceived.getUrgentFlag().equals("true")) {
                JOptionPane.showMessageDialog(
                    this,
                    "Your request id is " + requestReceived.getId(),
                    "Urgent request received",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (JMSException ex) {
            Logger.getLogger(DoctorApplicationGui.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
}
