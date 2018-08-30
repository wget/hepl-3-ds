/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.wget.hepl.ds.entitiesdataobjects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author wget
 */
@Entity
@Table(name = "requests")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Request.findAll", query = "SELECT r FROM Request r")
    , @NamedQuery(name = "Request.findById", query = "SELECT r FROM Request r WHERE r.id = :id")
    , @NamedQuery(name = "Request.findByDatetimeRequest", query = "SELECT r FROM Request r WHERE r.datetimeRequest = :datetimeRequest")
    , @NamedQuery(name = "Request.findByUrgentFlag", query = "SELECT r FROM Request r WHERE r.urgentFlag = :urgentFlag")})
public class Request implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    private Collection<RequestedAnalysis> requestedAnalysisCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "datetime_request")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeRequest;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "urgent_flag")
    private String urgentFlag;
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne
    private Doctor doctorId;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne
    private Patient patientId;

    public Request() {
    }

    public Request(Integer id) {
        this.id = id;
    }

    public Request(Integer id, String urgentFlag) {
        this.id = id;
        this.urgentFlag = urgentFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatetimeRequest() {
        return datetimeRequest;
    }

    public void setDatetimeRequest(Date datetimeRequest) {
        this.datetimeRequest = datetimeRequest;
    }

    public String getUrgentFlag() {
        return urgentFlag;
    }

    public void setUrgentFlag(String urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    public Doctor getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Doctor doctorId) {
        this.doctorId = doctorId;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
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
        if (!(object instanceof Request)) {
            return false;
        }
        Request other = (Request) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "be.wget.hepl.ds.entitiesdataobjects.Request[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<RequestedAnalysis> getRequestedAnalysisCollection() {
        return requestedAnalysisCollection;
    }

    public void setRequestedAnalysisCollection(Collection<RequestedAnalysis> requestedAnalysisCollection) {
        this.requestedAnalysisCollection = requestedAnalysisCollection;
    }
    
}
