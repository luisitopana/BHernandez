package com.lmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBolaextraida is a Querydsl query type for Bolaextraida
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBolaextraida extends EntityPathBase<Bolaextraida> {

    private static final long serialVersionUID = 923467407L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBolaextraida bolaextraida = new QBolaextraida("bolaextraida");

    public final NumberPath<Integer> idbolaextraida = createNumber("idbolaextraida", Integer.class);

    public final QPartida idpartida;

    public final NumberPath<Integer> numerobola = createNumber("numerobola", Integer.class);

    public final NumberPath<Integer> orden = createNumber("orden", Integer.class);

    public final DateTimePath<java.util.Date> timestamp = createDateTime("timestamp", java.util.Date.class);

    public QBolaextraida(String variable) {
        this(Bolaextraida.class, forVariable(variable), INITS);
    }

    public QBolaextraida(Path<? extends Bolaextraida> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBolaextraida(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBolaextraida(PathMetadata metadata, PathInits inits) {
        this(Bolaextraida.class, metadata, inits);
    }

    public QBolaextraida(Class<? extends Bolaextraida> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.idpartida = inits.isInitialized("idpartida") ? new QPartida(forProperty("idpartida"), inits.get("idpartida")) : null;
    }

}

