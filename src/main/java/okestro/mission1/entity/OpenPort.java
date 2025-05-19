package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "open_port")
public class OpenPort extends TimestampEntity{

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "open_port_id")
    private int openPortId;

    @NotBlank(message = "포트를 기입해주세요.")
    private int port;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="inbound_ip_id")
    private InboundIp inboundIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outbound_ip_id")
    private OutboundIp outboundIp;
}
