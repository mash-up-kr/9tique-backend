package kr.co.mash_up.nine_tique.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QZzimProduct_Id is a Querydsl query type for Id
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QZzimProduct_Id extends BeanPath<ZzimProduct.Id> {

    private static final long serialVersionUID = -888166863L;

    public static final QZzimProduct_Id id = new QZzimProduct_Id("id");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> zzimId = createNumber("zzimId", Long.class);

    public QZzimProduct_Id(String variable) {
        super(ZzimProduct.Id.class, forVariable(variable));
    }

    public QZzimProduct_Id(Path<? extends ZzimProduct.Id> path) {
        super(path.getType(), path.getMetadata());
    }

    public QZzimProduct_Id(PathMetadata metadata) {
        super(ZzimProduct.Id.class, metadata);
    }

}

