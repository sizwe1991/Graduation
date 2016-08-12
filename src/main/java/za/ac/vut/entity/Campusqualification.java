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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "campusqualification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campusqualification.findAll", query = "SELECT c FROM Campusqualification c"),
    @NamedQuery(name = "Campusqualification.findByIdCampQual", query = "SELECT c FROM Campusqualification c WHERE c.idCampQual = :idCampQual"),
    @NamedQuery(name = "Campusqualification.findByOfferingType", query = "SELECT c FROM Campusqualification c WHERE c.offeringType = :offeringType")})
public class Campusqualification implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_camp_qual")
    private Integer idCampQual;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "offering_type")
    private String offeringType;
    @OneToMany(mappedBy = "campusQualId")
    private Collection<GradStudent> gradStudentCollection;
    @JoinColumn(name = "campus_code", referencedColumnName = "campus_code")
    @ManyToOne(optional = false)
    private Campus campusCode;
    @JoinColumn(name = "qual_code", referencedColumnName = "qual_code")
    @ManyToOne(optional = false)
    private Qualification qualCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCampQual")
    private Collection<Userqualification> userqualificationCollection;

    public Campusqualification() {
    }

    public Campusqualification(Integer idCampQual) {
        this.idCampQual = idCampQual;
    }

    public Campusqualification(Integer idCampQual, String offeringType) {
        this.idCampQual = idCampQual;
        this.offeringType = offeringType;
    }

    public Integer getIdCampQual() {
        return idCampQual;
    }

    public void setIdCampQual(Integer idCampQual) {
        this.idCampQual = idCampQual;
    }

    public String getOfferingType() {
        return offeringType;
    }

    public void setOfferingType(String offeringType) {
        this.offeringType = offeringType;
    }

    @XmlTransient
    public Collection<GradStudent> getGradStudentCollection() {
        return gradStudentCollection;
    }

    public void setGradStudentCollection(Collection<GradStudent> gradStudentCollection) {
        this.gradStudentCollection = gradStudentCollection;
    }

    public Campus getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(Campus campusCode) {
        this.campusCode = campusCode;
    }

    public Qualification getQualCode() {
        return qualCode;
    }

    public void setQualCode(Qualification qualCode) {
        this.qualCode = qualCode;
    }

    @XmlTransient
    public Collection<Userqualification> getUserqualificationCollection() {
        return userqualificationCollection;
    }

    public void setUserqualificationCollection(Collection<Userqualification> userqualificationCollection) {
        this.userqualificationCollection = userqualificationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCampQual != null ? idCampQual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campusqualification)) {
            return false;
        }
        Campusqualification other = (Campusqualification) object;
        if ((this.idCampQual == null && other.idCampQual != null) || (this.idCampQual != null && !this.idCampQual.equals(other.idCampQual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.Campusqualification[ idCampQual=" + idCampQual + " ]";
    }
    
}
