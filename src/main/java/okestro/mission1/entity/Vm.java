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
import okestro.mission1.exception.custom.InvalidDataException;
import okestro.mission1.socket.VmEntityListener;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static okestro.mission1.util.Message.ERROR_BLANK_NETWORK_ID;
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
@EntityListeners(value = VmEntityListener.class)
public class Vm extends TimestampEntity {
    static final String ERROR_VM_NAME_BLANK = "VM 이름은 공백이 될 수 없습니다.";
    static final String ERROR_VM_VCPU_IS_NULL = "VM이 사용할 CPU가 null입니다.";
    static final String ERROR_VM_MEMORY_IS_NULL = "VM이 사용할 MEMORY가 null입니다.";
    static final String ERROR_VM_STORAGE_IS_NULL = "VM이 사용할 STORAGE가 null입니다.";
    static final String ERROR_VM_MIN_RESOURCE = "자원을 1 이상 입력해주세요";


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "vm_id")
    int vmId;

    @NotNull
    @Enumerated(value = STRING)
    VmStatus vmStatus;

    @NotBlank(message = ERROR_VM_NAME_BLANK)
    @Column(unique = true)
    String name;

    String description;

    @NotNull(message = ERROR_VM_VCPU_IS_NULL)
    @Column(name = "vcpu")
    @Min(value = 1, message = ERROR_VM_MIN_RESOURCE)
    Integer vCpu;

    @NotNull(message = ERROR_VM_MEMORY_IS_NULL)
    @Min(value = 1, message = ERROR_VM_MIN_RESOURCE)
    Integer memory;

    @NotNull(message = ERROR_VM_STORAGE_IS_NULL)
    @Min(value = 1, message = ERROR_VM_MIN_RESOURCE)
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

    @Transient
    private VmStatus previousVmStatus;


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
        this.networks = new ArrayList<>();
    }

    @PostLoad
    public void storePreviousStatus() {
        this.previousVmStatus = this.vmStatus;
    }

    public void updateVmStatus() {
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
        if (networks == null) return;
        if (networks.isEmpty()) throw new InvalidDataException(ERROR_BLANK_NETWORK_ID.getMessage());
        this.networks.forEach(network -> network.setVm(null));
        networks.forEach(network -> network.setVm(this));
        this.networks = networks;
    }

    public void setTagsFrom(List<Tag> tags) {
        if (tags.isEmpty()) return;
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
