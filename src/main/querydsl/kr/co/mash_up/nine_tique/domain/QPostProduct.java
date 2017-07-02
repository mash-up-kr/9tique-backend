package kr.co.mash_up.nine_tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPostProduct is a Querydsl query type for PostProduct
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPostProduct extends EntityPathBase<PostProduct> {

    private static final long serialVersionUID = 900066556L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostProduct postProduct = new QPostProduct("postProduct");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QPostProduct_Id id;

    public final QPost post;

    public final QProduct product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPostProduct(String variable) {
        this(PostProduct.class, forVariable(variable), INITS);
    }

    public QPostProduct(Path<? extends PostProduct> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPostProduct(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPostProduct(PathMetadata<?> metadata, PathInits inits) {
        this(PostProduct.class, metadata, inits);
    }

    public QPostProduct(Class<? extends PostProduct> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPostProduct_Id(forProperty("id")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

