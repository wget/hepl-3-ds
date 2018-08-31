/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wget.hepl.ds.analysisejb;

import be.wget.hepl.ds.ejbremoteinterfaces.AnalysisSessionBeanRemote;
import be.wget.hepl.ds.entitiesdataobjects.Analysis;
import be.wget.hepl.ds.entitiesdataobjects.Doctor;
import be.wget.hepl.ds.entitiesdataobjects.Patient;
import be.wget.hepl.ds.entitiesdataobjects.Request;
import be.wget.hepl.ds.entitiesdataobjects.RequestedAnalysis;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author wget
 */
@Stateful(name="AnalysisSessionBean", mappedName="ejb/AnalysisSessionBean")
public class AnalysisSessionBean implements AnalysisSessionBeanRemote {

    @Resource(mappedName = "jms/be.wget.hepl.ds.topic")
    private Topic topic;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    @Resource(mappedName = "jms/be.wget.hepl.ds.queue")
    private Queue queue;
    
    private Patient patient;
    private Doctor doctor;
    
    @Resource
    SessionContext sessionContext;

    @Override
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public ArrayList<Analysis> getAvailableAnalysis() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntitiesDataObjectsPU");
        EntityManager em = emf.createEntityManager();
        List<Analysis> analyses = em.createNamedQuery(
            "Analysis.findAll",
            Analysis.class).getResultList();
        ArrayList<Analysis> analysisArray = new ArrayList<>();
        for (Analysis analysis: analyses) {
            analysisArray.add(analysis);
        }
        return analysisArray;
    }

    @Override
    public Request setRequestedAnalysis(ArrayList<Analysis> analyses, boolean urgent) {
        Request request = new Request();
        request.setPatientId(this.patient.getId());
        request.setDoctorId(this.doctor.getId());
        request.setDatetimeRequest(new Date());
        request.setUrgentFlag("0");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntitiesDataObjectsPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(request);
        tx.commit();
        
        for (Analysis analysis: analyses) {
            System.out.println("DEBUG request id: " + request.getId());
            RequestedAnalysis ra = new RequestedAnalysis();
            ra.setAnalysisId(analysis.getId());
            ra.setRequestId(request.getId());
            tx.begin();
            em.persist(ra);
            tx.commit();
        }
        
        this.sendRequestToLabo(request);
        
        return request;
    }
    
    

    @Override
    public boolean loginDoctor() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntitiesDataObjectsPU");
        EntityManager em = emf.createEntityManager();
        List<Doctor> doctors = em.createNamedQuery("Doctor.findAll").getResultList();
            /*"Doctor.findByLogin")
                .setParameter("login", this.sessionContext.getCallerPrincipal().getName()).getResultList();*/
        if (doctors.get(0) == null) {
            return false;
        }
        
        this.doctor = doctors.get(0);
        return true;
    }

    private void sendRequestToLabo(Request req) {
        context.createProducer().send(queue, req);
    }

    @Override
    public void setAnalysisResults(final ArrayList<RequestedAnalysis> results) {
        
        // Receive results from labo scientist and persist them.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntitiesDataObjectsPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(results);
        tx.commit();

        // Get the requests for the matching results
        List<Request> requests = em.createNamedQuery(
            "Request.findById")
                .setParameter("id", results.get(0).getRequestId()).getResultList();
        
        // Send on topic if this is urgent
        if (requests.get(0).getUrgentFlag().equals("true")) {
            this.sendRequestAnswerToDoctor(requests.get(0));
        }
    }

    private void sendRequestAnswerToDoctor(Request messageData) {
        context.createProducer().send(topic, messageData);
    }

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public String getCallerPrincipalName() {
        return this.sessionContext.getCallerPrincipal().getName();
    }
    
    
}
