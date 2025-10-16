package com.lmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSala is a Querydsl query type for Sala
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSala extends EntityPathBase<Sala> {

    private static final long serialVersionUID = 1232585914L;

    public static final QSala sala = new QSala("sala");

    public final NumberPath<Integer> bolamaxbote = createNumber("bolamaxbote", Integer.class);

    public final StringPath codigo = createString("codigo");

    public final NumberPath<Integer> idsala = createNumber("idsala", Integer.class);

    public final StringPath nombre = createString("nombre");

    public final NumberPath<Integer> porcentajebingo = createNumber("porcentajebingo", Integer.class);

    public final NumberPath<Integer> porcentajebote = createNumber("porcentajebote", Integer.class);

    public final NumberPath<Integer> porcentajelinea = createNumber("porcentajelinea", Integer.class);

    public final NumberPath<Integer> precio = createNumber("precio", Integer.class);

    public final NumberPath<java.math.BigDecimal> premiobote = createNumber("premiobote", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> timestamp = createDateTime("timestamp", java.util.Date.class);

    public QSala(String variable) {
        super(Sala.class, forVariable(variable));
    }

    public QSala(Path<? extends Sala> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSala(PathMetadata metadata) {
        super(Sala.class, metadata);
    }

}

