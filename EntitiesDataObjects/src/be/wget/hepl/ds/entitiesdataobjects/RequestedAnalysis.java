/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wget.hepl.ds.entitiesdataobjects;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
    , @NamedQuery(name = "RequestedAnalysis.findByRequestId", query = "SELECT r FROM RequestedAnalysis r WHERE r.requestId = :requestId")
    , @NamedQuery(name = "RequestedAnalysis.findByAnalysisId", query = "SELECT r FROM RequestedAnalysis r WHERE r.analysisId = :analysisId")
    , @NamedQuery(name = "RequestedAnalysis.findByValue", query = "SELECT r FROM RequestedAnalysis r WHERE r.value = :value")
    , @NamedQuery(name = "RequestedAnalysis.findById", query = "SELECT r FROM RequestedAnalysis r WHERE r.id = :id")})
public class RequestedAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "request_id")
    private int requestId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "analysis_id")
    private int analysisId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private Double value;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Integer id;

    public RequestedAnalysis() {
    }

    public RequestedAnalysis(Integer id) {
        this.id = id;
    }

    public RequestedAnalysis(Integer id, int requestId, int analysisId) {
        this.id = id;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestedAnalysis)) {
            return false;
        }
        RequestedAnalysis other = (RequestedAnalysis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.wget.hepl.ds.entitiesdataobjects.RequestedAnalysis[ id=" + id + " ]";
    }
    
}
