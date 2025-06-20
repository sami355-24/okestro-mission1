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

    static final String IP_PATTERN = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    static final String ERROR_NOT_MATCH_IP_FORMAT = "IPv4 형식과 다릅니다.";
    static final String ERROR_VALUE_IS_HIGHER_THAN_MiN = "값은 1 이상이어야 합니다.";
    static final String ERROR_VALUE_IS_LOWER_THAN_MAX = "값은 65535 이하여야 합니다.";

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

    @Pattern(
            regexp = IP_PATTERN,
            message = ERROR_NOT_MATCH_IP_FORMAT)
    String openIp;

    @Min(value = 0, message = ERROR_VALUE_IS_HIGHER_THAN_MiN)
    @Max(value = 65535, message = ERROR_VALUE_IS_LOWER_THAN_MAX)
    int openPort;

    public void detachVm() {
        this.vm = null;
    }
}
