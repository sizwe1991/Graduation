/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;
import za.ac.vut.entity.Ceremony;
import za.ac.vut.entity.EmailConfiguration;
import za.ac.vut.entity.GradLog;
import za.ac.vut.entity.GradStudent;
import za.ac.vut.facade.CeremonyFacade;
import za.ac.vut.facade.EmailConfigurationFacade;
import za.ac.vut.facade.GradLogFacade;
import za.ac.vut.facade.GradStudentFacade;
import za.ac.vut.util.Messsage;
import za.ac.vut.util.SendEmail;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped
public class CeremonyController {

    private List<Ceremony> allceremonyStudList = new ArrayList<>(), ceremonyList = new ArrayList<>();
    private List<GradStudent> allGraduates = new ArrayList<>();

    private Messsage msg = new Messsage();

    private SendEmail sendEmail = new SendEmail();

    @EJB
    private CeremonyFacade ceremonyFacade;

    @EJB
    private GradStudentFacade gradStudentFacade;

    @EJB
    private GradLogFacade gradLogFacade;

    @EJB
    private EmailConfigurationFacade emailConfigurationFacade;

    private Ceremony ceremony = new Ceremony();

    private Date today = new Date();

    private String searchStudent, time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

    private EmailConfiguration email;

    @PostConstruct
    public void init() {

        for (EmailConfiguration myemail : emailConfigurationFacade.findAll()) {
            email = myemail;
        }

        allGraduates = gradStudentFacade.findAll();

        ceremonyList = ceremonyFacade.findAll();

        for (Ceremony objCeremony : ceremonyList) {
            if (allceremonyStudList.isEmpty()) {
                allceremonyStudList.add(objCeremony);
            } else {
                boolean value = false;
                for (Ceremony tempCeremony : allceremonyStudList) {
                    if (tempCeremony.getAdmitStno().equalsIgnoreCase(objCeremony.getAdmitStno())) {
                        value = true;
                    }
                }

                if (!value) {
                    allceremonyStudList.add(objCeremony);
                }
            }
        }

    }

    public void refreshCeremonyStudent() {
        searchStudent = "";

        allceremonyStudList.clear();

        for (Ceremony objCeremony : ceremonyList) {
            if (allceremonyStudList.isEmpty()) {
                allceremonyStudList.add(objCeremony);
            } else {
                boolean value = false;
                for (Ceremony tempCeremony : allceremonyStudList) {
                    if (tempCeremony.getAdmitStno().equalsIgnoreCase(objCeremony.getAdmitStno())) {
                        value = true;
                    }
                }

                if (!value) {
                    allceremonyStudList.add(objCeremony);
                }
            }
        }

    }

    public void searchStudent() {
        allceremonyStudList.clear();

        for (Ceremony objCeremony : ceremonyList) {
            if (objCeremony.getAdmitStno().toLowerCase().contains(searchStudent.toLowerCase())
                    || getGraduate(objCeremony.getAdmitStno()).getAdmitIdpassport().toLowerCase().
                    contains(searchStudent.toLowerCase())
                    || getGraduate(objCeremony.getAdmitStno()).getAdmitSurname().toLowerCase().
                    contains(searchStudent.toLowerCase())
                    || getGraduate(objCeremony.getAdmitStno()).getAdmitFullnames().toLowerCase().
                    contains(searchStudent.toLowerCase())) {
                allceremonyStudList.add(objCeremony);
            }
        }

    }

    public List<Ceremony> getAllceremonyStudList() {
        return allceremonyStudList;
    }

    public void setSearchStudent(String searchStudent) {
        this.searchStudent = searchStudent;
    }

    public String getSearchStudent() {
        return searchStudent;
    }

    public void setAllceremonyStudList(List<Ceremony> allceremonyStudList) {
        this.allceremonyStudList = allceremonyStudList;
    }

    public GradStudent getGraduate(String stNo) {
        GradStudent grad = new GradStudent();

        for (GradStudent g : allGraduates) {
            if (g.getAdmitStno() != null) {
                if (g.getAdmitStno().equalsIgnoreCase(stNo)) {
                    grad = g;
                }
            }
        }

        return grad;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        Ceremony objCeremony = (Ceremony) event.getObject();

        ceremonyFacade.edit(objCeremony);

        FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

        String userid = (String) session.getAttribute("user");

        GradLog gradLog = new GradLog();

        gradLog.setLogDate(today);
        gradLog.setLogTime(time);
        gradLog.setLogType("CHANGE GRADUATION ATTENDANCE");
        gradLog.setComment("User: [" + userid + "]" + " CHANGE GRADUATION ATTENDANCE: " + objCeremony.getStatus());
        gradLogFacade.create(gradLog);

        msg.info("Status Changed Successfully for " + objCeremony.getAdmitStno());

    }

    public void onCancel(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        Ceremony objCeremony = (Ceremony) event.getObject();

        GradStudent updateGraduate = getGraduate(objCeremony.getAdmitStno());

        msg.warn("No changes made to " + updateGraduate.getAdmitInit() + " " + updateGraduate.getAdmitSurname() + " (" + updateGraduate.getAdmitStno() + ")");
    }

    public void btnPresencia(Ceremony objCeremony) {
        if (objCeremony.getStatus().equalsIgnoreCase("Yes")) {
            GradStudent updateGraduate = getGraduate(objCeremony.getAdmitStno());
            msg.warn("No changes made to " + updateGraduate.getAdmitInit() + " " + updateGraduate.getAdmitSurname() + " (" + updateGraduate.getAdmitStno() + ")");
        } else {
            objCeremony.setStatus("Yes");
            ceremonyFacade.edit(objCeremony);

            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            GradLog gradLog = new GradLog();

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("CHANGE GRADUATION ATTENDANCE");
            gradLog.setComment("User: [" + userid + "]" + " CHANGE GRADUATION ATTENDANCE: " + objCeremony.getStatus());
            gradLogFacade.create(gradLog);

            GradStudent gradStudent = getGraduate(objCeremony.getAdmitStno());

            sendEmail.sendEmail("210136758@edu.vut.ac.za", email.getExamSubject(), "Dear " + gradStudent.getAdmitFullnames()
                    + "\n \n " + email.getExamBody() +" \n \n"
                    + "And you will be PRESENT in the graduation ceremony");

            msg.info("Status Changed Successfully for " + objCeremony.getAdmitStno());
        }
    }

    public void btnAbsencia(Ceremony objCeremony) {
        if (objCeremony.getStatus().equalsIgnoreCase("No")) {
            GradStudent updateGraduate = getGraduate(objCeremony.getAdmitStno());
            msg.warn("No changes made to " + updateGraduate.getAdmitInit() + " " + updateGraduate.getAdmitSurname() + " (" + updateGraduate.getAdmitStno() + ")");
        } else {
            objCeremony.setStatus("No");
            ceremonyFacade.edit(objCeremony);

            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            GradLog gradLog = new GradLog();

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("CHANGE GRADUATION ATTENDANCE");
            gradLog.setComment("User: [" + userid + "]" + " CHANGE GRADUATION ATTENDANCE: " + objCeremony.getStatus());
            gradLogFacade.create(gradLog);

            GradStudent gradStudent = getGraduate(objCeremony.getAdmitStno());

            sendEmail.sendEmail("210136758@edu.vut.ac.za", email.getExamSubject(), "Dear " + gradStudent.getAdmitFullnames()
                    + "\n \n" + email.getExamBody() +"\n \n"
                    + "And you will be ABSENT in the graduation ceremony");

            msg.info("Status Changed Successfully for " + objCeremony.getAdmitStno());
        }
    }

    public String getGradAttendance(String stNo) {
        String attendance = "";

        for (Ceremony objCeremony : allceremonyStudList) {
            if (objCeremony.getAdmitStno().equalsIgnoreCase(stNo)) {
                attendance = objCeremony.getStatus();
            }
        }

        if (attendance.equalsIgnoreCase("Yes")) {
            attendance = "Presencia";
        } else {
            attendance = "Absencia";
        }

        return attendance;
    }

}
