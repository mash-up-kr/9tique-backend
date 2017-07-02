package kr.co.mash_up.nine_tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QSellerProduct is a Querydsl query type for SellerProduct
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QSellerProduct extends EntityPathBase<SellerProduct> {

    private static final long serialVersionUID = 121015805L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSellerProduct sellerProduct = new QSellerProduct("sellerProduct");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final BooleanPath active = createBoolean("active");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QSellerProduct_Id id;

    public final QProduct product;

    public final QSeller seller;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSellerProduct(String variable) {
        this(SellerProduct.class, forVariable(variable), INITS);
    }

    public QSellerProduct(Path<? extends SellerProduct> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSellerProduct(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSellerProduct(PathMetadata<?> metadata, PathInits inits) {
        this(SellerProduct.class, metadata, inits);
    }

    public QSellerProduct(Class<? extends SellerProduct> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QSellerProduct_Id(forProperty("id")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.seller = inits.isInitialized("seller") ? new QSeller(forProperty("seller"), inits.get("seller")) : null;
    }

}

