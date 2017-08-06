package kr.co.mash_up.nine_tique.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostImage is a Querydsl query type for PostImage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPostImage extends EntityPathBase<PostImage> {

    private static final long serialVersionUID = 365258920L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostImage postImage = new QPostImage("postImage");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QPostImage_Id id;

    public final QImage image;

    public final QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPostImage(String variable) {
        this(PostImage.class, forVariable(variable), INITS);
    }

    public QPostImage(Path<? extends PostImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostImage(PathMetadata metadata, PathInits inits) {
        this(PostImage.class, metadata, inits);
    }

    public QPostImage(Class<? extends PostImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPostImage_Id(forProperty("id")) : null;
        this.image = inits.isInitialized("image") ? new QImage(forProperty("image")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post")) : null;
    }

}

