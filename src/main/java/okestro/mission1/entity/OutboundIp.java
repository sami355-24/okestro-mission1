package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "outbound_ip")
@Getter
public class OutboundIp extends TimestampEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "outbound_ip_id")
    private int outboundIpId;

    @NotBlank(message = "ip를 입력해주세요.")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vm_id")
    @NotBlank(message = "vm을 연결해주세요.")
    private Vm vm;
}
