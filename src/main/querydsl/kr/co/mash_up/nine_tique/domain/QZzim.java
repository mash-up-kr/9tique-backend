package kr.co.mash_up.nine_tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QZzim is a Querydsl query type for Zzim
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
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

    public final MapPath<ZzimProduct.Id, ZzimProduct, QZzimProduct> zzimProducts = this.<ZzimProduct.Id, ZzimProduct, QZzimProduct>createMap("zzimProducts", ZzimProduct.Id.class, ZzimProduct.class, QZzimProduct.class);

    public QZzim(String variable) {
        this(Zzim.class, forVariable(variable), INITS);
    }

    public QZzim(Path<? extends Zzim> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QZzim(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QZzim(PathMetadata<?> metadata, PathInits inits) {
        this(Zzim.class, metadata, inits);
    }

    public QZzim(Class<? extends Zzim> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

