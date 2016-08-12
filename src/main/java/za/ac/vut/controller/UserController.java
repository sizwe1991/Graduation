/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.controller;

import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;

import za.ac.vut.entity.*;
import za.ac.vut.facade.*;
import javax.faces.bean.*;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;
import za.ac.vut.util.*;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped

public class UserController {

    private Redirect rd = new Redirect();

    private Messsage msg = new Messsage();

    private String confirm;

    private Date today = new Date();
    private String time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

    private User user;
    private String StuffInput;
    private User selectedUser, selectedHod;

    private boolean changePwd;

    @EJB
    public UserFacade userFacade;

    @EJB
    private UserqualificationFacade userqualificationFacade;

    @EJB
    private CampusqualificationFacade campusqualificationFacade;

    @EJB
    private CampusFacade campusFacade;

    @EJB
    private GradLogFacade gradLogFacade;

    private List<Campusqualification> campusqualifications, userCampusqualifications;
    private List<Userqualification> userqualifications;
    private List<User> allUsers, userList;
    private List<Qualification> linkedQual, unLinkedQual;
    private List<Campus> campusList;

    @PostConstruct
    public void init() {
        getUser();

        initailizeUsers();

    }

    public UserController() {
        unLinkedQual = new ArrayList<>();
        campusqualifications = new ArrayList<>();
        userqualifications = new ArrayList<>();
        allUsers = new ArrayList<>();
        linkedQual = new ArrayList<>();
        user = new User();
        selectedHod = new User();
        selectedUser = new User();

        userList = new ArrayList<>();
        userCampusqualifications = new ArrayList<>();
        campusList = new ArrayList<>();
    }

    public void setChangePwd(boolean changePwd) {
        this.changePwd = changePwd;
    }

    public boolean isChangePwd() {
        return changePwd;
    }

    public Messsage getMsg() {
        return msg;
    }

    public void backToManageUsers() {
        refreshUsers();
        rd.manageUsersPage();
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getConfirm() {
        return confirm;
    }

    public void refreshUsers() {
        StuffInput = "";
        userList = userFacade.findAll();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getSelectedHod() {
        return selectedHod;
    }

    public void setSelectedHod(User selectedHod) {
        this.selectedHod = selectedHod;
    }

    public void setStuffInput(String StuffInput) {
        this.StuffInput = StuffInput;
    }

    public String getStuffInput() {
        return StuffInput;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User tblUser) {
        this.user = tblUser;
    }

    public void setUserCampusqualifications(List<Campusqualification> userCampusqualifications) {
        this.userCampusqualifications = userCampusqualifications;
    }

    public List<Campusqualification> getUserCampusqualifications() {
        return userCampusqualifications;
    }

    public void initailizeUsers() {
        campusList = campusFacade.findAll();
        allUsers = userFacade.findAll();
        userList = userFacade.findAll();
        campusqualifications = campusqualificationFacade.findAll();
        userqualifications = userqualificationFacade.findAll();
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void btnSearchUser() {
        userList.clear();
        for (User objTblUser : allUsers) {
            if (objTblUser.getUsername().toLowerCase().contains(StuffInput.toLowerCase())
                    || objTblUser.getName().toLowerCase().contains(StuffInput.toLowerCase())
                    || objTblUser.getEmail().toLowerCase().contains(StuffInput.toLowerCase())) {
                userList.add(objTblUser);
            }
        }

    }

    public void btnSaveUser() {
        if (user.getName().length() < 3) {
            msg.error("Name is too short");
        } else if (user.getName().length() < 2) {
            msg.error("Surname is too short");
        } else if (user.getUsername().isEmpty()) {
            msg.error("Please Enter User Staff No ");
        } else if (user.getUsername().length() < 4) {
            msg.error("Staff Number is too short");
        } else if (checkStaffNo(user.getUsername())) {
            msg.error("User with the same staff number exists");
        } else if (user.getEmail().isEmpty()) {
            msg.error("Please Enter User Email Address ");
        } else if (!user.getEmail().endsWith("@vut.ac.za")) {
            msg.error("Please Enter a valid VUT email address");
        } else if (user.getEmail().startsWith("@vut.ac.za")) {
            msg.error("invalid email address");
        } else if (checkEmail(user.getEmail())) {
            msg.error("User with the same email address exists");
        } else if (user.getRole().equalsIgnoreCase("none")) {
            msg.error("Please Select User Type ");
        } else if (user.getPassword().isEmpty()) {
            msg.error("Please enter Password");
        } else if (user.getPassword().length() < 4) {
            msg.error("Password must be atleast be 4 characters");
        } else if (user.getPassword().length() > 20) {
            msg.error("Password must be less than 20 characters");
        } else if (!user.getPassword().equals(confirm)) {
            msg.error("Password does match");
        } else {
            //save
            int stafNo = 0;

            try {
                stafNo = Integer.parseInt(user.getUsername());
            } catch (NumberFormatException s) {
                msg.warn("Staff Number must be a number");
                return;
            }

            user.setStatus("Active");

            if (changePwd) {
                user.setForcePassword(1);
            } else {
                user.setForcePassword(0);
            }

            try {
                userFacade.create(user);

                FacesContext context2 = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

                String userid = (String) session.getAttribute("user");

                GradLog gradLog = new GradLog();

                Date today = new Date();

//                log.setUsername(userid);
//
//                log.setDate(today);
                String time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
//                log.setTime(time);
//                log.setAction("Admin: Adding New User");

                gradLog.setLogDate(today);
                gradLog.setLogTime(time);
                gradLog.setLogType("ADDING NEW USER");
                gradLog.setComment("User: [" + userid + "] ADDED NEW USER: " + user.getUsername());
                gradLogFacade.create(gradLog);
                clearFields();
                refreshUsers();
                msg.info("New User has successfully been created !!");
            } catch (Exception s) {
                msg.warn("You have an Error:  " + s.getMessage());
            }

        }

    }

    public void btnReset(User user) {
        selectedUser = user;
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/admin/updateAdmin.xhtml?faces-redirect=true");
    }

    public void btnResetPasswordOnclick() {
        if (selectedUser.getPassword().isEmpty()) {
            msg.error("Please enter password");
        } else if (selectedUser.getPassword().length() < 4) {
            msg.error("Password must be atleast be 4 characters");
        } else if (selectedUser.getPassword().length() > 20) {
            msg.error("Password must be less than 4 characters");
        } else if (!confirm.equals(selectedUser.getPassword())) {
            msg.error("Password does not match, please try again");
        } else {

            if (changePwd) {
                selectedUser.setForcePassword(1);
            } else {
                selectedUser.setForcePassword(0);
            }

            userFacade.edit(selectedUser);
            msg.info(selectedUser.getUsername() + " password has been  resetted successfully");
        }

    }

    private void clearFields() {
        user.setUsername("");
        user.setName("");
        user.setEmail("");
        user.setPassword("");
        user.setRole(null);
    }

    private boolean checkStaffNo(String staffNo) {
        boolean value = false;

        for (User objTblUser : userList) {
            if (objTblUser.getUsername().equalsIgnoreCase(staffNo)) {
                value = true;
            }
        }
        return value;
    }

    private boolean checkEmail(String email) {
        boolean value = false;

        for (User objTblUser : userList) {
            if (objTblUser.getEmail().equalsIgnoreCase(email)) {
                value = true;
            }
        }
        return value;
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        User cancelledUser = (User) event.getObject();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Changes cancelled", cancelledUser.getName() + " ( " + cancelledUser.getUsername() + " )"));

    }

    public void onRowEdit(RowEditEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        User updateUser = (User) event.getObject();

        if (updateUser.getName().isEmpty()) {
            msg.error("Please enter user name");
        } else if (updateUser.getName().length() < 2) {
            msg.error("Name is too short");
        } else if (updateUser.getUsername().isEmpty()) {
            msg.error("Please enter username");
        } else if (updateUser.getUsername().length() < 3) {
            msg.error("Username is too short");
        } else if (updateUser.getRole().equalsIgnoreCase("none")) {
            msg.error("Please select user type");
        } else if (updateUser.getEmail().isEmpty()) {
            msg.error("Please Enter User Email Address ");
        } else if (!updateUser.getEmail().contains("@vut.ac.za")) {
            msg.error("Plase Enter a valid VUT email address");
        } else {

            try {
                userFacade.edit(updateUser);

                FacesContext context2 = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

                String userid = (String) session.getAttribute("user");

                GradLog gradLog = new GradLog();

                Date today = new Date();

//                log.setUsername(userid);
//               
//                log.setDate(today);
                String time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
//                log.setTime(time);
//                log.setAction("Admin: Update User");

                gradLog.setLogDate(today);
                gradLog.setLogTime(time);
                gradLog.setLogType("UPDATE USER");
                gradLog.setComment("User: [" + userid + "] UPDATE USER: " + updateUser.getUsername());
                gradLogFacade.create(gradLog);

                msg.info("User Information Updated Successfully");

            } catch (Exception e) {
                msg.warn("You have an Error:  " + e.getMessage());
            }

        }
    }

    public void btnLinkUser(User objUser) {
        selectedHod = objUser;

        populateUserCampusQual();
        rd.linkUserPage();

    }

    public String getBtnLinkText(Qualification qualCode, Campus campusCode) {
        String text = "Link";

        Campusqualification campusqualification = null;

        for (Campusqualification objCampusqualification : campusqualifications) {
            if (objCampusqualification.getCampusCode().getShortCode().charAt(0) == campusCode.getShortCode().charAt(0)
                    && objCampusqualification.getQualCode().equals(qualCode)) {
                campusqualification = objCampusqualification;
            }
        }

        for (Userqualification userqualification : userqualifications) {
            if (userqualification.getIdCampQual().equals(campusqualification)
                    && userqualification.getUsername().equals(selectedHod)) {
                text = "Unlink";
            }

        }

        return text;
    }

    private boolean checkQual(Qualification qualCode, Campus campusCode) {
        boolean value = false;
        for (Campusqualification objCampusqualification : campusqualifications) {
            if (objCampusqualification.getCampusCode().equals(campusCode)
                    & objCampusqualification.getQualCode().equals(qualCode)) {
                value = true;
            }
        }
        return value;
    }

    public void btnLinkHOD(Qualification qualCode, Campus campusCode) {

        String action = getBtnLinkText(qualCode, campusCode);

        Campusqualification campusqualification = null;

        for (Campusqualification objCampusqualification : campusqualifications) {
            if (objCampusqualification.getCampusCode().getShortCode().charAt(0) == campusCode.getShortCode().charAt(0)
                    && objCampusqualification.getQualCode().equals(qualCode)) {
                campusqualification = objCampusqualification;
            }
        }

        Userqualification userqualification = new Userqualification();

        userqualification.setUsername(selectedHod);
        userqualification.setIdCampQual(campusqualification);

        if (action.equalsIgnoreCase("Link")) {

            userqualificationFacade.create(userqualification);
            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            GradLog gradLog = new GradLog();

            Date today = new Date();

//            log.setUsername(userid);
//            log.setDate(today);
            String time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
//            log.setTime(time);
//            log.setAction("Admin: Link HOD to Qualification");

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("HOD LINK");
            gradLog.setComment("User: [" + userid + "] LINKED : " + user.getUsername() + " TO " + campusqualification.getQualCode().getQualName());
            gradLogFacade.create(gradLog);

            msg.info("User Linked successfully to " + campusqualification.getQualCode().getQualName());

        } else if (action.equalsIgnoreCase("Unlink")) { //Unlink
            userqualificationFacade.remove(userqualification);
            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            GradLog gradLog = new GradLog();

            Date today = new Date();

//            log.setUsername(userid);
//            log.setDate(today);
            String time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
//            log.setTime(time);
//            log.setAction("Admin: Link HOD to Qualification");

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("HOD UNLINK");
            gradLog.setComment("User: [" + userid + "] UNLINK : " + user.getUsername() + " TO " + campusqualification.getQualCode().getQualName());
            gradLogFacade.create(gradLog);

            msg.info("User UnLinked successfully to " + campusqualification.getQualCode().getQualName());
        }
    }

    public void onTabChange(TabChangeEvent event) {
        populateLinkedQual(event.getTab().getTitle(), selectedHod);
    }

    public void onTabChangeUnlink(TabChangeEvent event) {
        populateUnlinkedQual(event.getTab().getTitle(), selectedHod);
    }

    public void populateUserCampusQual() {
        userCampusqualifications.clear();

        List<Campusqualification> tempQual = new ArrayList<>();

        userqualifications.clear();
        userqualifications = userqualificationFacade.findAll();

        for (Userqualification userqualification : userqualifications) {
            for (Campus campus : campusList) {
                if (userqualification.getUsername().equals(selectedHod)
                        && userqualification.getIdCampQual().getCampusCode().equals(campus)) {
                    tempQual.add(userqualification.getIdCampQual());
                }
            }

        }

        for (Campusqualification campusQualification : tempQual) {
            if (userCampusqualifications.isEmpty()) {
                userCampusqualifications.add(campusQualification);
            } else {
                boolean value = false;

                for (Campusqualification myCampusQualification : userCampusqualifications) {
                    if (myCampusQualification.equals(userCampusqualifications)) {
                        value = true;
                    }
                }

                if (!value) {
                    userCampusqualifications.add(campusQualification);
                }
            }
        }

        Collections.sort(userCampusqualifications, new Comparator<Campusqualification>() {
            @Override
            public int compare(Campusqualification c1, Campusqualification c2) {
                return c1.getCampusCode().getShortCode().compareTo(c2.getCampusCode().getShortCode());
            }
        });

        Collections.sort(userCampusqualifications, new Comparator<Campusqualification>() {
            @Override
            public int compare(Campusqualification c1, Campusqualification c2) {
                return c1.getQualCode().getFacultyId().getDescription().compareTo(c2.getQualCode().getFacultyId().getDescription());
            }
        });

        Collections.sort(userCampusqualifications, new Comparator<Campusqualification>() {
            @Override
            public int compare(Campusqualification c1, Campusqualification c2) {
                return c1.getQualCode().getQualLevel().compareTo(c2.getQualCode().getQualLevel());
            }
        });
    }

    public void populateLinkedQual(String objCampus, User user) {
        linkedQual.clear();

        Campus campus = null;

        for (Campus c : campusFacade.findAll()) {
            if (c.getDescription().equalsIgnoreCase(objCampus)) {
                campus = c;
            }
        }

        List<Qualification> tempQual = new ArrayList<>();

        
         userqualifications.clear();
        userqualifications = userqualificationFacade.findAll();
        
        for (Userqualification userqualification : userqualifications) {
            if (userqualification.getUsername().equals(user)
                    && userqualification.getIdCampQual().getCampusCode().equals(campus)) {
                tempQual.add(userqualification.getIdCampQual().getQualCode());
            }
        }

        for (Qualification qual : tempQual) {
            if (linkedQual.isEmpty()) {
                linkedQual.add(qual);
            } else {
                boolean value = false;

                for (Qualification myQual : linkedQual) {
                    if (myQual.equals(qual)) {
                        value = true;
                    }
                }

                if (!value) {
                    linkedQual.add(qual);
                }
            }
        }

        Collections.sort(linkedQual, new Comparator<Qualification>() {
            @Override
            public int compare(Qualification c1, Qualification c2) {
                return c1.getQualLevel().compareTo(c2.getQualLevel());
            }
        });

    }

    public void populateUnlinkedQual(String objCampus, User user) {
        unLinkedQual.clear();

        Campus campus = null;

        for (Campus c : campusFacade.findAll()) {
            if (c.getDescription().equalsIgnoreCase(objCampus)) {
                campus = c;
            }
        }

        List<Qualification> tempQual = new ArrayList<>();
        
        campusqualifications.clear();
        campusqualifications=campusqualificationFacade.findAll();

        for (Campusqualification campusqualification : campusqualifications) {
            if (campusqualification.getCampusCode().equals(campus)) {
                tempQual.add(campusqualification.getQualCode());
            }
        }

        for (Qualification qual : tempQual) {
            if (unLinkedQual.isEmpty()) {
                unLinkedQual.add(qual);
            } else {
                boolean value = false;
                for (Qualification muQual : unLinkedQual) {
                    if (muQual.equals(qual)) {
                        value = true;
                    }
                }

                if (!value) {
                    unLinkedQual.add(qual);
                }
            }
        }

         userqualifications.clear();
        userqualifications = userqualificationFacade.findAll();
        
        for (Userqualification userqualification : userqualifications) {
            if (userqualification.getUsername().equals(user)
                    && userqualification.getIdCampQual().getCampusCode().equals(campus)) {

                unLinkedQual.remove(userqualification.getIdCampQual().getQualCode());
            }
        }

        Collections.sort(unLinkedQual, new Comparator<Qualification>() {
            @Override
            public int compare(Qualification c1, Qualification c2) {
                return c1.getQualLevel().compareTo(c2.getQualLevel());
            }
        });

        Collections.sort(unLinkedQual, new Comparator<Qualification>() {
            @Override
            public int compare(Qualification c1, Qualification c2) {
                return c1.getFacultyId().getFacultyName().compareTo(c2.getFacultyId().getFacultyName());
            }
        });

    }

    public List<Qualification> getLinkedQual() {
        return linkedQual;
    }

    public void setLinkedQual(List<Qualification> linkedQual) {
        this.linkedQual = linkedQual;
    }

    public List<Qualification> getUnLinkedQual() {
        return unLinkedQual;
    }

    public void setUnLinkedQual(List<Qualification> unLinkedQual) {
        this.unLinkedQual = unLinkedQual;
    }

    public void LinkHODToQual(Qualification qual, Campus campus, User user) {

        Campusqualification campusqualification = null;

        for (Campusqualification objCampusqualification : campusqualifications) {
            if (objCampusqualification.getCampusCode().equals(campus)
                    && objCampusqualification.getQualCode().equals(qual)) {
                campusqualification = objCampusqualification;
            }
        }

        if (campusqualification != null) {
            Userqualification userqualification = new Userqualification();
            userqualification.setUsername(user);
            userqualification.setIdCampQual(campusqualification);
            userqualificationFacade.create(userqualification);

            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            GradLog gradLog = new GradLog();

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("LOGIN");
            gradLog.setComment("User: [" + userid + "] LINKED [" + user.getUsername() + "] TO QUALIFICATION:" + qual.getQualName() + " AT " + campus.getDescription());
            gradLogFacade.create(gradLog);

            initailizeUsers();

            populateUserCampusQual();

            refreshPage();

            msg.info("User linked to qualification to " + qual.getQualName() + " at " + campus.getDescription());

        } else {
            msg.error("Sorry we couldn't find the qualification in the campus");
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

    public void refreshQualifications() {

        StuffInput = "";

        populateUserCampusQual();

        refreshPage();
    }

    public void btnSearchQualification() {
        List<Campusqualification> tempCampusqualifications = new ArrayList<>();

        userCampusqualifications.clear();

        //////////////////////////pop
        List<Campusqualification> tempQual = new ArrayList<>();

        for (Userqualification userqualification : userqualifications) {
            for (Campus campus : campusList) {
                if (userqualification.getUsername().equals(selectedHod)
                        && userqualification.getIdCampQual().getCampusCode().equals(campus)) {
                    tempQual.add(userqualification.getIdCampQual());
                }
            }

        }

        for (Campusqualification campusQualification : tempQual) {
            if (tempCampusqualifications.isEmpty()) {
                tempCampusqualifications.add(campusQualification);
            } else {
                boolean value = false;

                for (Campusqualification myCampusQualification : tempCampusqualifications) {
                    if (myCampusQualification.equals(tempCampusqualifications)) {
                        value = true;
                    }
                }

                if (!value) {
                    tempCampusqualifications.add(campusQualification);
                }
            }
        }
        //////////////////////////done

        for (Campusqualification campusqualification : tempCampusqualifications) {
            if (campusqualification.getCampusCode().getDescription().toLowerCase().contains(StuffInput.toLowerCase())
                    || campusqualification.getCampusCode().getShortCode().toLowerCase().contains(StuffInput.toLowerCase())
                    || campusqualification.getQualCode().getFacultyId().getFacultyName().toLowerCase().contains(StuffInput.toLowerCase())
                    || campusqualification.getQualCode().getQualName().toLowerCase().contains(StuffInput.toLowerCase())
                    || campusqualification.getQualCode().getQualCode().toLowerCase().contains(StuffInput.toLowerCase())
                    || campusqualification.getQualCode().getQualLevel().toLowerCase().contains(StuffInput.toLowerCase())) {
                userCampusqualifications.add(campusqualification);
            }
        }
    }

    public void UnLinkHODToQual(Qualification qual, Campus campus, User user) {

        Campusqualification campusqualification = null;

        for (Campusqualification objCampusqualification : campusqualifications) {
            if (objCampusqualification.getCampusCode().equals(campus)
                    && objCampusqualification.getQualCode().equals(qual)) {
                campusqualification = objCampusqualification;
            }
        }

        if (campusqualification != null) {
            Userqualification userqualification = null;

            for (Userqualification objUserqualification : userqualifications) {
                if (objUserqualification.getIdCampQual().equals(campusqualification)
                        && objUserqualification.getUsername().equals(user)) {
                    userqualification = objUserqualification;
                }
            }

            userqualificationFacade.remove(userqualification);

            FacesContext context2 = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context2.getExternalContext().getSession(true);

            String userid = (String) session.getAttribute("user");

            GradLog gradLog = new GradLog();

            gradLog.setLogDate(today);
            gradLog.setLogTime(time);
            gradLog.setLogType("LOGIN");
            gradLog.setComment("User: [" + userid + "] UNLINK [" + user.getUsername() + "] TO QUALIFICATION:" + qual.getQualName() + " AT " + campus.getDescription());
            gradLogFacade.create(gradLog);

            initailizeUsers();

            populateUserCampusQual();

            refreshPage();

            msg.info("User unlinked to qualification to " + qual.getQualName() + " at " + campus.getDescription());

        } else {
            msg.error("Sorry we couldn't find the qualification in the campus");
        }

    }

}
