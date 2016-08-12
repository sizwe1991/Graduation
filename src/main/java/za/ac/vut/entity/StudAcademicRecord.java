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
@Table(name = "stud_academic_record")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudAcademicRecord.findAll", query = "SELECT s FROM StudAcademicRecord s"),
    @NamedQuery(name = "StudAcademicRecord.findByGradStudAcademicId", query = "SELECT s FROM StudAcademicRecord s WHERE s.gradStudAcademicId = :gradStudAcademicId"),
    @NamedQuery(name = "StudAcademicRecord.findByAdmitStno", query = "SELECT s FROM StudAcademicRecord s WHERE s.admitStno = :admitStno"),
    @NamedQuery(name = "StudAcademicRecord.findByAdmitLevel", query = "SELECT s FROM StudAcademicRecord s WHERE s.admitLevel = :admitLevel"),
    @NamedQuery(name = "StudAcademicRecord.findByAdmitSubjectcode", query = "SELECT s FROM StudAcademicRecord s WHERE s.admitSubjectcode = :admitSubjectcode"),
    @NamedQuery(name = "StudAcademicRecord.findByAdmitSubjectdesc", query = "SELECT s FROM StudAcademicRecord s WHERE s.admitSubjectdesc = :admitSubjectdesc"),
    @NamedQuery(name = "StudAcademicRecord.findByAdmitOutcome", query = "SELECT s FROM StudAcademicRecord s WHERE s.admitOutcome = :admitOutcome"),
    @NamedQuery(name = "StudAcademicRecord.findByAdmitMark", query = "SELECT s FROM StudAcademicRecord s WHERE s.admitMark = :admitMark"),
    @NamedQuery(name = "StudAcademicRecord.findByAdmitYear", query = "SELECT s FROM StudAcademicRecord s WHERE s.admitYear = :admitYear"),
    @NamedQuery(name = "StudAcademicRecord.findByAdmitCreditscore", query = "SELECT s FROM StudAcademicRecord s WHERE s.admitCreditscore = :admitCreditscore")})
public class StudAcademicRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "grad_stud_academic_id")
    private Integer gradStudAcademicId;
    @Size(max = 255)
    @Column(name = "admit_stno")
    private String admitStno;
    @Size(max = 255)
    @Column(name = "admit_level")
    private String admitLevel;
    @Size(max = 255)
    @Column(name = "admit_subjectcode")
    private String admitSubjectcode;
    @Size(max = 255)
    @Column(name = "admit_subjectdesc")
    private String admitSubjectdesc;
    @Size(max = 255)
    @Column(name = "admit_outcome")
    private String admitOutcome;
    @Size(max = 255)
    @Column(name = "admit_mark")
    private String admitMark;
    @Size(max = 255)
    @Column(name = "admit_year")
    private String admitYear;
    @Size(max = 255)
    @Column(name = "admit_creditscore")
    private String admitCreditscore;
    @JoinColumn(name = "gradstud_id", referencedColumnName = "gradstud_id")
    @ManyToOne
    private GradStudent gradstudId;

    public StudAcademicRecord() {
    }

    public StudAcademicRecord(Integer gradStudAcademicId) {
        this.gradStudAcademicId = gradStudAcademicId;
    }

    public Integer getGradStudAcademicId() {
        return gradStudAcademicId;
    }

    public void setGradStudAcademicId(Integer gradStudAcademicId) {
        this.gradStudAcademicId = gradStudAcademicId;
    }

    public String getAdmitStno() {
        return admitStno;
    }

    public void setAdmitStno(String admitStno) {
        this.admitStno = admitStno;
    }

    public String getAdmitLevel() {
        return admitLevel;
    }

    public void setAdmitLevel(String admitLevel) {
        this.admitLevel = admitLevel;
    }

    public String getAdmitSubjectcode() {
        return admitSubjectcode;
    }

    public void setAdmitSubjectcode(String admitSubjectcode) {
        this.admitSubjectcode = admitSubjectcode;
    }

    public String getAdmitSubjectdesc() {
        return admitSubjectdesc;
    }

    public void setAdmitSubjectdesc(String admitSubjectdesc) {
        this.admitSubjectdesc = admitSubjectdesc;
    }

    public String getAdmitOutcome() {
        return admitOutcome;
    }

    public void setAdmitOutcome(String admitOutcome) {
        this.admitOutcome = admitOutcome;
    }

    public String getAdmitMark() {
        return admitMark;
    }

    public void setAdmitMark(String admitMark) {
        this.admitMark = admitMark;
    }

    public String getAdmitYear() {
        return admitYear;
    }

    public void setAdmitYear(String admitYear) {
        this.admitYear = admitYear;
    }

    public String getAdmitCreditscore() {
        return admitCreditscore;
    }

    public void setAdmitCreditscore(String admitCreditscore) {
        this.admitCreditscore = admitCreditscore;
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
        hash += (gradStudAcademicId != null ? gradStudAcademicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudAcademicRecord)) {
            return false;
        }
        StudAcademicRecord other = (StudAcademicRecord) object;
        if ((this.gradStudAcademicId == null && other.gradStudAcademicId != null) || (this.gradStudAcademicId != null && !this.gradStudAcademicId.equals(other.gradStudAcademicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.StudAcademicRecord[ gradStudAcademicId=" + gradStudAcademicId + " ]";
    }
    
}
