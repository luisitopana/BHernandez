package com.lmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarton is a Querydsl query type for Carton
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarton extends EntityPathBase<Carton> {

    private static final long serialVersionUID = -1353776202L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarton carton = new QCarton("carton");

    public final BooleanPath estaenjuego = createBoolean("estaenjuego");

    public final NumberPath<Integer> idcarton = createNumber("idcarton", Integer.class);

    public final QPartida idpartida;

    public final QUsuario idusuario;

    public final StringPath numeros = createString("numeros");

    public final BooleanPath premiadobingo = createBoolean("premiadobingo");

    public final BooleanPath premiadolinea = createBoolean("premiadolinea");

    public final DateTimePath<java.util.Date> timestamp = createDateTime("timestamp", java.util.Date.class);

    public QCarton(String variable) {
        this(Carton.class, forVariable(variable), INITS);
    }

    public QCarton(Path<? extends Carton> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarton(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarton(PathMetadata metadata, PathInits inits) {
        this(Carton.class, metadata, inits);
    }

    public QCarton(Class<? extends Carton> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.idpartida = inits.isInitialized("idpartida") ? new QPartida(forProperty("idpartida"), inits.get("idpartida")) : null;
        this.idusuario = inits.isInitialized("idusuario") ? new QUsuario(forProperty("idusuario")) : null;
    }

}

