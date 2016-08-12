/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2015127
 */
@Entity
@Table(name = "fraud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fraud.findAll", query = "SELECT f FROM Fraud f"),
    @NamedQuery(name = "Fraud.findByFraudId", query = "SELECT f FROM Fraud f WHERE f.fraudId = :fraudId"),
    @NamedQuery(name = "Fraud.findByAdmitStno", query = "SELECT f FROM Fraud f WHERE f.admitStno = :admitStno"),
    @NamedQuery(name = "Fraud.findByUsername", query = "SELECT f FROM Fraud f WHERE f.username = :username"),
    @NamedQuery(name = "Fraud.findByDate", query = "SELECT f FROM Fraud f WHERE f.date = :date"),
    @NamedQuery(name = "Fraud.findByTime", query = "SELECT f FROM Fraud f WHERE f.time = :time"),
    @NamedQuery(name = "Fraud.findByStatus", query = "SELECT f FROM Fraud f WHERE f.status = :status")})
public class Fraud implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fraud_id")
    private Integer fraudId;
    @Size(max = 255)
    @Column(name = "admit_stno")
    private String admitStno;
    @Size(max = 255)
    @Column(name = "username")
    private String username;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Size(max = 255)
    @Column(name = "time")
    private String time;
    @Size(max = 255)
    @Column(name = "status")
    private String status;

    public Fraud() {
    }

    public Fraud(Integer fraudId) {
        this.fraudId = fraudId;
    }

    public Integer getFraudId() {
        return fraudId;
    }

    public void setFraudId(Integer fraudId) {
        this.fraudId = fraudId;
    }

    public String getAdmitStno() {
        return admitStno;
    }

    public void setAdmitStno(String admitStno) {
        this.admitStno = admitStno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fraudId != null ? fraudId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fraud)) {
            return false;
        }
        Fraud other = (Fraud) object;
        if ((this.fraudId == null && other.fraudId != null) || (this.fraudId != null && !this.fraudId.equals(other.fraudId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.Fraud[ fraudId=" + fraudId + " ]";
    }
    
}
