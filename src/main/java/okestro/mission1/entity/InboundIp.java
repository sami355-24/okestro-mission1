package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "inbound_ip")
@Getter
public class InboundIp extends TimestampEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inbound_ip_id")
    private int inboundIpId;

    @NotBlank(message = "ip를 입력해주세요.")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vm_id")
    @NotBlank(message = "vm을 연결해주세요.")
    private Vm vm;


}
