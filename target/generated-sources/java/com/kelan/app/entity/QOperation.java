package com.kelan.app.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QOperation is a Querydsl query type for Operation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOperation extends EntityPathBase<Operation> {

    private static final long serialVersionUID = 232987205L;

    public static final QOperation operation1 = new QOperation("operation1");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath operation = createString("operation");

    public QOperation(String variable) {
        super(Operation.class, forVariable(variable));
    }

    public QOperation(Path<? extends Operation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOperation(PathMetadata metadata) {
        super(Operation.class, metadata);
    }

}

