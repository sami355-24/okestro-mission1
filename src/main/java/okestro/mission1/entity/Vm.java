package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "vm")
@NoArgsConstructor(access = PROTECTED)
@Getter
@FieldDefaults(level = PRIVATE)
public class Vm extends TimestampEntity{

    @Id
    @GeneratedValue(strategy = AUTO)
    int vmId;

    @NotBlank(message = "상태는 비어있으면 안됩니다.")
    @Enumerated(value = ORDINAL)
    Status status;

    @NotBlank(message = "VM 이름을 지어주세요.")
    String title;

    String description;

    @NotBlank(message = "사용하실 cpu 수를 입력해주세요.")
    @Column(name = "vcpu")
    int vCpu;

    @NotBlank(message = "사용하실 memory 크기를 입력해주세요.")
    int memory;

    @NotBlank(message = "사용하실 storage 크기를 입력해주세요.")
    int storage;

    @Column(name = "private_ip")
    String privateIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;
}
