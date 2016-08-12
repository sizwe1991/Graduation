package za.ac.vut.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import za.ac.vut.entity.Campusqualification;
import za.ac.vut.entity.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-11T12:35:48")
@StaticMetamodel(Userqualification.class)
public class Userqualification_ { 

    public static volatile SingularAttribute<Userqualification, Integer> qualUserId;
    public static volatile SingularAttribute<Userqualification, Campusqualification> idCampQual;
    public static volatile SingularAttribute<Userqualification, User> username;

}