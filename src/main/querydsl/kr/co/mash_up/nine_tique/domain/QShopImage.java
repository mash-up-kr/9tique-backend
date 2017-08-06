package kr.co.mash_up.nine_tique.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShopImage is a Querydsl query type for ShopImage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShopImage extends EntityPathBase<ShopImage> {

    private static final long serialVersionUID = 553528338L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShopImage shopImage = new QShopImage("shopImage");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QShopImage_Id id;

    public final QImage image;

    public final QShop shop;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShopImage(String variable) {
        this(ShopImage.class, forVariable(variable), INITS);
    }

    public QShopImage(Path<? extends ShopImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShopImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShopImage(PathMetadata metadata, PathInits inits) {
        this(ShopImage.class, metadata, inits);
    }

    public QShopImage(Class<? extends ShopImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QShopImage_Id(forProperty("id")) : null;
        this.image = inits.isInitialized("image") ? new QImage(forProperty("image")) : null;
        this.shop = inits.isInitialized("shop") ? new QShop(forProperty("shop")) : null;
    }

}

