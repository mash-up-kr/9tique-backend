package kr.co.mash_up.nine_tique.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostImage_Id is a Querydsl query type for Id
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QPostImage_Id extends BeanPath<PostImage.Id> {

    private static final long serialVersionUID = -2018595775L;

    public static final QPostImage_Id id = new QPostImage_Id("id");

    public final NumberPath<Long> imageId = createNumber("imageId", Long.class);

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public QPostImage_Id(String variable) {
        super(PostImage.Id.class, forVariable(variable));
    }

    public QPostImage_Id(Path<? extends PostImage.Id> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostImage_Id(PathMetadata metadata) {
        super(PostImage.Id.class, metadata);
    }

}

