package com.kelan.app.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-18T18:21:30.096+0800")
@StaticMetamodel(Role.class)
public class Role_ {
	public static volatile SingularAttribute<Role, Long> id;
	public static volatile SetAttribute<Role, Permission> permissions;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile SingularAttribute<Role, String> role;
	public static volatile SingularAttribute<Role, String> description;
}
