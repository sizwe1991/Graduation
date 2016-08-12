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
import za.ac.vut.entity.Faculty;
import za.ac.vut.facade.FacultyFacade;

/**
 *
 * @author 2015127
 */
@SessionScoped
@ManagedBean
public class FacultyController {

    private Faculty tblFaculty;

    @EJB
    private FacultyFacade facultyFacade;

    private List<Faculty> facultyList;

    @PostConstruct
    public void init() {
        populateFaculties();
    }

    public FacultyController() {
        facultyList = new ArrayList<>();
    }

    public List<Faculty> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(List<Faculty> facultyList) {
        this.facultyList = facultyList;
    }

    private void populateFaculties() {
        facultyList = facultyFacade.findAll();
    }

}
