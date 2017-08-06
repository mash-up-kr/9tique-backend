package kr.co.mash_up.nine_tique.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QZzim is a Querydsl query type for Zzim
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QZzim extends EntityPathBase<Zzim> {

    private static final long serialVersionUID = -101166025L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QZzim zzim = new QZzim("zzim");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public final ListPath<ZzimProduct, QZzimProduct> zzimProducts = this.<ZzimProduct, QZzimProduct>createList("zzimProducts", ZzimProduct.class, QZzimProduct.class, PathInits.DIRECT2);

    public QZzim(String variable) {
        this(Zzim.class, forVariable(variable), INITS);
    }

    public QZzim(Path<? extends Zzim> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QZzim(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QZzim(PathMetadata metadata, PathInits inits) {
        this(Zzim.class, metadata, inits);
    }

    public QZzim(Class<? extends Zzim> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

