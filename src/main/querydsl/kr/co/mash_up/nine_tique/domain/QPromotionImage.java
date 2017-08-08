package kr.co.mash_up.nine_tique.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromotionImage is a Querydsl query type for PromotionImage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPromotionImage extends EntityPathBase<PromotionImage> {

    private static final long serialVersionUID = 958383755L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromotionImage promotionImage = new QPromotionImage("promotionImage");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QPromotionImage_Id id;

    public final QImage image;

    public final QPromotion promotion;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPromotionImage(String variable) {
        this(PromotionImage.class, forVariable(variable), INITS);
    }

    public QPromotionImage(Path<? extends PromotionImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromotionImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromotionImage(PathMetadata metadata, PathInits inits) {
        this(PromotionImage.class, metadata, inits);
    }

    public QPromotionImage(Class<? extends PromotionImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPromotionImage_Id(forProperty("id")) : null;
        this.image = inits.isInitialized("image") ? new QImage(forProperty("image")) : null;
        this.promotion = inits.isInitialized("promotion") ? new QPromotion(forProperty("promotion")) : null;
    }

}

