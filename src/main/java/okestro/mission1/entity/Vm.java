package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "vm")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Vm extends TimestampEntity{

    @Id
    @GeneratedValue(strategy = AUTO)
    private int vmId;

    @NotBlank(message = "상태는 비어있으면 안됩니다.")
    @Enumerated(value = ORDINAL)
    private Status status;

    @NotBlank(message = "VM 이름을 지어주세요.")
    private String title;

    private String description;

    @NotBlank(message = "사용하실 cpu 수를 입력해주세요.")
    @Column(name = "vcpu")
    private int vCpu;

    @NotBlank(message = "사용하실 memory 크기를 입력해주세요.")
    private int memory;

    @NotBlank(message = "사용하실 storage 크기를 입력해주세요.")
    private int storage;

    @Column(name = "private_ip")
    private String privateIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
