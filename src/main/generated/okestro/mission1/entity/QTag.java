package okestro.mission1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTag is a Querydsl query type for Tag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTag extends EntityPathBase<Tag> {

    private static final long serialVersionUID = -2122721839L;

    public static final QTag tag = new QTag("tag");

    public final QTimestampEntity _super = new QTimestampEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    public final ListPath<VmTag, QVmTag> vmTags = this.<VmTag, QVmTag>createList("vmTags", VmTag.class, QVmTag.class, PathInits.DIRECT2);

    public QTag(String variable) {
        super(Tag.class, forVariable(variable));
    }

    public QTag(Path<? extends Tag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTag(PathMetadata metadata) {
        super(Tag.class, metadata);
    }

}

