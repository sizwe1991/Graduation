package za.ac.vut.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.ac.vut.entity.Campusqualification;
import za.ac.vut.entity.Faculty;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-11T12:35:48")
@StaticMetamodel(Qualification.class)
public class Qualification_ { 

    public static volatile SingularAttribute<Qualification, String> qualName;
    public static volatile SingularAttribute<Qualification, String> qualLevel;
    public static volatile SingularAttribute<Qualification, Faculty> facultyId;
    public static volatile SingularAttribute<Qualification, Long> qualCredit;
    public static volatile SingularAttribute<Qualification, String> qualCode;
    public static volatile CollectionAttribute<Qualification, Campusqualification> campusqualificationCollection;

}