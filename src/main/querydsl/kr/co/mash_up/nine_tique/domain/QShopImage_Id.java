package kr.co.mash_up.nine_tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QShopImage_Id is a Querydsl query type for Id
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QShopImage_Id extends BeanPath<ShopImage.Id> {

    private static final long serialVersionUID = 1783314583L;

    public static final QShopImage_Id id = new QShopImage_Id("id");

    public final NumberPath<Long> imageId = createNumber("imageId", Long.class);

    public final NumberPath<Long> shopId = createNumber("shopId", Long.class);

    public QShopImage_Id(String variable) {
        super(ShopImage.Id.class, forVariable(variable));
    }

    public QShopImage_Id(Path<? extends ShopImage.Id> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShopImage_Id(PathMetadata<?> metadata) {
        super(ShopImage.Id.class, metadata);
    }

}

