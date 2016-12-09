package kr.co.mash_up.nine_tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QSellerInfo is a Querydsl query type for SellerInfo
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QSellerInfo extends EntityPathBase<SellerInfo> {

    private static final long serialVersionUID = 1723919968L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSellerInfo sellerInfo = new QSellerInfo("sellerInfo");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath phone = createString("phone");

    public final ListPath<Product, QProduct> products = this.<Product, QProduct>createList("products", Product.class, QProduct.class, PathInits.DIRECT2);

    public final StringPath shopInfo = createString("shopInfo");

    public final StringPath shopName = createString("shopName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public QSellerInfo(String variable) {
        this(SellerInfo.class, forVariable(variable), INITS);
    }

    public QSellerInfo(Path<? extends SellerInfo> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSellerInfo(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSellerInfo(PathMetadata<?> metadata, PathInits inits) {
        this(SellerInfo.class, metadata, inits);
    }

    public QSellerInfo(Class<? extends SellerInfo> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

