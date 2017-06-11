package kr.co.mash_up_9tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QZzimProduct is a Querydsl query type for ZzimProduct
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QZzimProduct extends EntityPathBase<ZzimProduct> {

    private static final long serialVersionUID = 1862815731L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QZzimProduct zzimProduct = new QZzimProduct("zzimProduct");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath enabled = createBoolean("enabled");

    public final QZzimProduct_Id id;

    public final QProduct product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QZzim zzim;

    public QZzimProduct(String variable) {
        this(ZzimProduct.class, forVariable(variable), INITS);
    }

    public QZzimProduct(Path<? extends ZzimProduct> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QZzimProduct(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QZzimProduct(PathMetadata<?> metadata, PathInits inits) {
        this(ZzimProduct.class, metadata, inits);
    }

    public QZzimProduct(Class<? extends ZzimProduct> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QZzimProduct_Id(forProperty("id")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.zzim = inits.isInitialized("zzim") ? new QZzim(forProperty("zzim"), inits.get("zzim")) : null;
    }

}

