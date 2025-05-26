package okestro.mission1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVmTag is a Querydsl query type for VmTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVmTag extends EntityPathBase<VmTag> {

    private static final long serialVersionUID = 175967706L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVmTag vmTag = new QVmTag("vmTag");

    public final QTimestampEntity _super = new QTimestampEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final QTag tag;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    public final QVm vm;

    public final NumberPath<Integer> vmTagId = createNumber("vmTagId", Integer.class);

    public QVmTag(String variable) {
        this(VmTag.class, forVariable(variable), INITS);
    }

    public QVmTag(Path<? extends VmTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVmTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVmTag(PathMetadata metadata, PathInits inits) {
        this(VmTag.class, metadata, inits);
    }

    public QVmTag(Class<? extends VmTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
        this.vm = inits.isInitialized("vm") ? new QVm(forProperty("vm"), inits.get("vm")) : null;
    }

}

