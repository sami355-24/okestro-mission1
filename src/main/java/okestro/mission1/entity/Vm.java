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
import okestro.mission1.dto.controller.request.CreateVmRequestDto;
import okestro.mission1.dto.service.vm.UpdateVmServiceDto;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.hibernate.annotations.CascadeType.*;

@Entity
@Table(name = "vm")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@FieldDefaults(level = PRIVATE)
@SQLDelete(sql = "UPDATE vm SET deleted = true WHERE vm_id = ?")
@SQLRestriction("deleted = false")
public class Vm extends TimestampEntity {
    /**
     * 주의! 영속성 컨텍스트를 통해서 로직수행할때만 @SQLRestriction, @SQLDelete 동작 -> JPQL로 바로 변환될 경우 동작x
     */

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "vm_id")
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
    List<Network> networks;

    @OneToMany(mappedBy = "vm", fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade({ALL})
    List<VmTag> vmTags;

    @Builder.Default
    @Column(nullable = false, name = "deleted")
    boolean deleted = false;

    public Vm(CreateVmRequestDto requestDto, String privateIp, Member member) {
        this.vmStatus = VmStatus.STARTING;
        this.name = requestDto.name();
        this.description = requestDto.description();
        this.vCpu = requestDto.vCpu();
        this.memory = requestDto.memory();
        this.storage = requestDto.storage();
        this.privateIp = privateIp;
        this.vmTags = new ArrayList<>();
        this.member = member;
    }



    public void UpdateVmStatus() {
        this.vmStatus = VmStatus.getRandomStatus();
    }

    public void updateVmFrom(UpdateVmServiceDto updateVmServiceDto) {
        if (updateVmServiceDto.name() != null) this.name = updateVmServiceDto.name();
        if (updateVmServiceDto.description() != null) this.description = updateVmServiceDto.description();
        if (updateVmServiceDto.vCpu() != null) this.vCpu = updateVmServiceDto.vCpu();
        if (updateVmServiceDto.memory() != null) this.memory = updateVmServiceDto.memory();
        setNetworksFrom(updateVmServiceDto.networks());
        setTagsFrom(updateVmServiceDto.tags());
    }

    public void setNetworksFrom(List<Network> networks) {
        this.networks.forEach(network -> network.setVm(null));
        this.networks = networks;
        networks.forEach(network -> network.setVm(this));
    }

    public void setTagsFrom(List<Tag> tags) {
        this.vmTags.clear();
        tags.forEach(
                tag -> {
                    VmTag newVmTag = new VmTag(this, tag);
                    this.vmTags.add(newVmTag);
                    newVmTag.setVm(this);
                }
        );
    }
}
