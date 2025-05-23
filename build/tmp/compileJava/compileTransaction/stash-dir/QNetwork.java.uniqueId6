package okestro.mission1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNetwork is a Querydsl query type for Network
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNetwork extends EntityPathBase<Network> {

    private static final long serialVersionUID = -1402635035L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNetwork network = new QNetwork("network");

    public final QTimestampEntity _super = new QTimestampEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> networkId = createNumber("networkId", Integer.class);

    public final StringPath openIp = createString("openIp");

    public final NumberPath<Integer> openPort = createNumber("openPort", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    public final QVm vm;

    public QNetwork(String variable) {
        this(Network.class, forVariable(variable), INITS);
    }

    public QNetwork(Path<? extends Network> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNetwork(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNetwork(PathMetadata metadata, PathInits inits) {
        this(Network.class, metadata, inits);
    }

    public QNetwork(Class<? extends Network> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vm = inits.isInitialized("vm") ? new QVm(forProperty("vm"), inits.get("vm")) : null;
    }

}

