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
@Table(name = "ceremony")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ceremony.findAll", query = "SELECT c FROM Ceremony c"),
    @NamedQuery(name = "Ceremony.findByIdceremony", query = "SELECT c FROM Ceremony c WHERE c.idceremony = :idceremony"),
    @NamedQuery(name = "Ceremony.findByAdmitStno", query = "SELECT c FROM Ceremony c WHERE c.admitStno = :admitStno"),
    @NamedQuery(name = "Ceremony.findByStatus", query = "SELECT c FROM Ceremony c WHERE c.status = :status")})
public class Ceremony implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idceremony")
    private Integer idceremony;
    @Size(max = 255)
    @Column(name = "admit_stno")
    private String admitStno;
    @Size(max = 45)
    @Column(name = "status")
    private String status;

    public Ceremony() {
    }

    public Ceremony(Integer idceremony) {
        this.idceremony = idceremony;
    }

    public Integer getIdceremony() {
        return idceremony;
    }

    public void setIdceremony(Integer idceremony) {
        this.idceremony = idceremony;
    }

    public String getAdmitStno() {
        return admitStno;
    }

    public void setAdmitStno(String admitStno) {
        this.admitStno = admitStno;
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
        hash += (idceremony != null ? idceremony.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ceremony)) {
            return false;
        }
        Ceremony other = (Ceremony) object;
        if ((this.idceremony == null && other.idceremony != null) || (this.idceremony != null && !this.idceremony.equals(other.idceremony))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.Ceremony[ idceremony=" + idceremony + " ]";
    }
    
}
