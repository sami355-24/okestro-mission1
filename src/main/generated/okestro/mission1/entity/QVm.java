package okestro.mission1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVm is a Querydsl query type for Vm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVm extends EntityPathBase<Vm> {

    private static final long serialVersionUID = -761211488L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVm vm = new QVm("vm");

    public final QTimestampEntity _super = new QTimestampEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath description = createString("description");

    public final QMember member;

    public final NumberPath<Integer> memory = createNumber("memory", Integer.class);

    public final StringPath name = createString("name");

    public final ListPath<Network, QNetwork> networks = this.<Network, QNetwork>createList("networks", Network.class, QNetwork.class, PathInits.DIRECT2);

    public final StringPath privateIp = createString("privateIp");

    public final NumberPath<Integer> storage = createNumber("storage", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateAt = _super.updateAt;

    public final NumberPath<Integer> vCpu = createNumber("vCpu", Integer.class);

    public final NumberPath<Integer> vmId = createNumber("vmId", Integer.class);

    public final EnumPath<VmStatus> vmStatus = createEnum("vmStatus", VmStatus.class);

    public final ListPath<VmTag, QVmTag> vmTags = this.<VmTag, QVmTag>createList("vmTags", VmTag.class, QVmTag.class, PathInits.DIRECT2);

    public QVm(String variable) {
        this(Vm.class, forVariable(variable), INITS);
    }

    public QVm(Path<? extends Vm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVm(PathMetadata metadata, PathInits inits) {
        this(Vm.class, metadata, inits);
    }

    public QVm(Class<? extends Vm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

