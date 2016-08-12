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
@Table(name = "graduatesView")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GraduatesView.findAll", query = "SELECT g FROM GraduatesView g"),
    @NamedQuery(name = "GraduatesView.findByCampus", query = "SELECT g FROM GraduatesView g WHERE g.campus = :campus"),
    @NamedQuery(name = "GraduatesView.findByStudents", query = "SELECT g FROM GraduatesView g WHERE g.students = :students"),
    @NamedQuery(name = "GraduatesView.findByStatus", query = "SELECT g FROM GraduatesView g WHERE g.status = :status"),
    @NamedQuery(name = "GraduatesView.findByIdGraduates", query = "SELECT g FROM GraduatesView g WHERE g.idGraduates = :idGraduates")})
public class GraduatesView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 1)
    @Column(name = "campus")
    private String campus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "students")
    private long students;
    @Size(max = 45)
    @Column(name = "status")
    private String status;
    @Size(max = 46)
    @Column(name = "idGraduates")
    @Id
    private String idGraduates;

    public GraduatesView() {
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public long getStudents() {
        return students;
    }

    public void setStudents(long students) {
        this.students = students;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdGraduates() {
        return idGraduates;
    }

    public void setIdGraduates(String idGraduates) {
        this.idGraduates = idGraduates;
    }
    
}
