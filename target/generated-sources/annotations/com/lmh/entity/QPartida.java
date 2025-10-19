package com.lmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPartida is a Querydsl query type for Partida
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPartida extends EntityPathBase<Partida> {

    private static final long serialVersionUID = -364749316L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPartida partida = new QPartida("partida");

    public final NumberPath<Integer> bolamaxbote = createNumber("bolamaxbote", Integer.class);

    public final EnumPath<com.lmh.manager.PartidaState> estado = createEnum("estado", com.lmh.manager.PartidaState.class);

    public final DateTimePath<java.util.Date> fechafin = createDateTime("fechafin", java.util.Date.class);

    public final DateTimePath<java.util.Date> fechainicio = createDateTime("fechainicio", java.util.Date.class);

    public final NumberPath<Integer> idpartida = createNumber("idpartida", Integer.class);

    public final QSala idsala;

    public final NumberPath<Integer> premiobingo = createNumber("premiobingo", Integer.class);

    public final NumberPath<java.math.BigDecimal> premiobote = createNumber("premiobote", java.math.BigDecimal.class);

    public final NumberPath<Integer> premiolinea = createNumber("premiolinea", Integer.class);

    public final BooleanPath saliobote = createBoolean("saliobote");

    public final DateTimePath<java.util.Date> timestamp = createDateTime("timestamp", java.util.Date.class);

    public QPartida(String variable) {
        this(Partida.class, forVariable(variable), INITS);
    }

    public QPartida(Path<? extends Partida> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPartida(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPartida(PathMetadata metadata, PathInits inits) {
        this(Partida.class, metadata, inits);
    }

    public QPartida(Class<? extends Partida> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.idsala = inits.isInitialized("idsala") ? new QSala(forProperty("idsala")) : null;
    }

}

