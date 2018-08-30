/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wget.hepl.ds.patientejb;

import be.wget.hepl.ds.ejbremoteinterfaces.PatientSessionBeanRemote;
import be.wget.hepl.ds.entitiesdataobjects.Patient;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author wget
 */
@Stateless
public class PatientSessionBean implements PatientSessionBeanRemote {

    @Override
    public ArrayList<Patient> getPatients() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntitiesDataObjectsPU");
        EntityManager em = emf.createEntityManager();
        List<Patient> patients = em.createNamedQuery(
            "Patient.findAll",
            Patient.class).getResultList();
        ArrayList<Patient> patientsArray = new ArrayList<>();
        for (Patient patient: patients) {
            patientsArray.add(patient);
        }
        return patientsArray;
    }

    @Override
    public void setPatient(final Patient patient) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntitiesDataObjectsPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(patient);
        tx.commit();
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
