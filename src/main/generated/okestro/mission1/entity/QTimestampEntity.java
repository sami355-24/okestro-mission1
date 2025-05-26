package okestro.mission1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTimestampEntity is a Querydsl query type for TimestampEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QTimestampEntity extends EntityPathBase<TimestampEntity> {

    private static final long serialVersionUID = 503303248L;

    public static final QTimestampEntity timestampEntity = new QTimestampEntity("timestampEntity");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public QTimestampEntity(String variable) {
        super(TimestampEntity.class, forVariable(variable));
    }

    public QTimestampEntity(Path<? extends TimestampEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTimestampEntity(PathMetadata metadata) {
        super(TimestampEntity.class, metadata);
    }

}

