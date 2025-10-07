package com.lmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsuario is a Querydsl query type for Usuario
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsuario extends EntityPathBase<Usuario> {

    private static final long serialVersionUID = 295339863L;

    public static final QUsuario usuario = new QUsuario("usuario");

    public final DateTimePath<java.util.Date> fechaultimoacceso = createDateTime("fechaultimoacceso", java.util.Date.class);

    public final NumberPath<Integer> idusuario = createNumber("idusuario", Integer.class);

    public final StringPath nombreusuario = createString("nombreusuario");

    public final StringPath password = createString("password");

    public final NumberPath<java.math.BigDecimal> saldo = createNumber("saldo", java.math.BigDecimal.class);

    public final StringPath salt = createString("salt");

    public final DateTimePath<java.util.Date> timestamp = createDateTime("timestamp", java.util.Date.class);

    public QUsuario(String variable) {
        super(Usuario.class, forVariable(variable));
    }

    public QUsuario(Path<? extends Usuario> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsuario(PathMetadata metadata) {
        super(Usuario.class, metadata);
    }

}

