package com.girmiti.nexo.acquirer.dao.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNexoTxn is a Querydsl query type for NexoTxn
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNexoTxn extends EntityPathBase<NexoTxn> {

    private static final long serialVersionUID = -1013466929L;

    public static final QNexoTxn nexoTxn = new QNexoTxn("nexoTxn");

    public final StringPath acqrrId = createString("acqrrId");

    public final StringPath captureStatus = createString("captureStatus");

    public final StringPath cgTxnRef = createString("cgTxnRef");

    public final DateTimePath<java.sql.Timestamp> creDtTm = createDateTime("creDtTm", java.sql.Timestamp.class);

    public final StringPath initgPtyId = createString("initgPtyId");

    public final StringPath mrchntId = createString("mrchntId");

    public final StringPath msgFctn = createString("msgFctn");

    public final NumberPath<Long> nexoTxnId = createNumber("nexoTxnId", Long.class);

    public final StringPath pgRrn = createString("pgRrn");

    public final StringPath pgTxnRef = createString("pgTxnRef");

    public final StringPath requestData = createString("requestData");

    public final StringPath requestType = createString("requestType");

    public final StringPath responseData = createString("responseData");

    public final StringPath txRspn = createString("txRspn");

    public final StringPath txTxRef = createString("txTxRef");

    public QNexoTxn(String variable) {
        super(NexoTxn.class, forVariable(variable));
    }

    public QNexoTxn(Path<? extends NexoTxn> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNexoTxn(PathMetadata metadata) {
        super(NexoTxn.class, metadata);
    }

}

