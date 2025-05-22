package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import okestro.mission1.dto.request.CreateVmRequest;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "vm")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@FieldDefaults(level = PRIVATE)
@SQLDelete(sql = "UPDATE Vm SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Vm extends TimestampEntity {
    /**
     * 주의! 영속성 컨텍스트를 통해서 로직수행할때만 @SQLRestriction, @SQLDelete 동작 -> JPQL로 바로 변환될 경우 동작x
     */

    @Id
    @GeneratedValue(strategy = IDENTITY)
    int vmId;

    @NotNull
    @Enumerated(value = STRING)
    VmStatus vmStatus;

    @NotBlank(message = "VM 이름을 지어주세요.")
    @Column(unique = true)
    String name;

    String description;

    @NotNull(message = "사용하실 cpu 수를 입력해주세요.")
    @Column(name = "vcpu")
    @Min(1)
    Integer vCpu;

    @NotNull(message = "사용하실 memory 크기를 입력해주세요.")
    @Min(1)
    Integer memory;

    @NotNull(message = "사용하실 storage 크기를 입력해주세요.")
    @Min(1)
    Integer storage;

    @Column(name = "private_ip", unique = true)
    String privateIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @OneToMany(mappedBy = "vm", fetch = FetchType.LAZY)
    List<Network> network;

    @Builder.Default
    @Column(nullable = false)
    boolean deleted = false;

    public Vm(CreateVmRequest requestDto, String privateIp) {
        this.vmStatus = VmStatus.STARTING;
        this.name = requestDto.name();
        this.description = requestDto.description();
        this.vCpu = requestDto.vCpu();
        this.memory = requestDto.memory();
        this.storage = requestDto.storage();
        this.privateIp = privateIp;
        this.network = requestDto.networkIds().stream()
                .map(networkId -> Network.builder()
                        .networkId(networkId)
                        .vm(this)
                        .build())
                .toList();
    }
}
