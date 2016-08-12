/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.controller;

import javax.ejb.EJB;
import javax.faces.bean.*;
import za.ac.vut.facade.*;
import za.ac.vut.entity.*;
import za.ac.vut.util.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped

public class FraudController {

    @EJB
    private EmailConfigurationFacade emailConfigurationFacade;
    
    @EJB
    private FraudFacade fraudFacade;
    @EJB
    private GradLogFacade gradLogFacade;
    @EJB
    private GradStudentFacade gradStudentFacade;

   private Redirect redirect=new Redirect();
   
    private Fraud fraud = new Fraud();
    private GradLog gradLog = new GradLog();
    private GradStudent gradStudent = new GradStudent();
    private Messsage msg = new Messsage();
    private Status status = new Status();
      private EmailConfiguration email;

    private List<Fraud> fraudList = new ArrayList<>(), allFraudStud=new ArrayList<>();
    private List<GradStudent> gradStudentList = new ArrayList<>();

    private Date today = new Date();
    private String time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds(),
            comment, searchStud;

    private SendEmail sendEmail=new SendEmail();
    
    @PostConstruct
    public void init() {
        
          for (EmailConfiguration myemail : emailConfigurationFacade.findAll()) {
            email = myemail;
        }
          
        //      fraudList = fraudFacade.findAll();
        gradStudentList = gradStudentFacade.findAll();

        allFraudStud=fraudFacade.findAll();
        
        
        populatefraudList();

    }
    
    
    public void openRevertChnages()
    {
    populatefraudList();
    redirect.fraudRevertChanges();
    
    
    }
    

    private void populatefraudList() {
        fraudList.clear();

        FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

        String userid = (String) session.getAttribute("user");

        List<Fraud> tempList = new ArrayList<>();

        for (Fraud myFraud : allFraudStud) {

            String mystatus =getGraduateStudent(myFraud.getAdmitStno()).getAdmitStatus();

            if (mystatus != null) {
                if (myFraud.getUsername().equalsIgnoreCase(userid)
                        && (mystatus
                        .equalsIgnoreCase(status.getFRAUD_REJECT())
                        || mystatus
                        .equalsIgnoreCase(status.getFRAUD_APPROVE()))) {
                    tempList.add(myFraud);
                }
            }
        }

        for (Fraud fraudStudent : tempList) {

            if (fraudList.isEmpty()) {
                fraudList.add(fraudStudent);
            } else {
                boolean value = false;
                for (Fraud myFraud : fraudList) {
                    if (myFraud.getAdmitStno().equalsIgnoreCase(fraudStudent.getAdmitStno())) {
                        value = true;
                    }
                }

                if (!value) {
                    fraudList.add(fraudStudent);
                }
            }
        }
    }

    public void refreshFraudList() {
        searchStud = "";
        populatefraudList();
    }

    public List<Fraud> getFraudList() {
        return fraudList;
    }

    public GradStudent getGradStudent() {
        return gradStudent;
    }

    public void setGradStudent(GradStudent gradStudent) {
        this.gradStudent = gradStudent;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSearchStud() {
        return searchStud;
    }

    public void setSearchStud(String searchStud) {
        this.searchStud = searchStud;
    }

    public void searchFraudList() {
        fraudList.clear();

        FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

        String userid = (String) session.getAttribute("user");

        List<Fraud> tempList = new ArrayList<>(), searchList = new ArrayList<>();

        for (Fraud myFraud : allFraudStud) {
            String mystatus = getGraduateStudent(myFraud.getAdmitStno()).getAdmitStatus();

            if (mystatus != null) {
                if (myFraud.getUsername().equalsIgnoreCase(userid)
                        && (mystatus
                        .equalsIgnoreCase(status.getFRAUD_REJECT())
                        || mystatus
                        .equalsIgnoreCase(status.getFRAUD_APPROVE()))) {
                    tempList.add(myFraud);
                }
            }
        }

        for (Fraud fraudobj : tempList) {

            if (searchList.isEmpty()) {
                searchList.add(fraudobj);
            } else {
                boolean value = false;
                for (Fraud myFraudf : searchList) {
                    if (myFraudf.getAdmitStno().equalsIgnoreCase(fraudobj.getAdmitStno())) {
                        value = true;
                    }
                }

                if (!value) {
                    searchList.add(fraudobj);
                }
            }
        }

        fraudList.clear();

        for (Fraud fraudObject : searchList) {
            GradStudent myGradStudent = getGraduateStudent(fraudObject.getAdmitStno());

            if (fraudObject.getAdmitStno().toLowerCase().contains(searchStud.toLowerCase())
                    || myGradStudent
                    .getAdmitIdpassport().toLowerCase().contains(searchStud.toLowerCase())
                    || myGradStudent.getAdmitSurname().toLowerCase().contains(searchStud.toLowerCase())) {
                fraudList.add(fraudObject);
            }
        }

    }

    public void btnApprovedStudent(Fraud fraudStudent) {
        gradStudent = getGraduateStudent(fraudStudent.getAdmitStno());

        if (gradStudent.getAdmitStatus().equalsIgnoreCase(status.getFRAUD_APPROVE())) {
            msg.warn("No changes made to " + gradStudent.getAdmitInit() + " " + gradStudent.getAdmitSurname() + " (" + gradStudent.getAdmitStno() + ") since student has been approved already");
        } else if (gradStudent.getAdmitStatus().equalsIgnoreCase(status.getFRAUD_REJECT())) {
            gradStudent.setAdmitStatus(status.getFRAUD_APPROVE());
            gradStudentFacade.edit(gradStudent);
            fraudStudent.setStatus("APPROVED");
            fraudFacade.edit(fraudStudent);

            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("CHANGE VERIFICATION STATUS");
            gradLog.setComment("User: [" + userid + "] CHANGE VERIFICATION STATUS FOR " + gradStudent.getAdmitStno() + " to: " + gradStudent.getAdmitStatus());
            gradLogFacade.create(gradLog);

                 sendEmail.sendEmail("210136758@edu.vut.ac.za", email.getFraudSubjectApprove(), "Dear " + gradStudent.getAdmitFullnames()
        +"\n \n "+ email.getFraudBodyApprove());
                 
            populatefraudList();

            refreshPage();

            msg.info("Status Changed Successfully");
        }
    }

    public void btnRejectStudent(Fraud fraudStudent) {
        gradStudent = getGraduateStudent(fraudStudent.getAdmitStno());
        if (fraudStudent.getStatus().equalsIgnoreCase("REJECTED")
                || gradStudent.getAdmitStatus().equalsIgnoreCase(status.getFRAUD_REJECT())) {
            msg.warn("No changes made to " + gradStudent.getAdmitInit() + " " + gradStudent.getAdmitSurname() + " (" + gradStudent.getAdmitStno() + ") since student has been rejected already");
        } else {
            fraud = fraudStudent;

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').show();");
        }
    }

    public void btnClear()
    {
    comment="";
    }
    
    public void btnfraudConfirmReject() {
        if (comment.isEmpty()) {
            msg.error("Please enter comment for your rejection for this student");

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').show();");
        } else {
            gradStudent.setAdmitStatus(status.getFRAUD_REJECT());
            gradStudent.setComment(comment);
            gradStudentFacade.edit(gradStudent);
            fraud.setStatus("REJECTED");
            fraudFacade.edit(fraud);

            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("CHANGE VERIFICATION STATUS");
            gradLog.setComment("User: [" + userid + "] CHANGE VERIFICATION STATUS FOR " + gradStudent.getAdmitStno() + " to: " + gradStudent.getAdmitStatus());
            gradLogFacade.create(gradLog);
            
             sendEmail.sendEmail("210136758@edu.vut.ac.za", email.getFraudSubjectReject(), "Dear " + gradStudent.getAdmitFullnames()
        +"\n \n "+ email.getFraudBodyReject());

            populatefraudList();

            refreshPage();

            msg.info("Status Changed Successfully");
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

    public GradStudent getGraduateStudent(String stNo) {
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

}
