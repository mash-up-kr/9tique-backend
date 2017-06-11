package kr.co.mash_up_9tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSellerProduct_Id is a Querydsl query type for Id
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QSellerProduct_Id extends BeanPath<SellerProduct.Id> {

    private static final long serialVersionUID = 1174191217L;

    public static final QSellerProduct_Id id = new QSellerProduct_Id("id");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> sellerId = createNumber("sellerId", Long.class);

    public QSellerProduct_Id(String variable) {
        super(SellerProduct.Id.class, forVariable(variable));
    }

    public QSellerProduct_Id(Path<? extends SellerProduct.Id> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSellerProduct_Id(PathMetadata<?> metadata) {
        super(SellerProduct.Id.class, metadata);
    }

}

