/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wget.hepl.ds.ejbremoteinterfaces;

import be.wget.hepl.ds.entitiesdataobjects.Patient;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author wget
 */
@Remote
public interface PatientSessionBeanRemote {
    public ArrayList<Patient> getPatients();
    public void setPatient(final Patient patient);
}
