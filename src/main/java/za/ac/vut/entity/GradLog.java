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
import javax.persistence.Lob;
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
@Table(name = "grad_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GradLog.findAll", query = "SELECT g FROM GradLog g"),
    @NamedQuery(name = "GradLog.findByLogId", query = "SELECT g FROM GradLog g WHERE g.logId = :logId"),
    @NamedQuery(name = "GradLog.findByLogDate", query = "SELECT g FROM GradLog g WHERE g.logDate = :logDate"),
    @NamedQuery(name = "GradLog.findByLogTime", query = "SELECT g FROM GradLog g WHERE g.logTime = :logTime"),
    @NamedQuery(name = "GradLog.findByLogType", query = "SELECT g FROM GradLog g WHERE g.logType = :logType")})
public class GradLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "log_id")
    private Integer logId;
    @Column(name = "log_date")
    @Temporal(TemporalType.DATE)
    private Date logDate;
    @Size(max = 255)
    @Column(name = "log_time")
    private String logTime;
    @Size(max = 255)
    @Column(name = "log_type")
    private String logType;
    @Lob
    @Size(max = 65535)
    @Column(name = "comment")
    private String comment;

    public GradLog() {
    }

    public GradLog(Integer logId) {
        this.logId = logId;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logId != null ? logId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GradLog)) {
            return false;
        }
        GradLog other = (GradLog) object;
        if ((this.logId == null && other.logId != null) || (this.logId != null && !this.logId.equals(other.logId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.GradLog[ logId=" + logId + " ]";
    }
    
}
