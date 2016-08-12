/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.entity;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2015127
 */
@Entity
@Table(name = "email_configuration")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmailConfiguration.findAll", query = "SELECT e FROM EmailConfiguration e"),
    @NamedQuery(name = "EmailConfiguration.findByConfigurationNo", query = "SELECT e FROM EmailConfiguration e WHERE e.configurationNo = :configurationNo"),
    @NamedQuery(name = "EmailConfiguration.findByExamSubject", query = "SELECT e FROM EmailConfiguration e WHERE e.examSubject = :examSubject"),
    @NamedQuery(name = "EmailConfiguration.findByExamBody", query = "SELECT e FROM EmailConfiguration e WHERE e.examBody = :examBody"),
    @NamedQuery(name = "EmailConfiguration.findByFraudSubjectApprove", query = "SELECT e FROM EmailConfiguration e WHERE e.fraudSubjectApprove = :fraudSubjectApprove"),
    @NamedQuery(name = "EmailConfiguration.findByFraudBodyApprove", query = "SELECT e FROM EmailConfiguration e WHERE e.fraudBodyApprove = :fraudBodyApprove"),
    @NamedQuery(name = "EmailConfiguration.findByFraudSubjectReject", query = "SELECT e FROM EmailConfiguration e WHERE e.fraudSubjectReject = :fraudSubjectReject"),
    @NamedQuery(name = "EmailConfiguration.findByFraudBodyReject", query = "SELECT e FROM EmailConfiguration e WHERE e.fraudBodyReject = :fraudBodyReject"),
    @NamedQuery(name = "EmailConfiguration.findByHodSubjectApprove", query = "SELECT e FROM EmailConfiguration e WHERE e.hodSubjectApprove = :hodSubjectApprove"),
    @NamedQuery(name = "EmailConfiguration.findByHodBodyApprove", query = "SELECT e FROM EmailConfiguration e WHERE e.hodBodyApprove = :hodBodyApprove"),
    @NamedQuery(name = "EmailConfiguration.findByHodSubjectReject", query = "SELECT e FROM EmailConfiguration e WHERE e.hodSubjectReject = :hodSubjectReject"),
    @NamedQuery(name = "EmailConfiguration.findByHodBodyReject", query = "SELECT e FROM EmailConfiguration e WHERE e.hodBodyReject = :hodBodyReject")})
public class EmailConfiguration implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "configurationNo")
    private Integer configurationNo;
    @Size(max = 1000)
    @Column(name = "examSubject")
    private String examSubject;
    @Size(max = 1000)
    @Column(name = "examBody")
    private String examBody;
    @Size(max = 1000)
    @Column(name = "fraudSubjectApprove")
    private String fraudSubjectApprove;
    @Size(max = 1000)
    @Column(name = "fraudBodyApprove")
    private String fraudBodyApprove;
    @Size(max = 1000)
    @Column(name = "fraudSubjectReject")
    private String fraudSubjectReject;
    @Size(max = 1000)
    @Column(name = "fraudBodyReject")
    private String fraudBodyReject;
    @Size(max = 1000)
    @Column(name = "hodSubjectApprove")
    private String hodSubjectApprove;
    @Size(max = 1000)
    @Column(name = "hodBodyApprove")
    private String hodBodyApprove;
    @Size(max = 1000)
    @Column(name = "hodSubjectReject")
    private String hodSubjectReject;
    @Size(max = 1000)
    @Column(name = "hodBodyReject")
    private String hodBodyReject;

    public EmailConfiguration() {
    }

    public EmailConfiguration(Integer configurationNo) {
        this.configurationNo = configurationNo;
    }

    public Integer getConfigurationNo() {
        return configurationNo;
    }

    public void setConfigurationNo(Integer configurationNo) {
        this.configurationNo = configurationNo;
    }

    public String getExamSubject() {
        return examSubject;
    }

    public void setExamSubject(String examSubject) {
        this.examSubject = examSubject;
    }

    public String getExamBody() {
        return examBody;
    }

    public void setExamBody(String examBody) {
        this.examBody = examBody;
    }

    public String getFraudSubjectApprove() {
        return fraudSubjectApprove;
    }

    public void setFraudSubjectApprove(String fraudSubjectApprove) {
        this.fraudSubjectApprove = fraudSubjectApprove;
    }

    public String getFraudBodyApprove() {
        return fraudBodyApprove;
    }

    public void setFraudBodyApprove(String fraudBodyApprove) {
        this.fraudBodyApprove = fraudBodyApprove;
    }

    public String getFraudSubjectReject() {
        return fraudSubjectReject;
    }

    public void setFraudSubjectReject(String fraudSubjectReject) {
        this.fraudSubjectReject = fraudSubjectReject;
    }

    public String getFraudBodyReject() {
        return fraudBodyReject;
    }

    public void setFraudBodyReject(String fraudBodyReject) {
        this.fraudBodyReject = fraudBodyReject;
    }

    public String getHodSubjectApprove() {
        return hodSubjectApprove;
    }

    public void setHodSubjectApprove(String hodSubjectApprove) {
        this.hodSubjectApprove = hodSubjectApprove;
    }

    public String getHodBodyApprove() {
        return hodBodyApprove;
    }

    public void setHodBodyApprove(String hodBodyApprove) {
        this.hodBodyApprove = hodBodyApprove;
    }

    public String getHodSubjectReject() {
        return hodSubjectReject;
    }

    public void setHodSubjectReject(String hodSubjectReject) {
        this.hodSubjectReject = hodSubjectReject;
    }

    public String getHodBodyReject() {
        return hodBodyReject;
    }

    public void setHodBodyReject(String hodBodyReject) {
        this.hodBodyReject = hodBodyReject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configurationNo != null ? configurationNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmailConfiguration)) {
            return false;
        }
        EmailConfiguration other = (EmailConfiguration) object;
        if ((this.configurationNo == null && other.configurationNo != null) || (this.configurationNo != null && !this.configurationNo.equals(other.configurationNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.EmailConfiguration[ configurationNo=" + configurationNo + " ]";
    }
    
}
