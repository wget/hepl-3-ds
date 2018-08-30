/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wget.hepl.ds.ejbremoteinterfaces;

import be.wget.hepl.ds.entitiesdataobjects.Analysis;
import be.wget.hepl.ds.entitiesdataobjects.Patient;
import be.wget.hepl.ds.entitiesdataobjects.Request;
import be.wget.hepl.ds.entitiesdataobjects.RequestedAnalysis;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author wget
 */
@Remote
public interface AnalysisSessionBeanRemote {

    void setPatient(final Patient patient);

    ArrayList<Analysis> getAvailableAnalysis();

    Request setRequestedAnalysis(ArrayList<Analysis> analysis, boolean urgent);

    void loginDoctor(final String loginString);

    void setAnalysisResults(final ArrayList<RequestedAnalysis> results);
    
}
