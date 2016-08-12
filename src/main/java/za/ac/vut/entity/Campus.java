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
@Table(name = "campus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campus.findAll", query = "SELECT c FROM Campus c"),
    @NamedQuery(name = "Campus.findByCampusCode", query = "SELECT c FROM Campus c WHERE c.campusCode = :campusCode"),
    @NamedQuery(name = "Campus.findByDescription", query = "SELECT c FROM Campus c WHERE c.description = :description"),
    @NamedQuery(name = "Campus.findByShortCode", query = "SELECT c FROM Campus c WHERE c.shortCode = :shortCode")})
public class Campus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "campus_code")
    private String campusCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    @Size(max = 45)
    @Column(name = "shortCode")
    private String shortCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campusCode")
    private Collection<Campusqualification> campusqualificationCollection;

    public Campus() {
    }

    public Campus(String campusCode) {
        this.campusCode = campusCode;
    }

    public Campus(String campusCode, String description) {
        this.campusCode = campusCode;
        this.description = description;
    }

    public String getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    @XmlTransient
    public Collection<Campusqualification> getCampusqualificationCollection() {
        return campusqualificationCollection;
    }

    public void setCampusqualificationCollection(Collection<Campusqualification> campusqualificationCollection) {
        this.campusqualificationCollection = campusqualificationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (campusCode != null ? campusCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campus)) {
            return false;
        }
        Campus other = (Campus) object;
        if ((this.campusCode == null && other.campusCode != null) || (this.campusCode != null && !this.campusCode.equals(other.campusCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.Campus[ campusCode=" + campusCode + " ]";
    }
    
}
