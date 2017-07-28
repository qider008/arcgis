package com.kelan.app.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLog is a Querydsl query type for Log
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLog extends EntityPathBase<Log> {

    private static final long serialVersionUID = 1679713954L;

    public static final QLog log = new QLog("log");

    public final StringPath createBy = createString("createBy");

    public final DateTimePath<java.util.Date> createDate = createDateTime("createDate", java.util.Date.class);

    public final StringPath exception = createString("exception");

    public final StringPath id = createString("id");

    public final StringPath method = createString("method");

    public final StringPath params = createString("params");

    public final StringPath remoteAddr = createString("remoteAddr");

    public final StringPath requestUri = createString("requestUri");

    public final StringPath title = createString("title");

    public final StringPath type = createString("type");

    public final StringPath userAgent = createString("userAgent");

    public QLog(String variable) {
        super(Log.class, forVariable(variable));
    }

    public QLog(Path<? extends Log> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLog(PathMetadata metadata) {
        super(Log.class, metadata);
    }

}

