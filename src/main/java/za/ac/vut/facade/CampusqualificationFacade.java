/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import za.ac.vut.entity.Campusqualification;

/**
 *
 * @author 2015127
 */
@Stateless
public class CampusqualificationFacade extends AbstractFacade<Campusqualification> {
    @PersistenceContext(unitName = "za.ac.vut_Graduation_war_1.4PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CampusqualificationFacade() {
        super(Campusqualification.class);
    }
    
}