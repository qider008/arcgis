package com.kelan.app.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonOfCity is a Querydsl query type for PersonOfCity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPersonOfCity extends EntityPathBase<PersonOfCity> {

    private static final long serialVersionUID = -251170311L;

    public static final QPersonOfCity personOfCity = new QPersonOfCity("personOfCity");

    public final NumberPath<Long> cid = createNumber("cid", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> pid = createNumber("pid", Long.class);

    public QPersonOfCity(String variable) {
        super(PersonOfCity.class, forVariable(variable));
    }

    public QPersonOfCity(Path<? extends PersonOfCity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonOfCity(PathMetadata metadata) {
        super(PersonOfCity.class, metadata);
    }

}

