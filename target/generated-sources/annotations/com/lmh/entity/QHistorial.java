package com.lmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHistorial is a Querydsl query type for Historial
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHistorial extends EntityPathBase<Historial> {

    private static final long serialVersionUID = 894045592L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHistorial historial = new QHistorial("historial");

    public final NumberPath<Integer> accion = createNumber("accion", Integer.class);

    public final NumberPath<java.math.BigDecimal> cantidadactual = createNumber("cantidadactual", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> cantidadmodificar = createNumber("cantidadmodificar", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> cantidadrestante = createNumber("cantidadrestante", java.math.BigDecimal.class);

    public final NumberPath<Integer> idhistorial = createNumber("idhistorial", Integer.class);

    public final QUsuario idusuario;

    public final DateTimePath<java.util.Date> timestamp = createDateTime("timestamp", java.util.Date.class);

    public final StringPath tipomovimiento = createString("tipomovimiento");

    public QHistorial(String variable) {
        this(Historial.class, forVariable(variable), INITS);
    }

    public QHistorial(Path<? extends Historial> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHistorial(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHistorial(PathMetadata metadata, PathInits inits) {
        this(Historial.class, metadata, inits);
    }

    public QHistorial(Class<? extends Historial> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.idusuario = inits.isInitialized("idusuario") ? new QUsuario(forProperty("idusuario")) : null;
    }

}

