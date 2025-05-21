package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Table(name = "inbound_ip")
@Getter
@FieldDefaults(level = PRIVATE)
public class InboundIp extends TimestampEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inbound_ip_id")
    int inboundIpId;

    @NotBlank(message = "ip를 입력해주세요.")
    String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vm_id")
    @NotBlank(message = "vm을 연결해주세요.")
    Vm vm;
}
