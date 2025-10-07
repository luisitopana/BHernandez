package com.lmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHistorial is a Querydsl query type for Historial
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHistorial extends EntityPathBase<Historial> {

    private static final long serialVersionUID = 894045592L;

    public static final QHistorial historial = new QHistorial("historial");

    public final NumberPath<Integer> accion = createNumber("accion", Integer.class);

    public final NumberPath<java.math.BigDecimal> cantidadactual = createNumber("cantidadactual", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> cantidadmodificar = createNumber("cantidadmodificar", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> cantidadrestante = createNumber("cantidadrestante", java.math.BigDecimal.class);

    public final NumberPath<Integer> idhistorial = createNumber("idhistorial", Integer.class);

    public final DateTimePath<java.util.Date> timestamp = createDateTime("timestamp", java.util.Date.class);

    public final StringPath tipomovimiento = createString("tipomovimiento");

    public QHistorial(String variable) {
        super(Historial.class, forVariable(variable));
    }

    public QHistorial(Path<? extends Historial> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHistorial(PathMetadata metadata) {
        super(Historial.class, metadata);
    }

}

