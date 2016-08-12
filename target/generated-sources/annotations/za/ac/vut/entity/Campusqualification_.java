package za.ac.vut.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.ac.vut.entity.Campus;
import za.ac.vut.entity.GradStudent;
import za.ac.vut.entity.Qualification;
import za.ac.vut.entity.Userqualification;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-11T12:35:48")
@StaticMetamodel(Campusqualification.class)
public class Campusqualification_ { 

    public static volatile SingularAttribute<Campusqualification, String> offeringType;
    public static volatile SingularAttribute<Campusqualification, Campus> campusCode;
    public static volatile CollectionAttribute<Campusqualification, GradStudent> gradStudentCollection;
    public static volatile CollectionAttribute<Campusqualification, Userqualification> userqualificationCollection;
    public static volatile SingularAttribute<Campusqualification, Qualification> qualCode;
    public static volatile SingularAttribute<Campusqualification, Integer> idCampQual;

}