package kr.co.mash_up.nine_tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPromotionImage_Id is a Querydsl query type for Id
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QPromotionImage_Id extends BeanPath<PromotionImage.Id> {

    private static final long serialVersionUID = -1732092034L;

    public static final QPromotionImage_Id id = new QPromotionImage_Id("id");

    public final NumberPath<Long> imageId = createNumber("imageId", Long.class);

    public final NumberPath<Long> promotionId = createNumber("promotionId", Long.class);

    public QPromotionImage_Id(String variable) {
        super(PromotionImage.Id.class, forVariable(variable));
    }

    public QPromotionImage_Id(Path<? extends PromotionImage.Id> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPromotionImage_Id(PathMetadata<?> metadata) {
        super(PromotionImage.Id.class, metadata);
    }

}

