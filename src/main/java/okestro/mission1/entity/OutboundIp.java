package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Table(name = "outbound_ip")
@Getter
@FieldDefaults(level = PRIVATE)
public class OutboundIp extends TimestampEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "outbound_ip_id")
    int outboundIpId;

    @NotBlank(message = "ip를 입력해주세요.")
    String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vm_id")
    @NotBlank(message = "vm을 연결해주세요.")
    Vm vm;
}
