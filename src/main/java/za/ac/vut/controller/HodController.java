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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

import za.ac.vut.entity.*;
import za.ac.vut.facade.*;
import za.ac.vut.util.*;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped

public class HodController {

    private SendEmail sendEmail=new SendEmail();
    
    @EJB
    private HodFacade hodFacade;

    @EJB
    private GradStudentFacade gradStudentFacade;

    @EJB
    private GradLogFacade gradLogFacade;
    
    @EJB
    private EmailConfigurationFacade emailConfigurationFacade;

    private Hod hod = new Hod();
    private GradStudent gradStudent;

    private List<GradStudent> gradStudentList;

    private List<Hod> hodList, allHodList;

    private Messsage msg = new Messsage();
    private Status status = new Status();

    private Date today = new Date();
    private String searchInput, comment, time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

    private Campus selecteCampus;
    private Qualification selectedQualification;

    private List<GradStudent> qualStudentList;

    private List<Qualification> linkedQual, unlinkedQual;

    private EmailConfiguration email;
    
    @PostConstruct
    public void init() {
        
          for (EmailConfiguration myemail : emailConfigurationFacade.findAll()) {
            email = myemail;
        }

        //hodList = hodFacade.findAll();
        gradStudentList = gradStudentFacade.findAll();

        allHodList = hodFacade.findAll();

        populateHODList();
    }

    private void populateHODList() {
        hodList.clear();

        FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

        String userid = (String) session.getAttribute("user");

        List<Hod> temList = new ArrayList<>();

        for (Hod myHod : allHodList) {
            if (myHod.getUsername().equalsIgnoreCase(userid)) {
                //   hodList.add(myHod);
                temList.add(myHod);
            }
        }

        for (Hod hodObj : temList) {
            if (hodList.isEmpty()) {
                hodList.add(hodObj);
            } else {
                boolean value = false;

                for (Hod objHod : hodList) {
                    if (objHod.getAdmitStno().equalsIgnoreCase(hodObj.getAdmitStno())) {
                        value = true;
                    }
                }

                if (!value) {
                    hodList.add(hodObj);
                }
            }
        }

    }

    public HodController() {
        gradStudentList = new ArrayList<>();
        hodList = new ArrayList<>();
        allHodList = new ArrayList<>();
        linkedQual = new ArrayList<>();
        unlinkedQual = new ArrayList<>();
        qualStudentList = new ArrayList<>();
        gradStudent = new GradStudent();

    }

    public List<GradStudent> getQualStudentList() {
        return qualStudentList;
    }

    public void setQualStudentList(List<GradStudent> qualStudentList) {
        this.qualStudentList = qualStudentList;
    }

    public List<Hod> getHodList() {
        return hodList;
    }

    public void btnRevertChanges() {

        Redirect rd;
        rd = new Redirect();

        populateHODList();

        rd.hodRevertChnagesPage();

        comment = "";
    }

    public GradStudent getGradStudent() {
        return gradStudent;
    }

    public void setGradStudent(GradStudent gradStudent) {
        this.gradStudent = gradStudent;
    }

    public void setSelecteCampus(Campus selecteCampus) {
        this.selecteCampus = selecteCampus;
    }

    public void setSelectedQualification(Qualification selectedQualification) {
        this.selectedQualification = selectedQualification;
    }

    public Campus getSelecteCampus() {
        return selecteCampus;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public String getSearchInput() {
        return searchInput;
    }

    public Qualification getSelectedQualification() {
        return selectedQualification;
    }

    public List<GradStudent> getGradStudentList() {
        return gradStudentList;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public List<Qualification> getLinkedQual() {
        return linkedQual;
    }

    public List<Qualification> getUnlinkedQual() {
        return unlinkedQual;
    }

    public void setLinkedQual(List<Qualification> linkedQual) {
        this.linkedQual = linkedQual;
    }

    public void setUnlinkedQual(List<Qualification> unlinkedQual) {
        this.unlinkedQual = unlinkedQual;
    }

    public void viewStudents(Qualification qual, Campus campus) {
    
         selecteCampus=campus;
         
        selectedQualification = qual;

        qualStudentList.clear();

        List<GradStudent> tempGradStudent = new ArrayList<>();

        for (GradStudent objGradStudent : gradStudentList) {
            if (objGradStudent.getAdmitStatus() != null) {
                if (objGradStudent.getAdmitStatus().equalsIgnoreCase(status.getFRAUD_APPROVE())
                        && objGradStudent.getAdmitQualcode().equalsIgnoreCase(qual.getQualCode())
                        && objGradStudent.getOfferingType().substring(0, 1).equalsIgnoreCase(campus.getShortCode())) {
                    tempGradStudent.add(objGradStudent);
                }
            }
        }

        for (GradStudent objGradStudent : tempGradStudent) {
            if (qualStudentList.isEmpty()) {
                qualStudentList.add(objGradStudent);
            } else {
                boolean value = false;

                for (GradStudent myGradStudent : qualStudentList) {
                    if (myGradStudent.equals(objGradStudent)) {
                        value = true;
                    }
                }

                if (!value) {
                    qualStudentList.add(objGradStudent);
                }
            }
        }

        FacesContext.getCurrentInstance()
                .getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/hod/students.xhtml?faces-redirect=true");
    }

    
     public int getStudent(Qualification qual, Campus campus) {
         
    
         
         int number=0;
     

        

        List<GradStudent> tempGradStudent= new ArrayList<>(), objUsed = new ArrayList<>();

        for (GradStudent objGradStudent : gradStudentList) {
            if (objGradStudent.getAdmitStatus() != null) {
                if (objGradStudent.getAdmitStatus().equalsIgnoreCase(status.getFRAUD_APPROVE())
                        && objGradStudent.getAdmitQualcode().equalsIgnoreCase(qual.getQualCode())
                        && objGradStudent.getOfferingType().substring(0, 1).equalsIgnoreCase(campus.getShortCode())) {
                    tempGradStudent.add(objGradStudent);
                }
            }
        }

        for (GradStudent objGradStudent : tempGradStudent) {
            if (objUsed.isEmpty()) {
                objUsed.add(objGradStudent);
            } else {
                boolean value = false;

                for (GradStudent myGradStudent : objUsed) {
                    if (myGradStudent.equals(objGradStudent)) {
                        value = true;
                    }
                }

                if (!value) {
                    objUsed.add(objGradStudent);
                }
            }
        }

        number=objUsed.size();
        
        
       return number;
    }
    
    
    
    
    public void btnApproveStudentRevertion(Hod updateHod) {
        gradStudent = getGraduate(updateHod.getAdmitStno());

        if (updateHod.getStatus().equalsIgnoreCase("APPROVED")) {
            msg.warn("No changes made to " + gradStudent.getAdmitInit() + " " + gradStudent.getAdmitSurname() + " (" + gradStudent.getAdmitStno() + ") since the student was approved already");
        } else {
            gradStudent.setAdmitStatus(status.getHOD_APPROVE());
            updateHod.setStatus("APPROVED");
            hodFacade.edit(updateHod);
            gradStudentFacade.edit(gradStudent);

            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            GradLog gradLog = new GradLog();

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("CHANGE HOD APPROVAL");
            gradLog.setComment("User: [" + userid + "]" + " CHANGE HOD APPROVAL: " + updateHod.getStatus());
            gradLogFacade.create(gradLog);

             sendEmail.sendEmail("210136758@edu.vut.ac.za", email.getHodSubjectApprove(), "Dear " + gradStudent.getAdmitFullnames()
        +"\n \n " + email.getHodBodyApprove() );
             
            populateHODList();
            refreshPage();

            msg.info("Status Changed Successfully for " + updateHod.getAdmitStno());

        }

    }

    public void btnRejectStudentRevertion(Hod updateHod) {
        gradStudent = getGraduate(updateHod.getAdmitStno());
        hod = updateHod;

        if (updateHod.getStatus().equalsIgnoreCase("REJECTED")) {
            msg.warn("No changes made to " + gradStudent.getAdmitInit() + " " + gradStudent.getAdmitSurname() + " (" + gradStudent.getAdmitStno() + ") since the student was rejected already");
        } else {

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').show();");
        }
    }

    public void btnClear()
    {
    comment="";
    }
    
    public void btnConfirmRejcetionRevertion() {
        if (comment.isEmpty()) {
            msg.error("Please comment why you reject this student");
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').show();");
        } else {
            gradStudent.setAdmitStatus(status.getHOD_REJECT());
            gradStudent.setComment(comment);
            hod.setStatus("REJECTED");
            hodFacade.edit(hod);
            gradStudentFacade.edit(gradStudent);

            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            GradLog gradLog = new GradLog();

                      sendEmail.sendEmail("210136758@edu.vut.ac.za", email.getHodSubjectReject(), "Dear " + gradStudent.getAdmitFullnames()
        +"\n \n " + email.getHodBodyReject() );
                      
            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("CHANGE HOD APPROVAL");
            gradLog.setComment("User: [" + userid + "]" + " CHANGE HOD APPROVAL: " + hod.getStatus());
            gradLogFacade.create(gradLog);

            populateHODList();
            refreshPage();

            msg.info("Status Changed Successfully for " + hod.getAdmitStno());
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

    private Hod getHOD(String stNo) {
        Hod grad = new Hod();

        for (Hod g : hodList) {
            if (g.getAdmitStno() != null) {
                if (g.getAdmitStno().equalsIgnoreCase(stNo)) {
                    grad = g;
                }
            }
        }

        return grad;
    }

    public GradStudent getGraduate(String stNo) {
        GradStudent grad = new GradStudent();

        for (GradStudent g : gradStudentList) {
            if (g.getAdmitStno() != null) {
                if (g.getAdmitStno().equalsIgnoreCase(stNo)) {
                    grad = g;
                }
            }
        }

        return grad;
    }

    public void btnApproveStudent(GradStudent gradStudent) {

        gradStudent.setAdmitStatus(status.getHOD_APPROVE());
        gradStudentFacade.edit(gradStudent);

        FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

        String userid = (String) session.getAttribute("user");

        GradLog gradLog = new GradLog();

        gradLog.setLogDate(today);
        gradLog.setLogTime(time);
        gradLog.setLogType("HOD APPROVAL");
        gradLog.setComment("User: [" + userid + "] CHANGED STATUS FOR " + gradStudent.getAdmitStno() + " to: " + gradStudent.getAdmitStatus());
        gradLogFacade.create(gradLog);

        Hod hod = new Hod();
        hod.setAdmitStno(gradStudent.getAdmitStno());
        hod.setDate(today);
        hod.setUsername(userid);
        hod.setTime(time);

        hod.setStatus("APPROVED");

        if (checkStudNum(gradStudent.getAdmitStno())) {
            //edit
            hodFacade.edit(hod);
        } else {
            //add
            hodFacade.edit(hod);
        }

                   sendEmail.sendEmail("210136758@edu.vut.ac.za",email.getHodSubjectApprove(), "Dear " + gradStudent.getAdmitFullnames()
        +"\n \n " + email.getHodBodyApprove());
                   
        refreshStudents();

        msg.info("Status Changed Successfully");
    }

    private void refreshStudents() {
        qualStudentList.clear();

        List<GradStudent> tempGradStudent = new ArrayList<>();

        for (GradStudent objGradStudent : gradStudentList) {
            if (objGradStudent.getAdmitStatus() != null) {
                if (objGradStudent.getAdmitStatus().equalsIgnoreCase(status.getFRAUD_APPROVE())
                        && objGradStudent.getAdmitQualcode().equalsIgnoreCase(selectedQualification.getQualCode())
                        && objGradStudent.getOfferingType().substring(0, 1).equalsIgnoreCase(selecteCampus.getShortCode())) {
                    tempGradStudent.add(objGradStudent);
                }
            }
        }

        for (GradStudent objGradStudent : tempGradStudent) {
            if (qualStudentList.isEmpty()) {
                qualStudentList.add(objGradStudent);
            } else {
                boolean value = false;

                for (GradStudent myGradStudent : qualStudentList) {
                    if (myGradStudent.equals(objGradStudent)) {
                        value = true;
                    }
                }

                if (!value) {
                    qualStudentList.add(objGradStudent);
                }
            }
        }

    }

    public void btnRefreshRevertChnages() {
        searchInput = "";
        populateHODList();
    }

    public void btnsearchStudent() {

        hodList.clear();

        FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

        String userid = (String) session.getAttribute("user");

        List<Hod> temList = new ArrayList<>(), newList = new ArrayList<>();;

        for (Hod myHod : allHodList) {
            if (myHod.getUsername().equalsIgnoreCase(userid)) {
                //   hodList.add(myHod);
                temList.add(myHod);
            }
        }

        for (Hod hodObj : temList) {
            if (newList.isEmpty()) {
                newList.add(hodObj);
            } else {
                boolean value = false;

                for (Hod objHod : newList) {
                    if (objHod.getAdmitStno().equalsIgnoreCase(hodObj.getAdmitStno())) {
                        value = true;
                    }
                }

                if (!value) {
                    newList.add(hodObj);
                }
            }
        }

        for (Hod myHod : newList) {
            GradStudent myStudent = getGraduate(myHod.getAdmitStno());

            if (myHod.getAdmitStno().toLowerCase().contains(searchInput.toLowerCase())
                    || myStudent.getAdmitIdpassport().toLowerCase().contains(searchInput.toLowerCase())
                    || myStudent.getAdmitSurname().toLowerCase().contains(searchInput.toLowerCase())) {
                hodList.add(myHod);
            }
        }

    }

    private boolean checkStudNum(String number) {
        boolean value = false;
        for (Hod h : hodList) {
            if (h.getAdmitStno() != null) {
                if (h.getAdmitStno().equalsIgnoreCase(number)) {
                    value = true;
                }
            }
        }
        return value;
    }

    public void btnRejectStudent(GradStudent objGradStudent) {
        
        gradStudent = objGradStudent;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg').show();");
    }

    public void btnConfirmRejection() {

        if (comment.isEmpty()) {
            msg.error("Please comment why you reject this student");
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').show();");
        } else {

            gradStudent.setAdmitStatus(status.getHOD_REJECT());
            gradStudent.setComment(comment);
            gradStudentFacade.edit(gradStudent);

            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            GradLog gradLog = new GradLog();

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("HOD REJECTION");
            gradLog.setComment("User: [" + userid + "] CHANGED STATUS FOR " + gradStudent.getAdmitStno() + " to: " + gradStudent.getAdmitStatus());
            gradLogFacade.create(gradLog);

            Hod hod = new Hod();
            hod.setAdmitStno(gradStudent.getAdmitStno());
            hod.setDate(today);
            hod.setUsername(userid);
            hod.setTime(time);

            hod.setStatus("REJECTED");

            if (checkStudNum(gradStudent.getAdmitStno())) {
                //edit
                hodFacade.edit(hod);
            } else {
                //add
                hodFacade.edit(hod);
            }
            
                       sendEmail.sendEmail("210136758@edu.vut.ac.za", email.getHodSubjectReject(), "Dear " + gradStudent.getAdmitFullnames()
        +"\n \n "+email.getHodBodyReject() );

            refreshStudents();

            msg.info("Status Changed Successfully");
        }

    }
}
