package kr.co.mash_up.nine_tique.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSellerProduct is a Querydsl query type for SellerProduct
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSellerProduct extends EntityPathBase<SellerProduct> {

    private static final long serialVersionUID = 121015805L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSellerProduct sellerProduct = new QSellerProduct("sellerProduct");

    public final QAbstractEntity _super = new QAbstractEntity(this);

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
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSellerProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSellerProduct(PathMetadata metadata, PathInits inits) {
        this(SellerProduct.class, metadata, inits);
    }

    public QSellerProduct(Class<? extends SellerProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QSellerProduct_Id(forProperty("id")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.seller = inits.isInitialized("seller") ? new QSeller(forProperty("seller"), inits.get("seller")) : null;
    }

}

