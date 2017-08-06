package kr.co.mash_up.nine_tique.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShop is a Querydsl query type for Shop
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShop extends EntityPathBase<Shop> {

    private static final long serialVersionUID = -101391671L;

    public static final QShop shop = new QShop("shop");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final NumberPath<Long> commentCount = createNumber("commentCount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath kakaoOpenChatUrl = createString("kakaoOpenChatUrl");

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<Product, QProduct> products = this.<Product, QProduct>createList("products", Product.class, QProduct.class, PathInits.DIRECT2);

    public final ListPath<Seller, QSeller> sellers = this.<Seller, QSeller>createList("sellers", Seller.class, QSeller.class, PathInits.DIRECT2);

    public final ListPath<ShopComment, QShopComment> shopComments = this.<ShopComment, QShopComment>createList("shopComments", ShopComment.class, QShopComment.class, PathInits.DIRECT2);

    public final ListPath<ShopImage, QShopImage> shopImages = this.<ShopImage, QShopImage>createList("shopImages", ShopImage.class, QShopImage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShop(String variable) {
        super(Shop.class, forVariable(variable));
    }

    public QShop(Path<? extends Shop> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShop(PathMetadata metadata) {
        super(Shop.class, metadata);
    }

}

