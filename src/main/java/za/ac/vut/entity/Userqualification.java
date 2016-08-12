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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2015127
 */
@Entity
@Table(name = "userqualification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userqualification.findAll", query = "SELECT u FROM Userqualification u"),
    @NamedQuery(name = "Userqualification.findByQualUserId", query = "SELECT u FROM Userqualification u WHERE u.qualUserId = :qualUserId")})
public class Userqualification implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "qual_user_id")
    private Integer qualUserId;
    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne
    private User username;
    @JoinColumn(name = "id_camp_qual", referencedColumnName = "id_camp_qual")
    @ManyToOne(optional = false)
    private Campusqualification idCampQual;

    public Userqualification() {
    }

    public Userqualification(Integer qualUserId) {
        this.qualUserId = qualUserId;
    }

    public Integer getQualUserId() {
        return qualUserId;
    }

    public void setQualUserId(Integer qualUserId) {
        this.qualUserId = qualUserId;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public Campusqualification getIdCampQual() {
        return idCampQual;
    }

    public void setIdCampQual(Campusqualification idCampQual) {
        this.idCampQual = idCampQual;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (qualUserId != null ? qualUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userqualification)) {
            return false;
        }
        Userqualification other = (Userqualification) object;
        if ((this.qualUserId == null && other.qualUserId != null) || (this.qualUserId != null && !this.qualUserId.equals(other.qualUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.Userqualification[ qualUserId=" + qualUserId + " ]";
    }
    
}
