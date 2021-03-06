/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.vut.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import za.ac.vut.entity.Hod;

/**
 *
 * @author 2015127
 */
@Stateless
public class HodFacade extends AbstractFacade<Hod> {
    @PersistenceContext(unitName = "za.ac.vut_Graduation_war_1.4PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HodFacade() {
        super(Hod.class);
    }
    
}
