/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.controller;

import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author 2015127
 */
@Stateless
@ManagedBean
public class Redirect {

    private final String ADMIN = "ADMIN";
    private final String HOD = "HOD";
    private final String EXAM = "EXAM";
    private final String FRAUD = "Fraud";

    public void addUserPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/admin/addUser.xhtml?faces-redirect=true");
    }

  

    public void manageQualificationPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/admin/manageQual.xhtml?faces-redirect=true");
    }

    public void manageUsersPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/admin/manageUser.xhtml?faces-redirect=true");
    }
    
      public void emailConfiguration() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/admin/emailconfiguration.xhtml?faces-redirect=true");
    }
    
    
    public void dashBoardPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/dashboard/dashboard.xhtml?faces-redirect=true");
    }
    
         public void systemReportPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/dashboard/reports.xhtml?faces-redirect=true");
    }    
    public void linkUserPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/admin/hodLink.xhtml?faces-redirect=true");
    }

   


    public void viewStudentPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/hod/viewStudents.xhtml?faces-redirect=true");
    }

        public void hodRevertChnagesPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/hod/changeStatus.xhtml?faces-redirect=true");
    }
    public void viewFraudPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/fraud/fraud.xhtml?faces-redirect=true");
    }
    
      public void fraudRevertChanges() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/fraud/changeStatus.xhtml?faces-redirect=true");
    }

    public void viewExamPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/exam/exams.xhtml?faces-redirect=true");
    }
    
     public void examChnageStatusPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/exam/changeStatus.xhtml?faces-redirect=true");
    }

    public void viewAcademicRecord() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/hod/studentRecord.xhtml?faces-redirect=true");
    }

    public void studentNotFoundPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/studentnotfound/studentNotFound.xhtml?faces-redirect=true");
    }

    public void selfCheckPage() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/student/graduation_status.xhtml?faces-redirect=true");
    }
    
      public void forceChangePassword() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/changePassword/changePwd.xhtml?faces-redirect=true");
    }
    
       public void examOfficeRecieve() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/exam/receiveDocuments.xhtml?faces-redirect=true");
    }
       
            public void fraudRecieve() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(), null, "/fraud/approveStudent.xhtml?faces-redirect=true");
    }

            


    public String getADMIN() {
        return ADMIN;
    }

    public String getHOD() {
        return HOD;
    }

    public String getEXAM() {
        return EXAM;
    }

    public String getFRAUD() {
        return FRAUD;
    }

}
