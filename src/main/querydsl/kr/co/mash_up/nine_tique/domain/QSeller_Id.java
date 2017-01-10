package kr.co.mash_up.nine_tique.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSeller_Id is a Querydsl query type for Id
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QSeller_Id extends BeanPath<Seller.Id> {

    private static final long serialVersionUID = 1163961879L;

    public static final QSeller_Id id = new QSeller_Id("id");

    public final NumberPath<Long> shopId = createNumber("shopId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QSeller_Id(String variable) {
        super(Seller.Id.class, forVariable(variable));
    }

    public QSeller_Id(Path<? extends Seller.Id> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeller_Id(PathMetadata<?> metadata) {
        super(Seller.Id.class, metadata);
    }

}

