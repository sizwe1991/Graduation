/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 2015127
 */
@Entity
@Table(name = "grad_student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GradStudent.findAll", query = "SELECT g FROM GradStudent g"),
    @NamedQuery(name = "GradStudent.findByGradstudId", query = "SELECT g FROM GradStudent g WHERE g.gradstudId = :gradstudId"),
    @NamedQuery(name = "GradStudent.findByAdmitStno", query = "SELECT g FROM GradStudent g WHERE g.admitStno = :admitStno"),
    @NamedQuery(name = "GradStudent.findByOfferingType", query = "SELECT g FROM GradStudent g WHERE g.offeringType = :offeringType"),
    @NamedQuery(name = "GradStudent.findByAdmitQualcode", query = "SELECT g FROM GradStudent g WHERE g.admitQualcode = :admitQualcode"),
    @NamedQuery(name = "GradStudent.findByAdmitQualdesc", query = "SELECT g FROM GradStudent g WHERE g.admitQualdesc = :admitQualdesc"),
    @NamedQuery(name = "GradStudent.findByAdmitFullnames", query = "SELECT g FROM GradStudent g WHERE g.admitFullnames = :admitFullnames"),
    @NamedQuery(name = "GradStudent.findByAdmitInit", query = "SELECT g FROM GradStudent g WHERE g.admitInit = :admitInit"),
    @NamedQuery(name = "GradStudent.findByAdmitSurname", query = "SELECT g FROM GradStudent g WHERE g.admitSurname = :admitSurname"),
    @NamedQuery(name = "GradStudent.findByAdmitTitle", query = "SELECT g FROM GradStudent g WHERE g.admitTitle = :admitTitle"),
    @NamedQuery(name = "GradStudent.findByAdmitIdpassport", query = "SELECT g FROM GradStudent g WHERE g.admitIdpassport = :admitIdpassport"),
    @NamedQuery(name = "GradStudent.findByAdmitStatus", query = "SELECT g FROM GradStudent g WHERE g.admitStatus = :admitStatus")})
public class GradStudent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gradstud_id")
    private Integer gradstudId;
    @Size(max = 255)
    @Column(name = "admit_stno")
    private String admitStno;
    @Size(max = 255)
    @Column(name = "offeringType")
    private String offeringType;
    @Size(max = 255)
    @Column(name = "admit_qualcode")
    private String admitQualcode;
    @Size(max = 255)
    @Column(name = "admit_qualdesc")
    private String admitQualdesc;
    @Size(max = 255)
    @Column(name = "admit_fullnames")
    private String admitFullnames;
    @Size(max = 255)
    @Column(name = "admit_init")
    private String admitInit;
    @Size(max = 255)
    @Column(name = "admit_surname")
    private String admitSurname;
    @Size(max = 255)
    @Column(name = "admit_title")
    private String admitTitle;
    @Size(max = 255)
    @Column(name = "admit_idpassport")
    private String admitIdpassport;
    @Size(max = 255)
    @Column(name = "admit_status")
    private String admitStatus;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "comment")
    private String comment;
    @JoinColumn(name = "campus_qual_id", referencedColumnName = "id_camp_qual")
    @ManyToOne
    private Campusqualification campusQualId;
    @OneToMany(mappedBy = "gradstudId")
    private Collection<StudAcademicRecord> studAcademicRecordCollection;
    @OneToMany(mappedBy = "gradstudId")
    private Collection<StudMatric> studMatricCollection;

    public GradStudent() {
    }

    public GradStudent(Integer gradstudId) {
        this.gradstudId = gradstudId;
    }

    public Integer getGradstudId() {
        return gradstudId;
    }

    public void setGradstudId(Integer gradstudId) {
        this.gradstudId = gradstudId;
    }

    public String getAdmitStno() {
        return admitStno;
    }

    public void setAdmitStno(String admitStno) {
        this.admitStno = admitStno;
    }

    public String getOfferingType() {
        return offeringType;
    }

    public void setOfferingType(String offeringType) {
        this.offeringType = offeringType;
    }

    public String getAdmitQualcode() {
        return admitQualcode;
    }

    public void setAdmitQualcode(String admitQualcode) {
        this.admitQualcode = admitQualcode;
    }

    public String getAdmitQualdesc() {
        return admitQualdesc;
    }

    public void setAdmitQualdesc(String admitQualdesc) {
        this.admitQualdesc = admitQualdesc;
    }

    public String getAdmitFullnames() {
        return admitFullnames;
    }

    public void setAdmitFullnames(String admitFullnames) {
        this.admitFullnames = admitFullnames;
    }

    public String getAdmitInit() {
        return admitInit;
    }

    public void setAdmitInit(String admitInit) {
        this.admitInit = admitInit;
    }

    public String getAdmitSurname() {
        return admitSurname;
    }

    public void setAdmitSurname(String admitSurname) {
        this.admitSurname = admitSurname;
    }

    public String getAdmitTitle() {
        return admitTitle;
    }

    public void setAdmitTitle(String admitTitle) {
        this.admitTitle = admitTitle;
    }

    public String getAdmitIdpassport() {
        return admitIdpassport;
    }

    public void setAdmitIdpassport(String admitIdpassport) {
        this.admitIdpassport = admitIdpassport;
    }

    public String getAdmitStatus() {
        return admitStatus;
    }

    public void setAdmitStatus(String admitStatus) {
        this.admitStatus = admitStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Campusqualification getCampusQualId() {
        return campusQualId;
    }

    public void setCampusQualId(Campusqualification campusQualId) {
        this.campusQualId = campusQualId;
    }

    @XmlTransient
    public Collection<StudAcademicRecord> getStudAcademicRecordCollection() {
        return studAcademicRecordCollection;
    }

    public void setStudAcademicRecordCollection(Collection<StudAcademicRecord> studAcademicRecordCollection) {
        this.studAcademicRecordCollection = studAcademicRecordCollection;
    }

    @XmlTransient
    public Collection<StudMatric> getStudMatricCollection() {
        return studMatricCollection;
    }

    public void setStudMatricCollection(Collection<StudMatric> studMatricCollection) {
        this.studMatricCollection = studMatricCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gradstudId != null ? gradstudId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GradStudent)) {
            return false;
        }
        GradStudent other = (GradStudent) object;
        if ((this.gradstudId == null && other.gradstudId != null) || (this.gradstudId != null && !this.gradstudId.equals(other.gradstudId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.ac.vut.entity.GradStudent[ gradstudId=" + gradstudId + " ]";
    }
    
}
