package za.ac.vut.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.ac.vut.entity.Qualification;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-11T12:35:48")
@StaticMetamodel(Faculty.class)
public class Faculty_ { 

    public static volatile SingularAttribute<Faculty, Integer> facultyId;
    public static volatile CollectionAttribute<Faculty, Qualification> qualificationCollection;
    public static volatile SingularAttribute<Faculty, String> description;
    public static volatile SingularAttribute<Faculty, String> facultyName;

}