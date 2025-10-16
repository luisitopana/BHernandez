package com.lmh.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPatroncarton is a Querydsl query type for Patroncarton
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPatroncarton extends EntityPathBase<Patroncarton> {

    private static final long serialVersionUID = -654668220L;

    public static final QPatroncarton patroncarton = new QPatroncarton("patroncarton");

    public final NumberPath<Integer> idpatroncarton = createNumber("idpatroncarton", Integer.class);

    public final StringPath n1 = createString("n1");

    public final StringPath n10 = createString("n10");

    public final StringPath n11 = createString("n11");

    public final StringPath n12 = createString("n12");

    public final StringPath n13 = createString("n13");

    public final StringPath n14 = createString("n14");

    public final StringPath n15 = createString("n15");

    public final StringPath n16 = createString("n16");

    public final StringPath n17 = createString("n17");

    public final StringPath n18 = createString("n18");

    public final StringPath n19 = createString("n19");

    public final StringPath n2 = createString("n2");

    public final StringPath n20 = createString("n20");

    public final StringPath n21 = createString("n21");

    public final StringPath n22 = createString("n22");

    public final StringPath n23 = createString("n23");

    public final StringPath n24 = createString("n24");

    public final StringPath n25 = createString("n25");

    public final StringPath n26 = createString("n26");

    public final StringPath n27 = createString("n27");

    public final StringPath n3 = createString("n3");

    public final StringPath n4 = createString("n4");

    public final StringPath n5 = createString("n5");

    public final StringPath n6 = createString("n6");

    public final StringPath n7 = createString("n7");

    public final StringPath n8 = createString("n8");

    public final StringPath n9 = createString("n9");

    public final DateTimePath<java.util.Date> timestamp = createDateTime("timestamp", java.util.Date.class);

    public QPatroncarton(String variable) {
        super(Patroncarton.class, forVariable(variable));
    }

    public QPatroncarton(Path<? extends Patroncarton> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPatroncarton(PathMetadata metadata) {
        super(Patroncarton.class, metadata);
    }

}

