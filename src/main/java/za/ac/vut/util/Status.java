/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.util;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author 2015127
 */

@ManagedBean    
@SessionScoped
public class Status {
    
    private final String EXAM_RECIEVE = "EXAM:Received";
    private final String HOD_APPROVE = "HOD:Approved";
    private final String HOD_REJECT = "HOD:Rejected";
    private final String POSSIBLE = "POSSIBLE";
    private final String FRAUD_APPROVE = "Verification:Approved";
    private final String FRAUD_REJECT = "Verification:Rejected";
    private final String CEREMONY_ATTEND = "Ceremony:Yes";
    private final String CEREMONY_NOT_ATTEND = "Ceremony:No";

    public String getHOD_APPROVE() {
        return HOD_APPROVE;
    }

    public String getHOD_REJECT() {
        return HOD_REJECT;
    }

    public String getPOSSIBLE() {
        return POSSIBLE;
    }

    public String getFRAUD_APPROVE() {
        return FRAUD_APPROVE;
    }

    public String getFRAUD_REJECT() {
        return FRAUD_REJECT;
    }

    public String getCEREMONY_ATTEND() {
        return CEREMONY_ATTEND;
    }

    public String getCEREMONY_NOT_ATTEND() {
        return CEREMONY_NOT_ATTEND;
    }

    public String getEXAM_RECIEVE() {
        return EXAM_RECIEVE;
    }

    
   
}
