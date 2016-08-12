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
import za.ac.vut.entity.Campus;
import za.ac.vut.facade.CampusFacade;

/**
 *
 * @author 2015127
 */
@ManagedBean
@SessionScoped
public class CampusController {

  

    @EJB
    private CampusFacade tblCampusFacade;

    private List<Campus> campusList = new ArrayList<>();

    @PostConstruct
    public void init() {
        popCampus();
    }

    public CampusController() {

    }

    public void setCampusList(List<Campus> campusList) {
        this.campusList = campusList;
    }

    public List<Campus> getCampusList() {
        return campusList;
    }

    public void popCampus() {
        campusList.clear();
        campusList=tblCampusFacade.findAll();
        
    }
}
