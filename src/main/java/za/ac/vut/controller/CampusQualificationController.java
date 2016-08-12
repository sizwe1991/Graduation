/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.controller;

import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.ViewHandler;
import javax.faces.bean.*;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import za.ac.vut.entity.*;
import za.ac.vut.facade.*;
import za.ac.vut.util.*;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped

public class CampusQualificationController {

    @EJB
    private GradLogFacade gradLogFacade;

    @EJB
    private CampusFacade campusFacade;

    @EJB
    private FacultyFacade tblFacultyFacade;

    za.ac.vut.util.Messsage msg = new Messsage();
    @EJB
    private CampusqualificationFacade campusqualificationFacade;

    private Qualification selectedQualification;

    private Campusqualification campusqualification;

    private String SelectedFaculty;

    private List<Campus> campuseList;
    private List<Campusqualification> campusqualificationList;
    private List<Faculty> facultyList;

    private Redirect rd = new Redirect();

    @PostConstruct
    public void init() {
        initialize();
    }

    private void initialize() {
        campuseList = campusFacade.findAll();
        facultyList = tblFacultyFacade.findAll();
        campusqualificationList = campusqualificationFacade.findAll();
    }

    public CampusQualificationController() {
        facultyList = new ArrayList<>();
        campusqualificationList = new ArrayList<>();
        campuseList = new ArrayList<>();
        campusqualification = new Campusqualification();
        selectedQualification = new Qualification();
    }

    public Campusqualification getCampusqualification() {
        return campusqualification;
    }

    public void setCampusqualification(Campusqualification campusqualification) {
        this.campusqualification = campusqualification;
    }

    public void setSelectedFaculty(String SelectedFaculty) {
        this.SelectedFaculty = SelectedFaculty;
    }

    public String getSelectedFaculty() {
        return SelectedFaculty;
    }

    public Qualification getSelectedQualification() {
        return selectedQualification;
    }

    public void setSelectedQualification(Qualification selectedQualification) {
        this.selectedQualification = selectedQualification;
    }

//    public void click()
//    {
//         FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Access denied", "You do not have permission to access this system."));
//    }
    public void btnSelectCourse(Qualification objQualification) {
        selectedQualification = objQualification;
        for (Faculty objFacultyFacade : facultyList) {
            if (Objects.equals(selectedQualification.getFacultyId().getFacultyId(), objFacultyFacade.getFacultyId())) {
                SelectedFaculty = objFacultyFacade.getFacultyName();
            }
        }

    }

    private boolean checkCampusQual(Campus campus, Qualification qualCode) {
        boolean value = false;
        for (Campusqualification objcampusqualification : campusqualificationList) {
            if (objcampusqualification.getCampusCode().getShortCode().charAt(0) == campus.getShortCode().charAt(0)
                    && objcampusqualification.getQualCode().equals(qualCode)) {
                value = true;
            }
        }
        return value;
    }

    public String btnLinkText(Campus campus, Qualification qualification) {
        String txt = "";
        if (checkCampusQual(campus, qualification)) {
            txt = "Unlink";
        } else {
            txt = "Link";
        }
        return txt;
    }

    public void btnLinkUnlinkClick(Campus campus, Qualification qualification) {
        String txt = btnLinkText(campus, qualification);

        if (txt.equalsIgnoreCase("Unlink")) {

            try {

                for (Campusqualification objCampusqualification : campusqualificationList) {
                    if (objCampusqualification.getCampusCode().getShortCode().charAt(0) == campus.getShortCode().charAt(0)
                            && objCampusqualification.getQualCode().equals(qualification)) {
                        campusqualification = objCampusqualification;
                    }
                }

                Campusqualification tempQCampusqualification = campusqualification;

                campusqualificationFacade.remove(campusqualification);

                FacesContext context2 = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

                String userid = (String) session.getAttribute("user");

                GradLog gradLog = new GradLog();

                Date today = new Date();

                String time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

                gradLog.setLogTime(time);
                gradLog.setLogDate(today);
                gradLog.setLogType("UNLINK QUALIFICATION");
                gradLog.setComment("User: [" + userid + "] UNLINKED QUALIFICATION: " + tempQCampusqualification.getQualCode().getQualName() + " TO " + tempQCampusqualification.getCampusCode().getDescription());

                gradLogFacade.create(gradLog);

                initialize();

                refreshPage();

                msg.info("Qualification: " + tempQCampusqualification.getQualCode().getQualName() + " has been unlinked Successfully in " + tempQCampusqualification.getCampusCode().getDescription());

            } catch (Exception s) {
                msg.error(s.getMessage());
            }

        } else if (txt.equalsIgnoreCase("Link")) {

            try {

                campusqualification = new Campusqualification();

                campusqualification.setCampusCode(campus);
                campusqualification.setQualCode(qualification);
                campusqualification.setOfferingType("Full Time");

                FacesContext context2 = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

                String userid = (String) session.getAttribute("user");

                GradLog gradLog = new GradLog();

                Date today = new Date();

                String time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

                gradLog.setLogTime(time);
                gradLog.setLogDate(today);
                gradLog.setLogType("LINK QUALIFICATION");
                gradLog.setComment("User: [" + userid + "] LINKED QUALIFICATION: " + campusqualification.getQualCode() + " TO " + campusqualification.getCampusCode().getCampusCode());

                gradLogFacade.create(gradLog);

                campusqualificationFacade.create(campusqualification);

                selectedQualification = new Qualification();

                initialize();

                refreshPage();

                msg.info("Qualification: " + campusqualification.getQualCode().getQualName() + " has been linked Successfully in " + campusqualification.getCampusCode().getDescription());

            } catch (Exception s) {
                msg.error(s.getMessage());
            }
        }

    }

    private void refreshPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }
}
