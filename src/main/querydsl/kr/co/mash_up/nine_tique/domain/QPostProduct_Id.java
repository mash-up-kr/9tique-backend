package kr.co.mash_up.nine_tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPostProduct_Id is a Querydsl query type for Id
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QPostProduct_Id extends BeanPath<PostProduct.Id> {

    private static final long serialVersionUID = 401987437L;

    public static final QPostProduct_Id id = new QPostProduct_Id("id");

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public QPostProduct_Id(String variable) {
        super(PostProduct.Id.class, forVariable(variable));
    }

    public QPostProduct_Id(Path<? extends PostProduct.Id> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostProduct_Id(PathMetadata<?> metadata) {
        super(PostProduct.Id.class, metadata);
    }

}

