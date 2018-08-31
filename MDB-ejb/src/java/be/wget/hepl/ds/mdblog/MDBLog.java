/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wget.hepl.ds.mdblog;

import be.wget.hepl.ds.entitiesdataobjects.Log;
import be.wget.hepl.ds.entitiesdataobjects.Request;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author wget
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "jms/be.wget.hepl.ds.topic")
    ,
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/be.wget.hepl.ds.topic")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/be.wget.hepl.ds.topic")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class MDBLog implements MessageListener {
    
    public MDBLog() {
    }
    
    @Override
    public void onMessage(Message message) {
        ObjectMessage tm = (ObjectMessage)message;
        try {
            if (!tm.getStringProperty("destination").equals("mdb")) {
                System.out.println("DEBUG : message received not concerning MDB. Skipping...");
                return;
            }
                    
            ArrayList<String> payload = (ArrayList<String>)tm.getObject();

            int requestId = Integer.parseInt(payload.get(0));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date dateResult = df.parse(payload.get(1));

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntitiesDataObjectsPU");
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            List<Request> requests = em.createNamedQuery(
            "Request.findById")
                .setParameter("id", requestId).getResultList();
        
            int diff = (int) (dateResult.getTime() - requests.get(0).getDatetimeRequest().getTime());
            diff /= 1000;
            
            Log log = new Log();
            log.setValue("Request " + requestId + " took " + diff + " seconds to be resulted");
            
            tx.begin();
            em.persist(log);
            tx.commit();
        } catch (JMSException ex) {
            Logger.getLogger(MDBLog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MDBLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
