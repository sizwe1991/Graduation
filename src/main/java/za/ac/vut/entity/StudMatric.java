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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2015127
 */
@Entity
@Table(name = "stud_matric")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudMatric.findAll", query = "SELECT s FROM StudMatric s"),
    @NamedQuery(name = "StudMatric.findByGradStudMatricId", query = "SELECT s FROM StudMatric s WHERE s.gradStudMatricId = :gradStudMatricId"),
    @NamedQuery(name = "StudMatric.findByAdmitStno", query = "SELECT s FROM StudMatric s WHERE s.admitStno = :admitStno"),
    @NamedQuery(name = "StudMatric.findByAdmitSubjectdesc", query = "SELECT s FROM StudMatric s WHERE s.admitSubjectdesc = :admitSubjectdesc"),
    @NamedQuery(name = "StudMatric.findByAdmitSubjectlevel", query = "SELECT s FROM StudMatric s WHERE s.admitSubjectlevel = :admitSubjectlevel")})
public class StudMatric implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "grad_stud_matric_id")
    private Integer gradStudMatricId;
    @Size(max = 255)
    @Column(name = "admit_stno")
    private String admitStno;
    @Size(max = 255)
    @Column(name = "admit_subjectdesc")
    private String admitSubjectdesc;
    @Size(max = 255)
    @Column(name = "admit_subjectlevel")
    private String admitSubjectlevel;
    @JoinColumn(name = "gradstud_id", referencedColumnName = "gradstud_id")
    @ManyToOne
    private GradStudent gradstudId;

    public StudMatric() {
    }

    public StudMatric(Integer gradStudMatricId) {
        this.gradStudMatricId = gradStudMatricId;
    }

    public Integer getGradStudMatricId() {
        return gradStudMatricId;
    }

    public void setGradStudMatricId(Integer gradStudMatricId) {
        this.gradStudMatricId = gradStudMatricId;
    }

    public String getAdmitStno() {
        return admitStno;
    }

    public void setAdmitStno(String admitStno) {
        this.admitStno = admitStno;
    }

    public String getAdmitSubjectdesc() {
        return admitSubjectdesc;
    }

    public void setAdmitSubjectdesc(String admitSubjectdesc) {
        this.admitSubjectdesc = admitSubjectdesc;
    }

    public String getAdmitSubjectlevel() {
        return admitSubjectlevel;
    }

    public void setAdmitSubjectlevel(String admitSubjectlevel) {
        this.admitSubjectlevel = admitSubjectlevel;
    }

    public GradStudent getGradstudId() {
        return gradstudId;
    }

    public void setGradstudId(GradStudent gradstudId) {
        this.gradstudId = gradstudId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gradStudMatricId != null ? gradStudMatricId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudMatric)) {
            return false;
        }
        StudMatric other = (StudMatric) object;
        if ((this.gradStudMatricId == null && other.gradStudMatricId != null) || (this.gradStudMatricId != null && !this.gradStudMatricId.equals(other.gradStudMatricId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.StudMatric[ gradStudMatricId=" + gradStudMatricId + " ]";
    }
    
}
