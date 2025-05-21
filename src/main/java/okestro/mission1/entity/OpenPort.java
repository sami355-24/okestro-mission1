package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "open_port")
@FieldDefaults(level = PRIVATE)
public class OpenPort extends TimestampEntity{

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "open_port_id")
    int openPortId;

    @NotBlank(message = "포트를 기입해주세요.")
    int port;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="inbound_ip_id")
    InboundIp inboundIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outbound_ip_id")
    OutboundIp outboundIp;
}
