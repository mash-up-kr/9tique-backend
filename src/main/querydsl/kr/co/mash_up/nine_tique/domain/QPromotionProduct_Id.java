package kr.co.mash_up.nine_tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPromotionProduct_Id is a Querydsl query type for Id
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QPromotionProduct_Id extends BeanPath<PromotionProduct.Id> {

    private static final long serialVersionUID = 854175594L;

    public static final QPromotionProduct_Id id = new QPromotionProduct_Id("id");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> promotionId = createNumber("promotionId", Long.class);

    public QPromotionProduct_Id(String variable) {
        super(PromotionProduct.Id.class, forVariable(variable));
    }

    public QPromotionProduct_Id(Path<? extends PromotionProduct.Id> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPromotionProduct_Id(PathMetadata<?> metadata) {
        super(PromotionProduct.Id.class, metadata);
    }

}

