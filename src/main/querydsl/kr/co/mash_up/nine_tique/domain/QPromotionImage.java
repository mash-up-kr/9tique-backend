package kr.co.mash_up.nine_tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPromotionImage is a Querydsl query type for PromotionImage
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPromotionImage extends EntityPathBase<PromotionImage> {

    private static final long serialVersionUID = 958383755L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromotionImage promotionImage = new QPromotionImage("promotionImage");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final BooleanPath active = createBoolean("active");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originalFileName = createString("originalFileName");

    public final QPromotion promotion;

    public final NumberPath<Long> size = createNumber("size", Long.class);

    public final StringPath tempImageUrl = createString("tempImageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPromotionImage(String variable) {
        this(PromotionImage.class, forVariable(variable), INITS);
    }

    public QPromotionImage(Path<? extends PromotionImage> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPromotionImage(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPromotionImage(PathMetadata<?> metadata, PathInits inits) {
        this(PromotionImage.class, metadata, inits);
    }

    public QPromotionImage(Class<? extends PromotionImage> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.promotion = inits.isInitialized("promotion") ? new QPromotion(forProperty("promotion")) : null;
    }

}

