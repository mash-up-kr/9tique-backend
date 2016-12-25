package kr.co.mash_up.nine_tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -101321826L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final SetPath<Authority, QAuthority> authorities = this.<Authority, QAuthority>createSet("authorities", Authority.class, QAuthority.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath oauthToken = createString("oauthToken");

    public final EnumPath<User.OauthType> oauthType = createEnum("oauthType", User.OauthType.class);

    public final QSellerInfo sellerInfo;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QZzim zzim;

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUser(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUser(PathMetadata<?> metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sellerInfo = inits.isInitialized("sellerInfo") ? new QSellerInfo(forProperty("sellerInfo"), inits.get("sellerInfo")) : null;
        this.zzim = inits.isInitialized("zzim") ? new QZzim(forProperty("zzim"), inits.get("zzim")) : null;
    }

}

