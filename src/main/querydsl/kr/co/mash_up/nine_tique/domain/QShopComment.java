package kr.co.mash_up.nine_tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QShopComment is a Querydsl query type for ShopComment
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QShopComment extends EntityPathBase<ShopComment> {

    private static final long serialVersionUID = -1596743850L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShopComment shopComment = new QShopComment("shopComment");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QShop shop;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser writer;

    public QShopComment(String variable) {
        this(ShopComment.class, forVariable(variable), INITS);
    }

    public QShopComment(Path<? extends ShopComment> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QShopComment(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QShopComment(PathMetadata<?> metadata, PathInits inits) {
        this(ShopComment.class, metadata, inits);
    }

    public QShopComment(Class<? extends ShopComment> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shop = inits.isInitialized("shop") ? new QShop(forProperty("shop")) : null;
        this.writer = inits.isInitialized("writer") ? new QUser(forProperty("writer"), inits.get("writer")) : null;
    }

}

