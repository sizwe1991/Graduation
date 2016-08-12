/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.controller;

import java.util.*;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.*;
import za.ac.vut.entity.*;
import za.ac.vut.facade.CampusqualificationFacade;
import za.ac.vut.facade.*;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped
public class QualController {

    @EJB
    private QualificationFacade tblQualificationFacade;

    @EJB
    private FacultyFacade facultyFacade;

    private Faculty faculty;

    private Campus campus;

    @EJB
    private UserqualificationFacade userqualificationFacade;

    @EJB
    private CampusqualificationFacade campusqualificationFacade;

    private List<Userqualification> userqualificationList;
    private List<Qualification> qualificationList;
    private List<Campusqualification> campusqualificationList;
    private List<Faculty> facultyList;

    @PostConstruct
    public void init() {
        initialize();
    }

    private void initialize() {
        qualificationList = tblQualificationFacade.findAll();
        campusqualificationList = campusqualificationFacade.findAll();
        facultyList = facultyFacade.findAll();
        userqualificationList = userqualificationFacade.findAll();
    }

    public QualController() {
        userqualificationList = new ArrayList<>();

        qualificationList = new ArrayList<>();
        faculty = new Faculty();
        facultyList = new ArrayList<>();
        campusqualificationList = new ArrayList<>();
    }

    public List<Qualification> getQualDetials(int facultyID, String level) {
        List<Qualification> result = new ArrayList<>();
        for (Qualification objQualification : qualificationList) {
            if (objQualification.getFacultyId().getFacultyId() == facultyID && objQualification.getQualLevel().equalsIgnoreCase(level)) {
                result.add(objQualification);
            }
        }
        return result;
    }

    public void btnViewLinkedFaculties(User user) {

        List<Faculty> facultyTempList = new ArrayList<>();
        // campusLinkedFaculties.clear();

        for (Userqualification objuserUserqualification : userqualificationList) {
            if (objuserUserqualification.getIdCampQual().getCampusCode().getShortCode().charAt(0)
                    == campus.getShortCode().charAt(0)
                    && objuserUserqualification.getUsername().equals(user)) {

                for (Faculty objFaculty : this.facultyList) {
                    if (objFaculty.equals(objuserUserqualification.getIdCampQual().
                            getQualCode().getFacultyId())) {
                        faculty = objFaculty;
                    }
                }

                facultyTempList.add(faculty);

            }
        }

//        for (Faculty objFaculty : facultyTempList) {
//
//            boolean found = false;
//            int count = 0;
//            while (!found && count < campusLinkedFaculties.size()) {
//                if (campusLinkedFaculties.get(count).getFacultyId().equals(objFaculty.getFacultyId())) {
//                    found = true;
//                }
//                count++;
//            }
//            if (!found) {
//                campusLinkedFaculties.add(objFaculty);
//            }
//        }
        Collections.sort(facultyList, new Comparator<Faculty>() {
            @Override
            public int compare(Faculty p1, Faculty p2) {
                return p1.getFacultyName().compareTo(p2.getFacultyName());
            }

        });

    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Campus getCampus() {
        return campus;
    }

    public List<String> getDistinctLevel(int facultyID) {
        List<String> result = new ArrayList<>();

        List<String> tempList = new ArrayList<>();

        for (Qualification objQualification : qualificationList) {
            if (objQualification.getFacultyId().getFacultyId() == facultyID) {
                tempList.add(objQualification.getQualLevel());
            }
        }

        for (String objRslt : tempList) {
            if (result.isEmpty()) {
                result.add(objRslt);
            } else {
                boolean value = false;

                for (String vvv : result) {
                    if (objRslt.equalsIgnoreCase(vvv)) {
                        value = true;
                    }
                }

                if (!value) {
                    result.add(objRslt);
                }
            }
        }

        return result;
    }

    public List<Faculty> getCampusFaculties(Campus campusCode) {

        List<Faculty> facultyTempList = new ArrayList<>();

        for (Campusqualification objCampusqualification : campusqualificationList) {
            if (objCampusqualification.getCampusCode().getShortCode().charAt(0)
                    == campusCode.getShortCode().charAt(0)) {

                for (Faculty objFaculty : this.facultyList) {
                    if (objFaculty.equals(objCampusqualification.getQualCode().getFacultyId())) {
                        faculty = objFaculty;
                    }
                }

                facultyTempList.add(faculty);

            }
        }

        for (Faculty objFaculty : facultyTempList) {

            boolean found = false;
            int count = 0;
            while (!found && count < facultyList.size()) {
                if (facultyList.get(count).getFacultyId().equals(objFaculty.getFacultyId())) {
                    found = true;
                }
                count++;
            }
            if (!found) {
                facultyList.add(objFaculty);
            }
        }

        Collections.sort(facultyList, new Comparator<Faculty>() {
            @Override
            public int compare(Faculty p1, Faculty p2) {
                return p1.getFacultyName().compareTo(p2.getFacultyName());
            }

        });

        return facultyList;
    }

    public List<Qualification> getFacultyQualification(int facultyID, Campus campusCode) {
        List<Qualification> qualifications = new ArrayList<>();
        for (Campusqualification objCampusqualification : campusqualificationList) {
            if (objCampusqualification.getCampusCode().getShortCode().charAt(0) == campusCode.getShortCode().charAt(0)
                    && objCampusqualification.getQualCode().getFacultyId().getFacultyId() == facultyID) {
                qualifications.add(objCampusqualification.getQualCode());
            }
        }
        return qualifications;
    }

    public List<Qualification> getQualList() {
        return qualificationList;
    }
}
