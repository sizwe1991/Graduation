/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.controller;

import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.*;
import za.ac.vut.entity.*;
import za.ac.vut.facade.*;

/**
 *
 * @author 2015127
 */
@SessionScoped
@ManagedBean
public class dashboard {

    @EJB
    private GradStudentFacade gradStudentFacade;

    @EJB
    private CampusFacade campusFacade;

    @EJB
    private CeremonyFacade ceremonyFacade;

    private List<Campus> campusList = new ArrayList<>();
    private List<Ceremony> ceremonyList = new ArrayList<>();
    private List<GradStudent> gradStudentList = new ArrayList<>();

    private final String EXAM_RECIEVE = "EXAM:Received";
    private final String HOD_APPROVE = "HOD:Approved";

    private final String POSSIBLE = "POSSIBLE";
    private final String FRAUD_APPROVE = "Verification:Approved";

    @PostConstruct
    public void init() {
        campusList = campusFacade.findAll();
        ceremonyList = ceremonyFacade.findAll();
        gradStudentList = gradStudentFacade.findAll();
    }

    public int getCampusTotal(Campus objCampus) {
        int number = 0;
        for (GradStudent objStudent : gradStudentList) {
            if (objStudent.getAdmitStatus() != null) {
                if (objStudent.getOfferingType().substring(0, 1).equalsIgnoreCase(objCampus.getShortCode())) {
                    number++;
                }
            }
        }

        return number;
    }

    public int getCampusPossible(Campus objCampus) {
        int number = 0;
        for (GradStudent objStudent : gradStudentList) {
            if (objStudent.getAdmitStatus() != null) {
                if (objStudent.getOfferingType().substring(0, 1).equalsIgnoreCase(objCampus.getShortCode())
                        && objStudent.getAdmitStatus().equalsIgnoreCase(POSSIBLE)) {
                    number++;
                }
            }
        }

        return number;
    }

    public int getExamRecieved(Campus objCampus) {
        int number = 0;
        for (GradStudent objStudent : gradStudentList) {
            if (objStudent.getAdmitStatus() != null) {
                if (objStudent.getOfferingType().substring(0, 1).equalsIgnoreCase(objCampus.getShortCode())
                        && objStudent.getAdmitStatus().equalsIgnoreCase(EXAM_RECIEVE)) {
                    number++;
                }
            }
        }

        return number;
    }

    public int getFraudApproved(Campus objCampus) {
        int number = 0;
        for (GradStudent objStudent : gradStudentList) {
            if (objStudent.getAdmitStatus() != null) {
                if (objStudent.getOfferingType().substring(0, 1).equalsIgnoreCase(objCampus.getShortCode())
                        && objStudent.getAdmitStatus().equalsIgnoreCase(FRAUD_APPROVE)) {
                    number++;
                }
            }
        }

        return number;
    }

    public int getHODApproved(Campus objCampus) {
        int number = 0;
        for (GradStudent objStudent : gradStudentList) {
            if (objStudent.getAdmitStatus() != null) {
                if (objStudent.getOfferingType().substring(0, 1).equalsIgnoreCase(objCampus.getShortCode())
                        && objStudent.getAdmitStatus().equalsIgnoreCase(HOD_APPROVE)) {
                    number++;
                }
            }
        }

        return number;
    }

    public int getCeremonyAttend(Campus objCampus) {
        int number = 0;
        for (Ceremony ceremony : ceremonyList) {
            if (getStudentCampus(ceremony.getAdmitStno()).substring(0, 1) != null) {
                if (ceremony.getStatus().equalsIgnoreCase("Yes")
                        && getStudentCampus(ceremony.getAdmitStno()).substring(0, 1)
                        .equalsIgnoreCase(objCampus.getShortCode())) {
                    ++number;
                }
            }
        }

        return number;
    }

    private String getStudentCampus(String stNo) {
        String campusName = "";
        for (GradStudent g : gradStudentList) {
            if (stNo != null) {
                if (stNo.equalsIgnoreCase(g.getAdmitStno())) {
                    campusName = g.getOfferingType();
                }
            }

        }
        return campusName;
    }

    public int getCeremonyNotAttend(Campus objCampus) {
        int number = 0;
        for (Ceremony ceremony : ceremonyList) {
            if (getStudentCampus(ceremony.getAdmitStno()).substring(0, 1) != null) {
                if (ceremony.getStatus().equalsIgnoreCase("No")
                        && getStudentCampus(ceremony.getAdmitStno()).substring(0, 1)
                        .equalsIgnoreCase(objCampus.getShortCode())) {
                    ++number;
                }
            }
        }

        return number;
    }

    public void setCampusList(List<Campus> campusList) {
        this.campusList = campusList;
    }

    public List<Campus> getCampusList() {
        return campusList;
    }

}
