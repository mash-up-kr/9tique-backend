package kr.co.mash_up.nine_tique.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSellerProduct_Id is a Querydsl query type for Id
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QSellerProduct_Id extends BeanPath<SellerProduct.Id> {

    private static final long serialVersionUID = 1704331980L;

    public static final QSellerProduct_Id id = new QSellerProduct_Id("id");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> sellerId = createNumber("sellerId", Long.class);

    public QSellerProduct_Id(String variable) {
        super(SellerProduct.Id.class, forVariable(variable));
    }

    public QSellerProduct_Id(Path<? extends SellerProduct.Id> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSellerProduct_Id(PathMetadata metadata) {
        super(SellerProduct.Id.class, metadata);
    }

}

