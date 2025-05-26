package okestro.mission1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "network")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@FieldDefaults(level = PRIVATE)
public class Network extends TimestampEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "network_id")
    int networkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vm_id")
    @Setter(PUBLIC)
    private Vm vm;

    @NotBlank
    String name;

    @Pattern(regexp = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")
    String openIp;

    @Min(0)
    @Max(65535)
    int openPort;
}
