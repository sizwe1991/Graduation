/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 2015127
 */
@Entity
@Table(name = "qualification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Qualification.findAll", query = "SELECT q FROM Qualification q"),
    @NamedQuery(name = "Qualification.findByQualCode", query = "SELECT q FROM Qualification q WHERE q.qualCode = :qualCode"),
    @NamedQuery(name = "Qualification.findByQualName", query = "SELECT q FROM Qualification q WHERE q.qualName = :qualName"),
    @NamedQuery(name = "Qualification.findByQualLevel", query = "SELECT q FROM Qualification q WHERE q.qualLevel = :qualLevel"),
    @NamedQuery(name = "Qualification.findByQualCredit", query = "SELECT q FROM Qualification q WHERE q.qualCredit = :qualCredit")})
public class Qualification implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "qual_code")
    private String qualCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "qual_name")
    private String qualName;
    @Size(max = 255)
    @Column(name = "qual_level")
    private String qualLevel;
    @Column(name = "qual_credit")
    private Long qualCredit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "qualCode")
    private Collection<Campusqualification> campusqualificationCollection;
    @JoinColumn(name = "faculty_id", referencedColumnName = "faculty_id")
    @ManyToOne(optional = false)
    private Faculty facultyId;

    public Qualification() {
    }

    public Qualification(String qualCode) {
        this.qualCode = qualCode;
    }

    public Qualification(String qualCode, String qualName) {
        this.qualCode = qualCode;
        this.qualName = qualName;
    }

    public String getQualCode() {
        return qualCode;
    }

    public void setQualCode(String qualCode) {
        this.qualCode = qualCode;
    }

    public String getQualName() {
        return qualName;
    }

    public void setQualName(String qualName) {
        this.qualName = qualName;
    }

    public String getQualLevel() {
        return qualLevel;
    }

    public void setQualLevel(String qualLevel) {
        this.qualLevel = qualLevel;
    }

    public Long getQualCredit() {
        return qualCredit;
    }

    public void setQualCredit(Long qualCredit) {
        this.qualCredit = qualCredit;
    }

    @XmlTransient
    public Collection<Campusqualification> getCampusqualificationCollection() {
        return campusqualificationCollection;
    }

    public void setCampusqualificationCollection(Collection<Campusqualification> campusqualificationCollection) {
        this.campusqualificationCollection = campusqualificationCollection;
    }

    public Faculty getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Faculty facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (qualCode != null ? qualCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Qualification)) {
            return false;
        }
        Qualification other = (Qualification) object;
        if ((this.qualCode == null && other.qualCode != null) || (this.qualCode != null && !this.qualCode.equals(other.qualCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.Qualification[ qualCode=" + qualCode + " ]";
    }
    
}
