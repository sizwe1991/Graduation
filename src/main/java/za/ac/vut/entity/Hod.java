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
@Table(name = "hod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hod.findAll", query = "SELECT h FROM Hod h"),
    @NamedQuery(name = "Hod.findByHodId", query = "SELECT h FROM Hod h WHERE h.hodId = :hodId"),
    @NamedQuery(name = "Hod.findByAdmitStno", query = "SELECT h FROM Hod h WHERE h.admitStno = :admitStno"),
    @NamedQuery(name = "Hod.findByUsername", query = "SELECT h FROM Hod h WHERE h.username = :username"),
    @NamedQuery(name = "Hod.findByDate", query = "SELECT h FROM Hod h WHERE h.date = :date"),
    @NamedQuery(name = "Hod.findByTime", query = "SELECT h FROM Hod h WHERE h.time = :time"),
    @NamedQuery(name = "Hod.findByStatus", query = "SELECT h FROM Hod h WHERE h.status = :status")})
public class Hod implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hod_id")
    private Integer hodId;
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

    public Hod() {
    }

    public Hod(Integer hodId) {
        this.hodId = hodId;
    }

    public Integer getHodId() {
        return hodId;
    }

    public void setHodId(Integer hodId) {
        this.hodId = hodId;
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
        hash += (hodId != null ? hodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hod)) {
            return false;
        }
        Hod other = (Hod) object;
        if ((this.hodId == null && other.hodId != null) || (this.hodId != null && !this.hodId.equals(other.hodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.Hod[ hodId=" + hodId + " ]";
    }
    
}
