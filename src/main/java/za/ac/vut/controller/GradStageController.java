/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import za.ac.vut.entity.Gradstage;
import za.ac.vut.facade.GradstageFacade;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped
public class GradStageController {

    @EJB
    private GradstageFacade gradstageFacade;

    public int getStageNumber(String status) {
        int number = 0;

        for (Gradstage objGradstage : gradstageFacade.findAll()) {
            if (objGradstage.getStageName().equalsIgnoreCase(status)) {
                number = objGradstage.getStageNumber();
            }
        }

        return number;
    }

  
}
