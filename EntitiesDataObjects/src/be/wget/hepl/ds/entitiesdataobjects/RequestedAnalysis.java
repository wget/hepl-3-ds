/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wget.hepl.ds.entitiesdataobjects;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wget
 */
@Entity
@Table(name = "requested_analysis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequestedAnalysis.findAll", query = "SELECT r FROM RequestedAnalysis r")
    , @NamedQuery(name = "RequestedAnalysis.findByRequestId", query = "SELECT r FROM RequestedAnalysis r WHERE r.requestedAnalysisPK.requestId = :requestId")
    , @NamedQuery(name = "RequestedAnalysis.findByAnalysisId", query = "SELECT r FROM RequestedAnalysis r WHERE r.requestedAnalysisPK.analysisId = :analysisId")
    , @NamedQuery(name = "RequestedAnalysis.findByValue", query = "SELECT r FROM RequestedAnalysis r WHERE r.value = :value")})
public class RequestedAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RequestedAnalysisPK requestedAnalysisPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private Double value;
    @JoinColumn(name = "analysis_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Analysis analysis;
    @JoinColumn(name = "request_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Request request;

    public RequestedAnalysis() {
    }

    public RequestedAnalysis(RequestedAnalysisPK requestedAnalysisPK) {
        this.requestedAnalysisPK = requestedAnalysisPK;
    }

    public RequestedAnalysis(int requestId, int analysisId) {
        this.requestedAnalysisPK = new RequestedAnalysisPK(requestId, analysisId);
    }

    public RequestedAnalysisPK getRequestedAnalysisPK() {
        return requestedAnalysisPK;
    }

    public void setRequestedAnalysisPK(RequestedAnalysisPK requestedAnalysisPK) {
        this.requestedAnalysisPK = requestedAnalysisPK;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requestedAnalysisPK != null ? requestedAnalysisPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestedAnalysis)) {
            return false;
        }
        RequestedAnalysis other = (RequestedAnalysis) object;
        if ((this.requestedAnalysisPK == null && other.requestedAnalysisPK != null) || (this.requestedAnalysisPK != null && !this.requestedAnalysisPK.equals(other.requestedAnalysisPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.wget.hepl.ds.entitiesdataobjects.RequestedAnalysis[ requestedAnalysisPK=" + requestedAnalysisPK + " ]";
    }
    
}
