/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wget.hepl.ds.entitiesdataobjects;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author wget
 */
@Embeddable
public class RequestedAnalysisPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "request_id")
    private int requestId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "analysis_id")
    private int analysisId;

    public RequestedAnalysisPK() {
    }

    public RequestedAnalysisPK(int requestId, int analysisId) {
        this.requestId = requestId;
        this.analysisId = analysisId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(int analysisId) {
        this.analysisId = analysisId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) requestId;
        hash += (int) analysisId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestedAnalysisPK)) {
            return false;
        }
        RequestedAnalysisPK other = (RequestedAnalysisPK) object;
        if (this.requestId != other.requestId) {
            return false;
        }
        if (this.analysisId != other.analysisId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.wget.hepl.ds.entitiesdataobjects.RequestedAnalysisPK[ requestId=" + requestId + ", analysisId=" + analysisId + " ]";
    }
    
}
