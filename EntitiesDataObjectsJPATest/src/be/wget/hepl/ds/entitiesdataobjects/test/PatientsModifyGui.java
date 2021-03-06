/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wget.hepl.ds.entitiesdataobjects.test;

import be.wget.hepl.ds.entitiesdataobjects.Patient;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author wget
 */
public class PatientsModifyGui extends javax.swing.JFrame implements ListSelectionListener {

    private ArrayList<Patient> patientModel;
    private EntityManager em;
    private EntityTransaction tx;
    
    /**
     * Creates new form PatientsModifyGui
     */
    public PatientsModifyGui() {
        initComponents();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntitiesDataObjectsPU");
        this.em = emf.createEntityManager();
        this.tx = this.em.getTransaction();
        
        this.setTitle("JPA test");
        this.setLocationRelativeTo(null);

        this.patientModel = new ArrayList<>();
        this.patientsList.setModel(new DefaultListModel<>());
        this.patientsList.addListSelectionListener(this);
        this.patientUpdateButton.setEnabled(false);
        this.patientRemoveButton.setEnabled(false);

        this.getPatientsModel();
        this.updatePatientsListUi();
    }
    
    private void getPatientsModel() {
        
        // find is only useful when we know the primary key, let's use the
        // default NamedQuery instead
        //while ((patient = em.find(Patient.class, primaryKeyId)) != null) {
        List<Patient> patients = em.createNamedQuery(
            "Patient.findAll",
            Patient.class).getResultList();
        for (Patient patient: patients) {
            this.patientModel.add(patient);
        }
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
            this.patientRemoveButton.setEnabled(false);
            this.patientUpdateButton.setEnabled(false);
        } else {
            this.patientRemoveButton.setEnabled(true);
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
        patientRemoveButton = new javax.swing.JButton();
        patientAddButton = new javax.swing.JButton();

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

        patientRemoveButton.setText("Remove patient");
        patientRemoveButton.setPreferredSize(new java.awt.Dimension(140, 31));
        patientRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientRemoveButtonActionPerformed(evt);
            }
        });

        patientAddButton.setText("Add patient");
        patientAddButton.setPreferredSize(new java.awt.Dimension(140, 31));
        patientAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientAddButtonActionPerformed(evt);
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
                            .addComponent(patientLastNameTextfield)
                            .addComponent(patientLoginTextfield)
                            .addComponent(patientFirstNameTextfield)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(patientRemoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                        .addComponent(patientAddButton, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                        .addComponent(patientUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(patientRemoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patientUpdateButton))
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
        
        // Update model
        this.patientModel.set(this.patientsList.getSelectedIndex(), patient);
        this.tx.begin();
        this.em.persist(patient);
        this.tx.commit();
        
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
        this.patientModel.add(patient);
        this.tx.begin();
        this.em.persist(patient);
        this.tx.commit();
        
        // Update UI
        this.updatePatientsListUi();
    }//GEN-LAST:event_patientAddButtonActionPerformed

    private void patientRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientRemoveButtonActionPerformed
        if (this.patientsList.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(
                this,
                "You need to select a patient in order to be able to remove him/her.",
                "No patient selected",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Patient patient = this.patientModel.get(this.patientsList.getSelectedIndex());
        this.patientModel.remove(patient);
        this.tx.begin();
        this.em.remove(patient);
        this.tx.commit();
        
        // Update UI
        this.updatePatientsListUi();
    }//GEN-LAST:event_patientRemoveButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.setProperty("awt.useSystemAAFontSettings","lcd");
        System.setProperty("swing.aatext", "true");
        
        // Set the GTK look and feel only if it exists.
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(info.getClassName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {}
                break;
            }
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PatientsModifyGui().setVisible(true);
            }
        });
    }

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
    private javax.swing.JButton patientRemoveButton;
    private javax.swing.JButton patientUpdateButton;
    private javax.swing.JList<String> patientsList;
    private javax.swing.JLabel patientsListLabel;
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
}
