/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.controller;

import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.ViewHandler;
import javax.faces.bean.*;
import javax.faces.component.UIViewRoot;
import javax.faces.context.*;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import za.ac.vut.entity.*;
import za.ac.vut.facade.*;
import za.ac.vut.util.*;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped

public class StudentController implements Serializable {

    @EJB
    private UserFacade userFacade;

    @EJB
    private CeremonyFacade ceremonyFacade;

    private Ceremony ceremony;

    @EJB
    private GradStudentFacade studentFacade;

    private GradStudent gradStudent;

    @EJB
    private StudAcademicRecordFacade studAcademicRecordFacade;

    @EJB
    private GradLogFacade gradLogFacade;

    @EJB
    private EmailConfigurationFacade emailConfigurationFacade;

    @EJB
    private HodFacade hodFacade;

    private String selectedQual, DocRecieveDate, gradautionAttendanceStatus, docsReceivedBy;

    private List<Hod> hodList = new ArrayList<>();

    private Messsage msg = new Messsage();

    private Status status = new Status();

    private List<StudMatric> matricList = new ArrayList<>(), allMatricList = new ArrayList<>();

    private Redirect rd = new Redirect();

    private Date today = new Date();

    private String time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

    private String comment, searchInput, searchFraud, studNum, IDPassNum, ceremonoyStatus = "Yes", newStatus;

    private boolean id, matric;
    private SendEmail sendEmail = new SendEmail();

    private List<GradStudent> possibleStud = new ArrayList<>(), examRecievedStud = new ArrayList<>(), allStudList = new ArrayList<>();

    private List<String> qualificationList = new ArrayList<>(), fraudQualificationList = new ArrayList<>();

    private List<StudAcademicRecord> academicList = new ArrayList<>();
    private List<GradLog> gradLogList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();

    @EJB
    private StudMatricFacade matricFacade;

    @EJB
    private FraudFacade fraudFacade;

    private List<Fraud> fraudList = new ArrayList<>();

    private Map<String, Map<String, String>> data = new HashMap<>();

    private Map<String, String> statuses;
    private List<Ceremony> allceremonyStudList = new ArrayList<>();

    private EmailConfiguration email;

    @PostConstruct
    public void init() {

        for (EmailConfiguration myemail : emailConfigurationFacade.findAll()) {
            email = myemail;
        }
        
        userList = userFacade.findAll();
        allceremonyStudList = ceremonyFacade.findAll();
        gradLogList = gradLogFacade.findAll();

        hodList = hodFacade.findAll();
        fraudList = fraudFacade.findAll();
        allMatricList = matricFacade.findAll();
        academicList = studAcademicRecordFacade.findAll();
        final String POSSIBLE = "POSSIBLE";
        final String EXAM_RECIEVE = "EXAM:Received";

        List<String> tempQual = new ArrayList<>(), tempFraudQualList = new ArrayList<>();

        allStudList = studentFacade.findAll();

        for (GradStudent myStud : allStudList) {
            if (myStud.getAdmitStatus() != null) {
                if (myStud.getAdmitStatus().equalsIgnoreCase(POSSIBLE)) {
                    possibleStud.add(myStud);
                    tempQual.add(myStud.getAdmitQualdesc());
                }
            }
        }

        for (GradStudent objStud : allStudList) {
            if (objStud.getAdmitStatus() != null) {
                if (objStud.getAdmitStatus().equalsIgnoreCase(EXAM_RECIEVE)) {
                    examRecievedStud.add(objStud);
                    tempFraudQualList.add(objStud.getAdmitQualdesc());
                }
            }
        }

        for (String objRslt : tempQual) {
            if (qualificationList.isEmpty()) {
                qualificationList.add(objRslt);
            } else {
                boolean value = false;

                for (String vvv : qualificationList) {
                    if (objRslt.equalsIgnoreCase(vvv)) {
                        value = true;
                    }
                }

                if (!value) {
                    qualificationList.add(objRslt);
                }
            }
        }

        for (String objRslt : tempFraudQualList) {
            if (fraudQualificationList.isEmpty()) {
                fraudQualificationList.add(objRslt);
            } else {
                boolean value = false;

                for (String vvv : fraudQualificationList) {
                    if (objRslt.equalsIgnoreCase(vvv)) {
                        value = true;
                    }
                }

                if (!value) {
                    fraudQualificationList.add(objRslt);
                }
            }
        }

        Collections.sort(fraudQualificationList);
        Collections.sort(qualificationList);

        statuses = new HashMap<>();
        statuses.put("Reject", "Verification:Rejected");
        statuses.put("Approve", "Verification:Approved");

        Map<String, String> map = new HashMap<>();
        map.put("Both Genuine", "Genuine Matric and ID");
        data.put("Verification:Approved", map);

        map = new HashMap<>();
        map.put("Doubtful ID", "Doubtful ID");
        map.put("Doubtful Matric", "Doubtful Matric");
        map.put("Both doubtful", "Doubtful Matric and ID");
        data.put("Verification:Rejected", map);
    }

    public String getSelectedQual() {
        return selectedQual;
    }

    public void setDocRecieveDate(String DocRecieveDate) {
        this.DocRecieveDate = DocRecieveDate;
    }

    public String getDocRecieveDate() {
        return DocRecieveDate;
    }

    public void setSelectedQual(String selectedQual) {
        this.selectedQual = selectedQual;
    }

    public double getSum(String number) {
        double sum = 0;
        sum += Double.parseDouble(number);
        return sum;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public Map<String, String> getStatuses() {
        return statuses;
    }

    public void setMatricList(List<StudMatric> matricList) {
        this.matricList = matricList;

    }

    public List<StudMatric> getMatricList() {
        return matricList;
    }

    public List<String> getFraudQualificationList() {
        return fraudQualificationList;
    }

    public String getGradautionAttendanceStatus() {
        return gradautionAttendanceStatus;
    }

    public void setGradautionAttendanceStatus(String gradautionAttendanceStatus) {
        this.gradautionAttendanceStatus = gradautionAttendanceStatus;
    }

    public void setFraudQualificationList(List<String> fraudQualificationList) {
        this.fraudQualificationList = fraudQualificationList;
    }

    public void setQualificationList(List<String> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public void setDocsReceivedBy(String docsReceivedBy) {
        this.docsReceivedBy = docsReceivedBy;
    }

    public String getDocsReceivedBy() {
        return docsReceivedBy;
    }

    public List<String> getQualificationList() {
        return qualificationList;
    }

    public void setSearchFraud(String searchFraud) {
        this.searchFraud = searchFraud;
    }

    public String getSearchFraud() {
        return searchFraud;
    }

    public StudentController() {
        gradStudent = new GradStudent();

    }

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public String getStudNum() {
        return studNum;
    }

    public void setStudNum(String studNum) {
        this.studNum = studNum;
    }

    public void setCeremonoyStatus(String ceremonoyStatus) {
        this.ceremonoyStatus = ceremonoyStatus;
    }

    public String getCeremonoyStatus() {
        return ceremonoyStatus;
    }

    public void setExamRecievedStud(List<GradStudent> examRecievedStud) {
        this.examRecievedStud = examRecievedStud;
    }

    public List<GradStudent> getExamRecievedStud() {
        return examRecievedStud;
    }

    public void setIDPassNum(String IDPassNum) {
        this.IDPassNum = IDPassNum;
    }

    public String getIDPassNum() {
        return IDPassNum;
    }

    public List<GradStudent> getPossibleStud() {
        return possibleStud;
    }

    public void searchPossible() {
        possibleStud.clear();

        for (GradStudent objGradStudent : allStudList) {
            if (objGradStudent.getAdmitStatus() != null) {
                if (objGradStudent.getAdmitStatus().equalsIgnoreCase(status.getPOSSIBLE())) {
                    if ((objGradStudent.getAdmitStno().contains(searchInput)
                            || objGradStudent.getAdmitIdpassport().contains(searchInput)
                            || objGradStudent.getAdmitSurname().contains(searchInput)
                            || objGradStudent.getAdmitFullnames().contains(searchInput))) {
                        possibleStud.add(objGradStudent);
                    }
                }
            }

        }
    }

    public void searchFraudMethod() {
        examRecievedStud.clear();

        for (GradStudent objGradStudent : allStudList) {
            if (objGradStudent.getAdmitStatus() != null) {
                if (objGradStudent.getAdmitStatus().equalsIgnoreCase(status.getEXAM_RECIEVE())) {
                    if (objGradStudent.getAdmitStno().contains(searchFraud)
                            || objGradStudent.getAdmitIdpassport().contains(searchFraud.toUpperCase())
                            || objGradStudent.getAdmitSurname().contains(searchFraud.toUpperCase())
                            || objGradStudent.getAdmitFullnames().contains(searchFraud.toUpperCase())) {
                        examRecievedStud.add(objGradStudent);
                    }
                }
            }

        }
    }

    public void setPossibleStud(List<GradStudent> possibleStud) {
        this.possibleStud = possibleStud;
    }

    public void setMatric(boolean matric) {
        this.matric = matric;
    }

    public void setID(boolean id) {
        this.id = id;
    }

    public boolean getID() {
        return id;
    }

    public boolean getMatric() {
        return matric;
    }

    public GradStudent getGradStudent() {
        return gradStudent;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setGradStudent(GradStudent gradStudent) {
        this.gradStudent = gradStudent;
    }

    public GradStudent getStudentObject(String studInput) {
        GradStudent studentList = null;
        for (GradStudent mygrad : allStudList) {
            if (mygrad.getAdmitStno().equalsIgnoreCase(studInput)
                    || mygrad.getAdmitIdpassport().equalsIgnoreCase(studInput)) {
                studentList = mygrad;
            }
        }
        return studentList;
    }

    public List<StudAcademicRecord> studentAcademicInfo(GradStudent student) {

        List<StudAcademicRecord> record = new ArrayList<>();

        for (StudAcademicRecord academic : academicList) {

            if (student.getAdmitStno().equalsIgnoreCase(academic.getAdmitStno())) {
                record.add(academic);
            }

        }

        Collections.sort(record, new Comparator<StudAcademicRecord>() {
            @Override
            public int compare(StudAcademicRecord c1, StudAcademicRecord c2) {
                return c1.getAdmitSubjectcode().substring(5, 6).compareTo(c2.getAdmitSubjectcode().substring(5, 6));
            }
        });

        List<StudAcademicRecord> result = new ArrayList<>();

        for (StudAcademicRecord objRslt : record) {
            if (result.isEmpty()) {
                result.add(objRslt);
            } else {
                boolean value = false;

                for (StudAcademicRecord vvv : result) {
                    if (objRslt.getAdmitSubjectcode().equalsIgnoreCase(vvv.getAdmitSubjectcode())) {
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

    public List<StudAcademicRecord> studentAcademicInfoViaStudentNum(String student) {

        List<StudAcademicRecord> record = new ArrayList<>();

        for (StudAcademicRecord academic : academicList) {

            if (student.equalsIgnoreCase(academic.getAdmitStno())) {
                record.add(academic);
            }

        }

        Collections.sort(record, new Comparator<StudAcademicRecord>() {
            @Override
            public int compare(StudAcademicRecord c1, StudAcademicRecord c2) {
                return c1.getAdmitSubjectcode().substring(5, 6).compareTo(c2.getAdmitSubjectcode().substring(5, 6));
            }
        });

        List<StudAcademicRecord> result = new ArrayList<>();

        for (StudAcademicRecord objRslt : record) {
            if (result.isEmpty()) {
                result.add(objRslt);
            } else {
                boolean value = false;

                for (StudAcademicRecord vvv : result) {
                    if (objRslt.getAdmitSubjectcode().equalsIgnoreCase(vvv.getAdmitSubjectcode())) {
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

    public double getAvg(GradStudent student) {
        double number = 0;
        double sum = 0;

        for (StudAcademicRecord academic : studentAcademicInfo(student)) {

            sum += Double.parseDouble(academic.getAdmitMark());

        }

        number = sum / studentAcademicInfo(student).size();
        return Math.round(number);

    }

    public double getCredits(GradStudent student) {
        double number = 0;

        for (StudAcademicRecord academic : studentAcademicInfo(student)) {

            number += Double.parseDouble(academic.getAdmitCreditscore());

        }

        return Math.round(number);

    }

    public void onRowEdit(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        GradStudent updateGraduate = (GradStudent) event.getObject();

        if (updateGraduate.getAdmitStatus().equalsIgnoreCase("POSSIBLE")) {
            msg.error("Please select Graduation Status");

        } else {

            ceremony = new Ceremony();

            ceremony.setAdmitStno(updateGraduate.getAdmitStno());
            ceremony.setStatus(updateGraduate.getAdmitStatus());

            ceremonyFacade.create(ceremony);

            updateGraduate.setAdmitStatus(status.getEXAM_RECIEVE());
            studentFacade.edit(updateGraduate);

            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            GradLog gradLog = new GradLog();

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("RECIEVE DOCUMENTS");
            gradLog.setComment("User: [" + userid + "] RECIEVE DOCUMENTS FOR " + updateGraduate.getAdmitStno() + " GRADUATION ATTENDANCE: " + ceremony.getStatus());
            gradLogFacade.create(gradLog);

            FacesContext.getCurrentInstance().renderResponse();

            refreshPossibleStud();

            refreshPage();

            msg.info("Document Received Successfully");

        }

    }

    public void onRowSelect(SelectEvent event) {
        String stNo = ((GradStudent) event.getObject()).getAdmitStno();

        String tempVar = "";

        Date result = new Date();
        for (GradLog gradLog : gradLogList) {
            if (gradLog.getLogType().toLowerCase().contains("RECIEVE DOCUMENTS".toLowerCase())
                    && gradLog.getComment().toLowerCase().contains(stNo)) {
                result = gradLog.getLogDate();
                tempVar = gradLog.getComment();
            }
        }

        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(tempVar.substring(7, tempVar.indexOf("]")))) {
                docsReceivedBy = user.getUsername() + "-" + user.getName();
            }
        }

        DocRecieveDate = getFormatedDate("" + result);
        gradautionAttendanceStatus = getGradAttendance(stNo);
        // eturn result;
    }

    private String getGradAttendance(String stNo) {
        String attendance = "";

        for (Ceremony objCeremony : allceremonyStudList) {
            if (objCeremony.getAdmitStno().equalsIgnoreCase(stNo)) {
                attendance = objCeremony.getStatus();
            }
        }

        if (attendance.equalsIgnoreCase("Yes")) {
            attendance = "Presencia";
        } else if (attendance.equalsIgnoreCase("No")) {
            attendance = "Absencia";
        }

        return attendance;
    }

    private String getFormatedDate(String myDate) {
        String year = myDate.substring(24);
        String month = myDate.substring(4, 8);
        String day = myDate.substring(8, 11);
        return day + "-" + month + "-" + year;
    }

    public void btnRecieveDocumentPresencia(GradStudent gradStudent) {
        ceremony = new Ceremony();

        ceremony.setAdmitStno(gradStudent.getAdmitStno());
        ceremony.setStatus("Yes");

        ceremonyFacade.edit(ceremony);

        gradStudent.setAdmitStatus(status.getEXAM_RECIEVE());
        studentFacade.edit(gradStudent);

        FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

        String userid = (String) session.getAttribute("user");

        GradLog gradLog = new GradLog();

        gradLog.setLogDate(today);
        gradLog.setLogTime(time);
        gradLog.setLogType("RECIEVE DOCUMENTS");
        gradLog.setComment("User: [" + userid + "] RECIEVE DOCUMENTS FOR " + gradStudent.getAdmitStno() + " GRADUATION ATTENDANCE: " + ceremony.getStatus());
        gradLogFacade.create(gradLog);

        FacesContext.getCurrentInstance().renderResponse();

        sendEmail.sendEmail("210136758@edu.vut.ac.za", email.getExamSubject(), "Dear " + gradStudent.getAdmitFullnames()
                + "\n \n "+email.getExamBody() +"\n \n"
                + " You have indicated that you will be PRESENT in the graduation ceremony. "
                );

        refreshPossibleStud();

        refreshPage();

        msg.info("Document Received Successfully");
    }

    public void btnRecieveDocumentAbsencia(GradStudent gradStudent) {
        ceremony = new Ceremony();

        ceremony.setAdmitStno(gradStudent.getAdmitStno());
        ceremony.setStatus("No");

        ceremonyFacade.edit(ceremony);

        gradStudent.setAdmitStatus(status.getEXAM_RECIEVE());
        studentFacade.edit(gradStudent);

        FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

        String userid = (String) session.getAttribute("user");

        GradLog gradLog = new GradLog();

        gradLog.setLogDate(today);
        gradLog.setLogTime(time);
        gradLog.setLogType("RECIEVE DOCUMENTS");
        gradLog.setComment("User: [" + userid + "] RECIEVE DOCUMENTS FOR " + gradStudent.getAdmitStno() + " GRADUATION ATTENDANCE: " + ceremony.getStatus());
        gradLogFacade.create(gradLog);

        FacesContext.getCurrentInstance().renderResponse();

        sendEmail.sendEmail("210136758@edu.vut.ac.za", email.getExamSubject(), "Dear " + gradStudent.getAdmitFullnames()
                + "\n \n" +email.getExamBody() +"\n \n"
                + " You have indicated that you will be ABSENT in the graduation ceremony");

        refreshPossibleStud();

        refreshPage();

        msg.info("Document Received Successfully");
    }

    private void refreshPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }

    public void onCancelExam(RowEditEvent event) {
        //FacesContext context = FacesContext.getCurrentInstance();

        GradStudent updateGraduate = (GradStudent) event.getObject();

        msg.warn("No changes made to " + updateGraduate.getAdmitInit() + " " + updateGraduate.getAdmitSurname() + " (" + updateGraduate.getAdmitStno() + ")");
    }

    public String getStatus(String status) {
        String result = "";
        if (status.equals("No")) {
            result = "Absentia";
        } else if (status.equalsIgnoreCase("Yes")) {
            result = "Presentia";
        }
        return result;
    }

    public void onRowfraud(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        GradStudent updateGraduate = (GradStudent) event.getObject();
        updateGraduate.setAdmitStatus(newStatus);

        if (updateGraduate.getAdmitStatus().equalsIgnoreCase(status.getFRAUD_REJECT())
                && updateGraduate.getComment().isEmpty()) {
            msg.error("Comment is required when student is rejected");
        } else {

            studentFacade.edit(updateGraduate);

            examRecievedStud.remove(updateGraduate);

            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            GradLog gradLog = new GradLog();

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("DOCUMENT VERIFICATION");
            gradLog.setComment("User: [" + userid + "] VERIFIED DOCUMENTS FOR " + updateGraduate.getAdmitStno() + " to: " + updateGraduate.getAdmitStatus());
            gradLogFacade.create(gradLog);

            Fraud fraud = new Fraud();
            fraud.setAdmitStno(updateGraduate.getAdmitStno());
            fraud.setDate(today);
            fraud.setUsername(userid);
            fraud.setTime(time);

            if (updateGraduate.getAdmitStatus().equalsIgnoreCase(status.getFRAUD_APPROVE())) {
                fraud.setStatus("APPROVED");
            } else if (updateGraduate.getAdmitStatus().equalsIgnoreCase(status.getFRAUD_REJECT())) {
                fraud.setStatus("REJECTED");
            }

            if (checkStudentNum(updateGraduate.getAdmitStno())) {
                fraudFacade.edit(fraud);
            } else {
                fraudFacade.create(fraud);
            }

            msg.info("Status Changed Successfully");

            FacesContext.getCurrentInstance().renderResponse();
            refreshexamRecievedStud();
        }
    }

    public void btnFraudApproveStudent(GradStudent objGradStudent) {
        objGradStudent.setAdmitStatus(status.getFRAUD_APPROVE());
        studentFacade.edit(objGradStudent);

        examRecievedStud.remove(objGradStudent);

        FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

        String userid = (String) session.getAttribute("user");

        GradLog gradLog = new GradLog();

        gradLog.setLogDate(today);
        gradLog.setLogTime(time);
        gradLog.setLogType("DOCUMENT VERIFICATION");
        gradLog.setComment("User: [" + userid + "] VERIFIED DOCUMENTS FOR " + objGradStudent.getAdmitStno() + " to: " + objGradStudent.getAdmitStatus());
        gradLogFacade.create(gradLog);

        Fraud fraud = new Fraud();
        fraud.setAdmitStno(objGradStudent.getAdmitStno());
        fraud.setDate(today);
        fraud.setUsername(userid);
        fraud.setTime(time);

        fraud.setStatus("APPROVED");

        if (checkStudentNum(objGradStudent.getAdmitStno())) {
            fraudFacade.edit(fraud);
        } else {
            fraudFacade.create(fraud);
        }

        sendEmail.sendEmail("210136758@edu.vut.ac.za",email.getFraudSubjectApprove(), "Dear " + objGradStudent.getAdmitFullnames()
                + "\n \n" + email.getFraudBodyApprove() );

        FacesContext.getCurrentInstance().renderResponse();
        //  refreshexamRecievedStud();
        refreshPage();

        msg.info("Status Changed Successfully");

    }

    public void btnClearAll()
    {
    comment="";
    }
    
    
    public void btnFraudConfirmReject() {
        if (comment.isEmpty()) {
            msg.error("Please enter comment for your rejection for this student");

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').show();");
        } else {
            gradStudent.setAdmitStatus(status.getFRAUD_REJECT());
            gradStudent.setComment(comment);
            studentFacade.edit(gradStudent);

            examRecievedStud.remove(gradStudent);

            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            GradLog gradLog = new GradLog();

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("DOCUMENT VERIFICATION");
            gradLog.setComment("User: [" + userid + "] VERIFIED DOCUMENTS FOR " + gradStudent.getAdmitStno()
                    + " to: " + gradStudent.getAdmitStatus());
            gradLogFacade.create(gradLog);

            Fraud fraud = new Fraud();
            fraud.setAdmitStno(gradStudent.getAdmitStno());
            fraud.setDate(today);
            fraud.setUsername(userid);
            fraud.setTime(time);

            fraud.setStatus("REJECTED");

            if (checkStudentNum(gradStudent.getAdmitStno())) {
                fraudFacade.edit(fraud);
            } else {
                fraudFacade.create(fraud);
            }

            sendEmail.sendEmail("210136758@edu.vut.ac.za", email.getFraudSubjectReject(), "Dear " + gradStudent.getAdmitFullnames()
                    + "\n \n " + email.getFraudBodyReject());

            FacesContext.getCurrentInstance().renderResponse();
            //  refreshexamRecievedStud();
            refreshPage();

            msg.info("Status Changed Successfully");
        }
    }

    public void btnFraudRejectStudent(GradStudent objGradStudent) {
        gradStudent = objGradStudent;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg').show();");
    }

    private boolean checkStudentNum(String number) {
        boolean value = false;
        for (Fraud fraud : fraudList) {
            if (fraud.getAdmitStno() != null) {
                if (fraud.getAdmitStno().equalsIgnoreCase(number)) {
                    value = true;
                }
            }
        }
        return value;
    }

    public void onRowCancelHOD(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        GradStudent updateGraduate = (GradStudent) event.getObject();

        msg.warn("No changes made on " + updateGraduate.getAdmitInit());
    }

    public List<StudMatric> getStudMatric() {
        List<StudMatric> objMatrics = new ArrayList<>();
        for (StudMatric matric : allMatricList) {
            if (matric.getAdmitStno().equalsIgnoreCase(gradStudent.getAdmitStno())) {
                objMatrics.add(matric);
            }
        }
        return objMatrics;
    }

    public void refreshPossibleStud() {
        possibleStud.clear();

        searchInput = "";

        List<GradStudent> tempGradStudents = new ArrayList<>();

        for (GradStudent gradStudent1 : allStudList) {
            if (gradStudent1.getAdmitStatus() != null) {
                if (gradStudent1.getAdmitStatus().equalsIgnoreCase(status.getPOSSIBLE())) {
                    tempGradStudents.add(gradStudent1);
                }
            }
        }

        for (GradStudent myGradStudent : tempGradStudents) {
            if (possibleStud.isEmpty()) {
                possibleStud.add(myGradStudent);
            } else {
                boolean value = false;

                for (GradStudent objGradStudent : possibleStud) {
                    if (objGradStudent.equals(myGradStudent)) {
                        value = true;
                    }
                }

                if (!value) {
                    possibleStud.add(myGradStudent);
                }
            }
        }
    }

    public void refreshexamRecievedStud() {

        examRecievedStud.clear();
        for (GradStudent gradStudent1 : allStudList) {

            if (gradStudent1.getAdmitStatus() != null) {
                if (gradStudent1.getAdmitStatus().equalsIgnoreCase(status.getEXAM_RECIEVE())) {
                    examRecievedStud.add(gradStudent1);
                }
            }

        }
    }

    public void show(GradStudent g) {

        matricList.clear();

        for (StudMatric studMatric : allMatricList) {
            if (studMatric.getAdmitStno().equals(g.getAdmitStno())) {
                matricList.add(studMatric);
            }
        }

        RequestContext.getCurrentInstance().execute("PF('matric').show();");
    }

    public List<StudMatric> getMatric(GradStudent g) {

        matricList.clear();

        for (StudMatric studMatric : allMatricList) {
            if (studMatric.getAdmitStno().equals(g.getAdmitStno())) {
                matricList.add(studMatric);
            }
        }
        return matricList;

    }

    public void advanceSearch() {
        examRecievedStud.clear();
        for (GradStudent gradStudent1 : allStudList) {

            if (gradStudent1.getAdmitStatus() != null) {
                if (gradStudent1.getAdmitStatus().equalsIgnoreCase(status.getEXAM_RECIEVE())
                        && gradStudent1.getAdmitQualdesc().equalsIgnoreCase(selectedQual)) {
                    examRecievedStud.add(gradStudent1);
                }
            }

        }
    }
}
