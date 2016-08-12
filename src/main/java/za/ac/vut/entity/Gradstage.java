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
@Table(name = "gradstage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gradstage.findAll", query = "SELECT g FROM Gradstage g"),
    @NamedQuery(name = "Gradstage.findByIdgradstage", query = "SELECT g FROM Gradstage g WHERE g.idgradstage = :idgradstage"),
    @NamedQuery(name = "Gradstage.findByStageName", query = "SELECT g FROM Gradstage g WHERE g.stageName = :stageName"),
    @NamedQuery(name = "Gradstage.findByStageNumber", query = "SELECT g FROM Gradstage g WHERE g.stageNumber = :stageNumber")})
public class Gradstage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgradstage")
    private Integer idgradstage;
    @Size(max = 255)
    @Column(name = "stageName")
    private String stageName;
    @Column(name = "stageNumber")
    private Integer stageNumber;

    public Gradstage() {
    }

    public Gradstage(Integer idgradstage) {
        this.idgradstage = idgradstage;
    }

    public Integer getIdgradstage() {
        return idgradstage;
    }

    public void setIdgradstage(Integer idgradstage) {
        this.idgradstage = idgradstage;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Integer getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(Integer stageNumber) {
        this.stageNumber = stageNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgradstage != null ? idgradstage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gradstage)) {
            return false;
        }
        Gradstage other = (Gradstage) object;
        if ((this.idgradstage == null && other.idgradstage != null) || (this.idgradstage != null && !this.idgradstage.equals(other.idgradstage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.Gradstage[ idgradstage=" + idgradstage + " ]";
    }
    
}
