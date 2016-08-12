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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2015127
 */
@Entity
@Table(name = "transcriptView")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TranscriptView.findAll", query = "SELECT t FROM TranscriptView t"),
    @NamedQuery(name = "TranscriptView.findByGradStudAcademicId", query = "SELECT t FROM TranscriptView t WHERE t.gradStudAcademicId = :gradStudAcademicId"),
    @NamedQuery(name = "TranscriptView.findByAdmitStno", query = "SELECT t FROM TranscriptView t WHERE t.admitStno = :admitStno"),
    @NamedQuery(name = "TranscriptView.findByAdmitLevel", query = "SELECT t FROM TranscriptView t WHERE t.admitLevel = :admitLevel"),
    @NamedQuery(name = "TranscriptView.findByAdmitSubjectcode", query = "SELECT t FROM TranscriptView t WHERE t.admitSubjectcode = :admitSubjectcode"),
    @NamedQuery(name = "TranscriptView.findByAdmitSubjectdesc", query = "SELECT t FROM TranscriptView t WHERE t.admitSubjectdesc = :admitSubjectdesc"),
    @NamedQuery(name = "TranscriptView.findByAdmitOutcome", query = "SELECT t FROM TranscriptView t WHERE t.admitOutcome = :admitOutcome"),
    @NamedQuery(name = "TranscriptView.findByAdmitMark", query = "SELECT t FROM TranscriptView t WHERE t.admitMark = :admitMark"),
    @NamedQuery(name = "TranscriptView.findByAdmitYear", query = "SELECT t FROM TranscriptView t WHERE t.admitYear = :admitYear"),
    @NamedQuery(name = "TranscriptView.findByAdmitCreditscore", query = "SELECT t FROM TranscriptView t WHERE t.admitCreditscore = :admitCreditscore"),
    @NamedQuery(name = "TranscriptView.findByGradstudId", query = "SELECT t FROM TranscriptView t WHERE t.gradstudId = :gradstudId")})
public class TranscriptView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "grad_stud_academic_id")
    @Id
    private int gradStudAcademicId;
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
    @Column(name = "gradstud_id")
    private Integer gradstudId;

    public TranscriptView() {
    }

    public int getGradStudAcademicId() {
        return gradStudAcademicId;
    }

    public void setGradStudAcademicId(int gradStudAcademicId) {
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

    public Integer getGradstudId() {
        return gradstudId;
    }

    public void setGradstudId(Integer gradstudId) {
        this.gradstudId = gradstudId;
    }
    
}
