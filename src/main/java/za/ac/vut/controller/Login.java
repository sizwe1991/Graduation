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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import za.ac.vut.entity.*;
import za.ac.vut.facade.*;
import za.ac.vut.util.*;

/**
 *
 * @author 2015127
 */
@ManagedBean(name = "login")
@SessionScoped

public class Login {

    User objUser = new User();

    private String version;
    
    
    @EJB
    private GradStudentFacade studentFacade;

    private GradStudent gradStudent = new GradStudent();

    private final Status status = new Status();

    private String usernametxt = "Staff Number", passwordtxt = "Password:", newStatus;

    @EJB
    private UserFacade userFacade;

    @EJB
    private CampusqualificationFacade campusqualificationFacade;

    @EJB
    private UserqualificationFacade userqualificationFacade;

    private final Redirect rd = new Redirect();

    private final za.ac.vut.util.Messsage msg = new Messsage();

    private String username, password, confirm, oldPassword;

    private String studNum, IDPassNum, type = "STAFF";

    @EJB
    private StudAcademicRecordFacade studAcademicRecordFacade;

    private List<StudAcademicRecord> studentAcademicList;
    private List<GradStudent> gradStudentList;
    private List<User> userList;
    private List<Campusqualification> campusqualificationList;
    private List<Userqualification> userqualificationList;

    @EJB
    private GradLogFacade gradLogFacade;

    @PostConstruct
    public void init() {
        initializeList();
        //version = this.get
    }

    private void initializeList() {
        userqualificationList = userqualificationFacade.findAll();
        campusqualificationList = campusqualificationFacade.findAll();
        userList = userFacade.findAll();
        gradStudentList = studentFacade.findAll();
        studentAcademicList = studAcademicRecordFacade.findAll();
    }

    public Login() {
        studentAcademicList = new ArrayList<>();
        userList = new ArrayList<>();
        gradStudentList = new ArrayList<>();
        campusqualificationList = new ArrayList<>();
        userqualificationList = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    
    public void setType(String type) {
        this.type = type;
    }

    public String getUsernametxt() {
        return usernametxt;
    }

    public void setUsernametxt(String usernametxt) {
        this.usernametxt = usernametxt;
    }

    public String getPasswordtxt() {
        return passwordtxt;
    }

    public void setPasswordtxt(String passwordtxt) {
        this.passwordtxt = passwordtxt;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public GradStudent getGradStudent() {
        return gradStudent;
    }

    public String getStudNum() {
        return studNum;
    }

    public void setStudNum(String studNum) {
        this.studNum = studNum;
    }

    public List<StudAcademicRecord> getStudentAcademicList() {
        return studentAcademicList;
    }

    public void setStudentAcademicList(List<StudAcademicRecord> studentAcademicList) {
        this.studentAcademicList = studentAcademicList;
    }

    public String getIDPassNum() {
        return IDPassNum;
    }

    public void setIDPassNum(String IDPassNum) {
        this.IDPassNum = IDPassNum;
    }

    public void setGradStudent(GradStudent gradStudent) {
        this.gradStudent = gradStudent;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setObjUser(User objUser) {
        this.objUser = objUser;
    }

    public User getObjUser() {
        return objUser;
    }

    public void btnLogin() {

        FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);
        session.setAttribute("user", username);

        if (getType().equalsIgnoreCase("STAFF")) {

            if (username.isEmpty()) {
                msg.error("Please Enter Username");
            } else if (password.isEmpty()) {
                msg.error("Please Enter Password");
            } else if (!checkStaffLogin(username, password)) {
                msg.error("Invalid Username or Password, Please try again !!");
            } else if (!checkIfAuthourized(username)) {
                msg.error("You are not authourized to access the System, Please contact System Administrator !!");
            } else {

                for (User myuser : userList) {
                    if (myuser.getUsername().equalsIgnoreCase(username)) {
                        objUser = myuser;
                    }
                }

                if (objUser.getForcePassword() == 1) {
                    oldPassword = objUser.getPassword();
                    objUser.setPassword(null);
                    rd.forceChangePassword();
                } else {
                    GradLog gradLog = new GradLog();
                    Date today = new Date();
                    String time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

                    gradLog.setLogDate(today);
                    gradLog.setLogTime(time);
                    gradLog.setLogType("LOGIN");
                    gradLog.setComment("User: [" + username + "] LOGIN");
                    gradLogFacade.create(gradLog);
                    FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                            handleNavigation(FacesContext.getCurrentInstance(), null, "/dashboard/dashboard.xhtml?faces-redirect=true");
                }

            }

        } else if (getType().equalsIgnoreCase("STUDENT")) {
            setStudNum(username);
            setIDPassNum(password);

            btnSelfCheck();
        }

    }

    public void typeChange() {
        if (type != null) {
            if (getType().equalsIgnoreCase("STAFF")) {
                setUsernametxt("Staff Number ");
                setPasswordtxt("Password: ");
            } else if (getType().equalsIgnoreCase("STUDENT")) {
                setUsernametxt("Student Number ");
                setPasswordtxt("ID/Passport No: ");
            }
        }
    }

    private boolean checkStaffLogin(String user, String pwd) {
        boolean found = false;
        for (User myuser : userList) {
            if (myuser.getUsername().equalsIgnoreCase(user)
                    && myuser.getPassword().equals(pwd)) {
                found = true;
            }
        }
        return found;
    }

    private boolean checkIfAuthourized(String user) {
        boolean found = false;

        for (User myuser : userList) {
            if (myuser.getStatus().equalsIgnoreCase("Active")
                    && myuser.getUsername().equalsIgnoreCase(user)) {
                found = true;
            }
        }
        return found;
    }

    public List<Campus> getUserCampus() {
        List<Campus> campusList = new ArrayList<>();

        List<Campus> campusTempList = new ArrayList<>();

        Userqualification userqualification;

        for (Userqualification objUserqualification : userqualificationList) {
            if (objUserqualification.getUsername().equals(objUser)) {
                userqualification = objUserqualification;

                campusTempList.add(userqualification.getIdCampQual().getCampusCode());

            }
        }

        for (Campus objcampus : campusTempList) {
            if (campusList.isEmpty()) {
                campusList.add(objcampus);
            } else {
                boolean value = false;

                for (Campus mycampus : campusList) {
                    if (objcampus.equals(mycampus)) {
                        value = true;
                    }
                }

                if (!value) {
                    campusList.add(objcampus);
                }
            }
        }

        Collections.sort(campusList, new Comparator<Campus>() {
            @Override
            public int compare(Campus c1, Campus c2) {
                return c1.getDescription().compareTo(c2.getDescription());
            }
        });

        return campusList;
    }

    public List<Qualification> getQualfications(Campus campus) {
        List<Qualification> qualifications = new ArrayList<>();
        Userqualification userqualification;

        List<Qualification> tempQualifications = new ArrayList<>();

        for (Userqualification objUserqualification : userqualificationList) {
            if (objUserqualification.getUsername().equals(objUser)
                    && objUserqualification.getIdCampQual().getCampusCode().equals(campus)) {
                userqualification = objUserqualification;

                for (Campusqualification campusqualification : campusqualificationList) {
                    if (userqualification.getIdCampQual().equals(campusqualification)) {
                        tempQualifications.add(campusqualification.getQualCode());
                    }
                }
            }
        }

        for (Qualification qualification : tempQualifications) {
            if (qualifications.isEmpty()) {
                qualifications.add(qualification);
            } else {
                boolean value = false;
                for (Qualification myQualification : qualifications) {
                    if (myQualification.equals(qualification)) {
                        value = true;
                    }
                }
                if (!value) {
                    qualifications.add(qualification);
                }

            }

        }

        return qualifications;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getConfirm() {
        return confirm;
    }

    public void changePassword() {

        if (password.isEmpty()) {
            msg.error("Please Enter Password");
        } else if (getPassword().length() < 5) {
            msg.error("Password must be atleast be 5 Characters");
        } else if (oldPassword.equals(password)) {
            msg.error("Old password can not be the same as new password");
        } else if (confirm.isEmpty()) {
            msg.error("Please confirm password");
        } else if (!confirm.equals(password)) {
            msg.error("Password does not match, please try again");
        } else {
            GradLog gradLog = new GradLog();
            Date today = new Date();
            String time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("CHANGE PASSWORD");
            gradLog.setComment("User: [" + username + "] CHANGE PASSWORD");
            gradLogFacade.create(gradLog);

            objUser.setPassword(getPassword());
            objUser.setForcePassword(0);
            userFacade.edit(objUser);

            msg.info("Password has been Changed Successfully");

            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                    handleNavigation(FacesContext.getCurrentInstance(), null, "/dashboard/dashboard.xhtml?faces-redirect=true");
        }
    }

    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        gradStudent = null;

        setGradStudent(null);

        if (getObjUser() != null) {
            username = null;
            objUser = null;

        }

        if (session != null) {
            session.invalidate();

            // logger.log(Level.INFO, "User ({0}) logged out [ " + DateUtility.getCurrentDateTime() + " ] ", loggedinUser.getUsername());
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/index.xhtml?faces-redirect=true");

        }

    }

    public void btnSelfCheck() {
        boolean value = false;

        for (GradStudent objStudent : gradStudentList) {
            if (objStudent.getAdmitStno() != null && objStudent.getAdmitIdpassport() != null) {
                if (objStudent.getAdmitStno().equalsIgnoreCase(studNum)
                        && objStudent.getAdmitIdpassport().equalsIgnoreCase(IDPassNum)) {
                    gradStudent = objStudent;
                    value = true;
                }
            }

        }

        if (!value) {
            msg.error("Student details is not found on our Records, Please contact exam office for more info");
        } else {

            setGradStudent(gradStudent);
            studentAcademicList.clear();
            for (StudAcademicRecord academic : studentAcademicList) {
                if (gradStudent.getAdmitStno().equalsIgnoreCase(academic.getAdmitStno())) {
                    studentAcademicList.add(academic);
                }
            }

            rd.selfCheckPage();
        }

    }

    public String getGradStatus(String status) {

        String result = "";

        if (status.equalsIgnoreCase(this.status.getPOSSIBLE())) {
            result = "Your listed as possible graduate";
        } else if (status.equalsIgnoreCase(this.status.getEXAM_RECIEVE())) {
            result = "Examination office has received your documents, we are waiting for fraud and verification to check them";
        } else if (status.equalsIgnoreCase(this.status.getFRAUD_APPROVE())) {
            result = "Fraud and verification has approved your documents, we waiting for HOD Approval";
        } else if (status.equalsIgnoreCase(this.status.getFRAUD_REJECT())) {
            result = "Sorry we have Problems verifying you documents, please contact examination office for more detials ";
        } else if (status.equalsIgnoreCase(this.status.getHOD_APPROVE())) {
            result = "You are a confirmed graduate for the upcoming ceremony, please confirm attendance";
        } else if (status.equalsIgnoreCase(this.status.getHOD_REJECT())) {
            result = "HOD rejected you, please see them for more information";
        } else if (status.equalsIgnoreCase(this.status.getCEREMONY_ATTEND())) {
            result = "You are confirmed graduate, set has been booked for you on the of ceremony";
        } else if (status.equalsIgnoreCase(this.status.getCEREMONY_NOT_ATTEND())) {
            result = "You are confirmed graduate,but you wont be attending the ceremony";
        }

        return result;
    }

    public void btnConfirmAttendance() {
        if (gradStudent.getAdmitStatus().equalsIgnoreCase(status.getHOD_APPROVE())) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('confirmGrad').show();");
        } else if (gradStudent.getAdmitStatus().equalsIgnoreCase(status.getCEREMONY_ATTEND())
                || gradStudent.getAdmitStatus().equalsIgnoreCase(status.getCEREMONY_NOT_ATTEND())) {
            msg.error("Graduation attendance has been made already");
        } else {
            msg.error("Only confirmed Students can confirm graduation attendance");
        }

    }

    public void btnSaveAttendanceStatus() {
        if (getNewStatus() == null) {
            msg.error("Please Select Status");
        } else {
            gradStudent.setAdmitStatus(newStatus);
            studentFacade.edit(gradStudent);
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                    handleNavigation(FacesContext.getCurrentInstance(), null, "/index.xhtml?faces-redirect=true");
            msg.info("Status Changed Successfully");
        }
    }
    
 
}
