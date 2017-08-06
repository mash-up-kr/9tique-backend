package kr.co.mash_up.nine_tique.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromotionProduct is a Querydsl query type for PromotionProduct
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPromotionProduct extends EntityPathBase<PromotionProduct> {

    private static final long serialVersionUID = -337617377L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromotionProduct promotionProduct = new QPromotionProduct("promotionProduct");

    public final QPromotionProduct_Id id;

    public final QProduct product;

    public final QPromotion promotion;

    public QPromotionProduct(String variable) {
        this(PromotionProduct.class, forVariable(variable), INITS);
    }

    public QPromotionProduct(Path<? extends PromotionProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromotionProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromotionProduct(PathMetadata metadata, PathInits inits) {
        this(PromotionProduct.class, metadata, inits);
    }

    public QPromotionProduct(Class<? extends PromotionProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPromotionProduct_Id(forProperty("id")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.promotion = inits.isInitialized("promotion") ? new QPromotion(forProperty("promotion")) : null;
    }

}

