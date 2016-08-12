package za.ac.vut.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.ac.vut.entity.Campusqualification;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-11T12:35:48")
@StaticMetamodel(Campus.class)
public class Campus_ { 

    public static volatile SingularAttribute<Campus, String> campusCode;
    public static volatile SingularAttribute<Campus, String> description;
    public static volatile SingularAttribute<Campus, String> shortCode;
    public static volatile CollectionAttribute<Campus, Campusqualification> campusqualificationCollection;

}