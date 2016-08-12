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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import za.ac.vut.entity.EmailConfiguration;
import za.ac.vut.entity.GradStudent;
import za.ac.vut.facade.EmailConfigurationFacade;
import za.ac.vut.facade.GradStudentFacade;
import za.ac.vut.util.Messsage;
import za.ac.vut.util.SendEmail;
import za.ac.vut.util.Status;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped

public class EmailConfigController {

    private EmailConfiguration emailConfiguration;

    private Messsage msg = new Messsage();

    private String subject, body;

    @EJB
    private GradStudentFacade gradStudentFacade;

    private List<GradStudent> gradStudentList;

    @EJB
    private EmailConfigurationFacade emailConfigurationFacade;

    @PostConstruct
    public void init() {

        gradStudentList = gradStudentFacade.findAll();

        for (EmailConfiguration email : emailConfigurationFacade.findAll()) {
            emailConfiguration = email;
        }

    }

    public EmailConfigController() {
        gradStudentList = new ArrayList<>();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public EmailConfiguration getEmailConfiguration() {
        return emailConfiguration;
    }

    public void setEmailConfiguration(EmailConfiguration emailConfiguration) {
        this.emailConfiguration = emailConfiguration;
    }

    public void btnChangeMsg(String name) {
        emailConfigurationFacade.edit(emailConfiguration);
        msg.info("Thank you changes Saved:" + name);

    }

    public void btnSendPossibleEmail() {
        if (subject.isEmpty()) {
            msg.error("Please Enter Email Subject !!");
        } else if (body.isEmpty()) {
            msg.error("Please Enter Email Message !!");
        } else {

            SendEmail sendEmail = new SendEmail();

            Status status = new Status();

            for (GradStudent gradStudent : gradStudentList) {
                if (gradStudent.getAdmitStatus() != null) {
                    if (gradStudent.getAdmitStatus().equalsIgnoreCase(status.getPOSSIBLE())) {
                        sendEmail.sendEmail(gradStudent.getAdmitStno() + "@edu.vut.ac.za", subject, body);
                    }
                }
            }

            msg.info("Success, Email Sent to students !!");
        }

    }

}
