package com.kelan.app.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-07-18T18:21:30.081+0800")
@StaticMetamodel(Permission.class)
public class Permission_ {
	public static volatile SingularAttribute<Permission, Long> id;
	public static volatile SingularAttribute<Permission, Resource> resource;
	public static volatile SetAttribute<Permission, Operation> operations;
	public static volatile SingularAttribute<Permission, String> name;
	public static volatile SingularAttribute<Permission, String> description;
}
