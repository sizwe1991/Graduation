/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.*;
import za.ac.vut.entity.*;
import za.ac.vut.facade.*;
import za.ac.vut.util.Status;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped

public class UserQualificationController {

    private Redirect rd = new Redirect();
    private Qualification qualification = new Qualification();

    private Campus campus = new Campus();

    private Status status = new Status();

    private GradStudent gradStudent = new GradStudent();

    @EJB
    private GradStudentFacade gradStudentFacade;

    @EJB
    private QualificationFacade qualificationFacade;

    private List<GradStudent> gradStudents;
    private List<GradStudent> tempGradStudents;

    @PostConstruct
    public void init() {
        tempGradStudents = gradStudentFacade.findAll();

    }

    public void btnQualCodeClick(Qualification qual, Campus objCampus) {
        campus = objCampus;
        qualification = qual;
        rd.viewStudentPage();
    }

    public void btnQualCodeClicks(Qualification qual, Campus objCampus) {
        campus = objCampus;
        qualification = qual;
        // rd.viewStudentPage();
    }

    public Qualification getQualification() {
        return qualification;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public void setGradStudent(GradStudent gradStudent) {
        this.gradStudent = gradStudent;
    }

    public GradStudent getGradStudent() {
        return gradStudent;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public List<GradStudent> getGradStudents() {
        return gradStudents;
    }

    public void setGradStudents(List<GradStudent> gradStudents) {
        this.gradStudents = gradStudents;
    }

    
    private List<GradStudent> viewStudentList(Qualification qualification, Campus campus) {
        gradStudents = new ArrayList<>();

        for (GradStudent objGradStudent : tempGradStudents) {
            if (objGradStudent.getAdmitStatus() != null) {
                if (objGradStudent.getAdmitStatus().equalsIgnoreCase(status.getFRAUD_APPROVE())
                        && objGradStudent.getAdmitQualcode().equalsIgnoreCase(qualification.getQualCode())
                        && objGradStudent.getOfferingType().substring(0, 1).equalsIgnoreCase(campus.getShortCode())) {
                    gradStudents.add(objGradStudent);
                }
            }
        }

        return gradStudents;
    }

    public void getGradStudents(Qualification qualification, Campus campus) {
        gradStudents = new ArrayList<>();

        for (GradStudent objGradStudent : tempGradStudents) {
            if (objGradStudent.getAdmitStatus() != null) {
                if (objGradStudent.getAdmitStatus().equalsIgnoreCase(status.getFRAUD_APPROVE())
                        && objGradStudent.getAdmitQualcode().equalsIgnoreCase(qualification.getQualCode())
                        && objGradStudent.getOfferingType().substring(0, 1).equalsIgnoreCase(campus.getShortCode())) {
                    gradStudents.add(objGradStudent);
                }
            }
        }
        //return gradStudents;
    }

}
